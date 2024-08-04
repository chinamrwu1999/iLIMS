package com.amswh.iLIMS.project.service;
import com.amswh.iLIMS.framework.model.AjaxResult;
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

	   @Resource
	   SeqService seqService;

	   @Resource
	   PartnerBarService partnerBarService;

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
	public String saveBindedInfo(Map<String,Object> inputMap){

		PartnerBar partnerBar=new PartnerBar();
		MapUtil.copyFromMap(inputMap, partnerBar);

		PatientInfo patientInfo = new PatientInfo();
		MapUtil.copyFromMap(inputMap, patientInfo);

		PartyBar pb = new PartyBar();
		MapUtil.copyFromMap(inputMap, pb);
		if(inputMap.get("openId")!=null){
			pb.setBindWay("wechat");
		}
		String barId= seqService.nextBarId();
		partnerBar.setBarId(barId);
		if(partnerBarService.save(partnerBar)){
			Person patient = partyService.savePatient(patientInfo);
			pb.setBarId(barId);
			pb.setPartyId(patient.getPartyId());
			this.save(pb);
			return barId;
		}
		return  null;
	}

	public Map<String,Object> getBoundInfo(String barCode){
        return this.baseMapper.getBoundInfo(barCode);
	}

	public PartyBar getBarByCode(String barCode){
          return this.baseMapper.getBarByCode(barCode);
	}

	public AjaxResult listReceivedToday(Map<String,Object> input){
		 Integer pageSize=input.get("pageSize")==null?20:(Integer)input.get("pageSize");
		 Integer pageIndex=input.get("pageIndex")==null?0:(Integer)input.get("pageIndex");
		 Integer offset=pageIndex*pageSize;
		 input.put("offset",offset);
		 input.putIfAbsent("pageSize",pageSize);
		 Integer total=baseMapper.listReceivedTodayCount(input);
		 Map<String,Object> result=new HashMap<>();
		 result.put("total",total);
		 int pageNumber=(int)Math.ceil(total*1.0d/pageSize);
		 result.put("pageNumber",pageNumber);
		 result.put("currentPage",pageIndex);
		 result.put("list",baseMapper.listReceivedToday(input));
	     return AjaxResult.success(result);
	}

}