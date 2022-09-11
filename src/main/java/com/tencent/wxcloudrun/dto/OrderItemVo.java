package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderItemVo {
    private String id;
    private String spuId;
    private String skuId;
    private String goodsName;
    private List<SpecificationVO> specifications;
    private String goodsPictureUrl;
    private String originPrice;
    private String actualPrice;
    private Integer buyQuantity=1;
    private String startDate;
    private String endDate;
    private List<ButtonVo> buttonVOs;
}
