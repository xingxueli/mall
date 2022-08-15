package com.tencent.wxcloudrun.enums;

import lombok.Getter;

@Getter
public enum RoomType {
    SINGLE_BIG_ROOM(1, "单间大床房"),
    FAMILY_ROOM(2, "家庭房1孩"),
    FAMILY_DOUBLE_ROOM(3, "家庭房2孩"),
    DOUBLE_STANDAND_ROOM(4, "双人标准间"),
    THIRD_SINGLE_ROOM(5, "三人间"),
    FOUR_MAHJONG_ROOM(6, "4人间麻将房"),
    DOUBLE_BIG_ROOM(7, "双大床房"),
    UNKNOWN(9999, "Unknown");
    private int code;
    private String name;

    RoomType(int code, String sourceTigerName) {
        this.code = code;
        this.name = sourceTigerName;
    }
    
}
