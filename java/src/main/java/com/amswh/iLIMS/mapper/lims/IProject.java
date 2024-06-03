package com.amswh.iLIMS.mapper.lims;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.amswh.iLIMS.domain.Project;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface IProject extends BaseMapper<Project> {

    @Select("SELECt projectCode,diseaseCode FROM projectDisease")
    List<Map<String,String>> listProjectDiseases();
}