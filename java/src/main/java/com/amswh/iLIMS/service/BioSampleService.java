package com.amswh.iLIMS.service;
import com.amswh.iLIMS.domain.Analyte;
import com.amswh.iLIMS.domain.AnalyteProcess;
import com.amswh.iLIMS.mapper.lims.IBioSample;
import com.amswh.iLIMS.utils.MapUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.amswh.iLIMS.domain.BioSample;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


@Service
public class BioSampleService extends ServiceImpl<IBioSample, BioSample> {

	   @Resource
	   AnalyteService analyteService;

	   @Resource
	   AnalyteprocessService analyteprocessService;


	/**
	 *  接收样本
	 * @param inputMap
	 */
	@Transactional
	   public void receiveBioSample(Map<String,Object> inputMap){
           try {
			   BioSample sample = new BioSample();
			   MapUtil.copyFromMap(inputMap, sample);
			   if(this.save(sample)) {
				   Analyte analyte = new Analyte();
				   MapUtil.copyFromMap(inputMap, analyte);
				   if( analyteService.save(analyte)) {
					   AnalyteProcess analyteProcess = new AnalyteProcess();
					   analyteProcess.setAnalyteCode(analyte.getAnalyteCode());
					   analyteProcess.setAction("RECEIVE");
					   analyteProcess.setStatus("SUCCESS");
					   analyteprocessService.save(analyteProcess);
				   }
			   }

		   }catch (Exception err){
			   err.printStackTrace();
		   }

	   }

}