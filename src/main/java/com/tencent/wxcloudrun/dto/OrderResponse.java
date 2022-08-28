package com.tencent.wxcloudrun.dto;


import lombok.Data;

import java.util.List;

@Data
public class OrderResponse extends BaseResponse{
    private List<OrderVo> orders;
}
