package com.amswh.iLIMS.mapper.lims;
import com.amswh.iLIMS.domain.PartyRelationship;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.amswh.iLIMS.domain.Party;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface IParty extends BaseMapper<Party> {

     @Select("SELECT PG.*,P.phone,P.email FROM partyGroup PG left join Party P ON P.partyId=PG.partyId "+
     "WHERE P.externalId=#{externalId}")
     public Map<String,Object> findPartyByExternalId(String externalId);


     @Insert({
             "<script>",
             "INSERT INTO partyRelationship(fromId,toId,typeId,throughDate) VALUES",
             "<foreach collection='list' item='entity' separator=','>",
             "(#{entity.fromId}, #{entity.toId},#{entity.typeId},#{entity.throughDate})",
             "</foreach>",
             "</script>"
     })
     public int insertPartyRelationships(List<PartyRelationship> list);

     @Select({"<script>",
             "SELECT P.*,PS.*,PC.* FROM party P inner join Person PS  ON P.partyId=PS.partyId ",
             "LEFT JOIN PartyContact PC ON PC.partyId=PS.partyId ",
             "WHERE name=#{name} ",
             "<if test='phone !=null'>",
             "AND PC.contactType='phone' AND PC.contact=#{phone}",
             "</if>",
             "<if test='gender !=null'>",
             "AND gender=#{gender}",
             "</if>",
             "<if test='IDNumber !=null'>",
             "AND IDNumber=#{IDNumber}",
             "</if>",
             "<if test='email !=null'>",
             "AND PC.contactType='email' AND PC.contact=#{email}",
             "</if>",
             "<if test='mobile !=null'>",
             "AND PC.contactType='mobile' AND PC.contact=#{mobile}",
             "</if>",
             "<if test='openId !=null'>",
             "AND PC.contact=#{openId}",
             "</if>",
             "</script>"
     })
     public List<Map<String,Object>> existPerson(Map<String,Object> inputMap);

     @Select({"<script>",
      "SELECT PS.partyId,PS.name,PS.gender,PS.birthday,PS.IdCardType,PS.IDNumber  FROM Person P ",
             "LEFT JOIN PartyContact PC ON PS.partyId=PC.partyId ",
             "WHERE PC.contact=#{contact}",
      "</script>"
     })
     public List<Map<String,Object>> getPersonInfByContact(String contact);
}