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

	/**
	 * 自动样本分拣：根据条码从Partner处获取样本信息：报告病人信息、检测项目、partner本身
	 * @param barCode
	 */
	public PatientInfo fetchSampleInfoFromPartners(String barCode){
		  return  partnerService.fetchPatientInfo(barCode); //通过第三方API接口获取信息
	}

	/**
	 *  手动分拣
	 */
	  public PatientInfo categorizeSampleManually(Map<String,Object> inputMap){
		   PatientInfo patientInfo=new PatientInfo();
		   patientInfo.setBarCode(inputMap.get("barCode").toString());
		   patientInfo.setPartnerCode(inputMap.get("partnerCode").toString());
		   patientInfo.setProductCode(inputMap.get("productCode").toString());
		   return patientInfo;
	  }




	/**
	 *  接收样本
	 * @param inputMap
	 */
	@Transactional
	public void receiveBioSample(Map<String,Object> inputMap){
           try {
			   BioSample sample = new BioSample();
			   MapUtil.copyFromMap(inputMap, sample);
			   if(this.save(sample)) {//保存样本信息
				   Analyte analyte = new Analyte();
				   MapUtil.copyFromMap(inputMap, analyte);
				   if( analyteService.save(analyte)) {//保存分析物信息
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