package com.amswh.iLIMS.mapper.lims;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.amswh.iLIMS.domain.Person;
import org.apache.ibatis.annotations.Select;

import java.util.Map;


public interface IPerson extends BaseMapper<Person> {

    @Select("SELECT A.*,B.* FROM party A,Person B,PartyBar PB "+
    "WHERE A.partyId=B.partyId AND A.partyId=PB.partyId  "+
    "AND PB.barCode=#{barCode}"
    )
    public Map<String,Object> getPatientByBarCode(String barCode);
}