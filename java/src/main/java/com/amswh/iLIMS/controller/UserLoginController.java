package com.amswh.iLIMS.controller;


import com.amswh.framework.model.AjaxResult;
import com.amswh.framework.security.SysPermissionService;
import com.amswh.framework.system.service.SysMenuService;
import com.amswh.framework.utils.SecurityUtils;
import com.amswh.framework.system.model.LoginUser;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserLoginController {


//    @Resource
//    private SysLoginService loginService;

    @Resource
    private SysMenuService menuService;

    @Resource
    private SysPermissionService permissionService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody Map<String,Object> loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.get("username"), loginBody.get("password"));//, loginBody.get("code"), loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    @PostMapping("/auth/login")
    public AjaxResult loginAuth(@RequestBody LoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        AuthUser result = loginService.loginAuth(loginBody.getUsername(),loginBody.getPassword());
        // 如果为true,将登陆信息缓存至redis
        return ajax.put("user",result);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);

        permissionService.editPwd(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }





       @GetMapping("/getUserMenus")
       public AjaxResult getUserMenus(){
           LoginUser loginUser= SecurityUtils.getLoginUser();
           String partyId=loginUser.getUserInformation("partyId");


           return  null;
       }
}
