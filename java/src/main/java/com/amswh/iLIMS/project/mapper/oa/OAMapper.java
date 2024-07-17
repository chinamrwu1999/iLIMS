package com.amswh.iLIMS.project.mapper.oa;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 *   业务说明：
 *   1. 本接口查询艾米森OA系统上的与订单相关的记录。
 *   2. OA上的订单签订主体分为艾米森生命科技与艾米森医检所，分别用不同的表；
 *   3. 订单又分为正常销售订单（Normal）、试用订单（trial)、赠送订单（donate)等
 *
 */


@Mapper
public interface OAMapper {


    @Select("SELECT * FROM sale_order_view where orderNo=#{orderNo}")
    public Map<String,Object> querySaleOrder(String orderNo);


    @Select("SELECT * FROM sale_orderItem_view where orderNo=#{orderNo}")
    public List<Map<String,Object>> queryOrderItems(String orderNo);

}
