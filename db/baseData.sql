use iLIMS;

CREATE TABLE IF NOT EXISTS `disease`(
  `code` varchar(8) not null primary key comment '疾病代码',
  `name` varchar(80) not null comment '疾病名称'
) COMMENT '疾病';

CREATE TABLE IF NOT EXISTS `project`(
  `code` varchar(20) not null primary key comment '项目代码',
  `name` varchar(80) not null comment '项目名称',
  `type` varchar(8) not null comment '项目类型:分为S单癌、M多癌 项目',
  `provider` varchar(20)  comment '项目的研究者,外键,引用party(partyId)'
) COMMENT '研发项目信息';

INSERT INTO project(code,name,`type`) VALUES('AD01','艾长康(肠癌粪便项目)','S');
INSERT INTO project(code,name,`type`) VALUES('AD06','艾长健(肠癌血液项目)','S');
INSERT INTO project(code,name,`type`) VALUES('AD07','艾馨甘(肝癌血液项目)','S');
INSERT INTO project(code,name,`type`) VALUES('AD10','艾思宁(食管癌血液项目)','S');
INSERT INTO project(code,name,`type`) VALUES('AD12','艾消安(消化五癌血液项目)','P');
INSERT INTO project(code,name,`type`) VALUES('AD02','艾宫舒(宫颈癌脱落细胞项目)','S');
INSERT INTO project(code,name,`type`) VALUES('AD03','艾宫乐(子宫内膜癌脱落细胞项目)','S');
INSERT INTO project(code,name,`type`) VALUES('AD04','艾宫婷(妇科两癌联检脱落细胞项目)','P');
INSERT INTO project(code,name,`type`) VALUES('AD08','艾卫元(胃癌血液项目)','S');
INSERT INTO project(code,name,`type`) VALUES('AD09','艾怡宁(胰腺癌血液项目)','S');
INSERT INTO project(code,name,`type`) VALUES('AD11','艾光乐(膀胱癌尿液项目)','S');
INSERT INTO project(code,name,`type`) VALUES('AD21','泌尿四癌尿检项目(膀胱癌、肾孟癌、输尿管癌、前列腺癌)','P');
INSERT INTO project(code,name,`type`) VALUES('AD22','消化三癌血检项目(肠癌、胃癌、肝癌)','P');
INSERT INTO project(code,name,`type`) VALUES('AD23','高发四癌血液检测(肠癌、胃癌、肝癌、肺癌)','P');
INSERT INTO project(code,name,`type`) VALUES('AD24','高发五癌血检项目(肠癌、胃癌、肝癌、食管癌、肺癌)','P');
INSERT INTO project(code,name,`type`) VALUES('AD25','高发六癌血检项目(肠癌、胃癌、肝癌、食管癌、胰腺癌、肺癌)','P');
INSERT INTO project(code,name,`type`) VALUES('AD99','十二癌(男性)','P');
INSERT INTO project(code,name,`type`) VALUES('AD13','十二癌(女性)','P');
INSERT INTO project(code,name,`type`) VALUES('AD14','艾斐畅(肺癌血液项目)','S');
INSERT INTO project(code,name,`type`) VALUES('AD16','艾汝安(乳腺癌血液项目)','S');
INSERT INTO project(code,name,`type`) VALUES('AD17','艾馥明(乳腺癌、卵巢癌)','P');
INSERT INTO project(code,name,`type`) VALUES('AD15','艾鸾清(卵巢癌血液检项目)','S');


CREATE TABLE IF NOT EXISTS `projectDisease`(
  `projectCode` varchar(20) not null comment '项目代码',
  `diseaseCode` varchar(80) not null comment '疾病代码',
  primary key (`projectCode`,`diseaseCode`)
) COMMENT '研发项目针对的疾病';

CREATE TABLE IF NOT EXISTS `product`(
  `id` int unsigned not null AUTO_INCREMENT primary key ,
  `externalId` varchar(20) comment '产品外部码,专用于系统对接',
  `code` varchar(10) not null unique comment '产品代码',
  `name` varchar(80) not null comment '产品名称',
  `spec` varchar(80) comment '产品规格',
  `parentCode` varchar(10) comment '当服务类产品包含有不同的服务项时,此处指向服务型所属大服务的code',
  `projectCode` varchar(20) comment '产品所属项目的代码project(code)'
) COMMENT '产品或服务：产品或服务是研发项目的成果';

INSERT INTO product(code,projectCode,name) VALUES('LDT01','AD01','LDT肿瘤筛查项目-肠癌粪便检测'); 
INSERT INTO product(code,projectCode,name) VALUES('LDT06','AD06','LDT肿瘤筛查项目-肠癌血液检测'); 
INSERT INTO product(code,projectCode,name) VALUES('LDT07','AD07','LDT肿瘤筛查项目-肝癌血液检测'); 
INSERT INTO product(code,projectCode,name) VALUES('LDT10','AD10','LDT肿瘤筛查项目-食管癌血液检测 '); 
INSERT INTO product(code,projectCode,name) VALUES('LDT12','AD12','LDT肿瘤筛查项目-消化五癌血液检测  '); 
INSERT INTO product(code,projectCode,name) VALUES('LDT02','AD02','LDT肿瘤筛查项目-宫颈癌脱落细胞检测'); 
INSERT INTO product(code,projectCode,name) VALUES('LDT03','AD03','LDT肿瘤筛查项目-子宫内膜癌脱落细胞检测  '); 
INSERT INTO product(code,projectCode,name) VALUES('LDT04','AD04','LDT肿瘤筛查项目-妇科两癌联检脱落细胞检测'); 
INSERT INTO product(code,projectCode,name) VALUES('LDT08','AD08','LDT肿瘤筛查项目-胃癌血液检测'); 
INSERT INTO product(code,projectCode,name) VALUES('LDT09','AD09','LDT肿瘤筛查项目-胰腺血液检测'); 
INSERT INTO product(code,projectCode,name) VALUES('LDT11','AD11','LDT肿瘤筛查项目-膀胱尿液检测'); 
INSERT INTO product(code,projectCode,name) VALUES('LDT21','AD21','LDT肿瘤筛查项目-泌尿四癌尿液检测'); 
INSERT INTO product(code,projectCode,name) VALUES('LDT22','AD22','LDT肿瘤筛查项目-消化三癌血液检测'); 
INSERT INTO product(code,projectCode,name) VALUES('LDT23','AD23','LDT肿瘤筛查项目-高发四癌血液检测'); 
INSERT INTO product(code,projectCode,name) VALUES('LDT24','AD24','LDT肿瘤筛查项目-高发五癌血液检测'); 
INSERT INTO product(code,projectCode,name) VALUES('LDT25','AD25','LDT肿瘤筛查项目-高发六癌血液检测'); 
INSERT INTO product(code,projectCode,name) VALUES('LDT99','AD99','十二癌(男性)'); 
INSERT INTO product(code,projectCode,name) VALUES('LDT13','AD13','十二癌(女性)'); 
INSERT INTO product(code,projectCode,name) VALUES('LDT14','AD14','LDT肿瘤筛查项目-肺癌血液检查'); 
INSERT INTO product(code,projectCode,name) VALUES('LDT16','AD16','LDT肿瘤筛查项目-乳腺癌血液检查'); 
INSERT INTO product(code,projectCode,name) VALUES('LDT17','AD17','LDT肿瘤筛查项目-乳腺癌、卵巢癌血检'); 
INSERT INTO product(code,projectCode,name) VALUES('LDT15','AD15','LDT肿瘤筛查项目-卵巢癌血液检测'); 

CREATE TABLE IF NOT EXISTS `Analyte2Product`(
  `analyteId` varchar(20) comment '产品外部码,专用于系统对接',
  `productId` varchar(10) not null unique comment '产品代码',
  `createTime` timestamp not null default  CURRENT_TIMESTAMP,
  primary key (`analyteId`,`productId`)
) COMMENT '分析物编号到产品代码的映射关系';

INSERT INTO Analyte2Product(analyteId,productId) VALUES
('LDT01','ACK'),
('LDT02','AGS'),
('LDT03','AGL'),
('LDT04','AGT'),
('LDT06','ACJ'),
('LDT07','AXG'),
('LDT08','AWY'),
('LDT09','AYN'),
('LDT10','ASN'),
('LDT11','AGU'),
('LDT12','AXA'),
('LDT13','L13'),
('LDT14','L14'),
('LDT15','L15'),
('LDT16','L16'),
('LDT17','L17'),
('LDT21','L21'),
('LDT22','L22'),
('LDT23','L23'),
('LDT24','L24'),
('LDT25','L25'),
('LDT99','L99');


CREATE TABLE IF NOT EXISTS `enums`(
  `id` int not null primary key AUTO_INCREMENT,
  `code` varchar(10) not null comment '枚举代码',
  `name` varchar(80) not null comment '名称',
  `type` varchar(10) comment '枚举类型',
  `index` smallint  comment '显示顺序'
) COMMENT '枚举类型表';



CREATE TABLE IF NOT EXISTS `sequence` (
   seqName   varchar(20) not null primary key,
   seqId   int unsigned default null,
   colName varchar(20) not null,
   updateTime datetime default now()
)  COMMENT '序列号生成器用表';