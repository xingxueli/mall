package com.tencent.wxcloudrun.enums;

import lombok.Getter;

/**
 *
 * 接收的消息类型
 *
 */
@Getter
public enum MessageReceiveType {
    ORDER_PAID(1, "订单支付成功"),
    ORDER_CANCELED(2, "取消订单"),//取消的是已支付订单，如果是未支付订单，取消了也不用给消息，因为和钱无关
    CHECKED_IN(3, "入住办理成功"),
    UNKNOWN(9999, "Unknown");
    private int code;
    private String name;

    MessageReceiveType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getMessageName(int code){
        for (MessageReceiveType messageTypeEnum : MessageReceiveType.values()) {
            if(messageTypeEnum.code == code){
                return messageTypeEnum.name;
            }
        }
        return MessageReceiveType.UNKNOWN.getName();
    }
}
