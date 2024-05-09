 #查询艾米森销售订单
 select  id, sqr 申请人, ywy 业务员, ddbh 订单编号, khmc 客户名称 , xsms 销售模式, xdrq  申请日期, fhrq  发货日期
 from formtable_main_20 WHERE xsms IS NOT NULL AND ddbh !='' AND xdrq LIKE '2024%' limit 20;
 #查询艾米森销售订单详细
 select   cphh 产品货号, cpmc 产品名称, gg 规格, sl3 数量3,xmmc 项目名称
 from formtable_main_20_dt1 
 where hjje >0  
 ;
 #查询所有客户信息
  select id,name,createDate,createTime,lastupdateddate,lastupdatedtime from crm_customerInfo;


 select  id, sqr 申请人, ywy 业务员, ddbh 订单编号, khmc 客户名称 , xsms 销售模式, xdrq  申请日期, fhrq  发货日期
 from formtable_main_20 WHERE xsms IS NOT NULL AND ddbh !='' AND ddbh='202008002XD' ;

  202008002XD