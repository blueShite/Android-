package com.example.longhengyu.dinghuo.Title;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.longhengyu.dinghuo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TitleFragment extends Fragment {


    private final String title;
    private final boolean hidenBackBtn;

    private View view;
    private TextView textView;
    public Button button;

    public TitleFragment(String title, boolean hidenBackBtn) {
        // Required empty public constructor

        this.title = title;
        this.hidenBackBtn = hidenBackBtn;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_title, container, false);
        textView = (TextView)view.findViewById(R.id.nav_titleText);
        button = (Button)view.findViewById(R.id.nav_backBtn);
        textView.setText(title);
        if(hidenBackBtn){
            button.setVisibility(View.GONE);
        }

        return view;
    }

}
