package com.tencent.wxcloudrun.dto;

import com.tencent.wxcloudrun.utils.ConstantUtil;
import lombok.Data;

import java.util.HashMap;

/**
 *
 *  *     _jsonData = "{\n" +
 *  *             "  \"touser\": \"o9gQc5HYG_mDc-Rz9VOUnJN8l5uY\",\n" +
 *  *             "  \"template_id\": \"wqjsb7aUYKcdjqY-yMZI7FPQd65ds4NwcF_XOqGDQVk\",\n" +
 *  *             "  \"page\": \"index\",\n" +
 *  *             "  \"miniprogram_state\":\"developer\",\n" +
 *  *             "  \"lang\":\"zh_CN\",\n" +
 *  *             "  \"data\": {\n" +
 *  *             "      \"thing3\": {\n" +
 *  *             "          \"value\": \"339208499\"\n" +
 *  *             "      },\n" +
 *  *             "      \"thing6\": {\n" +
 *  *             "          \"value\": \"2015年01月05日\"\n" +
 *  *             "      },\n" +
 *  *             "      \"phone_number7\": {\n" +
 *  *             "          \"value\": \"20.5元\"\n" +
 *  *             "      } ,\n" +
 *  *             "      \"time8\": {\n" +
 *  *             "          \"value\": \"TIT创意园\"\n" +
 *  *             "      }\n" +
 *  *             "  }\n" +
 *  *             "}\n";
 *
 *
 *                房间入住
 */
@Data
public class Template3 {
    private String touser;//OPENID
    private String template_id = "iWEjfRGKgZBn_Wr1QMB79CtXBrRA-L3lxhDidN2ouQQ";
    private String page = "pages/order/order-list/index";//index
    private String miniprogram_state = ConstantUtil.MINIPROGRAM_STATE;
    private String lang = "zh_CN";//
    private HashMap<String,ValueDetail> data;//
}
