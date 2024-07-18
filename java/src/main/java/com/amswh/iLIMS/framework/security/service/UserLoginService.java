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
    TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    public String login(String username, String password){
        System.out.println(" kkkk login authenticating ........");
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        if(authentication==null){
            System.out.println("authentication failed!");
            return  null;
        }else{
            System.out.println(" kkkk login authenticating success........");
            LoginUser user= (LoginUser)authentication.getPrincipal();
            System.out.println("authenticated user:"+user.getUsername());
            String token=tokenService.createToken(user);
            System.out.println("token:"+token);
            return user.getUsername();
        }

    }


}
