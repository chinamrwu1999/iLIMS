package com.amswh.iLIMS.mapper.lims;

import com.amswh.iLIMS.domain.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ISysMenu extends BaseMapper<SysMenu> {

    @Select({"<script>",
        "SELECT M.perms FROM ",
        "FROM UserRole UR LEFT JOIN SysRole R ON R.id=UR.roleId ",
        "LEFT JOIN SysRoleMenu RM ON RM.roleId=UR.roleId ",
        "LEFT JOIN SysMenu M ON SM.id=RM.menuId ",
        "WHERE UR.partyId=#{partyId} ",
        "AND UR.throughDate IS NULL AND R.status=1 ",
        "AND M.status='on'  ",
    "</script>"})
    public List<String> getPartyMenuPermissions(String partyId);

    @Select({"<script>",
        "SELECT id,name,parentId,orderIndex,URL,`type`,",
        "visible,status,perms,icon,createTime",
        "FROM SysMenu M",
        "<if test='name!=null'>",
        "AND name like concat('%',#{name},'%')",
        "</if>",
        "<if test='visible!=null'>",
        "AND visible=#{visible}",
        "</if>",
        "<if test='status!=null'>",
        "AND status=#{status}",
        "</if>",
        "ORDER BY parentId,orderIndex",
    "</script>"})
    public List<SysMenu> selectMenuList(Map<String,Object> inputMap);


    @Select({"<script>",
            "SELECT id,name,parentId,orderIndex,URL,`type`,",
            "visible,status,perms,icon,createTime,orderIndex",
            "FROM SysMenu M",
            "LEFT JOIN SysRoleMenu RM ON M.id=RM.menuId",
            "LEFT JOIN UserRole UR ON RM.roleId=UR.roleId",
            "LEFT JOIN SysRole R ON R.id=UR.roleId",
            "WHERE UR.partyId=#{partyId}",
            "<if test='name!=null'>",
            "AND name like concat('%',#{name},'%')",
            "</if>",
            "<if test='visible!=null'>",
            "AND visible=#{visible}",
            "</if>",
            "<if test='status!=null'>",
            "AND status=#{status}",
            "</if>",
            "ORDER BY parentId,orderIndex",
            "</script>"})
    public List<SysMenu> selectMenuListByUserId(Map<String,Object> inputMap);

    @Select({"<script>",
            "select distinct m.id, m.parentId, m.name, m.URL, m.visible, ",
            "m.status, perms,  m.type, m.icon, m.orderIndex, m.createTime ",
            "FROM  SysMenu m where m.type in ('menu', 'directory') and m.status = 'on' " ,
            "order by m.parentId, m.orderIndex",
    "</script>"})
    public List<SysMenu> selectMenuTreeAll();

    @Select({"<script>",
            "select distinct m.id, m.parentId, m.name, m.URL, m.visible, ",
            "m.status, perms,  m.type, m.icon, m.orderIndex, m.createTime ",
            "FROM  SysMenu m where m.type in ('menu', 'directory') and m.status = 'on' " ,
            "LEFT JOIN SysRoleMenu RM ON M.id=RM.menuId",
            "LEFT JOIN UserRole UR ON RM.roleId=UR.roleId",
            "LEFT JOIN SysRole R ON R.id=UR.roleId",
            "WHERE UR.partyId=#{partyId} ",
            "AND m.type in ('menu', 'directory') and m.status = 'on' ",
           "order by m.parentId, m.orderIndex",
            "</script>"})
    public List<SysMenu> selectMenuTreeByPartyId(String partyId);


    @Select({"<script>",
        "SELECT m.id From SysMenu m",
        "LEFT JOIN SysRoleMenu rm on m.id = rm.menuId",
        "WHERE rm.roleId = #{roleId}",
        "order by m.parentId, m.orderIndex",
     "</script>"})
    public List<Integer> selectMenuListByRoleId(long roleId);


}
