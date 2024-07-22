package com.amswh.iLIMS.framework.security.mapper;

import com.amswh.iLIMS.framework.security.model.SysRole;
import com.amswh.iLIMS.framework.security.model.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    @Select({"<script>",
            "SELECT R.name FROM SysUser U INNER JOIN SysUserRole UR ON U.userId=UR.userId",
            "INNER JOIN SysRole R ON R.roleId=UR.roleId",
            "WHERE U.userName=#{userName}",
            "</script>"})
    public List<String> getUserRoles(String userName);

    @Select({"<script>",
            "SELECT R.* FROM SysUser U INNER JOIN SysUserRole UR ON U.userId=UR.userId",
            "INNER JOIN SysRole R ON R.roleId=UR.roleId",
            "WHERE U.userId=#{userId}",
            "</script>"})
    public List<SysRole> getUserRolesByUserId(Integer userId);

    /**
     * 获取某个用户所有角色状况。即在全部角色列表中，该用户具有哪些角色
     * @param userId
     * @return
     */

    @Select({"<script>",
            " select A.roleId,A.name roleName,A.chineseName,case B.userId when #{userId} then 'YES' else 'NO' end as checked from SysRole A ",
            "LEFT JOIN (SELECT * FROM SysUserRole where userId=#{userId}) as  B ON A.roleId=B.roleId",
            "</script>"})
    public List<Map<String,Object>> listUserRolesStatusByUserId(Integer userId);


    @Select({"<script>",
            " select A.roleId,A.name roleName,A.chineseName,status FROM SysRole A  order by roleId ",

            "</script>"})
    public List<Map<String,Object>> listAllRoles();


    @Delete("DELETE FROM SysUserRole where userId=#{userId}")
    public int deleteUserRoles(Integer userId);

    @Insert("INSERT INTO SysUserRole(userId,roleId) VALUES( #{userId},#{roleId} ) VALUES")
    @Options(useGeneratedKeys=true, keyProperty="expId")
    int saveExperiment(Map<String,Object> obj);




    @Insert({
            "<script>",
            "INSERT INTO SysUserRole(userId,roleId) VALUES( #{userId},#{roleId} ) VALUES",
            "<foreach collection='list' item='entity' separator=','>",
            "(#{entity.userId}, #{entity.roleId})",
            "</foreach>",
            "</script>"
    })
    void assignUserRoles(List<SysUserRole> entities);



}
