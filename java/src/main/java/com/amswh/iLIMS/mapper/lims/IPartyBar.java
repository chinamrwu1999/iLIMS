package com.amswh.iLIMS.mapper.lims;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.amswh.iLIMS.domain.PartyBar;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface IPartyBar extends BaseMapper<PartyBar> {


    /**
     * 根据条码追溯艾米森订单发货记录，查找条码所属产品类别、销往客户名称
     * @param barCode
     * @return
     */
    @Select("SELECT A.barCode," +
            "B.orderNo," +
            "C.productId,product.code productCode,product.name productName" +
            "O.customer," +
            "PG.fullName,PG.shortName," +
            "P.partyId,"+
            "P.externalId AS partnerId," +
            "B.createTime shipTime,"+
            "O.createTime orderTime "+
            "FROM shipItem A" +
            "LEFT JOIN orderShip B   ON A.shipId=B.id" +
            "LEFT JOIN orderItem C   ON B.orderNo=C.orderNo " +
            "LEFT JOIN `order` O     ON O.orderNo=C.orderNo " +
            "LEFT JOIN partyGroup PG ON PG.partyId=O.customer " +
            "LEFT JOIN party P ON P.partyId=PG.partyId " +
            "LEFT JOIN product  ON C.productId=product.id "+
            "WHERE A.barCode=#{barCode} ")
    public Map<String,Object> findPartner(String barCode);

    /**
     * 根据partyId获取绑定的检测条码信息
     * @param partyIds
     */

    @Select({"<script>",
    "select PB.partyId,PB.barCode,age,A.analyteCode,productCode,A.createTime,P.name productName,AP.action,AP.status,",
    "PS.name,PS.gender,PC.contact phone ",
    "FROM partyBar PB ",
    "LEFT JOIN PartnerBar PB1 ON PB1.barCode=PB.barCode ",
    "INNER JOIN analyte A ON A.barCode=PB.barCode ",
    "LEFT JOIN analyteProcess AP ON AP.analyteCode=A.analyteCode",
    "LEFT JOIN product P ON P.code=PB1.productCode ",
    "LEFT JOIN PERSON PS ON PS.partyId=PB.partyID",
    "LEFT JOIN PartyContact PC ON PC.partyId=PS.partyId",
    "WHERE partyId IN ",
    "<foreach item='partyId' collection='partyIds' open='(' separator=',' close=')'>",
    " #{partyId}",
    " </foreach>",
    "AND A.createTime IN ",
      "SELECT MAX(createTime) FROM analyte WHERE barCode IN (SELECT barCode FROM partyBar WHERE partyId IN",
    "<foreach item='partyId' collection='partyIds' open='(' separator=',' close=')'>",
    " #{partyId}",
    " </foreach>",
    ")GROUP BY barCode)) and PC.contactType='mobile'",
    "</script>" })
    public List<Map<String,Object>> getBarStatus(List<String> partyIds);


    /**
     * 查询barCode 绑定的patient、partner、product信息
     * @param barCode
     * @return
     */

    @Select({"<script>",
            "SELECT PB1.partyId,PB1.barCode,PB1.age,PB2.partnerId partnerCode,PB2.productCode,",
            "PS.name,PS.gender,PS.birthday,PC.contact phone,P.name productName,PG.fullName partnerName",
            "FROM PartyBar PB1 LEFT JOIN PartnerBar PB2 ON PB1.barCode=PB2.barCode",
            "LEFT JOIN Person PS ON PS.partyId=PB1.partyId ",
            "LEFT JOIN PartyContact PC ON PC.partyId=PS.partyId",
            "LEFT JOIN PartyGroup PG ON PG.partyId=PB2.partnerId",
            "LEFT JOIN Product P ON P.code=PB2.productCode",
            "WHERE PB1.barCode=#{barCode} and PC.contactType='mobile' ",
            "</script>" })
    public Map<String,Object> getBindedInfo(String barCode);


}