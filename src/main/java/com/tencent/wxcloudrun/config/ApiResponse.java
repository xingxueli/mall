package com.tencent.wxcloudrun.config;

import lombok.Data;

import java.util.HashMap;

@Data
public final class ApiResponse {

  private String code;
  private String errorMsg;
  private Object data;

  private ApiResponse(String code, String errorMsg, Object data) {
    this.code = code;
    this.errorMsg = errorMsg;
    this.data = data;
  }
  
  public static ApiResponse ok() {
    return new ApiResponse("Success", "", new HashMap<>());
  }

  public static ApiResponse ok(Object data) {
    return new ApiResponse("Success", "", data);
  }

  public static ApiResponse error(String errorMsg) {
    return new ApiResponse("Failed", errorMsg, new HashMap<>());
  }
}
