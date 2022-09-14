package com.tencent.wxcloudrun.controller;

import com.alibaba.fastjson.JSON;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.google.common.base.Preconditions;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.*;
import com.tencent.wxcloudrun.model.TOrder;
import com.tencent.wxcloudrun.service.OrderService;
import com.tencent.wxcloudrun.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @PostMapping(value = "/list")
    ApiResponse list(@RequestBody OrderRequest orderRequest) {
        logger.info("OrderController list");

        final OrderResponse orderResponse = orderService.getOrderList(orderRequest);
        return ApiResponse.ok(orderResponse);
    }

    @PostMapping(value = "/webList")
    ApiResponse webList(@RequestBody OrderRequest orderRequest) {
        logger.info("OrderController webList");

        final WebOrderResponse webOrderResponse = orderService.getWebOrderList(orderRequest);
        return ApiResponse.ok(webOrderResponse);
    }

    @GetMapping(value = "/orderStatus/count")
    ApiResponse orderStatusCount(@RequestParam(required = false) Integer orderStatus) {
        final List<OrderStatusCount> orderStatusCounts = orderService.orderStatusCount(orderStatus);
        return ApiResponse.ok(orderStatusCounts);
    }

    @PostMapping(value = "/create")
    ApiResponse create(@RequestBody AppletOrderRequest appletOrderRequest, HttpServletRequest request) {

        final List<AppletOrderDetail> goodsRequestList = appletOrderRequest.getGoodsRequestList();
        final List<StoreInfo> storeInfoList = appletOrderRequest.getStoreInfoList();
        final AppletOrderDetail appletOrderDetail = goodsRequestList.get(0);
        final StoreInfo storeInfo = storeInfoList.get(0);
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderName(appletOrderDetail.getOrderName());
        orderRequest.setOrderMobile(appletOrderDetail.getOrderMobile());
        orderRequest.setPredictStartTime(appletOrderDetail.getStartDate());
        orderRequest.setPredictEndTime(appletOrderDetail.getEndDate());
        orderRequest.setGuestRoomId(appletOrderDetail.getGuestRoomId());
        orderRequest.setRemark(storeInfo.getRemark());
        orderRequest.setPayType(appletOrderRequest.getPayType());
        orderRequest.setPayWay(appletOrderRequest.getPayWay());
        orderRequest.setTotalAmount(appletOrderRequest.getTotalAmount());
        orderRequest.setQuantity(appletOrderDetail.getQuantity());
        orderRequest.setOrderNum(appletOrderRequest.getOrderNum());

        Preconditions.checkNotNull(orderRequest.getOrderName());
        Preconditions.checkNotNull(orderRequest.getOrderMobile());
        Preconditions.checkNotNull(orderRequest.getPredictStartTime());
        Preconditions.checkNotNull(orderRequest.getPredictEndTime());
        Preconditions.checkNotNull(orderRequest.getGuestRoomId());
        Preconditions.checkNotNull(orderRequest.getRemark());
        Preconditions.checkNotNull(orderRequest.getTotalAmount());
        Preconditions.checkNotNull(orderRequest.getPayType());
        Preconditions.checkNotNull(orderRequest.getPayWay());
        Preconditions.checkNotNull(orderRequest.getQuantity());

        final AppletOrderResponse appletOrderResponse = orderService.create(orderRequest,request);
        return ApiResponse.ok(appletOrderResponse);
    }

    @PostMapping(value = "/webCreate")
    ApiResponse webCreate(@RequestBody OrderRequest orderRequest) {

        Preconditions.checkNotNull(orderRequest.getOrderName());
        Preconditions.checkNotNull(orderRequest.getOrderMobile());
        Preconditions.checkNotNull(orderRequest.getPredictStartTime());
        Preconditions.checkNotNull(orderRequest.getPredictEndTime());
        Preconditions.checkNotNull(orderRequest.getGuestRoomId());
        Preconditions.checkNotNull(orderRequest.getRemark());
        Preconditions.checkNotNull(orderRequest.getTotalAmount());
        Preconditions.checkNotNull(orderRequest.getPayWay());

        final boolean saveResult = orderService.webCreate(orderRequest);
        if(saveResult){
            return ApiResponse.ok(0);
        }else {
            return ApiResponse.error("处理错误，请联系管理员，管理员电话13888888888");
        }
    }

    @PostMapping(value = "/webUpdate")
    ApiResponse webUpdate(@RequestBody OrderRequest orderRequest) {

        Preconditions.checkNotNull(orderRequest.getOrderId());

        Preconditions.checkNotNull(orderRequest.getOrderName());
        Preconditions.checkNotNull(orderRequest.getOrderMobile());
        Preconditions.checkNotNull(orderRequest.getPredictStartTime());
        Preconditions.checkNotNull(orderRequest.getPredictEndTime());
        Preconditions.checkNotNull(orderRequest.getGuestRoomId());
        Preconditions.checkNotNull(orderRequest.getRemark());
        Preconditions.checkNotNull(orderRequest.getTotalAmount());
        Preconditions.checkNotNull(orderRequest.getPayWay());

        final boolean saveResult = orderService.webUpdate(orderRequest);
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

    @GetMapping(value = "/detail")
    ApiResponse detail(@RequestParam String orderNum) {
        Preconditions.checkNotNull(orderNum);
        return ApiResponse.ok(orderService.detail(orderNum));
    }
}
