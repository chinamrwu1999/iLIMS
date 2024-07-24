package com.amswh.iLIMS.framework.security.service;


import com.amswh.iLIMS.framework.model.AjaxResult;
import com.amswh.iLIMS.framework.security.SecurityUtils;
import com.amswh.iLIMS.framework.security.mapper.SysUserMapper;
import com.amswh.iLIMS.framework.security.model.LoginUser;
import com.amswh.iLIMS.framework.security.model.SysComponent;
import com.amswh.iLIMS.framework.security.model.SysUser;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> implements UserDetailsService {

    @Resource
    SysRoleService roleService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SysUser user=this.baseMapper.getUser(username);
        if(user!=null) {
            LoginUser loginUser=new LoginUser(user.getUsername(),user.getPassword());
            loginUser.setPermissions(this.getUserAuthorities(username));
            //System.out.println("login user returning");
            return  loginUser;
        }else{
            throw new UsernameNotFoundException("用户名 "+username+" 不存在！");
        }
    }

    /**
     * 获取用户授权
     * @param username
     * @return
     */
    public Set<String> getUserAuthorities(String username){


        Set<String> rt=new HashSet<>();
        List<SysComponent> perms=this.baseMapper.getUserPrivileges(username);
        if(perms!=null) {
            rt.addAll( perms.stream().map(SysComponent::getName).collect(Collectors.toSet()));
        }
        Set<String> roles=roleService.getUserRoles(username);
        if(roles!=null){
            for(String role:roles){
                rt.add("ROLE_"+role);
            }
        }
        return  rt;
    }

    /**
     * 创建新用户
     * @param userName
     * @param password
     * @return
     */
    public SysUser createUser(String userName,String password){

        SysUser sysUser=new SysUser();
        sysUser.setUsername(userName);
        sysUser.setPassword(SecurityUtils.encryptPassword(password));
        this.save(sysUser);
        return sysUser;
    }


    /**
     * 改变用户账号的激活或失活状态
     * @param userId
     * @param status
     * @return
     */
    public boolean changeUserStatus(Integer userId,String status){
        SysUser sysUser=this.getById(userId);
        sysUser.setStatus(status);
        return this.updateById(sysUser);
      }

    /**
     * 重置密码。系统管理员重置用户密码为123456
     * @param userId
     * @return
     */
    public AjaxResult resetPassword(Integer userId){
         SysUser sysUser=this.getById(userId);
         if(sysUser==null) return AjaxResult.error("未找到该ID对应的用户账号！");
         sysUser.setPassword(SecurityUtils.encryptPassword("123456"));
         this.updateById(sysUser);
         return  AjaxResult.success("密码成功重置为123456");
    }

    /**
     * 用户修改自己的密码
     * @param userId
     * @param oldPwd
     * @param newPwd
     * @return
     */

    public AjaxResult changeMyPassword(Integer userId, String oldPwd, String newPwd){
        SysUser sysUser=this.getById(userId);
        if(sysUser==null) return AjaxResult.error("未找到该ID对应的用户账号！");
        if(!SecurityUtils.matchesPassword(oldPwd, sysUser.getPassword())){
            return AjaxResult.error("旧密码错误！");
        }
        sysUser.setPassword(SecurityUtils.encryptPassword(newPwd));
        if( this.updateById(sysUser)) {
            return AjaxResult.success("密码修改成功！");
        }else{
            return AjaxResult.error("密码修改失败！");
        }
    }

    public SysUser getSysUser(String userName){
        return baseMapper.getUser(userName);
    }


    public List<SysUser> getPageUsers(Integer pageIndex,Integer pageSize){
        if(pageSize==null || pageSize<=0) pageSize=20;
        if(pageIndex==null || pageIndex<0) pageIndex=0;
        Integer offset=pageIndex*pageSize;
        return this.baseMapper.listUser(pageSize,offset);
    }

    public Integer totalUsers(){
        return  this.baseMapper.countUsers();
    }

}

