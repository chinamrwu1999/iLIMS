package com.amswh.iLIMS.framework.security;


import com.amswh.iLIMS.framework.commons.HttpStatus;
import com.amswh.iLIMS.framework.commons.ServiceException;

import com.amswh.iLIMS.framework.security.model.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * 安全服务工具类
 *
 * @author ruoyi
 */
public class SecurityUtils
{

    public static LoginUser getLoginUser()
    {
        try
        {
            Object obj= getAuthentication().getPrincipal();
            System.out.println("user detail :username is "+obj);
          //  UserDetails details=(UserDetails) ;

            return (LoginUser) obj;
        }
        catch (Exception e)
        {
            System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n");
            e.printStackTrace();
            System.out.println("\n\n");
            throw new ServiceException("获取用户信息异常", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication()
    {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword 真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 是否为管理员
     *
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }
}