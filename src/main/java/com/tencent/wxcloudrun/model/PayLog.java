package com.tencent.wxcloudrun.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PayLog implements Serializable {

  @TableId(type = IdType.AUTO)
  private Integer id;
  private String openid;

  private Integer orderId;
  private String tag;//素材名称

  private String transactionId;//微信支付的订单id
  private Integer price;//分
  private Integer totalFee;//支付金额
  private Integer resultStatus;//支付成功状态(1:成功， 0:失败)
  private String resultCode;//支付结果返回
  private String returnCode;//支付回调信息返回码
  private String returnData;//返回信息
  private Date createTime;//真正的下单时间

}
