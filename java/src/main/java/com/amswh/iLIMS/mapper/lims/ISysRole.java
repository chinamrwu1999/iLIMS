package com.amswh.iLIMS.mapper.lims;

import com.amswh.iLIMS.domain.SysMenu;
import com.amswh.iLIMS.domain.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ISysRole extends BaseMapper<SysRole> {



    @Select({"<script>",
    "SELECT SR.roleKey FROM  UserRole UR",
    "LEFT JOIN SysRole SR ON SR.id=UR.roleId ",
    "WHERE UR.partyId=#{partyId} ",
    "AND UR.throughDate IS NULL AND SR.status=1 ",
    "</script>"})
    public List<String> getRolesByPartyId(String partyId);

}
