package com.example.bankingapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bankingapp.R;
import com.example.bankingapp.activities.Exchange;
import com.example.bankingapp.activities.PayBill;
import com.example.bankingapp.activities.Transfer;
import com.example.bankingapp.storage.UserStorage;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    private UserStorage userStorage;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CardView transfer, report, pay_bill, exchange;
    private List<CardView> listCard;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            userStorage = new UserStorage(requireContext());
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        transfer = view.findViewById(R.id.transfer);
        report = view.findViewById(R.id.report);
        pay_bill = view.findViewById(R.id.pay_bill);
        exchange = view.findViewById(R.id.exchange);
        TextView textView = view.findViewById(R.id.textView3);
        TextView card_name = view.findViewById(R.id.card_name);

        textView.setText(String.format("Hi, %s", userStorage.getUser().getName()));
        card_name.setText(userStorage.getUser().getName());



        listCard = new ArrayList<CardView>(Arrays.asList(transfer, report, pay_bill, exchange));

        listCard.forEach(card -> {
            card.setOnClickListener(v -> {
                if (v.getId() == transfer.getId()) {
                    Intent intent = new Intent(getActivity(), Transfer.class);
                    startActivity(intent);
                } else if (v.getId() == report.getId()) {
//                    Intent intent = new Intent(getActivity(), TransferReport.class);
//                    startActivity(intent);
                } else if (v.getId() == pay_bill.getId()) {
                    Intent intent = new Intent(getActivity(), PayBill.class);
                    startActivity(intent);
                } else if (v.getId() == exchange.getId()) {
                    Intent intent = new Intent(getActivity(), Exchange.class);
                    startActivity(intent);
                }
            });
        });

        // Inflate the layout for this fragment
        return view;
    }
}