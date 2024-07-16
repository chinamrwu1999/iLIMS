package com.amswh.framework.security.service;


import com.amswh.framework.security.mapper.SysUserMapper;
import com.amswh.framework.security.model.LoginUser;
import com.amswh.framework.security.model.SysComponent;
import com.amswh.framework.security.model.SysUser;
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

}

