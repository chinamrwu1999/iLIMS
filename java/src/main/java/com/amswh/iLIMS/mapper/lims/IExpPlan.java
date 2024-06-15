package com.amswh.iLIMS.mapper.lims;

import com.amswh.iLIMS.domain.ExpPlan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

public interface IExpPlan extends BaseMapper<ExpPlan> {

    @Insert({"<script>",
            "INSERT INTO expAnalyte(expPlanId,analyteCode) VALUES",
            "<foreach collection='items' item='item' separator=','>",
            "(#{expId},#{item})",
            "</foreach>",
            "</script>"})
    public void batchInsert(String expId, List<String> items);
}
