package com.amswh.iLIMS.service;

import com.amswh.iLIMS.domain.SysRole;
import com.amswh.iLIMS.mapper.lims.ISysRole;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;

import java.util.List;

public class SysRoleService extends ServiceImpl<ISysRole, SysRole> {

    @Resource
    SysMenuService menuService;

    public List<String> getPartyRoles(String partyId){
          return this.baseMapper.getRolesByPartyId(partyId);
    }


    public List<String> getPartyPermissions(String partyId){

        return menuService.getPartyMenuPermissions(partyId);
    }
}
