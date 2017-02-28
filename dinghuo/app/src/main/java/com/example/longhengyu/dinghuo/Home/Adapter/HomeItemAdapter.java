package com.example.longhengyu.dinghuo.Home.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.longhengyu.dinghuo.Home.Bean.CommodityBean;
import com.example.longhengyu.dinghuo.Home.Interface.HomeItemInterface;
import com.example.longhengyu.dinghuo.R;
import com.example.longhengyu.dinghuo.Tools.MyApplication;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by longhengyu on 2016/12/30.
 */
public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.ViewHolder> {

    private List<CommodityBean> mlist;

    private HomeItemInterface mhomeItemInterface;

    public HomeItemAdapter(List<CommodityBean> list,HomeItemInterface homeItemInterface){
        mlist = list;
        mhomeItemInterface = homeItemInterface;
    }

    public void reloadAdapter(List<CommodityBean> list){
        mlist = list;
        notifyDataSetChanged();
    }

    @Override
    public HomeItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_homeitem,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HomeItemAdapter.ViewHolder holder, final int position) {

        CommodityBean bean = mlist.get(position);
        if(!bean.Image450.isEmpty()){
            Picasso.with(MyApplication.getContext())
                    .load(bean.Image450)
                    .placeholder(R.mipmap.notimageurl)
                    .into(holder.commodityImage);
        }
        holder.nameText.setText(bean.ProductName);
        holder.priceText.setText("价格:"+bean.VipPriceByBox);
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mhomeItemInterface.onClickHomeAddBtn(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mlist==null){
            return 0;
        }
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView commodityImage;
        TextView nameText;
        TextView priceText;
        Button addBtn;

        public ViewHolder(View itemView) {

            super(itemView);
            commodityImage = (ImageView)itemView.findViewById(R.id.home_CommodityImage);
            nameText = (TextView)itemView.findViewById(R.id.home_commodityName);
            priceText = (TextView)itemView.findViewById(R.id.home_commodityPrice);
            addBtn = (Button)itemView.findViewById(R.id.home_commodityAddBtn);
        }
    }
}
