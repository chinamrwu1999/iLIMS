package com.amswh.iLIMS.framework.security.controller;
import com.amswh.iLIMS.framework.model.AjaxResult;

import com.amswh.iLIMS.framework.security.model.LoginUser;
import com.amswh.iLIMS.framework.security.service.UserLoginService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
            return  AjaxResult.error("password must has at least 6 characters");
        }
        String token=loginService.login(username,password);
        AjaxResult result=AjaxResult.success();
        result.put("token",token);
//        LoginUser loginUser=  (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if(loginUser!=null) {
//            for (String role : loginUser.getRoles()) {
//                System.out.println(">>>role is " + role);
//            }
//        }
        return  result;
    }


    @PostMapping("/business")
    @PreAuthorize("@ss.hasRole('admin')")
    public AjaxResult businessTest(@RequestBody Map<String,String> input){
        String orderNo=input.get("orderNo");
        System.out.println("orderNo:"+orderNo);
        return  AjaxResult.success("OK");
    }





}
