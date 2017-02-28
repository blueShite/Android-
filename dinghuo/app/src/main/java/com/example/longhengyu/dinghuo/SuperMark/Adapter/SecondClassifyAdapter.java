package com.example.longhengyu.dinghuo.SuperMark.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.longhengyu.dinghuo.R;
import com.example.longhengyu.dinghuo.SuperMark.Bean.SecondClassifyBean;
import com.example.longhengyu.dinghuo.SuperMark.Interface.MarkSecondClassify;

import java.util.List;

/**
 * Created by longhengyu on 2017/2/13.
 */
public class SecondClassifyAdapter extends RecyclerView.Adapter<SecondClassifyAdapter.ViewHoder> {

    private List<SecondClassifyBean> mlist;

    public MarkSecondClassify markSecondClassify;

    public SecondClassifyAdapter(List<SecondClassifyBean> list){
        mlist = list;
    }

    public void reloadData(List<SecondClassifyBean> list){
        mlist = list;
        notifyDataSetChanged();
    }

    @Override
    public SecondClassifyAdapter.ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_marksecond,parent,false);
        ViewHoder viewHoder = new ViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(SecondClassifyAdapter.ViewHoder holder, final int position) {

        holder.nameText.setText(mlist.get(position).Name);
        holder.nameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markSecondClassify.onClickSecondClassify(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {

        TextView nameText;

        public ViewHoder(View itemView) {
            super(itemView);
            nameText = (TextView) itemView.findViewById(R.id.secondName);
        }
    }
}
