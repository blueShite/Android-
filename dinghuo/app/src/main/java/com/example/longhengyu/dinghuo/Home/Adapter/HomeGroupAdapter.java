package com.example.longhengyu.dinghuo.Home.Adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.longhengyu.dinghuo.Home.Bean.CommodityBean;
import com.example.longhengyu.dinghuo.Home.Interface.HomeGroupInterface;
import com.example.longhengyu.dinghuo.Home.Interface.HomeItemInterface;
import com.example.longhengyu.dinghuo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longhengyu on 2016/12/30.
 */
public class HomeGroupAdapter extends RecyclerView.Adapter<HomeGroupAdapter.ViewHolder> {

    private List<List<CommodityBean>>mlist;
    private HomeGroupInterface mhomeGroupInterface;

    public HomeGroupAdapter(List<List<CommodityBean>>list,HomeGroupInterface homeGroupInterface){
        mlist = list;
        mhomeGroupInterface = homeGroupInterface;
    }

    public void reloadAdapter(List<List<CommodityBean>>list){
        mlist = list;
        notifyDataSetChanged();
    }

    @Override
    public HomeGroupAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_home,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HomeGroupAdapter.ViewHolder holder, final int position) {

        String[] str = {"热销产品","新品上架"};
        holder.groupName.setText(str[position]);
        HomeItemAdapter adapter = (HomeItemAdapter) holder.recyclerView.getAdapter();
        if(adapter==null){
            List<CommodityBean>list = new ArrayList<>();
            adapter = new HomeItemAdapter(list, new HomeItemInterface() {
                @Override
                public void onClickHomeAddBtn(int point) {
                    mhomeGroupInterface.onClickItem(position,point);
                }
            });
            holder.recyclerView.setAdapter(adapter);
        }
        adapter.reloadAdapter(mlist.get(position));
    }

    @Override
    public int getItemCount() {
        if (mlist==null){
            return 0;
        }
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView groupName;
        RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            groupName = (TextView)itemView.findViewById(R.id.home_groupText);
            recyclerView = (RecyclerView)itemView.findViewById(R.id.home_groupRecycle);
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
        }
    }
}
