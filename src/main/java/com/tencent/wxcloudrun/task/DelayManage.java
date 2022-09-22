package com.tencent.wxcloudrun.task;

import com.tencent.wxcloudrun.enums.OrderStatusEnum;
import com.tencent.wxcloudrun.model.TOrder;
import com.tencent.wxcloudrun.service.OrderService;
import com.tencent.wxcloudrun.utils.ConstantUtil;
import com.tencent.wxcloudrun.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class DelayManage {

    @Resource
    private DelayService delayService;

    @Resource
    private OrderService orderService;

    /**
     * 服务器启动时，自动加载待支付订单
     */
    @PostConstruct
    public void initOrderStatus() {
        final List<TOrder> orderTaskList = orderService.getOrderTaskList(OrderStatusEnum.TO_BE_PAID.getCode());
        if(CollectionUtils.isNotEmpty(orderTaskList)){
            for (TOrder tOrder : orderTaskList) {
                LocalDateTime localDateTime = DateUtils.toLocalDateTime(tOrder.getCreateTime());
                localDateTime.plusMinutes(ConstantUtil.THIRTY_MINUTES);
                if(LocalDateTime.now().isAfter(localDateTime)){
                    //直接更新状态
                    orderService.autoCancelOrder(tOrder.getOrderNum());
                }else{
                    //计算剩余时间并放到队列中
                    final Duration between = Duration.between(LocalDateTime.now(), localDateTime);
                    DelayTask delayTask = new DelayTask(tOrder.getOrderNum(),between.toSeconds());
                    delayService.put(delayTask);
                }
            }
        }
        DelayQueue<DelayTask> delayQueue = delayService.getDelayQueue();
        //启动一个线程持续健康订单超时
        Executors.newFixedThreadPool(2).execute(() -> {
            try {
                while (true) {
                    if (delayQueue.size() > 0) {
                        //容器中超时的订单记录会被取出
                        DelayTask delayTask = delayQueue.take();
                        //执行你的操作,如删除订单等
                        runTask(delayTask);
                    }
                }
            } catch (InterruptedException e) {
                log.error("InterruptedException error:", e);
            }
        });
    }

    private void runTask(DelayTask delayTask){
        orderService.autoCancelOrder(delayTask.getTaskName());
    }
}
