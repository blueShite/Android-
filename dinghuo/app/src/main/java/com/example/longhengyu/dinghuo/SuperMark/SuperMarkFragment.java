package com.example.longhengyu.dinghuo.SuperMark;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.example.longhengyu.dinghuo.Home.Bean.CommodityBean;
import com.example.longhengyu.dinghuo.Manage.LoginManage;
import com.example.longhengyu.dinghuo.NetWork.RequestTools;
import com.example.longhengyu.dinghuo.R;
import com.example.longhengyu.dinghuo.SuperMark.Adapter.SecondClassifyAdapter;
import com.example.longhengyu.dinghuo.SuperMark.Adapter.SuperMarkClassifyAdapter;
import com.example.longhengyu.dinghuo.SuperMark.Adapter.SuperMarkCommodityAdapter;
import com.example.longhengyu.dinghuo.SuperMark.Bean.FristClassifyBean;
import com.example.longhengyu.dinghuo.SuperMark.Bean.SecondClassifyBean;
import com.example.longhengyu.dinghuo.SuperMark.Interface.MarkFirstClassify;
import com.example.longhengyu.dinghuo.SuperMark.Interface.MarkSecondClassify;
import com.example.longhengyu.dinghuo.Tools.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuperMarkFragment extends Fragment {

    private View view;
    //分类列表
    private RecyclerView classifyRecycler;
    //商品列表
    private RecyclerView commodityRecycler;
    //分类列表数据源
    private List<FristClassifyBean> firstlist;
    //商品列表数据源
    private List<CommodityBean> commodityList;
    //二级列表数据
    private List<SecondClassifyBean> secondList;

    public SuperMarkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_super_mark, container, false);
        initClassifyList();
        initCommodityList();
        requestFirstClassifyData();
        return view;
    }

    //初始化视图
    private void initClassifyList() {

        firstlist = new ArrayList<>();
        classifyRecycler = (RecyclerView) view.findViewById(R.id.superMark_classifyRecycler);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        classifyRecycler.setLayoutManager(manager);
        final SuperMarkClassifyAdapter classifyAdapter = new SuperMarkClassifyAdapter(firstlist);
        classifyRecycler.setAdapter(classifyAdapter);
        classifyAdapter.firstInterface = new MarkFirstClassify() {
            @Override
            public void onClickFirstClassify(int i) {

                for (int j = 0; j < firstlist.size(); j++) {
                    FristClassifyBean bean = firstlist.get(j);
                    if (j == i) {
                        bean.isSelect = true;
                    } else {
                        bean.isSelect = false;
                    }
                }
                classifyAdapter.reloadAdapter(firstlist);
                requestSecondData(firstlist.get(i).SequenceCode);
            }
        };
    }

    private void initCommodityList(){

        commodityList = new ArrayList<>();
        secondList = new ArrayList<>();
        commodityRecycler = (RecyclerView) view.findViewById(R.id.superMark_commodityRecycler);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        commodityRecycler.setLayoutManager(manager);
        SuperMarkCommodityAdapter commodityAdapter = new SuperMarkCommodityAdapter(commodityList);
        commodityAdapter.markSecondClassify = new MarkSecondClassify() {
            @Override
            public void onClickSecondClassify(int index) {
                requestCommodityData(secondList.get(index).SequenceCode);
            }
        };
        commodityRecycler.setAdapter(commodityAdapter);
    }

    private void requestFirstClassifyData() {

        final BaseActivity activity = (BaseActivity) getActivity();
        activity.showProgressDialog();
        RequestTools.getInstance().getRequest("/api/ProductBigCategory", true, null, "markFristClassify", new
                StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                activity.closeProgressDialog();
                                Toast.makeText(getActivity(), "分类加载失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(final String response, int id) {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                try {

                                    JSONObject object = new JSONObject(response);
                                    boolean result = object.getBoolean("result");
                                    String message = object.getString("message");
                                    if (result) {

                                        Gson gson = new Gson();
                                        JSONArray array = object.getJSONArray("data");
                                        for (int i = 0; i < array.length(); i++) {
                                            String beanStr = array.get(i).toString();
                                            FristClassifyBean bean = gson.fromJson(beanStr, FristClassifyBean.class);
                                            if (i == 0) {
                                                bean.isSelect = true;
                                            } else {
                                                bean.isSelect = false;
                                            }
                                            firstlist.add(bean);

                                        }
                                        SuperMarkClassifyAdapter classifyAdapter = (SuperMarkClassifyAdapter)
                                                classifyRecycler.getAdapter();
                                        classifyAdapter.reloadAdapter(firstlist);
                                        requestSecondData(firstlist.get(0).SequenceCode);

                                    } else {
                                        activity.closeProgressDialog();
                                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {

                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });
    }

    private void requestSecondData(String id){

        Map<String,String> map = new HashMap<>();
        map.put("c",id);
        final BaseActivity activity = (BaseActivity) getActivity();
        RequestTools.getInstance().getRequest("/api/ProductChildCategory", true, map, "secondRequest", new StringCallback() {

            @Override
            public void onError(Call call, Exception e, int id) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.closeProgressDialog();
                        Toast.makeText(getActivity(), "分类加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(String response, int id) {


                try {
                    JSONObject object = new JSONObject(response);
                    boolean result = object.getBoolean("result");
                    final String message = object.getString("message");
                    if(result){

                        Gson gson = new Gson();
                        String arrayStr = object.getJSONArray("data").toString();
                        secondList = gson.fromJson(arrayStr,new TypeToken<List<SecondClassifyBean>>(){}.getType());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SuperMarkCommodityAdapter adapter = (SuperMarkCommodityAdapter) commodityRecycler.getAdapter();
                                adapter.mSecondList = secondList;
                                requestCommodityData(secondList.get(0).SequenceCode);
                            }
                        });

                    }else{
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                activity.closeProgressDialog();
                                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void requestCommodityData(String id){

        final BaseActivity activity = (BaseActivity) getActivity();
        if(!activity.isLoding){
            activity.showProgressDialog();
        }
        commodityList.clear();
        Map<String,String> map = new HashMap<>();
        map.put("c",id);
        map.put("page","1");
        map.put("pageSize","100");
        map.put("sortType","desc");
        map.put("k","");
        map.put("l","");
        map.put("b","");
        map.put("special","");
        map.put("sort","bySold");
        map.put("userid", LoginManage.getInstance().returnLoginBean().id);
        RequestTools.getInstance().getRequest("/Api/ProductSearch", true, map, "commodityRequest", new StringCallback() {
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
            public void onResponse(String response, int id) {

                try {

                    JSONObject object = new JSONObject(response);
                    boolean result = object.getBoolean("result");
                    final String message = object.getString("message");
                    if(result){
                        Gson gson = new Gson();
                        String arrayStr = object.getJSONArray("data").toString();
                        commodityList = gson.fromJson(arrayStr,new TypeToken<List<CommodityBean>>(){}.getType());
                        commodityList.add(0,new CommodityBean());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                               activity.closeProgressDialog();
                                SuperMarkCommodityAdapter adapter = (SuperMarkCommodityAdapter)commodityRecycler.getAdapter();
                                adapter.reloadAdapter(commodityList,secondList);
                            }
                        });

                    }else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                activity.closeProgressDialog();
                            Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}