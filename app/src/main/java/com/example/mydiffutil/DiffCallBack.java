package com.example.mydiffutil;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;


public class DiffCallBack extends DiffUtil.Callback {
    private List<TestBean> mOldDatas, mNewDatas;

    public DiffCallBack(List<TestBean> mOldDatas, List<TestBean> mNewDatas) {
        this.mOldDatas = mOldDatas;
        this.mNewDatas = mNewDatas;
    }

    @Override
    public int getOldListSize() {
        return mOldDatas != null ? mOldDatas.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return mNewDatas != null ? mNewDatas.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
//        Log.e("zzz","areItemsTheSame--->"+(mOldDatas.get(oldItemPosition).getName().equals(mNewDatas.get(newItemPosition).getName())));
        return mOldDatas.get(oldItemPosition).getName().equals(mNewDatas.get(newItemPosition).getName());
    }


    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

        TestBean beanOld = mOldDatas.get(oldItemPosition);
        TestBean beanNew = mNewDatas.get(newItemPosition);
//        Log.e("zzz","areContentsTheSame-getDesc--->"+(beanOld.getDesc().equals(beanNew.getDesc())));
//        Log.e("zzz","areContentsTheSame-getPic--->"+(beanOld.getPic()==(beanNew.getPic())));
        if (!beanOld.getDesc().equals(beanNew.getDesc())) {
            return false;
        }
        return true;
    }


    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        TestBean oldBean = mOldDatas.get(oldItemPosition);
        TestBean newBean = mNewDatas.get(newItemPosition);

        Bundle payload = new Bundle();
        if (!oldBean.getDesc().equals(newBean.getDesc())) {
            payload.putString("KEY_DESC", newBean.getDesc());
        }

        if (payload.size() == 0)
            return null;
        return payload;//
    }
}
