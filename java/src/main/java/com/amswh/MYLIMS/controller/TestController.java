package com.amswh.MYLIMS.controller;

import com.amswh.MYLIMS.domain.Analyte;
import com.amswh.MYLIMS.domain.AnalyteProcess;
import com.amswh.MYLIMS.domain.BioSample;
import com.amswh.MYLIMS.domain.Patient;
import com.amswh.MYLIMS.partner.rh.RHService;
import com.amswh.MYLIMS.service.AnalyteProcessService;
import com.amswh.MYLIMS.service.AnalyteService;
import com.amswh.MYLIMS.service.BioSampleService;
import com.amswh.MYLIMS.service.PatientService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    BioSampleService bioSampleServiceservice;

    @Resource
    AnalyteService analyteService;

    @Resource
    PatientService patientService;

    @Resource
    AnalyteProcessService processService;

    @Resource
    RHService rhService;


    @GetMapping("/service")
    public void TestMe(){
//         this.create();
//         List<BioSample> list=bioSampleServiceservice.list();
//         System.out.println(list.size());
//         for(BioSample obj:list){
//             System.out.println(obj.getId()+"\t"+obj.getBarCode()+"\t"+obj.getPartnerCode());
//         }
//         this.query();

         rhService.fetchServiceStatus("62400498308");
         System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        rhService.pushTestResult();
         System.out.println("OK");
    }

    private void create(){
          BioSample sample=new BioSample();
        sample.setPartnerCode("XNYT");
        sample.setBarCode("AMS24000045301");
        sample.setSampleType("F");
          this.bioSampleServiceservice.save(sample);
        Analyte analyte=new Analyte();
        analyte.setBarCode("AMS24000045301");
        analyte.setAnalyteCode("ACJ2024000008");
        analyte.setProductCode("LDT06");
        this.analyteService.save(analyte);

        AnalyteProcess process=new AnalyteProcess();
        process.setAction("RECEIVE");
        process.setAnalyteCode("ACJ2024000008");
        process.setStatus("SUCCESS");
        process.setEmployeeId("15010040");
        processService.save(process);



        Patient patient=new Patient();
        patient.setName("刘亦菲");
        patient.setGender("F");
        patient.setAge(65);
        patient.setPhone("15172509685");
        patient.setSrc("API");
        patient.setBarCode("AMS24000045301");
        patientService.save(patient);



    }

//    private void query(){
//         List<Map<String,Object>> list=this.processService.getBaseMapper().getSampleStatus("AMS24000045301");
//         list.stream().forEach( x -> {
//              for(String key:x.keySet()){
//                  System.out.println(key+":"+x.get(key));
//              }
//         });
//    }
}
