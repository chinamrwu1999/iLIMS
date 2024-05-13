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

    /**
     * 查询OA上最新的客户名单，用于同步到本地LIS系统
     * @return
     */
    @Select("SELECT id oaId,name customerName,concat(createDate,' ',createTime) createTime, concat(lastupdateddate,' ',lastupdatedtime) updateTime "
            +"FROM crm_customerInfo WHERE date(createDate) > date(#{lastDate})")
    public List<Map<String,String>> queryLatestOACustomerInfo(String lastDate);



    /**************************************************************************************/
    @Select("SELECT id,cpmc,xmmc,xmbh FROM uf_cpxx")
    public List<Map<String,Object>> getOAProductList();

    /**
     * 根据订单号查询艾米森销售订单
     * @param orderNo
     * @return
     */
    @Select(" select  id, sqr applicant, ywy empId, ddbh orderNo, khmc customerId , xsms saleType, xdrq  applyDate, fhrq  deliverDate" +
            " from formtable_main_20 WHERE ddbh =#{orderNo}")
    public List<Map<String,Object>> queryOrderByNo(String orderNo);


    /**
     * 查询艾米森销售订单包含的产品信息
     * @param orderNo
     * @return
     */

    @Select("SELECT  item.cpmc productName, item.gg spec, item.sl3 数量3 quanty,"+
            "P.xmmc projectName,P.xmbh projectNo " +
            "FROM formtable_main_20_dt1 item " +
            "LEFT JOIN  formtable_main_20 main ON main.id=item.mainId "+
            "LEFT JOIN  uf_cpxx P ON item.cphh=P.id " +
            "WHERE item.hjje >0   AND main.ddbh=#{orderNo}")
    public List<Map<String,Object>> queryOrderItems(String orderNo);


    /**
     * 根据艾米森订单号查询客户名称
     * @param orderNo
     * @return
     */
    @Select("SELECT id, name customerName " +
            "FROM formtable_main_20 T1 left join crm_customerInfo T2 ON T1.khmc=T2.id "+
            "WHERE T1.ddbh =#{orderNo}")
    public List<Map<String,Object>> queryCustomerByOrderNo(String orderNo);


}
