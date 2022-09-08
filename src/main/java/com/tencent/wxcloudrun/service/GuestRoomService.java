package com.tencent.wxcloudrun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tencent.wxcloudrun.dto.RoomItem;
import com.tencent.wxcloudrun.dto.RoomRequest;
import com.tencent.wxcloudrun.dto.RoomResponse;
import com.tencent.wxcloudrun.model.GuestRoom;

public interface GuestRoomService extends IService<GuestRoom> {

    RoomResponse getRoomList(RoomRequest roomRequest);
    RoomItem getRoomDetail(RoomRequest roomRequest);
}
