package com.tencent.wxcloudrun.controller;

import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.Intercepter.HeaderContext;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.CounterRequest;
import com.tencent.wxcloudrun.dto.MessageRequest;
import com.tencent.wxcloudrun.model.Counter;
import com.tencent.wxcloudrun.service.CounterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class MessageController {

  final Logger logger;

  public MessageController() {
    this.logger = LoggerFactory.getLogger(MessageController.class);
  }

  @PostMapping(value = "/sendMsg")
  String sendMsg(@RequestBody MessageRequest messageRequest) {
    logger.info("MessageController, sendMsg,messageRequest={}", JSON.toJSONString(messageRequest));
    return "success";
  }
  
}