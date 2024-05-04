package com.amswh.MYLIMS.controller;
/*

 */

import com.amswh.MYLIMS.service.PatientService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Resource
    PatientService patientService;


    /*
          业务：通过微信小程序绑定采样管
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
    public void bindBox(Map<String,Object> mp){
         Object object=mp.get("barCode");
         List<String> required= List.of("barCode","name","gender");

         if(object==null){

         }
    }


}
