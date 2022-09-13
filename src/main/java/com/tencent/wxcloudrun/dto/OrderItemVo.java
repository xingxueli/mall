package com.tencent.wxcloudrun.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
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

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date startDate;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date endDate;

    private List<ButtonVo> buttonVOs;
}
