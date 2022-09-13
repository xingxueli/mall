package com.tencent.wxcloudrun.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class OrderRequest extends BaseRequest{

  private Integer orderId;
  private Integer hotelRegisterId;

  private String orderNum;
  private String cancelReasonType;

  private String orderName;//下单人名字
  private String orderMobile;
  private Integer orderStatus;//5
  private Integer guestRoomId;//可能只是创建了订单，但是创建了订单的时候，已经选定了房间，只是未支付。
  private String orderStatusName;//待付款
  private String orderSatusRemark;//需支付￥0.20
  private String remark;//需支付￥0.20

  private Integer totalAmount;//
  private Integer discountAmount;//
  private Integer paymentAmount;//
  private Integer payType;//支付类型(1=线上、2=线下)
  private Integer payWay;//支付方式 1 微信支付  2 支付宝

  private Integer quantity;//天数

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date predictStartTime;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date predictEndTime;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date actualStartTime;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date actualEndTime;

}
