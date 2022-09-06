package com.tencent.wxcloudrun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tencent.wxcloudrun.dto.HotelRegisterRequest;
import com.tencent.wxcloudrun.model.HotelRegister;

public interface HotelRegisterService extends IService<HotelRegister> {

    void saveHotelRegister(HotelRegisterRequest hotelRegisterRequest,int checkType);

    void updateHotelRegister(HotelRegisterRequest hotelRegisterRequest,int checkType);

}
