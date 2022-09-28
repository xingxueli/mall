package com.tencent.wxcloudrun.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tencent.wxcloudrun.dto.MessageDetailRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MessageDetail implements Serializable {

  @TableId(type = IdType.AUTO)
  private Long id;

  private Long messageId;

  private String templateId;
  private String subscribeStatusString;
  private String popupScene;
  private String msgId;
  private String errorCode;
  private String errorStatus;

}
