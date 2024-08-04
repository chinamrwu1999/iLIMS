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
import com.amswh.iLIMS.project.domain.Sequence;
import com.amswh.iLIMS.project.domain.SurveyTemplate;
import com.amswh.iLIMS.project.domain.survey.Choice;
import com.amswh.iLIMS.project.domain.survey.Risk;
import com.amswh.iLIMS.project.domain.survey.Survey;
import com.amswh.iLIMS.project.service.ExpAnalyteService;
import com.amswh.iLIMS.project.service.SeqService;
import com.amswh.iLIMS.project.service.SurveyService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.apache.commons.codec.binary.Base64;
import org.jose4j.jwt.JwtClaims;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyPair;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;


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

   @Resource
    SeqService seqService;

   @Resource
    JdbcClient jdbcClient;



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
      //  return AjaxResult.success(this.fetchSurvey());
       // this.fetchSurvey();
       // SeqserviceTest();
        this.JDBCClientTest();
        return null;

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
        return surveyService.getSurveyTemplate("ACK");

   }

   private void SeqserviceTest(){
           DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
           List<Sequence> sequences=seqService.list();
           sequences.forEach(x -> System.out.println(x.getSeqName()+":"+x.getSeqId()+"\t"+dateTimeFormatter.format(x.getUpdateTime())));

           int size=10;
           for(int i=0;i<size;i++){
               sequences=seqService.list();
               Sequence sq =sequences.stream().filter(x -> x.getSeqName().equalsIgnoreCase("PartnerBar")).findFirst().orElse(null);
               System.out.print(String.format("%02d:%s\t%d\t",i+1,seqService.nextBarId(),sq.getSeqId()));

               if((i+1) % 3 ==0) System.out.println();
           }
       sequences=seqService.list();
       sequences.forEach(x -> System.out.println(x.getSeqName()+":"+x.getSeqId()+"\t"+dateTimeFormatter.format(x.getUpdateTime())));

   }

   private  void JDBCClientTest(){
//        List<Map<String,Object>> rows=this.jdbcClient.sql("SELECT * FROM Patient").query().listOfRows();
//        for(Map<String,Object> row:rows){
//            for(String key:row.keySet()){
//                System.out.print(key+":"+row.get(key)+"\t");
//            }
//            System.out.println();
//        }

       List<Map<String,Object>> rows=this.jdbcClient.sql("SELECT seqName,colName FROM Sequence").query().listOfRows();
       String sql="SELECT max(substr(%s,2,11)) FROM %s";
       String updateSQL="UPDATE sequence set seqId=:seqId WHERE seqName=:seqName";
       for(Map<String,Object> row:rows){
             String strSQL=String.format(sql,row.get("colName"),row.get("seqName"));
             Object obj=jdbcClient.sql(strSQL).query().singleValue();
             Long maxId=0L;
             if(obj!=null){
                  maxId=Long.parseLong(obj.toString());
             }
             jdbcClient.sql(updateSQL).param("seqId",maxId)
                             .param("seqName",row.get("seqName").toString()).update();

       }



   }

}
