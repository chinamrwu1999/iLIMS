
CREATE TABLE IF NOT EXISTS `OAOrder`(
    `id` int unsigned not null AUTO_INCREMENT primary key,
    `orderId` varchar(20) NOT NULL comment 'OA上的订单编号',
    `requestId` int unsigned not null comment '流程ID',
    `orderType` varchar(20) comment '订单类别：正常销售、试用、赠送等',
    `seller` varchar(20) comment '销售元',
    `orgId` varchar(20)  comment '艾米森订单、或 医检所订单'
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

CREATE TABLE IF NOT EXISTS `OAOrderDeliver`(
    `id` int unsigned not null AUTO_INCREMENT primary key,
    `orderId` varchar(20) NOT NULL comment 'OA上的订单编号',
    `barCode` varchar(100) not null unique comment '发货条码号',
    `empId` varchar(12) not null comment '发货人工号',
    `createTime` datetime not null comment '本条记录产生的时间',
) ENGINE InnoDB AUTO_INCREMENT=1 comment 'OA订单发货信息';

CREATE TABLE IF NOT EXISTS `OACustomer`(
    `id` int unsigned not null AUTO_INCREMENT primary key,
    `customerId` varchar(20) NOT NULL comment '客户Id',
    `fullName` varchar(50) not null comment '客户全名',
    `alias`  varchar(50) not null comment '别名',
    `shortName` varchar(20) comment '简称',
    `createTime` datetime not null comment '本条记录产生的时间',
) ENGINE InnoDB AUTO_INCREMENT=1 comment 'OACRM上的客户';