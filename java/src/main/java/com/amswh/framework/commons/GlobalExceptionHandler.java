package com.amswh.framework.commons;


import com.amswh.framework.model.AjaxResult;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public AjaxResult handleRuntimeException(RuntimeException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常:{}", requestURI, e.getMessage());
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常:{}", requestURI, e.getMessage());
        return AjaxResult.error(e.getMessage());
    }

    @ExceptionHandler(ServiceException.class)
    public AjaxResult handleServiceException(ServiceException e, HttpServletRequest request)
    {
         return AjaxResult.error(e.getMessage());
    }

    @ExceptionHandler(IllegalAccessException.class)
    public AjaxResult handleIllegalAccessException(IllegalAccessException e, HttpServletRequest request)
    {
        return AjaxResult.error("非法访问错误:"+e.getMessage());
    }

    @ExceptionHandler(InstantiationException.class)
    public AjaxResult handleInstantiationException(InstantiationException e, HttpServletRequest request)
    {
        return AjaxResult.error("实例化对象错误："+e.getMessage());
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public AjaxResult handleDuplicatedInsert(SQLIntegrityConstraintViolationException e, HttpServletRequest request)
    {
        return AjaxResult.error("重复添加："+e.getMessage());
    }



}
