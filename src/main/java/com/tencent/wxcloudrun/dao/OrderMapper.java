package com.tencent.wxcloudrun.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tencent.wxcloudrun.dto.OrderStatusCount;
import com.tencent.wxcloudrun.model.TOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<TOrder> {

    @Select("select order_status as tabType,count(1) as orderNum from t_order group by order_status ")
    List<OrderStatusCount> orderStatusCount();

}
