create database mydb;
use mydb;

Create table `SysUser`(
    `userId` int unsigned not null auto_increment primary key,
    `userName` varchar(30) not null ,
    `password` varchar(60) not null,
    `createTime` timestamp not null default CURRENT_TIMESTAMP
) comment '用户定义';
create table `SysRole`(
        `roleId` int unsigned not null auto_increment primary key,
        `name` varchar(30) not null,
        `status` char(1) not null default 'A' comment '激活与否,A(active)激活,D(ead)失活',
        `createTime` timestamp not null default CURRENT_TIMESTAMP
) comment '角色定义';

create table `SysUserRole`(
      `id` int unsigned not null auto_increment primary key,
      `userId` int unsigned not null,
      `roleId` int unsigned not null,
       `createTime` timestamp not null default CURRENT_TIMESTAMP,
      unique(`userId`,`roleId`)
) comment '用户对应的角色';

create table `SysComponent`(
   `componentId` int unsigned not null auto_increment primary key,
   `name` varchar(20) not null ,
   `description` varchar(60),
   `url` varchar(250) not null,
   `createTime` timestamp not null default CURRENT_TIMESTAMP
) comment '系统功能模块定义';

create table `SysRolePrivilege`(
      `privilegeId` int unsigned not null auto_increment primary key,
      `roleId` int unsigned not null,
      `componentId` int not null,
      `thruDate` datetime default null,
      `createTime` timestamp not null default CURRENT_TIMESTAMP
) comment '角色拥有的权限';

create table `SysMenu`(
   `menuId` int unsigned not null auto_increment primary key,
   `name` varchar(20) not null,
   `label` varchar(20) not null,
   `displayIndex` smallint not null default 0,
   `parentId` int unsigned default  0,
    `createTime` timestamp not null default CURRENT_TIMESTAMP
) comment '菜单定义';
create table `SysMenuComponent`(
   `id` int unsigned not null auto_increment primary key,
   `menuId` int unsigned not null,
   `componentId` int unsigned not null,
   `createTime` timestamp not null default CURRENT_TIMESTAMP
) comment '菜单对应的链接';
create table `SysRoleMenu`(
     `id` int unsigned not null auto_increment primary key,
     `roleId` int unsigned not null,
     `menuId` int unsigned not null,
     `createTime` timestamp not null default CURRENT_TIMESTAMP
) comment '角色的菜单分配';


CREATE TABLE IF NOT EXISTS `SysUserLogin`(
     `id` int unsigned not null AUTO_INCREMENT primary key,
     `userId`  int unsigned not null ,
     `loginTime` timestamp not null DEFAULT CURRENT_TIMESTAMP,
     `checkoutTime` datetime comment '退出时间'
) comment '用户登录日志';

INSERT INTO `SysMenu`(`menuId`,`name`,`label`,`parentId`,`displayIndex`) values
(0,'root','root',null,null);
(1,'receiveSample','样本接收',0,0),
(2,'testSample','样本检测',0,1),
(3,'reportReview','数据审核',0,3),
(4,'dataReview','检测报告',0,4);

INSERT INTO `SysMenu`(`menuId`,`name`,`label`,`parentId`,`displayIndex`) values
(5,'receiveSample','分拣',1,0),
(6,'testSample','收样',1,1),
(7,'testSample','异常样本',1,2),

(8,'reportReview','实验安排',2,0),
(9,'dataReview','检测数据',2,1);
INSERT INTO `SysMenu`(`menuId`,`name`,`label`,`parentId`,`displayIndex`) values
(10,'reportReview','数据审核',9,0),
(11,'dataReview','数据看看',9,1);

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
(1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9);

INSERT INTO `SysRoleMenu`(`roleId`,`menuId`) values
(1,10),(1,11);
