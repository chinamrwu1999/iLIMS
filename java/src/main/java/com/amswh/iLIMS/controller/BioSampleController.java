package com.amswh.iLIMS.controller;


import com.amswh.iLIMS.domain.Biosample;
import com.amswh.iLIMS.domain.Person;
import com.amswh.iLIMS.service.AnalyteService;
import com.amswh.iLIMS.service.AnalyteprocessService;
import com.amswh.iLIMS.service.BiosampleService;
import com.amswh.iLIMS.service.PersonService;
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
    BiosampleService sampleService;

//    @Resource
//    Patientervice patientService;

    @Autowired
    AnalyteService analyteService;

    @Resource
    AnalyteprocessService analyteProcessService;

    @Resource
    PersonService personService;


    /**
     * 医检所前端收样人员收到样本
     * @param sample 样本信息描述
     */
    @Transactional
    @PostMapping("/receive")
    public void receiveSample(@Valid Biosample sample){
        // patientService.getMap(new HashMap<String,String>("barCode:"))
       // Person patient =personService.;
       // if(patient==null) {//未找到对应的病人信息，需要从Partner处获取病人信息
            //下面读取OA订单信息，获取Partner的ID
            String sampleSrc=null;



            if(sampleSrc==null){ //下面轮询调用第三方API拉取病人信息

          //  }


       // }else{

        }



    }


}
