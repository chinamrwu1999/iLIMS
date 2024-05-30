package com.amswh.framework.utils;

import com.amswh.framework.model.MapResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());


    public MapResult success()
    {
        return MapResult.success();
    }

    /**
     * 返回失败消息
     */
    public MapResult error()
    {
        return MapResult.error();
    }

    /**
     * 返回成功消息
     */
    public MapResult success(String message)
    {
        return MapResult.success(message);
    }

    /**
     * 返回失败消息
     */
    public MapResult error(String message)
    {
        return MapResult.error(message);
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected MapResult toAjax(int rows)
    {
        return rows > 0 ? MapResult.success() : MapResult.error();
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected MapResult toAjax(boolean result)
    {
        return result ? success() : error();
    }

    /**
     * 获取用户缓存信息
     */
//    public LoginUser getLoginUser()
//    {
//        return SecurityUtils.getLoginUser();
//    }

    /**
     * 获取登录用户id
     */
//    public Long getUserId()
//    {
//        return getLoginUser().getUserId();
//    }

    /**
     * 获取登录部门id
     */
//    public Long getDeptId()
//    {
//        return getLoginUser().getDeptId();
//    }

    /**
     * 获取登录用户名
     */
//    public String getUsername()
//    {
//        return getLoginUser().getUsername();
//    }

}
