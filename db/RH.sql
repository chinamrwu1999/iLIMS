#瑞华保险相关的数据库表
CREATE TABLE RHProject(
 code VARCHAR(20) NOT NULL PRIMARY KEY comment '瑞华保险项目代码',
 name VARCHAR(80) NOT NULL comment '瑞华健康项目名称',
 createTime DATETIME NOT NULL DEFAULT now()
 ) comment '瑞华保险推出销售的健康保险项目';

 INSERT INTO RHProject(code,name) VALUES
('P202400002','瑞华优医保中端医疗保险健康管理服务（尊享计划二）-适用31周岁及以上'),
('P2021400002','瑞华优医保中端医疗保险健康管理服务（尊享计划二）-适用31周岁及以上'),
('P202400006','瑞华优医保医疗保险（互联网）健康管理服务（尊享计划二）-适用31周岁及以上');


CREATE TABLE RHService(
 code VARCHAR(20) NOT NULL PRIMARY KEY comment '瑞华保险项目代码',
 name VARCHAR(80) NOT NULL comment '瑞华健康项目名称',
 orgName VARCHAR(80) NOT NULL comment '服务提供商',
 createTime DATETIME NOT NULL DEFAULT now()
 ) comment '每一保险project下面包含一个或多个service';
 INSERT INTO RHService(code,name,orgName) values
 ('S00048','居家肠癌筛查服务','武汉艾米森生命科技有限公司');


 CREATE TABLE IF NOT EXISTS RHPly(
     plyNo varchar(30) not null  primary key  comment '保单号',
     masterNo varchar(30) not null  comment '瑞华的Master_Protocol_No',
     appName varchar(20)  comment '投保人姓名',
     mobile varchar(20) comment '投保人手机号',
     certfcls smallint COMMENT '证件类型：0 身份证' ,
     certfcde varchar(25) COMMENT '证件号码',
     createTime datetime not null default now()
 ) comment '保单信息';

 CREATE TABLE IF NOT EXISTS RHUser(
     plyNo VARCHAR(30) NOT NULL COMMENT '保单号',
     personId VARCHAR(12) comment '?',
     insuredName VARCHAR(20) COMMENT '受益人姓名'
     mobile varchar(20) comment '投保人手机号',
     certfcls smallint COMMENT '证件类型：0 身份证' ,
     certfcde varchar(25) COMMENT '证件号码',
     createTime datetime not null default now()
 ) comment '保单受益人信息';