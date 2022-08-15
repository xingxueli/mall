package com.tencent.wxcloudrun.controller;


import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.RoomsResponse;
import com.tencent.wxcloudrun.service.GuestRoomService;
import com.tencent.wxcloudrun.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

    @GetMapping(value = "/list")
    ApiResponse list() {
        logger.info("GuestRoomController list");
        final List<RoomsResponse> collect = guestRoomService.list().stream().map(r -> {
            RoomsResponse roomsResponse = new RoomsResponse();
            roomsResponse.setSpuId(r.getId());
            roomsResponse.setPrice(r.getRoomPrice());
            roomsResponse.setOriginPrice(r.getRoomOriginPrice());
            roomsResponse.setThumb(r.getImageUrl());
            roomsResponse.setTitle(r.getRoomName());
            if (StringUtils.isNotEmpty(r.getTags())) {
                roomsResponse.setTags(JSON.parseArray(r.getTags(), String.class));
            }
            return roomsResponse;
        }).collect(Collectors.toList());
        return ApiResponse.ok(collect);
    }


}
