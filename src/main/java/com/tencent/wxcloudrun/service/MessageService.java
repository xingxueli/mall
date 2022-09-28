package com.tencent.wxcloudrun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tencent.wxcloudrun.dto.MessageRequest;
import com.tencent.wxcloudrun.dto.RoomAndHotelRegisterDto;
import com.tencent.wxcloudrun.enums.MessageTypeEnum;
import com.tencent.wxcloudrun.model.Message;
import com.tencent.wxcloudrun.model.TOrder;

public interface MessageService extends IService<Message> {

    void sendSubscribeMessageAsync(MessageTypeEnum messageTypeEnum, TOrder tOrder, RoomAndHotelRegisterDto roomAndHotelRegister);

    void saveMessageCallBackNotify(MessageRequest messageRequest);
}
