package com.amswh.iLIMS.project.mapper.lims;
import com.amswh.iLIMS.project.domain.Enums;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface IEnums extends BaseMapper<Enums> {

    @Select("SELECT * FROM enums where `type`=#{emumType}")
    public List<Enums> fetchEnums(String enumType);
}