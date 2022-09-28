package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tencent.wxcloudrun.Intercepter.HeaderContext;
import com.tencent.wxcloudrun.dao.HotelRegisterMapper;
import com.tencent.wxcloudrun.dao.MessageDetailMapper;
import com.tencent.wxcloudrun.dao.MessageMapper;
import com.tencent.wxcloudrun.dto.*;
import com.tencent.wxcloudrun.enums.CancelType;
import com.tencent.wxcloudrun.enums.MessageTypeEnum;
import com.tencent.wxcloudrun.model.HotelRegister;
import com.tencent.wxcloudrun.model.Message;
import com.tencent.wxcloudrun.model.MessageDetail;
import com.tencent.wxcloudrun.model.TOrder;
import com.tencent.wxcloudrun.service.MessageService;
import com.tencent.wxcloudrun.utils.DateUtil;
import com.tencent.wxcloudrun.utils.WeChatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
@Slf4j
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Autowired
    MessageDetailMapper messageDetailMapper;

    private final static ExecutorService executorService = Executors.newFixedThreadPool(4);

    public void sendSubscribeMessageAsync(MessageTypeEnum messageTypeEnum, TOrder tOrder, RoomAndHotelRegisterDto roomAndHotelRegister){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                sendSubscribeMessage(messageTypeEnum,tOrder,roomAndHotelRegister);
            }
        });
    }

    private void sendSubscribeMessage(MessageTypeEnum messageTypeEnum, TOrder tOrder, RoomAndHotelRegisterDto roomAndHotelRegister){

        String _jsonData = "";
        switch (messageTypeEnum){
            case ORDER_PAID:
                _jsonData = JSON.toJSONString(buildTemplate1(tOrder,roomAndHotelRegister));
            case ORDER_CANCELED:
                _jsonData = JSON.toJSONString(buildTemplate2(tOrder,roomAndHotelRegister));
            case CHECKED_IN:
                _jsonData = JSON.toJSONString(buildTemplate3(tOrder,roomAndHotelRegister));
        }
        // 根据小程序穿过来的code想这个url发送请求
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send";
        // 发送请求，返回Json字符串
        String str = WeChatUtil.httpRequest(url, "POST", _jsonData);
        JSONObject jsonObject = JSONObject.parseObject(str);
        //jsonObject____{"errcode":0,"errmsg":"ok","msgid":2589480923856076802}
        log.info("jsonObject={}",jsonObject.toJSONString());
    }

    private Template1 buildTemplate1(TOrder tOrder, RoomAndHotelRegisterDto roomAndHotelRegister){
        Template1 template1 = new Template1();
        template1.setTouser(HeaderContext.getHeaders().getOpenId());
        HashMap<String, ValueDetail> data = new HashMap<>();
        data.put("number1",ValueDetail.builder().value(tOrder.getOrderNum()).build());
        data.put("name2",ValueDetail.builder().value(tOrder.getOrderName()).build());
        String createTime = DateUtil.formatDateTime(tOrder.getCreateTime());
        data.put("date3",ValueDetail.builder().value(createTime).build());
        //订单内容
        String orderContent = "您支付了一笔订单，预定的房间号是"+roomAndHotelRegister.getGuestRoom().getRoomNum();
        data.put("thing6",ValueDetail.builder().value(orderContent).build());
        //订单金额
        String orderAmount = String.valueOf(tOrder.getTotalAmount()).concat("元");
        data.put("amount10",ValueDetail.builder().value(orderAmount).build());
        template1.setData(data);
        return template1;
    }

    private Template2 buildTemplate2(TOrder tOrder, RoomAndHotelRegisterDto roomAndHotelRegister){
        Template2 template2 = new Template2();
        template2.setTouser(HeaderContext.getHeaders().getOpenId());
        HashMap<String, ValueDetail> data = new HashMap<>();
        data.put("character_string1",ValueDetail.builder().value(tOrder.getOrderNum()).build());
        data.put("thing2",ValueDetail.builder().value(tOrder.getOrderName()).build());
        //订单金额
        String orderAmount = String.valueOf(tOrder.getTotalAmount()).concat("元");
        data.put("amount3",ValueDetail.builder().value(orderAmount).build());
        //取消原因
        data.put("thing4",ValueDetail.builder().value(CancelType.getCancelTypeName(tOrder.getCancelReasonType())).build());
        //商品名称
        String roomName = roomAndHotelRegister.getGuestRoom().getRoomName().concat(" ").concat(roomAndHotelRegister.getGuestRoom().getRoomNum());
        data.put("thing9",ValueDetail.builder().value(roomName).build());
        template2.setData(data);
        return template2;
    }

    private Template3 buildTemplate3(TOrder tOrder, RoomAndHotelRegisterDto roomAndHotelRegister){
        Template3 template3 = new Template3();
        template3.setTouser(HeaderContext.getHeaders().getOpenId());
        HashMap<String, ValueDetail> data = new HashMap<>();
        //温馨提示
        String tipsContent = "已给您办理入住，房间号是"+roomAndHotelRegister.getGuestRoom().getRoomNum();
        data.put("thing3",ValueDetail.builder().value(tipsContent).build());
        //姓名
        data.put("thing6",ValueDetail.builder().value(tOrder.getOrderName()).build());
        //电话
        data.put("phone_number7",ValueDetail.builder().value(tOrder.getOrderMobile()).build());
        //审核时间
        String createTime = DateUtil.formatDateTime(roomAndHotelRegister.getHotelRegister().getCreateTime());
        data.put("time8",ValueDetail.builder().value(createTime).build());
        template3.setData(data);
        return template3;
    }

    public void saveMessageCallBackNotify(MessageRequest messageRequest){
        Message message = new Message();
        message.setCreateTime(messageRequest.getCreateTime());
        message.setEvent(messageRequest.getEvent());
        message.setFromUserName(messageRequest.getFromUserName());
        message.setToUserName(messageRequest.getToUserName());
        message.setMsgType(messageRequest.getMsgType());
        this.save(message);
        final List<MessageDetailRequest> list = messageRequest.getList();
        if(!CollectionUtils.isEmpty(list)){
            MessageDetail messageDetail = null;
            for (MessageDetailRequest messageDetailRequest : list) {
                messageDetail = new MessageDetail();
                messageDetail.setMessageId(message.getId());
                messageDetail.setErrorCode(messageDetailRequest.getErrorCode());
                messageDetail.setErrorStatus(messageDetailRequest.getErrorStatus());
                messageDetail.setMsgId(messageDetailRequest.getMsgID());
                messageDetail.setTemplateId(messageDetailRequest.getTemplateId());
                messageDetail.setSubscribeStatusString(messageDetailRequest.getSubscribeStatusString());
                messageDetail.setPopupScene(messageDetailRequest.getPopupScene());
                messageDetailMapper.insert(messageDetail);
            }
        }
    }

}
