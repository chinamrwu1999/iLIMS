package com.amswh.iLIMS.mapper.lims;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.amswh.iLIMS.domain.Bar;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;


@Mapper
@BatchDataSource()
public interface IBar extends BaseMapper<Bar> {
}