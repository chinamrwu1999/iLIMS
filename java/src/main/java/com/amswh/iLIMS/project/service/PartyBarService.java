package com.amswh.iLIMS.project.service;
import com.amswh.iLIMS.project.domain.PartnerBar;
import com.amswh.iLIMS.project.domain.PartyBar;
import com.amswh.iLIMS.project.domain.Person;
import com.amswh.iLIMS.project.mapper.lims.IPartyBar;
import com.amswh.iLIMS.partner.PartnerService;
import com.amswh.iLIMS.partner.PatientInfo;
import com.amswh.iLIMS.utils.MapUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.aot.generate.InMemoryGeneratedFiles;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class PartyBarService extends ServiceImpl<IPartyBar, PartyBar> {

	   @Resource
	   PartyService partyService;

	   public Map<String,Object> findPartner(String barCode){
		   return this.baseMapper.findPartner(barCode);
	   }
	   public List<Map<String,Object>> getBarStatus(List<String> partyIds){
		    return  this.baseMapper.getBarStatus(partyIds);
	   }



	/**
	 *  保存扫码绑定信息：
	 *  1. 保存受检者个人信息
	 *  2. 保存受检者与条码的关联信息
	 *  3  保存Partner与条码的信息
	 *  4. 保存条码对应的检测产品信息
	 * @return
	 */
	@Transactional
	public boolean saveBindedInfo(Map<String,Object> inputMap){
		PatientInfo patientInfo = new PatientInfo();
		MapUtil.copyFromMap(inputMap, patientInfo);
		Person patient = partyService.savePatient(patientInfo);
		PartyBar pb = new PartyBar();
		MapUtil.copyFromMap(inputMap, pb);
		pb.setPartyId(patient.getPartyId());
		this.save(pb);
		return true;

	}


	public boolean updataPartyBar(Map<String,Object> input){
		 if(input.get("partyId")!=null){
			 String partyId=input.get("partyId").toString();
			 PartyBar pb=new PartyBar();
			 pb.setPartyId(partyId);
			 if(input.get("partnerCode")!=null){
				 pb.setPartnerCode(input.get("partnerCode").toString().trim());
			 }else{
				 pb.setPartnerCode(null);
			 }
			 if(input.get("productCode")!=null){
				 pb.setProductCode(input.get("productCode").toString().trim());
			 }else{
				 pb.setProductCode(null);
			 }
			 if(input.get("age")!=null){
				 pb.setAge((Integer) input.get("age"));
			 }else{
				 pb.setAge(null);
			 }
			 return baseMapper.updatePartyBar(pb);

		 }
		 return false;
	}



	public Map<String,Object> getBoundInfo(String barCode){
        return this.baseMapper.getBoundInfo(barCode);
	}

}