package com.tencent.wxcloudrun.enums;

import lombok.Getter;

@Getter
public enum CheckType {
    UNCHECK(0, "空闲"),
    CHECKIN(1, "入住"),
    CHECKOUT(2, "退房"),
    UNKNOWN(9999, "Unknown");
    private int code;
    private String name;

    CheckType(int code, String sourceTigerName) {
        this.code = code;
        this.name = sourceTigerName;
    }

    public static String getCheckTypeName(int code){
        for (CheckType checkType : CheckType.values()) {
            if(checkType.code == code){
                return checkType.name;
            }
        }
        return CheckType.UNKNOWN.getName();
    }
    
}
