package com.amswh.iLIMS.partner;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
public class PatientInfo {


    public PatientInfo(String barCode){
          this.barCode=barCode;
          this.otherInfo=new HashMap<>();
    }
    public PatientInfo(String barCode,String name){
        this(barCode);
        this.name=name;
    }
    private String name;
    private String gender;
    private Integer age;
    private String birthDate;
    private String productCode;
    private String samplingTime;
    private String barCode;
    private String IDNumber;
    private String IDType;
    private String phone;
    private String partnerCode;
    private Map<String,Object> otherInfo;
    public Object getOtherInfo(String fieldName){
         return this.otherInfo.get(fieldName);
    }
    public void setOtherFieldInfo(String fieldName,Object value){
         this.otherInfo.put(fieldName,value);
    }
    public void listOtherInfo(){
        if(this.otherInfo!=null && !this.otherInfo.isEmpty()){
            for(String key:this.otherInfo.keySet()){
                System.out.println(key+":"+this.otherInfo.get(key));
            }
        }
    }

}
