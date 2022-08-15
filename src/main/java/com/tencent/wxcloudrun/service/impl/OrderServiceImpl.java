package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tencent.wxcloudrun.dao.OrderMapper;
import com.tencent.wxcloudrun.model.TOrder;
import com.tencent.wxcloudrun.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, TOrder> implements OrderService {

}
