package com.example.bankingapp.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bankingapp.R;
import com.example.bankingapp.activities.Language;
import com.example.bankingapp.activities.SignIn;
import com.example.bankingapp.database.dto.UserDTO;
import com.example.bankingapp.storage.UserStorage;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Locale;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Setting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Setting extends Fragment {

    private TextView lang;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private Button backBtn, logout;
    private String mParam2;

    public Setting() {
        // Required empty public constructor
    }

    public static Setting newInstance(String param1, String param2) {
        Setting fragment = new Setting();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        backBtn = view.findViewById(R.id.setting_back);
        logout = view.findViewById(R.id.logout);

        logout.setOnClickListener(v -> {
            try {
                UserStorage userStorage = new UserStorage(getContext());
                userStorage.clearUser();
                userStorage.clearToken();
                userStorage.clearLanguage();
            } catch (GeneralSecurityException | IOException e) {
                throw new RuntimeException(e);
            }

            Intent intent = new Intent(getActivity(), SignIn.class);
            startActivity(intent);
        });

        backBtn.setOnClickListener(v -> {
            Log.d("TAG", "fragment: " + requireActivity().getSupportFragmentManager().getBackStackEntryCount());

            requireActivity().getSupportFragmentManager().popBackStack();
        });

        UserStorage userStorage = null;
        try {
            userStorage = new UserStorage(requireContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert userStorage != null;
        String userName = userStorage.getUser().getName();

        TextView name = view.findViewById(R.id.setting_name);
        name.setText(userName);

        lang = view.findViewById(R.id.setting_language);
        lang.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Language.class);
            startActivity(intent);
        });

        return view;
    }


}
