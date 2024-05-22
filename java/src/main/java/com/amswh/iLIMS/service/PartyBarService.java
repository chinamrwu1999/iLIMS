package com.amswh.iLIMS.service;
import com.amswh.iLIMS.domain.PartyBar;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.amswh.iLIMS.mapper.lims.IPartyBar;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class PartyBarService extends ServiceImpl<IPartyBar, PartyBar> {

	   public Map<String,Object> findPartner(String barCode){
		   return this.baseMapper.findPartner(barCode);
	   }
}