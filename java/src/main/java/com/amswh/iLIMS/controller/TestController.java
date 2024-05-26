package com.amswh.iLIMS.controller;


import com.amswh.iLIMS.partner.service.HYService;

import com.amswh.iLIMS.partner.service.MEGAService;
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
    MEGAService partnerService;

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


    }catch (Exception err){
        err.printStackTrace();
    }
   }
}
