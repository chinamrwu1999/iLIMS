package com.amswh.iLIMS.framework.security.service;


import com.amswh.iLIMS.framework.model.AjaxResult;
import com.amswh.iLIMS.framework.security.SecurityUtils;
import com.amswh.iLIMS.framework.security.mapper.SysUserMapper;
import com.amswh.iLIMS.framework.security.model.LoginUser;
import com.amswh.iLIMS.framework.security.model.SysComponent;
import com.amswh.iLIMS.framework.security.model.SysUser;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("querying user:"+username);
        SysUser user=this.baseMapper.getUser(username);
        if(user!=null) {
            System.out.println("found user:"+user.getUsername());

            LoginUser loginUser=new LoginUser(user.getUsername(),user.getPassword());
            loginUser.setPermissions(this.getUserAuthorities(username));
            System.out.println("login user returning");
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
        System.out.println("query permissions....");
        List<SysComponent> perms=this.baseMapper.getUserPrivileges(username);
        if(perms!=null) {
            System.out.println(" found permissions");
            return perms.stream().map(SysComponent::getName).collect(Collectors.toSet());
        }else {
            System.out.println("not found permissions");
            return null;
        }
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
       return   this.save(sysUser);
    }

    public AjaxResult changePassword(Integer userId, String oldPwd, String newPwd){
         SysUser sysUser=this.getById(userId);
         if(sysUser==null) return AjaxResult.error("未找到该ID对应的用户账号！");
         if(!SecurityUtils.matchesPassword(oldPwd, sysUser.getPassword())){
             return AjaxResult.error("旧密码错误！");
         }
         sysUser.setPassword(SecurityUtils.encryptPassword(newPwd));
         this.save(sysUser);
         return  AjaxResult.success("密码修改成功！");
    }

}

