package com.amswh.iLIMS.project.mapper.lims;
import com.amswh.iLIMS.project.domain.Project;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface IProject extends BaseMapper<Project> {

    @Select("SELECt projectCode,diseaseCode FROM projectDisease")
    List<Map<String,String>> listProjectDiseases();
}