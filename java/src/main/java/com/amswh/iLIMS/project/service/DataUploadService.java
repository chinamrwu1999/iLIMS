package com.amswh.iLIMS.project.service;
import com.amswh.iLIMS.project.domain.DataUpload;
import com.amswh.iLIMS.project.mapper.lims.IDataUpload;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class DataUploadService extends ServiceImpl<IDataUpload, DataUpload> {

    public List<DataUpload> listExperiment(Map<String,Object> input){
        return this.baseMapper.listExperiment(input);
    }


}