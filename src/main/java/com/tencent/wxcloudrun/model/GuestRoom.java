package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class GuestRoom implements Serializable {

  private Integer id;
  private Integer storeId;

  private String roomPrice;
  private String roomOriginPrice;

  private Integer roomStatus;
  private String roomName;
  private String roomNum;
  private String imageUrl;
  private String tags;
  private Integer type;

  private LocalDateTime createTime;
  private LocalDateTime updateTime;

}
