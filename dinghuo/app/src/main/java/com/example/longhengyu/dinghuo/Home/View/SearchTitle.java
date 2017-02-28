package com.example.longhengyu.dinghuo.Home.View;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.longhengyu.dinghuo.R;

/**
 * Created by longhengyu on 2016/12/29.
 */
public class SearchTitle extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_searchtitle,container,false);
        return view;
    }
}
