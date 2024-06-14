package com.amswh.iLIMS.mapper.lims;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.amswh.iLIMS.domain.AnalyteProcess;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface IAnalyteProcess extends BaseMapper<AnalyteProcess> {

    /**
     * 查找待检测分析物
     * @return
     */
    @Select({"<script>",
    "SELECT AP.analyteCode,PS.name,PD.productCode,PD.productName,PG.partyId partnerId,PG.fullName",
    "FROM analyteProcess AP where action='RECEIVE' AND status='success' AND createTime >= DATE_SUB(now(), INTERVAL 7 DAY) " +
    "LEFT JOIN analyte A ON A.analyteCode=AP.analyteCode ",
    "LEFT JOIN partyBar PB ON A.barCode=PB.barCode ",
    "LEFT JOIN PartnerBar PB1 ON A.barCode=PB1.barCode",
    "LEFT JOIN Person PS ON PS.partyId=PB.partyId",
    "LEFT JOIN PartyGroup PG ON PG.partyId=PB1.partyId",
    "LEFT JOIN Product PD ON PD.productCode=PB1.productCode",
    "WHERE AP.analyteCode not in (SELECT analyteCode FROM expAnalyte A1,expPlan A2 where A1.expPlanId=A2.id AND A2.createTime >= DATE_SUB(now(), INTERVAL 14 DAY))",
    "AND AP.analyteCode not   in (SELECT analyteCode FROM analyteProcess A where action='REVIEW1'  AND createTime >= DATE_SUB(now(), INTERVAL 14 DAY)) ",
    "<if test='productCode!=null'>" ,
            "AND PB1.productCode=#{productCode}",
    "</if>",
    "<if test='analyteCode!=null'>" ,
    "AND A.analyteCode=#{analyteCode}",
    "</if>",
    "</script>"})
    public List<Map<String,Object>> listToTest(Map<String,Object> condition);

    /**
     * 查找待报告一审分析物
     * @return
     */
    @Select({
"<script>",
        "SELECT A.analyteCode,PS.name,PD.productCode,PD.productName,PG.partyId partnerId,PG.fullName",
        "FROM analyte A ON A.analyteCode=AP.analyteCode",
        "LEFT JOIN partyBar PB ON A.barCode=PB.barCode",
        "LEFT JOIN PartnerBar PB1 ON A.barCode=PB1.barCode",
        "LEFT JOIN Person PS ON PS.partyId=PB.partyId",
        "LEFT JOIN PartyGroup PG ON PG.partyId=PB1.partyId",
        "LEFT JOIN Product PD ON PD.productCode=PB1.productCode",
        "WHERE A.analyteCode IN (",
        "SELECT DISTINCT analyteCode FROM PCRData PD INNER JOIN DataUpload  DU ON PD.uploadId=DU.id",
        "WHERE DU.uploadTime > DATE_SUB(now(), INTERVAL 7 DAY) AND ( analyteCode NOT LIKE 'PC%' AND analyteCode NOT LIKE 'NC%')",
        "AND testName not like 'ACK3S%'",
        ") AND A.analyte not  in (",
        "SELECT analyteCode FROM analyteProcess A where action='REVIEW1'  AND createTime >= DATE_SUB(now(), INTERVAL 14 DAY))",
        "<if test='productCode!=null'>" ,
        "AND PB1.productCode=#{productCode}",
        "</if>",
        "<if test='analyteCode!=null'>" ,
        "AND A.analyteCode=#{analyteCode}",
        "</if>",
 "</script>"})
    public List<Map<String,Object>> listToReview1(Map<String,Object> condition);

    /**
     * 查找待二审列表
     * @return
     */
    @Select({"<script>",
        "SELECT AP.analyteCode,PS.name,PD.productCode,PD.productName,PG.partyId partnerId,PG.fullName",
        "FROM analyteProcess AP where action='REVIEW1' AND status='success' AND createTime >= DATE_SUB(now(), INTERVAL 7 DAY) " +
        "LEFT JOIN analyte A ON A.analyteCode=AP.analyteCode ",
        "LEFT JOIN partyBar PB ON A.barCode=PB.barCode ",
        "LEFT JOIN PartnerBar PB1 ON A.barCode=PB1.barCode",
        "LEFT JOIN Person PS ON PS.partyId=PB.partyId",
        "LEFT JOIN PartyGroup PG ON PG.partyId=PB1.partyId",
        "LEFT JOIN Product PD ON PD.productCode=PB1.productCode",
        "WHERE AP.analyteCode not in (SELECT analyteCode FROM analyteProcess AP1 WHERE action='REVIEW2'  AND AP1.createTime >= DATE_SUB(now(), INTERVAL 14 DAY))",
        "</script>"})
    public List<Map<String,Object>> listToReview2(Map<String,Object> condition);
    /**
     * 查找待三审列表
     * @return
     */
    @Select({"<script>",
            "SELECT AP.analyteCode,PS.name,PD.productCode,PD.productName,PG.partyId partnerId,PG.fullName",
            "FROM analyteProcess AP where action='REVIEW1' AND status='success' AND createTime >= DATE_SUB(now(), INTERVAL 7 DAY) " +
            "LEFT JOIN analyte A ON A.analyteCode=AP.analyteCode ",
            "LEFT JOIN partyBar PB ON A.barCode=PB.barCode ",
            "LEFT JOIN PartnerBar PB1 ON A.barCode=PB1.barCode",
            "LEFT JOIN Person PS ON PS.partyId=PB.partyId",
            "LEFT JOIN PartyGroup PG ON PG.partyId=PB1.partyId",
            "LEFT JOIN Product PD ON PD.productCode=PB1.productCode",
            "WHERE AP.analyteCode not in (SELECT analyteCode FROM analyteProcess AP1 WHERE action='REVIEW3'  AND AP1.createTime >= DATE_SUB(now(), INTERVAL 14 DAY))",
            "</script>"})
    public List<Map<String,Object>> listToReview3(Map<String,Object> condition);

    /**
     * 通用样本状态查询
     * @param condition
     * @return
     */

    @Select({"<script>",
            "SELECT A.barCode,A.analyteCode,PS.name,PD.productCode,PD.productName,PG.partyId partnerId,PG.fullName",
            "LEFT JOIN analyte A ON A.analyteCode=AP.analyteCode ",
            "LEFT JOIN partyBar PB ON A.barCode=PB.barCode ",
            "LEFT JOIN PartnerBar PB1 ON A.barCode=PB1.barCode",
            "LEFT JOIN Person PS ON PS.partyId=PB.partyId",
            "LEFT JOIN PartyGroup PG ON PG.partyId=PB1.partyId",
            "LEFT JOIN Product PD ON PD.productCode=PB1.productCode",
            "WHERE AP.analyteCode not in (SELECT analyteCode FROM analyteProcess AP1 WHERE action='REVIEW3'  AND AP1.createTime >= DATE_SUB(now(), INTERVAL 14 DAY))",
            "</script>"})
    public List<Map<String,Object>> queryAnalytesStatus(Map<String,Object> condition);



    @Insert({"<script>",
    "INSERT INTO analyteProcess(analyteCode,action,status,employeeId) VALUES",
    "<foreach item='item' collection='items' separator=',' >",
    " (#{item.analyteCode},#{item.action},#{item.status},#{item.employeeId})",
    " </foreach>",
    "</script>"})
    public Integer batchProcessAnalytes(List<Map<String,String>> items);




}