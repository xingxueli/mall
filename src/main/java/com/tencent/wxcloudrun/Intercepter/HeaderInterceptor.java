package com.tencent.wxcloudrun.Intercepter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Slf4j
public class HeaderInterceptor implements HandlerInterceptor {

    /** {@inheritDoc} */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("utf-8");
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            CommonHeader commonHeader = validateAndGet(request,method);
            HeaderContext.setHeaders(commonHeader);
        }
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    /** {@inheritDoc} */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //一定要在请求结束后调用remove清除当前线程的副本变量值，否则会造成内存泄漏
        HeaderContext.removeHeaders();
    }

    /**
     * 校验并获取header
     * @param request
     * @return
     */
    private CommonHeader validateAndGet(HttpServletRequest request, Method method) throws Exception {
        CommonHeader commonHeader = new CommonHeader();
        Class clazz = commonHeader.getClass();
        Field[] fields= clazz.getDeclaredFields();
        Field.setAccessible(fields,true);
        for(Field field : fields){
            BusinessHeader businessHeader = field.getAnnotation(BusinessHeader.class);
            if(null != businessHeader){
                String name = businessHeader.name();
                String value = request.getHeader(name);
                log.info("name={},value={}",name,value);
                boolean required = businessHeader.required();
                if(null != value){
                    field.set(commonHeader, ConvertUtils.convert(value,field.getType()));
                }else if(required){
                    throw new Exception(" header里有值未传 ");
                }
            }
        }
        return commonHeader;
    }

}
