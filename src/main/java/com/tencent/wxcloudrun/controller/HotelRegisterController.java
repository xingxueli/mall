package com.tencent.wxcloudrun.controller;


import com.google.common.base.Preconditions;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.HotelRegisterRequest;
import com.tencent.wxcloudrun.model.GuestRoom;
import com.tencent.wxcloudrun.service.GuestRoomService;
import com.tencent.wxcloudrun.service.HotelRegisterService;
import com.tencent.wxcloudrun.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequestMapping("hotel")
public class HotelRegisterController {

    final HotelRegisterService hotelRegisterService;
    final GuestRoomService guestRoomService;
    final OrderService orderService;
    final Logger logger;

    public HotelRegisterController(@Autowired HotelRegisterService hotelRegisterService,
                                   @Autowired GuestRoomService guestRoomService,
                                   @Autowired OrderService orderService) {
        this.hotelRegisterService = hotelRegisterService;
        this.guestRoomService = guestRoomService;
        this.orderService = orderService;
        this.logger = LoggerFactory.getLogger(OrderController.class);
    }

    /**
     * 入住
     * @param hotelRegisterRequest
     * @return
     */
    @PostMapping(value = "/checkin")
    ApiResponse checkin(@RequestBody HotelRegisterRequest hotelRegisterRequest) {
        logger.info("HotelRegisterController checkin");
        Preconditions.checkNotNull(hotelRegisterRequest.getId());
        Preconditions.checkNotNull(hotelRegisterRequest.getGuestRoomId());
        Preconditions.checkNotNull(hotelRegisterRequest.getOrderNum());
        Preconditions.checkNotNull(hotelRegisterRequest.getOrderId());
        Preconditions.checkNotNull(hotelRegisterRequest.getRemark());
        Preconditions.checkNotNull(hotelRegisterRequest.getStartTime());

        final GuestRoom guestRoom = guestRoomService.getById(hotelRegisterRequest.getGuestRoomId());
        Preconditions.checkArgument(guestRoom.getRoomStatus().intValue() == 1,"该房间已被其他订单占用，如需入住，请先做其他订单的退房操作");
        int checkType = 1;
        hotelRegisterService.updateHotelRegister(hotelRegisterRequest,checkType);
        return ApiResponse.ok();
    }

    /**
     * 退房
     * @param hotelRegisterRequest
     * @return
     */
    @GetMapping(value = "/checkout")
    ApiResponse checkout(@RequestBody HotelRegisterRequest hotelRegisterRequest) {
        logger.info("HotelRegisterController checkout");
        Preconditions.checkNotNull(hotelRegisterRequest.getId());//如果是退房，则之前的记录一定存在
        Preconditions.checkNotNull(hotelRegisterRequest.getEndTime());

        int checkType = 1;
        hotelRegisterService.updateHotelRegister(hotelRegisterRequest,checkType);
        return ApiResponse.ok();
    }

    public static void main(String[] args) {
        String ZONE = "America/New_York";
        String ZONE_INDIAN = "Asia/Kolkata";
        System.out.println(LocalDateTime.now().getHour());
        LocalDateTime now = Instant.now().atZone(ZoneId.of(ZONE)).toLocalDateTime();
        System.out.println(now);
    }

}
