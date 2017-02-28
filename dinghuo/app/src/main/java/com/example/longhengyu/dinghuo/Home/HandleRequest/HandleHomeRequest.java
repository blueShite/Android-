package com.example.longhengyu.dinghuo.Home.HandleRequest;

import com.example.longhengyu.dinghuo.Home.Bean.CommodityBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longhengyu on 2016/12/30.
 */
public class HandleHomeRequest {

    public static List<CommodityBean> handleHomeCommodityResponse(String response) throws JSONException {

        try {
            Gson gson = new Gson();
            List<CommodityBean> list = gson.fromJson(response,new TypeToken<List<CommodityBean>>(){}.getType());
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }
}
