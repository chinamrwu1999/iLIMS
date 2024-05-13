package com.amswh.iLIMS.controller;


import com.amswh.iLIMS.partner.rh.RHService;

import com.amswh.iLIMS.service.impl.MyPartyService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.amswh.iLIMS.service.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    BiosampleService bioSampleServiceservice;

    @Resource
    AnalyteService analyteService;



    @Resource
    AnalyteprocessService processService;

    @Resource
    RHService rhService;

    @Resource
    MyPartyService myPartyService;


    @GetMapping("/service")
    public void TestMe(){
    Map<String,Object> input=new HashMap<>();

    input.put("name","吴志诚1") ;
    input.put("age","45") ;
    input.put("birthday","1975-12-18") ;
    input.put("phone","15172509685") ;
    input.put("gender","M") ;
    input.put("IdCardType","ID") ;
    input.put("IDNumber","510107197512150052") ;
    input.put("height",161) ;
    try {
        myPartyService.addPerson(input);
    }catch (Exception err){
        err.printStackTrace();
    }



    }

    private void create(){



    }

//    private void query(){
//         List<Map<String,Object>> list=this.processService.getBaseMapper().getSampleStatus("AMS24000045301");
//         list.stream().forEach( x -> {
//              for(String key:x.keySet()){
//                  System.out.println(key+":"+x.get(key));
//              }
//         });
//    }
}
