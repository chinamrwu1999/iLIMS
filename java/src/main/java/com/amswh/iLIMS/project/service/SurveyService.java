package com.amswh.iLIMS.project.service;

import com.amswh.iLIMS.project.domain.SurveyTemplate;
import com.amswh.iLIMS.project.domain.survey.Choice;
import com.amswh.iLIMS.project.domain.survey.Risk;
import com.amswh.iLIMS.project.domain.survey.Survey;
import com.amswh.iLIMS.project.mapper.lims.ISurveyTemplate;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SurveyService extends ServiceImpl<ISurveyTemplate, SurveyTemplate> {


    public Map<String,Object> getSurveyTemplate(String productId){
        return this.baseMapper.getSurveyTemplate(productId);
    }

    public int insertAnswers(Map<String,String> inputMap){
        String barCode=inputMap.get("barCode").toString();
        String answers=inputMap.get("answers").toString();
        ObjectMapper mapper=new ObjectMapper();
        try {


            JsonNode node= mapper.readTree(answers);
            Map<String,String> mp=new HashMap<>();
            mp.put("barCode",barCode);
            mp.put("answers",mapper.writeValueAsString(node));
            return this.baseMapper.insertSurveyAnswers(mp);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public List<String> getSurveyAnswers(String barCode){
        List<String> result=new ArrayList<>();
        String answers=this.baseMapper.getSurveyAnswers(barCode);
        if(answers!=null){
            String [] array=answers.split("#");
            Collections.addAll(result, array);
            return result;
        }
        return result;
    }

    public boolean addNewTemplate(SurveyTemplate template){
        return this.save(template);
    }




    public Survey getSurvey(String barCode){

        Map<String,String> mp=baseMapper.fetch_Survey_and_Template(barCode);
        String template=mp.get("template");
        String [] answers=mp.get("answers")!=null?mp.get("answers").split("#"):null;
        ObjectMapper objectMapper = new ObjectMapper();
        Survey survey = null;
        try {
          survey=objectMapper.readValue(template, Survey.class);
           if(answers!=null) {
               for (Risk risk : survey.getRisks()) {
                   List<Choice> choices = risk.getChoices();
                   for (Choice choice : choices) {
                       for (String answer : answers) {
                           if (answer.equals(choice.getText())) {
                               choice.setChoice(Boolean.TRUE);
                               continue;
                           }
                       }
                   }
               }
           }
        }catch (Exception err){
            err.printStackTrace();
        }
        return  survey;
    }


}
