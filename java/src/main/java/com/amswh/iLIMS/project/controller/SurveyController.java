package com.amswh.iLIMS.project.controller;


import com.amswh.iLIMS.framework.model.AjaxResult;
import com.amswh.iLIMS.project.domain.survey.Survey;
import com.amswh.iLIMS.project.service.ConstantsService;
import com.amswh.iLIMS.project.service.SurveyService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SurveyController {

    @Resource
    SurveyService surveyService;
    @Resource
    ConstantsService constants;

    /**
     * 获取问卷调查模板
     * @param code：产品代码 类似：LDT1 LDT2 或条码号
     * @return
     */
    @GetMapping("/survey/fetch/{code}")
    public AjaxResult getSurveyTemplate(@PathVariable String code){
             Survey survey=null;
             if(code.length()>5){   // barCode
                 survey=this.surveyService.getSurvey(code);
             }else {
                 survey = this.surveyService.getSurveyTemplate(code);
             }
           return AjaxResult.success(survey);
    }

    /**保存调查问卷答案
     *
     * @param inputMap：{
     *                “barCode":"条码号123456",
     *                "answers":"直系亲属曾患肠癌#长期便秘#久坐不动"
     *               }
     */

    @PostMapping("/survey/save")
    public AjaxResult saveSurveyAnswers(Map<String,String> inputMap){
          String barCode=inputMap.get("barCode");
          String answers=inputMap.get("answers");
          if(barCode==null || barCode.trim().length()<5 || answers==null || answers.trim().length()<5){
              return AjaxResult.error("输入条件不满足问卷调查");
          }

          if(this.surveyService.insertAnswers(inputMap)>0){
              return AjaxResult.success("问卷调查保存成功");
          }else{
              return AjaxResult.success("问卷调查保存失败");
          }
    }

}
