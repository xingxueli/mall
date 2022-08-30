package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tencent.wxcloudrun.dao.GuestRoomMapper;
import com.tencent.wxcloudrun.dto.*;
import com.tencent.wxcloudrun.enums.OrderStatusEnum;
import com.tencent.wxcloudrun.model.GuestRoom;
import com.tencent.wxcloudrun.service.GuestRoomService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuestRoomServiceImpl extends ServiceImpl<GuestRoomMapper, GuestRoom> implements GuestRoomService {

    public RoomResponse getRoomList(RoomRequest roomRequest){
        RoomResponse roomResponse = new RoomResponse();

        QueryWrapper<GuestRoom> queueWrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(roomRequest.getRoomNum())){
            queueWrapper.like("room_num",roomRequest.getRoomNum());
        }
        if(StringUtils.isNotEmpty(roomRequest.getRoomName())){
            queueWrapper.like("room_name",roomRequest.getRoomName());
        }
        queueWrapper.eq("room_shelves",1);//只展示上架的房间
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
            roomItem.setPrice(r.getRoomPrice());
            roomItem.setOriginPrice(r.getRoomOriginPrice());
            roomItem.setThumb(r.getImageUrl());
            roomItem.setTitle(r.getRoomName());
            roomItem.setStoreId(r.getStoreId());
            roomItem.setRoomStatus(r.getRoomStatus());
            roomItem.setStoreId(r.getStoreId());
            roomItem.setStoreName(r.getStoreName());
            roomItem.setCreateTime(r.getCreateTime());
            if (StringUtils.isNotEmpty(r.getTags())) {
                roomItem.setTags(JSON.parseArray(r.getTags(), String.class));
            }
            return roomItem;
        }).collect(Collectors.toList());
    }

}
