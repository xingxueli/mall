package com.tencent.wxcloudrun.Intercepter;

import com.tencent.wxcloudrun.config.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Object handleValidationException(HttpServletRequest request, MethodArgumentNotValidException e) {
        //参数校验异常
        return ApiResponse.error(e.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    @ResponseBody
    public Object handleIllegalException(HttpServletRequest request, Exception e) {
        //非法参数等异常
        return ApiResponse.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleOtherException(HttpServletRequest request, Exception e) {
        //只有这种错误信息不明的情况才会打印堆栈信息
        e.printStackTrace();
        return ApiResponse.error(e.getMessage());
    }
}
