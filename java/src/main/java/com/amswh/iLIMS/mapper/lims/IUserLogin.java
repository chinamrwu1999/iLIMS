package com.amswh.iLIMS.mapper.lims;

import com.amswh.iLIMS.domain.User;
import com.amswh.iLIMS.domain.UserLogin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

public interface IUserLogin extends BaseMapper<UserLogin> {


    /**
     * LIS用户登录：根据userId查找工号，userId可以是工号、手机号、微信openId
     * @param userId
     */

    @Select({"<script>",
       "SELECT distinct P.partyId,PS.name,P.externalId",
            "FROM Party P  ",
            "INNER JOIN Person PS ON PS.partyId=P.partyId",
            "LEFT JOIN partyContact PC ON P.partyId=PC.partyId",
            "LEFT JOIN PartyRelationship PR ON PR.fromId=PC.partyId",
            "LEFT JOIN party P1 ON P1.partyId=PR.toId",
            "LEFT JOIN PartyRelationship PR1 ON PR1.toId=P1.partyId",
            "LEFT JOIN party P2 ON P2.partyId=PR1.fromId",
            "WHERE (PC.contact=#{userId} AND P2.partyType='ROOT') or P.externalId=#{userId}",
    "</script>"})
    public Map<String,String> LoginLIS(String userId);

    /**
     * 受检者用户登录：userId可以是微信openId或手机号
     * @param userId
     */

    @Select({"<script>",
            "SELECT P.partyId,PS.name,P.externalId",
            "FROM  Party P",
            "INNER JOIN Person PS ON PS.partyId=PC.partyId",
            "INNER JOIN partyContact PC ON P.partyId=PC.partyId",
            "WHERE PC.contact=#{userId} ",
            "</script>"})
    public Map<String,String> LoginByWechat(String userId);


    @Select({"<script>",
            "SELECT partyId deptId,fullName deptName FROM partyGroup PG ",
            "LEFT JOIN PartyRelationship PR ON PG.partyId=PR.toId",
            "WHERE PR.fromId=#{partyId} and PR.typeId='member'",
            "</script>"})
    public Map<String,String> getDepartment(String partyId);


    /**
     * 用户登录核验密码
     * @param partyId
     * @param password
     * @return
     */
    @Select("SELECT partyId,password FROM User where partyId=#{partyId} AND password=md5(#{password})")
    public Map<String,String>  matchPassword(String partyId, String password);



}
