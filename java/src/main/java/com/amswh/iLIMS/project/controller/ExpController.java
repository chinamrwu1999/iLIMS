package com.amswh.iLIMS.project.controller;

import com.amswh.iLIMS.framework.model.AjaxResult;
import com.amswh.iLIMS.project.service.ExpStepService;
import com.amswh.iLIMS.project.service.ReagentBatchService;
import com.amswh.iLIMS.project.service.ReagentService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 检测实验相关业务：实验步骤配置、实验样本规划
 *
 */
@RestController
@RequestMapping("/exp")
public class ExpController {

    @Resource
    ExpStepService expStepService;
    @Resource
    ReagentService reagentService;

    @Resource
    ReagentBatchService batchService;


    /** 获取产品实验步骤配置、以及没以步骤需要的试剂信息
     *
     * @param productCode
     * @return
     */

    @GetMapping("/configs/{productCode}")
    public AjaxResult getExpStepConfigRegeants(@PathVariable String productCode){
        return AjaxResult.success(expStepService.getExpConfigReagent(productCode));
    }




}
