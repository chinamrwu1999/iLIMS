package com.amswh.MYLIMS.mapper;

import com.amswh.MYLIMS.domain.BioSample;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface IBioSampleMapper extends BaseMapper<BioSample> {

    /*
        生物样本基本信息
     */
    @Select("SELECT B.barCode,B.type,sampleTime,partnerCode,B.sender,B.createTime receiveTime,B.status,B.sampleImage,B.formImage, "+
            "C.fullName companyName " +
            "FROM BioSample B " +
            "LEFT JOIN company C ON B.partnerCode=C.code "+
            "LEFT JOIN enums E ON E.value=B.type "+
            "WHERE B.barCode=#{barCode} AND E.type='sampleType'"
            )
    public List<Map<String,Object>> getBioSampleInfo(String barCode);






}
