package com.amswh.iLIMS.mapper.lims;

import com.amswh.iLIMS.domain.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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


}
