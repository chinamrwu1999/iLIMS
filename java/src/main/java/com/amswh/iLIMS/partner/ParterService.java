package com.amswh.iLIMS.partner;


import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ParterService {

    Map<String,IPartner> parters=new HashMap<>(); // 用于存放各个Partner实现类



    @PostConstruct
    public void loadServices(){
        System.out.println("Initializing partner service......");

    }

    public Map<String,Object> fetchPatientInfo(String partnerCode,String barCode){
        IPartner partner=this.parters.get(partnerCode);
        if(partner==null){
            partner=this.parters.get("NORMAL");
        }
        if(partner!=null){
            return partner.fetchPatientInfo(barCode);
        }
        return null;
    }

    public Map<String,Object> fetchPatientInfo(String barCode){
         for(String partnerCode:parters.keySet()){
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


}
