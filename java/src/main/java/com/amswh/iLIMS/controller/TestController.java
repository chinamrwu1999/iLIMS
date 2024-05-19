package com.amswh.iLIMS.controller;


import com.amswh.iLIMS.partner.rh.RHService;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.amswh.iLIMS.service.*;

import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    BiosampleService sampleService;

    @Resource
    AnalyteService analyteService;



    @Resource
    AnalyteprocessService processService;

    @Resource
    RHService rhService;

    @Resource
    PartyService partyService;


    @PostMapping("/service")
    public void TestMe(@RequestBody Map<String,Object> input){
    try {

        partyService.addPerson(input);
    }catch (Exception err){
        err.printStackTrace();
    }
   }
}
