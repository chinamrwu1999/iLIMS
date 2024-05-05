package com.amswh.MYLIMS.controller;

import com.amswh.MYLIMS.domain.BioSample;
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

    @Transactional
    @PostMapping("/receive")
    public void receiveSample(@Valid BioSample sample){

    }


}
