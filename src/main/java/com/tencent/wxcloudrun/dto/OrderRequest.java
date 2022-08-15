package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderRequest {

  private Integer id;

  private String orderNum;

  private String orderName;//下单人名字
  private String orderMobile;
  private String orderStatus;

  private LocalDateTime startTime;
  private LocalDateTime endTime;

}
