package com.amswh.iLIMS.controller;


import com.amswh.framework.model.AjaxResult;
import com.amswh.framework.security.service.SysPermissionService;
import com.amswh.framework.system.model.Constants;
import com.amswh.framework.system.model.SysMenu;
import com.amswh.framework.system.service.SysLoginService;
import com.amswh.framework.system.service.SysMenuService;
import com.amswh.framework.utils.SecurityUtils;
import com.amswh.iLIMS.service.PartyService;
import com.amswh.iLIMS.service.PersonService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserLoginController {


    @Resource
    private SysLoginService loginService;

    @Resource
    private SysMenuService menuService;

    @Resource
    private SysPermissionService permissionService;

    @Resource
    PartyService partyService;

    @Resource
    PersonService personService;

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
        String token = loginService.login(loginBody.get("username").toString(), loginBody.get("password").toString());//, loginBody.get("code"), loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 登录后获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/get/LoginInfo")
    public AjaxResult getInfo()
    {
        LoginUser loginUser=SecurityUtils.getLoginUser();
        Map<String, String> userInfo = loginUser.getUserInfo();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(loginUser.getUsername());
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(loginUser.getUsername());

       // permissionService.editPwd(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", userInfo);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 登录后获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("/getLoginRouters")
    public AjaxResult getRouters()
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(loginUser.getUsername());
        return AjaxResult.success(menuService.buildMenus(menus));
    }
       @GetMapping("/getLoginMenus")
       public AjaxResult getUserMenus(){
           LoginUser loginUser= SecurityUtils.getLoginUser();
           String partyId=loginUser.getUserInformation("partyId");


           return  null;
       }

    /**
     * 手机用户注册
     * @return
     */

    @PostMapping("/register/mobile")
    public AjaxResult registerNewMobileUser(@RequestHeader String openId,@RequestBody Map<String,Object> userInfo) throws ServerException {

        try {
            userInfo.putIfAbsent("openId",openId);
            partyService.addPerson(userInfo);
            return AjaxResult.success("新用户注册成功！");
        } catch (Exception e) {
            throw new ServerException("手机用户注册错误:"+e.getMessage());
        }
    }
}
