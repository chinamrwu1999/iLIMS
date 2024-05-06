
CREATE TABLE IF NOT EXISTS `OAOrder`(
    `id` int unsigned not null AUTO_INCREMENT primary key,
    `orderId` varchar(20) NOT NULL comment 'OA上的订单编号',
    `requestId` int unsigned not null comment '流程ID',
    `orderType` varchar(20) comment '订单类别：正常销售、试用、赠送等',
    `seller` varchar(20) comment '销售元',
    `orgId` varchar(20)  comment '艾米森订单、或 医检所订单',
    `customerId` varchar(20) comment '客户代码:对应样本来源',
    `orderTime` datetime not null comment 'OA上的订单日期',
    `createTime` datetime not null comment '本条记录产生的时间'
) ENGINE InnoDB AUTO_INCREMENT=1 comment '从OA系统同步过来的B端客户信息';

CREATE TABLE IF NOT EXISTS `OAOrderDetail`(
    `id` int unsigned not null AUTO_INCREMENT primary key,
    `orderId` varchar(20) NOT NULL comment 'OA上的订单编号',
    `productCode` int unsigned not null comment '销售产品的代码',
    `quantity` varchar(20) comment '数量',
    `createTime` datetime not null comment '本条记录产生的时间',
    unique(`orderId`,`productCode`)
) ENGINE InnoDB AUTO_INCREMENT=1 comment 'OA订单销售详细产品';

CREATE TABLE IF NOT EXISTS `OAOrderExpress`(
     `id` int unsigned not null AUTO_INCREMENT primary key,
     `orderId`    varchar(20) NOT NULL comment 'OA上的订单编号',
     `expressNo`  varchar(20) NOT NULL comment '订单对应的快递单号',
     `expressCode` varchar(12) not null comment '快递服务商代码:SF表示顺丰,JD表示京东,YT表示圆通,ZT表示中通等',
     `empId` varchar(12) not null comment '发货人工号',
     `createDate` datetime not null comment '本条记录生成时间，也表示发货日期',
     unique(`orderId`,`expressNo`)
) ENGINE InnoDB AUTO_INCREMENT=1 comment 'OA订单快递发货信息';


CREATE TABLE IF NOT EXISTS `OAExpressDetail`(
    `id` int unsigned not null AUTO_INCREMENT primary key,
    `expressId` varchar(20) NOT NULL comment '外键:OAOrderExpress表的id列',
    `barCode` varchar(100) not null unique comment '发货条码号',
    unique(`expressId`,`barCode`)
) ENGINE InnoDB AUTO_INCREMENT=1 comment 'OA订单发货快递详情';

CREATE TABLE IF NOT EXISTS `OACustomer`(
    `id` int unsigned not null AUTO_INCREMENT primary key,
    `customerId` varchar(20) NOT NULL comment '客户Id',
    `fullName` varchar(50) not null comment '客户全名',
    `alias`  varchar(50) not null comment '别名',
    `shortName` varchar(20) comment '简称',
    `createTime` datetime not null comment '本条记录产生的时间'
) ENGINE InnoDB AUTO_INCREMENT=1 comment 'OACRM上的客户';


CREATE TABLE IF NOT EXISTS `OACustomerRelation`(
    `id` int unsigned not null AUTO_INCREMENT primary key,
    `customerFrom` varchar(20) NOT NULL comment '上一级客户Id',
    `customerTo` varchar(50) not null comment '子客户Id',
    `relationType`  varchar(10) not null comment '客户之间的关心：总公司-分公司、加盟门店等',
    `createTime` datetime not null comment '本条记录产生的时间'
) ENGINE InnoDB AUTO_INCREMENT=1 comment 'OA上CRM客户自己的关系';
