package com.amswh.iLIMS.controller;


import com.amswh.iLIMS.partner.service.*;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.amswh.iLIMS.service.*;

import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

   @Resource
//   ADCService service;
   SeqService service;


   // @PostMapping("/service")
    @GetMapping("/hello")
    @Transactional(transactionManager="limsTransactionManager")
    public void TestMe(){
    try {
         // service.fetchPatientInfo("WD2018120529");
        long seq=service.getNextSeqId("party",1);
        System.out.println("party:"+seq);
        long seq1=service.getNextSeqId("sample",1);
        System.out.println("sample:"+seq1);

    }catch (Exception err){
        err.printStackTrace();
    }
   }
}
