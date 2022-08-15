package com.tencent.wxcloudrun.controller;

import com.alibaba.fastjson.JSON;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.CounterRequest;
import com.tencent.wxcloudrun.dto.OrderRequest;
import com.tencent.wxcloudrun.model.TOrder;
import com.tencent.wxcloudrun.service.OrderService;
import com.tencent.wxcloudrun.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("order")
public class OrderController {

    final OrderService orderService;
    final WxPayService wxPayService;
    final Logger logger;

    public OrderController(@Autowired OrderService orderService,@Autowired WxPayService wxPayService) {
        this.orderService = orderService;
        this.wxPayService = wxPayService;
        this.logger = LoggerFactory.getLogger(OrderController.class);
    }

    final ThreadLocalRandom current = ThreadLocalRandom.current();

    /**
     * 获取当前计数
     * @return API response json
     */
    @GetMapping(value = "/list")
    ApiResponse list() {
        logger.info("OrderController list");
        return ApiResponse.ok(orderService.list());
    }


    /**
     * 更新计数，自增或者清零
     * @param request {@link CounterRequest}
     * @return API response json
     */
    @PostMapping(value = "/create")
    ApiResponse create(@RequestBody OrderRequest request) {


//        // 调用支付接口
//        WxPayMpOrderResult result = null;
//        try {
//            WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
//            orderRequest.setOutTradeNo(order.getOrderSn());
//            orderRequest.setTotalFee(order.getPrice().multiply(new BigDecimal(100)).intValue());
//            orderRequest.setSpbillCreateIp(IpUtil.getIpAddr(request));
//            orderRequest.setOpenid(wxuser.getOpenId());
//            result = wxPayService.createOrder(orderRequest);
//        } catch (WxPayException e) {
//            logger.info("WxPayException={}",e);
//        }


        logger.info("OrderController, create: {}", JSON.toJSONString(request));

        TOrder order = new TOrder();
        order.setOrderNum(generateOrderNum());
        order.setOrderName(request.getOrderName());
        order.setOrderMobile(request.getOrderMobile());
        final boolean saveResult = orderService.save(order);
        if(saveResult){
            return ApiResponse.ok(0);
        }else {
            return ApiResponse.error("处理错误，请联系管理员，管理员电话13888888888");
        }
    }

    private String generateOrderNum(){
        final String s = DateUtils.formatOrderDateTime(LocalDateTime.now());
        final int i = current.nextInt(100000, 999999);
        return s.concat(String.valueOf(i));
    }

}
