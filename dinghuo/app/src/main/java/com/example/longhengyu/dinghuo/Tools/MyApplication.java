package com.example.longhengyu.dinghuo.Tools;

import android.app.Application;
import android.content.Context;

/**
 * Created by longhengyu on 2016/12/29.
 */
public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {

        context = getApplicationContext();
    }

    public static Context getContext(){

        return context;
    }
}
