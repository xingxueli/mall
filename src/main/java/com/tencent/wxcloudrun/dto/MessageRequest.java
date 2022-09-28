package com.tencent.wxcloudrun.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * {
 *   "ToUserName": "gh_919b00572d95", // 小程序/公众号的原始ID，资源复用配置多个时可以区别消息是给谁的
 *   "FromUserName": "oVneZ57wJnV-ObtCiGv26PRrOz2g", // 该小程序/公众号的用户身份openid
 *   "CreateTime": 1651049934, // 消息时间
 *   "MsgType": "text", // 消息类型
 *   "Content": "回复文本", // 消息内容
 *   "MsgId": 23637352235060880 // 唯一消息ID，可能发送多个重复消息，需要注意用此 ID 去重
 * }
 */

@Data
public class MessageRequest {

  @JsonProperty(value = "ToUserName")
  private String ToUserName;
  @JsonProperty(value = "FromUserName")
  private String FromUserName;
  @JsonProperty(value = "CreateTime")
  private Integer CreateTime;
  @JsonProperty(value = "MsgType")
  private String MsgType;
  @JsonProperty(value = "Event")
  private String Event;
  @JsonProperty(value = "List")
  private List<MessageDetailRequest> List;

}
