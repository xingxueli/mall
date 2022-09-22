package com.tencent.wxcloudrun.task;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.DelayQueue;

@Service
@Slf4j
@Getter
public class DelayService {


    private final DelayQueue<DelayTask> delayQueue = new DelayQueue<>();


    public void put(DelayTask task) {
        delayQueue.put(task);
    }

    public boolean remove(DelayTask task) {
        return delayQueue.remove(task);
    }


    public boolean remove(String taskName) {
        DelayTask[] array = delayQueue.toArray(new DelayTask[]{});
        if (array.length > 0) {
            for (DelayTask task : array) {
                if (task.getTaskName().equals(taskName)) {
                    return remove(task);
                }
            }
        }
        return true;

    }
}
