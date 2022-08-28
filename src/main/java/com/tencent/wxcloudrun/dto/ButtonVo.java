package com.tencent.wxcloudrun.dto;

import lombok.Data;

/**
 *       buttonVOs: [
 *         { primary: false, type: 4, name: '申请售后' },
 *         { primary: true, type: 6, name: '评价' },
 *       ],
 */
@Data
public class ButtonVo {
    private Boolean primary;
    private Integer type;
    private String name;
}
