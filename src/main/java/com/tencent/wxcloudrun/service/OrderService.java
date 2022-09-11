package com.tencent.wxcloudrun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tencent.wxcloudrun.dto.*;
import com.tencent.wxcloudrun.model.TOrder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService extends IService<TOrder>{

    OrderResponse  getOrderList(OrderRequest orderRequest);
    List<OrderStatusCount> orderStatusCount(Integer orderStatus);
    void cancel(OrderRequest orderRequest);
    AppletOrderResponse create(OrderRequest orderRequest, HttpServletRequest request);
    WebOrderResponse getWebOrderList(OrderRequest orderRequest);
    boolean webCreate(OrderRequest orderRequest);
    boolean webUpdate(OrderRequest orderRequest);
}
