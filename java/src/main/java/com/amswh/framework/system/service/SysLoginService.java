package com.amswh.framework.system.service;


import com.amswh.framework.commons.ServiceException;
import com.amswh.framework.commons.service.RedisCache;
import com.amswh.framework.security.service.TokenService;
import com.amswh.framework.system.model.LoginLog;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 登录校验方法
 *
 * @author ruoyi
 */
@Component
public class SysLoginService
{
    @Resource
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserService userService;
//
//    @Autowired
//    private ISysConfigService configService;
//
//    @Autowired
//    private SysUserMapper sysUserMapper;
//
//    @Autowired
//    private SysUserPwdLogMapper sysUserPwdLogMapper;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
//     * @param code 验证码
//     * @param uuid 唯一标识
     * @return 结果
     */

    public String login(String username, String password)
    {

        Authentication authentication = null;
        try
        {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            // authenticationManager.
        }catch (Exception e)
        {
                System.out.println(username+" login failed:"+e.getMessage());
                throw new ServiceException("用户名密码不匹配");

        }

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        Map<String,String> infMap=loginUser.getUserInfo();
        System.out.println(">>>>>>>>>>>> login user info:");
        for(String key:infMap.keySet()){
            System.out.println(key+":"+infMap.get(key));
        }

        String token=tokenService.createToken(loginUser);
        LoginLog log=new LoginLog();
        log.setUserId(username);
        this.userService.writeLoginLog(log);
        return token;
    }


    public String getCurrentUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userDetails.getUsername(); // Assuming userId is stored in username field
        return userId;


    }

}

