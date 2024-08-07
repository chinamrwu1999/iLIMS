package com.amswh.iLIMS.project.mapper.lims;

import com.amswh.iLIMS.project.domain.ExpPlan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface IExpPlan extends BaseMapper<ExpPlan> {

    @Insert({"<script>",
            "INSERT INTO expAnalyte(expPlanId,analyteCode) VALUES",
            "<foreach collection='items' item='item' separator=','>",
            "(#{expId},#{item})",
            "</foreach>",
            "</script>"})
    public void batchInsert(String expId, List<String> items);


    /**
     * 查找待检测分析物
     * @return
     */
    @Select({"<script>",
        "SELECT PD.code productCode,PD.name productName,count(*) cnt",
        "FROM analyteProcess AP",
        "LEFT JOIN analyte A ON A.analyteCode=AP.analyteCode",
        "LEFT JOIN partnerBar PB ON A.barId=PB.barId",
        "LEFT JOIN Product PD ON PD.code=PB.productCode",
        "WHERE AP.action='RECEIVE' AND AP.status='success' AND AP.createTime >= DATE_SUB(now(), INTERVAL 5 DAY)",
        "AND AP.analyteCode not in (SELECT analyteCode FROM expAnalyte A1,expPlan A2 where A1.expPlanId=A2.id AND A2.createTime >= DATE_SUB(now(), INTERVAL 7 DAY))",
        "GROUP BY productCode,productName having count(*) >0 order by count(*) desc",
            "</script>"})
    public List<Map<String,Object>> AnalyteCountToTest();


    /**
     * 代检测分析物列表
     * @param productCode
     * @return
     */

    @Select({"<script>",
        "SELECT AP.analyteCode",
        "FROM analyteProcess AP",
        "LEFT JOIN analyte A ON A.analyteCode=AP.analyteCode",
        "LEFT JOIN partyBar PB ON A.barCode=PB.barCode",
        "WHERE AP.action='RECEIVE' AND AP.status='success' AND AP.createTime >= DATE_SUB(now(), INTERVAL 5 DAY)",
        "AND PB.productCode=#{productCode}",
        "AND AP.analyteCode not in (SELECT analyteCode FROM expAnalyte A1,expPlan A2 where A1.expPlanId=A2.id AND A2.createTime >= DATE_SUB(now(), INTERVAL 7 DAY))",
        "AND AP.analyteCode not   in (SELECT analyteCode FROM analyteProcess A where action='REVIEW1'  AND createTime >= DATE_SUB(now(), INTERVAL 7 DAY))",
        "ORDER BY AP.createTime asc",
        "</script>"})
    public List<String>  listAnalytesToTest(String productCode);


    @Select({"<script>",
            "select step.productCode,stepId,stepName,step.reagentId ,",
            "B.batchNo,B.remaining,CONCAT(R.name,'_',R.model) reagentName",
            "from expSteps step",
            "left join reagentBatch B ON step.reagentId=B.reagentId",
            "INNER JOIN reagent R ON R.id=B.reagentId",
            "where steps.productCode=#{productNo} and remaining &gt;0 order by step.name,remaining desc;",
            "</script>"})
    public List<Map<String,Object>> listReagentsOfExpSteps(String productNo);





}
