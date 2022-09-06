package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tencent.wxcloudrun.dao.HotelRegisterMapper;
import com.tencent.wxcloudrun.dto.HotelRegisterRequest;
import com.tencent.wxcloudrun.enums.CheckType;
import com.tencent.wxcloudrun.enums.RoomStatus;
import com.tencent.wxcloudrun.model.GuestRoom;
import com.tencent.wxcloudrun.model.HotelRegister;
import com.tencent.wxcloudrun.service.GuestRoomService;
import com.tencent.wxcloudrun.service.HotelRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HotelRegisterServiceImpl extends ServiceImpl<HotelRegisterMapper, HotelRegister> implements HotelRegisterService {

    @Autowired
    GuestRoomService guestRoomService;

    public void saveHotelRegister(HotelRegisterRequest hotelRegisterRequest,int checkType){
        HotelRegister hotelRegister = new HotelRegister();
        hotelRegister.setRegisterStatus(checkType);//创建订单时候选择的房间
        hotelRegister.setCreateTime(new Date());
        hotelRegister.setStartTime(hotelRegisterRequest.getActualStartTime());
        hotelRegister.setEndTime(hotelRegisterRequest.getActualEndTime());
        hotelRegister.setRemark(hotelRegisterRequest.getRemark());
        hotelRegister.setGuestRoomId(hotelRegisterRequest.getGuestRoomId());
        hotelRegister.setOrderNum(hotelRegisterRequest.getOrderNum());
        hotelRegister.setOrderId(hotelRegisterRequest.getOrderId());
        this.save(hotelRegister);
    }

    public void updateHotelRegister(HotelRegisterRequest hotelRegisterRequest,int checkType){
        HotelRegister hotelRegister = new HotelRegister();
        hotelRegister.setId(hotelRegisterRequest.getHotelRegisterId());
        hotelRegister.setRegisterStatus(checkType);
        hotelRegister.setEndTime(hotelRegisterRequest.getActualEndTime());
        hotelRegister.setStartTime(hotelRegisterRequest.getActualStartTime());
        hotelRegister.setRemark(hotelRegisterRequest.getRemark());
        hotelRegister.setGuestRoomId(hotelRegisterRequest.getGuestRoomId());
        hotelRegister.setOrderNum(hotelRegisterRequest.getOrderNum());
        hotelRegister.setOrderId(hotelRegisterRequest.getOrderId());
        this.updateById(hotelRegister);
        //入住的时候需要更新库存
        Integer roomStatus = null;
        if(checkType == CheckType.CHECKIN.getCode()){
            roomStatus = RoomStatus.RESERVED.getCode();
        }else if(checkType == CheckType.CHECKOUT.getCode()){
            roomStatus = RoomStatus.UNRESERVED.getCode();
        }
        GuestRoom guestRoom = new GuestRoom();
        guestRoom.setId(hotelRegisterRequest.getGuestRoomId());
        guestRoom.setRoomStatus(roomStatus);
        guestRoomService.updateById(guestRoom);
    }

}
