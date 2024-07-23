use iLIMS;

DROP TABLE IF EXISTS `SysUser`;
CREATE TABLE IF NOT EXISTS  `SysUser`(
    `userId` int unsigned not null auto_increment primary key,
    `userName` varchar(30) not null unique,
    `password` varchar(60) not null,
    `status`  char(1) not null default 'A' comment '激活与否,A(active)激活,D(ead)失活',
    `createTime` timestamp not null default CURRENT_TIMESTAMP
) comment '用户定义';

DROP TABLE IF EXISTS `SysRole`;
CREATE TABLE IF NOT EXISTS `SysRole`(
        `roleId` int unsigned not null auto_increment primary key,
        `name` varchar(30) not null,
        `chineseName` varchar(30) ,
        `status` char(1) not null default 'A' comment '激活与否,A(active)激活,D(ead)失活',
        `createTime` timestamp not null default CURRENT_TIMESTAMP
) comment '角色定义';

DROP TABLE IF EXISTS `SysUserRole`;
CREATE TABLE IF NOT EXISTS `SysUserRole`(
      `id` int unsigned not null auto_increment primary key,
      `userId` int unsigned not null,
      `roleId` int unsigned not null,
       `createTime` timestamp not null default CURRENT_TIMESTAMP,
      unique(`userId`,`roleId`)
) comment '用户对应的角色';

DROP TABLE IF EXISTS `SysComponent`;
CREATE TABLE IF NOT EXISTS `SysComponent`(
   `componentId` int unsigned not null auto_increment primary key,
   `name` varchar(20) not null ,
   `description` varchar(60),
   `url` varchar(250) not null,
   `createTime` timestamp not null default CURRENT_TIMESTAMP
) comment '系统功能模块定义';

DROP TABLE IF EXISTS `SysRolePrivilege`;
CREATE TABLE IF NOT EXISTS `SysRolePrivilege`(
      `privilegeId` int unsigned not null auto_increment primary key,
      `roleId` int unsigned not null,
      `componentId` int not null,
      `thruDate` datetime default null,
      `createTime` timestamp not null default CURRENT_TIMESTAMP
) comment '角色拥有的权限';

DROP TABLE IF EXISTS `SysMenu`;
CREATE TABLE IF NOT EXISTS `SysMenu`(
   `menuId` int unsigned not null auto_increment primary key,
   `name` varchar(20) not null,
   `label` varchar(20) not null,
   `displayIndex` smallint not null default 0,
   `parentId` int unsigned default  0,
    `createTime` timestamp not null default CURRENT_TIMESTAMP
) comment '菜单定义';

DROP TABLE IF EXISTS `SysMenuComponent`;
CREATE TABLE IF NOT EXISTS`SysMenuComponent`(
   `id` int unsigned not null auto_increment primary key,
   `menuId` int unsigned not null,
   `componentId` int unsigned not null,
   `createTime` timestamp not null default CURRENT_TIMESTAMP
) comment '菜单对应的链接';

DROP TABLE IF EXISTS `SysRoleMenu`;
CREATE TABLE IF NOT EXISTS `SysRoleMenu`(
     `id` int unsigned not null auto_increment primary key,
     `roleId` int unsigned not null,
     `menuId` int unsigned not null,
     `createTime` timestamp not null default CURRENT_TIMESTAMP
) comment '角色的菜单分配';

DROP TABLE IF EXISTS `SysUserLogin`;
CREATE TABLE IF NOT EXISTS `SysUserLogin`(
     `id` int unsigned not null AUTO_INCREMENT primary key,
     `userId`  int unsigned not null ,
     `loginTime` timestamp not null DEFAULT CURRENT_TIMESTAMP,
     `checkoutTime` datetime comment '退出时间'
) comment '用户登录日志';

DROP TABLE IF EXISTS `SysKeys`;
CREATE TABLE IF NOT EXISTS `SysKeys`(
     `id`  varchar(20) not null primary key  ,
     `privateKey` text not null,
     `publicKey`  text not null,
     `createTime` datetime  not null default now()
) comment '存储系统用到的RSA加密的公钥和私钥';