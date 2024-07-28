package com.amswh.iLIMS.project.mapper.lims;
import com.amswh.iLIMS.project.domain.Bar;
import com.amswh.iLIMS.project.domain.PcrData;
import com.amswh.iLIMS.project.domain.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface IBar extends BaseMapper<Bar> {

    @Insert({
            "<script>",
            "INSERT INTO Bar(barCode,productCode,batchNo) VALUES",
            "<foreach collection='list' item='entity' separator=','>",
            "(#{entity.barCode}, #{entity.productCode},#{entity.batchNo})",
            "</foreach>",
            "</script>"
    })
    public int generateBarCodes(List<Bar> entities);


    @Select("SELECT * FROM Bar where barCode=#{barCode}")
    public Bar getBar(String barCode);


    @Select("SELECT A.* FROM product A left join  Bar ON A.code=Bar.productCode where Bar.barCode=#{barCode}")
    public Product getProductOfBar(String barCode);

    /**
     * 根据扫码绑定信息获取Patient和age
     * @param barCode
     * @return
     */
    @Select("SELECT P.partyId,P.name,P.gender,date_format(P.birthday,'%Y-%m-%d') birthday,"+
            "P.IDCardType,P.IDNumber,PB.age FROM PartyBar PB,Person P "+
            "WHERE PB.partyId=P.partyId AND PB.barCode=#{barCode}")
    public Map<String,Object> getPatient(String barCode);


    @Select({"<script>",
            "select A.barCode,A.analyteCode," ,
            "PS.name,PS.gender,",
            "productCode,P.name productName",
            "AP.createTime,AP.action,AP.status",
            "FROM PartyBar PB ",
            "LEFT JOIN PartnerBar PB1 ON PB1.barCode=PB.barCode ",
            "LEFT JOIN analyte A ON A.barCode=PB.barCode ",
            "LEFT JOIN analyteProcess AP ON AP.analyteCode=A.analyteCode",
            "LEFT JOIN product P ON P.code=PB1.productCode ",
            "LEFT JOIN PERSON PS ON PS.partyId=PB.partyID",
            "WHERE A.analyteCode=#{analyteCode} ",
            "ORDER BY AP.createTime desc limit 1",
            "</script>" })
    public Map<String,Object> getAnalyteProgress(String analyteCode);


    @Select("SELECT barCode FROM analyte where analyteCode=#{analyteCode} or barCode=#{analyteCode}")
    public String getBarCode(String analyteCode);

    /**
     * 根据条码获取分析物。可能是多个分析物返回
     * @param barCode
     * @return 如果存在复检，则返回多个分析物
     */

    @Select("SELECT analyteCode,createTime FROM analyte WHERE barCode=#{barCode}")
    public List<Map<String,Object>> getAnalyteCodes(String barCode);


    @Select({"SELECT date_format(createTime,'%Y-%m-%d %H:%i:%s') bindingTime,",
            "case bindWay when 'wechat' then '微信扫码绑定' when 'api' then '通过客户API读取' when 'manual' then '手工录入' end as bindWay",
            "FROM PartyAnalyte PB where barCode =#{code} or barCode IN "+
            "(SELECT barCode FROM analyte WHERE analyteCode=#{code})"})
    public Map<String,Object>   getBindingTime(String code);


    @Select({"<script>",
            "SELECT analyteCode," +
            "case action ",
            "WHEN 'RECEIVE' then '收样' ",
            "WHEN 'RECHECK' THEN '复检' ",
            "WHEN 'TEST' THEN '实验检测' ",
            "WHEN 'RPTRV1' THEN '报告一审' ",
            "WHEN 'RPTRV2' THEN '报告二审核' ",
            "WHEN 'RPTRV3' THEN '报告三审' ",
            "WHEN 'RPTGT' THEN '报告生成' ",
            "WHEN 'RPTPUB' THEN '报告发布' ",
            "END AS action,",
            "case status WHEN 'success' then '成功' ",
            "WHEN 'fail' THEN '失败'",
            "END as status,remark",
            "createTime actionTime,employeeId",
            "FROM analyteProcess AP left join party P ON P.externalId=employeeId",
            "WHERE analyteCode in (SELECT analyteCode FROM analyte WHERE barCode=#{code} OR analyte=#{bode} )",
            "ORDER BY AP.createTime  ",
            "</script>"})
    public List<Map<String,Object>>  getBarProgress(String code);



}