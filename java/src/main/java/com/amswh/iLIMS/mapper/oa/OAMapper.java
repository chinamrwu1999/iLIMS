package com.amswh.iLIMS.mapper.oa;


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

    /**业务说明：
     * 查询OA上最新的正常订单（艾米森销售订单）
     * @param updateTime
     * @return
     */
    @Select(" select  id, sqr 申请人, ywy 业务员, ddbh 订单编号, khmc 客户名称 , xsms 销售模式, xdrq  申请日期, fhrq  发货日期\n" +
            " from formtable_main_20 WHERE xsms IS NOT NULL AND ddbh !='' AND DATE(xdrq)> date(#{updateTime})")
    public List<Map<String,Object>> queryLatestNormalOrder(String updateTime);


    /**
     * 根据订单编号查询艾米森销售订单的明细
     * @param orderId
     * @return
     */
    @Select("SELECT   cphh productCode, cpmc productName, gg specification, sl3 quantity,xmmc projectName FROM formtable_main_20_dt1  A "+
            "LEFT JOIN formtable_main_20 main ON A.mainId=main.Id "+
            "WHERE hjje >0 AND main.ddbh=#{mainId} ")
    public List<Map<String,Object>> queryNormalOrderDetail(String orderId);


    /**
     * 查询OA上最新的客户名单，用于同步到本地LIS系统
     * @return
     */
    @Select("SELECT id oaId,name customerName,concat(createDate,' ',createTime) createTime, concat(lastupdateddate,' ',lastupdatedtime) updateTime "
            +"FROM crm_customerInfo WHERE date(createDate) > date(#{lastDate})")
    public List<Map<String,String>> queryLatestOACustomerInfo(String lastDate);




}
