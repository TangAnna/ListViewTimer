package com.example.tang.listviewtimer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TangAnna
 * @description: ListView中每一项item都有计时器
 * @date :${DATA} 19:40
 */
public class MyFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private List<TaskModel> mData;
    private MyTaskAdapter mAdapter;
    private MyThread myThread;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //优化刷新adapter的方法
                    mAdapter.notifyTime();
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        mData = new ArrayList<>();
        mAdapter = new MyTaskAdapter(mData, getContext());
        mRecyclerView.setAdapter(mAdapter);
        initData();

    }

    /**
     * 获取数据
     */
    private void initData() {
        for (int i = 0; i < 100; i++) {
            TaskModel taskModel = new TaskModel();
            mData.add(taskModel);
        }
        myThread = new MyThread(mData);
        new Thread(myThread).start();
        mAdapter.notifyDataSetChanged();
    }


    class MyThread implements Runnable {
        private List<TaskModel> mModelList;
        public boolean endThread;

        public MyThread(List<TaskModel> modelList) {
            mModelList = modelList;
        }

        @Override
        public void run() {
            try {
                while (!endThread) {
                    Thread.sleep(1000);
                    //当前时间 单位毫秒
                    long currentTime = System.currentTimeMillis();
                    for (int i = 0; i < mModelList.size(); i++) {
                        //计算出每个任务的发送时间与当前时间的时间差值
                        mData.get(i).countTime = currentTime - mData.get(i).sendTime;
                        Log.d("TAG", "initData: 当前时间==" + currentTime);
                        Log.d("TAG", "initData: 时间差==" + mData.get(i).countTime);
                        //时间转换
                        long counttime = mModelList.get(i).countTime;
                        long hours = (counttime) / (1000 * 60 * 60);
                        long minutes = (counttime - hours * (1000 * 60 * 60)) / (1000 * 60);
                        long second = (counttime / 1000 - hours * 60 * 60 - minutes * 60);
                        Log.d("TAG", "run: 转换时间" + "hours==" + hours);
                        Log.d("TAG", "run: 转换时间" + "minutes==" + minutes);
                        Log.d("TAG", "run: 转换时间" + "second==" + second);
                        //并保存在商品time这个属性内
                        String hoursStr = hours + "";
                        String minutesStr = minutes + "";
                        String secondStr = second + "";
                        if (hours < 10) {
                            hoursStr = "0" + hoursStr;
                        }
                        if (minutes < 10) {
                            minutesStr = "0" + minutesStr;
                        }
                        if (second < 10) {
                            secondStr = "0" + secondStr;
                        }
                        String finaltime = hoursStr + ":" + minutesStr + ":" + secondStr;
                        mModelList.get(i).timer = finaltime;
                        Message message = new Message();
                        message.what = 1;
                        //发送信息给handler
                        mHandler.sendMessage(message);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myThread.endThread = true;
    }

    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }
}
