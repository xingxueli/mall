package com.tencent.wxcloudrun.dto;


import lombok.Data;

import java.util.List;

@Data
public class WebOrderResponse extends BaseResponse{
    private List<WebOrderVo> orders;
}
