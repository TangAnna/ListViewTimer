# ListViewTimer
列表中每一个item都有计时器 （可实现倒计时或者计时器的功能）

项目中有时会遇到列表中含有倒计时或者是计时器的需求，实现的方式有很多种，此Demo中是使用Thread + Handler实时的对item的局部进行
刷新达到计时或倒计时的功能

本项目中实现的是计时器功能（当前时间距离过去的一个时间的时间差值）

实现步骤：
1.创建数据模型TaskModel

        public class TaskModel {

            public String timer; //计时器显示的结果  10:31

            public long sendTime = 1547516639000L;//目标时间

            public long countTime;//long型的时间差值

        }

1.获取服务器端接口中得到的目标时间（最好是long数据类型方便加减操作）准备好数据；

2.创建Thread,主要是计算当前时间和目标时间的差值，





