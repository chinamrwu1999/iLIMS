use database iLIMS ;

CREATE TABLE IF NOT EXISTS `SysUser`(
    `id` int unsigned not null AUTO_INCREMENT primary key,
    `partyId` varchar(10) ,
    `password` varchar(40) not null comment '加密保存的用户密码', 
    `createTime` timestamp not null default CURRENT_TIMESTAMP
) comment '系统用户';

CREATE TABLE IF NOT EXISTS `SysRole`(
     `id` int unsigned not null AUTO_INCREMENT primary key,
     `roleKey` varchar(80) not null,
     `roleName` varchar(30) not null ,
     `status` boolean default 1 comment '角色是否正常使用:1 正常使用 0 停用',
     `createTime` timestamp not null DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment '系统角色';

CREATE TABLE IF NOT EXISTS `SysUserRole`(
    `id` int unsigned not null AUTO_INCREMENT primary key,
    `partyId` varchar(10) not null,
    `roleId` int unsigned not null,
    `throughDate` datetime default null comment '角色有效截至日期',
    `createTime` timestamp not null DEFAULT CURRENT_TIMESTAMP 
) comment '用户角色分配';

CREATE TABLE IF NOT EXISTS `SysMenu`(
  `id` int unsigned not null AUTO_INCREMENT primary key,
  `menuName` varchar(30)  not null comment '菜单名称',
  `parentId` int unsigned default 0 comment '父菜单id',
  `orderIndex` smallint unsigned default 0 comment '显示顺序',
  `path` varchar(250) comment '菜单对应的连接URL',
  `component` varchar(250) comment '组件路径',
  `query` varchar(250) comment '路由参数',
  `isFrame` boolean default 0 comment '是否为外链',
  `menuType` ENUM('menu','directory','button') comment '菜单类别',
  `status` ENUM('on','off') comment '菜单启用on或停用off',
  `visible` boolean default 1 comment '是否可见,1可见0不可见',
  `perms` varchar(150) CHARACTER SET latin1 comment '权限标志字符串',
  `icon` varchar(100) comment '菜单图标',
  `cached` boolean default 1 comment '是否缓存',
  `createTime` DATETIME not null DEFAULT now()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment '系统菜单';

CREATE TABLE IF NOT EXISTS `SysRoleMenu`(
     `roleId` int unsigned not null,
     `menuId` int unsigned not null,
     `createTime` timestamp not null default CURRENT_TIMESTAMP,
     primary key(`roleId`,`menuId`)
) ;

CREATE TABLE IF NOT EXISTS `SysUserLogin`(
     `id` int unsigned not null AUTO_INCREMENT primary key,
     `userId`  int unsigned not null ,
     `loginTime` timestamp not null DEFAULT CURRENT_TIMESTAMP,
     `checkoutTime` datetime comment '退出时间'
) comment '用户登录日志';
create index userLoginIndex on `UserLogin`(`userId`);