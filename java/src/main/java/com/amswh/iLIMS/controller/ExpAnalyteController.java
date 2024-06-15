package com.amswh.iLIMS.controller;


import com.amswh.framework.model.AjaxResult;
import com.amswh.iLIMS.domain.ExpPlan;
import com.amswh.iLIMS.service.DataUploadService;
import com.amswh.iLIMS.service.ExpAnalyteService;
import com.amswh.iLIMS.service.PcrdataService;
import com.amswh.iLIMS.service.SeqService;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @PostMapping("/pcr/saveExpPlan")
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

    @PostMapping("/pcr/listExperiment")
    public AjaxResult listExperiment(@RequestBody Map<String, Object> input){
        return AjaxResult.success(this.uploadService.listExperiment(input));
    }
    @GetMapping("/pcr/exp/{expId}")
    public AjaxResult getPCRData(@PathVariable Long expId){
         return  AjaxResult.success(this.pcrService.getPCRDataOfExp(expId));
    }

    @GetMapping("/pcr/bar/{barCode}")
    public AjaxResult getPCRData(@PathVariable String barCode){
       // return  AjaxResult.success(this.pcrService.getPCRDataOfExp(bar));
        return AjaxResult.success(pcrService.getPCRDataByBarCode(barCode));
    }



}
