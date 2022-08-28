package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class PaymentVo {

    private Integer payStatus=1;
    private String amount="20";
    private String currency="CNY";
    private Integer payType=0;
    private Integer payWay=0;
    private String payWayName="微信支付";
    private String interactId="4923587";
    private String traceNo="121212";
    private String channelTrxNo="121212";
    private Integer period;
    private Long payTime=1594869391000L;
    private Long paySuccessTime=1594869391287L;
}
