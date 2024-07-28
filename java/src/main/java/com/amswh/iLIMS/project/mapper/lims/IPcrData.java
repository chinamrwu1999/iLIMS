package com.amswh.iLIMS.project.mapper.lims;
import com.amswh.iLIMS.project.domain.PcrData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface IPcrData extends BaseMapper<PcrData> {

    @Select("SELECT * FROM PCRData where uploadId=#{expId} order by sampleId,well,target")
    List<PcrData> listExpData(long expId);

    @Select({"<script>",
            "SELECT d.* FROM PCRData D inner join analyte A on A.analyteCode=D.analyteCode",
            "WHERE A.barCode=#{barCode} ",
    "</script>"})
    List<PcrData> getPCRDataByBarCode(String barCode);

    @Select("SELECT * FROM PCRData WHERE analyteCode=#{code}")
    List<PcrData> getPCRDataByAnalyteCode(String code);

    @Select({"<script>",
            "SELECT id,uploadId,analyteCode,well,target,CT,predict,`choose` FROM PCRData where analyteCode IN ",
            "(SELECT analyteCode FROM analyte WHERE barCode=#{barCode})",
            "Order BY sampleId,well,target",
            "</script>"})
    public List<PcrData> getSamplePCRData(String barCode);

}