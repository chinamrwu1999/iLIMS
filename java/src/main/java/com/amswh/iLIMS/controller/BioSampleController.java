package com.amswh.iLIMS.controller;


import com.amswh.framework.model.AjaxResult;
import com.amswh.iLIMS.domain.Bar;
import com.amswh.iLIMS.domain.BioSample;
import com.amswh.iLIMS.domain.PartnerBar;
import com.amswh.iLIMS.domain.Person;
import com.amswh.iLIMS.partner.PartnerService;
import com.amswh.iLIMS.partner.PatientInfo;
import com.amswh.iLIMS.service.*;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
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
    PartyService partyService;

    @Resource
    PartyBarService partyBarService;

    @Resource
    BarService barService;

    @Resource
    PartnerService partnerService;

    @Resource
    PartnerBarService partnerBarService;

    /**
     *  样本分拣
     * @param inputMap
     */

    @PostMapping("/categorize")
    public AjaxResult categorizeSample(@RequestBody Map<String,String> inputMap){

        if(inputMap.get("barCode")==null && inputMap.get("udi")==null){
            return AjaxResult.error("条码号与udi至少提供一样");
        }
        String barCode=null;
        if(inputMap.get("barCode")!=null){
            barCode=inputMap.get("barCode");
        } else if(inputMap.get("udi")!=null){
           barCode=inputMap.get("udi");
        }
        PatientInfo patientInfo=partnerService.fetchPatientInfo(barCode);
        if(this.saveBarParties(patientInfo)) {//保存受检者、partner信息
            return AjaxResult.success(patientInfo);
        }else{
            AjaxResult result=new AjaxResult();
            result.put("code","200");
            result.put("msg","未找到该条码的足够信息,请手工分拣");
            result.put("data",patientInfo);
            return result;
        }
  }



    /**
     * 医检所前端收样人员收到样本
     * @param barCode 样本信息描述
     */
    @GetMapping("/receive/{barCode}")
    public AjaxResult receiveSample(@PathVariable String barCode){



        return null;
    }

    /**
     *  查看检测进度检测
     */

    public void querySampleStatus(String barCode ){

    }

    @Transactional
    private boolean saveBarParties(PatientInfo patientInfo){
        if(patientInfo!=null){
            Person patient=partyService.savePatient(patientInfo);
            PartnerBar pb=new PartnerBar();
            BeanUtils.copyProperties(patient,pb);
            pb.setPartnerId(patientInfo.getPartnerCode());
            partnerBarService.save(pb);
            return true;
        }
        return false;
    }


}
