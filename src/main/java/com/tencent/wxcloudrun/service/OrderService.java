package com.tencent.wxcloudrun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tencent.wxcloudrun.dto.OrderRequest;
import com.tencent.wxcloudrun.dto.OrderResponse;
import com.tencent.wxcloudrun.dto.OrderStatusCount;
import com.tencent.wxcloudrun.dto.WebOrderResponse;
import com.tencent.wxcloudrun.model.TOrder;

import java.util.List;

public interface OrderService extends IService<TOrder>{

    OrderResponse  getOrderList(OrderRequest orderRequest);
    List<OrderStatusCount> orderStatusCount(Integer orderStatus);
    void cancel(OrderRequest orderRequest);
    boolean create(OrderRequest orderRequest);
    WebOrderResponse getWebOrderList(OrderRequest orderRequest);
    boolean webCreate(OrderRequest orderRequest);
    boolean webUpdate(OrderRequest orderRequest);
}
