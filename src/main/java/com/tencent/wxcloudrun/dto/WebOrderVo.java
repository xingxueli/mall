package com.tencent.wxcloudrun.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class WebOrderVo {
    private Integer storeId;
    private Integer guestRoomId;
    private Integer hotelRegisterId;
    private String storeName;
    private String roomName;
    private String roomNum;
    private String uid;
    private String orderId;
    private String orderNum;
    private String orderMobile;
    private Integer orderStatus=5;
    private Integer totalAmount;
    private String paymentAmount;
    private String discountAmount;
    private String remark;
    private Integer cancelType=0;
    private Integer cancelReasonType=0;
    private String cancelReason;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    private String autoCancelTime;
    private String orderStatusName;
    private String orderSatusRemark;
    private String orderName;
    private Integer payType;
    private String payTypeName;
    private Integer payWay;
    private String payWayName;
    private Integer registerStatus;
    private String registerStatusString;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date payTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date paySuccessTime;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date predictStartTime;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date predictEndTime;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date actualStartTime;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date actualEndTime;
}
