package com.tencent.wxcloudrun.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * //支付状态 5 待支付 10 已支付待入住 40 已支付已入住 50 已退房  70 已取消(已支付) 80 已取消(未支付)
 */
@Getter
public enum OrderStatusEnum {
    TO_BE_PAID(5, "待支付"),
    PAID_AND_CHECK_IN(10, "已支付待入住"),
    PAID_AND_CHECKED_IN(40, "已支付已入住"),
    ALREADY_CHECK_OUT(50, "已退房"),
    CANCELED_PAID(70, "已取消(已支付)"),
    CANCELED_UNPIAD(80, "已取消(未支付)"),
    UNKNOWN(9999, "Unknown");
    private int code;
    private String name;

    OrderStatusEnum(int code, String sourceTigerName) {
        this.code = code;
        this.name = sourceTigerName;
    }

    public static String getOrderStatusName(int code){
        for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()) {
            if(orderStatusEnum.code == code){
                return orderStatusEnum.name;
            }
        }
        return OrderStatusEnum.UNKNOWN.getName();
    }
}
