package com.amswh.iLIMS.framework.security.service;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysPermissionService {
//
//    @Resource
//    private SysRoleService roleService;
//
//    @Resource
//    private SysMenuService menuService;
//    /**
//     * 获取角色数据权限
//     *
//     * @param partyId 用户信息
//     * @return 角色权限信息
//     */
//    public Set<String> getRolePermission(String partyId)
//    {
//        return new HashSet<String>(roleService.getPartyRoles(partyId));
//    }
//
//    /**
//     * 获取菜单数据权限
//     *
//     * @param partyId 用户信息
//     * @return 菜单权限信息
//     */
//    public Set<String> getMenuPermission(String partyId)
//    {
//        Set<String> perms = new HashSet<String>();
//        List<String> roles=roleService.getPartyRoles(partyId);
//        if(roles==null || roles.isEmpty()) return null;
//        if (roles.contains("adin"))  {
//            perms.add("*:*:*");
//        }else{
//          //  perms.addAll(menuService.selectMenuPermsByUserId(user.getUserId()));
//            perms.addAll(menuService.getPartyMenuPermissions(partyId));
//        }
//        return perms;
//    }

    /**
     * 判断用户是否需要修改密码
     */
//    public void editPwd(SysUser user){
//        // 记录最后一次修改密码的时间
//        LambdaQueryWrapper<SysUserPwdLog> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(SysUserPwdLog::getUserId,user.getUserId()).orderByDesc(SysUserPwdLog::getId).last("limit 1");
//        SysUserPwdLog pwdLog = sysUserPwdLogMapper.selectOne(queryWrapper);
//        if(pwdLog==null || DateUtils.getDatePoorDay(new Date(),pwdLog.getCreateTime())>DateUtils.HALF_YEAR){
//            user.setEditPwdFlag(true);
//        }
//    }

}
