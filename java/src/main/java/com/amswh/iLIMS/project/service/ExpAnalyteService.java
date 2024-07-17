package com.amswh.iLIMS.project.service;

import com.amswh.iLIMS.project.domain.ExpAnalyte;
import com.amswh.iLIMS.project.domain.ExpPlan;
import com.amswh.iLIMS.project.mapper.lims.IExpAnalyte;
import com.amswh.iLIMS.project.mapper.lims.IExpPlan;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ExpAnalyteService extends ServiceImpl<IExpAnalyte, ExpAnalyte> {

    @Resource
    IExpPlan  planMapper;

    public void batchInsertExpAnalytes(String expId,List<String> analytes){

        this.planMapper.batchInsert(expId,analytes);
    }

    public int addNewPlan(ExpPlan obj){
       return this.planMapper.insert(obj);
    }


    /**
     * 统计各个产品待计划安排的分析物总数
     * @return
     */

    public List<Map<String,Object>> getProductAnalytesCount_to_test(){
             return this.planMapper.AnalyteCountToTest();
    }

    public List<String> listAnalytesToTest(String productNo){
         if(productNo==null || productNo.trim().isEmpty()) return null;
         return  this.planMapper.listAnalytesToTest(productNo);
    }



    public List<Map<String,Object>> listReagents4Exp(String productNo){
        return this.planMapper.listReagentsOfExpSteps(productNo);
    }


}
