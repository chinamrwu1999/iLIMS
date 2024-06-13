package com.amswh.iLIMS.mapper.lims;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.amswh.iLIMS.domain.AnalyteProcess;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface IAnalyteProcess extends BaseMapper<AnalyteProcess> {

    @Select({"<script>",
        "SELECT analyteCode FROM analyteProcess A where action='RECEIVE' AND status='success' AND createTime >= DATE_SUB(now(), INTERVAL 15 DAY))"
    "</script>"})
    public List<Map<String,Object>> listToTest();
}