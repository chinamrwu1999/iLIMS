package com.amswh.iLIMS.partner;


import com.amswh.framework.Constants;
import com.amswh.iLIMS.domain.PartyGroup;
import com.amswh.iLIMS.service.ConstantsService;
import com.amswh.iLIMS.service.PartyService;
import com.amswh.iLIMS.service.PartygroupService;
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

    private Map<String,String> expressNo2PartnerMap=new HashMap<String,String>();//

    @Resource
    ConstantsService constantsService;

    @Resource
    PartygroupService partygroupService;

    @PostConstruct
    public void loadServices(){
        Set<String> set1=this.parters.keySet();
        this.orderedCodes=new ArrayList<>();
        for(String code: Arrays.asList("YQ","HY","MEGA","XNYT","PAJK","RH")){
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
             System.out.println(" >>>>>>>>>>>>>> "+ code+" is ready for service");
         }
    }

    public PatientInfo fetPatientInfoWithExpressNo(String barCode,String expressNo){
           String code=this.getPartnerCode(expressNo);
           if(code !=null){
               return this.fetchPatientInfo(code,barCode);
           }else{

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
                 System.out.println(">>>>>>>>>>>>>>>>>>>"+partnerCode);
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
            System.out.println((index++)+" >>>>>>>>>>>>>>>>>>       initializing partner service: " + value.whoAmI() + " with class: " + value.getClass().getName());
            parters.put(value.whoAmI(), value);
        }
    }

    public void putExpressNo(String expressNo,String partnerCode){
        this.expressNo2PartnerMap.putIfAbsent(expressNo,partnerCode);
    }

    public String getPartnerCode(String expressNo){
        return this.expressNo2PartnerMap.get(expressNo);
    }


}
