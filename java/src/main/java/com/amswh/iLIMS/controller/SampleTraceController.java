package com.amswh.iLIMS.controller;


import com.amswh.framework.model.AjaxResult;
import com.amswh.iLIMS.service.PartyService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


/**
 *  本controller处理单个样本关联的所有信息查询，包括：受检者个人信息、检测数据和结果、时间迹线、销售合同、报告审核
 */

@RestController
public class SampleTraceController {
        @Resource
        PartyService partyService;
    /**
     *  获取病人个人信息
     * @param analyteCode:可以是分析物代码或barCode
     * @return
     */
    public AjaxResult getPatientInf(@PathVariable String analyteCode){

        return  null;
    }
}
