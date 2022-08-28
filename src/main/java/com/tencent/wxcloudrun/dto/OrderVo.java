package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderVo {
    private String saasId = "88888888";
    private String storeId="1000";
    private String storeName="红兴农家院";
    private String uid="88888888205468";
    private String orderId="354021735982432279";
    private String orderNo="354021731671873099";
    private Integer orderStatus=5;
    private String totalAmount="10010";
    private String paymentAmount="20";
    private String discountAmount="9990";
    private String remark="买电风扇送电池吗";
    private Integer cancelType=0;
    private Integer cancelReasonType=0;
    private String cancelReason="";
    private String createTime="1600350829288";
    private List<OrderItemVo> orderItemVOs;
    private PaymentVo paymentVO;
    private List<ButtonVo> buttonVOs;
    private String autoCancelTime="1823652629288";
    private String orderStatusName="待付款";
    private String orderSatusRemark="需支付￥0.20";
}
