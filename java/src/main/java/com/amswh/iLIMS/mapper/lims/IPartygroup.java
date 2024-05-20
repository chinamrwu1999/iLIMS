package com.amswh.iLIMS.mapper.lims;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.amswh.iLIMS.domain.Partygroup;
import org.apache.ibatis.annotations.Select;

import java.util.Map;


public interface IPartygroup extends BaseMapper<Partygroup> {

    @Select("SELECT P.partyId,phone,email,createTime,fullName,shortName FROM party P,partyGroup PG "
     +"WHERE P.partyId=PG.partyId and P.partyType='root'"
    )
    public Map<String,Object> getRootParty();
}