package com.tencent.wxcloudrun.dto;

import lombok.Data;

/**
 *                 specifications: [
 *                   {
 *                     specTitle: '颜色',
 *                     specValue: '经典白',
 *                   },
 *                   {
 *                     specTitle: '类型',
 *                     specValue: '经典套装',
 *                   },
 *                 ],
 */
@Data
public class SpecificationVO {
    private String specTitle;
    private String specValue;
}
