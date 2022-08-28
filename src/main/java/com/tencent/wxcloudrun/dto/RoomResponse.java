package com.tencent.wxcloudrun.dto;


import lombok.Data;

import java.util.List;

@Data
public class RoomResponse extends BaseResponse{
    private List<RoomItem> rooms;
}
