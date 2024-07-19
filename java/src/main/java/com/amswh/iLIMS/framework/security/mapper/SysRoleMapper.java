package com.amswh.iLIMS.framework.security.mapper;

import com.amswh.iLIMS.framework.security.model.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    @Select({"<script>",
            "SELECT R.name FROM SysUser U INNER JOIN SysUserRole UR ON U.userId=UR.userId",
            "INNER JOIN SysRole R ON R.roleId=UR.roleId",
            "WHERE U.userName=#{userName}",
            "</script>"})
    public List<String> getUserRoles(String userName);

}
