package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class TOrder implements Serializable {

  private Integer id;

  private String orderNum;
  private String uid;
  private String orderName;//下单人名字
  private String orderMobile;
  private String remark;//订单备注
  private Integer orderStatus;//支付状态 5 待支付 10 已支付待入住 40 已支付已入住 50 已退房  70 已取消(已支付) 80 已取消(未支付)

  private Integer payWay;//支付方式 0 微信支付
  private Integer payType;//支付类型(0=线上、1=线下)
  private Integer cancelReasonType;//手动取消的取消原因字典  1 计划有变 2 时间选错 3 其他原因      cancelReason 为对应文案
  private Integer cancelType;//0 自动取消 1 手动取消 手动取消的时候有取消原因类型

  private Integer totalAmount;//
  private Integer discountAmount;//
  private Integer paymentAmount;//
  private Integer days;//天数

  private Date createTime;//创建订单日期
  private Date payTime;//订单支付时间

  private Date startTime;//预计入住时间
  private Date endTime;//预计退房时间

}
