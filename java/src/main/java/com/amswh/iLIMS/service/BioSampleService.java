package com.amswh.iLIMS.service;
import com.amswh.iLIMS.domain.Analyte;
import com.amswh.iLIMS.domain.AnalyteProcess;
import com.amswh.iLIMS.mapper.lims.IBioSample;
import com.amswh.iLIMS.oa.OAQueryService;
import com.amswh.iLIMS.partner.PartnerService;
import com.amswh.iLIMS.partner.PatientInfo;
import com.amswh.iLIMS.utils.MapUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.amswh.iLIMS.domain.BioSample;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


@Service
public class BioSampleService extends ServiceImpl<IBioSample, BioSample> {
	   @Resource
	   AnalyteService analyteService;
	   @Resource
	   AnalyteprocessService analyteprocessService;
	   @Resource
	   PartnerService partnerService;
	   @Resource
	   OAQueryService  OA;
}