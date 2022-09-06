package com.tencent.wxcloudrun.enums;

import lombok.Getter;

@Getter
public enum RoomStatus {
    UNRESERVED(0, "未预定"),
    RESERVED(1, "已预定"),
    UNKNOWN(9999, "Unknown");
    private int code;
    private String name;

    RoomStatus(int code, String sourceTigerName) {
        this.code = code;
        this.name = sourceTigerName;
    }

    public static String getCheckTypeName(int code){
        for (RoomStatus checkType : RoomStatus.values()) {
            if(checkType.code == code){
                return checkType.name;
            }
        }
        return RoomStatus.UNKNOWN.getName();
    }
    
}
