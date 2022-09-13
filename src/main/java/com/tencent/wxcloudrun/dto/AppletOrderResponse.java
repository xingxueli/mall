package com.tencent.wxcloudrun.dto;


import lombok.Data;

import java.util.List;

@Data
public class AppletOrderResponse{
    private String channel;
    private Integer payAmt;
    private String tradeNo;
    private String transactionId;
    private PayInfo payInfo;
}
