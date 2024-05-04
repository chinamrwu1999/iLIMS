use myLIMS;

CREATE TABLE IF NOT EXISTS `disease`(
  `code` varchar(8) not null primary key comment '疾病代码',
  `name` varchar(80) not null comment '疾病名称'
) COMMENT '疾病';

CREATE TABLE IF NOT EXISTS `project`(
  `code` varchar(20) not null primary key comment '项目代码',
  `name` varchar(80) not null comment '项目名称',
  `type` varchar(8) not null comment '项目类型:分为S单癌、M多癌 项目'
) COMMENT '研发项目信息';

CREATE TABLE IF NOT EXISTS `projectDisease`(
  `projectCode` varchar(20) not null comment '项目代码',
  `diseaseCode` varchar(80) not null comment '疾病代码',
  primary key (`projectCode`,`diseaseCode`)
) COMMENT '研发项目针对的疾病';

CREATE TABLE IF NOT EXISTS `product`(
  `code` varchar(10) not null primary key comment '产品代码',
  `name` varchar(80) not null comment '产品名称',
  `spec` varchar(80) comment '产品规格'
) COMMENT '产品或服务基本：产品或服务是研发项目的成果';

CREATE TABLE IF NOT EXISTS `enums`(
  `id` int not null primary key AUTO_INCREMENT,
  `code` varchar(10) not null comment '枚举代码',
  `name` varchar(80) not null comment '名称',
  `type` varchar(10) comment '枚举类型',
  `index` smallint  comment '显示顺序'
) COMMENT '枚举类型表';




