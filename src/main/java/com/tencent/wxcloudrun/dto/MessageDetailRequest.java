package com.tencent.wxcloudrun.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MessageDetailRequest {

  @JsonProperty(value = "TemplateId")
  private String TemplateId;
  @JsonProperty(value = "SubscribeStatusString")
  private String SubscribeStatusString;
  @JsonProperty(value = "PopupScene")
  private String PopupScene;
  @JsonProperty(value = "MsgID")
  private String MsgID;
  @JsonProperty(value = "ErrorCode")
  private String ErrorCode;
  @JsonProperty(value = "ErrorStatus")
  private String ErrorStatus;

}
