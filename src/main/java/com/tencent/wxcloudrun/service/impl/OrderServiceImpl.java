package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.tencent.wxcloudrun.Intercepter.HeaderContext;
import com.tencent.wxcloudrun.dao.OrderMapper;
import com.tencent.wxcloudrun.dto.*;
import com.tencent.wxcloudrun.enums.CheckType;
import com.tencent.wxcloudrun.enums.OrderStatusEnum;
import com.tencent.wxcloudrun.enums.PayType;
import com.tencent.wxcloudrun.model.GuestRoom;
import com.tencent.wxcloudrun.model.HotelRegister;
import com.tencent.wxcloudrun.model.TOrder;
import com.tencent.wxcloudrun.service.GuestRoomService;
import com.tencent.wxcloudrun.service.HotelRegisterService;
import com.tencent.wxcloudrun.service.OrderService;
import com.tencent.wxcloudrun.utils.DateUtils;
import com.tencent.wxcloudrun.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
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

    @Autowired
    WxPayService wxPayService;

    public OrderResponse  getOrderList(OrderRequest orderRequest){
        OrderResponse orderResponse = new OrderResponse();

        QueryWrapper<TOrder> queueWrapper = new QueryWrapper<>();
        if(orderRequest.getOrderStatus() != null && orderRequest.getOrderStatus().intValue() != -1){
            queueWrapper.eq("order_status",orderRequest.getOrderStatus().intValue());
        }
        queueWrapper.eq("uid", HeaderContext.getHeaders().getOpenId());
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
            final HotelRegister hotelRegister = roomAndHotelRegisterDto.getHotelRegister();
            if(hotelRegister == null){
                return orderVo;
            }
            orderItemVo.setId(String.valueOf(hotelRegister.getId()));

            final GuestRoom guestRoom = roomAndHotelRegisterDto.getGuestRoom();
            if(guestRoom == null){
                return orderVo;
            }
            orderItemVo.setActualPrice(guestRoom.getRoomPrice());
            orderItemVo.setOriginPrice(guestRoom.getRoomOriginPrice());
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

    public WebOrderResponse  getWebOrderList(OrderRequest orderRequest){
        WebOrderResponse webOrderResponse = new WebOrderResponse();

        QueryWrapper<TOrder> queueWrapper = new QueryWrapper<>();
        if(orderRequest.getOrderStatus() != null && orderRequest.getOrderStatus().intValue() != -1){
            queueWrapper.eq("order_status",orderRequest.getOrderStatus().intValue());
        }
        Page<TOrder> pageCondition = new Page<>(orderRequest.getPageNum(),orderRequest.getPageSize());
        final Page<TOrder> page = this.page(pageCondition, queueWrapper);
        webOrderResponse.setPageNum(orderRequest.getPageNum());
        webOrderResponse.setTotalCount(page.getTotal());
        webOrderResponse.setPageSize(orderRequest.getPageSize());

        final List<TOrder> records = page.getRecords();
        webOrderResponse.setOrders(webTransform(records));
        return webOrderResponse;
    }

    private List<WebOrderVo> webTransform(List<TOrder> records){
        return records.stream().map(r -> {
            WebOrderVo webOrderVo = new WebOrderVo();
            webOrderVo.setOrderId(String.valueOf(r.getId()));
            webOrderVo.setOrderNum(r.getOrderNum());
            webOrderVo.setOrderStatus(r.getOrderStatus());
            webOrderVo.setCancelReasonType(r.getCancelReasonType());
            webOrderVo.setCreateTime(r.getCreateTime());
            webOrderVo.setDiscountAmount(String.valueOf(r.getDiscountAmount()));
            webOrderVo.setPaymentAmount(String.valueOf(r.getPaymentAmount()));
            webOrderVo.setTotalAmount(r.getTotalAmount());
            webOrderVo.setOrderStatusName(OrderStatusEnum.getOrderStatusName(r.getOrderStatus()));
            webOrderVo.setOrderSatusRemark("");
            webOrderVo.setOrderName(r.getOrderName());
            webOrderVo.setPayType(r.getPayType());
            webOrderVo.setPayWay(r.getPayWay());
            webOrderVo.setPayWayName("微信支付");
            webOrderVo.setPayTypeName(PayType.getPayTypeName(r.getPayType()));
            webOrderVo.setPredictStartTime(r.getStartTime());
            webOrderVo.setPredictEndTime(r.getEndTime());
            webOrderVo.setRemark(r.getRemark());
            webOrderVo.setOrderMobile(r.getOrderMobile());
            if(r.getPayTime()!=null){
                webOrderVo.setPaySuccessTime(r.getPayTime());
                webOrderVo.setPayTime(r.getPayTime());
            }
            final RoomAndHotelRegisterDto roomAndHotelRegisterDto = getRoomAndHotelRegister(r.getId());
            final GuestRoom guestRoom = roomAndHotelRegisterDto.getGuestRoom();
            if(guestRoom == null){
                return webOrderVo;
            }
            webOrderVo.setRoomName(guestRoom.getRoomName());
            webOrderVo.setRoomNum(guestRoom.getRoomNum());
            webOrderVo.setStoreId(guestRoom.getStoreId());
            webOrderVo.setStoreName(guestRoom.getStoreName());
            webOrderVo.setGuestRoomId(guestRoom.getId());
            final HotelRegister hotelRegister = roomAndHotelRegisterDto.getHotelRegister();
            if(hotelRegister == null){
                return webOrderVo;
            }
            webOrderVo.setActualStartTime(hotelRegister.getStartTime());
            webOrderVo.setActualEndTime(hotelRegister.getEndTime());
            webOrderVo.setRegisterStatus(hotelRegister.getRegisterStatus());
            webOrderVo.setRegisterStatusString(CheckType.getCheckTypeName(hotelRegister.getRegisterStatus().intValue()));
            webOrderVo.setHotelRegisterId(hotelRegister.getId());
            return webOrderVo;
        }).collect(Collectors.toList());
    }

    private RoomAndHotelRegisterDto getRoomAndHotelRegister(Integer orderId){
        RoomAndHotelRegisterDto roomAndHotelRegisterDto = new RoomAndHotelRegisterDto();
        QueryWrapper<HotelRegister> queueWrapper = new QueryWrapper<>();
        queueWrapper.eq("order_id",orderId);
        final HotelRegister hotelRegister = hotelRegisterService.getOne(queueWrapper);
        if(hotelRegister != null){
            roomAndHotelRegisterDto.setHotelRegister(hotelRegister);
            final GuestRoom guestRoom = guestRoomService.getById(hotelRegister.getGuestRoomId());
            roomAndHotelRegisterDto.setGuestRoom(guestRoom);
        }
        return roomAndHotelRegisterDto;
    }

    public List<OrderStatusCount>  orderStatusCount(Integer or){
        return this.getBaseMapper().orderStatusCount();
    }

    public void  cancel(OrderRequest orderRequest){
        final RoomAndHotelRegisterDto roomAndHotelRegister = getRoomAndHotelRegister(orderRequest.getOrderId());
        GuestRoom guestRoom = new GuestRoom();
        guestRoom.setId(roomAndHotelRegister.getGuestRoom().getId());
        guestRoom.setRoomStatus(0);//如果取消，那么不管付钱还是没付钱，都把库存改为为占用
        guestRoomService.updateById(guestRoom);
        //添加更新取消原因,通过按钮点击的一定是手动取消
        TOrder tOrder = new TOrder();
        tOrder.setCancelType(2);
        tOrder.setId(orderRequest.getOrderId());
        this.updateById(tOrder);
        //如果取消的时候，该人已付款，那么需要退款
        //todo

    }

    public AppletOrderResponse create(OrderRequest orderRequest, HttpServletRequest request){
        log.info("OrderServiceImpl, create: {}", JSON.toJSONString(orderRequest));

        final String orderNum = generateOrderNum();
        TOrder order = new TOrder();
        order.setOrderNum(orderNum);
        order.setOrderName(orderRequest.getOrderName());
        order.setOrderMobile(orderRequest.getOrderMobile());
        order.setStartTime(orderRequest.getPredictStartTime());
        order.setEndTime(orderRequest.getPredictEndTime());
        order.setRemark(orderRequest.getRemark());
        order.setTotalAmount(orderRequest.getTotalAmount());
        order.setDiscountAmount(0);
        order.setCreateTime(new Date());
        order.setUid(HeaderContext.getHeaders().getOpenId());
        order.setPayType(PayType.ONLINE.getCode());
        final boolean saveResult = this.save(order);

        HotelRegisterRequest hotelRegisterRequest = new HotelRegisterRequest();
        hotelRegisterRequest.setGuestRoomId(orderRequest.getGuestRoomId());
        hotelRegisterRequest.setActualStartTime(orderRequest.getActualStartTime());
        hotelRegisterRequest.setActualEndTime(orderRequest.getActualEndTime());
        hotelRegisterRequest.setRemark(orderRequest.getRemark());
        hotelRegisterRequest.setOrderNum(orderNum);
        hotelRegisterRequest.setOrderId(order.getId());
        hotelRegisterService.saveHotelRegister(hotelRegisterRequest, CheckType.UNCHECK.getCode());


        // 调用支付接口
        WxPayMpOrderResult result = null;
        try {
            WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = new WxPayUnifiedOrderRequest();
            wxPayUnifiedOrderRequest.setOutTradeNo(orderNum);
            wxPayUnifiedOrderRequest.setTotalFee(new BigDecimal(orderRequest.getTotalAmount()).multiply(new BigDecimal(100)).intValue());
            wxPayUnifiedOrderRequest.setSpbillCreateIp(IpUtil.getIpAddr(request));
            wxPayUnifiedOrderRequest.setOpenid(HeaderContext.getHeaders().getOpenId());
            result = wxPayService.createOrder(wxPayUnifiedOrderRequest);
        } catch (WxPayException e) {
            log.info("WxPayException={}",e);
        }

        AppletOrderResponse appletOrderResponse = new AppletOrderResponse();
        PayInfo payInfo = new PayInfo();
        if(result != null){
            payInfo.setPaySign(result.getPaySign());
            payInfo.setSignType(result.getSignType());
            payInfo.setTimeStamp(result.getTimeStamp());
            payInfo.setPackageName(result.getPackageValue());
            payInfo.setNonceStr(result.getNonceStr());
        }
        appletOrderResponse.setPayInfo(payInfo);
        appletOrderResponse.setTradeNo(orderNum);
        appletOrderResponse.setTransactionId(orderNum);
        return appletOrderResponse;
    }

    public boolean webCreate(OrderRequest orderRequest){
        log.info("OrderServiceImpl, webCreate: {}", JSON.toJSONString(orderRequest));

        final String orderNum = generateOrderNum();
        TOrder order = new TOrder();
        order.setOrderNum(orderNum);
        order.setOrderName(orderRequest.getOrderName());
        order.setOrderMobile(orderRequest.getOrderMobile());
        order.setStartTime(orderRequest.getPredictStartTime());
        order.setEndTime(orderRequest.getPredictEndTime());
        order.setRemark(orderRequest.getRemark());
        order.setTotalAmount(orderRequest.getTotalAmount());
        order.setDiscountAmount(0);
        order.setCreateTime(new Date());
        order.setPayType(PayType.OFFLINE.getCode());
        order.setUid("admin");
        final boolean saveResult = this.save(order);

        HotelRegisterRequest hotelRegisterRequest = new HotelRegisterRequest();
        hotelRegisterRequest.setGuestRoomId(orderRequest.getGuestRoomId());
        hotelRegisterRequest.setRemark(orderRequest.getRemark());
        hotelRegisterRequest.setOrderNum(orderNum);
        hotelRegisterRequest.setOrderId(order.getId());
        hotelRegisterService.saveHotelRegister(hotelRegisterRequest,CheckType.UNCHECK.getCode());
        return saveResult;
    }

    public boolean webUpdate(OrderRequest orderRequest){
        log.info("OrderServiceImpl, webUpdate: {}", JSON.toJSONString(orderRequest));

        final String orderNum = generateOrderNum();
        TOrder order = new TOrder();
        order.setOrderNum(orderNum);
        order.setOrderName(orderRequest.getOrderName());
        order.setOrderMobile(orderRequest.getOrderMobile());
        order.setStartTime(orderRequest.getPredictStartTime());
        order.setEndTime(orderRequest.getPredictEndTime());
        order.setRemark(orderRequest.getRemark());
        order.setTotalAmount(orderRequest.getTotalAmount());
        order.setDiscountAmount(0);
        order.setCreateTime(new Date());
        order.setPayType(PayType.OFFLINE.getCode());
        order.setUid("admin");
        order.setId(orderRequest.getOrderId());
        final boolean saveResult = this.updateById(order);

        HotelRegister hotelRegister = new HotelRegister();
        hotelRegister.setGuestRoomId(orderRequest.getGuestRoomId());
        hotelRegister.setRemark(orderRequest.getRemark());
        hotelRegister.setOrderNum(orderNum);
        hotelRegister.setOrderId(order.getId());
        if(orderRequest.getHotelRegisterId() == null){
            hotelRegisterService.getBaseMapper().insert(hotelRegister);
        }else{
            hotelRegister.setId(orderRequest.getHotelRegisterId());
            hotelRegisterService.getBaseMapper().updateById(hotelRegister);
        }
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
