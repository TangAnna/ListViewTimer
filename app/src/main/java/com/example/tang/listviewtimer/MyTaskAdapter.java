package com.example.tang.listviewtimer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author TangAnna
 * @description:
 * @date :${DATA} 11:29
 */
public class MyTaskAdapter extends RecyclerView.Adapter<MyTaskAdapter.MyViewHolder> {

    private List<TaskModel> mData;
    //存放每一个holder的集合
    private List<MyTaskAdapter.MyViewHolder> mHolderList = new ArrayList<>();
    private Context mContext;

    public MyTaskAdapter(List<TaskModel> data, Context context) {
        mData = data;
        mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list, null, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.setDataPosition(position);

        //先判断集合中是否包含当前的ViewHolder
        if (!mHolderList.contains(myViewHolder)) {
            mHolderList.add(myViewHolder);//添加
        }
        //设置数据
        myViewHolder.mTextView.setText(mData.get(position).timer);

        myViewHolder.mTvSendTime.setText(formatDate(mData.get(position).sendTime));
    }

    /**
     * 格式化时间
     *
     * @param l
     * @return
     */
    public String formatDate(long l) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String format = sdf.format(l);
        return format;
    }

    /**
     * 只刷新item中的计时器数据
     */
    public void notifyTime() {
        for (int i = 0; i < mHolderList.size(); i++) {
            mHolderList.get(i).mTextView.setText(mData.get(mHolderList.get(i).position).timer);
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;
        private int position;
        TextView mTvSendTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_item_time);
            mTvSendTime = itemView.findViewById(R.id.tv_sendTime);
        }

        /**
         * 绑定position
         *
         * @param position
         */
        public void setDataPosition(int position) {
            this.position = position;
        }

    }
}
