package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class PayInfo {
    private String timeStamp;
    private String signType;
    private String nonceStr;
    private String paySign;
    private String packageName;
}
