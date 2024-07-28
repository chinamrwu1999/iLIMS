package com.amswh.iLIMS.project.service;
import com.amswh.iLIMS.project.domain.DataUpload;
import com.amswh.iLIMS.project.mapper.lims.IDataUpload;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DataUploadService extends ServiceImpl<IDataUpload, DataUpload> {

    public Map<String,Object> listExperiment(Map<String,Object> input){
         int pageSize=input.get("pageSize")!=null? (Integer) input.get("pageSize"):20;
         int pageIndex=input.get("pageIndex")!=null?(Integer) input.get("pageIndex"):0;
         Integer offset=pageIndex*pageSize;
         input.put("offset",offset);
         Map<String,Object> result=new HashMap<>();
         result.put("list",this.baseMapper.listExperiment(input));
         result.put("currentPage",pageIndex);
         Integer total= baseMapper.getExperimentCount(input);
         int pageNumber=(int)Math.ceil(1.0*total / pageSize );
         result.put("pageNumber",pageNumber);
         result.put("total",total);
         return  result;

    }


}