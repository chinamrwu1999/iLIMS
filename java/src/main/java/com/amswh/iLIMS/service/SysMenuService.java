package com.amswh.iLIMS.service;

import com.amswh.iLIMS.domain.SysMenu;
import com.amswh.iLIMS.mapper.lims.ISysMenu;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

public class SysMenuService extends ServiceImpl<ISysMenu, SysMenu> {

    public List<String> getPartyMenuPermissions(String partyId){
        return this.baseMapper.getPartyMenuPermissions(partyId);
    }

}
