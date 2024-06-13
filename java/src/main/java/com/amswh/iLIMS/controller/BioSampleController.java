package com.amswh.iLIMS.controller;


import com.amswh.framework.model.AjaxResult;
import com.amswh.framework.utils.StringUtils;
import com.amswh.iLIMS.domain.*;
import com.amswh.iLIMS.partner.PartnerService;
import com.amswh.iLIMS.partner.PatientInfo;
import com.amswh.iLIMS.service.*;
import com.amswh.iLIMS.utils.MapUtil;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
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

//    @Resource
//    Patientervice patientService;

    @Autowired
    AnalyteService analyteService;

    @Resource
    AnalyteprocessService analyteProcessService;

    @Resource
    PartyBarService partyBarService;

    @Resource
    BarExpressService barExpressService;

    @Resource
    BarService barService;

    /**
     *  样本分拣信息检索：
     *  输入条码号或udi，查询病人信息、产品信息、partner信息给前端
     * @param inputMap
     */

    @PostMapping("/sample/fetchBoundInf")
    @Transactional
    public AjaxResult fetchBoundInf(@RequestBody Map<String,Object> inputMap){
        if(inputMap.get("barCode")==null && inputMap.get("udi")==null){
            return AjaxResult.error("条码号与udi至少提供一样");
        }
        String barCode=null;
        if(inputMap.get("barCode")!=null){
            barCode=inputMap.get("barCode").toString();
        } else if(inputMap.get("udi")!=null){
           barCode=inputMap.get("udi").toString();
        }
        //PatientInfo patientInfo=partnerService.fetchPatientInfo(barCode);
        Map<String,Object> mp=partyBarService.getBindedInfo(barCode);
        if(mp!=null && !mp.isEmpty() ) {//保存受检者、partner信息
            return AjaxResult.success(mp);
        }else{
            AjaxResult result=new AjaxResult();
            result.put("code","200");
            result.put("msg","未找到该条码的足够信息,请手工分拣");
            PatientInfo patientInfo=new PatientInfo();
            patientInfo.setBarCode(barCode);
            result.put("data",patientInfo);
            return result;
        }
  }


    /**
     * 分拣成功：保存快递单号与条码关联信息
     * @param inputMap
     * @return
     */

    @PostMapping("/sample/saveBoundInf")
    @Transactional
    public AjaxResult saveBoundInf(@RequestBody Map<String,Object> inputMap){
        if(inputMap.get("expressNo")==null ||  inputMap.get("expressNo").toString().isBlank() ||
           inputMap.get("barCode")==null ||  inputMap.get("barCode").toString().isBlank()
        ){
            return AjaxResult.error("快递单号、条码号都不能为空");
        }
        inputMap.put("bindWay","API");
        if(partyBarService.saveBindedInfo(inputMap)) {
            BarExpress be=new BarExpress();
            MapUtil.copyFromMap(inputMap,be);
            barExpressService.save(be);
            return AjaxResult.success("OK,样本信息保存成功");
        }
        return AjaxResult.error("收样失败！请联系技术人员");
    }



    /**
     * 医检所前端收样人员收到样本，查看该样本绑定的相关信息
     * @param barCode 样本信息描述
     */
    @GetMapping("/sample/receive/{barCode}")
    public AjaxResult receiveSample(@PathVariable String barCode){
        Map<String,Object> mp=partyBarService.getBindedInfo(barCode);
        if(mp!=null && !mp.isEmpty() ) {//保存受检者、partner信息
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
    @PostMapping("/sample/receive/save")
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
        process.setEmployeeId("15010040");//此处需要从session里面获取

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
