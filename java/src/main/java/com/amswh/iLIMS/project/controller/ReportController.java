package com.amswh.iLIMS.project.controller;


import com.amswh.iLIMS.framework.model.AjaxResult;
import com.amswh.iLIMS.project.service.AnalyteprocessService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 *  处理报告相关事务接口，报告报告审核，报告查询
 */


@RestController
public class ReportController {

    @Resource
    AnalyteprocessService analyteprocessService;

    /**
     * 拉取待审核分析物列表
     *
     * @param inputMap 查询条件，不可为空。至少包含step,表示1审、2审、3审：
     *                 {"step":1}
     * @return
     */
    @PostMapping("/report/listToReview")
    public AjaxResult fetchAnalytesToReview(Map<String, Object> inputMap) {
        Integer step = (Integer) inputMap.get("step");
        if (1 == step) {
            return AjaxResult.success(this.analyteprocessService.listToReview1(inputMap));
        }
        if (2 == step) {
            return AjaxResult.success(this.analyteprocessService.listToReview2(inputMap));
        }
        if (3 == step) {
            return AjaxResult.success(this.analyteprocessService.listToReview3(inputMap));
        }
        return AjaxResult.error("获取报告审核列表时，发送错误");
    }

    @PostMapping("/report/review")
    public AjaxResult reportReview(Map<String, String> inputMap) {
        int k=analyteprocessService.processAnalytes(inputMap);
        if(k>0) {
           return  AjaxResult.success(k+"份报告审核成功！");
        }
        return AjaxResult.error("获取报告审核列表时，发送错误");
    }
}
