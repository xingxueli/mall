package com.tencent.wxcloudrun.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class HotelRegisterRequest{

  private Integer hotelRegisterId;
  private Integer guestRoomId;
  private Integer orderId;

  private String orderNum;//订单编号

  private String remark;//入住、退房备注
  private Integer registerStatus;//1 入住 2 退房

  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
  private Date createTime;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
  private Date actualStartTime;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
  private Date actualEndTime;

}
