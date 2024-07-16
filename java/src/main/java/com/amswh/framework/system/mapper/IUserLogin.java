package com.amswh.framework.system.mapper;

import com.amswh.framework.system.model.LoginLog;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

public interface IUserLogin extends BaseMapper<LoginLog> {


    /**
     * 查找系统登录用户的部门号
     * @param partyId
     */

    @Select({"<script>",
            "SELECT PG.partyId deptId,fullName deptName FROM partyGroup PG",
            "left JOIN PartyRelationship PR1 ON PR1.toId=PG.partyId",
            "LEFT JOIN party P1 ON P1.partyId=PR1.fromId",
            "LEFT JOIN PartyRelationship PR2 ON PR2.toId=PG.partyId",
            "LEFT JOIN Party P2 ON P2.partyId=PR2.fromId",
            "WHERE P1.partyId=#{partyId} AND PR1.typeId='member'",
            "AND P2.partyType='ROOT' AND PR2.typeId='OWN'",
    "</script>"})
    public Map<String,String> getDepartment(String partyId);

    /**
     * 受检者用户登录：userId可以是微信openId或手机号
     * @param userId
     */
    /*
       如果输入联系方式，如手机号，可能存在多个party返回的情况
     */
    @Select({"<script>",
            "SELECT distinct P.partyId,PS.name,P.externalId",
            "FROM  Party P",
            "INNER JOIN Person PS ON PS.partyId=PC.partyId",
            "INNER JOIN partyContact PC ON P.partyId=PC.partyId",
            "WHERE PC.contact=#{userId} OR P.externalId=#{userId} ",
            "</script>"})
    public Map<String,String> queryPartyByContactOrEmployeeId(String userId);




    /**
     * 用户登录核验密码
     * @param partyId
     * @return
     */
    @Select("SELECT password FROM User where partyId=#{partyId} ")
    public String getUserPassword(String partyId);



}
