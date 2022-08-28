package com.tencent.wxcloudrun.dto;


import com.tencent.wxcloudrun.model.GuestRoom;
import com.tencent.wxcloudrun.model.HotelRegister;
import lombok.Data;

@Data
public class RoomAndHotelRegisterDto{
    private GuestRoom guestRoom;
    private HotelRegister hotelRegister;
}
