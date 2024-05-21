package com.amswh.iLIMS.controller;


import com.amswh.iLIMS.domain.Bar;
import com.amswh.iLIMS.partner.rh.RHService;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.amswh.iLIMS.service.*;

import java.util.ArrayList;
import java.util.List;
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
    RHService rhService;

    @Resource
    PartyService partyService;

    @Resource
    BarService barService;


    @PostMapping("/service")
    @Transactional(transactionManager="limsTransactionManager")
    public void TestMe(@RequestBody Map<String,Object> input){
    try {

       // partyService.addPerson(input);
        //partyService.addOrganization(input);
        barService.generateBarCodes("20240521","LDT12",3,"",50);

    }catch (Exception err){
        err.printStackTrace();
    }
   }
}
