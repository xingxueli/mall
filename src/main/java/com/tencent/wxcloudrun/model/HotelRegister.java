package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class HotelRegister implements Serializable {

  private Integer id;
  private Integer guestRoomId;

  private String remark;
  private Integer orderId;

  private Date createTime;
  private Date startTime;
  private Date endTime;

}
