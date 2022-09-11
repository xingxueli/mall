package com.tencent.wxcloudrun.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class AppletOrderDetail {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    private String goodsName;
    private String orderMobile;
    private String orderName;
    private String thumb;
    @JsonProperty("spuId")
    private Integer guestRoomId;
    private Integer storeId;
    private Integer price;//单价
    private Integer quantity;//天数

}
