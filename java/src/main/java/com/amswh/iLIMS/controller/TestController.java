package com.amswh.iLIMS.controller;


import com.amswh.iLIMS.partner.service.HYService;

import com.amswh.iLIMS.partner.service.PAJKService;
import com.amswh.iLIMS.partner.service.XNYTService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.amswh.iLIMS.service.*;

import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    BioSampleService sampleService;

    @Resource
    AnalyteService analyteService;



    @Resource
    AnalyteprocessService processService;

    @Resource
    XNYTService partnerService;

    @Resource
    PartyService partyService;

    @Resource
    BarService barService;

    @Resource
    PAJKService pajkService;


   // @PostMapping("/service")
    @GetMapping("/service")
    @Transactional(transactionManager="limsTransactionManager")
    public void TestMe(){
    try {

       // partyService.addPerson(input);
        //partyService.addOrganization(input);
      //  barService.generateBarCodes("20240521","LDT12",3,"",50);
      //  YQ.fetchPatientInfo("10791014221");
//      Map<String,Object> mp=  partnerService.fetchPatientInfo("2024042804251");
//      if(mp!=null) {
//          for (String key : mp.keySet()) {
//              System.out.println(key + ":" + mp.get(key));
//          }
//      }
      //  pajkService.fetchToken();
        pajkService.fetchPatientInfo("8020857862");

    }catch (Exception err){
        err.printStackTrace();
    }
   }
}
