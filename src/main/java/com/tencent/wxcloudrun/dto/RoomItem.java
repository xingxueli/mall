package com.tencent.wxcloudrun.dto;


import lombok.Data;

import java.util.List;

@Data
public class RoomItem {
    private Integer spuId;//roomId
    private Integer storeId;//roomId
    private String thumb;//roomUrl

    private String title;//room名称
    private String price;
    private String originPrice;
    private Integer roomStatus;

    private List<String> tags;
}
