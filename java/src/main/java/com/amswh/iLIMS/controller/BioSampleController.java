package com.amswh.iLIMS.controller;


import com.amswh.iLIMS.domain.Bar;
import com.amswh.iLIMS.domain.BioSample;
import com.amswh.iLIMS.service.*;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sample")
public class BioSampleController {

    @Resource
    BioSampleService sampleService;

//    @Resource
//    Patientervice patientService;

    @Autowired
    AnalyteService analyteService;

    @Resource
    AnalyteprocessService analyteProcessService;

    @Resource
    PersonService personService;

    @Resource
    PartyBarService partyBarService;

    @Resource
    BarService barService;

    /**
     *  样本分拣
     * @param inputMap
     */

    public void categorizeSample(@RequestBody Map<String,String> inputMap){

        if(inputMap.get("barCode")==null && inputMap.get("udi")==null){
            System.out.println("条码号与udi至少提供一样");
            return;
        }
        String barCode=null;
        if(inputMap.get("barCode")!=null){
            barCode=inputMap.get("barCode");
        } else if(inputMap.get("udi")!=null){

        }
       // if()


    }



    /**
     * 医检所前端收样人员收到样本
     * @param barCode 样本信息描述
     */
    @Transactional
    @PostMapping("/receive/{barCode}")
    public Map<String,Object> receiveSample(@PathVariable String barCode){

        Map<String,Object> result=new HashMap<>();
        String partner=null;
        String productCode=null;


        Bar bar=this.barService.getGeneratedBar(barCode);
        if(bar!=null){
            productCode=bar.getProductCode();
        }

        Map<String,Object> mp=this.partyBarService.findPartner(barCode);
        if(!(mp==null || mp.isEmpty())) { //根据barCode追溯艾米森的订单信息
              if(mp.get("productCode")!=null){
                  productCode=mp.get("productCode").toString();
                  result.put("productName",mp.get("productName"));
              }
              if(mp.get("partnerId")!=null){
                  partner="NORMAL";
                  result.put("partyId",mp.get("partyId"));
                  result.put("sender",mp.get("fullName"));
              }
        }else{

        }

        return result;
    }


}
