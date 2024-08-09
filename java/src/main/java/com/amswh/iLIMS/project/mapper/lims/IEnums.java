package com.amswh.iLIMS.project.mapper.lims;
import com.amswh.iLIMS.project.domain.China2024;
import com.amswh.iLIMS.project.domain.Enums;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface IEnums extends BaseMapper<Enums> {

    @Select("SELECT * FROM enums where `type`=#{emumType}")
    public List<Enums> fetchEnums(String enumType);

    @Select("SELECT * FROM china2024 where adcode=#{adcode}")
    public List<China2024> getCity(String adCode);

    @Select("SELECT * FROM china2024 where parent=#{adCode}")
    public List<China2024> getChildrenCities(String adCode);



}