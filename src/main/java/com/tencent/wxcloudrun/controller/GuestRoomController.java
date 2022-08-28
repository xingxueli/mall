package com.tencent.wxcloudrun.controller;


import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.RoomRequest;
import com.tencent.wxcloudrun.dto.RoomResponse;
import com.tencent.wxcloudrun.service.GuestRoomService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping(value = "/add")
    ApiResponse add(@RequestBody RoomRequest roomRequest) {
        logger.info("GuestRoomController add");
        return ApiResponse.ok();
    }

    @GetMapping(value = "/update")
    ApiResponse update(@RequestBody RoomRequest roomRequest) {
        logger.info("GuestRoomController update");
        return ApiResponse.ok();
    }

    @GetMapping(value = "/delete")
    ApiResponse delete(@RequestBody RoomRequest roomRequest) {
        logger.info("GuestRoomController delete");
        return ApiResponse.ok();
    }

}
