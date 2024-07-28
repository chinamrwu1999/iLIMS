package com.amswh.iLIMS.project.service;
import com.amswh.iLIMS.project.domain.PcrData;
import com.amswh.iLIMS.project.mapper.lims.IPcrData;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PcrdataService extends ServiceImpl<IPcrData, PcrData> {


	public List<PcrData> getPCRDataOfExp(long expId){
		 return this.baseMapper.listExpData(expId);
	}

	public List<PcrData> getPCRDataByBarCode(String barCode){
          return  this.baseMapper.getPCRDataByBarCode(barCode);
	}

	public List<PcrData> getSamplePCRData(String barCode){
		return  this.baseMapper.getSamplePCRData(barCode);
	}

}