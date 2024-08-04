package com.amswh.iLIMS.partner;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
public class PatientInfo {


    public PatientInfo(){
        this.otherInfo=new HashMap<>();
    }
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
    private String birthday;

    private String samplingTime;
    private String barCode;

    @JsonProperty("IDNumber")
    private String IDNumber;
    @JsonProperty("IDType")
    private String IDType;
    private String phone;
    private String partnerCode;
    private String productName;
    private String productCode;

    private String partnerName;
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

    @JsonIgnore
    public Map<String,Object> getPatientMap(){
        Map<String,Object> mp=new HashMap<>();
        if(this.name!=null){
            mp.put("name",this.name);
        }
        if(this.barCode!=null){
            mp.put("barCode",this.barCode);
        }
        if(this.age!=null){
            mp.put("age",this.age);
        }
        if(this.birthday!=null){
            mp.put("birthday",this.birthday);
        }
        if(this.IDNumber!=null){
            mp.put("IDNumber",this.IDNumber);
        }
        if(this.IDType!=null){
            mp.put("IDType",this.IDType);
        }
        if(this.phone!=null){
            mp.put("phone",this.phone);
        }
        if(this.partnerCode!=null){
            mp.put("partnerCode",this.partnerCode);
        }
        if(this.partnerName!=null){
            mp.put("partnerName",this.partnerName);
        }
        if(this.productCode!=null){
            mp.put("productCode",this.productCode);
        }
        if(this.productName!=null){
            mp.put("productName",this.productName);
        }
        if(this.gender!=null){
           mp.put("gender",this.gender);
        }
        mp.putAll(this.otherInfo);
        return mp;

    }

}
