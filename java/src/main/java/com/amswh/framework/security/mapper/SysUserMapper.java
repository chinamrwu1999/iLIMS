package com.amswh.framework.security.mapper;

import com.amswh.framework.security.model.SysComponent;
import com.amswh.framework.security.model.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("SELECT * FROM SysUser WHERE userName=#{userName}")
    public SysUser getUser(String userName);

    @Select({"<script>",
            "SELECT C.* FROM SysComponent C,SysRolePrivilege P,SysUserRole UR,SysUser U",
            "WHERE C.componentId=P.componentId AND P.roleId=UR.roleId AND UR.userId=U.userId",
            "AND U.userName=#{userName}",
            "</script>"})
    public List<SysComponent> getUserPrivileges(String userName);
}
