package com.example.longhengyu.dinghuo.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.longhengyu.dinghuo.MainActivity;
import com.example.longhengyu.dinghuo.Manage.Bean.LoginBean;
import com.example.longhengyu.dinghuo.Manage.LoginManage;
import com.example.longhengyu.dinghuo.NetWork.RequestTools;
import com.example.longhengyu.dinghuo.R;
import com.example.longhengyu.dinghuo.Tools.BaseActivity;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by longhengyu on 2017/2/9.
 */
public class LoginActivity extends BaseActivity {

    private EditText accEditText;
    private EditText passEditText;
    private TextView loginBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView(){
        accEditText = (EditText)findViewById(R.id.Login_acc);
        passEditText = (EditText)findViewById(R.id.Login_pass);
        accEditText.setText("13676917233");
        passEditText.setText("123456");
        loginBtn = (TextView)findViewById(R.id.Login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLogin(accEditText.getText().toString(),passEditText.getText().toString());
            }
        });
    }

    private void requestLogin(final String acc, String pass){
        Map<String,String> map = new HashMap<>();
        map.put("loginUserName",acc);
        map.put("loginValidate",pass);
        map.put("loginPwd","");
        final BaseActivity activity = this;
        activity.showProgressDialog();
        RequestTools.getInstance().postRequest("/api/login", false, map, "login", new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.closeProgressDialog();
                        Toast.makeText(activity,"登录失败",Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(String response, int id) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.closeProgressDialog();
                    }
                });

                try {

                    JSONObject object = new JSONObject(response);
                    boolean result = object.getBoolean("result");
                    if(result){
                        Gson gson = new Gson();
                        String string = object.getString("data");
                        LoginBean bean = gson.fromJson(string,LoginBean.class);
                        LoginManage.getInstance().rewriteLoginBean(bean);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity,"登录成功",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.setClass(activity, MainActivity.class);
                                startActivity(intent);
                            }
                        });

                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity,"登录失败",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity,"登录失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
