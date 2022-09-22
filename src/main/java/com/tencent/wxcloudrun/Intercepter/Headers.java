package com.tencent.wxcloudrun.Intercepter;

public class Headers {

    public static final String OPENID = "X-WX-OPENID";//小程序用户 openid

    public static final String APPID = "X-WX-APPID";//小程序 AppID

    public static final String UNIONID = "X-WX-UNIONID";//小程序用户 unionid，并且满足 unionid 获取条件时有

    public static final String FROM_OPENID = "X-WX-FROM-OPENID";//资源复用情况下，小程序用户 openid

    public static final String FROM_APPID = "X-WX-FROM-APPID";//资源复用情况下，使用方小程序 AppID

    public static final String FROM_UNIONID = "X-WX-FROM-UNIONID";//资源复用情况下，小程序用户 unionid，并且满足 unionid 获取条件时有

    public static final String ENV = "X-WX-ENV";//所在云环境 ID

    public static final String SOURCE = "X-WX-SOURCE";//调用来源（本次运行是被什么触发）    eg. wx_web_internet_cloudrun_debug

    public static final String FORWARDED = "X-Forwarded-For";//客户端 IPv4 或IPv6 地址

    public static final String OPENAPI_SEQID = "x-openapi-seqid";//如果云调用请求正常，会在返回的 Header 中附带云调用唯一的 x-openapi-seqid，代表该请求正在使用云调用链路

}
