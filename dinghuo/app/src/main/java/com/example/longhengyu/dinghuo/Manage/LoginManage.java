package com.example.longhengyu.dinghuo.Manage;

import com.example.longhengyu.dinghuo.Manage.Bean.LoginBean;
import com.example.longhengyu.dinghuo.Tools.MyApplication;

/**
 * Created by longhengyu on 2017/2/9.
 */
public class LoginManage {

    private static LoginManage instance = new LoginManage();
    private LoginManage (){}
    public static LoginManage getInstance() {
        return instance;
    }

    private static LoginBean bean;

    public void rewriteLoginBean(LoginBean loginBean){
        bean = loginBean;
    }

    public LoginBean returnLoginBean(){
        return bean;
    }

    public String returnToken(){
        return bean.access_token;
    }
}
