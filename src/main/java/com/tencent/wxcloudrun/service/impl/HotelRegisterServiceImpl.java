package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tencent.wxcloudrun.dao.HotelRegisterMapper;
import com.tencent.wxcloudrun.dto.HotelRegisterRequest;
import com.tencent.wxcloudrun.model.HotelRegister;
import com.tencent.wxcloudrun.service.HotelRegisterService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HotelRegisterServiceImpl extends ServiceImpl<HotelRegisterMapper, HotelRegister> implements HotelRegisterService {

    public void saveHotelRegister(HotelRegisterRequest hotelRegisterRequest){
        HotelRegister hotelRegister = new HotelRegister();
        hotelRegister.setRegisterStatus(0);//创建订单时候选择的房间
        hotelRegister.setCreateTime(new Date());
        hotelRegister.setStartTime(hotelRegisterRequest.getStartTime());
        hotelRegister.setEndTime(hotelRegisterRequest.getEndTime());
        hotelRegister.setRemark(hotelRegisterRequest.getRemark());
        hotelRegister.setGuestRoomId(hotelRegisterRequest.getGuestRoomId());
        hotelRegister.setOrderNum(hotelRegisterRequest.getOrderNum());
        hotelRegister.setOrderId(hotelRegisterRequest.getOrderId());
        this.save(hotelRegister);
    }

    public void updateHotelRegister(HotelRegisterRequest hotelRegisterRequest,int checkType){
        HotelRegister hotelRegister = new HotelRegister();
        hotelRegister.setId(hotelRegisterRequest.getOrderId());
        hotelRegister.setRegisterStatus(checkType);
        hotelRegister.setEndTime(hotelRegisterRequest.getEndTime());
        hotelRegister.setCreateTime(new Date());
        hotelRegister.setStartTime(hotelRegisterRequest.getStartTime());
        hotelRegister.setRemark(hotelRegisterRequest.getRemark());
        hotelRegister.setGuestRoomId(hotelRegisterRequest.getGuestRoomId());
        hotelRegister.setOrderNum(hotelRegisterRequest.getOrderNum());
        hotelRegister.setOrderId(hotelRegisterRequest.getOrderId());
        this.updateById(hotelRegister);
    }
}
