package com.amswh.iLIMS.project.controller;


import com.amswh.iLIMS.framework.model.AjaxResult;
import com.amswh.iLIMS.project.domain.ExpPlan;
import com.amswh.iLIMS.project.domain.PcrData;
import com.amswh.iLIMS.project.service.DataUploadService;
import com.amswh.iLIMS.project.service.ExpAnalyteService;
import com.amswh.iLIMS.project.service.PcrdataService;
import com.amswh.iLIMS.project.service.SeqService;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 *  本controller处理PCR实验相关API接口，主要包括:
 *  1. 样本检测实验的规划 ExpPlan
 *  2. PCR实验数据上传
 *  3. PCR实验配置步骤
 */
@RestController
public class ExpAnalyteController {
    @Resource
    ExpAnalyteService expAnalyteService;
    @Resource
    SeqService seqService;
    @Resource
    DataUploadService uploadService;


    @Resource
    PcrdataService pcrService;

    @PostMapping("/exp/saveExpPlan")
    @Transactional
    public AjaxResult saveExpPlan(@RequestBody List<String> analyteCodes){
        String employee="15010040"; //需要从session获取
        ExpPlan entity=new ExpPlan();
        String expId=seqService.nextExpPlainSeq();
        entity.setId(expId);
        entity.setEmployeeId(employee);
        if(this.expAnalyteService.addNewPlan(entity)>0){
            this.expAnalyteService.batchInsertExpAnalytes(expId,analyteCodes);
            return AjaxResult.success("OK");
        }
        return AjaxResult.error("保存实验计划数据失败");
    }

    /**
     * 列出代查看的实验列表
     * @return
     */

    @PostMapping("/pcr/list/experiments")
    public AjaxResult listExperiment(@RequestBody Map<String, Object> input){
        return AjaxResult.success(this.uploadService.listExperiment(input));
    }
    @PostMapping("/pcr/data")
    public AjaxResult getPCRData(Map<String,Object> input){
         if(input.get("expId")==null){
             return AjaxResult.error("缺少实验ID");
         }
         int expId=(Integer)input.get("expId");
         int pageSize=input.get("pageSize")!=null?(Integer)input.get("pageSize"):20;
         int pageIndex=input.get("pageIndex")!=null?(Integer)input.get("pageIndex"):0;
         List<PcrData> data=this.pcrService.getPCRDataOfExp(expId);
         Map<String,Object> result=new HashMap<>();
         if(data!=null && !data.isEmpty()){
             int total=data.size();
                List<String> sampleIds=data.stream().filter(x -> !(x.getAnalyteCode().startsWith("PC") || x.getAnalyteCode().startsWith("NC")))
                        .map(PcrData::getAnalyteCode).toList();
                result.put("total",total);
                result.put("sampleNumber",sampleIds.stream().distinct());
                int k=data.stream().filter(x -> x.getAnalyteCode().equals(sampleIds.get(0))).toList().size();
                int actualPageSize=k* (int)Math.round(1.0d* pageSize/k);
                List<PcrData> sampleData=  data.stream().filter(x -> !(x.getAnalyteCode().startsWith("PC") || x.getAnalyteCode().startsWith("NC"))).toList();
                sampleData.addAll(data.stream().filter(x -> x.getAnalyteCode().startsWith("PC") || x.getAnalyteCode().startsWith("NC")).toList());
                int index0=pageIndex*actualPageSize;
                int index1=index0+actualPageSize;
                if(index1 >= total){
                    index1=total;
                }
                result.put("list",sampleData.subList(index0,index1));
                result.put("pageNumber",(int) Math.ceil(1.0d*total/actualPageSize));
                result.put("currentIndex",pageIndex);
         }else{
             result.put("pageNumber",0);
             result.put("total",0);
             result.put("sampleNumber",0);
             result.put("list",new ArrayList<>());
         }
        return  AjaxResult.success(result);

    }


    /**
     *  获取样本检测PCR数据
     */
    @PostMapping("/sample/pcr/{barCode}")
    public AjaxResult getSamplePCRData(@PathVariable  String barCode ){
        return AjaxResult.success(pcrService.getSamplePCRData(barCode));
    }



    @GetMapping("/pcr/bar/{barCode}")
    public AjaxResult getPCRData(@PathVariable String barCode){
       // return  AjaxResult.success(this.pcrService.getPCRDataOfExp(bar));
        return AjaxResult.success(pcrService.getPCRDataByBarCode(barCode));
    }
    /**
     * 返回每个产品类别待实验安排的分析物数量
     * @return
     */

    @GetMapping("/exp/toTest/count")
    public AjaxResult AnalytesCountToTest(){
           return AjaxResult.success(this.expAnalyteService.getProductAnalytesCount_to_test());
    }

    /**
     * 返回每个产品类别待实验安排的分析物数量
     * @return
     */

    @GetMapping("/exp/analytesToTest/{productNo}")
    public AjaxResult  listAnalytesToTest(@PathVariable String productNo){
        return AjaxResult.success(this.expAnalyteService.listAnalytesToTest(productNo));
    }



    @GetMapping("/exp/reagent/{productCode}")
    public AjaxResult listReagents4Exp(@PathVariable String productCode){
         return  AjaxResult.success(this.expAnalyteService.listReagents4Exp(productCode));
    }

}
