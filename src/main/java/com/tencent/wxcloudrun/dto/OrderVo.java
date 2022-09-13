package com.tencent.wxcloudrun.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderVo {
    private String saasId;
    private Integer storeId;
    private String storeName;
    private String uid;
    private String orderId;
    private String orderNo;
    private Integer orderStatus=5;
    private String totalAmount;
    private String paymentAmount;
    private String discountAmount;
    private String remark;
    private Integer cancelType=0;
    private Integer cancelReasonType=0;
    private String cancelReason;
    private String createTime;
    private List<OrderItemVo> orderItemVOs;
    private PaymentVo paymentVO;
    private List<ButtonVo> buttonVOs;
    private String autoCancelTime;
    private String orderStatusName;
    private String orderSatusRemark;

    private String orderName;
    private String orderMobile;

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date predictStartTime;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date predictEndTime;
}
