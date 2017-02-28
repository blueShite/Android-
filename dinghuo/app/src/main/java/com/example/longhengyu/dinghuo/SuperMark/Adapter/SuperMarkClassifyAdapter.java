package com.example.longhengyu.dinghuo.SuperMark.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.longhengyu.dinghuo.R;
import com.example.longhengyu.dinghuo.SuperMark.Bean.FristClassifyBean;
import com.example.longhengyu.dinghuo.SuperMark.Interface.MarkFirstClassify;

import java.util.List;

/**
 * Created by longhengyu on 2016/12/31.
 */
public class SuperMarkClassifyAdapter extends RecyclerView.Adapter<SuperMarkClassifyAdapter.ViewHolder> {

    private List<FristClassifyBean> mlist;

    public MarkFirstClassify firstInterface;

    public SuperMarkClassifyAdapter(List<FristClassifyBean> list){
        mlist = list;
    }

    public void reloadAdapter(List<FristClassifyBean> list){
        mlist = list;
        notifyDataSetChanged();
    }

    @Override
    public SuperMarkClassifyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_markclassify,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SuperMarkClassifyAdapter.ViewHolder holder, final int position) {

        holder.nameText.setText(mlist.get(position).Name);
        if(mlist.get(position).isSelect){
            holder.selectView.setVisibility(View.VISIBLE);
        }else {
            holder.selectView.setVisibility(View.INVISIBLE);
        }
        holder.selfView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstInterface.onClickFirstClassify(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameText;
        View selfView;
        View selectView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameText = (TextView) itemView.findViewById(R.id.superMarkClassify_name);
            selfView = itemView;
            selectView = itemView.findViewById(R.id.superMarkClassify_selectView);
        }
    }
}
