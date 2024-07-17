package com.amswh.iLIMS.project.mapper.lims;
import com.amswh.iLIMS.project.domain.BioSample;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


/**
 * 本接口定义与BioSample相关的综合查询：
 * 1. 根据barCode查询样本进度（时间迹线)
 */

public interface IBioSample extends BaseMapper<BioSample> {


}