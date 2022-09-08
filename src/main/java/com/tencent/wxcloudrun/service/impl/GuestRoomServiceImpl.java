package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tencent.wxcloudrun.dao.GuestRoomMapper;
import com.tencent.wxcloudrun.dao.HotelRegisterMapper;
import com.tencent.wxcloudrun.dto.*;
import com.tencent.wxcloudrun.enums.OrderStatusEnum;
import com.tencent.wxcloudrun.enums.RoomType;
import com.tencent.wxcloudrun.model.GuestRoom;
import com.tencent.wxcloudrun.model.HotelRegister;
import com.tencent.wxcloudrun.model.TOrder;
import com.tencent.wxcloudrun.service.GuestRoomService;
import com.tencent.wxcloudrun.service.HotelRegisterService;
import com.tencent.wxcloudrun.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuestRoomServiceImpl extends ServiceImpl<GuestRoomMapper, GuestRoom> implements GuestRoomService {

    @Autowired
    OrderService orderService;

    @Autowired
    HotelRegisterMapper hotelRegisterMapper;

    public RoomResponse getRoomList(RoomRequest roomRequest){
        RoomResponse roomResponse = new RoomResponse();

        QueryWrapper<GuestRoom> queueWrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(roomRequest.getRoomNum())){
            queueWrapper.like("room_num",roomRequest.getRoomNum());
        }
        if(StringUtils.isNotEmpty(roomRequest.getRoomName())){
            queueWrapper.like("room_name",roomRequest.getRoomName());
        }
        if(roomRequest.getFromApplet() == null || roomRequest.getFromApplet().intValue() == 1){
            queueWrapper.eq("room_shelves",1);//只展示上架的房间
        }
        Page<GuestRoom> pageCondition = new Page<>(roomRequest.getPageNum(),roomRequest.getPageSize());
        final Page<GuestRoom> page = this.page(pageCondition, queueWrapper);
        roomResponse.setPageNum(roomRequest.getPageNum());
        roomResponse.setTotalCount(page.getTotal());
        roomResponse.setPageSize(roomRequest.getPageSize());

        final List<GuestRoom> records = page.getRecords();
        final List<RoomItem> rooms = transform(records);
        roomResponse.setRooms(rooms);
        return roomResponse;
    }

    private List<RoomItem> transform(List<GuestRoom> records){
        return records.stream().map(r -> {
            RoomItem roomItem = new RoomItem();
            roomItem.setSpuId(r.getId());
            roomItem.setPrice(Integer.parseInt(r.getRoomPrice()));
            roomItem.setOriginPrice(Integer.parseInt(r.getRoomOriginPrice()));
            roomItem.setThumb(r.getImageUrl());
            List<String> images = new ArrayList<>();
            images.add(r.getImageUrl());
            roomItem.setImages(images);
            roomItem.setTitle(r.getRoomName());
            roomItem.setStoreId(r.getStoreId());
            roomItem.setRoomStatus(r.getRoomStatus());
            roomItem.setStoreId(r.getStoreId());
            roomItem.setStoreName(r.getStoreName());
            roomItem.setCreateTime(r.getCreateTime());
            roomItem.setType(r.getType());
            roomItem.setTypeString(RoomType.getRoomTypeName(r.getType()));
            roomItem.setRoomNum(r.getRoomNum());
            if(r.getRoomStatus().intValue() == 1){
                roomItem.setRoomStatusString("已预定");
            }else if(r.getRoomStatus().intValue() == 0){
                roomItem.setRoomStatusString("可预定");
            }
            if(r.getRoomShelves().intValue() == 1){
                roomItem.setRoomShelvesString("上架");
            }else if(r.getRoomShelves().intValue() == 2){
                roomItem.setRoomShelvesString("下架");
            }
            if (StringUtils.isNotEmpty(r.getTags())) {
                roomItem.setTags(JSON.parseArray(r.getTags(), String.class));
            }
            return roomItem;
        }).collect(Collectors.toList());
    }

    public RoomItem getRoomDetail(RoomRequest roomRequest){
        List<RoomItem> roomItems = new ArrayList<>();
        RoomItem roomItem = new RoomItem();

        final GuestRoom guestRoom = this.getById(roomRequest.getSpuId());
        List<GuestRoom> guestRooms = new ArrayList<>();
        guestRooms.add(guestRoom);
        roomItems = transform(guestRooms);

        final RoomItem result = roomItems.get(0);

        final HotelRegister hotelRegister = hotelRegisterMapper.getLatestOrderByRoomId(roomRequest.getSpuId());
        if(hotelRegister != null){
            final TOrder tOrder = orderService.getById(hotelRegister.getOrderId());
            if(tOrder != null){
                result.setReserveTime(tOrder.getCreateTime());
                result.setStartDate(tOrder.getStartTime());
                result.setEndDate(tOrder.getEndTime());
            }
        }
        return result;
    }

}
