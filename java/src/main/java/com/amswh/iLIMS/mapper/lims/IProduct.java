package com.amswh.iLIMS.mapper.lims;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.amswh.iLIMS.domain.Product;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface IProduct extends BaseMapper<Product> {

    @Select("SELECT analyteId,productId FROM Analyte2Product ")
    public Map<String,String> getAnalyte2Product();

}