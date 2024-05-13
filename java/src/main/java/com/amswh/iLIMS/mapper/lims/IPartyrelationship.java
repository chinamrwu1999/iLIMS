package com.amswh.iLIMS.mapper.lims;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.amswh.iLIMS.domain.Partyrelationship;
import org.apache.ibatis.annotations.Insert;

import java.util.List;
import java.util.Map;


public interface IPartyrelationship extends BaseMapper<Partyrelationship> {

    @Insert({
            "<script>",
            "INSERT INTO partyRelationship(fromId,toId,typeId,throughDate) VALUES",
            "<foreach collection='list' item='entity' separator=','>",
            "(#{entity.fromId},#{entity.toId}, #{entity.typeId},#{entity.throughDate})",
            "</foreach>",
            "</script>"
    })
    int batchAddRelations(List<Map<String,Object>> entities);
}