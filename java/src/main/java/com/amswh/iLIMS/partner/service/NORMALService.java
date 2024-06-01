package com.amswh.iLIMS.partner.service;

import com.amswh.iLIMS.domain.BarExpress;
import com.amswh.iLIMS.domain.Product;
import com.amswh.iLIMS.oa.OAQueryService;
import com.amswh.iLIMS.partner.IPartner;
import com.amswh.iLIMS.partner.PatientInfo;
import com.amswh.iLIMS.service.BarExpressService;
import com.amswh.iLIMS.service.BarService;
import com.amswh.iLIMS.service.OrderService;
import com.amswh.iLIMS.service.PartyService;
import jakarta.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

public class NORMALService implements IPartner {

    @Resource
    OAQueryService OA;

    @Resource
    OrderService orderService;

    @Resource
    BarService barService;

    @Resource
    BarExpressService barExpressService;

    @Resource
    PartyService partyService;

    @Override
    public PatientInfo fetchPatientInfo(String barCode) throws Exception {
       return null;
    }



    private Product findProductInfo(String barCode){
     Product result=orderService.getShippedProductInfo(barCode); //根据发货信息查找条码的产品类型
        if(result!=null ){
            return result;
        }
         result=barService.getProductOfBar(barCode); //根据生成条码时候的
        if(result !=null){
            return  result;
        }
        result=barExpressService.getProductByBarCode(barCode); //根据分拣时候的记录
        if(result !=null){
            return  result;
        }
        return null;
    }

    /**
     * 根据条码查找产品发给了哪位客户，从而 确定sample src
     * @param barCode
     * @return
     */
    private Map<String,Object> findPartnerInfo(String barCode){

        BarExpress express =barExpressService.getBarExpressByBarCode(barCode); //根据分拣数据找
        if(express !=null &&express.getPartnerId()!=null){
             Map<String,Object> mp=partyService.findPartyByExternalId(express.getPartnerId());
             if(mp!=null){
                 Map<String,Object> result=new HashMap<>();
                 result.put("sampleSrc",express.getPartnerId());
                 result.put("partner",mp.get("shortName"));
                 return result;
             }
        }
        Map<String,Object> order=orderService.getOrderInfo(barCode);// 根据订单查找客户
        if(order !=null){
           if(order.get("externalId")!=null){
               Map<String,Object> result=new HashMap<>();
               result.put("sampleSrc",order.get("externalId"));
               result.put("partner",order.get("shortName"));
               return result;
           }else if(order.get("fullName")!=null){
               Map<String,Object> result=new HashMap<>();
               result.put("sampleSrc",inferSampleSrc(order.get("fullName").toString()));
               result.put("partner",order.get("shortName"));
               return result;
           }
        }

        return null;
    }

    /**
     * 根据用户手机扫码绑定时候的信息查找
     * @param barCode
     * @return
     */
    private Map<String,Object> findPatientInfoByScanBindInfo(String barCode){

        return null;
    }

    @Override
    public String whoAmI() {
        return "NORMAL";
    }

    private String inferSampleSrc(String name){

        if(name.indexOf("杭州")>=0 && name.indexOf("迪安") >0){
            return "HZDA";
        }

        if(name.indexOf("林州")>=0 && name.indexOf("食管癌")>=0){
            return "LZ";
        }
        if(name.indexOf("杭州")>=0 && name.indexOf("同创")>=0){
            return "HZTC";
        }
        if(name.indexOf("环亚")>=0 ){
            return "HY";
        }
        if(name.indexOf("解码")>=0 ){
            return "SHJM";
        }
        if(name.indexOf("瑞华")>=0 && name.indexOf("保险")>0 && name.indexOf("健康")>0 ){
            return "RH";
        }
        if(name.indexOf("云鹊")>=0 ){
            return "YQ";
        }
        if(name.indexOf("泸州")>=0 && name.indexOf("医疗投资") >0){
            return "XNYT";
        }
        if(name.indexOf("黄石")>=0 && name.indexOf("爱康")>=0){
            return "HSAK";
        }
        if(name.indexOf("武汉")>=0 && name.indexOf("康圣达") >0){
            return "KSD";
        }
        return "NORMAL";
    }
}
