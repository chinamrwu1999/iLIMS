package com.amswh.MYLIMS.mapper.oa;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface OAMapper {

    @Select("SELECT ")
    public List<Map<String,Object>> fetchOAOrder(LocalDateTime updateTime);
}
