package com.tencent.wxcloudrun.enums;

import lombok.Getter;

@Getter
public enum CancelType {
    PLAN_CHANGED(1, "计划有变"),
    TIME_ERROR(2, "时间选错"),
    OTHER(3, "其他原因"),
    UNKNOWN(9999, "Unknown");
    private int code;
    private String name;

    CancelType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getCancelTypeName(int code){
        for (CancelType cancelType : CancelType.values()) {
            if(cancelType.code == code){
                return cancelType.name;
            }
        }
        return CancelType.UNKNOWN.getName();
    }
    
}
