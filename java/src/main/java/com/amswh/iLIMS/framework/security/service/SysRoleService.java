package com.amswh.iLIMS.framework.security.service;


import com.amswh.iLIMS.framework.security.mapper.SysRoleMapper;
import com.amswh.iLIMS.framework.security.model.SysRole;
import com.amswh.iLIMS.framework.security.model.SysUserRole;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SysRoleService extends ServiceImpl<SysRoleMapper, SysRole> {

    @Resource
    SysUserRoleMapper userRoleMapper;
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

    public List<Map<String,Object>> listUserRolesStatusByUserId(Integer userId){
        return  baseMapper.listUserRolesStatusByUserId(userId);
    }


    public List<Map<String,Object>> listAllRoles(){
        return  baseMapper.listAllRoles();
    }

    @Transactional
    public boolean updateUserRoles(Integer userId,List<Integer> roles){
            List<SysUserRole> userRoles=new ArrayList<>();
            for(Integer role:roles){
                SysUserRole obj=new SysUserRole();
                obj.setUserId(userId);
                obj.setRoleId(role);
                userRoles.add(obj);
            }
            this.baseMapper.deleteUserRoles(userId);
            if(!userRoles.isEmpty()){
                this.baseMapper.assignUserRoles(userRoles);
               // return true;
            }
            return true;

    }

}
