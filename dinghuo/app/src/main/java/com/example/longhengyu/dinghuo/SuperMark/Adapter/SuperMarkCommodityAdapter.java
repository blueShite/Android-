package com.example.longhengyu.dinghuo.SuperMark.Adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.longhengyu.dinghuo.Home.Bean.CommodityBean;
import com.example.longhengyu.dinghuo.R;
import com.example.longhengyu.dinghuo.SuperMark.Bean.SecondClassifyBean;
import com.example.longhengyu.dinghuo.SuperMark.Interface.MarkSecondClassify;
import com.example.longhengyu.dinghuo.Tools.MyApplication;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longhengyu on 2016/12/31.
 */
public class SuperMarkCommodityAdapter extends RecyclerView.Adapter<SuperMarkCommodityAdapter.ViewHolder> {

    private List<CommodityBean> mlist;

    public List<SecondClassifyBean> mSecondList;

    public MarkSecondClassify markSecondClassify;

    private View mheaderView;

    public SuperMarkCommodityAdapter(List<CommodityBean> list){
        mlist = list;
    }

    public void reloadAdapter(List<CommodityBean> list,List<SecondClassifyBean> secondList){
        mlist = list;
        mSecondList = secondList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {

        if(position==0){
                return 0;
        }
        return 1;
    }

    @Override
    public SuperMarkCommodityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if(viewType==0){
                mheaderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_supermark,parent,false);
                ViewHolder viewHolder = new ViewHolder(mheaderView);
                return viewHolder;
            }
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_markcommodity,parent,false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
    }

    @Override
    public void onBindViewHolder(SuperMarkCommodityAdapter.ViewHolder holder, int position) {

        CommodityBean bean = mlist.get(position);

        if(position==0){

            if(mSecondList==null){
                mSecondList = new ArrayList<>();
            }
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
            holder.headerRecycler.setLayoutManager(layoutManager);
            SecondClassifyAdapter secondClassifyAdapter = new SecondClassifyAdapter(mSecondList);
            secondClassifyAdapter.markSecondClassify = new MarkSecondClassify() {
                @Override
                public void onClickSecondClassify(int index) {
                    markSecondClassify.onClickSecondClassify(index);
                }
            };
            holder.headerRecycler.setAdapter(secondClassifyAdapter);
            return;
        }

        holder.commodityName.setText(bean.ProductName);
        if(!bean.Image450.isEmpty()){
            Picasso.with(MyApplication.getContext())
                    .load(bean.Image450)
                    .placeholder(R.mipmap.notimageurl)
                    .into(holder.commodityImage);
        }
        holder.commodityPrice.setText("价格:"+bean.VipPriceByBox);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{

        ImageView commodityImage;
        TextView commodityName;
        TextView commodityPrice;
        Button addBtn;
        View selfView;

        ImageView commodityImageHeader;
        TextView commodityNameHeader;
        TextView commodityPriceHeader;
        Button addBtnHeader;
        RecyclerView headerRecycler;

        public ViewHolder(View itemView) {

            super(itemView);
            if(mheaderView!=null&&itemView==mheaderView){
                headerRecycler = (RecyclerView) itemView.findViewById(R.id.superMark_headerList);
                return;

            }
            selfView = itemView;
            commodityImage = (ImageView)itemView.findViewById(R.id.markCommodityImage);
            commodityName = (TextView) itemView.findViewById(R.id.markCommodityName);
            commodityPrice = (TextView)itemView.findViewById(R.id.markCommodityPrice);
            addBtn = (Button)itemView.findViewById(R.id.markCommodityAdd);
        }
    }
}
