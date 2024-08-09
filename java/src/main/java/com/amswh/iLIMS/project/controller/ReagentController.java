package com.amswh.iLIMS.project.controller;


import com.amswh.iLIMS.framework.model.AjaxResult;
import com.amswh.iLIMS.project.service.ReagentBatchService;
import com.amswh.iLIMS.project.service.ReagentService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/reagent")
public class ReagentController {

    @Resource
    ReagentBatchService batchService;
    @Resource
    ReagentService reagentService;

    @GetMapping("/batch/list")
    public AjaxResult listReagentBatches(){
        return  AjaxResult.success(batchService.listReagentBatch());
    }

    @PostMapping("/batch/add")
    public AjaxResult addReagentBatches(@RequestBody Map<String,Object> input){
          if(input.get("reagentId")==null || input.get("batchNo")==null)
              return AjaxResult.error("试剂编号和批次号都不能为空");
          if(batchService.addBatch(input)){
              return AjaxResult.success("批次添加成功");
          }else{
              return  AjaxResult.error("添加试剂批次错误，请咨询后端开发人员!");
          }
    }

    @PostMapping("/addNew")
    public AjaxResult addNewReagent(@RequestBody Map<String,Object> input){
        if(input.get("id")==null || input.get("name")==null)
            return AjaxResult.error("试剂编号和名称都不能为空");
        if(reagentService.addNewReagent(input)){
            return AjaxResult.success("试剂添加成功");
        }else{
            return  AjaxResult.error("添加试剂错误，请咨询后端开发人员!");
        }
    }

}
