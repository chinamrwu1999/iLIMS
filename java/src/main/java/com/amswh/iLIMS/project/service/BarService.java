package com.amswh.iLIMS.project.service;
import com.amswh.iLIMS.project.domain.Bar;
import com.amswh.iLIMS.project.domain.PcrData;
import com.amswh.iLIMS.project.domain.Product;
import com.amswh.iLIMS.project.mapper.lims.IBar;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class BarService extends ServiceImpl<IBar, Bar> {




	/**
	 * 批量生成条码：条码的构成为prefix+batchNO+productNo+3位随机数字+6位流水号
	 * @param batchNo: 批次号
	 * @param productNo:产品代码
	 * @param digitRandom:随机数位数
	 * @param prefix:前缀
	 * @param size:生成条码的数量
	 */
	 public void generateBarCodes(String batchNo,String productNo,int digitRandom,String prefix,int size){
		 double rd=0d;
		 int randomInt=0;
		 List<Bar> bars=new ArrayList<>();
		 for(int i=0;i<size;i++){
			 Bar bar=new Bar();
			 bar.setBatchNo(batchNo);
			 bar.setProductCode(productNo);
			 rd=Math.random()*1000;
			 randomInt=(int)rd;
			 bar.setBarCode(String.format(prefix+batchNo+"%03d%06d",randomInt,i));
			 bars.add(bar);
		 }
		 this.baseMapper.generateBarCodes(bars);


	 }

	/**
	 *  根据barCode查询条码是否是艾米森生成的,如果是则返回
	 * @param barCode
	 * @return Bar
	 */
	 public Bar getBar(String barCode){
          return  this.baseMapper.getBar(barCode);
	 }


	 public Product getProductOfBar(String barCode){
		  return baseMapper.getProductOfBar(barCode);
	 }

	 public Map<String,Object> getPatient(String barcode){
		 return baseMapper.getPatient(barcode);
	 }


	public Map<String,Object>  getBarProgress(String analyteCode){
		return  this.baseMapper.getAnalyteProgress(analyteCode);
	}


	/**
	 * 根据条码返回分析物
	 * @param barCode
	 * @return
	 */
	public List<Map<String,Object>> getAnalytes(String barCode){
		 return  this.baseMapper.getAnalyteCodes(barCode);
	}



	/**
	 *
	 * @param code :条码或分析物代码
	 * @return
	 */
	public String  getBarCode(String code){
		return  this.baseMapper.getBarCode(code);
	}

	public Map<String,Object> getBindingTime(String code){
             return this.baseMapper.getBindingTime(code);
	}

}