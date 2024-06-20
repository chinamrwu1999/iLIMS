use iLIMS ;

CREATE TABLE IF NOT EXISTS  `party`(
    `partyId`   varchar(12)  primary key comment 'partyId 唯一标识一个人或组织机构的字符串',
    `externalId` varchar(20) comment '与外部数据交互时,本party在外部系统的Id',
    `partyType` varchar(10)  NOT NULL comment 'part类别代码,用于标识是个人(PSON)、公司(COMP)、医院(HSPT)、政府机构(GOVM),其中有一个特殊的表示root表示本系统的所有者',
    `createTime` datetime not null default now()
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT 'party 是个人和组织机构的最高类别';

CREATE TABLE IF NOT EXISTS  `person`(
    `partyId`   varchar(12) NOT NULL primary key comment 'partyId 唯一标识一个人或组织机构的字符串',
    `name`   varchar(12)  NOT NULL comment '人员姓名',
    `gender` char(1) not null comment '性别',
    `birthday` date comment '出生日期',
    `IdCardType` char(2) comment '证件类型:Id身份证,PP护照',
    `IDNumber`    varchar(30) comment '证件号码',
    `height`   decimal(4,1) comment '身高',
    `weight`   decimal(3,1) comment '体重'
 ) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT '个人基本信息';


CREATE TABLE IF NOT EXISTS  `PartyContact`(
  `id` int unsigned not null AUTO_INCREMENT primary key,
  `partyId`  varchar(12) not null,
  `contactType` varchar(20) not null comment '联系方式:mobile(手机号)、email、wechat、phone等',
  `contact` varchar(50) not null,
  `createTime` datetime not null default now(),
  unique (partyId,contact)
) comment 'Party的联系方式,例如电话、微信号等';


CREATE TABLE IF NOT EXISTS  `partyGroup`(
    `partyId`   varchar(12) NOT NULL primary key comment 'partyId 唯一标识一个人或组织机构的字符串',
    `fullName` varchar(60)  NOT NULL comment '全称',
    `shortName` varchar(20)  comment '简称'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT '组织机构party';

CREATE TABLE IF NOT EXISTS  `address`(
    `addressId` int unsigned NOT NULL AUTO_INCREMENT primary key comment '自增主键',
    `country` char(3)  not null default 'CHN' comment '三字母国家代码,默认中国CHN',
    `geoId` varchar(10)  comment '行政区划代码,如果有',
    `street` varchar(120)  comment '街道详细地址',
    `type` varchar(30) comment '地址类别：常驻地址、收货地址',
    `createTime` datetime not null default now()
) ENGINE=InnoDB AUTO_INCREMENT=1  DEFAULT CHARSET=utf8mb4 COMMENT '地址信息';



CREATE TABLE IF NOT EXISTS  `partyAddress`(
      `partyId`  varchar(12) not null,
      `addressId` int unsigned not null,
      primary key(`partyId`,`addressId`)
) ENGINE=InnoDB comment 'party与地址关联信息' ;

/* CREATE TABLE IF NOT EXISTS  `relationshipType`(
    `id`  int unsigned NOT NULL primary key AUTO_INCREMENT comment '自增主键',
    `name` varchar(60)  NOT NULL comment '关系名称',
    `parentId`  varchar(12) ,
    `description` char(1)  comment '简称',
    `createTime` datetime not null default now()
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT '关系类型'; */





CREATE TABLE IF NOT EXISTS  `partyRelationship`(
    `id` int unsigned NOT NULL AUTO_INCREMENT primary key comment '自增主键',
    `fromId`   varchar(12)  NOT NULL  comment '关系甲方',
    `toId`     varchar(12)  NOT NULL comment '关系的乙方',
    `relationType` varchar(10)  not null  comment '关系类型',
    `throughDate` date comment '关系的有效截至日期,null表示无期',
    `createTime` datetime not null default now()
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT 'party之间的关系';




CREATE TABLE IF NOT EXISTS  `orderType`( 
    `id` int unsigned NOT NULL primary key AUTO_INCREMENT comment '自增主键',
    `name` varchar(20) not null unique comment '类型名称',
    `parentId` int not null   comment '上一级类型',
    `remark` int not null comment '备注'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT '订单类型';

CREATE TABLE IF NOT EXISTS  `order`( 
    `id` int unsigned NOT NULL primary key AUTO_INCREMENT comment '自增主键',
    `orderNo` varchar(20) not null unique comment '订单编号',
    `salesPerson` int not null comment '拉来订单的销售员Id: references party(partyId)',
    `customer`  varchar(12) not null   comment '买单者: references party(partyId)',
    `typeId` int not null    comment '订单类型Id',
    `createTime` datetime not  null default now()
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT '订单信息';

CREATE TABLE IF NOT EXISTS  `orderItem`( 
    `id` int unsigned NOT NULL primary key AUTO_INCREMENT comment '自增主键',
    `orderNo` varchar(20) not null unique comment '订单编号',
    `productCode` varchar(20) not null  comment '订单产品代码',
    `quantity` int not null  comment '数量',
    `createTime` datetime not  null default now()
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT '订单购买产品清单';


CREATE TABLE IF NOT EXISTS  `orderShip`(
    `id` int unsigned NOT NULL primary key AUTO_INCREMENT comment '自增主键',
    `orderNo` varchar(20) NOT NULL comment '订单编号',
    `partyId` int unsigned NOT NULl comment '经办人',
    `expressNo` varchar(50) comment '快递单号',
    `expressId`   varchar(8) comment '快递公司代码:顺丰SF、京东JD、圆通YT、申通ST、中通ZT、韵达YT、中铁快运ZTKY',
    `createTime` DATETIME NOT NULL default now() comment '实际发货日期'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT '订单发货信息';


CREATE TABLE IF NOT EXISTS  `shipItem`(
    `id` int unsigned NOT NULL primary key AUTO_INCREMENT comment '自增主键',
    `itemId` int unsigned NOT NULL comment '外键引用orderItem表的id',
    `shipId` int unsigned NOT NULL comment 'orderShip表自增主键',
    `barCode` varchar(60) comment '条码',
    `udi` varchar(60) ,
    `createTime` DATETIME NOT NULL default now()
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT '发货明细';

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


CREATE TABLE IF NOT EXISTS `geo`(
    geoId varchar(20) not null primary key,
    geoName varchar(40) not null,
    geoType varchar(30) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '国家地理（行政区划表）';

CREATE TABLE IF NOT EXISTS `GeoAssoc`(
    id INT unsigned not null primary key AUTO_INCREMENT,
    geoId varchar(20) not null,
    geoIdTo varchar(20) not null,
    assocType varchar(30) not null 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '地理区域之间的关系';

CREATE TABLE IF NOT EXISTS  `partyGeo`(
    `id` int unsigned NOT NULL AUTO_INCREMENT primary key comment '自增主键',
    `partyId`   char(8)  NOT NULL  comment 'partyId',
    `geoId`     varchar(20)  NOT NULL comment '地理位置Id',
    `throughDate` date default null,
    `createTime` datetime not null default now()
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT 'party的地理位置关系';