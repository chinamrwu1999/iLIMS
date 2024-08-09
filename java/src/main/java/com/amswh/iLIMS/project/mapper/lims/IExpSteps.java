package com.amswh.iLIMS.project.mapper.lims;

import com.amswh.iLIMS.project.domain.ExpSteps;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface IExpSteps extends BaseMapper<ExpSteps> {

    @Select({"<script>",
            "SELECT S.stepId,S.stepName,S.reagentId,S.productCode,",
            "R.model,R.spec,R.`name` as reagentName,",
            "B.batchNo,B.quantity,B.remaining,B.produceDate,B.expireDate",
            "FROM expSteps S LEFT JOIN reagent R on S.reagentId= R.id",
            "LEFT JOIN reagentBatch B ON B.reagentId=R.id",
            "WHERE S.productCode=#{productCode}  ",
            "AND remaining>0 AND DATE(expireDate) > DATE(now())",
            "ORDER BY S.id" ,
            "</script>"})
    public List<Map<String,Object>> getExpStepConfig(String productCode);





}
