package com.tencent.wxcloudrun.controller;

import com.alibaba.fastjson.JSON;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.google.common.base.Preconditions;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.CounterRequest;
import com.tencent.wxcloudrun.dto.OrderRequest;
import com.tencent.wxcloudrun.dto.OrderResponse;
import com.tencent.wxcloudrun.dto.OrderStatusCount;
import com.tencent.wxcloudrun.model.TOrder;
import com.tencent.wxcloudrun.service.OrderService;
import com.tencent.wxcloudrun.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("order")
public class OrderController {

    final OrderService orderService;
    final Logger logger;

    public OrderController(@Autowired OrderService orderService,@Autowired WxPayService wxPayService) {
        this.orderService = orderService;
        this.logger = LoggerFactory.getLogger(OrderController.class);
    }

    @GetMapping(value = "/list")
    ApiResponse list(@RequestBody OrderRequest orderRequest) {
        logger.info("OrderController list");

        final OrderResponse orderResponse = orderService.getOrderList(orderRequest);
        return ApiResponse.ok(orderResponse);
    }

    @GetMapping(value = "/orderStatus/count")
    ApiResponse orderStatusCount(@RequestBody OrderRequest orderRequest) {

        final List<OrderStatusCount> orderStatusCounts = orderService.orderStatusCount(orderRequest);
        return ApiResponse.ok(orderStatusCounts);
    }

    @PostMapping(value = "/create")
    ApiResponse create(@RequestBody OrderRequest request) {

        final boolean saveResult = orderService.create(request);
        if(saveResult){
            return ApiResponse.ok(0);
        }else {
            return ApiResponse.error("处理错误，请联系管理员，管理员电话13888888888");
        }
    }


    @PostMapping(value = "/cancel")
    ApiResponse cancel(@RequestBody OrderRequest request) {
        orderService.cancel(request);
        return ApiResponse.ok();
    }
}
