package com.amswh.iLIMS.service;
import com.amswh.iLIMS.domain.Bar;
import com.amswh.iLIMS.mapper.lims.IBar;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BarService extends ServiceImpl<IBar, Bar> {

	 @Resource
	 IBar mapper1;

	 public void test(){

	 }


	}