package com.amswh.iLIMS.project.mapper.lims;

import com.amswh.iLIMS.project.domain.SurveyTemplate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

public interface ISurveyTemplate extends BaseMapper<SurveyTemplate> {
    @Select("SELECT productCode,template FROM surveyTemplate WHERE productCode=#{productId}")
    public Map<String,Object> getSurveyTemplate(String productId);

    @Insert({"<script>",
            "INSERT INTO PatientSurvey(barCode,answers) VALUES(#{barCode},#{answers})",
            "</script>"})
    public int insertSurveyAnswers(Map<String,String> inputMap);


    @Select("SELECT answers FROM PatientSurvey WHERE barCode=#{barCode}")
    public String getSurveyAnswers(String barCode);

    @Select("SELECT answers FROM PatientSurvey WHERE barCode=#{barCode}")
    public String getPatientSurveyAnswers(String barCode);


    /**
     * 根据条码号获取产品问卷模板
     * @param barCode
     * @return
     */
    @Select({"<script>",
            "SELECT template,S.answers FROM PartyBar PB left join SurveyTemplate ST ON PB.productCode=ST.productCode",
            "LEFT JOIN PatientSurvey S ON S.barCode=PB.barCode ",
            "WHERE PB.barCode=#{barCode}",
            "</script>"})
    public Map<String,String> fetch_Survey_and_Template(String barCode);

}
