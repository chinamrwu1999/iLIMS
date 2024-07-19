package com.amswh.iLIMS.framework.security.controller;


import com.amswh.iLIMS.framework.model.AjaxResult;
import com.amswh.iLIMS.framework.security.SecurityUtils;
import com.amswh.iLIMS.framework.security.model.LoginUser;
import com.amswh.iLIMS.framework.security.service.UserLoginService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SysLoginController {

    @Resource
    UserLoginService loginService;

    @PostMapping("/login")
    public AjaxResult login(@RequestBody Map<String,String> input){

        String username=input.get("userName");
        String password=input.get("password");
        if(password==null || password.trim().length()<6){
            return  AjaxResult.error("密码至少6个字符");
        }
        String token=loginService.login(username,password);
        AjaxResult result=AjaxResult.success();
        result.put("token",token);
        LoginUser loginUser=SecurityUtils.getLoginUser();
        System.out.println("logined user is :"+loginUser.getUsername());
        for(String role:loginUser.getRoles()){
            System.out.println(">>>role is "+role);
        }
        return  result;
    }


}
