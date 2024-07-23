package com.amswh.iLIMS.framework.security.mapper;

import com.amswh.iLIMS.framework.security.model.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {


    @Select({"<script>",
        "SELECT M.*,SC.url FROM SysUser U INNER JOIN SysUserRole UR ON U.userId=UR.userId",
        "LEFT JOIN SysRoleMenu RM ON RM.roleId=UR.roleId ",
        "LEFT JOIN SysMenu M ON M.menuId=RM.menuId ",
            "LEFT JOIN SysMenuComponent MC ON MC.menuId=M.menuId",
            "LEFT JOIN SysComponent SC ON SC.componentId=MC.componentId",
        "WHERE U.username=#{username} order by M.menuId",
     "</script>"})
    public List<SysMenu> getUserMenus(String userName);


    @Select({"<script>",
            "SELECT SC.name FROM SysUser U INNER JOIN SysUserRole UR ON U.userId=UR.userId",
            "LEFT JOIN SysRolePrivilege RP ON RP.roleId=UR.roleId ",
           "LEFT JOIN SysComponent SC ON SC.componentId=RP.componentId",
            "WHERE U.userId=#{userId}",
            "</script>"})
    public List<String> getUserPrivileges(Integer userId);


    @Select({"<script>",
            "SELECT M.*,SC.url FROM SysRole R INNER JOIN SysUserRole UR ON R.roleId=UR.roleId",
            "LEFT JOIN SysRoleMenu RM ON RM.roleId=UR.roleId ",
            "LEFT JOIN SysMenu M ON M.menuId=RM.menuId ",
            "LEFT JOIN SysMenuComponent MC ON MC.menuId=M.menuId",
            "LEFT JOIN SysComponent SC ON SC.componentId=MC.componentId",
            "WHERE R.name='admin' order by M.menuId",
            "</script>"})
    public List<SysMenu> listAllMenus();
}
