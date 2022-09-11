package com.tencent.wxcloudrun.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class AppletOrderRequest{

  private List<AppletOrderDetail> goodsRequestList;
  private List<StoreInfo> storeInfoList;
  private Integer totalAmount;

  private String orderNum;
  private Integer payType;//支付类型(1=线上、2=线下)
  private Integer payWay;//支付方式 1 微信支付  2 支付宝

}
