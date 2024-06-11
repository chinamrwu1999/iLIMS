package com.amswh.framework.utils;

import com.amswh.framework.model.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());


    public AjaxResult success()
    {
        return AjaxResult.success();
    }

    /**
     * 返回失败消息
     */
    public AjaxResult error()
    {
        return AjaxResult.error();
    }

    /**
     * 返回成功消息
     */
    public AjaxResult success(String message)
    {
        return AjaxResult.success(message);
    }

    /**
     * 返回失败消息
     */
    public AjaxResult error(String message)
    {
        return AjaxResult.error(message);
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows)
    {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected AjaxResult toAjax(boolean result)
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
