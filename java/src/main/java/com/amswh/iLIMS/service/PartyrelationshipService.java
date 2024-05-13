package com.amswh.iLIMS.service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.amswh.iLIMS.domain.Partyrelationship;
import com.amswh.iLIMS.mapper.lims.IPartyrelationship;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class PartyrelationshipService extends ServiceImpl<IPartyrelationship, Partyrelationship> {


	public int batchAddPartyRelationships(List<Map<String,Object>> objs){
		return this.baseMapper.batchAddRelations(objs);
	}
}