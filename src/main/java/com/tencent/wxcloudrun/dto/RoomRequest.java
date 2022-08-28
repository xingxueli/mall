package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoomRequest extends BaseRequest{

  private Integer id;

  private String roomNum;

  private String orderName;//下单人名字
  private String orderMobile;
  private Integer orderStatus;
  private Integer storeId;

  private LocalDateTime startTime;
  private LocalDateTime endTime;

}
