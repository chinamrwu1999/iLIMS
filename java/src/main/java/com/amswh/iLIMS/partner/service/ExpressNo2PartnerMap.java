package com.amswh.iLIMS.partner.service;


import java.util.HashMap;
import java.util.Map;

public class ExpressNo2PartnerMap {

    private  record  OrderedPartner(String code,int index){};
    private final Map<String,OrderedPartner> map=new HashMap<>();
    int maxSize=100;
    int index=0;

    private  boolean isFull(){
        return  this.map.size()==this.maxSize;
    }
    public void pushNewPartner(String expressNo,String partnenr){

        if(!this.map.containsKey(expressNo)){
            if(isFull()){
                this.removeEarlyPartner();
            }
            OrderedPartner orderedPartner=new OrderedPartner(partnenr,this.index);
            this.map.put(expressNo,orderedPartner);
            this.index+=1;
        }
    }

    /** 移除最早进入的快递单号
     *
     */
    public void removeEarlyPartner(){
        if(isFull()) {
            String oldkey = null;
            int k = Integer.MAX_VALUE;
            for (String key : map.keySet()) {
                OrderedPartner obj = map.get(key);
                if (obj.index < k) {
                    oldkey = key;
                    k = obj.index;
                }
            }
            this.map.remove(oldkey);
        }
    }

    public String getPartner(String expressNo){
        if(expressNo==null || expressNo.isBlank()) return  null;
        OrderedPartner obj=this.map.get(expressNo);
        if(obj !=null){
            return obj.code;
        }else{
            return  null;
        }
    }

    public void reset(){
        this.map.clear();
        this.index=0;
    }

}