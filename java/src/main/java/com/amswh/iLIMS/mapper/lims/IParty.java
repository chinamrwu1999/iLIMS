package com.amswh.iLIMS.mapper.lims;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.amswh.iLIMS.domain.Party;
import org.apache.ibatis.annotations.Select;

import java.util.Map;


public interface IParty extends BaseMapper<Party> {

     @Select("SELECT PG.*,P.phone,P.email FROM partyGroup PG left join Party P ON P.partyId=PG.partyId "+
     "WHERE P.externalId=#{externalId}")
     public Map<String,Object> findPartyByExternalId(String externalId);
}