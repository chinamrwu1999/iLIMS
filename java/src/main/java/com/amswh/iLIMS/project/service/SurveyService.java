package com.amswh.iLIMS.project.service;

import com.amswh.iLIMS.project.domain.SurveyTemplate;
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

    public int insertAnswers(Map<String,Object> inputMap){
        String barCode=inputMap.get("barCode").toString();
        String answers=inputMap.get("answers").toString();
        ObjectMapper mapper=new ObjectMapper();
        try {


            JsonNode node= mapper.readTree(answers);
            Map<String,Object> mp=new HashMap<>();
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

}
