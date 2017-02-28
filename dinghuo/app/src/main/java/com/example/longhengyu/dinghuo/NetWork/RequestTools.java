package com.example.longhengyu.dinghuo.NetWork;


import android.content.SharedPreferences;
import android.util.Log;

import com.example.longhengyu.dinghuo.Manage.LoginManage;
import com.example.longhengyu.dinghuo.Tools.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

/**
 * Created by longhengyu on 2016/12/30.
 */
public class RequestTools {

    //static修饰的静态变量在内存中一旦创建，便永久存在
    private static RequestTools instance = new RequestTools();
    private RequestTools (){}
    public static RequestTools getInstance() {
        return instance;
    }

    private static final String BaseUrl = "http://dappres.aibianli.cn";

    public  void getRequest(String path, boolean isHeader, Map<String,String> map,String tag, StringCallback callback){

        String requestUrl = BaseUrl+path;

        if(isHeader){
            OkHttpUtils
                    .get()
                    .url(requestUrl)
                    .params(map)
                    .addHeader("Authorization","bearer "+ LoginManage.getInstance().returnToken())
                    .tag(tag)
                    .build()
                    .execute(callback);

        }else {
            OkHttpUtils
                    .get()
                    .url(requestUrl)
                    .params(map)
                    .tag(tag)
                    .build()
                    .execute(callback);
        }

    }

    public void postRequest(String path, boolean isHeader, Map<String,String> map,String tag,Callback callback){

        String requestUrl = BaseUrl+path;

        if(isHeader){
            OkHttpUtils
                    .post()
                    .url(requestUrl)
                    .params(map)
                    .addHeader("Authorization","bearer "+ LoginManage.getInstance().returnToken())
                    .tag(tag)
                    .build()
                    .execute(callback);

        }else {
            OkHttpUtils
                    .post()
                    .url(requestUrl)
                    .params(map)
                    .tag(tag)
                    .build()
                    .execute(callback);
        }
    }

}
