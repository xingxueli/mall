package com.tencent.wxcloudrun.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tencent.wxcloudrun.dto.MessageDetailRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Message implements Serializable {

  @TableId(type = IdType.AUTO)
  private Long id;

  private String toUserName;
  private String fromUserName;
  private Integer createTime;
  private String msgType;
  private String event;

}
