package com.tencent.wxcloudrun.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static final DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter orderFormatter= DateTimeFormatter.ofPattern("yyMMddHHmmss");
    public static final DateTimeFormatter dateFormatter= DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String formatDate(LocalDate localDate){
        return dateFormatter.format(localDate);
    }

    public static String formatDateTime(LocalDateTime localDateTime){
        return dateTimeFormatter.format(localDateTime);
    }

    public static String formatOrderDateTime(LocalDateTime localDateTime){
        return orderFormatter.format(localDateTime);
    }
}
