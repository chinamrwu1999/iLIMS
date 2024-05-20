package com.amswh.iLIMS.controller;


import com.amswh.iLIMS.domain.Bar;
import com.amswh.iLIMS.partner.rh.RHService;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.amswh.iLIMS.service.*;

import java.util.ArrayList;
import java.util.List;
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

    @Resource
    BarService barService;


    @PostMapping("/service")
    public void TestMe(@RequestBody Map<String,Object> input){
    try {

       // partyService.addPerson(input);
        //partyService.addOrganization(input);
        double rd=Math.random()*1000;
        int randomInt=(int)rd;
        List<Bar> bars=new ArrayList<>();
        for(int i=0;i<10;i++){
            Bar bar=new Bar();
            bar.setBatchNo("20250520");
            bar.setProductCode("LDT01");
            bar.setBarCode(String.format("0120240520%3d%6d",randomInt,i));
            bars.add(bar);
        }
        this.barService.saveBatch(bars);
    }catch (Exception err){
        err.printStackTrace();
    }
   }
}
