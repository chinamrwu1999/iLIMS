package com.amswh.iLIMS.controller;


import com.amswh.iLIMS.partner.service.*;

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

   @Resource
   ExpAnalyteService service;


   // @PostMapping("/service")
    @GetMapping("/hello")
    @Transactional(transactionManager="limsTransactionManager")
    public void TestMe(){
    try {
        List<String> sampleIds=new ArrayList<>();
        sampleIds.add("ACK240001");
        sampleIds.add("ACK240002");
        sampleIds.add("ACK240003");
        sampleIds.add("ACK240004");
        sampleIds.add("ACK240005");
        String expId="240614001";
        service.batchInsertExpAnalytes(expId,sampleIds);


    }catch (Exception err){
        err.printStackTrace();
    }
   }
}
