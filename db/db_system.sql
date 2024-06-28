use database iLIMS ;

CREATE TABLE IF NOT EXISTS User(
    `id` int unsigned not null AUTO_INCREMENT primary key,
    `partyId` varchar(10) ,
    `password` varchar(40) not null comment '登录密码:MD5加密保存的用户密码', 
    `createTime` timestamp not null default CURRENT_TIMESTAMP
) comment '系统用户';

CREATE TABLE IF NOT EXISTS UserLogin(
      `id` int unsigned not null AUTO_INCREMENT primary key,
     `loginId` varchar(50) not null comment '登录时候用到的账号，可以是:员工号或partyId 或手机号或openId',
     `loginTime` timestamp not null DEFAULT CURRENT_TIMESTAMP,
     `loginType` ENUM('LIS','wechat') comment '登录方式:LIS从LIS系统的web端登录,wechat从微信小程序登录',
     `checkoutTime` datetime comment '退出时间'
) comment '用户登录日志';


CREATE TABLE IF NOT EXISTS `Role`(
     `
)
