package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TOrder implements Serializable {

  private Integer id;

  private String orderNum;

  private String orderName;//下单人名字
  private String orderMobile;
  private String orderStatus;

  private LocalDateTime createTime;

  private LocalDateTime startTime;
  private LocalDateTime endTime;
}
