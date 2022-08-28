package com.tencent.wxcloudrun.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class OrderRequest extends BaseRequest{

  private Integer id;

  private String orderNum;
  private String cancelReasonType;

  private String orderName;//下单人名字
  private String orderMobile;
  private Integer orderStatus;//5
  private String orderStatusName;//待付款
  private String orderSatusRemark;//需支付￥0.20
  private String remark;//需支付￥0.20

  private Integer totalAmount;//
  private Integer discountAmount;//
  private Integer paymentAmount;//

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date startTime;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date endTime;

}
