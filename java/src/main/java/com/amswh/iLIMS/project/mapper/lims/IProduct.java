package com.amswh.iLIMS.project.mapper.lims;
import com.amswh.iLIMS.project.domain.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;


public interface IProduct extends BaseMapper<Product> {

    @Select("SELECT analyteId,productId FROM Analyte2Product ")
    public Map<String,String> getAnalyte2Product();

}