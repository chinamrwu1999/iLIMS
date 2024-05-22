package com.amswh.iLIMS.mapper.lims;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.amswh.iLIMS.domain.PartyBar;
import org.apache.ibatis.annotations.Select;

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


}