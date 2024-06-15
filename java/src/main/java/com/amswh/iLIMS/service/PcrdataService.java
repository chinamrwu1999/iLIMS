package com.amswh.iLIMS.service;
import com.amswh.iLIMS.domain.PcrData;
import com.amswh.iLIMS.mapper.lims.IPcrData;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class PcrdataService extends ServiceImpl<IPcrData, PcrData> {


	public List<PcrData> getPCRDataOfExp(long expId){
//		 List<PcrData> data=this.baseMapper.listExpData(expId);
//		 List<String> sampleIds=data.stream().map(x -> x.getAnalyteCode()).collect(Collectors.toSet()).stream().toList();
//		 Map<String,Object> result=new HashMap<>();
//		 for(String analyteId:sampleIds){
//			 List<PcrData> sample=data.stream().filter(x -> x.getAnalyteCode().equals(analyteId)).toList();
//			 List<String> wells=sample.stream().map(x -> x.getWell()).collect(Collectors.toSet()).stream().toList();
//			 Map<String,Object> sampleMap=new HashMap<>();
//			 for(String well:wells){
//				 List<PcrData> wellData=sample.stream().filter(x -> x.getWell().equals(well)).toList();
//				 sampleMap.put(well,wellData);
//			 }
//			 result.put(analyteId,sampleMap);
//		 }
//		 return result;
		 return this.baseMapper.listExpData(expId);
	}

	public List<PcrData> getPCRDataByBarCode(String barCode){
          return  this.baseMapper.getPCRDataByBarCode(barCode);
	}

}