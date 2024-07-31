package com.amswh.iLIMS.project.controller;


import com.amswh.iLIMS.framework.model.AjaxResult;
import com.amswh.iLIMS.framework.security.model.LoginUser;
import com.amswh.iLIMS.framework.security.model.SysKeys;
import com.amswh.iLIMS.framework.security.model.SysMenu;
import com.amswh.iLIMS.framework.security.service.JoseJWTService;
import com.amswh.iLIMS.framework.security.service.SysKeyService;
import com.amswh.iLIMS.framework.security.service.SysMenuService;
import com.amswh.iLIMS.framework.security.service.SysUserService;
import com.amswh.iLIMS.framework.utils.RSAUtils;
import com.amswh.iLIMS.project.domain.SurveyTemplate;
import com.amswh.iLIMS.project.domain.survey.Choice;
import com.amswh.iLIMS.project.domain.survey.Risk;
import com.amswh.iLIMS.project.domain.survey.Survey;
import com.amswh.iLIMS.project.service.ExpAnalyteService;
import com.amswh.iLIMS.project.service.SurveyService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.apache.commons.codec.binary.Base64;
import org.jose4j.jwt.JwtClaims;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyPair;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/test")
public  class TestController {

   @Resource
   ExpAnalyteService service;

   @Resource
   SysMenuService menuService;

   @Resource
    JoseJWTService tokenService;

   @Resource
    SurveyService surveyService;

   @Resource
   SysKeyService keyService;

   @Resource
    SysUserService userService;


   // @PostMapping("/service")
    @GetMapping("/hello")
    @Transactional(transactionManager="limsTransactionManager")
    public AjaxResult TestMe(){
    try {
        System.out.println("calling test hello........");
        //return AjaxResult.success(menuTreeTest());
        //RSAKeyTest();
      // UserTest();
      // return AjaxResult.success(InsertJSONSurveyTemplate());
        return AjaxResult.success(this.fetchSurvey());
       // this.fetchSurvey();
       // return null;

    }catch (Exception err){
        err.printStackTrace();
    }
    return  AjaxResult.error("发现错误哦");
   }

   private  List<SysMenu> menuTreeTest(){
       //return menuService.getUserMenu(1);
       return null;
   }

   private void RSAKeyTest(){
       try {
           KeyPair pair= RSAUtils.generateKeyPair();
          // RSAUtils.saveKeyForEncodedBase64(pair.getPrivate(),new File("E:/private_key.txt"));
          // RSAUtils.saveKeyForEncodedBase64(pair.getPublic(),new File("E:/public_key.txt"));

           Key privateKey=pair.getPrivate();
           Key publicKey=pair.getPublic();
           byte[] encBytes = privateKey.getEncoded();
           String encBase64 = Base64.encodeBase64String(encBytes);
           byte[] encBytes1 = publicKey.getEncoded();
           String encBase641 = Base64.encodeBase64String(encBytes1);

           SysKeys sysKey=new SysKeys();
           sysKey.setId("AMS");
           sysKey.setPrivateKey(encBase64);
           sysKey.setPublicKey(encBase641);
           this.keyService.save(sysKey);



       } catch (Exception e) {
           throw new RuntimeException(e);
       }

   }


   private void UserTest(){
        userService.createUser("guess","guess123");
   }


   private SurveyTemplate InsertJSONSurveyTemplate(){
       String fileName = "E:/iLIMS/db/ACK_survey.json";

       try {
           List<String> lines = Files.readAllLines(Paths.get(fileName));
           StringBuilder jsonText= new StringBuilder();
           for (String line : lines) {
               jsonText.append(line.trim());
           }
           //ObjectMapper objectMapper = new ObjectMapper();
          // JsonNode jsonNode = objectMapper.readTree(jsonText.toString());
           SurveyTemplate template=new SurveyTemplate();
           template.setProductCode("ACK");
           template.setTemplate(jsonText.toString());
           surveyService.addNewTemplate(template);
           return template;
       } catch (IOException e) {
           e.printStackTrace();
       }
       return null;
   }

   private Survey fetchSurvey(){
        List<String> answers=this.surveyService.getSurveyAnswers("ACK24004567");
        answers.forEach( x -> System.out.println("answer:"+x));
        Map<String,Object> mp=surveyService.getSurveyTemplate("ACK");
        String template=mp.get("template").toString();
        System.out.println("\n\n\template:\n"+template);

        try {
            System.out.println("\n\n\n=========================================================================>\n");
            ObjectMapper objectMapper = new ObjectMapper();
            Survey survey = objectMapper.readValue(template, Survey.class);
            System.out.println(survey.getTitle());
            for(Risk risk:survey.getRisks()){
                  System.out.println(risk.getName());
                  List<Choice> choices=risk.getChoices();
                  for(Choice choice:choices){
                     for(String answer:answers){
                         if(answer.equals(choice.getText())){
                             choice.setChoice(Boolean.TRUE);
                             continue;
                         }
                     }
                  }
            }
         return  survey;
        }catch (Exception err){
            err.printStackTrace();
        }


        return null;
   }
}
