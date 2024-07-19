package com.amswh.iLIMS.framework.security.service;

import com.amswh.iLIMS.framework.security.model.LoginUser;
import jakarta.annotation.Resource;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService {
    @Resource
    SysUserService userService;

    @Resource
    SysRoleService roleService;

    @Resource
    JoseJWTService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    public String login(String username, String password){

        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        if(authentication==null){

            return  null;
        }else{

            LoginUser user= (LoginUser)authentication.getPrincipal();
            user.setPermissions(userService.getUserAuthorities(username));
            user.setRoles(roleService.getUserRoles(user.getUsername()));
            return tokenService.createToken(user);
        }

    }


}
