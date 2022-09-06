package com.tencent.wxcloudrun.Intercepter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeaderContext {

    /**
     * <p>Constructor for HeaderContext.</p>
     */
    public HeaderContext() {
    }

    /**
     * 子线程可见
     */
    private static final ThreadLocal<CommonHeader> context = new ThreadLocal<>();

    public static void setHeaders(CommonHeader commonHeader) {
        context.set(commonHeader);
    }

    public static CommonHeader getHeaders() {
        CommonHeader header = context.get();
        if (header == null) {
            context.remove();
        }
        return header;
    }

    /**
     * <p>removeHeaders.</p>
     */
    public static void removeHeaders() {
        context.remove();
    }
}
