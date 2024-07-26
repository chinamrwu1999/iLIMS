package com.amswh.iLIMS.project.controller;


import com.amswh.iLIMS.framework.model.AjaxResult;

import com.amswh.iLIMS.framework.security.SecurityUtils;
import com.amswh.iLIMS.framework.security.model.LoginUser;
import com.amswh.iLIMS.partner.PartnerService;
import com.amswh.iLIMS.partner.PatientInfo;
import com.amswh.iLIMS.partner.service.NORMALService;
import com.amswh.iLIMS.project.domain.*;
import com.amswh.iLIMS.project.service.*;
import com.amswh.iLIMS.utils.MapUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class BioSampleController {

    @Resource
    BioSampleService sampleService;

    @Resource
    PartyService partyService;

    @Autowired
    AnalyteService analyteService;

    @Resource
    AnalyteprocessService analyteProcessService;

    @Resource
    PartyBarService partyBarService;

    @Resource
    NORMALService normalService;

    @Resource
    BarExpressService barExpressService;

    @Resource
    PartnerService partnerService;

    @Resource
    BarService barService;

    /**
     *  样本分拣：
     *  输入条码号或udi，获取病人信息、产品信息、partner信息给前端
     * @param inputMap
     */

    @PostMapping("/sample/categorize")
    @Transactional
    public AjaxResult sampleCategorize(@RequestBody Map<String,Object> inputMap){
        String barCode=inputMap.get("barCode")==null?null:inputMap.get("barCode").toString().trim();
        String expressNo=inputMap.get("expressNo")==null?null:inputMap.get("expressNo").toString().trim();
        String udi=inputMap.get("udi")==null?null:inputMap.get("udi").toString().trim();
        if(barCode==null && udi==null){  return AjaxResult.error("条码号与udi至少提供一样");  }
        if(barCode==null ){   barCode=udi;    }

        if(expressNo!=null){ // 保存快递单号与条码的关联信息
            BarExpress be=new BarExpress();
            be.setBarCode(barCode);
            be.setExpressNo(expressNo);
            be.setUdi(udi);
            barExpressService.save(be);
        }
        Map<String,Object> boundInfo=partyBarService.getBoundInfo(barCode);// 获取扫码绑定信息
        if(boundInfo!=null && !boundInfo.isEmpty() ) {//
             if("unknown".equals(boundInfo.get("partnerCode").toString())){ //代理商未知
               Map<String,Object> partnerMap=  normalService.findPartnerInfo(barCode);
               if(partnerMap!=null){
                   boundInfo.put("partnerCode",partnerMap.get("sampleSrc"));
                   boundInfo.put("partnerName",partnerMap.get("customerName"));
                   partyBarService.updataPartyBar(boundInfo);
                   return AjaxResult.success(boundInfo);
               }else{
                   return AjaxResult.error("未能找到合作代理客户信息，请手工确认或联系相关销售人员",boundInfo);
               }
            }else{
                return AjaxResult.success(boundInfo);
            }
        }else{// 未找到扫码绑定信息，说明样本来自某个Partner，需要调用Partner的API接口获取受检者个人信息  ---------------------- 2
              PatientInfo patientInfo=partnerService.fetPatientInfoWithExpressNo(barCode,expressNo);
             if(patientInfo!=null){ // 通过partner的api成功获取信息
                   Person patient =partyService.savePatient(patientInfo);
                   if(patient!=null){
                        String partyId=patient.getPartyId();
                        PartyBar pb=new PartyBar();
                        BeanUtils.copyProperties(patientInfo,pb);
                        pb.setPartyId(partyId);
                        pb.setBindWay("api");
                        partyBarService.save(pb);
                        return AjaxResult.success(patientInfo);
                   }else{
                       return  AjaxResult.error("保存受检者个人信息时发生异常",patientInfo);
                   }
             }else{ //既没有绑定信息，也没有partner的API信息   --------------------------------------------- 3
                   Map<String,Object> result=new HashMap<>();
                   Map<String,Object> partnerMap=normalService.findPartnerInfo(barCode);
                   if(partnerMap!=null && !partnerMap.isEmpty()){
                       result.put("partnerCode",partnerMap.get("sampleSrc"));
                       result.put("partnerName",partnerMap.get("partner"));
                   }
                   Product product=normalService.findProductInfo(barCode);
                   if(product!=null){
                       result.put("productCode",product.getCode());
                       result.put("productName",product.getName());
                   }
                  return AjaxResult.success("未能找到受检者个人的信息，请手工录入！",result);
             }
        }
  }
    /**
     * 医检所前端收样人员收到样本，查看该样本绑定的相关信息
     * @param barCode 样本信息描述
     */
    @GetMapping("/sample/boundInfo/{barCode}")
    public AjaxResult receive_Sample_BoundInfo(@PathVariable String barCode){
        Map<String,Object> mp=partyBarService.getBoundInfo(barCode);
        if(mp!=null && !mp.isEmpty() ) {
            return AjaxResult.success(mp);
        }else{
            AjaxResult result=new AjaxResult();
            result.put("code","200");
            result.put("msg","未找到该条码的分拣信息,请手工收样");
            PatientInfo patientInfo=new PatientInfo();
            patientInfo.setBarCode(barCode);
            result.put("data",patientInfo);
            return result;
        }
    }

    /**
     *  保存收样信息
     * @param inputMap 样本信息描述
     */
    @PostMapping("/sample/receive")
    @Transactional
    public AjaxResult saveReceivedSampleInf(@RequestBody Map<String,Object> inputMap){
        BioSample sample=new BioSample();
        MapUtil.copyFromMap(inputMap,sample);
        Analyte analyte =new Analyte();
        MapUtil.copyFromMap(inputMap,analyte);
        AnalyteProcess process=new AnalyteProcess();
        process.setAction("RECEIVE");
        process.setStatus("success");
        process.setAnalyteCode(inputMap.get("analyteCode").toString());
        LoginUser loginUser=SecurityUtils.getLoginUser();
        process.setEmployeeId(loginUser.getUsername());//此处需要从session里面获取

        if(analyteService.save(analyte) &&
          sampleService.save(sample) &&
          analyteProcessService.save(process)){
              return AjaxResult.success("OK,收样成功");
        }else{
            AjaxResult result=new AjaxResult();
            result.put("code","200");
            result.put("msg","未找到该条码的分拣信息,请手工收样");
            PatientInfo patientInfo=new PatientInfo();
            MapUtil.copyFromMap(inputMap,patientInfo);
            result.put("data",patientInfo);
            return result;
        }
    }
    /**
     *  查看检测进度检测
     */
    @PostMapping("/sample/queryProgress")
    public AjaxResult queryBarProgress(String barCode ){
          Map<String,Object> progress=this.barService.getBarProgress(barCode);
          if(progress!=null){
              return  AjaxResult.success(progress);
          }else{
              return AjaxResult.error("未查到该条码的相关信息，请检查条码号是否输入正确");
          }
    }



}
