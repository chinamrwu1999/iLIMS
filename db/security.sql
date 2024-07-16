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
) comment '系统功能模块定义';j

create table `SysRolePrivilege`(
      `privilegeId` int unsigned not null primary key,
      `roleId` int unsigned not null,
      `componentId` int not null,
      `thruDate` datetime default null,
      `createTime` timestamp not null default CURRENT_TIMESTAMP
) comment '角色拥有的权限';

create table `SysMenu`(
   `menuId` int unsigned not null primary key,
   `name` varchar(20) not null,
   `label` varchar(20) not null,
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

insert into `SysUser`(`userName`,`password`) values('15010040','someone1999');
insert into `SysRole`(`name`) values('admin');
insert into `SysRole`(`name`) values('employee');
