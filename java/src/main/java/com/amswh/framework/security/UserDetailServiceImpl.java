package com.amswh.framework.security;
import com.amswh.framework.commons.ServiceException;
import com.amswh.framework.model.LoginUser;
import com.amswh.iLIMS.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;

public class UserDetailServiceImpl implements UserDetailsService{

    @Resource
    UserService userService;

    @Resource
    SysPermissionService permissionService;

    /**
     * 此方法会被Spring Security 的Authentication对象调用
     * @param username
     * @return UserDetail 对象 ,该object包含用户Id和密码，被Security用来与用户输入的用户Id、密码比较
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser user=userService.queryLoginUser(username);
        if(user==null){
            throw new UsernameNotFoundException("用户登录: 用户 "+username+" 不存在");
        }
        Set<String> perms=permissionService.getMenuPermission(user.getUsername());
        user.setPermissions(perms);
        return user;
    }
}
