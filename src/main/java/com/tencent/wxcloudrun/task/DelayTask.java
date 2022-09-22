package com.tencent.wxcloudrun.task;

import lombok.Data;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@Data
public class DelayTask implements Delayed {

    private  String taskName;//任务名称  orderNum

    private long expire ;//到期时间

    public DelayTask(String taskName, long secondsDelay) {
        super();
        this.taskName = taskName;
        long now = System.currentTimeMillis();
        this.expire =  now+ (secondsDelay*1000);
    }

    //用于队列中排序过期时间
    @Override
    public int compareTo(Delayed o) {
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) -o.getDelay(TimeUnit.MILLISECONDS));
    }
    //用于获取过期时间
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.expire - System.currentTimeMillis() , TimeUnit.MILLISECONDS);
    }
}
