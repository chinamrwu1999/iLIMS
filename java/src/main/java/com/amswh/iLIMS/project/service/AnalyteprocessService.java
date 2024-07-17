package com.amswh.iLIMS.project.service;
import com.amswh.iLIMS.project.domain.AnalyteProcess;
import com.amswh.iLIMS.project.mapper.lims.IAnalyteProcess;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class AnalyteprocessService extends ServiceImpl<IAnalyteProcess, AnalyteProcess> {
	public List<Map<String,Object>> listToTest(Map<String,Object> cond){
	   return this.baseMapper.listToTest(cond);
	}

	public List<Map<String,Object>> listToReview1(Map<String,Object> cond){
		return this.baseMapper.listToReview1(cond);
	}

	public List<Map<String,Object>> listToReview2(Map<String,Object> cond){
		return this.baseMapper.listToTest(cond);
	}

	public List<Map<String,Object>> listToReview3(Map<String,Object> cond){
		return this.baseMapper.listToTest(cond);
	}

	public Integer batchProcessAnalytes(List<Map<String,String>> mps){
		 return this.baseMapper.batchProcessAnalytes(mps);
	}

	/**
	 *  分析物处理记录：向analyteProcess表添加记录
	 * @param mps
	 * @return
	 */
	public Integer processAnalytes(Map<String,String> mps){
		List<Map<String,String>> items=new ArrayList<>();
		items.add(mps);
		return this.baseMapper.batchProcessAnalytes(items);
	}


}