package com.amswh.iLIMS.project.mapper.lims;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.amswh.iLIMS.project.domain.Person;
import org.apache.ibatis.annotations.Select;

import java.util.Map;


public interface IPerson extends BaseMapper<Person> {



    @Select({"<script>",
            "SELECT P.partyId,P.externalId,",
            "PS.name,gender,IDCardType,IDNumber,birthday,PB.age ",
            "FROM PartyBar PB left join party P ON P.partyId=PB.partyId ",
            "LEFT JOIN Person PS ON PS.partyId=PB.partyId ",
            "WHERE  PB.barCode=#{code} or PB.barCode in ",
            "(SELECT barCode FROM analyte WHERE analyteCode=#{code}) ",
            "</script>"})
    public Map<String,Object>  getPatientByBarCode(String code);



}