package com.example.longhengyu.dinghuo.Home;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.example.longhengyu.dinghuo.Home.Adapter.HomeGroupAdapter;
import com.example.longhengyu.dinghuo.Home.Bean.CommodityBean;
import com.example.longhengyu.dinghuo.Home.HandleRequest.HandleHomeRequest;
import com.example.longhengyu.dinghuo.Home.Interface.HomeGroupInterface;
import com.example.longhengyu.dinghuo.NetWork.RequestTools;
import com.example.longhengyu.dinghuo.R;
import com.example.longhengyu.dinghuo.Tools.BaseActivity;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by longhengyu on 2016/12/29.
 */
public class HomeFragment extends Fragment {

    private View view;
    private RecyclerView homeRecycler;
    private List<List<CommodityBean>> list;
    RecyclerViewHeader header;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home,container,false);
        list = new ArrayList<>();
        initView();
        requestHomeData("hot");
        return view;
    }

    private void initView(){

        homeRecycler = (RecyclerView)view.findViewById(R.id.HomeRecyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        homeRecycler.setLayoutManager(manager);
        header = RecyclerViewHeader.fromXml(getActivity(), R.layout.header_home);
        header.attachTo(homeRecycler);
        HomeGroupAdapter adapter = new HomeGroupAdapter(list, new HomeGroupInterface() {
            @Override
            public void onClickItem(int group, int post) {
                Toast.makeText(getActivity(),"第"+group+"组"+"第"+post+"个",Toast.LENGTH_SHORT).show();
            }
        });
        homeRecycler.setAdapter(adapter);
        TextView headerClass1 = (TextView)header.findViewById(R.id.homeheader_class1);
        headerClass1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"点击了酒水饮料",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void requestHomeData(final String type) {

        final BaseActivity activity = (BaseActivity)getActivity();
        if(type.equals("hot")){
            activity.showProgressDialog();
        }
        Map<String, String> map = new HashMap<>();
        map.put("userid", "1933");
        map.put("showtype", type);

        RequestTools.getInstance().getRequest("/api/ProductIndex", true, map, "HomeData", new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        activity.closeProgressDialog();
                    }
                });
            }

            @Override
            public void onResponse(final String response, int id) {

                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if (type.equals("new")){
                            activity.closeProgressDialog();
                        }
                        try {

                            JSONObject object = new JSONObject(response);
                            boolean isreust = object.getBoolean("result");
                            if(isreust){
                                String string = object.getJSONArray("data").toString();
                               List<CommodityBean> commlist =  HandleHomeRequest.handleHomeCommodityResponse(string);
                                list.add(commlist);
                                if(type.equals("hot")){
                                    requestHomeData("new");
                                }
                                if(type.equals("new")){
                                    HomeGroupAdapter adapter = (HomeGroupAdapter)homeRecycler.getAdapter();
                                    adapter.reloadAdapter(list);
                                }

                            }else {
                                Toast.makeText(getActivity(),"请求失败",Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
