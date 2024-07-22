package com.amswh.iLIMS.framework.security.controller;
import com.amswh.iLIMS.framework.model.AjaxResult;

import com.amswh.iLIMS.framework.security.model.LoginUser;
import com.amswh.iLIMS.framework.security.model.SysUser;
import com.amswh.iLIMS.framework.security.service.*;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SysLoginController {

    @Resource
    UserLoginService loginService;
    @Resource
    SysUserService userService;
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    SysRoleService roleService;
    @Resource
    JoseJWTService tokenService;

    @Resource
    SysMenuService menuService;

    @PostMapping("/login")
    public AjaxResult login(@RequestBody Map<String,String> input){
        String username=input.get("userName");
        String password=input.get("password");
        if(password==null || password.trim().length()<6){
            return  AjaxResult.error("At least 6 characters required for password");
        }
        String token=loginService.login(username,password);

        SysUser user=userService.getSysUser(username);
        Integer userId=user.getUserId();
        Map<String,Object> dataMap=new HashMap<>();
        dataMap.put("token",token);
        dataMap.put("menuTrue",menuService.getUserMenu(userId));
        Map<String,Object> userInfo=new HashMap<>();
//        if(!"admin".equalsIgnoreCase(username)){
//
//        }
//        dataMap.put("userInf",)

        return  AjaxResult.success(dataMap);
    }


    @PostMapping("/business")
   // @PreAuthorize("@ss.hasRole('admin')")
    public AjaxResult businessTest(@RequestBody Map<String,String> input){
        String orderNo=input.get("orderNo");
        System.out.println("orderNo:"+orderNo);
        return  AjaxResult.success("OK");
    }

    /**
     * 重置用户密码
     * @param input: userId 用户Id 整型主键；
     * @return
     */
    @GetMapping("/login/changePassword")
    public AjaxResult changeUserPassword(@RequestBody Map<String,Object> input){
        Integer userId=(Integer) input.get("userId");
        String oldPassword=input.get("oldPassword").toString();
        String newPassword=input.get("newPassword").toString();
        return  userService.changeMyPassword(userId,oldPassword,newPassword);
    }





}
