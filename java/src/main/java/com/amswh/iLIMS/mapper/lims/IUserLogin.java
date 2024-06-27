package com.amswh.iLIMS.mapper.lims;

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
       "SELECT PC.partyId,PC.contactType,PC.contact,PS.name,P.externalId empId",
            "FROM partyContact PC",
            "INNER JOIN party P ON P.partyId=PC.partyId",
            "INNER JOIN Person PS ON PS.partyId=PC.partyId",
            "LEFT JOIN PartyRelationship PR ON PR.fromId=PC.partyId",
            "LEFT JOIN party P1 ON P1.partyId=PR.toId",
            "LEFT JOIN PartyRelationship PR1 ON PR1.toId=P1.partyId",
            "LEFT JOIN party P2 ON P2.partyId=PR1.fromId",
            "WHERE PC.contact=#{userId} AND P2.partyType='ROOT'",
    "</script>"})
    public Map<String,String> LoginLIS(String userId);


}
