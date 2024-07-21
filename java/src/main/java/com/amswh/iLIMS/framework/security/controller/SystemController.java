package com.amswh.iLIMS.framework.security.controller;

import com.amswh.iLIMS.framework.model.AjaxResult;
import com.amswh.iLIMS.framework.security.model.SysMenu;
import com.amswh.iLIMS.framework.security.model.SysUser;
import com.amswh.iLIMS.framework.security.service.SysMenuService;
import com.amswh.iLIMS.framework.security.service.SysRoleService;
import com.amswh.iLIMS.framework.security.service.SysUserService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 *   本controller处理系统用户、角色、菜单、权限等功能API接口
 */


@RestController
@RequestMapping("/system")
public class SystemController {

        @Resource
        SysUserService userService;

        @Resource
        SysRoleService roleService;

        @Resource
        SysMenuService menuService;

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
        if(username==null || username.trim().length()<6){
            message+="用户名不得少于6个字符";
        }
        if(password==null || password.trim().length()<6){
            message+="密码不得少于6个字符";
        }
        if(!message.isEmpty()){
            return AjaxResult.error(message);
        }
        SysUser created=userService.createUser(username,password);
        created.setPassword("**********");
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
     * 重置用户密码
     * @param  userId 用户Id 整型主键；
     * @return
     */
    @GetMapping("/user/resetPassword/{userId}")
    public AjaxResult resetUserPassword(@PathVariable Integer userId){
      return  userService.resetPassword(userId);
    }
    @PostMapping("/role/create")
    public AjaxResult createRole(@RequestBody Map<String,String> input){
        String roleName=input.get("roleName");
        String chineseName=input.get("chineseName");
        if(roleName==null || roleName.isBlank() || chineseName==null || chineseName.isBlank()){
            return AjaxResult.error("角色英文名称与中文名称都不能空!");
        }
        if(this.roleService.createRole(roleName,chineseName)){
            return AjaxResult.success("角色创建成功！");
        }else{
            return AjaxResult.error("创建角色失败");
        }

    }

    /**
     * 列出用户拥有的角色
     * @param userId
     * @return
     */

    @GetMapping("/userRole/list/{userId}")
    public AjaxResult assignUserRole(@PathVariable Integer userId){
        return AjaxResult.success(this.roleService.listUserRoles(userId));

    }


    @PostMapping("/menu/create")
    public AjaxResult createMenu(@RequestBody Map<String,Object> input){
        Object menuName=input.get("menuName");
        Object chineseName=input.get("chineseName");
        Object parentId=input.get("parentId");
        Object displayIndex=input.get("order");

        if(menuName==null || chineseName==null ){
            return AjaxResult.error("菜单英文名称与中文名称都不能空!");
        }
        SysMenu menu=new SysMenu();
        menu.setName(menuName.toString().trim());
        menu.setLabel(chineseName.toString().trim());
        if(parentId!=null){
            menu.setParentId((Integer) parentId);
        }else{
            menu.setParentId(0);
        }
        if(displayIndex!=null){
            menu.setDisplayIndex((Integer) displayIndex);
        }else{
            menu.setDisplayIndex(0);
        }
        if(this.menuService.createMenu(menu)){
            return AjaxResult.success("菜单创建成功！");
        }else{
            return AjaxResult.error("创建菜单失败");
        }
      //  return  null;
    }

    @GetMapping("/menu/list")
    public AjaxResult  listAllMenu(@RequestBody Map<String,Object> input){
             return  AjaxResult.success(menuService.listAllMenus());
    }



    /**
     * 用户分配角色
     * @param input
     * @return
     */
    @PostMapping("/userRole/update")
    public AjaxResult changeUserRole(@RequestBody Map<String,Object> input){

        return  null;
    }

}
