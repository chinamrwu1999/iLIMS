package com.amswh.iLIMS.mapper.lims;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.amswh.iLIMS.domain.PartyGroup;
import org.apache.ibatis.annotations.Select;

import java.util.Map;


public interface IPartyGroup extends BaseMapper<PartyGroup> {

    @Select("SELECT P.partyId,phone,email,createTime,fullName,shortName FROM party P,partyGroup PG "
            +"WHERE P.partyId=PG.partyId and P.partyType='root'"
    )
    public Map<String,Object> getRootParty();

}