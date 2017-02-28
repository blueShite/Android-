package com.example.longhengyu.dinghuo.Tools;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


/**
 * Created by longhengyu on 2016/12/29.
 */
public class BaseActivity extends AppCompatActivity {

    public ProgressDialog progressDialog;

    private long firstTime=0;
    public Boolean isLoding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customWindow();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    public void customWindow(){
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    public void showProgressDialog(){

        if(progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        isLoding = true;
        progressDialog.show();
    }

    public void closeProgressDialog(){
        if(progressDialog!=null){
            isLoding = false;
            progressDialog.dismiss();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                long secondTime=System.currentTimeMillis();
                if(secondTime-firstTime>2000){
                    Toast.makeText(this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
                    firstTime=secondTime;
                    if(ActivityCollector.activities.size()>2){
                        return true;
                    }
                    return false;
                }else{
                    ActivityCollector.finishAll();
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }
}