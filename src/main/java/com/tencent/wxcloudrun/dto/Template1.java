package com.tencent.wxcloudrun.dto;

import com.tencent.wxcloudrun.utils.ConstantUtil;
import lombok.Data;

import java.util.HashMap;

/**
 *     _jsonData = "{\n" +
 *             "  \"touser\": \"o9gQc5HYG_mDc-Rz9VOUnJN8l5uY\",\n" +
 *             "  \"template_id\": \"wqjsb7aUYKcdjqY-yMZI7FPQd65ds4NwcF_XOqGDQVk\",\n" +
 *             "  \"page\": \"index\",\n" +
 *             "  \"miniprogram_state\":\"developer\",\n" +
 *             "  \"lang\":\"zh_CN\",\n" +
 *             "  \"data\": {\n" +
 *             "      \"number1\": {\n" +
 *             "          \"value\": \"2333232333232\"\n" +
 *             "      },\n" +
 *             "      \"name2\": {\n" +
 *             "          \"value\": \"张三\"\n" +
 *             "      },\n" +
 *             "      \"date3\": {\n" +
 *             "          \"value\": \"2015年01月05日\"\n" +
 *             "      } ,\n" +
 *             "      \"thing6\": {\n" +
 *             "          \"value\": \"支付了2-301房间\"\n" +
 *             "      } ,\n" +
 *             "      \"amount10\": {\n" +
 *             "          \"value\": \"100元\"\n" +
 *             "      }\n" +
 *             "  }\n" +
 *             "}\n";
 *
 *
 *
 *             订单支付成功
 */
@Data
public class Template1 {
    private String touser;//OPENID
    private String template_id = "qzQx2J_pSwoS3jE8jHQgv2OMX78kOkRLlUQSNnSo0BQ";
    private String page = "pages/order/order-list/index";//index
    private String miniprogram_state = ConstantUtil.MINIPROGRAM_STATE;
    private String lang = "zh_CN";//
    private HashMap<String,ValueDetail> data;//
}
