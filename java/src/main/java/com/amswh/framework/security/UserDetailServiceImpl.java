package com.amswh.framework.security;
import com.amswh.framework.commons.ServiceException;
import com.amswh.framework.model.LoginUser;
import com.amswh.iLIMS.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailServiceImpl implements UserDetailsService{

    @Resource
    UserService userService;

    @Resource
    SysPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser user=userService.queryLoginUser(username);
        if(user==null){
            throw new ServiceException("用户登录: 用户 "+username+" 不存在");
        }
        return user;
    }

    public UserDetails createLoginUser(){

    }

}
