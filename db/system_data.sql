
delete from SysMenu;

INSERT INTO `SysMenu`(`menuId`,`name`,`label`,`parentId`,`displayIndex`) values
(1,'IVDBusiness','医检业务',0,0),
(2,'receiveSample','样本接收',1,0),(3,'test','样本检测',1,1),(4,'expData','实验数据',1,2),(5,'reagent','试剂管理',1,3),
(6,'report','检测报告',0,1),
(7,'reportReview','报告审核',6,0),(8,'reportGeneration','报告生成',6,1),(9,'reportGeneration','报告发布',6,2),
(10,'customerCenter','客服中心',0,2),
(11,'outliers','异常样本',10,1),(12,'followUp','检后回访',10,2),(13,'colonoscopy','肠镜报销',10,3),
(14,'order','销售订单',0,3),
(15,'shipment','订单发货',14,1),(16,'orderQuery','订单查询',14,2),
(18,'production','生产工具',0,4),
(19,'seqGeneration','序列号生产',18,1),(20,'package','产品装箱',18,2),
(21,'statistic','统计查询',0,5),
(22,'samleTrace','样本状态',21,1),(23,'sampleSummary','收样统计',21,2),(24,'clinicSummary','临床统计',21,3),
(25,'system','系统管理',0,6),
(26,'systemUsers','用户管理',25,1),(27,'systemRoles','角色管理',25,2),(28,'privileges','权限管理',25,3);





insert into `SysUser`(`userName`,`password`) values('15010040','$2a$12$05taBl.lV1hE71FB1bMlee4T4f8hw0nJWCZdhJUYSATJvdW7Eu83W');
insert into `SysRole`(`name`) values('admin');
insert into `SysRole`(`name`) values('employee');
insert into  `SysUserRole`(`userId`,`roleId`) values(1,1);

insert into `SysComponent`(`componentId`,`name`,`description`,`url`) values
(1,'sample:sorting','收样分拣','/sample/sorting'),
(2,'sample:receive','收样','/sample/receive');
insert into `SysMenuComponent`(`menuId`,`componentId`) values
(5,1),(6,2);

INSERT INTO `SysRoleMenu`(`roleId`,`menuId`) values
(1, 1),(1, 2),(1, 3),(1, 4),(1, 5),(1, 6),(1, 7),(1, 8),(1, 9),(1,10),
(1,11),(1,12),(1,13),(1,14),(1,15),(1,16),(1,18),(1,19),(1,20),
(1,21),(1,22),(1,23),(1,24),(1,25),(1,26),(1,27),(1,28);


