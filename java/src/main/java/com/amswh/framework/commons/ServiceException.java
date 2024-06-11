package com.amswh.framework.commons;

import lombok.Data;


public final class ServiceException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 错误数据
     */
    private Object data;


    private String detailMessage;

    /**
     * 空构造方法，避免反序列化问题
     */
    public ServiceException()
    {
    }

    public ServiceException(String message)
    {
        this.message = message;
    }

    public ServiceException(String message, Integer code)
    {
        this.message = message;
        this.code = code;
    }


    public ServiceException(String message, Integer code,Object data)
    {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public String getDetailMessage()
    {
        return detailMessage;
    }

    public String getMessage()
    {
        return message;
    }

    public Integer getCode()
    {
        return code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ServiceException setMessage(String message)
    {
        this.message = message;
        return this;
    }

    public ServiceException setDetailMessage(String detailMessage)
    {
        this.detailMessage = detailMessage;
        return this;
    }
}
