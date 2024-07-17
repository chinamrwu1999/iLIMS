package com.amswh.iLIMS.project.service;

import com.amswh.iLIMS.project.domain.Disease;
import com.amswh.iLIMS.project.mapper.lims.IDisease;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DiseaseService extends ServiceImpl<IDisease, Disease> {
}
