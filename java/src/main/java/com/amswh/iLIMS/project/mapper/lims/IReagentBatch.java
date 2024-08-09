package com.amswh.iLIMS.project.mapper.lims;

import com.amswh.iLIMS.project.domain.ReagentBatch;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface IReagentBatch extends BaseMapper<ReagentBatch> {

    @Select({"<script>",
            "SELECT B.reagentId,name reagentName,model,spec,batChNo,quantity,remaining,produceDate,expireDate ",
            "FROM reagent R left join reagentBatch B ON B.reagentId=R.id",
            "</script>"})
    public List<Map<String,Object>> listReagentBatch();

    @Select("SELECT * FROM reagentBatch WHERE reagentId=#{reagentId} and batchNo=#{batchNo}")
    public ReagentBatch getBatch(String reagentId,String batchNo);



}
