package com.amswh.iLIMS.partner.service;

import com.amswh.iLIMS.project.domain.Product;
import com.amswh.iLIMS.oa.OAQueryService;
import com.amswh.iLIMS.partner.IPartner;
import com.amswh.iLIMS.partner.PatientInfo;
import com.amswh.iLIMS.project.service.BarExpressService;
import com.amswh.iLIMS.project.service.BarService;
import com.amswh.iLIMS.project.service.OrderService;
import com.amswh.iLIMS.project.service.PartyService;
import com.amswh.iLIMS.utils.MapUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NORMALService {
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


    /**
     * 当partner的API无法获取patient信息，还有2个渠道可能会获取病人信息
     * 1. 用户扫码绑定barCode时候
     *
     * @param barCode
     * @return
     * @throws Exception
     */

    public PatientInfo fetchPatientInfo(String barCode) throws Exception {
       Map<String,Object> mp=barService.getPatient(barCode); //根据样本绑定信息获取
       PatientInfo patient=new PatientInfo();
       if(!(mp==null || mp.isEmpty())) { //扫码绑定信息
           MapUtil.copyFromMap(mp, patient);
           if(mp.get("birthday")!=null) {
               patient.setBirthday(mp.get("birthday").toString());
           }
       }
        return  patient;
    }



    public Product findProductInfo(String barCode){
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
    public Map<String,Object> findPartnerInfo(String barCode){
       Map<String,Object> order=orderService.getOrderInfo(barCode);// 根据订单发货查找客户
        if(order !=null){
            Map<String,Object> result=new HashMap<>();
            result.put("partnerName",order.get("customerName"));
           if(order.get("externalId")!=null){
               result.put("partnerCode",order.get("externalId"));
           }else if(order.get("fullName")!=null){
               result.put("partnerCode",inferSampleSrc(order.get("customerName").toString()));
           }
            return result;
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
