package com.amswh.iLIMS.project.mapper.lims;
import com.amswh.iLIMS.project.domain.PartyGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;


public interface IPartyGroup extends BaseMapper<PartyGroup> {

    @Select("SELECT P.partyId,phone,email,createTime,fullName,shortName FROM party P,partyGroup PG "
            +"WHERE P.partyId=PG.partyId and P.partyType='root'"
    )
    public Map<String,Object> getRootParty();

}