package com.tencent.wxcloudrun.enums;

import lombok.Getter;

@Getter
public enum OrderButtonTypes {
    PAY(1, "付款"),
    CANCEL(2, "取消"),
    CONFIRM(3, "确认收货"),
    APPLY_REFUND(4, "申请售后"),
    VIEW_REFUND(5, "查看退款"),
    COMMENT(6, "评价"),
    DELETE(7, "删除订单"),
    DELIVERY(8, "查看物流"),
    REBUY(9, "再次购买"),
    INVITE_GROUPON(11, "邀请好友拼团"),
    UNKNOWN(9999, "Unknown");
    private int code;
    private String name;

    OrderButtonTypes(int code, String sourceTigerName) {
        this.code = code;
        this.name = sourceTigerName;
    }

    public static String getOrderButtonTypesName(int code){
        for (OrderButtonTypes orderButtonTypes : OrderButtonTypes.values()) {
            if(orderButtonTypes.code == code){
                return orderButtonTypes.name;
            }
        }
        return OrderButtonTypes.UNKNOWN.getName();
    }
    
}
