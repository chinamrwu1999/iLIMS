package com.amswh.iLIMS.partner;


import com.amswh.iLIMS.partner.service.ExpressNo2PartnerMap;
import com.amswh.iLIMS.project.domain.PartyGroup;
import com.amswh.iLIMS.project.service.ConstantsService;
import com.amswh.iLIMS.project.service.PartygroupService;
import com.amswh.iLIMS.utils.MyStringUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PartnerService implements ApplicationContextAware {

    private Map<String,IPartner> parters=new HashMap<>(); // 用于存放各个Partner实现类

    private ExpressNo2PartnerMap expressNo2PartnerMap=new ExpressNo2PartnerMap();//

    @Resource
    ConstantsService constantsService;

    @Resource
    PartygroupService partygroupService;



    public PatientInfo fetPatientInfoWithExpressNo(String barCode,String expressNo){
           String code=this.expressNo2PartnerMap.getPartner(expressNo);
           PatientInfo patientInf=null;
           if(code !=null){
               patientInf= this.fetchPatientInfo(code,barCode);
               if(patientInf!=null) {
                   patientInf.setPartnerCode(code);

               }
           }else{
               patientInf=this.fetchPatientInfo(barCode); //轮询各个Partner的API
               if(patientInf!=null) {
                   this.expressNo2PartnerMap.pushNewPartner(expressNo, patientInf.getPartnerCode());
               }
           }
           if(patientInf!=null) {
               patientInf.setPartnerName(constantsService.getPartnerName(patientInf.getPartnerCode()));
               patientInf.setProductName(constantsService.getProductName(patientInf.getProductCode()));

           }
        return  patientInf;

    }

    public PatientInfo fetchPatientInfo(String partnerCode,String barCode){
        IPartner partner=this.parters.get(partnerCode);
        if(partner==null){       return null;   }
        try {
                PatientInfo patientInfo=partner.fetchPatientInfo(barCode);
                if(patientInfo!=null) {
                    patientInfo.setProductName(constantsService.getProductName(patientInfo.getProductCode()));
                }

                return patientInfo;
          }catch (Exception err){
                err.printStackTrace();
          }

        return null;
    }

    public PatientInfo fetchPatientInfo(String barCode){

         for(String partnerCode:parters.keySet()){
             try {
                 PatientInfo patient = this.fetchPatientInfo(partnerCode, barCode);
                 if (patient != null) {
                     patient.setPartnerCode(partnerCode);
                     return patient;
                 }
             } catch(Exception err){
                 System.out.println("\n调用第三方API异常:partner="+partnerCode+" barCode="+barCode);

               //  err.printStackTrace();
             }

        }
         return  null;
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, IPartner> map = applicationContext.getBeansOfType(IPartner.class);
        parters = new HashMap<>();
        System.out.println("\n\n");

        for(String key:map.keySet()){
            IPartner value=map.get(key);

            parters.put(value.whoAmI(), value);
            System.out.println("\t\t\t\t\t\t>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  " + value.whoAmI() + " is ready for service " );
        }
        System.out.println("\n\n");

    }




}
