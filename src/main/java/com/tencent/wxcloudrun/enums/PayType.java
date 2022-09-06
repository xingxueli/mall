package com.tencent.wxcloudrun.enums;

import lombok.Getter;

@Getter
public enum PayType {
    ONLINE(1, "线上"),
    OFFLINE(2, "线下"),
    UNKNOWN(9999, "Unknown");
    private int code;
    private String name;

    PayType(int code, String sourceTigerName) {
        this.code = code;
        this.name = sourceTigerName;
    }

    public static String getPayTypeName(int code){
        for (PayType payType : PayType.values()) {
            if(payType.code == code){
                return payType.name;
            }
        }
        return PayType.UNKNOWN.getName();
    }
    
}
