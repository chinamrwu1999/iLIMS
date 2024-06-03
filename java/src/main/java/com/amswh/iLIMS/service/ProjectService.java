package com.amswh.iLIMS.service;

import com.amswh.iLIMS.domain.Project;
import com.amswh.iLIMS.mapper.lims.IProject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProjectService extends ServiceImpl<IProject, Project> {

    public List<Map<String,String>> listProjectDiseases(){
         return this.baseMapper.listProjectDiseases();
    }

}
