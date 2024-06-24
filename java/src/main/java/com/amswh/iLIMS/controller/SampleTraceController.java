package com.amswh.iLIMS.controller;


import com.amswh.framework.model.AjaxResult;
import com.amswh.iLIMS.service.BarService;
import com.amswh.iLIMS.service.PartyService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *  本controller处理单个样本关联的所有信息查询，包括：受检者个人信息、检测数据和结果、时间迹线、销售合同、报告审核
 */

@RestController
public class SampleTraceController {
        @Resource
        PartyService partyService;

        @Resource
        BarService barService;
    /**
     *  获取病人个人信息
     * @param barCode:可以是分析物代码或barCode / 或分析物代码
     * @return
     */
    public AjaxResult getPatientInf(@PathVariable String barCode){
        return  AjaxResult.success(this.partyService.getPatientByBarCode(barCode));
    }

    /**
     * 跟踪样本的处理进度
     * @param code : barCode 或 analyteCode
     * @return
     */

    public AjaxResult getTraceTimes(@PathVariable String code){
        List<Map<String,Object>> result=new ArrayList<>();
        Map<String,Object> bindingTime=this.barService.getBindingTime(code);
        Map<String,Object> barProgress=this.barService.getBarProgress(code);
        result.add(bindingTime);
        result.add(barProgress);
        return AjaxResult.success(result);
    }
}
