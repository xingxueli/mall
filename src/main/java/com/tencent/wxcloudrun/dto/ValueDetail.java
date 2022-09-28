package com.tencent.wxcloudrun.dto;

import lombok.Builder;
import lombok.Data;

/**
 *     _jsonData = "{\n" +
 *             "  \"touser\": \"o9gQc5HYG_mDc-Rz9VOUnJN8l5uY\",\n" +
 *             "  \"template_id\": \"wqjsb7aUYKcdjqY-yMZI7FPQd65ds4NwcF_XOqGDQVk\",\n" +
 *             "  \"page\": \"index\",\n" +
 *             "  \"miniprogram_state\":\"developer\",\n" +
 *             "  \"lang\":\"zh_CN\",\n" +
 *             "  \"data\": {\n" +
 *             "      \"character_string1\": {\n" +
 *             "          \"value\": \"339208499\"\n" +
 *             "      },\n" +
 *             "      \"thing2\": {\n" +
 *             "          \"value\": \"2015年01月05日\"\n" +
 *             "      },\n" +
 *             "      \"amount3\": {\n" +
 *             "          \"value\": \"20.5元\"\n" +
 *             "      } ,\n" +
 *             "      \"thing4\": {\n" +
 *             "          \"value\": \"TIT创意园\"\n" +
 *             "      } ,\n" +
 *             "      \"thing9\": {\n" +
 *             "          \"value\": \"广州市新港中路397号\"\n" +
 *             "      }\n" +
 *             "  }\n" +
 *             "}\n";
 */
@Data
@Builder
public class ValueDetail {
    private String value;
}
