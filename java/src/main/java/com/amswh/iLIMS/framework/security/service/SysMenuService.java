package com.amswh.iLIMS.framework.security.service;

import com.amswh.iLIMS.framework.security.mapper.SysMenuMapper;
import com.amswh.iLIMS.framework.security.model.SysMenu;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysMenuService extends ServiceImpl<SysMenuMapper, SysMenu> {


       public List<SysMenu> getUserMenu(long userId){
             List<SysMenu> menus= this.baseMapper.getUserMenus(userId);
             List<SysMenu> topMenus = new ArrayList<>(menus.stream().filter(x -> x.getParentId() == 0).toList());
             for(SysMenu menu:topMenus){
                 findChildren(menus,menu);
             }

             return menus;
       }


       private void findChildren(List<SysMenu> menusList, SysMenu menu ){
            if(menu==null) return ;
            List<SysMenu> children=menusList.stream().filter(x -> x.getParentId().equals(menu.getMenuId())).toList();
            for(SysMenu m:children) {
                findChildren(children, m);
            }
            menu.setChildren(children);
       }

}
