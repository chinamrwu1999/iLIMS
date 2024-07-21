package com.amswh.iLIMS.framework.security.service;


import com.amswh.iLIMS.framework.security.mapper.SysRoleMapper;
import com.amswh.iLIMS.framework.security.model.SysComponent;
import com.amswh.iLIMS.framework.security.model.SysRole;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SysRoleService extends ServiceImpl<SysRoleMapper, SysRole> {

    public Set<String> getUserRoles(String username){
        List<String> roles= this.baseMapper.getUserRoles(username);
        if(roles!=null){
            return new HashSet<>(roles);
        }
        return null;
    }

    public List<SysRole> listUserRoles(Integer userId){
        return  this.baseMapper.getUserRolesByUserId(userId);
    }

    public  boolean createRole(String roleName,String chineseName){
        SysRole role=new SysRole();
        role.setRoleName(roleName);
        role.setChineseName(chineseName);
        return this.save(role);

    }





}
