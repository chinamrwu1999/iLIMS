package com.amswh.iLIMS.project.service;

import com.amswh.iLIMS.project.domain.ExpSteps;
import com.amswh.iLIMS.project.mapper.lims.IExpSteps;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExpStepService extends ServiceImpl<IExpSteps, ExpSteps> {


     /**
      * 获取实验步骤需要的试剂
      * @param productCode
      * @return
      */
     public List<Map<String,Object>> getExpConfigReagent(String productCode){
          List<Map<String,Object>> data= baseMapper.getExpStepConfig(productCode);
          List<Map<String,Object>> result=new ArrayList<>();
          Set<String> steps=data.stream().map(x -> x.get("stepId").toString()).collect(Collectors.toSet());

          for(String step:steps){
               List<Map<String,Object>> d1=data.stream().filter(x -> x.get("stepId").toString().equals(step)).toList();
               Map<String,Object> node1=d1.get(0);
               Map<String,Object> mp=new HashMap<>();
               mp.put("productCode",productCode);
               mp.put("stepId",step);
               mp.put("stepName",node1.get("stepName"));
               Set<String> reagents=d1.stream().map(x -> x.get("reagentId").toString()).collect(Collectors.toSet());
               for(String reagent:reagents){
                    List<Map<String,Object>> d2=d1.stream().filter(x -> x.get("reagentId").toString().equals(reagent)).toList();
                    Map<String,Object> node2=d2.get(0);
                    mp.put("reagentId",reagent);
                    mp.put("reagentName",node2.get("reagentName"));
                    mp.put("model",node2.get("model"));
                    mp.put("spec",node2.get("spec"));

                    Set<String> batches=d2.stream().map(x -> x.get("batchNo").toString()).collect(Collectors.toSet());
                    List<Map<String,Object>> batchList=new ArrayList<>();
                    for(String batch:batches){
                         List<Map<String,Object>> batchData=d2.stream().filter(x -> x.get("batchNo").toString().equals(batch)).toList();
                         for(Map<String,Object> obj: batchData){
                              Map<String,Object> kk=new HashMap<>();
                              kk.put("batchNo",batch);
                              kk.put("remaining",obj.get("remaining"));
                              kk.put("produceData",obj.get("produceDate"));
                              kk.put("expireDate",obj.get("expireDate"));
                              batchList.add(kk);
                         }

                    }
                    mp.put("batches",batchList);
               }
               result.add(mp);

          }


          return result;
     }

     /**
      * 保存实验安排中每个步骤用到的试剂信息
      */

//     public boolean saveExpPlanReagents(List<Map<String,Object>> input){
//
//     }

}
