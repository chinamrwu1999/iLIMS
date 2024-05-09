CREATE DATABASE IF NOT EXISTS myLIMS;
USE iLIMS;

CREATE TABLE IF NOT EXISTS  `biosample`(
    `id` int unsigned not null AUTO_INCREMENT primary key  COMMENT '自增列,主键，无业务意义',
    `barCode` varchar(80) not null COMMENT '唯一标识生物样本的条形码号',
    `type` varchar(12) not null COMMENT '类型：F粪便、B血液、C细胞、T组织',
    `weight` decimal(5,3) COMMENT '重量',
    `volume` decimal(5,3) COMMENT '体积',
    `color` varchar(30) COMMENT '样本颜色',
    `location` varchar(60)  COMMENT '样本收到后的存放位置',
    `sampleTime` datetime COMMENT '样本在病人身上的采样时间',
    `partnerCode` varchar(12) COMMENT '样本来源：来自哪个合作伙伴',
    `sender` varchar(100) COMMENT '送检单位',
    `status` char(1) not null default 'S' COMMENT '样本状态:S 表示合格(Succeed),F 表示不合格(Fail)',
    `sampleImage` varchar(200) COMMENT '样本照片',
    `formImage` varchar(200) COMMENT '个人信息表格图片',
    `surveyImage` varchar(200) COMMENT '如有纸质问卷，则拍照保存',
    `isVIP` boolean COMMENT '是否重点关注样本',
    constraint uniq_bar unique(`barCode`,`partnerCode`),
    `createTime` datetime not null default now() COMMENT '收到样本时间，插入数据库时候的时间'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT '生物样本基本信息表';

CREATE TABLE IF NOT EXISTS `analyte`(
    `id` int unsigned NOT NULL AUTO_INCREMENT primary key COMMENT '',
    `barCode` varchar(60) NOT NULL COMMENT '分析物对应的样本条码号',
    `analyteCode` varchar(20) NOT NULL COMMENT '分析物编号，用于实验室内部编排实验用，类如 ACK23000018等',
    `productCode` varchar(20) NOT NULL COMMENT '改分析物所做检测对应的产品或服务ID',
    `createTime` datetime not null default now(),
    unique(`barCode`,`analyteCode`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT '分析物:一份生物样本BioSample 可能会被多次检测,每一次检测用到的只是BioSample的一部分,称为analyte，不同analyte可以做不同检测';

CREATE index analyteIndex on `analyte`(`analyteCode`);


CREATE TABLE IF NOT EXISTS `analyteProcess` (
    `id` int unsigned NOT NULL AUTO_INCREMENT primary key COMMENT '自增列主键',
    `analyteCode` varchar(20) NOT NULL COMMENT '分析物编号,引用自analyte表的analyteId',
    `action` varchar(20) not null COMMENT '操作名称，枚举类型，收样、复检、检测',
    `status` varchar(20) not null COMMENT '操作完成后的状态,success、fail等',
    `remark` varchar(100) COMMENT '备注',
    `employeeId` varchar(10) not null COMMENT '操作人的员工工号',
    `createTime` DATETIME not null default now(),
    unique (`analyteCode`,`action`),
    constraint fk_analyteProcess_01 foreign Key (`analyteCode`) references `analyte`(`analyteCode`)   
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT '分析物处理表：记录分析物被操作处理的每一个步骤状态';

CREATE index analyte_process_Index1 on `analyteProcess`(`analyteCode`);
CREATE index analyte_process_ActionIndex1 on `analyteProcess`(`action`);
CREATE INDEX index_analyteProcessTime ON analyteProcess(`createTime`);

CREATE TABLE IF NOT EXISTS DataUpload (
    `id` int unsigned not null AUTO_INCREMENT primary key comment '自增列主键',
    `instrument` varchar(20) comment '仪器类型',
    `testTime` datetime not null comment '检测时间',    
    `inputFile` varchar(100) not null comment '上传时候用的数据文件名称',
    `machineFile` varchar(100) not null comment '实验仪器生成的原始数据文件名称',
    `testName` varchar(60)  comment '实验名称',
    `employeeId` varchar(12) not null comment '数据上传人的工号',
    `productCode` varchar(12) not null comment '本次实验检测产品的代码',
    `uploadTime` DATETIME not null default now() comment '实验数据上传时间'
) ENGINE=InnoDB AUTO_INCREMENT=10000 comment '实验数据上传信息';
CREATE INDEX index_DataUploadTime ON DataUpload(`uploadTime`);

CREATE TABLE IF NOT EXISTS `PCRData`(
  `id` int unsigned not null AUTO_INCREMENT primary key comment '自增列主键',
  `uploadId` int unsigned not null ,
  `analyteCode` varchar(20) not null ,
  `well` varchar(4) not null comment '孔洞编号:PCR仪器板子上有96空或48空用于放测试样本, 不同样本不能混在同一个well',
  `target` varchar(40) not null comment '基因标记物的代码',
  `CT` decimal(6,4) DEFAULT NULL COMMENT '检测到的CT值，如果是undetermined，则存储-1',
  `predict` varchar(12) not null comment '该标记的检测结果判定：阴性、阳性、不合格（内参)等',
  `choose` boolean not null default 1 
) ENGINE=InnoDB AUTO_INCREMENT=2000000 comment 'PCR检测数据';

CREATE index index_PCRData_analyteId on `PCRData`( `analyteCode`);

CREATE TABLE IF NOT EXISTS TargetCurve(
    `id` int unsigned not null primary key,
    `dltRN` varchar(800) not null,
    constraint fk_curve foreign key(`id`) references `PCRData`(`id`)
) ENGINE=InnoDB comment '标记物的扩展曲线值';

 





 

