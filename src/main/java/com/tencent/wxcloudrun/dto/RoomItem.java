package com.tencent.wxcloudrun.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RoomItem {
    private Integer spuId;//roomId
    private Integer storeId;//roomId
    private String thumb;//roomUrl

    private String title;//room名称
    private String storeName;
    private Integer type;
    private String typeString;
    private Integer price;
    private Integer originPrice;
    private Integer roomStatus;
    private String roomStatusString;
    private String roomShelvesString;
    private String roomNum;
//    private String reserveText;//预定文案

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date reserveTime;//预定实际 即 订单的创建时间

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date startDate;//订单里边的时间

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date endDate;//订单里边的时间

    private List<String> tags;
    private List<String> images;
}
