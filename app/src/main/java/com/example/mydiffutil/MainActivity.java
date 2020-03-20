package com.example.mydiffutil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<TestBean> mDatas;
    private RecyclerView mRv;
    private DiffAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        mRv = (RecyclerView) findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DiffAdapter(this, mDatas);
        mRv.setAdapter(mAdapter);
    }

    private void initData() {
        mDatas = new ArrayList<>();
        mDatas.add(new TestBean("dh1", "test111"));
        mDatas.add(new TestBean("dh2", "test222"));
        mDatas.add(new TestBean("dh3", "test333"));
        mDatas.add(new TestBean("dh4", "test444"));
        mDatas.add(new TestBean("dh5", "test555"));
    }

    public void onRefresh(View view) {
        try {
            mNewDatas = new ArrayList<>();
            for (TestBean bean : mDatas) {
                mNewDatas.add(bean.clone());
            }
            mNewDatas.add(new TestBean("aaa", "bbb"));
            mNewDatas.get(0).setDesc("Android+");
            TestBean testBean = mNewDatas.get(1);
            mNewDatas.remove(testBean);
            mNewDatas.add(testBean);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(mDatas, mNewDatas), true);
                    Message message = mHandler.obtainMessage(H_CODE_UPDATE);
                    message.obj = diffResult;
                    message.sendToTarget();
                }
            }).start();

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    private static final int H_CODE_UPDATE = 1;
    private List<TestBean> mNewDatas;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case H_CODE_UPDATE:
                    DiffUtil.DiffResult diffResult = (DiffUtil.DiffResult) msg.obj;
                    diffResult.dispatchUpdatesTo(mAdapter);
                    mDatas = mNewDatas;
                    mAdapter.setDatas(mDatas);
                    break;
            }
        }
    };

}
