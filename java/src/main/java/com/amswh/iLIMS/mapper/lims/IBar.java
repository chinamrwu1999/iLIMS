package com.amswh.iLIMS.mapper.lims;
import com.amswh.iLIMS.domain.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.amswh.iLIMS.domain.Bar;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


public interface IBar extends BaseMapper<Bar> {

    @Insert({
            "<script>",
            "INSERT INTO Bar(barCode,productCode,batchNo) VALUES",
            "<foreach collection='list' item='entity' separator=','>",
            "(#{entity.barCode}, #{entity.productCode},#{entity.batchNo})",
            "</foreach>",
            "</script>"
    })
    public int generateBarCodes(List<Bar> entities);


    @Select("SELECT * FROM Bar where barCode=#{barCode}")
    public Bar getGeneratedBar(String barCode);


    @Select("SELECT A.* FROM product A left join  Bar ON A.code=Bar.productCode where Bar.barCode=#{barCode}")
    public Product getProductOfBar(String barCode);

}