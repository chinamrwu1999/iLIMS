package com.amswh.iLIMS.service;

import com.amswh.iLIMS.domain.ExpAnalyte;
import com.amswh.iLIMS.domain.ExpPlan;
import com.amswh.iLIMS.mapper.lims.IExpAnalyte;
import com.amswh.iLIMS.mapper.lims.IExpPlan;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
