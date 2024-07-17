package com.amswh.iLIMS.project.service;

import com.amswh.iLIMS.project.domain.Project;
import com.amswh.iLIMS.project.mapper.lims.IProject;
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
