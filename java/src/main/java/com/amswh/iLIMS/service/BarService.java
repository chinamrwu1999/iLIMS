package com.amswh.iLIMS.service;
import com.amswh.iLIMS.domain.Bar;
import com.amswh.iLIMS.mapper.lims.IBar;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


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
		 this.getBaseMapper().generateBarCodes(bars);


	 }


	}