package com.amswh.iLIMS.project.service;

import com.amswh.iLIMS.project.domain.Reagent;
import com.amswh.iLIMS.project.mapper.lims.IExpSteps;
import com.amswh.iLIMS.project.mapper.lims.IReagent;
import com.amswh.iLIMS.utils.MapUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class ReagentService extends ServiceImpl<IReagent, Reagent> {

    @Resource
    IExpSteps expSteps;
    public boolean addNewReagent(Map<String,Object> input){
        Reagent reagent=new Reagent();
        MapUtil.copyFromMap(input,reagent);
        return  save(reagent);
    }




}
