package com.amswh.iLIMS.mapper.lims;
import com.amswh.iLIMS.domain.PcrData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.amswh.iLIMS.domain.DataUpload;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface IDataUpload extends BaseMapper<DataUpload> {


    @Select({"<script>",
            "SELECT *  FROM DataUpload WHERE 1=1",
            "<if test='beginDate!=null and endDate !=null'>",
            "AND date(uploadTime) &gt;=date(#{beginDate}) and date(uploadTime) &lt;= date(#{endDate})",
            "</if>",
            "<if test='testName !=null'>",
            "AND testName like '%testName%'",
            "order by uploadTime desc limit #{pageIndex},#{pageSize}",
            "</script>"})
    public List<DataUpload> listExperiment(Map<String,Object> input);
}