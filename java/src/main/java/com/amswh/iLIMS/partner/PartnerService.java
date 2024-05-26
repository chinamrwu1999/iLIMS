package com.amswh.iLIMS.partner;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PartnerService implements ApplicationContextAware {
    private List<String> orderedCodes;// 根据partner的重要程度排序，可以确定哪个服务先调用
    Map<String,IPartner> parters=new HashMap<>(); // 用于存放各个Partner实现类
    @PostConstruct
    public void loadServices(){
        Set<String> set1=this.parters.keySet();
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

    public Map<String,Object> fetchPatientInfo(String partnerCode,String barCode){
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

    public Map<String,Object> fetchPatientInfo(String barCode){

         for(String partnerCode:orderedCodes){
             try {
                 Map<String, Object> result = this.fetchPatientInfo(partnerCode, barCode);
                 if (result != null) {
                     return result;
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
}
