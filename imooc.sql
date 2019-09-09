/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 50562
 Source Host           : localhost:3306
 Source Schema         : imooc

 Target Server Type    : MySQL
 Target Server Version : 50562
 File Encoding         : 65001

 Date: 27/08/2019 21:44:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bgm
-- ----------------------------
DROP TABLE IF EXISTS `bgm`;
CREATE TABLE `bgm`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '播放地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of bgm
-- ----------------------------
INSERT INTO `bgm` VALUES ('1001', '1993', '60054701993', '\\bgm\\60054701993.mp3');
INSERT INTO `bgm` VALUES ('1003', 'tou', 'caibutou', '\\bgm\\caibutou.mp3');
INSERT INTO `bgm` VALUES ('1004', 'wenrou', 'gaisidewenrou', '\\bgm\\gaisidewenrou.mp3');
INSERT INTO `bgm` VALUES ('1005', 'nan', 'nanrenhua', '\\bgm\\nanrenhua.mp3');
INSERT INTO `bgm` VALUES ('19081975PT5DXM14', 'sss', 'aaa', '\\bgm\\caibutou.mp3');
INSERT INTO `bgm` VALUES ('1908269NYZXDSTMW', '网络歌手', '猜不透', '\\bgm\\caibutou12.mp3');
INSERT INTO `bgm` VALUES ('1908269RM3BPTZ9P', 'ada', 'sdsad', '\\bgm\\60054701993.mp3');

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`  (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `father_comment_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `to_user_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `video_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '视频id',
  `from_user_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '留言者，评论的用户id',
  `comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论内容',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '课程评论表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of comments
-- ----------------------------
INSERT INTO `comments` VALUES ('19080165Y8FFGBMW', NULL, NULL, '1907286MT7828000', '1907177ZWFKR8ARP', '测试', '2019-08-01 08:42:15');
INSERT INTO `comments` VALUES ('190801739W2F9KP0', NULL, NULL, '190729AH9MGZY3R4', '1907177ZWFKR8ARP', '常规测试', '2019-08-01 09:58:33');
INSERT INTO `comments` VALUES ('190801741D1G3BMW', NULL, NULL, '190729AH9MGZY3R4', '1907177ZWFKR8ARP', '说点什么', '2019-08-01 10:00:39');
INSERT INTO `comments` VALUES ('1908017543FH6KD4', NULL, NULL, '1907286MT7828000', '1907177ZWFKR8ARP', '说点什么', '2019-08-01 10:03:56');
INSERT INTO `comments` VALUES ('19080176BWFYFFW0', NULL, NULL, '1907276M691FTYNC', '1907177ZWFKR8ARP', '好的呢', '2019-08-01 10:07:46');
INSERT INTO `comments` VALUES ('19080176WPT6N828', NULL, NULL, '190729AH9MGZY3R4', '1907177ZWFKR8ARP', '怎么说？', '2019-08-01 10:09:09');
INSERT INTO `comments` VALUES ('1908017771PWN3F8', NULL, NULL, '190729AH9MGZY3R4', '1907177ZWFKR8ARP', '怎么说？', '2019-08-01 10:10:15');
INSERT INTO `comments` VALUES ('19080177YGTZ6XKP', NULL, NULL, '190729AH9MGZY3R4', '1907177ZWFKR8ARP', '帅气', '2019-08-01 10:12:21');
INSERT INTO `comments` VALUES ('1908017908233BMW', NULL, NULL, '190729AH9MGZY3R4', '1907177ZWFKR8ARP', '测试啦', '2019-08-01 10:15:32');
INSERT INTO `comments` VALUES ('1908017AFCSN3BMW', NULL, NULL, '190729AH9MGZY3R4', '1907177ZWFKR8ARP', '测试', '2019-08-01 10:20:03');
INSERT INTO `comments` VALUES ('1908017B8WP0GW28', NULL, NULL, '1907286MT7828000', '1907177ZWFKR8ARP', '和各个环节', '2019-08-01 10:22:28');
INSERT INTO `comments` VALUES ('1908017C6GDBX86W', NULL, NULL, '1907286MT7828000', '1907177ZWFKR8ARP', '是的撒大苏打', '2019-08-01 10:25:13');
INSERT INTO `comments` VALUES ('1908017DHP5HNT2W', NULL, NULL, '190729AH9MGZY3R4', '1907177ZWFKR8ARP', 'asfadadfaf', '2019-08-01 10:29:18');
INSERT INTO `comments` VALUES ('1908017HC8Y5B6CH', NULL, NULL, '190729AH9MGZY3R4', '1907177ZWFKR8ARP', 'asscsfdd', '2019-08-01 10:37:51');
INSERT INTO `comments` VALUES ('1908017HPFACS9KP', NULL, NULL, '190729AH9MGZY3R4', '1907177ZWFKR8ARP', 'sefsdfdsfsdf', '2019-08-01 10:38:43');
INSERT INTO `comments` VALUES ('1908017KKDB3NNF8', NULL, NULL, '190729AH9MGZY3R4', '1907177ZWFKR8ARP', '啥掉', '2019-08-01 10:41:24');
INSERT INTO `comments` VALUES ('1908017KX2GMGBMW', NULL, NULL, '190729AH9MGZY3R4', '1907177ZWFKR8ARP', '你是谁', '2019-08-01 10:42:13');
INSERT INTO `comments` VALUES ('1908017NFZTBDSY8', NULL, NULL, '1907276M691FTYNC', '1907177ZWFKR8ARP', '好的呀', '2019-08-01 10:47:08');
INSERT INTO `comments` VALUES ('1908017RZN1K4FA8', NULL, NULL, '190729AH9MGZY3R4', '1907177ZWFKR8ARP', '你好呀', '2019-08-01 10:54:30');
INSERT INTO `comments` VALUES ('1908017S40TY99WH', NULL, NULL, '190729AH9MGZY3R4', '1907177ZWFKR8ARP', '撒飒飒阿萨', '2019-08-01 10:54:58');
INSERT INTO `comments` VALUES ('1908017TPSCNZ5S8', NULL, NULL, '190729AH9MGZY3R4', '1907177ZWFKR8ARP', 'wjwrejrew', '2019-08-01 10:59:46');
INSERT INTO `comments` VALUES ('1908017TT5PH3G7C', NULL, NULL, '190729AH9MGZY3R4', '1907177ZWFKR8ARP', 'shfkjdfshfkjsdgdfgdfgdfgds', '2019-08-01 11:00:02');
INSERT INTO `comments` VALUES ('1908017W49R6WZC0', NULL, NULL, '190729AH9MGZY3R4', '1907177ZWFKR8ARP', 'adfsdfs', '2019-08-01 11:01:00');
INSERT INTO `comments` VALUES ('1908017W5A2FC6W0', NULL, NULL, '190729AH9MGZY3R4', '1907177ZWFKR8ARP', 'scsaassvdffghg', '2019-08-01 11:01:07');
INSERT INTO `comments` VALUES ('190801BXCZ8GT44H', '1908017TT5PH3G7C', '1907177ZWFKR8ARP', '190729AH9MGZY3R4', '1907177ZWFKR8ARP', '你好呀', '2019-08-01 16:41:13');
INSERT INTO `comments` VALUES ('190801BYB3B978PH', '1908017W5A2FC6W0', '1907177ZWFKR8ARP', '190729AH9MGZY3R4', '1907177ZWFKR8ARP', '傻不傻', '2019-08-01 16:44:01');
INSERT INTO `comments` VALUES ('190801BZN9CBB1WH', 'undefined', 'undefined', '190729AH9MGZY3R4', '1907177ZWFKR8ARP', 'asdyasd', '2019-08-01 16:47:54');
INSERT INTO `comments` VALUES ('190801BZZA8P7Z9P', 'undefined', 'undefined', '190729AH9MGZY3R4', '1907177ZWFKR8ARP', 'ysbdnsdbfsd', '2019-08-01 16:48:46');
INSERT INTO `comments` VALUES ('190801C006D763C0', '190801BZZA8P7Z9P', '1907177ZWFKR8ARP', '190729AH9MGZY3R4', '1907177ZWFKR8ARP', 'sdasdas', '2019-08-01 16:48:51');
INSERT INTO `comments` VALUES ('190801C0Z7S82K40', '1908017RZN1K4FA8', '1907177ZWFKR8ARP', '190729AH9MGZY3R4', '1907177ZWFKR8ARP', '测试', '2019-08-01 16:51:45');
INSERT INTO `comments` VALUES ('190801FN1HWCR3MW', 'undefined', 'undefined', '1907286MT7828000', '1907177ZWFKR8ARP', '哈哈哈哈哈', '2019-08-01 20:34:11');
INSERT INTO `comments` VALUES ('190801FN793A4N7C', 'undefined', 'undefined', '1907276M691FTYNC', '1907177ZWFKR8ARP', 'h啊哈哈', '2019-08-01 20:34:48');
INSERT INTO `comments` VALUES ('190801FZHATMHG0H', 'undefined', 'undefined', '190801FNC3YWTF80', '1907177ZWFKR8ARP', '你好呀', '2019-08-01 20:59:48');
INSERT INTO `comments` VALUES ('190801FZNT8XTRS8', 'undefined', 'undefined', '190801FNC3YWTF80', '1907177ZWFKR8ARP', 'hello', '2019-08-01 21:00:10');
INSERT INTO `comments` VALUES ('190801FZRCHNAHSW', '190801FZNT8XTRS8', '1907177ZWFKR8ARP', '190801FNC3YWTF80', '1907177ZWFKR8ARP', '你好呀', '2019-08-01 21:00:20');
INSERT INTO `comments` VALUES ('190801G140WKKP00', '190801FZRCHNAHSW', '1907177ZWFKR8ARP', '190801FNC3YWTF80', '1907177ZWFKR8ARP', '住在新泽西州新泽西', '2019-08-01 21:04:29');
INSERT INTO `comments` VALUES ('190801G18AXRWC28', 'undefined', 'undefined', '190801FNC3YWTF80', '1907177ZWFKR8ARP', 'x查询查询', '2019-08-01 21:04:57');
INSERT INTO `comments` VALUES ('190801G19ADDF79P', '190801G18AXRWC28', '1907177ZWFKR8ARP', '190801FNC3YWTF80', '1907177ZWFKR8ARP', '嘻嘻嘻在程序中', '2019-08-01 21:05:03');
INSERT INTO `comments` VALUES ('190801G1FGK59WH0', 'undefined', 'undefined', '190724BHFCKDD968', '1907177ZWFKR8ARP', '热不？', '2019-08-01 21:05:36');
INSERT INTO `comments` VALUES ('190801G1HHC6N44H', '190801G1FGK59WH0', '1907177ZWFKR8ARP', '190724BHFCKDD968', '1907177ZWFKR8ARP', '不热', '2019-08-01 21:05:49');
INSERT INTO `comments` VALUES ('1908279GMKXRZHX4', 'undefined', 'undefined', '1908198GK0AY3X1P', '1907177ZWFKR8ARP', '实打实打算', '2019-08-27 13:23:39');
INSERT INTO `comments` VALUES ('1908279GPXZ32FW0', 'undefined', 'undefined', '1907276KZGNMH4H0', '1907177ZWFKR8ARP', '嗡嗡嗡', '2019-08-27 13:23:54');

-- ----------------------------
-- Table structure for search_records
-- ----------------------------
DROP TABLE IF EXISTS `search_records`;
CREATE TABLE `search_records`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '搜索的内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '视频搜索的记录表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of search_records
-- ----------------------------
INSERT INTO `search_records` VALUES ('1', '慕课网');
INSERT INTO `search_records` VALUES ('190728AS65K8P84H', 'zdgahsdasjd');
INSERT INTO `search_records` VALUES ('190728ASKZN5R4M8', '郭金聪');
INSERT INTO `search_records` VALUES ('1907296462NXRW6W', '郭金聪');
INSERT INTO `search_records` VALUES ('190801FNFSB24Z2W', '哈哈哈哈');
INSERT INTO `search_records` VALUES ('190801FNTFFZ09D4', 'sadasdasd');
INSERT INTO `search_records` VALUES ('190801FTNXM06KD4', 'dfsefrfsd');
INSERT INTO `search_records` VALUES ('1908279GCSPZRFCH', '郭金聪');
INSERT INTO `search_records` VALUES ('2', '慕课网');
INSERT INTO `search_records` VALUES ('3', '慕课网');
INSERT INTO `search_records` VALUES ('4', '慕课网');
INSERT INTO `search_records` VALUES ('5', '慕课');
INSERT INTO `search_records` VALUES ('6', '慕课');
INSERT INTO `search_records` VALUES ('7', 'zookeeper');
INSERT INTO `search_records` VALUES ('8', 'zookeeper');
INSERT INTO `search_records` VALUES ('9', 'zookeeper');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `face_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '我的头像，如果没有默认给一张',
  `nickname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `fans_counts` int(11) NULL DEFAULT 0 COMMENT '我的粉丝数量',
  `follow_counts` int(11) NULL DEFAULT 0 COMMENT '我关注的人总数',
  `receive_like_counts` int(11) NULL DEFAULT 0 COMMENT '我接受到的赞美/收藏 的数量',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id`(`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1907177ZWFKR8ARP', 'lucy', '4QrcOUm6Wau+VuBX8g+IPg==', '/1907177ZWFKR8ARP/face/wxa250f029d848129e.o6zAJsyvLmFg0zQIHvdw-kA4wmF8.yYPclUj7tqwgc4fb9eeeb2948f7cd080ee7f7c73f734.png', 'lucy', 11, 21, 35);
INSERT INTO `users` VALUES ('190718ADY502H5WH', 'jack', '4QrcOUm6Wau+VuBX8g+IPg==', '/190718ADY502H5WH/face/wxa250f029d848129e.o6zAJsyvLmFg0zQIHvdw-kA4wmF8.UpyXdQ0xCIus3f58a3221f5476b5495c1cd8c0b81517.jpg', 'jack', 1, 1, 3);

-- ----------------------------
-- Table structure for users_fans
-- ----------------------------
DROP TABLE IF EXISTS `users_fans`;
CREATE TABLE `users_fans`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户',
  `fan_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '粉丝',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`, `fan_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户粉丝关联关系表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of users_fans
-- ----------------------------
INSERT INTO `users_fans` VALUES ('1907308XTWNAKGR4', '1907177ZWFKR8ARP', '190718ADY502H5WH');
INSERT INTO `users_fans` VALUES ('19073168D70HT3TC', '190718ADY502H5WH', '1907177ZWFKR8ARP');

-- ----------------------------
-- Table structure for users_like_videos
-- ----------------------------
DROP TABLE IF EXISTS `users_like_videos`;
CREATE TABLE `users_like_videos`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户',
  `video_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '视频',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_video_rel`(`user_id`, `video_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户喜欢的/赞过的视频' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of users_like_videos
-- ----------------------------
INSERT INTO `users_like_videos` VALUES ('1908198GMG9KNSRP', '1907177ZWFKR8ARP', '190724BHFCKDD968');
INSERT INTO `users_like_videos` VALUES ('1908279G8997WRWH', '1907177ZWFKR8ARP', '1907276A9251K0BC');
INSERT INTO `users_like_videos` VALUES ('190801FN0K1Y1BTC', '1907177ZWFKR8ARP', '1907286MT7828000');
INSERT INTO `users_like_videos` VALUES ('19073168BS2GS8M8', '1907177ZWFKR8ARP', '190729AH9MGZY3R4');
INSERT INTO `users_like_videos` VALUES ('1908279GN5HH7TC0', '1907177ZWFKR8ARP', '1908198GK0AY3X1P');
INSERT INTO `users_like_videos` VALUES ('190730A70Y7FBA14', '190718ADY502H5WH', '1907276KTCXWSX8H');
INSERT INTO `users_like_videos` VALUES ('19073081Y2KSXXGC', '190718ADY502H5WH', '1907276M691FTYNC');
INSERT INTO `users_like_videos` VALUES ('190729CMWHADPTF8', '190718ADY502H5WH', '190729AH9MGZY3R4');

-- ----------------------------
-- Table structure for users_report
-- ----------------------------
DROP TABLE IF EXISTS `users_report`;
CREATE TABLE `users_report`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `deal_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '被举报用户id',
  `deal_video_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型标题，让用户选择，详情见 枚举',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内容',
  `userid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '举报人的id',
  `create_date` datetime NOT NULL COMMENT '举报时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '举报用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of users_report
-- ----------------------------
INSERT INTO `users_report` VALUES ('1907317A3B81P940', '190718ADY502H5WH', '190729AH9MGZY3R4', '政治敏感', '', '1907177ZWFKR8ARP', '2019-07-31 10:18:52');
INSERT INTO `users_report` VALUES ('1907317CNX984ZR4', '190718ADY502H5WH', '190729AH9MGZY3R4', '政治敏感', '', '1907177ZWFKR8ARP', '2019-07-31 10:26:39');
INSERT INTO `users_report` VALUES ('1907317D08PW3KWH', '190718ADY502H5WH', '190729AH9MGZY3R4', '政治敏感', '', '1907177ZWFKR8ARP', '2019-07-31 10:27:33');
INSERT INTO `users_report` VALUES ('1907317DFHMZZRD4', '190718ADY502H5WH', '190729AH9MGZY3R4', '辱骂谩骂', '', '1907177ZWFKR8ARP', '2019-07-31 10:29:05');
INSERT INTO `users_report` VALUES ('1907317FBSDFM9YW', '190718ADY502H5WH', '190729AH9MGZY3R4', '辱骂谩骂', '测试', '1907177ZWFKR8ARP', '2019-07-31 10:31:47');

-- ----------------------------
-- Table structure for videos
-- ----------------------------
DROP TABLE IF EXISTS `videos`;
CREATE TABLE `videos`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发布者id',
  `audio_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户使用音频的信息',
  `video_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '视频描述',
  `video_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '视频存放的路径',
  `video_seconds` float(6, 2) NULL DEFAULT NULL COMMENT '视频秒数',
  `video_width` int(6) NULL DEFAULT NULL COMMENT '视频宽度',
  `video_height` int(6) NULL DEFAULT NULL COMMENT '视频高度',
  `cover_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '视频封面图',
  `like_counts` bigint(20) NOT NULL DEFAULT 0 COMMENT '喜欢/赞美的数量',
  `status` int(1) NOT NULL COMMENT '视频状态：\r\n1、发布成功\r\n2、禁止播放，管理员操作',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '视频信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of videos
-- ----------------------------
INSERT INTO `videos` VALUES ('190724BHFCKDD968', '1907177ZWFKR8ARP', '1004', '郭金聪', '/1907177ZWFKR8ARP/video/5322dc94-c59a-4787-9dbb-4826747a96d0.mp4', 15.04, 540, 960, '/1907177ZWFKR8ARP/video/5322dc94-c59a-4787-9dbb-4826747a96d0.jpg', 1, 1, '2019-07-24 16:14:21');
INSERT INTO `videos` VALUES ('1907276A1G55WZR4', '190718ADY502H5WH', '1005', '好开心', '/190718ADY502H5WH/video/192532c2-599b-451d-9708-3e2b8a2ade52.mp4', 15.02, 540, 960, '/190718ADY502H5WH/video/192532c2-599b-451d-9708-3e2b8a2ade52.jpg', 0, 1, '2019-07-27 08:54:36');
INSERT INTO `videos` VALUES ('1907276A9251K0BC', '190718ADY502H5WH', '1003', '恶搞一下', '/190718ADY502H5WH/video/fe4c7f1f-5a33-4b19-ac81-094a19c3abae.mp4', 15.02, 540, 960, '/190718ADY502H5WH/video/fe4c7f1f-5a33-4b19-ac81-094a19c3abae.jpg', 1, 1, '2019-07-27 08:55:25');
INSERT INTO `videos` VALUES ('1907276GB7G1K86W', '190718ADY502H5WH', '', '球星', '/190718ADY502H5WH/video/wxa250f029d848129e.o6zAJsyvLmFg0zQIHvdw-kA4wmF8.uOJvl943D6eDf56d1b11e7e6913ec7ed0d9dde8126ce.mp4', 11.59, 720, 1280, '/190718ADY502H5WH/video/5b29da78-aabd-4361-b9bb-3e67ff35dcf7.jpg', 0, 1, '2019-07-27 09:10:39');
INSERT INTO `videos` VALUES ('1907276KTCXWSX8H', '1907177ZWFKR8ARP', '1002', '好呀', '/1907177ZWFKR8ARP/video/5e01258b-42f3-4ebf-b73c-2e4a5bbebde3.mp4', 15.02, 540, 960, '/1907177ZWFKR8ARP/video/5e01258b-42f3-4ebf-b73c-2e4a5bbebde3.jpg', 1, 1, '2019-07-27 09:17:58');
INSERT INTO `videos` VALUES ('1907276KZGNMH4H0', '1907177ZWFKR8ARP', '', '厉害了', '/1907177ZWFKR8ARP/video/wxa250f029d848129e.o6zAJsyvLmFg0zQIHvdw-kA4wmF8.jNDvyt47uQWw7a9b0b786dcadce5d35acfe215fd3ed7.mp4', 15.02, 540, 960, '/1907177ZWFKR8ARP/video/87d876ae-9e17-460d-952f-bb9aa3b68622.jpg', 0, 1, '2019-07-27 09:18:25');
INSERT INTO `videos` VALUES ('1907276M691FTYNC', '1907177ZWFKR8ARP', '1001', '', '/1907177ZWFKR8ARP/video/e947dc36-28a7-4eda-9f1e-4860758bc82d.mp4', 11.59, 720, 1280, '/1907177ZWFKR8ARP/video/e947dc36-28a7-4eda-9f1e-4860758bc82d.jpg', 1, 1, '2019-07-27 09:19:08');
INSERT INTO `videos` VALUES ('1907286MT7828000', '1907177ZWFKR8ARP', '', '', '/1907177ZWFKR8ARP/video/wxa250f029d848129e.o6zAJsyvLmFg0zQIHvdw-kA4wmF8.ylWya7zhGTsbf56d1b11e7e6913ec7ed0d9dde8126ce.mp4', 11.59, 720, 1280, '/1907177ZWFKR8ARP/video/109b5aca-d236-48d8-9a27-7f03768ce0ce.jpg', 1, 1, '2019-07-28 09:20:57');
INSERT INTO `videos` VALUES ('190729AH9MGZY3R4', '190718ADY502H5WH', '1004', 'haoya', '/190718ADY502H5WH/video/68cfda11-9212-4b2c-b26f-76a6074ae741.mp4', 11.59, 720, 1280, '/190718ADY502H5WH/video/68cfda11-9212-4b2c-b26f-76a6074ae741.jpg', 2, 2, '2019-07-29 14:49:46');
INSERT INTO `videos` VALUES ('190801FNC3YWTF80', '1907177ZWFKR8ARP', '', '', '/1907177ZWFKR8ARP/video/wxa250f029d848129e.o6zAJsyvLmFg0zQIHvdw-kA4wmF8.tor4BXcFblB17a9b0b786dcadce5d35acfe215fd3ed7.mp4', 15.02, 540, 960, '/1907177ZWFKR8ARP/video/7b4026e4-b570-4753-9804-cd58112dd54c.jpg', 0, 1, '2019-08-01 20:35:19');
INSERT INTO `videos` VALUES ('1908198GK0AY3X1P', '1907177ZWFKR8ARP', '1001', '', '/1907177ZWFKR8ARP/video/ec64a25b-d2e9-44a1-88b1-d3c3c9d19372.mp4', 15.02, 540, 960, '/1907177ZWFKR8ARP/video/ec64a25b-d2e9-44a1-88b1-d3c3c9d19372.jpg', 1, 1, '2019-08-19 11:59:25');

SET FOREIGN_KEY_CHECKS = 1;
