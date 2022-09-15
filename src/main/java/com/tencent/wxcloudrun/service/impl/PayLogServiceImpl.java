package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tencent.wxcloudrun.dao.PayLogMapper;
import com.tencent.wxcloudrun.model.PayLog;
import com.tencent.wxcloudrun.service.PayLogService;
import org.springframework.stereotype.Service;

@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

}
