package com.amswh.iLIMS.controller;


import com.amswh.framework.model.AjaxResult;
import com.amswh.iLIMS.service.SurveyService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SurveyController {

    @Resource
    SurveyService surveyService;

    /**
     * 获取问卷调查模板
     * @param productId：产品代码 类似：LDT1 LDT2 等
     * @return
     */
    @GetMapping("/survey/template/{productId}")
    public AjaxResult getSurveyTemplate(@PathVariable String productId){

    }
}
