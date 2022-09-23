package com.tencent.wxcloudrun.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.Intercepter.HeaderContext;
import com.tencent.wxcloudrun.dto.MessageRequest;
import com.tencent.wxcloudrun.dto.MsgContent;
import com.tencent.wxcloudrun.utils.WeChatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 *  小程序官方文档地址
 * link：https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/template-message/templateMessage.send.html
 *
 * 1、消息推送回复   http://api.weixin.qq.com/cgi-bin/message/custom/send
 * link：https://developers.weixin.qq.com/miniprogram/dev/wxcloudrun/src/development/weixin/callback.html
 * 2、在云托管中使用开放接口服务文档
 * link：https://developers.weixin.qq.com/miniprogram/dev/wxcloudrun/src/guide/weixin/open.html
 * 3、小程序消息订阅
 * link：https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/subscribe-message.html#%E8%AE%A2%E9%98%85%E6%B6%88%E6%81%AF%E8%AF%AD%E9%9F%B3%E6%8F%90%E9%86%92
 *
 */
@RestController
@RequestMapping("message")
public class MessageController {

  final Logger logger;

  public MessageController() {
    this.logger = LoggerFactory.getLogger(MessageController.class);
  }

  /**
   * 两类：
   * 1、可以接收推送消息
   * 2、可以接收订阅消息的触发回调
   * @param messageRequest
   * @return
   */
  @PostMapping(value = "/receiveMsg")
  String sendMsg(@RequestBody MessageRequest messageRequest) {
    //todo 目前还不能接收通，因为需要管理员才能开通，可以让王博申请一个账号来搞这个事情
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

    String openId = HeaderContext.getHeaders().getOpenId();
    logger.info("MessageController sendMessage openId={}",openId);
    _jsonData = "{\n" +
            "  \"touser\": \"o9gQc5HYG_mDc-Rz9VOUnJN8l5uY\",\n" +
            "  \"template_id\": \"wqjsb7aUYKcdjqY-yMZI7FPQd65ds4NwcF_XOqGDQVk\",\n" +
            "  \"page\": \"index\",\n" +
            "  \"miniprogram_state\":\"developer\",\n" +
            "  \"lang\":\"zh_CN\",\n" +
            "  \"data\": {\n" +
            "      \"character_string1\": {\n" +
            "          \"value\": \"339208499\"\n" +
            "      },\n" +
            "      \"thing2\": {\n" +
            "          \"value\": \"2015年01月05日\"\n" +
            "      },\n" +
            "      \"amount3\": {\n" +
            "          \"value\": \"TIT创意园\"\n" +
            "      } ,\n" +
            "      \"thing4\": {\n" +
            "          \"value\": \"TIT创意园\"\n" +
            "      } ,\n" +
            "      \"thing9\": {\n" +
            "          \"value\": \"广州市新港中路397号\"\n" +
            "      }\n" +
            "  }\n" +
            "}\n";
    // 根据小程序穿过来的code想这个url发送请求
    String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send";
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