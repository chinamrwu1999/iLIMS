package com.amswh.iLIMS.controller;
/*

 */


import com.amswh.iLIMS.domain.Person;
import com.amswh.iLIMS.service.PersonService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Resource
    PersonService patientService;


    /*
          业务：通过微信小程序扫码绑定病人信息与采样管条码
          输入（前端校验）：
                barCode 采样管条码号（必填）
                name: 病人姓名（必填）
                gender: 性别（必填）
                age：年龄 或
                birthday：出生日期
                phone：
                IDNumber：身份证号码

     */
    @PostMapping("/bindBox")
    @Transactional
    public void bindBoxByScan(@Valid Person patient){
        //patient.setSrc("mobile");
        this.patientService.save(patient);


    }





}
