package com.amswh.iLIMS.controller;


import com.amswh.framework.model.AjaxResult;
import com.amswh.iLIMS.service.ConstantsService;
import com.amswh.iLIMS.service.SurveyService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SurveyController {

    @Resource
    SurveyService surveyService;
    @Resource
    ConstantsService constants;

    /**
     * 获取问卷调查模板
     * @param productId：产品代码 类似：LDT1 LDT2 或样本编号ACK24000001等
     * @return
     */
    @GetMapping("/survey/getTemplate/{productId}")
    public AjaxResult getSurveyTemplate(@PathVariable String productId){
             Map<String,Object> data=new HashMap<>();
             if(productId.length()>5){
                 String code=productId.substring(0,3).toUpperCase();
                 data= this.surveyService.getSurveyTemplate(constants.getProductIdBySampleHeader(code));
             }else{
                 data=this.surveyService.getSurveyTemplate(productId);
             }
             if(!data.isEmpty()) {
                 try {
                     ObjectMapper mapper = new ObjectMapper();
                     JsonNode node = mapper.readTree(data.get("template").toString());
                     data.put("template", mapper.writeValueAsString(node));

                 } catch (Exception err) {
                     err.printStackTrace();
                 }
             }
                 return AjaxResult.success(data);

    }

    /**保存调查问卷答案
     *
     * @param inputMap：{
     *                “barCode":"条码号123456",
     *                "answers":"直系亲属曾患肠癌#长期便秘#久坐不动"
     *               }
     */

    @PostMapping("/survey/saveSurveyAnswers")
    public AjaxResult saveSurveyAnswers(Map<String,Object> inputMap){
          Object obj=inputMap.get("barCode");
          Object obj1=inputMap.get("answers");
          if(obj==null || obj.toString().trim().length()<5 || obj1==null || obj1.toString().trim().length()<5){
              return AjaxResult.error("输入条件不满足问卷调查");
          }
          String barCode=obj.toString().trim();
          String answers=obj1.toString().trim();
          inputMap.put("barCode",barCode);
          inputMap.put("answers",answers);
          if(this.surveyService.insertAnswers(inputMap)>0){
              return AjaxResult.success("问卷调查保存成功");
          }else{
              return AjaxResult.success("问卷调查保存失败");
          }
    }

    /**
     * 获取某个条码对应的调查问卷
     * @param barCode
     * @return
     */

    @GetMapping("/survey/getSurveyAnswers/{barCode}")
    public AjaxResult getSurveyAnswers(@PathVariable  String barCode){
               return AjaxResult.success(this.surveyService.getSurveyAnswers(barCode));
    }


}
