package com.amswh.iLIMS.project.mapper.lims;
import com.amswh.iLIMS.project.domain.Order;
import com.amswh.iLIMS.project.domain.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;


public interface IOrder extends BaseMapper<Order> {

    /**
     * 根据订单发货条码查找产品和项目信息
     * @param barCode
     * @return
     */
     @Select("SELECT P.*  "+
     "FROM shipItem SI "+
     "LEFT JOIN orderShip OS ON SI.shipId=OS.id "+
     "LEFT JOIN orderItem OI ON OS.orderNo=OI.orderNo and SI.itemId=OI.id "+
     "LEFT JOIN product P ON OI.productCode=P.code "+
     "WHERE SI.barCode=#{barCode} or SI.udi=#{barCode}"
     )
     public Product getOrderProduct(String barCode);


    /**
     * 根据订单发货条码查找产品和项目信息
     * @param barCode
     * @return
     */
    @Select({"<script>",
            "SELECT P.*,PG.fullName customerName,PG.shortName,Party.externalId " ,
            "FROM shipItem SI " ,
            "INNER JOIN orderShip OS ON SI.shipId=OS.id " ,
            "LEFT JOIN orderItem OI ON OS.orderNo=OI.orderNo and SI.itemId=OI.id " ,
            "LEFT JOIN product P ON OI.productCode=P.code " ,
            "LEFT JOIN `Order` O ON O.orderNo=OI.orderNo " ,
            "LEFT JOIN partyGroup PG ON PG.partyId=O.customer " ,
            "LEFT JOIN party　ON party.partyId=PG.partyId " ,
            "WHERE SI.barCode=#{barCode} or SI.udi=#{barCode}",
            "</script>"})
    public Map<String,Object> getOrderInfo(String barCode);


    /**
     * 根据订单发货信息找到OA订单号
     * @param barCode
     * @return
     */
    @Select("SELECT orderNo "+
            "FROM shipItem SI "+
            "LEFT JOIN orderShip OS ON SI.shipId=OS.id "+
            "WHERE SI.barCode=#{barCode} or udi=#{barCode}"
    )
    public String getOrderNo(String barCode);



}