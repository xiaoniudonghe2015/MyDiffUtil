package com.example.mydiffutil;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DiffAdapter extends RecyclerView.Adapter<DiffAdapter.DiffVH> {
    private List<TestBean> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;

    public DiffAdapter(Context mContext, List<TestBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setDatas(List<TestBean> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public DiffVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DiffVH(mInflater.inflate(R.layout.item_diff, parent, false));
    }

    @Override
    public void onBindViewHolder(final DiffVH holder, final int position) {
        TestBean bean = mDatas.get(position);
        holder.tv1.setText(bean.getName());
        holder.tv2.setText(bean.getDesc());
    }

    @Override
    public void onBindViewHolder(DiffVH holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            Bundle payload = (Bundle) payloads.get(0);
            TestBean bean = mDatas.get(position);
            for (String key : payload.keySet()) {
                switch (key) {
                    case "KEY_DESC":
                        holder.tv2.setText(bean.getDesc());
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    class DiffVH extends RecyclerView.ViewHolder {
        TextView tv1, tv2;

        public DiffVH(View itemView) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.tv1);
            tv2 = (TextView) itemView.findViewById(R.id.tv2);
        }
    }
}
