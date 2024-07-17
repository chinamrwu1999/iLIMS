package com.amswh.iLIMS.project.service;
import com.amswh.iLIMS.project.mapper.lims.IBioSample;
import com.amswh.iLIMS.oa.OAQueryService;
import com.amswh.iLIMS.partner.PartnerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.amswh.iLIMS.project.domain.BioSample;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


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