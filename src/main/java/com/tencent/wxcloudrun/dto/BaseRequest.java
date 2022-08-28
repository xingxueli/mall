package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class BaseRequest {
    private long pageSize=1;
    private long pageNum=10;
}
