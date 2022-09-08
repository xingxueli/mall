package com.tencent.wxcloudrun.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tencent.wxcloudrun.model.HotelRegister;
import com.tencent.wxcloudrun.model.TOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface HotelRegisterMapper extends BaseMapper<HotelRegister> {

    @Select("select * from hotel_register where guest_room_id = #{guestRoomId} order by id desc limit 1 ")
    HotelRegister getLatestOrderByRoomId(@Param("guestRoomId") Integer guestRoomId);
}
