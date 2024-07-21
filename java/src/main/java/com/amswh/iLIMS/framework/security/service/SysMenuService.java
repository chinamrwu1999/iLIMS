package com.amswh.iLIMS.framework.security.service;

import com.amswh.iLIMS.framework.security.mapper.SysMenuMapper;
import com.amswh.iLIMS.framework.security.model.SysMenu;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SysMenuService extends ServiceImpl<SysMenuMapper, SysMenu> {


       public List<SysMenu> getUserMenu(Integer userId){
              List<SysMenu> menuList= this.baseMapper.getUserMenus(userId);
           menuList.sort(Comparator.comparingInt(SysMenu::getParentId));
             return buildMenuTree(menuList);
       }

    private  List<SysMenu> findRootMenus(List<SysMenu> menuList) {
        List<SysMenu> rootMenus = new ArrayList<>();
        Set<Integer> allMenuIds = new HashSet<>();
        for (SysMenu menu : menuList) {
            allMenuIds.add(menu.getMenuId());
        }

        for (SysMenu menu : menuList) {
            if (!allMenuIds.contains(menu.getParentId())) {
                rootMenus.add(menu);
            }
        }

        return rootMenus;
    }
    private  void buildMenuTree(List<SysMenu> menuList, SysMenu parentMenu) {
        for (SysMenu menu : menuList) {
            if (Objects.equals(menu.getParentId(), parentMenu.getMenuId())) {
                if (parentMenu.getChildren() == null) {
                    parentMenu.setChildren(new ArrayList<>());
                }
                parentMenu.getChildren().add(menu);
                buildMenuTree(menuList, menu);
            }
        }
    }
    private  List<SysMenu> buildMenuTree(List<SysMenu> menuList) {
        List<SysMenu> rootMenus = findRootMenus(menuList);
        for (SysMenu rootMenu : rootMenus) {
            buildMenuTree(menuList, rootMenu);
        }
        return rootMenus;
    }

    public List<String> getUserPrivileges(Integer userId){
           return  this.baseMapper.getUserPrivileges(userId);
    }

    public boolean createMenu(SysMenu menu){
          return this.save(menu);
    }

    public List<SysMenu> listAllMenus(){
           return baseMapper.listAllMenus();
    }

}
