package com.tencent.wxcloudrun.Intercepter;

import lombok.*;


@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CommonHeader {

    @BusinessHeader(name = Headers.OPENID,required = true)
    private String openId;

    @BusinessHeader(name = Headers.APPID)
    private String appId;

    @BusinessHeader(name = Headers.UNIONID)
    private String unionId;

    @BusinessHeader(name = Headers.ENV)
    private String env;

    @BusinessHeader(name = Headers.SOURCE)
    private String source;

    @BusinessHeader(name = Headers.FORWARDED)
    private String forwarded;

}
