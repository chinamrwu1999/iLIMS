package com.amswh.MYLIMS.mapper;

import com.amswh.MYLIMS.domain.Patient;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

public interface IPatientMapper extends BaseMapper<Patient> {


    /*
       根据条码查询对应的病人
     */
    @Select("SELECT * FROM Patient WHERE barCode=#{barCode}")
    Patient getPatientByCarCode(String barCode);

}
