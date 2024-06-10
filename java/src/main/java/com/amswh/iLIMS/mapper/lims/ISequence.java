package com.amswh.iLIMS.mapper.lims;


import com.amswh.iLIMS.domain.Sequence;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ISequence extends BaseMapper<Sequence> {

    @Update("UPDATE sequence set seqId=seqId WHERE seqName=#{seqName}")
    public int updateForLock(String seqName);

    @Select("SELECT seqId FROM sequence WHERE seqName=#{seqName}")
    public Long selectSeqId(String seqName);

    @Update("UPDATE sequence set seqId=#{seqId},updateTime=now() WHERE seqName=#{seqName}")
    public int updateSequence(String seqName,Long seqId);






}
