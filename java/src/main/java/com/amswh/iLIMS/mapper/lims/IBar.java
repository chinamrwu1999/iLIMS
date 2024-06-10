package com.amswh.iLIMS.mapper.lims;
import com.amswh.iLIMS.domain.Person;
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
    public Bar getBar(String barCode);


    @Select("SELECT A.* FROM product A left join  Bar ON A.code=Bar.productCode where Bar.barCode=#{barCode}")
    public Product getProductOfBar(String barCode);

    /**
     * 根据扫码绑定信息获取Patient和age
     * @param barCode
     * @return
     */
    @Select("SELECT P.partyId,P.name,P.gender,,date_format(P.birthday,'%Y-%m-%d') birthday,"+
            "P.IDCardType,P.IDNumber,PB.age FROM PartyBar PB,Person P "+
            "WHERE PB.partyId=P.partyId AND PB.barCode=#{barCode}")
    public Map<String,Object> getPatient(String barCode);

}