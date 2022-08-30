package com.tencent.wxcloudrun.dto;

import lombok.Data;


@Data
public class RoomRequest extends BaseRequest{

  private Integer id;

  private String roomNum;//客房编号

  private String roomName;//客房名称
  private String imageUrl;//图片地址
  private Integer type;//1、单间大床房 2、家庭房1孩 3、家庭房2孩 4、双人间 5、三人间 6、4人间麻将房 7、双大床房    RoomType
  private Integer storeId;//店id
  private Integer roomShelves;//0 下架  1  上架   默认0
  private String storeName;//店名

  private String roomPrice;//客房价格
  private String roomOriginPrice;//客房划线价格

}
