
CREATE DATABASE IF NOT EXISTS bubble_video DEFAULT CHARACTER SET = utf8mb4;
USE bubble_video;

CREATE TABLE IF NOT EXISTS video_report
(
  id            BIGINT  PRIMARY KEY COMMENT '举报ID',
  video_id      BIGINT  NOT NULL COMMENT '用户上传的视屏ID（非腾讯的video_id)',
  user_id       BIGINT DEFAULT '0' NOT NULL COMMENT '举报者用户ID（为0表示未登录情况下举报）',
  report_reason VARCHAR(255)  DEFAULT NULL
  COMMENT '举报理由',
  status        TINYINT DEFAULT '2' NOT NULL COMMENT '处理审核状态（1是，2否）'
--   create_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
--   COMMENT '记录创建时间',
--   modify_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
--   COMMENT '记录修改的时间',
--   creator_id    BIGINT DEFAULT '0'                  NOT NULL
--   COMMENT '记录创建者ID（0为无）',
--   modifier_id   BIGINT DEFAULT '0'                  NOT NULL
--   COMMENT '更新记录的操作者ID（0为没有）',
--   record_status TINYINT(3) UNSIGNED DEFAULT '0'     NOT NULL
--   COMMENT '记录状态（0为正常）'
);

CREATE TABLE  IF NOT EXISTS `user` (
  `id`             bigint(20) UNSIGNED NOT NULL PRIMARY KEY COMMENT '用户ID',
  `username`       VARCHAR(20) COMMENT '用户名',
  `avatar_url`     varchar(255)  COMMENT '用户头像URL',
  `nick_name`      varchar(20)   COMMENT '用户名称',
  `phone_ext`      VARCHAR(5) COMMENT '手机区号',
  `phone`          varchar(20)  COMMENT '手机号',
  `email`          varchar(128)  COMMENT '用户email',
  `password`       TEXT(64) COMMENT '用户密码',
  `password_salt`  TEXT(16) COMMENT '密码混淆随机值',
  `gender`         tinyint UNSIGNED  COMMENT '性别(0: 未设置）',
  `self_signature` varchar(255)  COMMENT '用户个性签名',
  `birthday`       BIGINT(13)  COMMENT '用户生日',
  `status`         VARCHAR(20)         NOT NULL DEFAULT 'USER_NORMAL' COMMENT '用户状态状态：正常（USER_NORMAL），已锁定（USER_LOCKED），已过期（USER_EXPIRED）',
  `country`        varchar(255) COMMENT '用户国家（地区）',
  `state`          varchar(255)  COMMENT '用户所在省份、洲',
  `city`           varchar(255)  COMMENT '用户所在城市',
  `create_time`    timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间'
--   `modify_time`    timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '记录修改的时间' ,
--   `creator_id`     bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '记录创建者ID（0为无）',
--   `modifier_id`    bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '更新记录的操作者ID（0为没有）',
--   `record_status`  tinyint UNSIGNED    NOT NULL DEFAULT 0 COMMENT '记录状态（0为正常）',
);

CREATE TABLE  IF NOT EXISTS `user_relation` (
`id` bigint(20) UNSIGNED NOT NULL  PRIMARY KEY  COMMENT '记录ID',
`relation_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`follower_id` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '被关注的用户ID',
`follower_nick_name` varchar(20)  NOT NULL DEFAULT '' COMMENT '关注者名称',
`follower_avatar_url` varchar(255)  NOT NULL DEFAULT '' COMMENT '关注者头像',
`follower_self_signature` varchar(255)  COMMENT '关注者用户个性签名',
`following_id` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '关注的用户ID',
`following_nick_name` varchar(20)   NOT NULL DEFAULT '' COMMENT '关注的用户名称',
`following_avatar_url` varchar(255)    NOT NULL DEFAULT '' COMMENT '关注的用户头像',
`following_self_signature` varchar(255)  COMMENT '关注的用户个性签名'
-- `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
-- `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录修改的时间',
-- `creator_id` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '记录创建者ID（0为无）',
-- `modifier_id` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '更新记录的操作者ID（0为没有）',
-- `record_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '记录状态（0为正常）',
);

CREATE TABLE  IF NOT EXISTS `social_platform` (
  `id`            bigint(20) UNSIGNED PRIMARY KEY  NOT NULL,
  `user_id`       bigint(20) UNSIGNED NOT NULL COMMENT '用户ID',
  `openid`        varchar(128) NOT NULL DEFAULT '' COMMENT '社交平台用户唯一标识',
  `access_token`  VARCHAR(1024) COMMENT '平台交互访问令牌',
  `type`          VARCHAR(20)  NOT NULL COMMENT '社交平台类型'
--   `create_time`   timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
--   `modify_time`   timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录修改的时间',
--   `creator_id`    bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '记录创建者ID（0为无）',
--   `modifier_id`   bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '更新记录的操作者ID（0为没有）',
--   `record_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '记录状态（0为正常）',
);

CREATE TABLE  IF NOT EXISTS `video` (
  `id`            bigint(20) UNSIGNED  PRIMARY KEY  NOT NULL,
  `title`         varchar(255) COMMENT '视屏标题、字幕',
  `description`   text  COMMENT '视屏描述信息',
  `file_id`       VARCHAR(50)         NOT NULL COMMENT '视屏文件ID',
  `user_id`       bigint(20) UNSIGNED NOT NULL COMMENT '发布用户ID',
  `cover_url`     varchar(255)  COMMENT '视屏封面地址',
  `cover_width`   SMALLINT COMMENT '视屏封面宽度',
  `cover_height`  SMALLINT COMMENT '视屏封面高度',
  `video_width`   SMALLINT COMMENT '视屏宽度',
  `video_height`  SMALLINT COMMENT '视屏高度',
  `video_url`     varchar(255)        NOT NULL COMMENT '视屏播放地址',
  `play_count`    INTEGER COMMENT '播放次数',
  `video_status`  VARCHAR(20)         NOT NULL DEFAULT 'VIDEO_NORMAL'
  COMMENT '视屏状态：正常（VIDEO_NORMAL）, 非法视屏（VIDEO_INVALID）'
--   `create_time`   timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
--   `modify_time`   timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录修改的时间',
--   `creator_id`    bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '记录创建者ID（0为无）',
--   `modifier_id`   bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '更新记录的操作者ID（0为没有）',
--   `record_status` tinyint UNSIGNED    NOT NULL DEFAULT 0 COMMENT '记录状态（0为正常）',
);

CREATE TABLE  IF NOT EXISTS `video_comment` (
  `id`            bigint(20)       PRIMARY KEY   NOT NULL    COMMENT '评论ID',
  `video_id`      bigint(20) UNSIGNED NOT NULL COMMENT '用户上传的视屏ID（非腾讯的video_id)',
  `commenter_id`  bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '评论者用户ID',
  `content`       text                NOT NULL COMMENT '评论内容 ',
  `comment_time`  TIMESTAMP           NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间'
--   `create_time`   timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
--   `modify_time`   timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录修改的时间',
--   `creator_id`    bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '记录创建者ID（0为无）',
--   `modifier_id`   bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '更新记录的操作者ID（0为没有）',
--   `record_status` tinyint UNSIGNED    NOT NULL DEFAULT 0 COMMENT '记录状态（0为正常）',
);

CREATE TABLE  IF NOT EXISTS `video_like` (
`id` bigint(20) NOT NULL PRIMARY KEY  COMMENT '评论ID',
`video_id` bigint(20) UNSIGNED NOT NULL COMMENT '用户上传的视屏ID（非腾讯的video_id)',
`liker_id` bigint(20) UNSIGNED NOT NULL COMMENT '点赞者用户ID'
-- `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
-- `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录修改的时间',
-- `creator_id` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '记录创建者ID（0为无）',
-- `modifier_id` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '更新记录的操作者ID（0为没有）',
-- `record_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '记录状态（0为正常）',
);

CREATE TABLE  IF NOT EXISTS `gt_push_client_id`(
  `id` bigint(20) UNSIGNED NOT NULL  PRIMARY KEY COMMENT 'ID',
  `user_id`  bigint(20) UNSIGNED NOT NULL COMMENT '用户ID',
  `client_id` VARCHAR(40) NOT NULL COMMENT '个推CID'
--   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
--   `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录修改的时间',
--   `creator_id` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '记录创建者ID（0为无）',
--   `modifier_id` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '更新记录的操作者ID（0为没有）',
--   `record_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '记录状态（0为正常）',
);

CREATE TABLE  IF NOT EXISTS `privilege`(
  `id`          BIGINT(20) UNSIGNED NOT NULL  PRIMARY KEY
  COMMENT '权限ID',
  `name`        VARCHAR(20)         NOT NULL COMMENT '权限名称',
  `value`       VARCHAR(50)         NOT NULL  NULL COMMENT '权限值如： user:video:delete',
  `uri`         VARCHAR(127)        NOT NULL NULL COMMENT '权限路径',
  `type`        TINYINT             NOT NULL DEFAULT 0 COMMENT '权限类型: 目录（0）， 菜单（1），按钮（2）',
  `action`      TINYINT             NOT NULL DEFAULT 0 COMMENT '动作: 查（0），增（1），改（2），删（3）',
  `description` VARCHAR(200)                 DEFAULT NULL COMMENT '权限描述信息'
--   `create_time` timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
--   `modify_time` timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录修改的时间',
--   `creator_id` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '记录创建者ID（0为无）',
--   `modifier_id` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '更新记录的操作者ID（0为没有）',
--   `record_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '记录状态（0为正常）'
);

CREATE TABLE  IF NOT EXISTS `role`(
  `id` bigint(20) UNSIGNED NOT NULL  PRIMARY KEY COMMENT '角色ID',
  `name` VARCHAR(20) NOT NULL COMMENT '角色名称',
  `description` VARCHAR(200) DEFAULT NULL COMMENT '角色描述信息'
--   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
--   `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录修改的时间',
--   `creator_id` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '记录创建者ID（0为无）',
--   `modifier_id` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '更新记录的操作者ID（0为没有）',
--   `record_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '记录状态（0为正常）'
);

CREATE TABLE  IF NOT EXISTS `role_privilege`(
  `id`            BIGINT(20) UNSIGNED NOT NULL  PRIMARY KEY
  COMMENT '权限ID',
  `privilege_id`  VARCHAR(20)         NOT NULL COMMENT '权限名称',
  `role_id`       VARCHAR(200)                 DEFAULT NULL COMMENT '权限描述信息'
--   `create_time`   timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
--   `modify_time`   timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录修改的时间',
--   `creator_id`    bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '记录创建者ID（0为无）',
--   `modifier_id`   bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '更新记录的操作者ID（0为没有）',
--   `record_status` tinyint UNSIGNED    NOT NULL DEFAULT 0 COMMENT '记录状态（0为正常）'
);

CREATE TABLE  IF NOT EXISTS `user_role`(
  `id` bigint(20) UNSIGNED NOT NULL  PRIMARY KEY COMMENT '权限ID',
  `user_id` bigint(20)  NOT NULL COMMENT '权限名称',
  `role_id`bigint(20)  DEFAULT NULL COMMENT '权限描述信息'
--   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
--   `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录修改的时间',
--   `creator_id` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '记录创建者ID（0为无）',
--   `modifier_id` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '更新记录的操作者ID（0为没有）',
--   `record_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '记录状态（0为正常）'
);

CREATE TABLE  IF NOT EXISTS `key_value_pair`(
  `id` bigint(20) UNSIGNED NOT NULL  PRIMARY KEY COMMENT '键值对ID',
  `pair_key` VARCHAR(100) NOT NULL UNIQUE COMMENT '键',
  `pair_value` VARCHAR(100) NOT NULL COMMENT '值'
--   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
--   `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录修改的时间',
--   `creator_id` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '记录创建者ID（0为无）',
--   `modifier_id` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '更新记录的操作者ID（0为没有）',
--   `record_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '记录状态（0为正常）'
);


# INSERT INTO role(`name`, `description`) VALUES ('ADMINISTRATOR', 'Super Administrator');
# INSERT INTO user_role (`user_id`, `role_id`) VALUE (75, 1);
# INSERT INTO video_report (`user_id`, `video_id`) VALUE (0, 1);

