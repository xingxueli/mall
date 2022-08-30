package com.tencent.wxcloudrun.controller;


import com.google.common.base.Preconditions;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.RoomRequest;
import com.tencent.wxcloudrun.dto.RoomResponse;
import com.tencent.wxcloudrun.model.GuestRoom;
import com.tencent.wxcloudrun.service.GuestRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("room")
public class GuestRoomController {

    final GuestRoomService guestRoomService;
    final Logger logger;

    public GuestRoomController(@Autowired GuestRoomService guestRoomService) {
        this.guestRoomService = guestRoomService;
        this.logger = LoggerFactory.getLogger(OrderController.class);
    }

    @PostMapping(value = "/list")
    ApiResponse list(@RequestBody RoomRequest roomRequest) {
        logger.info("GuestRoomController list");
        final RoomResponse roomList = guestRoomService.getRoomList(roomRequest);
        return ApiResponse.ok(roomList);
    }

    /**
     * 新增
     * @param roomRequest
     * @return
     */
    @PostMapping(value = "/create")
    ApiResponse create(@RequestBody RoomRequest roomRequest) {
        logger.info("GuestRoomController create");
        Preconditions.checkNotNull(roomRequest.getRoomNum());
        Preconditions.checkNotNull(roomRequest.getRoomName());
        Preconditions.checkNotNull(roomRequest.getRoomPrice());
        Preconditions.checkNotNull(roomRequest.getRoomOriginPrice());
        Preconditions.checkNotNull(roomRequest.getImageUrl());
        Preconditions.checkNotNull(roomRequest.getStoreId());
        Preconditions.checkNotNull(roomRequest.getStoreName());
        Preconditions.checkNotNull(roomRequest.getType());

        GuestRoom guestRoom = new GuestRoom();
        guestRoom.setRoomStatus(0);//初始化    客房状态   0 未预定 1 已预定
        guestRoom.setRoomNum(roomRequest.getRoomNum());
        guestRoom.setRoomName(roomRequest.getRoomName());
        guestRoom.setRoomPrice(roomRequest.getRoomPrice());
        guestRoom.setRoomOriginPrice(roomRequest.getRoomOriginPrice());
        guestRoom.setCreateTime(new Date());
        guestRoom.setImageUrl(roomRequest.getImageUrl());
        guestRoom.setStoreId(roomRequest.getStoreId());
        guestRoom.setStoreName(roomRequest.getStoreName());
        guestRoom.setType(roomRequest.getType());
        guestRoomService.save(guestRoom);
        return ApiResponse.ok();
    }

    /**
     * 修改信息
     * @param roomRequest
     * @return
     */
    @PostMapping(value = "/update")
    ApiResponse update(@RequestBody RoomRequest roomRequest) {
        logger.info("GuestRoomController update");
        Preconditions.checkNotNull(roomRequest.getId());

        GuestRoom guestRoom = new GuestRoom();
        guestRoom.setId(roomRequest.getId());
        guestRoom.setRoomNum(roomRequest.getRoomNum());
        guestRoom.setRoomName(roomRequest.getRoomName());
        guestRoom.setRoomPrice(roomRequest.getRoomPrice());
        guestRoom.setRoomOriginPrice(roomRequest.getRoomOriginPrice());
        guestRoom.setUpdateTime(new Date());
        guestRoom.setImageUrl(roomRequest.getImageUrl());
        guestRoom.setStoreId(roomRequest.getStoreId());
        guestRoom.setStoreName(roomRequest.getStoreName());
        guestRoom.setType(roomRequest.getType());
        guestRoomService.updateById(guestRoom);
        return ApiResponse.ok();
    }

    /**
     * 上下架
     * @param roomRequest
     * @return
     */
    @PostMapping(value = "/roomShelves")
    ApiResponse roomShelves(@RequestBody RoomRequest roomRequest) {
        logger.info("GuestRoomController roomShelves");
        Preconditions.checkNotNull(roomRequest.getId());
        Preconditions.checkNotNull(roomRequest.getRoomShelves());

        GuestRoom guestRoom = new GuestRoom();
        guestRoom.setId(roomRequest.getId());
        guestRoom.setRoomShelves(roomRequest.getRoomShelves());
        guestRoomService.updateById(guestRoom);
        return ApiResponse.ok();
    }

}
