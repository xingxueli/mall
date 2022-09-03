package com.tencent.wxcloudrun.enums;

import lombok.Getter;

/**
 */
@Getter
public enum StoreEnum {
    STORE1(1, "红心农家乐"),
    STORE2(2, "红兴农家乐"),
    UNKNOWN(9999, "Unknown");
    private int code;
    private String name;

    StoreEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getStoreName(int code){
        for (StoreEnum storeEnum : StoreEnum.values()) {
            if(storeEnum.code == code){
                return storeEnum.name;
            }
        }
        return StoreEnum.UNKNOWN.getName();
    }
}
