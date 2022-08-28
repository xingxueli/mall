package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.binarywang.wxpay.service.WxPayService;
import com.tencent.wxcloudrun.dao.OrderMapper;
import com.tencent.wxcloudrun.dto.*;
import com.tencent.wxcloudrun.enums.OrderStatusEnum;
import com.tencent.wxcloudrun.model.GuestRoom;
import com.tencent.wxcloudrun.model.HotelRegister;
import com.tencent.wxcloudrun.model.TOrder;
import com.tencent.wxcloudrun.service.GuestRoomService;
import com.tencent.wxcloudrun.service.HotelRegisterService;
import com.tencent.wxcloudrun.service.OrderService;
import com.tencent.wxcloudrun.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, TOrder> implements OrderService {

    @Autowired
    HotelRegisterService hotelRegisterService;

    @Autowired
    GuestRoomService guestRoomService;

    final ThreadLocalRandom current = ThreadLocalRandom.current();

//    @Autowired
//    WxPayService wxPayService;

    public OrderResponse  getOrderList(OrderRequest orderRequest){
        OrderResponse orderResponse = new OrderResponse();

        QueryWrapper<TOrder> queueWrapper = new QueryWrapper<>();
        if(orderRequest.getOrderStatus() != null && orderRequest.getOrderStatus().intValue() != -1){
            queueWrapper.eq("order_status",orderRequest.getOrderStatus().intValue());
        }
        Page<TOrder> pageCondition = new Page<>(orderRequest.getPageNum(),orderRequest.getPageSize());
        final Page<TOrder> page = this.page(pageCondition, queueWrapper);
        orderResponse.setPageNum(orderRequest.getPageNum());
        orderResponse.setTotalCount(page.getTotal());
        orderResponse.setPageSize(orderRequest.getPageSize());

        final List<TOrder> records = page.getRecords();
        orderResponse.setOrders(transform(records));
        return orderResponse;
    }

    private List<OrderVo> transform(List<TOrder> records){
        return records.stream().map(r -> {
            OrderVo orderVo = new OrderVo();
            orderVo.setOrderId(String.valueOf(r.getId()));
            orderVo.setOrderNo(r.getOrderNum());
            orderVo.setOrderStatus(r.getOrderStatus());
            orderVo.setCancelReasonType(r.getCancelReasonType());
            orderVo.setCreateTime(String.valueOf(r.getCreateTime().getTime()));
            orderVo.setDiscountAmount(String.valueOf(r.getDiscountAmount()));
            orderVo.setPaymentAmount(String.valueOf(r.getPaymentAmount()));
            orderVo.setTotalAmount(String.valueOf(r.getTotalAmount()));
            orderVo.setOrderStatusName(OrderStatusEnum.getOrderStatusName(r.getOrderStatus()));
            orderVo.setOrderSatusRemark("");

            List<OrderItemVo> orderItemVos = new ArrayList<>();
            OrderItemVo orderItemVo = new OrderItemVo();
            final RoomAndHotelRegisterDto roomAndHotelRegisterDto = getRoomAndHotelRegister(r.getId());
            final GuestRoom guestRoom = roomAndHotelRegisterDto.getGuestRoom();
            final HotelRegister hotelRegister = roomAndHotelRegisterDto.getHotelRegister();
            orderItemVo.setActualPrice(guestRoom.getRoomPrice());
            orderItemVo.setOriginPrice(guestRoom.getRoomOriginPrice());
            orderItemVo.setId(String.valueOf(hotelRegister.getId()));
            orderItemVo.setSpuId(String.valueOf(guestRoom.getType()));
            orderItemVo.setSkuId(String.valueOf(guestRoom.getId()));
            orderItemVo.setGoodsPictureUrl(guestRoom.getImageUrl());
            orderItemVo.setGoodsName(guestRoom.getRoomName());
            orderItemVos.add(orderItemVo);
            orderVo.setOrderItemVOs(orderItemVos);

            PaymentVo paymentVo = new PaymentVo();
            paymentVo.setAmount(String.valueOf(r.getPaymentAmount()));
            if(r.getPayTime()!=null){
                paymentVo.setPaySuccessTime(r.getPayTime().getTime());
                paymentVo.setPayTime(r.getPayTime().getTime());
            }
            paymentVo.setPayType(r.getPayType());
            paymentVo.setPayWay(r.getPayWay());
            paymentVo.setPayWayName("微信支付");
            orderVo.setPaymentVO(paymentVo);
            return orderVo;
        }).collect(Collectors.toList());
    }

    private RoomAndHotelRegisterDto getRoomAndHotelRegister(Integer orderId){
        RoomAndHotelRegisterDto roomAndHotelRegisterDto = new RoomAndHotelRegisterDto();
        QueryWrapper<HotelRegister> queueWrapper = new QueryWrapper<>();
        queueWrapper.eq("order_id",orderId);
        final HotelRegister hotelRegister = hotelRegisterService.getOne(queueWrapper);
        roomAndHotelRegisterDto.setHotelRegister(hotelRegister);
        final GuestRoom guestRoom = guestRoomService.getById(hotelRegister.getGuestRoomId());
        roomAndHotelRegisterDto.setGuestRoom(guestRoom);
        return roomAndHotelRegisterDto;
    }

    public List<OrderStatusCount>  orderStatusCount(OrderRequest orderRequest){
        return this.getBaseMapper().orderStatusCount();
    }

    public void  cancel(OrderRequest orderRequest){
        final RoomAndHotelRegisterDto roomAndHotelRegister = getRoomAndHotelRegister(orderRequest.getId());
        GuestRoom guestRoom = new GuestRoom();
        guestRoom.setId(roomAndHotelRegister.getGuestRoom().getId());
        guestRoom.setRoomStatus(0);//如果取消，那么不管付钱还是没付钱，都把库存改为为占用
        guestRoomService.updateById(guestRoom);
        //添加更新取消原因,通过按钮点击的一定是手动取消
        TOrder tOrder = new TOrder();
        tOrder.setCancelType(2);
        tOrder.setId(orderRequest.getId());
        this.updateById(tOrder);
        //如果取消的时候，该人已付款，那么需要退款
        //todo

    }

    public boolean create(OrderRequest orderRequest){
        log.info("OrderServiceImpl, create: {}", JSON.toJSONString(orderRequest));

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

        TOrder order = new TOrder();
        order.setOrderNum(generateOrderNum());
        order.setOrderName(orderRequest.getOrderName());
        order.setOrderMobile(orderRequest.getOrderMobile());
        order.setStartTime(orderRequest.getStartTime());
        order.setEndTime(orderRequest.getEndTime());
        order.setRemark(orderRequest.getRemark());
        order.setTotalAmount(orderRequest.getTotalAmount());
        order.setDiscountAmount(0);
        order.setCreateTime(new Date());
        final boolean saveResult = this.save(order);
        return saveResult;
    }

    public void orderPay(){
        TOrder order = new TOrder();
//        order.setId();
//        order.setPaymentAmount();
//        order.setPayTime();
//        order.setOrderStatus(OrderStatusEnum.PAID_AND_CHECK_IN.getCode());
        this.updateById(order);
    }

    private String generateOrderNum(){
        final String s = DateUtils.formatOrderDateTime(LocalDateTime.now());
        final int i = current.nextInt(100000, 999999);
        return s.concat(String.valueOf(i));
    }
}
