package com.tencent.wxcloudrun.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String roomNum;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    private List<String> tags;
}
