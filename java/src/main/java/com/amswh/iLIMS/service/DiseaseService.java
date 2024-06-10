package com.amswh.iLIMS.service;

import com.amswh.iLIMS.domain.Disease;
import com.amswh.iLIMS.mapper.lims.IDisease;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DiseaseService extends ServiceImpl<IDisease, Disease> {
}
