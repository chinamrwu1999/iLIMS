package com.amswh.iLIMS.mapper.lims;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.amswh.iLIMS.domain.PcrData;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface IPcrData extends BaseMapper<PcrData> {

    @Select("SELECT * FROM PCRData where uploadId=#{expId}")
    List<PcrData> listExpData(long expId);

    @Select({"<script>",
            "SELECT d.* FROM PCRData where analyteCode IN (",
            "SELECT analyteCode FROM analyte WHERE barCode=#{barCode})",
    "</script>"})
    List<PcrData> getPCRDataByBarCode(String barCode);

}