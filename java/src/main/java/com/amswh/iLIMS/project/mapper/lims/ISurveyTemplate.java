package com.amswh.iLIMS.project.mapper.lims;

import com.amswh.iLIMS.project.domain.SurveyTemplate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

public interface ISurveyTemplate extends BaseMapper<SurveyTemplate> {
    @Select("SELECT productId,template FROM surveyTemplate WHERE productId=#{productId}")
    public Map<String,Object> getSurveyTemplate(String productId);

    @Insert({"<script>",
            "INSERT INTO PatientSurvey(barCode,answers) VALUES(#{barCode},#{answers})",
            "</script>"})
    public int insertSurveyAnswers(Map<String,Object> inputMap);


    @Select("SELECT answers FROM PatientSurvey WHERE barCode=#{barCode}")
    public String getSurveyAnswers(String barCode);
}
