package com.amswh.iLIMS.framework.security.controller;

import com.amswh.iLIMS.framework.model.AjaxResult;
import com.amswh.iLIMS.framework.security.model.SysUser;
import com.amswh.iLIMS.framework.security.service.SysUserService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 *   本controller处理系统用户、角色、菜单、权限等功能API接口
 */


@RestController
@RequestMapping("/system")
public class SystemController {

      @Resource
      SysUserService userService;

    /**
     * 新建用户
     * @param input ：userName 字符串，不得少于8个字符  password 密码 不得少于8个字符
     * @return
     */
    @PostMapping("/user/create")
    public AjaxResult createUser(@RequestBody Map<String,String> input){
        String username=input.get("userName");
        String password=input.get("password");
        String message="";
        if(username==null || username.trim().length()<8){
            message+="用户名不得少于8个字符";
        }
        if(password==null || password.trim().length()<8){
            message+="密码不得少于8个字符";
        }
        if(message.length()>0){
            return AjaxResult.error(message);
        }
        SysUser created=userService.createUser(username,password);
        return  AjaxResult.success(created);
    }

    /**
     * 激活或失活用户账号
     * @param input: userId 用户Id 整型主键； status 单个字符 A 激活 D 失活
     * @return
     */
    @PostMapping("/user/changeStatus")
    public AjaxResult changeUserStatus(@RequestBody Map<String,Object> input){
        Integer userId=(Integer) input.get("userId");
        String status=input.get("status").toString();
        SysUser sysUser=this.userService.getById(userId);
        if(sysUser==null){
            return AjaxResult.error("用户不存在!");
        }
        return  AjaxResult.success( this.userService.changeUserStatus(userId,status));
    }

    /**
     * 激活或失活用户账号
     * @param input: userId 用户Id 整型主键； oldPassword 旧密码 newPassword 新密码
     * @return
     */
    @PostMapping("/user/changePassword")
    public AjaxResult changeUserPassword(@RequestBody Map<String,Object> input){
        if(input.get("oldPassword")==null || input.get("newPassword")==null){
            return AjaxResult.error("必需同时提供新密码和旧密码！");
        }
        Integer userId=(Integer) input.get("userId");
        String old=input.get("oldPassword").toString();
        String password=input.get("oldPassword").toString();
        return  userService.changePassword(userId,old,password);
    }

    @PostMapping("/role/create")
    public AjaxResult createRole(@RequestBody Map<String,Object> input){

        return  null;
    }

    @PostMapping("/role/edit")
    public AjaxResult editRole(@RequestBody Map<String,Object> input){

        return  null;
    }

    @PostMapping("/menu/create")
    public AjaxResult createMenu(@RequestBody Map<String,Object> input){

        return  null;
    }

    @PostMapping("/menu/edit")
    public AjaxResult editMenu(@RequestBody Map<String,Object> input){

        return  null;
    }

    @PostMapping("/userRole/create")
    public AjaxResult assignUserRole(@RequestBody Map<String,Object> input){

        return  null;
    }

    @PostMapping("/userRole/edit")
    public AjaxResult changeUserRole(@RequestBody Map<String,Object> input){

        return  null;
    }

    @PostMapping("/permission")
    public AjaxResult authorize(@RequestBody Map<String,Object> input){

        return  null;
    }

    @PostMapping("/myMenus")
    public AjaxResult getMyMenu(){

        return  null;
    }
}
