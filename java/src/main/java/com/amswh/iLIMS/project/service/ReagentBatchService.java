package com.amswh.iLIMS.project.service;

import com.amswh.iLIMS.project.domain.Reagent;
import com.amswh.iLIMS.project.domain.ReagentBatch;
import com.amswh.iLIMS.project.mapper.lims.IReagentBatch;
import com.amswh.iLIMS.utils.MapUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
@Service
public class ReagentBatchService extends ServiceImpl<IReagentBatch, ReagentBatch> {



    public List<Map<String,Object>> listReagentBatch(){
         return  baseMapper.listReagentBatch();

    }


    /**
     *
     * @param input
     * @return
     */
    public boolean addBatch(Map<String,Object> input){
        String reagentId=input.get("reagentId")!=null?input.get("reagentId").toString().trim():null;
        String batchNo=input.get("batchNo")!=null?input.get("batchNo").toString().trim():null;
        if(reagentId==null || batchNo==null) return  false;
        ReagentBatch batch=baseMapper.getBatch(reagentId,batchNo);
        if(batch!=null){
            int quantity=(Integer)input.get("quantity");
            batch.setQuantity(batch.getQuantity()+quantity);
            batch.setRemaining(batch.getRemaining()+quantity);
            return this.updateById(batch);
        }else {
            batch=new ReagentBatch();
            MapUtil.copyFromMap(input,batch);
            batch.setRemaining(batch.getQuantity());
            return save(batch);
        }

    }






}
