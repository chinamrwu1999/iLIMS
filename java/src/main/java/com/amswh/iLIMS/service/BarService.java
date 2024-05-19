package com.amswh.iLIMS.service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.amswh.iLIMS.domain.Bar;
import com.amswh.iLIMS.mapper.lims.IBar;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class BarService extends ServiceImpl<IBar, Bar> {

	 @Resource
	 IBar mapper1;

	 public void test(){

	 }


	}