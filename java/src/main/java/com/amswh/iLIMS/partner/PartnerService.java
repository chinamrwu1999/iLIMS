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
    private List<String> orderedCodes;// 根据partner的重要程度排序，可以确定哪个服务先调用
    private Map<String,IPartner> parters=new HashMap<>(); // 用于存放各个Partner实现类

    private ExpressNo2PartnerMap expressNo2PartnerMap=new ExpressNo2PartnerMap();//

    @Resource
    ConstantsService constantsService;

    @Resource
    PartygroupService partygroupService;

    @PostConstruct
    public void loadServices(){
        Set<String> set1=this.parters.keySet();
        this.orderedCodes=new ArrayList<>();
        for(String code: Arrays.asList("YQ","HY","XNYT","MEGA","PAJK","RH")){
            if(set1.contains(code)){
                this.orderedCodes.add(code);
            }
        }
         for(String key:set1){
                if(!this.orderedCodes.contains(key)){
                    this.orderedCodes.add(key);
                }
         }
         for(String code:orderedCodes){
             System.out.println(" \t\t\t\t>>>>>>>>>>>>>> "+ code+" is ready for service");
         }
         System.out.println("\n\n");
    }

    public PatientInfo fetPatientInfoWithExpressNo(String barCode,String expressNo){
           String code=this.expressNo2PartnerMap.getPartner(expressNo);
           if(code !=null){
               return this.fetchPatientInfo(code,barCode);
           }else{
               PatientInfo patientInfo=this.fetchPatientInfo(barCode);
               this.expressNo2PartnerMap.pushNewPartner(expressNo,patientInfo.getPartnerCode());
               return  patientInfo;
           }

    }

    public PatientInfo fetchPatientInfo(String partnerCode,String barCode){
        IPartner partner=this.parters.get(partnerCode);
        if(partner==null){
            partner=this.parters.get("NORMAL");
        }
        try {
                return partner.fetchPatientInfo(barCode);
          }catch (Exception err){
                err.printStackTrace();
          }

        return null;
    }

    public PatientInfo fetchPatientInfo(String barCode){

         for(String partnerCode:orderedCodes){
             try {
                // System.out.println(">>>>>>>>>>>>>>>>>>>"+partnerCode);
                 PatientInfo patient = this.fetchPatientInfo(partnerCode, barCode);
                 if (patient != null) {
                     patient.setPartnerCode(partnerCode);
                     if(!MyStringUtils.isEmpty(patient.getProductCode())){
                         patient.setProductName(constantsService.getProductName(patient.getProductCode()));
                     }
                     if(!MyStringUtils.isEmpty(patient.getPartnerCode())){
                          PartyGroup company=partygroupService.getById(patient.getPartnerCode());
                          if(company!=null) {
                              patient.setPartnerName(company.getFullName());
                          }
                     }
                     return patient;
                 }
             } catch(Exception err){
                 System.out.println("\n调用第三方API异常:partner="+partnerCode+" barCode="+barCode);
                 System.out.println(err.getMessage()+"\n");
                 err.printStackTrace();
             }

        }
         return  null;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, IPartner> map = applicationContext.getBeansOfType(IPartner.class);
        parters = new HashMap<>();
        System.out.println("\n");
        int index=0;
        for(String key:map.keySet()){
            IPartner value=map.get(key);
            //System.out.println((index++)+" >>>>>>>>>>>>>>>>>>       initializing partner service: " + value.whoAmI() + " with class: " + value.getClass().getName());
            parters.put(value.whoAmI(), value);
        }
    }

}
