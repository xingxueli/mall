package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.google.common.base.Preconditions;
import com.tencent.wxcloudrun.Intercepter.HeaderContext;
import com.tencent.wxcloudrun.dao.OrderMapper;
import com.tencent.wxcloudrun.dto.*;
import com.tencent.wxcloudrun.enums.*;
import com.tencent.wxcloudrun.model.GuestRoom;
import com.tencent.wxcloudrun.model.HotelRegister;
import com.tencent.wxcloudrun.model.PayLog;
import com.tencent.wxcloudrun.model.TOrder;
import com.tencent.wxcloudrun.service.*;
import com.tencent.wxcloudrun.task.DelayService;
import com.tencent.wxcloudrun.task.DelayTask;
import com.tencent.wxcloudrun.utils.ConstantUtil;
import com.tencent.wxcloudrun.utils.DateUtils;
import com.tencent.wxcloudrun.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.list.AbstractLinkedList;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    @Autowired
    WxPayService wxPayService;

    @Autowired
    PayLogService payLogService;

    @Autowired
    DelayService delayService;

    @Autowired
    MessageService messageService;

    private static final String CALLBACK_ADDRESS = "https://springboot-krih-3055-4-1313299760.sh.run.tcloudbase.com";

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
            orderVo.setPaymentAmount(orderVo.getTotalAmount());
            orderVo.setRemark(r.getRemark());
            orderVo.setOrderName(r.getOrderName());
            orderVo.setOrderMobile(r.getOrderMobile());
            orderVo.setPredictEndTime(r.getEndTime());
            orderVo.setPredictStartTime(r.getStartTime());
            orderVo.setQuantity(r.getDays());
            LocalDateTime localDateTime = DateUtils.toLocalDateTime(r.getCreateTime());
            localDateTime.plusMinutes(ConstantUtil.THIRTY_MINUTES);
            orderVo.setAutoCancelTime(String.valueOf(DateUtils.getTime(localDateTime)));
            buildButtonVos(orderVo,r);

            List<OrderItemVo> orderItemVos = new ArrayList<>();
            OrderItemVo orderItemVo = new OrderItemVo();
            final RoomAndHotelRegisterDto roomAndHotelRegisterDto = getRoomAndHotelRegister(r.getId());
            final HotelRegister hotelRegister = roomAndHotelRegisterDto.getHotelRegister();
            if(hotelRegister == null){
                return orderVo;
            }
            orderItemVo.setId(String.valueOf(hotelRegister.getId()));
            orderItemVo.setStartDate(hotelRegister.getStartTime());
            orderItemVo.setEndDate(hotelRegister.getEndTime());
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
            orderVo.setStoreId(guestRoom.getStoreId());
            orderVo.setStoreName(guestRoom.getStoreName());

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
            paymentVo.setPayWayName("????????????");
            orderVo.setPaymentVO(paymentVo);
            return orderVo;
        }).collect(Collectors.toList());
    }

    private void buildButtonVos(OrderVo orderVo,TOrder tOrder){
        List<ButtonVo> list = new ArrayList<>();
        if(tOrder.getOrderStatus().intValue() == OrderStatusEnum.TO_BE_PAID.getCode()){
            ButtonVo buttonVo1 = new ButtonVo();
            buttonVo1.setPrimary(true);
            buttonVo1.setName(OrderButtonTypes.PAY.getName());
            buttonVo1.setType(OrderButtonTypes.PAY.getCode());
            list.add(buttonVo1);
            ButtonVo buttonVo2 = new ButtonVo();
            buttonVo2.setPrimary(false);
            buttonVo2.setName(OrderButtonTypes.CANCEL.getName());
            buttonVo2.setType(OrderButtonTypes.CANCEL.getCode());
            list.add(buttonVo2);
            orderVo.setButtonVOs(list);
        }else if(tOrder.getOrderStatus().intValue() == OrderStatusEnum.PAID_AND_CHECK_IN.getCode()){
            ButtonVo buttonVo2 = new ButtonVo();
            buttonVo2.setPrimary(false);
            buttonVo2.setName(OrderButtonTypes.CANCEL.getName());
            buttonVo2.setType(OrderButtonTypes.CANCEL.getCode());
            list.add(buttonVo2);
            orderVo.setButtonVOs(list);
        }else{
            ButtonVo buttonVo2 = new ButtonVo();
            buttonVo2.setPrimary(false);
            buttonVo2.setName(OrderButtonTypes.COMMENT.getName());
            buttonVo2.setType(OrderButtonTypes.COMMENT.getCode());
            list.add(buttonVo2);
            orderVo.setButtonVOs(list);
        }
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
            webOrderVo.setPayWayName("????????????");
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

    public RoomAndHotelRegisterDto getRoomAndHotelRegister(Integer orderId){
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

    public void cancel(OrderRequest orderRequest) throws Exception {

        final String orderNum = orderRequest.getOrderNum();
        QueryWrapper<TOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_num",orderRequest.getOrderNum());
        final TOrder tOrder = this.getOne(queryWrapper);
        if(tOrder == null){
            throw new Exception("???????????????");
        }

        Integer orderStatus = null;
        //??????????????????
        //???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        if(tOrder.getOrderStatus().intValue() == OrderStatusEnum.PAID_AND_CHECK_IN.getCode()){
            WxPayRefundResult result = null;
            try {
                WxPayRefundRequest wxPayRefundRequest = new WxPayRefundRequest();
                wxPayRefundRequest.setOutTradeNo(orderNum);
                wxPayRefundRequest.setOutRefundNo("T".concat(orderNum));//????????????
                wxPayRefundRequest.setTotalFee(new BigDecimal(tOrder.getTotalAmount()).multiply(new BigDecimal(100)).intValue());
                wxPayRefundRequest.setRefundFee(new BigDecimal(tOrder.getTotalAmount()).multiply(new BigDecimal(100)).intValue());
                wxPayRefundRequest.setNotifyUrl(CALLBACK_ADDRESS.concat("/order/refundNotify"));
                result = wxPayService.refund(wxPayRefundRequest);
            } catch (WxPayException e) {
                log.info("WxPayException={}",e);
            }
        }else{
            //??????????????????
            orderRequest.setOrderStatus(OrderStatusEnum.CANCELED_UNPIAD.getCode());
            doCancel(tOrder,orderRequest);
        }

    }

    private void doCancel(TOrder tOrder,OrderRequest orderRequest){
        final RoomAndHotelRegisterDto roomAndHotelRegister = getRoomAndHotelRegister(tOrder.getId());
        GuestRoom guestRoom = new GuestRoom();
        guestRoom.setId(roomAndHotelRegister.getGuestRoom().getId());
        guestRoom.setRoomStatus(0);//??????????????????????????????????????????????????????????????????????????????
        guestRoomService.updateById(guestRoom);
        //????????????????????????,??????????????????????????????????????????
        TOrder condition = new TOrder();
        condition.setCancelType(2);
        condition.setCancelReasonType(orderRequest.getCancelReasonType());
        condition.setCancelTime(new Date());
        condition.setId(tOrder.getId());
        condition.setOrderStatus(orderRequest.getOrderStatus());
        this.updateById(condition);
        //???????????????????????????

    }

    @Override
    public void autoCancelOrder(String orderNum){
        Preconditions.checkNotNull(orderNum);
        QueryWrapper<TOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_num",orderNum);
        final TOrder tOrder = this.getOne(queryWrapper);
        if(tOrder == null){
            return;
        }

        TOrder condition = new TOrder();
        condition.setCancelTime(new Date());
        condition.setId(tOrder.getId());
        condition.setOrderStatus(OrderStatusEnum.CANCELED_UNPIAD.getCode());
        this.updateById(condition);
    }

    public AppletOrderResponse create(OrderRequest orderRequest, HttpServletRequest request){
        log.info("OrderServiceImpl, create: {}", JSON.toJSONString(orderRequest));
        String orderNum = orderRequest.getOrderNum();
        if(StringUtils.isEmpty(orderRequest.getOrderNum())){//??????????????????????????????????????????????????????????????????????????????????????????
            orderNum = generateOrderNum();
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
            order.setDays(orderRequest.getQuantity());
            final boolean saveResult = this.save(order);

            HotelRegisterRequest hotelRegisterRequest = new HotelRegisterRequest();
            hotelRegisterRequest.setGuestRoomId(orderRequest.getGuestRoomId());
            hotelRegisterRequest.setActualStartTime(orderRequest.getActualStartTime());
            hotelRegisterRequest.setActualEndTime(orderRequest.getActualEndTime());
            hotelRegisterRequest.setRemark(orderRequest.getRemark());
            hotelRegisterRequest.setOrderNum(orderNum);
            hotelRegisterRequest.setOrderId(order.getId());
            hotelRegisterService.saveHotelRegister(hotelRegisterRequest, CheckType.UNCHECK.getCode());
            //???????????????30??????????????????
            addTask(orderNum);
        }


        // ??????????????????
        WxPayMpOrderResult result = null;
        try {
            WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = new WxPayUnifiedOrderRequest();
            wxPayUnifiedOrderRequest.setOutTradeNo(orderNum);
            wxPayUnifiedOrderRequest.setTotalFee(new BigDecimal(orderRequest.getTotalAmount()).multiply(new BigDecimal(100)).intValue());
            wxPayUnifiedOrderRequest.setSpbillCreateIp(IpUtil.getIpAddr(request));
            wxPayUnifiedOrderRequest.setOpenid(HeaderContext.getHeaders().getOpenId());
            wxPayUnifiedOrderRequest.setBody("????????????");
            wxPayUnifiedOrderRequest.setNotifyUrl(CALLBACK_ADDRESS.concat("/order/payNotify"));
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
        appletOrderResponse.setChannel("wechat");
        appletOrderResponse.setPayAmt(orderRequest.getTotalAmount());
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
        //???????????????30??????????????????
        addTask(orderNum);
        return saveResult;
    }

    private void addTask(String orderNum){
        //???????????????30??????????????????
        DelayTask delayTask = new DelayTask(orderNum,ConstantUtil.THIRTY_MINUTES * 60);
        delayService.put(delayTask);
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

    public OrderVo detail(String orderNum){
        QueryWrapper<TOrder> queueWrapper = new QueryWrapper<>();
        queueWrapper.eq("order_num", orderNum);
        final TOrder tOrder = this.getOne(queueWrapper);
        if(tOrder == null){
            return null;
        }
        List<TOrder> tOrders = new ArrayList<>();
        tOrders.add(tOrder);
        final List<OrderVo> transform = transform(tOrders);
        return transform.get(0);
    }

    private String generateOrderNum(){
        final String s = DateUtils.formatOrderDateTime(LocalDateTime.now());
        final int i = ThreadLocalRandom.current().nextInt(100000, 999999);
        return s.concat(String.valueOf(i));
    }

    /**
     * ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     *
     * ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * @param xmlData
     */
    @Override
    public void payNotify(String xmlData){
        WxPayOrderNotifyResult orderNotifyResult = null;
        try {
            orderNotifyResult = wxPayService.parseOrderNotifyResult(xmlData);
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        if(orderNotifyResult != null){
            final String orderNum = orderNotifyResult.getOutTradeNo();
            QueryWrapper<TOrder> queueWrapper = new QueryWrapper<>();
            queueWrapper.eq("order_num", orderNum);
            final TOrder tOrder = this.getOne(queueWrapper);
            if(tOrder == null){
                return;
            }
            //?????????????????????????????????
            if(tOrder.getOrderStatus().intValue() != OrderStatusEnum.TO_BE_PAID.getCode()){
                return;
            }
            //???????????????????????????????????????????????????
            TOrder condition = new TOrder();
            condition.setId(tOrder.getId());
            condition.setOrderStatus(OrderStatusEnum.PAID_AND_CHECK_IN.getCode());
            condition.setPayTime(new Date());
            this.updateById(condition);
            //?????????????????????
            PayLog payLog = new PayLog();
            payLog.setOrderId(tOrder.getId());
            payLog.setCreateTime(new Date());
            payLog.setPrice(orderNotifyResult.getTotalFee());
            payLog.setTotalFee(orderNotifyResult.getTotalFee());
            payLog.setResultCode(orderNotifyResult.getResultCode());
            payLog.setReturnCode(orderNotifyResult.getReturnCode());
            payLog.setResultStatus(1);
            payLog.setTag(" ");
            payLog.setTransactionId(orderNotifyResult.getTransactionId());
            payLog.setReturnData(orderNotifyResult.getReturnMsg());
            payLog.setOpenid(orderNotifyResult.getOpenid());
            payLogService.getBaseMapper().insert(payLog);
            //???????????????????????????????????????
            messageService.sendSubscribeMessageAsync(MessageTypeEnum.ORDER_PAID,tOrder,getRoomAndHotelRegister(tOrder.getId()));
        }
    }

    /**
     * ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     *
     * ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * @param xmlData
     */
    @Override
    public void refundNotify(String xmlData){
        WxPayRefundNotifyResult refundNotifyResult = null;
        try {
            refundNotifyResult = wxPayService.parseRefundNotifyResult(xmlData);
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        if(refundNotifyResult != null){
            final WxPayRefundNotifyResult.ReqInfo reqInfo = refundNotifyResult.getReqInfo();
            final String orderNum = reqInfo.getOutTradeNo();
            QueryWrapper<TOrder> queueWrapper = new QueryWrapper<>();
            queueWrapper.eq("order_num", orderNum);
            final TOrder tOrder = this.getOne(queueWrapper);
            if(tOrder == null){
                return;
            }
            //??????????????????????????????????????????
            if(tOrder.getOrderStatus().intValue() != OrderStatusEnum.PAID_AND_CHECK_IN.getCode()){
                return;
            }
            //??????????????????
            OrderRequest orderRequest = new OrderRequest();
            orderRequest.setOrderStatus(OrderStatusEnum.CANCELED_PAID.getCode());
            doCancel(tOrder,orderRequest);
            //?????????????????????????????????????????????????????????????????????????????????????????????
            messageService.sendSubscribeMessageAsync(MessageTypeEnum.ORDER_CANCELED,tOrder,getRoomAndHotelRegister(tOrder.getId()));
        }
    }

    @Override
    public List<TOrder> getOrderTaskList(Integer orderStatus){
        QueryWrapper<TOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_status", orderStatus);
        return this.list(queryWrapper);
    }
}
