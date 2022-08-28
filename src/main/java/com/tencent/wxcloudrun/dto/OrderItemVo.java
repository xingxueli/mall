package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderItemVo {
    private String id="354021736133427225";
    private String spuId="3";
    private String skuId="135696670";
    private String goodsName=
            "腾讯极光盒子4智能网络电视机顶盒6K千兆网络机顶盒4K高分辨率";
    private List<SpecificationVO> specifications;
    private String goodsPictureUrl="https=//cdn-we-retail.ym.tencent.com/tsr/goods/dz-3b.png";
    private String originPrice="0";
    private String actualPrice="9999";
    private Integer buyQuantity=1;
    private String startDate;
    private String endDate;
    private List<ButtonVo> buttonVOs;
}
