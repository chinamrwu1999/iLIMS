package com.amswh.MYLIMS.controller;

import com.amswh.MYLIMS.domain.BioSample;
import com.amswh.MYLIMS.domain.Patient;
import com.amswh.MYLIMS.service.AnalyteProcessService;
import com.amswh.MYLIMS.service.AnalyteService;
import com.amswh.MYLIMS.service.BioSampleService;
import com.amswh.MYLIMS.service.PatientService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/sample")
public class BioSampleController {

    @Resource
    BioSampleService sampleService;

    @Resource
    PatientService patientService;

    @Autowired
    AnalyteService analyteService;

    @Resource
    AnalyteProcessService analyteProcessService;


    /**
     * 医检所前端收样人员收到样本
     * @param sample 样本信息描述
     */
    @Transactional
    @PostMapping("/receive")
    public void receiveSample(@Valid BioSample sample){
        // patientService.getMap(new HashMap<String,String>("barCode:"))
        Patient patient =patientService.getBaseMapper().getPatientByCarCode(sample.getBarCode());
        if(patient==null) {//未找到对应的病人信息，需要从Partner处获取病人信息
            //下面读取OA订单信息，获取Partner的ID
            String sampleSrc=null;



            if(sampleSrc==null){ //下面轮询调用第三方API拉取病人信息

            }


        }else{

        }



    }


}
