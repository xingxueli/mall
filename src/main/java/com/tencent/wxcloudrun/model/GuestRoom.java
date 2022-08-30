package com.tencent.wxcloudrun.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class GuestRoom implements Serializable {

  @TableId(type = IdType.AUTO)
  private Integer id;
  private Integer storeId;
  private String storeName;

  private String roomPrice;
  private String roomOriginPrice;

  private Integer roomStatus;
  private Integer roomShelves;
  private String roomName;
  private String roomNum;
  private String imageUrl;
  private String tags;
  private Integer type;

  private Date createTime;
  private Date updateTime;

}
