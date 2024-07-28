package com.amswh.iLIMS.project.mapper.lims;

import com.amswh.iLIMS.project.domain.BarExpress;
import com.amswh.iLIMS.project.domain.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

public interface IBarExpress  extends BaseMapper<BarExpress> {

    @Select("SELECT * FROM BarExpress WHERE barCode=#{barCode}")
    public BarExpress getBarExpressByBarCode(String barCode);

    @Select("SELECT P.* FROM Product P LEFT JOIN BarExpress BE ON P.code=BE.productCode WHERE BE.barCode=#{barCode}")
    public Product getProductByBarCode(String barCode);

}
