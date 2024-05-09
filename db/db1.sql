CREATE TABLE IF NOT EXISTS  `party`(
    `partyId`   varchar(8)   NOT NULL primary key comment 'partyId 唯一标识一个人或组织机构的字符串',
    `partyType` varchar(12)  NOT NULL comment 'part类别代码,用于标识是个人(PSON)、公司(COMP)、医院(HSPT)、政府机构(GOVM)',
    `phone`     varchar(20) comment'电话',
    `email`    varchar(30) comment '电子邮箱',
     `createTime` datetime not null default now()
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT 'party 是个人和组织机构的最高类别';

CREATE TABLE IF NOT EXISTS  `person`(
    `partyId`   char(8)   NOT NULL primary key comment 'partyId 唯一标识一个人或组织机构的字符串',
    `name` varchar(12)  NOT NULL comment '人员姓名',
    `gender` char(1) not null comment `性别`,
    `birthday`     date comment'出生日期',
    `wechat`   varchar(36) comment '微信的openId'
    `IdCardType` char(2) comment '证件类型:Id身份证,PP护照',
    `IDNumber`    varchar(30) comment '证件号码',
    `height`   decimal(4,1) comment '身高',
    `weight`   decimal(3,1) comment '体重',
 ) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT '个人基本信息';


CREATE TABLE IF NOT EXISTS  `partyGroup`(
    `id` int unsigned not null AUTO_INCREMENT primary key comment '自增列主键',
    `partyId`   char(8)   NOT NULL primary key comment 'partyId 唯一标识一个人或组织机构的字符串',
    `name` varchar(12)  NOT NULL comment '人员姓名',
    `gender` char(1) not null comment `性别`,
    `birthday`     date comment'出生日期',
    `email`    varchar(30) comment '电子邮箱',
    `IdCardType` char(2) comment '证件类型:Id身份证,PP护照',
    `IDNumber`    varchar(30) comment '证件号码',
    `createTime` datetime not null default now()
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT 'party的微信账号信息';




