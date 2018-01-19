SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------

drop TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id`    BIGINT   NOT NULL AUTO_INCREMENT,
  `phone`      VARCHAR(30)    NOT NULL UNIQUE,#手机号码
  `password`   VARCHAR(100)    NOT NULL, #密码
  `user_name`  VARCHAR(100)    DEFAULT null UNIQUE, #用户名
  `register_time`   DATETIME      NOT NULL ,  #注册时间
  `is_actived`   INTEGER     DEFAULT 1 ,  #是否活跃 1活动 0禁用 默认为1
  PRIMARY KEY (`user_id`)
)engine=InnoDB DEFAULT charset=utf8;

drop TABLE IF EXISTS `user_friends`;
CREATE TABLE `user_friends` (
  `user_id`    BIGINT   NOT NULL ,#用户id
  `friend_id`  BIGINT  NOT NULL ,#好友id
  PRIMARY KEY (`user_id`,`friend_id`)
)engine=InnoDB DEFAULT charset=utf8;

drop TABLE IF EXISTS `device`;
CREATE TABLE `device` (
  `device_id`    BIGINT   NOT NULL AUTO_INCREMENT ,
  `device_ver`     VARCHAR(64)  NOT NULL ,#设备版本号
  `sensor_count`   INTEGER    NOT NULL, #传感器模块的数量
  `driver_count`   INTEGER    NOT NULL , #输出模块的数量
  `io_cfg`         VARCHAR (255)  ,# 模块配置表
  `sampling_period`   INTEGER     NOT NULL  ,  #发送周期(s)
  `io_cfg_time`    DATETIME   ,  #模块配置更新时间
  `is_online`      INTEGER     NOT  NULL ,  #设备在线状态 枚举0:离线1:在线)
  `online_time`      TIMESTAMP     NOT  NULL ,  #设备在线时间 枚举0:离线1:在线)
   `sinario_cfg`      VARCHAR (512)  ,# 场景区配置
  `sinario_cfg_time`   DATETIME     ,  #场景区配置时间
   `user_id`  VARCHAR(64)   ,  #用户id
   `user_phone`  VARCHAR(64)   ,  #用户手机号
  PRIMARY KEY (`device_id`)
)engine=InnoDB DEFAULT charset=utf8;


drop TABLE IF EXISTS `data`;
CREATE TABLE `data` (
  `id`    BIGINT   NOT NULL AUTO_INCREMENT,#主键id
  `device_id`  BIGINT  NOT NULL ,#设备id
   `receive_time`    DATETIME  NOT NULL ,#接收时间
  `data`  VARCHAR(1024)  NOT NULL ,#数据包
  PRIMARY KEY (`id`)
)engine=InnoDB DEFAULT charset=utf8;

drop TABLE IF EXISTS `share`;
CREATE TABLE `share` (
  `user_id`    BIGINT   NOT NULL ,#用户id
  `share_id`    BIGINT   NOT NULL ,#分享用户id
  `device_id`    BIGINT   NOT NULL ,#设备id
  `share_time`     DATETIME  NOT NULL ,#分享时间
  PRIMARY KEY (`user_id`,`share_id`,`device_id`)
)engine=InnoDB DEFAULT charset=utf8;

drop TABLE IF EXISTS `app_ver`;
CREATE TABLE `app_ver` (
  `id`    BIGINT   NOT NULL AUTO_INCREMENT,#主键id
  `platform`     INTEGER  NOT NULL ,# 平台 枚举 0:安卓 1:iOS
  `app_ver`   VARCHAR(255)  NOT NULL, #版本号
  `describ`      VARCHAR(255)   NOT NULL , #描述信息
  `rel_time`   DATETIME DEFAULT NULL,# 发布时间为NULL时 该版本发布不生效
  `url`   VARCHAR (255)    NOT NULL  ,  #下载链接(s)
  PRIMARY KEY (`id`)
)engine=InnoDB DEFAULT charset=utf8;

drop TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `id`    BIGINT   NOT NULL AUTO_INCREMENT ,#主键id
  `op_time`     DATETIME   NOT NULL ,# 操作时间
  `user_id`   BIGINT  DEFAULT NULL, #用户id
  `user_name`  VARCHAR(255)   DEFAULT NULL , #用户名
  `ip_addr`   VARCHAR(255) NOT NULL,# ip地址
  `app_ver`   VARCHAR (255) DEFAULT NULL,  #app版本
  `event`  VARCHAR(255)   DEFAULT NULL , #发生的事情
  `url`   VARCHAR(255) NOT NULL,# 访问地址
  `request`   VARCHAR (255) DEFAULT NULL,  #事件详情
  `response`   VARCHAR (1024) DEFAULT NULL,  #事件详情
  PRIMARY KEY (`id`)
)engine=InnoDB DEFAULT charset=utf8;


drop TABLE IF EXISTS `autho_code`;
CREATE TABLE `autho_code` (
  `id`    BIGINT   NOT NULL  AUTO_INCREMENT,#主键id
  `phone`     VARCHAR(255)   NOT NULL UNIQUE  ,# 电话号码
  `autho_code`    VARCHAR(255)   NOT NULL, #验证码
  PRIMARY KEY (`id`)
)engine=InnoDB DEFAULT charset=utf8;

drop TABLE IF EXISTS `online_log`;
CREATE TABLE `online_log` (
  `id`    BIGINT   NOT NULL  AUTO_INCREMENT,#主键id
  `deviceId`     BIGINT   NOT NULL  ,# 设备id
  `off_line_time`    DATETIME , #离线时间
  `on_line_time`    DATETIME , #在线时间
  PRIMARY KEY (`id`)
)engine=InnoDB DEFAULT charset=utf8;

