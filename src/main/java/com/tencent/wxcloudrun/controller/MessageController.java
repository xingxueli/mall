package com.tencent.wxcloudrun.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.Intercepter.HeaderContext;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.CounterRequest;
import com.tencent.wxcloudrun.dto.MessageRequest;
import com.tencent.wxcloudrun.model.Counter;
import com.tencent.wxcloudrun.model.MsgContent;
import com.tencent.wxcloudrun.service.CounterService;
import com.tencent.wxcloudrun.utils.WeChatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * 1、消息推送回复   http://api.weixin.qq.com/cgi-bin/message/custom/send
 * link：https://developers.weixin.qq.com/miniprogram/dev/wxcloudrun/src/development/weixin/callback.html
 * 2、在云托管中使用开放接口服务文档
 * link：https://developers.weixin.qq.com/miniprogram/dev/wxcloudrun/src/guide/weixin/open.html
 *
 */
@RestController
@RequestMapping("message")
public class MessageController {

  final Logger logger;

  public MessageController() {
    this.logger = LoggerFactory.getLogger(MessageController.class);
  }

  @PostMapping(value = "/receiveMsg")
  String sendMsg(@RequestBody MessageRequest messageRequest) {
    logger.info("MessageController, sendMsg,messageRequest={}", JSON.toJSONString(messageRequest));
    return "success";
  }

  /**
   * 云托管可以不调用
   * @param _jsonData
   */
//  private String getAccessToken(){
//    // 微信小程序ID
//    String appid = "wxf087609e0fb66298";
//    // 微信小程序秘钥
//    String secret = "9d230ef9975532e7192e5993100ee7e9";
//
//    String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;
//    // 发送请求，返回Json字符串
//    String str = WeChatUtil.httpRequest(url, "GET", null);
//    // 转成Json对象 获取openid
//    JSONObject jsonObject = JSONObject.parseObject(str);
//    System.out.println("access_token---"+jsonObject.toJSONString());
//    // 我们需要的openid，在一个小程序中，openid是唯一的
//    String access_token = jsonObject.get("access_token").toString();
//    return access_token;
//  }


  @RequestMapping("/sendMsg")
  @ResponseBody
  public void sendMessage(@RequestBody String _jsonData){
    System.out.println("sendMesg传入参数"+_jsonData);

    // 根据小程序穿过来的code想这个url发送请求
    String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send";
    // 发送请求，返回Json字符串
    String str = WeChatUtil.httpRequest(url, "POST", _jsonData);
    // 转成Json对象 获取openid
    JSONObject jsonObject = JSONObject.parseObject(str);
    System.out.println("jsonObject____"+jsonObject.toJSONString());
    // 我们需要的openid，在一个小程序中，openid是唯一的
  }

  @RequestMapping("/test")
  @ResponseBody
  public String test(@RequestBody MsgContent msgContent){
    final String _jsonData = JSON.toJSONString(msgContent);
    logger.info("test={}", _jsonData);

    // 根据小程序穿过来的code想这个url发送请求
    String url = "https://api.weixin.qq.com/wxa/msg_sec_check";
    // 发送请求，返回Json字符串
    String str = WeChatUtil.httpRequest(url, "POST", _jsonData);
    // 转成Json对象 获取openid
    JSONObject jsonObject = JSONObject.parseObject(str);
    System.out.println("jsonObject____"+jsonObject.toJSONString());
    return str;
  }

}