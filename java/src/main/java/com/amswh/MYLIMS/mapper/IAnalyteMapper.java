package com.amswh.MYLIMS.mapper;

import com.amswh.MYLIMS.domain.Analyte;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface IAnalyteMapper extends BaseMapper<Analyte> {

    @Select("SELECT A.analyteCode, A.productCode, B.barCode,name,gender,age,email,phone FROM analyte A "
    +" LEFT JOIN Patient B ON A.barCode=B.barCode AND A.analyteCode=#{analyteId}")
    public Map<String,Object> fetchPatientInfoByAnalyteId(String analyteId);
}
