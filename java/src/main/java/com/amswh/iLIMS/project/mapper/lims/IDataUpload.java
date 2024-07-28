package com.amswh.iLIMS.project.mapper.lims;
import com.amswh.iLIMS.project.domain.DataUpload;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
            "</if>",
            "order by uploadTime desc ",
            "<if test='offset &gt;0 and pageSize &gt;0'>",
            "limit #{offset},#{pageSize}",
            "</if>",
            "</script>"})
    public List<DataUpload> listExperiment(Map<String,Object> input);

    @Select({"<script>",
            "SELECT count(*) cnt FROM DataUpload WHERE 1=1",
            "<if test='beginDate!=null and endDate !=null'>",
            "AND date(uploadTime) &gt;=date(#{beginDate}) and date(uploadTime) &lt;= date(#{endDate})",
            "</if>",
            "<if test='testName !=null'>",
            "AND testName like '%testName%'",
            "</if>",
            "</script>"})
    public Integer getExperimentCount(Map<String,Object> input);
}