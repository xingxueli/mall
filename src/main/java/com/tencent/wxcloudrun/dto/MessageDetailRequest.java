package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class MessageDetailRequest {

  private String TemplateId;
  private String SubscribeStatusString;
  private String PopupScene;
  private String MsgId;
  private String ErrorCode;
  private String ErrorStatus;

}
