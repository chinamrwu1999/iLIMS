use iLIMS ;

CREATE TABLE IF NOT EXISTS  `party`(
    `partyId`   int unsigned NOT NULL AUTO_INCREMENT primary key comment 'partyId 唯一标识一个人或组织机构的字符串',
    `externalId` varchar(20) comment '与外部数据交互时,本party在外部系统的Id',
    `partyType` char(4)  NOT NULL comment 'part类别代码,用于标识是个人(PSON)、公司(COMP)、医院(HSPT)、政府机构(GOVM)',
    `phone`     varchar(20) comment'电话',
    `email`    varchar(30) comment '电子邮箱',
    `createTime` datetime not null default now()
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT 'party 是个人和组织机构的最高类别';

CREATE TABLE IF NOT EXISTS  `person`(
    `partyId`   int unsigned  NOT NULL primary key comment 'partyId 唯一标识一个人或组织机构的字符串',
    `name`   varchar(12)  NOT NULL comment '人员姓名',
    `gender` char(1) not null comment '性别',
    `birthday` date comment '出生日期',
    `age` smallint unsigned comment '年龄:年龄是变量。此处适用于某人享用服务时候的年龄',
    `wechat`   varchar(36) comment '微信的openId',
    `IdCardType` char(2) comment '证件类型:Id身份证,PP护照',
    `IDNumber`    varchar(30) comment '证件号码',
    `height`   decimal(4,1) comment '身高',
    `weight`   decimal(3,1) comment '体重'
 ) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT '个人基本信息';


CREATE TABLE IF NOT EXISTS  `partyGroup`(
    `partyId`   int unsigned NOT NULL primary key comment 'partyId 唯一标识一个人或组织机构的字符串',
    `fullName` varchar(60)  NOT NULL comment '全称',
    `shortName` varchar(20)  comment '简称',
    `country`   varchar(3) comment '国家代码,例如中国CHN、日本JPN、应该UK' ,
    `geoId`   varchar(10)  comment '行政区划代码'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT '组织机构party';

CREATE TABLE IF NOT EXISTS  `address`(
    `addressId` int unsigned NOT NULL AUTO_INCREMENT primary key comment '自增主键',
    `country` char(3)  not null default 'CHN' comment '三字母国家代码,默认中国CHN',
    `geoId` varchar(10)  comment '行政区划代码,如果有',
    `stree` varchar(120)  comment '街道详细地址',
    `type` varchar(30) comment '地址类别：常驻地址、收货地址',
    `createTime` datetime not null default now()
) ENGINE=InnoDB AUTO_INCREMENT=1  DEFAULT CHARSET=utf8mb4 COMMENT '地址信息';


CREATE TABLE IF NOT EXISTS  `partyAddress`(
      `partyId` int unsigned not null,
      `addressId` int unsigned not null,
      primary key(`partyId`,`addressId`)
) ENGINE=InnoDB comment 'party与地址关联信息' ;

CREATE TABLE IF NOT EXISTS  `relationshipType`(
    `id`  int unsigned NOT NULL primary key AUTO_INCREMENT comment '自增主键',
    `name` varchar(60)  NOT NULL comment '关系名称',
    `parentId` int unsigned ,
    `description` char(1)  comment '简称',
    `createTime` datetime not null default now()
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT '关系类型';

CREATE TABLE IF NOT EXISTS  `partyRelationship`(
    `id` int unsigned NOT NULL AUTO_INCREMENT primary key comment '自增主键',
    `fromId`  int unsigned  NOT NULL  comment '关系甲方',
    `toId`    int unsigned  NOT NULL comment '关系的乙方',
    `typeId` int  not null  comment '关系类型Id, references relationshipType(id)',
    `throughDate` date comment '关系的有效截至日期,null表示无期',
    `createTime` datetime not null default now()
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT 'party之间的关系';

CREATE TABLE IF NOT EXISTS  `orderType`( 
    `id` int unsigned NOT NULL primary key AUTO_INCREMENT comment '自增主键',
    `name` varchar(20) not null unique comment '订单编号',
    `parentId` int not null   comment '拉来订单的销售员Id;references party(partyId)',
    `description` int not null comment '买单者:references party(partyId)'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT '订单类型';

CREATE TABLE IF NOT EXISTS  `order`( 
    `id` int unsigned NOT NULL primary key AUTO_INCREMENT comment '自增主键',
    `orderNo` varchar(20) not null unique comment '订单编号',
    `salesPerson` int not null comment '拉来订单的销售员Id: references party(partyId)',
    `customer` int not null   comment '买单者: references party(partyId)',
    `typeId` int not null    comment '订单类型Id',
    `createTime` datetime not  null default now()
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT '订单信息';

CREATE TABLE IF NOT EXISTS  `orderItem`( 
    `id` int unsigned NOT NULL primary key AUTO_INCREMENT comment '自增主键',
    `orderNo` varchar(20) not null unique comment '订单编号',
    `productId` int not null  comment '订单产品代码',
    `quantity` int not null  comment '数量',
    `createTime` datetime not  null default now()
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT '订单购买产品清单';

CREATE TABLE IF NOT EXISTS  `orderAddress`( 
    `id` int unsigned NOT NULL primary key AUTO_INCREMENT comment '自增主键',
    `orderNo` varchar(20) not null unique comment '订单编号',
    `name` varchar(20) not null comment '收货人姓名',
    `phone` int not null   comment '买单者: references party(partyId)',
    `geoCode` varchar(8)   comment '收货人的省市区行政编码',
    `address` varchar(80) not null    comment '详细收货地址',
    `deadline` date  comment '最迟应发货日期',
    `createTime` datetime not  null default now()
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT '订单发货信息';

CREATE TABLE IF NOT EXISTS  `orderShip`(
    `id` int unsigned NOT NULL primary key AUTO_INCREMENT comment '自增主键',
    `orderNo` varchar(20) NOT NULL comment '订单编号',
    `partyId` int unsigned NOT NULl comment '经办人',
    `expressNo` varchar(50) comment '快递单号',
    `expId`   varchar(8) comment '快递公司代码:顺丰SF、京东JD、圆通YT、申通ST、中通ZT、韵达YT、中铁快运ZTKY',
    `createTime` DATETIME NOT NULL default now() comment '实际发货日期'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT '订单发货信息';


CREATE TABLE IF NOT EXISTS  `shipItem`(
    `id` int unsigned NOT NULL primary key AUTO_INCREMENT comment '自增主键',
    `shipId` int unsigned NOT NULL comment 'orderShip表自增主键',
    `barCode` varchar(30) NOT NULl comment '采样管上的条码',
    `UDI` varchar(50) comment 'UDI码',
    `createTime` DATETIME NOT NULL default now()
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT '发货明细';