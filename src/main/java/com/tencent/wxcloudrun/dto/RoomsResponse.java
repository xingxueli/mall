package com.tencent.wxcloudrun.dto;


import lombok.Data;

import java.util.List;

@Data
public class RoomsResponse {
    private Integer spuId;//roomId
    private String thumb;//roomUrl

    private String title;//room名称
    private String price;
    private String originPrice;

    private List<String> tags;
}
