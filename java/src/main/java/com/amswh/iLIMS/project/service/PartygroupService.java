package com.amswh.iLIMS.project.service;
import com.amswh.iLIMS.project.domain.PartyGroup;
import com.amswh.iLIMS.project.mapper.lims.IPartyGroup;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class PartygroupService extends ServiceImpl<IPartyGroup, PartyGroup> {

	public List<Map<String,Object>> listDepartments(){
		return this.baseMapper.listDepartments();
	}
}