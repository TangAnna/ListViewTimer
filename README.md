# ListViewTimer
列表中每一个item都有计时器 （可实现倒计时或者计时器的功能）

项目中有时会遇到列表中含有倒计时或者是计时器的需求，实现的方式有很多种，此Demo中是使用Thread + Handler实时的对item的局部进行
刷新达到计时或倒计时的功能

本项目中实现的是计时器功能（当前时间距离过去的一个时间的时间差值）

实现步骤：

1.创建数据模型TaskModel

        public class TaskModel {

            public String timer; //计时器显示的结果  10:31:30(时分秒的格式)

            public long sendTime = 1547516639000L;//目标时间

            public long countTime;//long型的时间差值

        }

2.获取服务器端接口中得到的目标时间（最好是long数据类型方便加减操作）准备好数据；

3.创建Thread,主要是计算当前时间和目标时间的差值，也就是countTime字段的值；

获取当前时间方法：long currentTime = System.currentTimeMillis();

时间差值：countTime = currentTime - sendTime;

页面显示的时间差值：
```
long hours = (counttime) / (1000 * 60 * 60);

                          long minutes = (counttime - hours * (1000 * 60 * 60)) / (1000 * 60);

                          long second = (counttime / 1000 - hours * 60 * 60 - minutes * 60);

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
                          timer = hoursStr + ":" + minutesStr + ":" + secondStr;
```

4.准备好数据，我们就要创建适配器，适配器和普通的适配器一样，只不过要创建一个ViewHolder的集合mHolderList，在onBindViewHolder方法中将每一个ViewHolder
add进mHolderList中 <br>
//先判断集合中是否包含当前的ViewHolder
if (!mHolderList.contains(myViewHolder)) {
     mHolderList.add(myViewHolder);//添加
}

然后提供一个方法更新item中时间差<br>
    /**
     * 只刷新item中的计时器数据
     */
    public void notifyTime() {
        for (int i = 0; i < mHolderList.size(); i++) {
            mHolderList.get(i).mTextView.setText(mData.get(mHolderList.get(i).position).timer);
        }
    }

5.创建Handler，每次收到新的消息就调用Adapter中的notifyTime方法；
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

6.在之前创建好的Thread中获取完时间差值之后创建message并发送给Handler

        Message message = new Message();
        message.what = 1;
        //发送信息给handler
        mHandler.sendMessage(message);

注：Thread中要有一个停止发送消息的标记值endThread，随着当前页面的生命周期停止Thread

        @Override
            public void onDestroy() {
                super.onDestroy();
                myThread.endThread = true;
            }

具体的详细实现看Demo中，每一步都有注释可以看懂;









