package com.example.longhengyu.dinghuo.Person;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.longhengyu.dinghuo.R;
import com.example.longhengyu.dinghuo.Title.TitleFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonFragment extends Fragment {

    private View view;
    private TitleFragment titleFragment;

    public PersonFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_person,container,false);
        initTitle();
        return view;
    }

    //初始化导航条
    private void initTitle(){

        titleFragment = new TitleFragment("个人中心",true);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.personTitle,titleFragment);
        ft.commit();
    }
}
