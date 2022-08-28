package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class BaseResponse {
    private long pageNum;
    private long pageSize;
    private long totalCount;
}
