package com.amswh.iLIMS.framework.security.controller;


import com.amswh.iLIMS.framework.model.AjaxResult;
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
        System.out.println("user login.....");
        String username=input.get("userName");
        String password=input.get("password");
        if(password==null || password.trim().length()<6){
            return  AjaxResult.error("密码至少6个字符");
        }
        loginService.login(username,password);

        return  null;
    }


}
