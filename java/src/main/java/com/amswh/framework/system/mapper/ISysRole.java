package com.amswh.framework.system.mapper;

import com.amswh.framework.system.model.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ISysRole extends BaseMapper<SysRole> {



    @Select({"<script>",
    "SELECT SR.roleKey FROM  UserRole UR",
    "LEFT JOIN SysRole SR ON SR.id=UR.roleId ",
    "WHERE UR.partyId=#{partyId} ",
    "AND UR.throughDate IS NULL AND SR.status=1 ",
    "</script>"})
    public List<String> getRolesByPartyId(String partyId);



}
