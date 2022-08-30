package com.tencent.wxcloudrun.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class HotelRegister implements Serializable {

  @TableId(type = IdType.AUTO)
  private Integer id;
  private Integer guestRoomId;

  private String remark;//入住备注信息
  private Integer orderId;//订单id
  private Integer registerStatus;//1 入住 2 退房
  private String orderNum;//订单号

  private Date createTime;
  private Date startTime;
  private Date endTime;

}
