/*
Navicat MySQL Data Transfer

Source Server         : dev - 124.206.188.76
Source Server Version : 50711
Source Host           : 124.206.188.76:3306
Source Database       : letsgo

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2017-03-22 13:39:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for a_call
-- ----------------------------
DROP TABLE IF EXISTS `a_call`;
CREATE TABLE `a_call` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `t_id` bigint(20) DEFAULT NULL COMMENT '团队ID',
  `team_id` varchar(32) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id（点名人）',
  `location_id` bigint(20) DEFAULT NULL COMMENT '位置id（点名人位置）',
  `status` int(11) DEFAULT '0' COMMENT '进行状态【0：进行中；1：已完成】',
  `notarrived_count` int(11) DEFAULT '0' COMMENT '未到人数',
  `arrived_count` int(11) DEFAULT '0' COMMENT '已到人数',
  `mark` varchar(256) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2076 DEFAULT CHARSET=utf8mb4 COMMENT='点名表（a_call）';

-- ----------------------------
-- Table structure for a_call_detail
-- ----------------------------
DROP TABLE IF EXISTS `a_call_detail`;
CREATE TABLE `a_call_detail` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `call_id` bigint(20) DEFAULT NULL COMMENT '点名ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `group_id` bigint(20) DEFAULT NULL COMMENT '小组ID',
  `member_id` bigint(20) DEFAULT NULL COMMENT '成员ID',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `group_count` int(11) DEFAULT NULL COMMENT '小组人数',
  `status` int(11) DEFAULT '0' COMMENT '签到状态【0：未签到；1：已签到】',
  `is_manual` int(11) DEFAULT '0' COMMENT '是否手动签到',
  `call_time` varchar(32) DEFAULT NULL COMMENT '签到时间',
  `latitude` double DEFAULT NULL COMMENT '纬度',
  `longitude` double DEFAULT NULL COMMENT '经度',
  `distance` double DEFAULT NULL COMMENT '距离',
  `address` varchar(256) DEFAULT NULL COMMENT '地址',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6498 DEFAULT CHARSET=utf8mb4 COMMENT='点名对象明细表（a_call_detail）';

-- ----------------------------
-- Table structure for a_call_detail_history
-- ----------------------------
DROP TABLE IF EXISTS `a_call_detail_history`;
CREATE TABLE `a_call_detail_history` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `call_id` bigint(20) DEFAULT NULL COMMENT '点名ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `group_id` bigint(20) DEFAULT NULL COMMENT '小组ID',
  `member_id` bigint(20) DEFAULT NULL COMMENT '成员ID',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `group_count` int(11) DEFAULT NULL COMMENT '小组人数',
  `status` int(11) DEFAULT '0' COMMENT '签到状态【0：未签到；1：已签到】',
  `is_manual` int(11) DEFAULT '0' COMMENT '是否手动签到',
  `call_time` varchar(32) DEFAULT NULL COMMENT '签到时间',
  `latitude` double DEFAULT NULL COMMENT '纬度',
  `longitude` double DEFAULT NULL COMMENT '经度',
  `distance` double DEFAULT NULL COMMENT '距离',
  `address` varchar(256) DEFAULT NULL COMMENT '地址',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6496 DEFAULT CHARSET=utf8mb4 COMMENT='点名对象明细表（a_call_detail_history）';

-- ----------------------------
-- Table structure for a_call_history
-- ----------------------------
DROP TABLE IF EXISTS `a_call_history`;
CREATE TABLE `a_call_history` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `t_id` bigint(20) DEFAULT NULL COMMENT '团队ID',
  `team_id` varchar(32) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id（点名人）',
  `location_id` bigint(20) DEFAULT NULL COMMENT '位置id（点名人位置）',
  `status` int(11) DEFAULT '0' COMMENT '进行状态【0：进行中；1：已完成】',
  `notarrived_count` int(11) DEFAULT '0' COMMENT '未到人数',
  `arrived_count` int(11) DEFAULT '0' COMMENT '已到人数',
  `mark` varchar(256) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2059 DEFAULT CHARSET=utf8mb4 COMMENT='点名表（a_call_history）';

-- ----------------------------
-- Table structure for a_location
-- ----------------------------
DROP TABLE IF EXISTS `a_location`;
CREATE TABLE `a_location` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `longitude` double DEFAULT NULL COMMENT '经度',
  `latitude` double DEFAULT NULL COMMENT '纬度',
  `location` varchar(256) DEFAULT NULL COMMENT '地址',
  `finish_time` timestamp NULL DEFAULT NULL COMMENT '提交时间',
  `ip` varchar(32) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=195492 DEFAULT CHARSET=utf8mb4 COMMENT='用户位置表（a_location）';

-- ----------------------------
-- Table structure for a_location_history
-- ----------------------------
DROP TABLE IF EXISTS `a_location_history`;
CREATE TABLE `a_location_history` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `longitude` double DEFAULT NULL COMMENT '经度',
  `latitude` double DEFAULT NULL COMMENT '纬度',
  `location` varchar(256) DEFAULT NULL COMMENT '地址',
  `finish_time` timestamp NULL DEFAULT NULL COMMENT '提交时间',
  `ip` varchar(32) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=195492 DEFAULT CHARSET=utf8mb4 COMMENT='用户位置历史表（a_location_history）';

-- ----------------------------
-- Table structure for a_room
-- ----------------------------
DROP TABLE IF EXISTS `a_room`;
CREATE TABLE `a_room` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `t_id` bigint(20) DEFAULT NULL COMMENT '团队ID',
  `team_id` varchar(32) DEFAULT NULL,
  `room_num` varchar(32) DEFAULT NULL COMMENT '房间号',
  `count` int(11) DEFAULT '0' COMMENT '入住人数',
  `room_type` int(11) DEFAULT NULL COMMENT '房间类型',
  `total_card_count` int(11) DEFAULT '1' COMMENT '房卡数量',
  `card_count` int(11) DEFAULT '0' COMMENT '领取房卡数量',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2334 DEFAULT CHARSET=utf8mb4 COMMENT='房间表（a_room）';

-- ----------------------------
-- Table structure for a_room_detail
-- ----------------------------
DROP TABLE IF EXISTS `a_room_detail`;
CREATE TABLE `a_room_detail` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `room_id` bigint(20) DEFAULT NULL COMMENT '房间ID',
  `group_id` bigint(20) DEFAULT NULL COMMENT '小组ID',
  `member_id` bigint(20) DEFAULT NULL COMMENT '成员ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3470 DEFAULT CHARSET=utf8mb4 COMMENT='房间明细表（a_room_detail）';

-- ----------------------------
-- Table structure for d_airport
-- ----------------------------
DROP TABLE IF EXISTS `d_airport`;
CREATE TABLE `d_airport` (
  `fight_no` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '航班号',
  `from_code` varchar(3) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '出发城市三字码',
  `from_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '出发城市中文',
  `to_code` varchar(3) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '到达城市三字码',
  `to_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '到达城市中文',
  `stop_code1` varchar(3) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '经停城市1三字代码',
  `stop_name1` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '经停城市1中文',
  `stop_code2` varchar(3) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '经停城市2三字代码',
  `stop_name2` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '经停城市2中文',
  `takeoff_time` varchar(8) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '起飞时间',
  `arrivel_time` varchar(8) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '到达时间',
  `airline_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '航司两字代码',
  `airline_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '航空公司中文',
  `is_stop` varchar(4) DEFAULT NULL COMMENT '经停状态'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for d_hotel
-- ----------------------------
DROP TABLE IF EXISTS `d_hotel`;
CREATE TABLE `d_hotel` (
  `hotel_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `hotel_chinese_name` varchar(300) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '酒店中文名称',
  `hotel_english_name` varchar(300) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '酒店英文名称',
  `hotel_pingyin_name` varchar(300) DEFAULT NULL COMMENT '酒店拼音名称',
  `delta` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '大洲',
  `country` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '国家',
  `country_name` varchar(100) DEFAULT NULL COMMENT '国家名称',
  `country_id` varchar(100) DEFAULT NULL COMMENT '国家id',
  `city` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '城市',
  `city_name` varchar(100) DEFAULT NULL COMMENT '城市名称',
  `city_id` varchar(100) DEFAULT NULL COMMENT '城市id',
  `part_icular_address` varchar(600) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '具体地址',
  `star_lv` varchar(10) DEFAULT NULL COMMENT '酒店星级',
  `briefintroduction` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '简介',
  `telephone` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '酒店电话',
  `pic_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '图片',
  `lon` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '经度',
  `lat` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '纬度',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `city1` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`hotel_id`),
  KEY `hotel_name_index` (`hotel_chinese_name`),
  KEY `idx_city1` (`city1`),
  KEY `inx_country_id` (`country_id`),
  KEY `inx_city` (`city`),
  KEY `inx_city_id` (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for d_region
-- ----------------------------
DROP TABLE IF EXISTS `d_region`;
CREATE TABLE `d_region` (
  `areaid` varchar(100) NOT NULL COMMENT '地区ID',
  `alias` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '中文名',
  `areaengname` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '英文名',
  `areapinyinname` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '英文拼音',
  `delta` varchar(10) DEFAULT NULL COMMENT '10：亚洲，11：欧洲，12：北美洲，13	南美洲，14：非洲，15：大洋洲，16：	南极洲 ',
  `lv` int(1) DEFAULT '0',
  `parentid` varchar(100) DEFAULT '0' COMMENT '父地区ID',
  `lat` double DEFAULT NULL COMMENT '经度',
  `lng` double DEFAULT NULL COMMENT '纬度',
  `timezone` varchar(64) DEFAULT NULL COMMENT '时区Id',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`areaid`),
  KEY `ai_parentid_inx` (`parentid`),
  KEY `dr_alias_inx` (`alias`) USING BTREE,
  KEY `areaid_inx` (`areaid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for d_region_old
-- ----------------------------
DROP TABLE IF EXISTS `d_region_old`;
CREATE TABLE `d_region_old` (
  `areaid` varchar(100) NOT NULL,
  `alias` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `areaengname` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `areapinyinname` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `delta` varchar(10) DEFAULT NULL COMMENT '10：亚洲，11：欧洲，12：北美洲，13	南美洲，14：非洲，15：大洋洲，16：	南极洲 ',
  `countryid` varchar(10) DEFAULT NULL,
  `cityid` varchar(10) DEFAULT NULL,
  `lv` int(1) DEFAULT '0',
  `parentid` varchar(100) DEFAULT '0',
  `lat` double DEFAULT NULL,
  `lng` double DEFAULT NULL,
  PRIMARY KEY (`areaid`),
  KEY `idx_areaid` (`areaid`(20))
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for d_spot
-- ----------------------------
DROP TABLE IF EXISTS `d_spot`;
CREATE TABLE `d_spot` (
  `mafeng_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `DB_ID` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL COMMENT '中文名字',
  `en_name` varchar(255) DEFAULT NULL COMMENT '英文名字',
  `spotpinyinname` varchar(255) DEFAULT NULL COMMENT '景点拼音',
  `destionation_id` varchar(255) DEFAULT NULL COMMENT '所属目的地',
  `brief` varchar(1024) DEFAULT NULL COMMENT '简介信息',
  `telno` varchar(64) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL COMMENT '官方网址',
  `traffic` text COMMENT '交通',
  `ticket` text COMMENT '门票',
  `open_time` text COMMENT '开放时间',
  `cost_time` text COMMENT '用时参考',
  `lng` varchar(255) DEFAULT NULL,
  `lat` varchar(255) DEFAULT NULL,
  `entrace` varchar(255) DEFAULT NULL COMMENT '温馨小提示',
  `address` varchar(255) DEFAULT NULL COMMENT '景点地址',
  `business_hours` varchar(255) DEFAULT NULL COMMENT '营业时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '抓取数据来源',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`mafeng_id`)
) ENGINE=MyISAM AUTO_INCREMENT=30979 DEFAULT CHARSET=utf8mb4 COMMENT='景点表';

-- ----------------------------
-- Table structure for d_spot_old
-- ----------------------------
DROP TABLE IF EXISTS `d_spot_old`;
CREATE TABLE `d_spot_old` (
  `mafeng_id` bigint(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `destionation_id` varchar(255) DEFAULT NULL COMMENT '所属目的地',
  `brief` varchar(1024) DEFAULT NULL COMMENT '简介信息',
  `telno` varchar(64) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `traffic` text COMMENT '交通',
  `ticket` text COMMENT '门票',
  `open_time` text COMMENT '开放时间',
  `cost_time` text COMMENT '用时参考',
  `lng` varchar(255) DEFAULT NULL,
  `lat` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`mafeng_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='景点表';

-- ----------------------------
-- Table structure for j_album_schedule
-- ----------------------------
DROP TABLE IF EXISTS `j_album_schedule`;
CREATE TABLE `j_album_schedule` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `schedule_id` bigint(20) DEFAULT NULL COMMENT '行程id',
  `schedule_detail_id` bigint(20) DEFAULT NULL COMMENT '行程详细id',
  `object_id` bigint(20) DEFAULT NULL COMMENT '每日行程关联表id',
  `type` int(11) DEFAULT NULL COMMENT '类型（1：景点，2：酒店，3：自费项目）',
  `photo_url` varchar(512) DEFAULT NULL COMMENT '图片地址',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1292 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for j_answer
-- ----------------------------
DROP TABLE IF EXISTS `j_answer`;
CREATE TABLE `j_answer` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `survey_id` bigint(20) DEFAULT NULL COMMENT '问卷ID',
  `question_id` bigint(20) DEFAULT NULL COMMENT '问题表id',
  `question_option_id` bigint(20) DEFAULT NULL COMMENT '选项表id',
  `content` text COMMENT '问答题答案',
  `more_option` varchar(64) DEFAULT NULL COMMENT '多选项',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) DEFAULT NULL COMMENT '版本',
  `option_answer` varchar(1024) DEFAULT NULL COMMENT '选项内容，多选以分号隔开',
  `type` int(11) DEFAULT NULL COMMENT '类型1为景点 2为当地交通 3为住宿 4为用餐 5为服务人员 6整体',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2958 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for j_destination
-- ----------------------------
DROP TABLE IF EXISTS `j_destination`;
CREATE TABLE `j_destination` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `destination` varchar(64) DEFAULT NULL COMMENT '目的地',
  `city` varchar(64) DEFAULT NULL COMMENT '所在城市',
  `timezone` varchar(64) DEFAULT NULL COMMENT '所在时区',
  `country` varchar(64) DEFAULT NULL COMMENT '所在国家',
  `lat` double DEFAULT NULL COMMENT '经度',
  `lng` double DEFAULT NULL COMMENT '纬度',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=183 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for j_hotel_evaluations
-- ----------------------------
DROP TABLE IF EXISTS `j_hotel_evaluations`;
CREATE TABLE `j_hotel_evaluations` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `hotel_name` varchar(64) DEFAULT NULL COMMENT '酒店名称',
  `hotel_eng_name` varchar(64) DEFAULT NULL COMMENT '酒店英文名称',
  `hotel_level` int(11) DEFAULT NULL COMMENT '酒店级别',
  `evaluate_time` timestamp NULL DEFAULT NULL COMMENT '评价时间',
  `grade` int(5) DEFAULT NULL COMMENT '综合评分',
  `state` varchar(32) DEFAULT NULL COMMENT '国家',
  `continent` varchar(32) DEFAULT NULL COMMENT '大洲',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `travel_id` bigint(20) DEFAULT NULL,
  `hotel_id` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for j_hotel_schedule
-- ----------------------------
DROP TABLE IF EXISTS `j_hotel_schedule`;
CREATE TABLE `j_hotel_schedule` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `schedule_detail_id` bigint(20) DEFAULT NULL COMMENT '行程详细id',
  `hotel_name` varchar(255) DEFAULT NULL COMMENT '酒店名称',
  `same_level` int(11) DEFAULT NULL COMMENT '是否是同级酒店（1：是，0：否）',
  `hotel_confirm` int(11) DEFAULT NULL COMMENT '酒店是否确认（0：不确定，1：确定）',
  `hotel_input` varchar(255) DEFAULT NULL COMMENT '酒店不确定后输入的',
  `delta` varchar(100) DEFAULT NULL COMMENT '大洲',
  `country` varchar(100) DEFAULT NULL COMMENT '国家',
  `city` varchar(100) DEFAULT NULL COMMENT '城市',
  `star_lv` varchar(100) DEFAULT NULL COMMENT '酒店星级',
  `briefintroduction` text COMMENT '简介',
  `lon` varchar(100) DEFAULT NULL COMMENT '经度',
  `lat` varchar(100) DEFAULT NULL COMMENT '纬度',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=397 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for j_line_evaluate
-- ----------------------------
DROP TABLE IF EXISTS `j_line_evaluate`;
CREATE TABLE `j_line_evaluate` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `country` varchar(64) DEFAULT NULL COMMENT '国家',
  `continent` varchar(32) DEFAULT NULL COMMENT '大洲',
  `start_date` timestamp NULL DEFAULT NULL COMMENT '开始时间',
  `end_date` timestamp NULL DEFAULT NULL COMMENT '结束时间',
  `count` int(11) DEFAULT NULL COMMENT '出团次数',
  `evaluate_num` int(11) DEFAULT NULL COMMENT '评价总人数',
  `grade` double DEFAULT NULL COMMENT '综合评分',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `travel_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for j_other_info
-- ----------------------------
DROP TABLE IF EXISTS `j_other_info`;
CREATE TABLE `j_other_info` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `schedule_id` bigint(20) DEFAULT NULL COMMENT '行程ID',
  `cost_include` text COMMENT '旅游费用包含',
  `cost_not_include` text COMMENT '旅游费用不包含',
  `own_expence_info` text COMMENT '自费项目介绍',
  `shopping_place` text COMMENT '购物场所',
  `travel_agency_info` text COMMENT '地接社信息',
  `group_club_info` text COMMENT '接团社信息',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='其他信息，行程中对应的费用项目、购物场所、地接社信息';

-- ----------------------------
-- Table structure for j_own_expense_schedule
-- ----------------------------
DROP TABLE IF EXISTS `j_own_expense_schedule`;
CREATE TABLE `j_own_expense_schedule` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `schedule_id` bigint(20) DEFAULT NULL COMMENT '行程id',
  `schedule_detail_id` bigint(20) DEFAULT NULL COMMENT '行程详细id',
  `item_name` varchar(100) DEFAULT NULL COMMENT '项目名称',
  `limit_number` varchar(255) DEFAULT NULL COMMENT '人数限制',
  `service_item` varchar(255) DEFAULT NULL COMMENT '服务项目',
  `reference_price` varchar(255) DEFAULT NULL COMMENT '参考价格',
  `briefintroduction` text COMMENT '简介',
  `item_city` varchar(100) DEFAULT NULL COMMENT '项目城市',
  `lon` varchar(100) DEFAULT NULL COMMENT '经度',
  `lat` varchar(100) DEFAULT NULL COMMENT '纬度',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=369 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for j_prompt_info
-- ----------------------------
DROP TABLE IF EXISTS `j_prompt_info`;
CREATE TABLE `j_prompt_info` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `schedule_id` bigint(20) DEFAULT NULL COMMENT '行程表_ID',
  `weather_situation` text COMMENT '天气概况',
  `dress_code` text COMMENT '着装要求',
  `time_info` text COMMENT '时间',
  `languages` text COMMENT '语言',
  `voltage` text COMMENT '电压',
  `foods` text COMMENT '饮食',
  `essential_item` text COMMENT '必备物品',
  `currency` text COMMENT '货币',
  `telephone_communication` text COMMENT '电话通信',
  `hotel` text COMMENT '酒店住宿',
  `customs_forbid` text COMMENT '风俗禁忌',
  `water_activities_note` text COMMENT '水上活动的注意事项',
  `tip_case` text COMMENT '小费情况',
  `customs` text COMMENT '海关',
  `china_notic` text COMMENT '中华人民共和国海关总署公告',
  `new_title` varchar(64) DEFAULT NULL COMMENT '新增标题',
  `new_contant` text COMMENT '新增内容',
  `security` text COMMENT '安全与防范',
  `special_attention` text CHARACTER SET utf8 COMMENT '特别注意',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `version` bigint(20) DEFAULT '0' COMMENT '版本号',
  `cost_include` text CHARACTER SET utf8 COMMENT '旅游费用包含',
  `cost_not_include` text CHARACTER SET utf8 COMMENT '旅游费用不包含',
  `own_expence_info` text CHARACTER SET utf8 COMMENT '自费项目介绍',
  `shopping_place` text CHARACTER SET utf8 COMMENT '购物场所',
  `travel_agency_info` text CHARACTER SET utf8 COMMENT '地接社信息',
  `group_club_info` text CHARACTER SET utf8 COMMENT '接团社信息',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=759 DEFAULT CHARSET=utf8mb4 COMMENT='包括 重要信息提示 和 风险与安全需知';

-- ----------------------------
-- Table structure for j_question
-- ----------------------------
DROP TABLE IF EXISTS `j_question`;
CREATE TABLE `j_question` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `survey_id` bigint(20) DEFAULT NULL COMMENT '问卷id',
  `question_type` int(11) DEFAULT NULL COMMENT '题型，1为单选，2为多选，3为问答，4为模板问题',
  `title` varchar(128) DEFAULT NULL COMMENT '问题标题',
  `description` varchar(128) DEFAULT NULL COMMENT '说明',
  `is_required` int(11) DEFAULT NULL COMMENT '是否必答,1:是，0:否',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) DEFAULT NULL COMMENT '版本',
  `type` int(11) DEFAULT NULL COMMENT '类型1为景点 2为当地交通 3为住宿 4为用餐 5为服务人员 6为整体',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2159 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for j_question_option
-- ----------------------------
DROP TABLE IF EXISTS `j_question_option`;
CREATE TABLE `j_question_option` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `question_id` bigint(20) DEFAULT NULL COMMENT '问题表id',
  `question_option` varchar(64) DEFAULT NULL COMMENT '选项内容',
  `survey_id` bigint(20) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1107 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for j_question_record
-- ----------------------------
DROP TABLE IF EXISTS `j_question_record`;
CREATE TABLE `j_question_record` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `team_num` varchar(32) DEFAULT NULL COMMENT '团号',
  `schedule_name` varchar(255) DEFAULT NULL COMMENT '行程名称',
  `start_date` timestamp NULL DEFAULT NULL COMMENT '行程开始时间',
  `end_date` timestamp NULL DEFAULT NULL COMMENT '行程结束时间',
  `real_name` varchar(32) DEFAULT NULL COMMENT '游客姓名',
  `sex` int(11) DEFAULT NULL COMMENT '性别【1：男性；0：女性】',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号码',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `mould_question1` varchar(255) DEFAULT NULL COMMENT '模板问题1',
  `mould_question2` varchar(255) DEFAULT NULL COMMENT '模板问题2',
  `mould_question3` varchar(255) DEFAULT NULL COMMENT '模板问题3',
  `mould_question4` varchar(255) DEFAULT NULL COMMENT '模板问题4',
  `mould_question5` varchar(255) DEFAULT NULL COMMENT '模板问题5',
  `mould_question6` varchar(255) DEFAULT NULL COMMENT '模板问题6',
  `custom_question1` varchar(255) DEFAULT NULL COMMENT '自定义问题1',
  `custom_question2` varchar(255) DEFAULT NULL,
  `custom_question3` varchar(255) DEFAULT NULL,
  `custom_question4` varchar(255) DEFAULT NULL,
  `custom_question5` varchar(255) DEFAULT NULL,
  `custom_question6` varchar(255) DEFAULT NULL,
  `custom_question7` varchar(255) DEFAULT NULL,
  `custom_question8` varchar(255) DEFAULT NULL,
  `custom_question9` varchar(255) DEFAULT NULL,
  `custom_question10` varchar(255) DEFAULT NULL,
  `custom_question11` varchar(255) DEFAULT NULL,
  `custom_question12` varchar(255) DEFAULT NULL,
  `custom_question13` varchar(255) DEFAULT NULL,
  `custom_question14` varchar(255) DEFAULT NULL,
  `custom_question15` varchar(255) DEFAULT NULL,
  `custom_question16` varchar(255) DEFAULT '',
  `custom_question17` varchar(255) DEFAULT NULL,
  `custom_question18` varchar(255) DEFAULT NULL,
  `custom_question19` varchar(255) DEFAULT NULL,
  `custom_question20` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for j_scenic
-- ----------------------------
DROP TABLE IF EXISTS `j_scenic`;
CREATE TABLE `j_scenic` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `schedule_detail_id` bigint(20) DEFAULT NULL COMMENT '行程id',
  `spot_id` bigint(11) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL COMMENT '景点名称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `version` bigint(20) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4713 DEFAULT CHARSET=utf8mb4 COMMENT='每日行程对应的景点';

-- ----------------------------
-- Table structure for j_scenic_schedule
-- ----------------------------
DROP TABLE IF EXISTS `j_scenic_schedule`;
CREATE TABLE `j_scenic_schedule` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `schedule_detail_id` bigint(20) DEFAULT NULL COMMENT '行程详细id',
  `scenic_name` varchar(255) DEFAULT NULL COMMENT '景点名称',
  `scenic_city` varchar(100) DEFAULT NULL COMMENT '景点城市',
  `briefintroduction` text COMMENT '简介',
  `lon` varchar(100) DEFAULT NULL COMMENT '经度',
  `lat` varchar(100) DEFAULT NULL COMMENT '纬度',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=398 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for j_schedule
-- ----------------------------
DROP TABLE IF EXISTS `j_schedule`;
CREATE TABLE `j_schedule` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `team_id` bigint(20) NOT NULL COMMENT '团队ID',
  `name` varchar(255) NOT NULL COMMENT '行程名称',
  `start_place` varchar(64) NOT NULL COMMENT '出发地',
  `start_date` timestamp NOT NULL COMMENT '开始日期',
  `end_date` timestamp NOT NULL COMMENT '结束日期',
  `collection_time` timestamp NULL DEFAULT NULL COMMENT '集合时间',
  `collection_place` varchar(256) DEFAULT NULL COMMENT '集合地点',
  `leader_id` bigint(20) DEFAULT NULL COMMENT '领队id',
  `feature` text CHARACTER SET utf8 COMMENT '行程特色',
  `feature_photo` text CHARACTER SET utf8 COMMENT '行程特色图片',
  `emergency_contact` text CHARACTER SET utf8 COMMENT '紧急联系人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  `process_status` int(10) DEFAULT NULL COMMENT '2已发布-3未发布-4待确认',
  `process_photos_url` text CHARACTER SET utf8 COMMENT '行程资料',
  `end_place` varchar(50) DEFAULT '' COMMENT '目的地国家的名称',
  `TYPE` int(11) DEFAULT NULL COMMENT '1为旅行社端的行程，2为领队自己创建的行程（公司后台的）',
  `user_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `del` int(11) DEFAULT '0' COMMENT '逻辑删除字段：0正常  1删除',
  PRIMARY KEY (`ID`),
  KEY `index_teamID` (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2336 DEFAULT CHARSET=utf8mb4 COMMENT='行程表（j_schedule）';

-- ----------------------------
-- Table structure for j_schedule_desc
-- ----------------------------
DROP TABLE IF EXISTS `j_schedule_desc`;
CREATE TABLE `j_schedule_desc` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `schedule_id` bigint(20) DEFAULT NULL COMMENT '行程id',
  `title` varchar(1000) DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8 COMMENT '内容',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=142 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for j_schedule_detail
-- ----------------------------
DROP TABLE IF EXISTS `j_schedule_detail`;
CREATE TABLE `j_schedule_detail` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `jour_id` bigint(20) DEFAULT NULL COMMENT '行程ID',
  `day_num` int(11) DEFAULT NULL COMMENT '第几天',
  `schedule_date` timestamp NULL DEFAULT NULL COMMENT '日期',
  `start_place_id` varchar(64) DEFAULT NULL,
  `start_place` varchar(64) NOT NULL COMMENT '出发地',
  `traffic1` tinyint(1) DEFAULT '-1' COMMENT '1 飞机， 2 火车  ， 3 大巴，4 船',
  `destination_id1` varchar(64) DEFAULT NULL,
  `destination1` varchar(64) DEFAULT NULL COMMENT '目的地1',
  `traffic2` tinyint(1) DEFAULT '-1' COMMENT '1 飞机， 2 火车  ， 3 大巴，4 船',
  `destination_id2` varchar(64) DEFAULT NULL,
  `destination2` varchar(64) DEFAULT NULL COMMENT '目的地2',
  `traffic3` tinyint(1) DEFAULT '-1' COMMENT '1 飞机， 2 火车  ， 3 大巴，4 船',
  `destination_id3` varchar(64) DEFAULT NULL,
  `destination3` varchar(64) DEFAULT NULL COMMENT '目的地3',
  `traffic4` tinyint(1) DEFAULT '-1' COMMENT '1 飞机， 2 火车  ， 3 大巴，4 船',
  `destination_id4` varchar(64) DEFAULT NULL,
  `destination4` varchar(64) DEFAULT NULL COMMENT '目的地4',
  `traffic5` tinyint(1) DEFAULT '-1' COMMENT '1 飞机， 2 火车  ， 3 大巴，4 船',
  `destination_id5` varchar(64) DEFAULT NULL,
  `destination5` varchar(255) DEFAULT NULL,
  `schedule_photos_url1` varchar(256) DEFAULT NULL COMMENT '行程图片1',
  `schedule_photos_url2` varchar(256) DEFAULT NULL COMMENT '行程图片2',
  `schedule_photos_url3` varchar(256) DEFAULT NULL COMMENT '行程图片3',
  `desc` text CHARACTER SET utf8 NOT NULL COMMENT '行程概述',
  `hotel_confirm` int(11) DEFAULT NULL COMMENT '酒店是否确认',
  `hotel_id` varchar(255) DEFAULT NULL,
  `hotel` varchar(255) DEFAULT NULL COMMENT '酒店名称',
  `hotel_input` varchar(255) DEFAULT NULL COMMENT '酒店不确定后输入的名称',
  `same_level` int(11) DEFAULT NULL COMMENT '是否是同级酒店',
  `flight1` text CHARACTER SET utf8 COMMENT '航班1',
  `flight2` text CHARACTER SET utf8 COMMENT '航班2',
  `traffic_info` text CHARACTER SET utf8 COMMENT '交通',
  `shopp_info` text CHARACTER SET utf8 COMMENT '购物',
  `catering_info` text CHARACTER SET utf8 COMMENT '餐饮说明',
  `leader_id` bigint(20) DEFAULT NULL COMMENT '导游id',
  `new_entry` text CHARACTER SET utf8 COMMENT '新增条目',
  `new_contant` text CHARACTER SET utf8 COMMENT '新增内容',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  `countryId1` varchar(100) DEFAULT NULL COMMENT '国家1Id',
  `country1` varchar(100) DEFAULT NULL COMMENT '国家1',
  `countryId2` varchar(100) DEFAULT NULL COMMENT '国家2Id',
  `country2` varchar(100) DEFAULT NULL COMMENT '国家2',
  `countryId3` varchar(100) DEFAULT NULL COMMENT '国家3Id',
  `country3` varchar(100) DEFAULT NULL COMMENT '国家3',
  `countryId4` varchar(100) DEFAULT NULL COMMENT '国家4Id',
  `country4` varchar(100) DEFAULT NULL COMMENT '国家4',
  `countryId5` varchar(100) DEFAULT NULL,
  `country5` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2615 DEFAULT CHARSET=utf8mb4 COMMENT='行程明细表（j_schedule_detail）';

-- ----------------------------
-- Table structure for j_schedule_detail_comment
-- ----------------------------
DROP TABLE IF EXISTS `j_schedule_detail_comment`;
CREATE TABLE `j_schedule_detail_comment` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `satisfied_status` int(11) DEFAULT NULL COMMENT '5为非常满意 4为满意 3为一般 2为不满意 1为非常不满意',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `version` bigint(20) DEFAULT NULL COMMENT '版本',
  `schedule_detail_id` bigint(20) DEFAULT NULL COMMENT '行程详情的id',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3055 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for j_score
-- ----------------------------
DROP TABLE IF EXISTS `j_score`;
CREATE TABLE `j_score` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `schedule_detail_comment_id` bigint(20) DEFAULT NULL COMMENT '每日行程点评id',
  `evaluate_option` int(11) DEFAULT NULL COMMENT '1为景点 2为当地交通 3为住宿 4为用餐 5为服务人员 6其他',
  `evaluate_id` varchar(256) DEFAULT NULL COMMENT 'evaluateOption对应项的ID evaluateOption为其他时,该项为输入框填写的内容',
  `score_option` varchar(32) DEFAULT NULL COMMENT '对什么做的评分',
  `score` int(11) DEFAULT NULL COMMENT '评分1-5分',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `version` bigint(20) DEFAULT NULL COMMENT '版本',
  `evaluate_sub_info` varchar(1024) DEFAULT NULL,
  `evaluate_proposal` varchar(256) DEFAULT NULL COMMENT '其他内容',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for j_score_record
-- ----------------------------
DROP TABLE IF EXISTS `j_score_record`;
CREATE TABLE `j_score_record` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `team_num` varchar(32) DEFAULT NULL COMMENT '团号',
  `name` varchar(255) DEFAULT NULL COMMENT '行程名称',
  `schedule_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '行程日期',
  `real_name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `sex` int(11) DEFAULT NULL COMMENT '性别【1：男性；0：女性】',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号码',
  `satisfied_status` int(11) DEFAULT NULL COMMENT '5为非常满意 4为满意 3为一般 2为不满意 1为非常不满意',
  `evaluate_option` int(11) DEFAULT NULL COMMENT '1为景点 2为当地交通 3为住宿 4为用餐 5为服务人员 6其他',
  `evaluate_id` varchar(256) DEFAULT NULL COMMENT 'valuateOption对应项的ID evaluateOption为其他时,该项为输入框填写的内容',
  `score_option` varchar(32) DEFAULT NULL COMMENT '对什么做的评分',
  `score` int(11) DEFAULT NULL COMMENT '评分1-5分',
  `evaluate_sub_info` varchar(1024) DEFAULT NULL COMMENT '用餐对应的菜量为4-a;卫生4-b;口味为4-c;环境为4-d;上菜数度为4-e.',
  `evaluate_proposal` varchar(256) DEFAULT NULL COMMENT '该项为输入框填写的内容',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for j_shopping_schedule
-- ----------------------------
DROP TABLE IF EXISTS `j_shopping_schedule`;
CREATE TABLE `j_shopping_schedule` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `schedule_id` bigint(20) DEFAULT NULL COMMENT '行程id',
  `schedule_detail_id` bigint(20) DEFAULT NULL COMMENT '行程详细id',
  `shopping_name` varchar(255) DEFAULT NULL COMMENT '场所名称',
  `shopping_city` varchar(100) DEFAULT NULL COMMENT '城市名称',
  `sell_products` varchar(255) DEFAULT NULL COMMENT '售卖产品',
  `residence_time` varchar(255) DEFAULT NULL COMMENT '停留时间',
  `lon` varchar(100) DEFAULT NULL COMMENT '经度',
  `lat` varchar(100) DEFAULT NULL COMMENT '纬度',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=358 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for j_survey
-- ----------------------------
DROP TABLE IF EXISTS `j_survey`;
CREATE TABLE `j_survey` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(64) DEFAULT NULL COMMENT '标题',
  `description` varchar(128) DEFAULT NULL COMMENT '说明',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) DEFAULT NULL COMMENT '版本',
  `team_id` bigint(20) DEFAULT NULL COMMENT '团队id',
  `survey_status` int(11) DEFAULT '0' COMMENT '问卷是否已创建完毕，0位否1为是，当问卷和问题选项全部提交才为1',
  `travel_id` bigint(20) DEFAULT NULL COMMENT '旅行社id',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=484 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for j_survey_detail
-- ----------------------------
DROP TABLE IF EXISTS `j_survey_detail`;
CREATE TABLE `j_survey_detail` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `pdf_url` text,
  `survey_id` bigint(20) DEFAULT NULL COMMENT '问卷id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `confirm_status` int(11) DEFAULT NULL COMMENT '确认状态1:是，0:否',
  `tx_groupId` varchar(32) DEFAULT '' COMMENT '腾讯组Id',
  `submit_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '提交时间',
  `sign_url` varchar(200) DEFAULT NULL COMMENT '签名地址（Url）',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `version` bigint(20) DEFAULT NULL COMMENT '版本',
  `thumbnail_url` varchar(200) DEFAULT NULL COMMENT 'pdf缩略图路径',
  `first` int(11) DEFAULT NULL COMMENT '是否第一次访问 1是；0否',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=524 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for j_travel_notes
-- ----------------------------
DROP TABLE IF EXISTS `j_travel_notes`;
CREATE TABLE `j_travel_notes` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `schedule_Id` bigint(20) DEFAULT NULL COMMENT '行程id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `schedule_imgUrl` varchar(256) DEFAULT NULL COMMENT '行程图片',
  `title` varchar(256) DEFAULT NULL COMMENT '标题',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `version` bigint(20) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for j_travel_notes_detail
-- ----------------------------
DROP TABLE IF EXISTS `j_travel_notes_detail`;
CREATE TABLE `j_travel_notes_detail` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `schedule_detai_iId` bigint(20) DEFAULT NULL COMMENT '行程明细id',
  `travel_notes_id` bigint(20) DEFAULT NULL COMMENT '游记id',
  `weather` varchar(256) DEFAULT NULL COMMENT '天气',
  `schedule_detai_imgUrl` varchar(256) DEFAULT NULL COMMENT '每日行程图片',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `version` bigint(20) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for j_travel_notes_detail_comment
-- ----------------------------
DROP TABLE IF EXISTS `j_travel_notes_detail_comment`;
CREATE TABLE `j_travel_notes_detail_comment` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `travel_notes_detail_id` bigint(20) DEFAULT NULL COMMENT '游记明细id',
  `type` bigint(20) DEFAULT NULL COMMENT '点评类型（1酒店 2景点 3其他）',
  `type_id` varchar(64) DEFAULT NULL COMMENT '点评项id',
  `type_name` varchar(64) DEFAULT NULL COMMENT '点评项名称',
  `comment` varchar(256) DEFAULT NULL COMMENT '点评内容',
  `type_imgUrl` varchar(256) DEFAULT NULL COMMENT '点评项图片',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `version` bigint(20) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for m_message
-- ----------------------------
DROP TABLE IF EXISTS `m_message`;
CREATE TABLE `m_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `travel_id` bigint(20) DEFAULT NULL COMMENT '旅行社的唯一标识',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `member_id` bigint(20) DEFAULT NULL COMMENT '成员的唯一标识',
  `travel_headUrl` varchar(256) DEFAULT NULL COMMENT '旅行社头像地址',
  `photo_url` varchar(256) DEFAULT NULL COMMENT '头像的url',
  `team_id` bigint(20) DEFAULT NULL COMMENT '团的唯一标识',
  `t_group_id` varchar(256) DEFAULT NULL COMMENT '腾讯组ID',
  `team_name` varchar(512) DEFAULT NULL COMMENT '团名称',
  `team_num` varchar(512) DEFAULT NULL COMMENT '团号',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间，每有一条回复时，都要更新此字段',
  `status` tinyint(4) DEFAULT NULL COMMENT '0 为未读 1 为已读为回复 2 为 已读回复',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COMMENT='问题反馈表';

-- ----------------------------
-- Table structure for m_message_text
-- ----------------------------
DROP TABLE IF EXISTS `m_message_text`;
CREATE TABLE `m_message_text` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `m_id` bigint(20) DEFAULT NULL COMMENT '反馈表的ID',
  `sender_id` bigint(20) DEFAULT NULL COMMENT '回复人的ID 旅行社ID 或者memberID',
  `sender_name` varchar(256) DEFAULT NULL COMMENT '回复人名字',
  `message` varchar(512) DEFAULT NULL COMMENT '回复的内容',
  `post_time` timestamp NULL DEFAULT NULL COMMENT '回复时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '0 成员1 旅行社',
  `photo_url1` varchar(256) DEFAULT NULL,
  `photo_url2` varchar(256) DEFAULT NULL,
  `photo_url3` varchar(256) DEFAULT NULL,
  `photo_url4` varchar(256) DEFAULT NULL,
  `photo_url5` varchar(256) DEFAULT NULL,
  `photo_url6` varchar(256) DEFAULT NULL,
  `photo_url7` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `photo_url8` varchar(256) DEFAULT NULL,
  `photo_url9` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=307 DEFAULT CHARSET=utf8mb4 COMMENT='问题反馈回复表';

-- ----------------------------
-- Table structure for n_announcement
-- ----------------------------
DROP TABLE IF EXISTS `n_announcement`;
CREATE TABLE `n_announcement` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `t_id` bigint(20) DEFAULT NULL COMMENT '团队ID',
  `team_id` varchar(32) DEFAULT NULL,
  `title` varchar(256) DEFAULT NULL COMMENT '标题',
  `content` longtext COMMENT '内容',
  `user_id` bigint(20) DEFAULT NULL COMMENT '发送人用户ID',
  `sender_name` varchar(32) DEFAULT NULL COMMENT '发布人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=111111111300 DEFAULT CHARSET=utf8mb4 COMMENT='公告表（n_announcement）';

-- ----------------------------
-- Table structure for n_notice
-- ----------------------------
DROP TABLE IF EXISTS `n_notice`;
CREATE TABLE `n_notice` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `t_id` bigint(20) DEFAULT NULL COMMENT '团队ID',
  `team_id` varchar(32) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL COMMENT '发送人用户ID',
  `sender_id` bigint(20) DEFAULT NULL COMMENT '发送人',
  `sender_name` varchar(32) DEFAULT NULL COMMENT '发送人姓名',
  `content` varchar(512) DEFAULT NULL COMMENT '内容',
  `video_url` varchar(256) DEFAULT NULL COMMENT '语音地址',
  `video_len` varchar(32) DEFAULT NULL COMMENT '语音时长',
  `type` int(11) DEFAULT NULL COMMENT '消息类型【1：集合；2：通知】',
  `time` timestamp NULL DEFAULT NULL COMMENT '事件/集合时间',
  `first_remind` int(11) DEFAULT NULL COMMENT '提醒',
  `timezone_id` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '时区id',
  `timezone` varchar(64) DEFAULT NULL COMMENT '时区',
  `longitude` double DEFAULT NULL COMMENT '经度',
  `latitude` double DEFAULT NULL COMMENT '纬度',
  `location` varchar(256) DEFAULT NULL COMMENT '地址',
  `traffic` varchar(512) DEFAULT NULL COMMENT '交通',
  `sure_count` int(11) DEFAULT '0' COMMENT '已确认人数',
  `notsure_count` int(11) DEFAULT NULL COMMENT '未确认人数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  `notice_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0 不需要签名  1 需要游客签名',
  `sign_count` smallint(6) NOT NULL DEFAULT '0' COMMENT '通知签名照片的个数',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3334084 DEFAULT CHARSET=utf8mb4 COMMENT='通知集合表（n_notice）';

-- ----------------------------
-- Table structure for n_notice_detail
-- ----------------------------
DROP TABLE IF EXISTS `n_notice_detail`;
CREATE TABLE `n_notice_detail` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `notice_id` bigint(20) DEFAULT NULL COMMENT '通知ID',
  `group_id` bigint(20) DEFAULT NULL COMMENT '小组ID',
  `member_id` bigint(20) DEFAULT NULL COMMENT '成员ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `team_id` varchar(32) DEFAULT NULL COMMENT '腾讯组ID',
  `type` int(11) DEFAULT NULL COMMENT '消息分类【1：集合；2：通知；3：公告；4：回复；',
  `is_active` int(11) DEFAULT NULL COMMENT '是否需要确认【0：不需要；1：需要】，2：需要签名',
  `active_status` int(11) DEFAULT '0' COMMENT '是否读取/确认【如果是需要确认：0：未确认；1：已确认；如果是不需要确认：0：未读取；1：已读取】',
  `active_time` timestamp NULL DEFAULT NULL COMMENT '确认时间',
  `is_option` int(11) DEFAULT NULL COMMENT '用户是否点击查看列表动作',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`),
  KEY `index_user_id` (`user_id`),
  KEY `index_team_id` (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=390776 DEFAULT CHARSET=utf8mb4 COMMENT='通知确认表（n_notice_detail）';

-- ----------------------------
-- Table structure for n_notice_reply
-- ----------------------------
DROP TABLE IF EXISTS `n_notice_reply`;
CREATE TABLE `n_notice_reply` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `notice_id` bigint(20) DEFAULT NULL COMMENT '通知ID',
  `member_id` bigint(20) DEFAULT NULL COMMENT '成员ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `reply` varchar(256) DEFAULT NULL COMMENT '回复',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1210 DEFAULT CHARSET=utf8mb4 COMMENT='通知回复表（n_notice_reply）';

-- ----------------------------
-- Table structure for n_notice_sign
-- ----------------------------
DROP TABLE IF EXISTS `n_notice_sign`;
CREATE TABLE `n_notice_sign` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `notice_detail_id` bigint(20) DEFAULT NULL COMMENT '通知ID',
  `member_id` bigint(20) DEFAULT NULL COMMENT '成员id',
  `member_name` varchar(255) DEFAULT NULL COMMENT '成员名字',
  `sign_url` varchar(255) DEFAULT NULL COMMENT '游客的签名地址',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='通知签名确认表（n_notice_sign）';

-- ----------------------------
-- Table structure for n_remind
-- ----------------------------
DROP TABLE IF EXISTS `n_remind`;
CREATE TABLE `n_remind` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `notice_id` bigint(20) DEFAULT NULL COMMENT '通知ID',
  `member_id` bigint(20) DEFAULT NULL COMMENT '成员ID',
  `appointed_time` timestamp NULL DEFAULT NULL COMMENT '事件/集合时间',
  `first_remind` int(11) DEFAULT NULL COMMENT '提醒',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1316 DEFAULT CHARSET=utf8mb4 COMMENT='提醒表（n_remind）';

-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(190) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for oauth_approvals
-- ----------------------------
DROP TABLE IF EXISTS `oauth_approvals`;
CREATE TABLE `oauth_approvals` (
  `userId` varchar(256) DEFAULT NULL,
  `clientId` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `expiresAt` timestamp NULL DEFAULT NULL,
  `lastModifiedAt` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(190) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for oauth_client_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_token`;
CREATE TABLE `oauth_client_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(190) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code` (
  `code` varchar(256) DEFAULT NULL,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for s_app_ver
-- ----------------------------
DROP TABLE IF EXISTS `s_app_ver`;
CREATE TABLE `s_app_ver` (
  `ID` bigint(20) NOT NULL COMMENT 'ID',
  `device_type` varchar(32) NOT NULL COMMENT '设备类型',
  `module` varchar(32) NOT NULL COMMENT '模块',
  `version` varchar(32) NOT NULL COMMENT '版本号',
  `content` varchar(2048) DEFAULT NULL COMMENT '版本说明',
  `updateurl` varchar(512) NOT NULL COMMENT '下载URL',
  `updateforce` int(11) NOT NULL COMMENT '是否强制更新',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for s_basedata_ver
-- ----------------------------
DROP TABLE IF EXISTS `s_basedata_ver`;
CREATE TABLE `s_basedata_ver` (
  `ID` bigint(20) NOT NULL COMMENT 'ID',
  `device_type` varchar(32) NOT NULL COMMENT '设备类型',
  `module` varchar(32) NOT NULL COMMENT '模块',
  `version` varchar(32) NOT NULL COMMENT '版本号',
  `updateurl` varchar(512) NOT NULL COMMENT '下载URL',
  `checkcode` varchar(64) DEFAULT NULL COMMENT '校验码',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for s_catalog
-- ----------------------------
DROP TABLE IF EXISTS `s_catalog`;
CREATE TABLE `s_catalog` (
  `catalog_code` varchar(32) NOT NULL COMMENT '类目编号',
  `catalog_name` varchar(64) DEFAULT NULL COMMENT '类目名称',
  `descn` varchar(256) DEFAULT NULL COMMENT '类目描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`catalog_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据字典分类表（s_catalog）';

-- ----------------------------
-- Table structure for s_dataitem
-- ----------------------------
DROP TABLE IF EXISTS `s_dataitem`;
CREATE TABLE `s_dataitem` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `item_code` varchar(32) DEFAULT NULL COMMENT '数据项编码',
  `item_name` varchar(64) DEFAULT NULL COMMENT '数据项目名称',
  `item_value` varchar(32) DEFAULT NULL COMMENT '数据项值',
  `catalog_code` varchar(32) NOT NULL COMMENT '所属类目编号',
  `descn` varchar(256) DEFAULT NULL COMMENT '数据项描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COMMENT='数据字典数据项表（s_dataitem）';

-- ----------------------------
-- Table structure for s_device
-- ----------------------------
DROP TABLE IF EXISTS `s_device`;
CREATE TABLE `s_device` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `device_uuid` varchar(128) DEFAULT NULL COMMENT '设备唯一标识',
  `device_type` varchar(64) DEFAULT NULL COMMENT '设备类别【IOS、Android】',
  `device_model` varchar(64) DEFAULT NULL COMMENT '设备型号',
  `device_version` varchar(32) DEFAULT NULL COMMENT '设备版本号',
  `regId` varchar(128) DEFAULT NULL COMMENT '小米注册ID',
  `alias` varchar(128) DEFAULT NULL COMMENT '小米别名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  `is_HuaWei` varchar(1) DEFAULT '0' COMMENT '是否为华为手机：1是；0否',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=253 DEFAULT CHARSET=utf8mb4 COMMENT='用户设备表（s_device）';

-- ----------------------------
-- Table structure for s_friends
-- ----------------------------
DROP TABLE IF EXISTS `s_friends`;
CREATE TABLE `s_friends` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `tx_user_id` varchar(64) DEFAULT NULL,
  `friend_user_id` bigint(20) DEFAULT NULL COMMENT '好友id',
  `friend_tx_user_id` varchar(64) DEFAULT NULL,
  `friend_type` int(11) DEFAULT NULL COMMENT '好友类型.1:游客,2:同行',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `version` bigint(20) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=221 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for s_invitation_code
-- ----------------------------
DROP TABLE IF EXISTS `s_invitation_code`;
CREATE TABLE `s_invitation_code` (
  `code` varchar(10) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for s_invitation_logs
-- ----------------------------
DROP TABLE IF EXISTS `s_invitation_logs`;
CREATE TABLE `s_invitation_logs` (
  `ID` bigint(20) DEFAULT NULL,
  `inviter_user_id` bigint(20) DEFAULT NULL COMMENT '邀请者的用户ID',
  `invitee_user_id` bigint(20) DEFAULT NULL COMMENT '被邀请者的用户ID',
  `create_time` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for s_log_manage
-- ----------------------------
DROP TABLE IF EXISTS `s_log_manage`;
CREATE TABLE `s_log_manage` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `account_type` int(11) DEFAULT NULL COMMENT '账号类型:0旅行社管理员，1门店销售（门店），2计调，3领队，',
  `NAME` varchar(64) DEFAULT NULL COMMENT '姓名',
  `operate_type` varchar(16) DEFAULT NULL COMMENT '操作类型',
  `operate_model` varchar(32) DEFAULT NULL COMMENT '操作模块',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `operate_content` text COMMENT '操作内容',
  `operate_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `VERSION` bigint(20) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1444 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for s_message
-- ----------------------------
DROP TABLE IF EXISTS `s_message`;
CREATE TABLE `s_message` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `content` longtext COMMENT '短信内容',
  `param` longtext COMMENT '参数',
  `send_time` timestamp NULL DEFAULT NULL COMMENT '发送时间',
  `sms_type` int(11) DEFAULT NULL COMMENT '发送类型',
  `sms_status` varchar(256) DEFAULT NULL COMMENT '发送状态【1：成功；0：失败】',
  `phone` varchar(32) DEFAULT NULL COMMENT '接收人手机',
  `valid` int(11) DEFAULT '1' COMMENT '短信有效性【1：有效；0：无效】',
  `mark` varchar(256) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=16622 DEFAULT CHARSET=utf8mb4 COMMENT='短信表（s_message）';

-- ----------------------------
-- Table structure for s_nation_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `s_nation_dictionary`;
CREATE TABLE `s_nation_dictionary` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `code` varchar(20) DEFAULT NULL COMMENT '币种三字码',
  `name` varchar(100) DEFAULT NULL COMMENT '币种名称',
  `nationname` varchar(100) DEFAULT NULL COMMENT '国家名称',
  `coinsign` varchar(100) DEFAULT NULL COMMENT '币种符号',
  `nationflagurl` varchar(250) DEFAULT NULL COMMENT '国旗地址',
  `nationdesc` varchar(1000) DEFAULT NULL COMMENT '国家描述',
  `createtime` timestamp NULL DEFAULT NULL COMMENT '记录时间',
  `useful` int(11) DEFAULT NULL COMMENT '是否可用',
  `continentscode` varchar(20) DEFAULT NULL COMMENT '洲编码',
  `continentsname` varchar(20) DEFAULT NULL COMMENT '洲名称',
  `ishot` int(11) DEFAULT NULL COMMENT '是否热门',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for s_push
-- ----------------------------
DROP TABLE IF EXISTS `s_push`;
CREATE TABLE `s_push` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `secretKey` varchar(32) DEFAULT NULL,
  `msgId` varchar(128) DEFAULT NULL COMMENT '消息ID',
  `title` varchar(16) DEFAULT NULL COMMENT '标题',
  `description` varchar(128) DEFAULT NULL COMMENT '描述',
  `param` longtext COMMENT '消息体',
  `push_status` int(11) DEFAULT NULL COMMENT '推送状态【1：成功；0失败】',
  `error_message` varchar(255) DEFAULT NULL,
  `error_code` int(11) DEFAULT NULL,
  `result` varchar(512) DEFAULT NULL,
  `msgResult` varchar(1024) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=497504 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for s_resource
-- ----------------------------
DROP TABLE IF EXISTS `s_resource`;
CREATE TABLE `s_resource` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(32) NOT NULL COMMENT '权限名称',
  `res_type` varchar(32) NOT NULL COMMENT '权限类型【如URL】',
  `res_string` varchar(128) NOT NULL COMMENT '权限内容【如，url字符串】',
  `priority` int(11) NOT NULL COMMENT '优先级',
  `moudle` varchar(32) DEFAULT NULL COMMENT '权限模块',
  `descn` varchar(256) DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限资源表（s_resource）';

-- ----------------------------
-- Table structure for s_reward_details
-- ----------------------------
DROP TABLE IF EXISTS `s_reward_details`;
CREATE TABLE `s_reward_details` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '奖励人',
  `amount` double DEFAULT NULL COMMENT '奖励金额',
  `descn` varchar(100) DEFAULT NULL COMMENT '奖励描述',
  `is_into` int(11) DEFAULT NULL COMMENT '是否入账【0：未入账；1：已入账】',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `from_id` bigint(20) DEFAULT NULL COMMENT '奖励来源（用户ID）',
  `reward_type` int(4) DEFAULT NULL COMMENT '奖励类型【1：邀请奖励；2：带团人头奖励；3：分享奖励】',
  `team_id` bigint(20) DEFAULT NULL COMMENT '团队ID',
  `reward_from` int(4) DEFAULT NULL COMMENT '带团奖励对象 1：邀请人，2：被邀请人',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for s_role
-- ----------------------------
DROP TABLE IF EXISTS `s_role`;
CREATE TABLE `s_role` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `roleName` varchar(32) NOT NULL COMMENT '角色名称',
  `descn` varchar(256) DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='角色表（s_role）';

-- ----------------------------
-- Table structure for s_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `s_role_resource`;
CREATE TABLE `s_role_resource` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `resource_id` bigint(20) NOT NULL COMMENT '权限ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表（s_role_resource）';

-- ----------------------------
-- Table structure for s_tags
-- ----------------------------
DROP TABLE IF EXISTS `s_tags`;
CREATE TABLE `s_tags` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `version` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for s_tally_book
-- ----------------------------
DROP TABLE IF EXISTS `s_tally_book`;
CREATE TABLE `s_tally_book` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `expense` int(11) DEFAULT NULL COMMENT '消费类型\r\n            0为餐饮\r\n            1为交通\r\n            2为购物\r\n            3为娱乐\r\n            4为门票\r\n            5为住宿\r\n            6为医疗\r\n            7为美容\r\n            8为保险\r\n            9为其他',
  `money` double DEFAULT NULL COMMENT '金额',
  `equivalent_money` double DEFAULT NULL COMMENT '折算成人民币金额',
  `currency` varchar(20) DEFAULT NULL COMMENT '币种',
  `record_date` timestamp NULL DEFAULT NULL COMMENT '日期',
  `payment_type` int(11) DEFAULT NULL COMMENT '支付方式',
  `remarks` varchar(256) DEFAULT NULL COMMENT '备注',
  `thumbnail_url` varchar(256) DEFAULT NULL COMMENT '缩略图',
  `photo_url` varchar(256) DEFAULT NULL COMMENT '图片址址',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `version` bigint(20) DEFAULT NULL COMMENT '版本',
  `tx_groupId` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=207 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for s_thirdparty_account
-- ----------------------------
DROP TABLE IF EXISTS `s_thirdparty_account`;
CREATE TABLE `s_thirdparty_account` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `account_type` int(11) DEFAULT NULL COMMENT '账号类型',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `unionid` varchar(256) DEFAULT NULL COMMENT '微信的唯一标识',
  `backupField1` varchar(256) DEFAULT NULL,
  `backupField2` varchar(256) DEFAULT NULL,
  `backupField3` varchar(256) DEFAULT NULL,
  `backupField4` varchar(256) DEFAULT NULL,
  `backupField5` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=147 DEFAULT CHARSET=utf8mb4 COMMENT='第三方账号表（s_social_account）';

-- ----------------------------
-- Table structure for s_trade_detail
-- ----------------------------
DROP TABLE IF EXISTS `s_trade_detail`;
CREATE TABLE `s_trade_detail` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `trade_orderno` varchar(40) DEFAULT NULL COMMENT '交易订单号',
  `trade_type` int(11) DEFAULT NULL COMMENT '交易类型，1转帐，2提现',
  `trade_name` varchar(256) DEFAULT '' COMMENT '交易描述',
  `trade_amount` double DEFAULT NULL,
  `trade_date` timestamp NULL DEFAULT NULL COMMENT '交易日期',
  `trade_status` int(11) DEFAULT NULL COMMENT '交易状态(1交易成功,2交易失败)',
  `cardno` varchar(40) DEFAULT NULL COMMENT '银行卡号',
  `jrmf_orderno` varchar(40) DEFAULT NULL COMMENT '金融魔方订单号',
  `arrive_date` timestamp NULL DEFAULT NULL COMMENT '预计到账日期',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `version` bigint(20) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for s_user
-- ----------------------------
DROP TABLE IF EXISTS `s_user`;
CREATE TABLE `s_user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_name` varchar(64) DEFAULT NULL COMMENT '用户名',
  `real_name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `nick_name` varchar(64) DEFAULT NULL COMMENT '昵称',
  `password` varchar(128) DEFAULT NULL COMMENT '密码',
  `sex` int(11) DEFAULT NULL COMMENT '性别 1:男，2：女',
  `user_status` int(11) DEFAULT '1' COMMENT '用户状态：[1:可用，2:审核中，3:停用]',
  `birthday` varchar(64) DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `mail` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `type` int(11) DEFAULT NULL,
  `photo_url` varchar(256) DEFAULT NULL COMMENT '头像地址',
  `userSig` varchar(1024) DEFAULT NULL COMMENT '腾讯云签名',
  `login_total_count` int(11) DEFAULT NULL COMMENT '登录限制次数',
  `login_count` int(11) DEFAULT NULL COMMENT '登录累计次数',
  `invite_code` varchar(10) DEFAULT NULL COMMENT '邀请码',
  `invite_count` int(11) DEFAULT NULL COMMENT '邀请人数',
  `total_reward` double(11,0) DEFAULT NULL COMMENT '奖励',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  `guide_status` int(20) DEFAULT '0',
  `traveler_Id` bigint(20) DEFAULT NULL,
  `remarks` varchar(1024) DEFAULT NULL,
  `role` int(20) DEFAULT NULL,
  `evaluationTags` varchar(255) DEFAULT NULL COMMENT '自我评价标签  如果有多个标签，则需要用英文逗号“,”分隔开 ',
  `languages` varchar(255) DEFAULT NULL COMMENT '擅长语种',
  `tourRoutes` varchar(255) DEFAULT NULL COMMENT '擅长旅游线路',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=66879 DEFAULT CHARSET=utf8mb4 COMMENT='用户表（s_user）';

-- ----------------------------
-- Table structure for s_user_role
-- ----------------------------
DROP TABLE IF EXISTS `s_user_role`;
CREATE TABLE `s_user_role` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID; 领队(2.0改为普通用户)：roleId为1；导游：roleId为2；游客：roleId为3',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=950 DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表（s_user_role）';

-- ----------------------------
-- Table structure for s_user_tag
-- ----------------------------
DROP TABLE IF EXISTS `s_user_tag`;
CREATE TABLE `s_user_tag` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `labelling_user_id` bigint(20) DEFAULT NULL,
  `tag_id` bigint(20) DEFAULT NULL,
  `labeled_user_id` bigint(20) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `version` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=391 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for s_validation_code
-- ----------------------------
DROP TABLE IF EXISTS `s_validation_code`;
CREATE TABLE `s_validation_code` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `code` varchar(8) DEFAULT NULL COMMENT '验证码',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `valid_time` bigint(20) DEFAULT NULL COMMENT '有效时间',
  `scope` int(4) DEFAULT NULL COMMENT '作用范围【1：登录；2：设置密码；3：注册】',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3548 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for s_wx_info
-- ----------------------------
DROP TABLE IF EXISTS `s_wx_info`;
CREATE TABLE `s_wx_info` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '系统id',
  `unionid` varchar(256) DEFAULT NULL COMMENT '微信的唯一标识',
  `openid` varchar(256) DEFAULT NULL COMMENT 'app用户登录授权的openid',
  `appId` varchar(256) DEFAULT NULL COMMENT '关注微信公众号的openid',
  `nickname` varchar(128) DEFAULT NULL COMMENT '微信昵称',
  `sex` varchar(10) DEFAULT NULL COMMENT '性别',
  `province` varchar(64) DEFAULT NULL COMMENT '省份',
  `city` varchar(64) DEFAULT NULL COMMENT '微信用户注册的城市',
  `country` varchar(128) DEFAULT NULL COMMENT '微信用户注册的国家',
  `headImgUrl` varchar(512) DEFAULT NULL COMMENT '微信头像的url',
  `binding` tinyint(4) DEFAULT NULL COMMENT '0 为绑定用户 1为没有绑定用户 默认为1',
  `status` tinyint(4) DEFAULT NULL COMMENT '微信用户是否取消关注公众号【0 没有取消 ， 1 取消关注】',
  `backupField1` varchar(256) DEFAULT NULL,
  `backupField2` varchar(256) DEFAULT NULL,
  `backupField3` varchar(256) DEFAULT NULL,
  `backupField4` varchar(256) DEFAULT NULL,
  `backupField5` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8mb4 COMMENT='微信信息表';

-- ----------------------------
-- Table structure for s_wx_template_message
-- ----------------------------
DROP TABLE IF EXISTS `s_wx_template_message`;
CREATE TABLE `s_wx_template_message` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `openid` varchar(256) DEFAULT NULL COMMENT 'app用户登录授权的openid',
  `template_id` varchar(256) DEFAULT NULL COMMENT '模板ID',
  `url` varchar(256) DEFAULT NULL COMMENT '模板跳转路径',
  `template_data` varchar(512) DEFAULT NULL COMMENT '模板数据',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3738 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for s_yahoo_rate
-- ----------------------------
DROP TABLE IF EXISTS `s_yahoo_rate`;
CREATE TABLE `s_yahoo_rate` (
  `ID` varchar(130) NOT NULL COMMENT '国家ID',
  `name` varchar(130) DEFAULT NULL COMMENT '汇率国家',
  `rate` varchar(130) DEFAULT NULL COMMENT '汇率',
  `date` varchar(50) DEFAULT NULL COMMENT '日期',
  `time` varchar(100) DEFAULT NULL COMMENT '时间',
  `ask` varchar(100) DEFAULT NULL COMMENT '卖出价',
  `bid` varchar(100) DEFAULT NULL COMMENT '买入价',
  `lastupdatetime` timestamp NULL DEFAULT NULL COMMENT '最新更新时间',
  `isdel` int(11) DEFAULT NULL COMMENT '是否删除【0：未删除；1：删除】',
  `batch` int(11) DEFAULT NULL COMMENT '批次号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for s_yahoo_weather
-- ----------------------------
DROP TABLE IF EXISTS `s_yahoo_weather`;
CREATE TABLE `s_yahoo_weather` (
  `ID` bigint(20) NOT NULL,
  `name` varchar(130) DEFAULT NULL COMMENT '城市名',
  `city_name` varchar(130) DEFAULT NULL COMMENT '城市中文名',
  `high` varchar(50) DEFAULT NULL COMMENT '最高温度',
  `low` varchar(50) DEFAULT NULL COMMENT '最低温度',
  `date` varchar(50) DEFAULT NULL COMMENT '日期',
  `day` varchar(50) DEFAULT NULL COMMENT '星期',
  `code` varchar(50) DEFAULT NULL COMMENT '天气描述码',
  `text` varchar(200) DEFAULT NULL COMMENT '天气描述',
  `descn` varchar(200) DEFAULT NULL COMMENT '天气中文描述',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '最新更新时间',
  `version` bigint(20) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_album_agency
-- ----------------------------
DROP TABLE IF EXISTS `t_album_agency`;
CREATE TABLE `t_album_agency` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `travel_id` bigint(20) DEFAULT NULL COMMENT '旅行社id',
  `object_id` bigint(20) DEFAULT NULL COMMENT '旅行社私有表id',
  `type` int(11) DEFAULT NULL COMMENT '旅行社私有表类型（1：景点，2：酒店，3：自费项目）',
  `photo_url` varchar(512) DEFAULT NULL COMMENT '图片地址',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` varchar(255) DEFAULT NULL COMMENT '删除时此字段更新为deleted',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=647 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_base_pinyin
-- ----------------------------
DROP TABLE IF EXISTS `t_base_pinyin`;
CREATE TABLE `t_base_pinyin` (
  `code_` int(11) NOT NULL,
  `pin_yin_` varchar(255) CHARACTER SET gbk DEFAULT NULL,
  PRIMARY KEY (`code_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_card_language_type
-- ----------------------------
DROP TABLE IF EXISTS `t_card_language_type`;
CREATE TABLE `t_card_language_type` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `leader_id` bigint(20) DEFAULT NULL,
  `member_id` bigint(20) DEFAULT NULL,
  `card_type` int(11) DEFAULT NULL,
  `card_num` varchar(32) DEFAULT NULL,
  `card_valid_time` date DEFAULT NULL,
  `language_type` int(11) DEFAULT NULL,
  `language` varchar(32) DEFAULT NULL,
  `proficiency` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='证件类型和语言类型';

-- ----------------------------
-- Table structure for t_event_record
-- ----------------------------
DROP TABLE IF EXISTS `t_event_record`;
CREATE TABLE `t_event_record` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `team_id` varchar(32) DEFAULT NULL COMMENT '腾迅groupid',
  `leader_id` bigint(20) DEFAULT NULL COMMENT '领队id',
  `voice_url` varchar(256) DEFAULT NULL COMMENT '语音地址',
  `video_len` varchar(10) DEFAULT NULL COMMENT '语音时长',
  `content` varchar(256) DEFAULT NULL COMMENT '文本内容',
  `record_time` varchar(32) DEFAULT NULL COMMENT '记录时间',
  `lat` double DEFAULT NULL COMMENT '事件纬度',
  `lon` double DEFAULT NULL COMMENT '事件经度',
  `location` varchar(64) DEFAULT NULL COMMENT '事件位置',
  `thumbnail_url` text COMMENT '缩略图地址',
  `photos_url` text COMMENT '图片地址',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人(用户id)',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `VERSION` bigint(20) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=232 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_group
-- ----------------------------
DROP TABLE IF EXISTS `t_group`;
CREATE TABLE `t_group` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `t_id` bigint(20) DEFAULT NULL COMMENT '团队ID',
  `team_id` varchar(32) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `photo_url` varchar(256) DEFAULT NULL COMMENT '头像地址',
  `total_count` int(11) DEFAULT '0' COMMENT '总人数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8mb4 COMMENT='小组表（t_group）';

-- ----------------------------
-- Table structure for t_hotel_agency
-- ----------------------------
DROP TABLE IF EXISTS `t_hotel_agency`;
CREATE TABLE `t_hotel_agency` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `travel_id` bigint(20) DEFAULT NULL COMMENT '旅行社id',
  `hotel_name` varchar(255) DEFAULT NULL COMMENT '酒店中文名称',
  `delta` varchar(100) DEFAULT NULL COMMENT '大洲',
  `country` varchar(100) DEFAULT NULL COMMENT '国家',
  `city` varchar(100) DEFAULT NULL COMMENT '城市',
  `city_id` varchar(255) DEFAULT NULL COMMENT '城市id',
  `star_lv` varchar(100) DEFAULT NULL COMMENT '酒店星级',
  `briefintroduction` text COMMENT '简介',
  `lon` varchar(100) DEFAULT NULL COMMENT '经度',
  `lat` varchar(100) DEFAULT NULL COMMENT '纬度',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` varchar(255) DEFAULT NULL COMMENT '删除时此字段更新为deleted',
  `hotel_id` varchar(255) DEFAULT NULL COMMENT '共有酒店库d_hotel的hotel_id',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_leader
-- ----------------------------
DROP TABLE IF EXISTS `t_leader`;
CREATE TABLE `t_leader` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `travel_id` bigint(20) DEFAULT NULL COMMENT '旅行社ID',
  `name` varchar(32) NOT NULL COMMENT '姓名',
  `sex` int(11) NOT NULL COMMENT '性别【1：男性；0：女性】',
  `leader_type` int(11) DEFAULT NULL COMMENT '类别[1:领队；2:导游；3:导游兼领队]',
  `leader_status` int(11) DEFAULT NULL COMMENT '状态「1:专职；2兼职；3:离职」',
  `photo_url` varchar(256) DEFAULT NULL COMMENT '头像地址',
  `birthday` timestamp NULL DEFAULT NULL COMMENT '出生日期',
  `phone` varchar(32) NOT NULL COMMENT '手机号码',
  `weixin` varchar(64) DEFAULT NULL COMMENT '微信',
  `qq` varchar(32) DEFAULT NULL COMMENT 'QQ',
  `lead_time` date DEFAULT NULL COMMENT '开始带团时间',
  `destination_group1` varchar(15) DEFAULT NULL COMMENT '擅长路线：港奥台',
  `destination_group2` varchar(15) DEFAULT NULL COMMENT '擅长路线：日韩',
  `destination_group3` varchar(15) DEFAULT NULL COMMENT '擅长路线：东南亚',
  `destination_group4` varchar(15) DEFAULT NULL COMMENT '擅长路线：欧洲',
  `destination_group5` varchar(15) DEFAULT NULL COMMENT '擅长路线：美洲',
  `destination_group6` varchar(15) DEFAULT NULL COMMENT '擅长路线：澳洲',
  `destination_group7` varchar(15) DEFAULT NULL COMMENT '擅长路线：中东非洲',
  `destination_group8` varchar(15) DEFAULT NULL,
  `card_type` int(11) DEFAULT NULL COMMENT '证件类型[1:领队证；2:导游证] ',
  `card_code` varchar(30) DEFAULT NULL COMMENT '证件号',
  `validity_date` date DEFAULT NULL COMMENT '证件有效期',
  `language1` varchar(30) DEFAULT NULL COMMENT '语言种类1',
  `language1_level` int(11) DEFAULT NULL COMMENT '语言1熟练程度：1:非常熟练；2:一般；3略懂',
  `language2` varchar(30) DEFAULT NULL COMMENT '语言种类2',
  `language2_level` int(11) DEFAULT NULL COMMENT '语言2熟练程度：1:非常熟练；2:一般；3略懂',
  `language3` varchar(30) DEFAULT NULL COMMENT '语言种类3',
  `language3_level` int(11) DEFAULT NULL COMMENT '语言3熟练程度：1:非常熟练；2:一般；3略懂',
  `photo_library2_url` varchar(256) DEFAULT NULL COMMENT '照片库2',
  `photo_library3_url` varchar(256) DEFAULT NULL COMMENT '照片库3',
  `photo_library1_url` varchar(256) DEFAULT NULL COMMENT '照片库1',
  `photo_library4_url` varchar(256) DEFAULT NULL COMMENT '照片库4',
  `photo_library5_url` varchar(256) DEFAULT NULL COMMENT '照片库5',
  `photo_library6_url` varchar(256) DEFAULT NULL COMMENT '照片库6',
  `photo_library7_url` varchar(256) DEFAULT NULL COMMENT '照片库7',
  `photo_library8_url` varchar(256) DEFAULT NULL COMMENT '照片库8',
  `photo_library9_url` varchar(256) DEFAULT NULL COMMENT '照片库9',
  `photo_library10_url` varchar(256) DEFAULT NULL COMMENT '照片库10',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `evaluationTag` varchar(256) DEFAULT NULL COMMENT '自我评价标签  如果有多个标签，则需要用英文逗号“,”分隔开 ',
  `languages` varchar(256) DEFAULT NULL COMMENT '擅长语种',
  `tourRoutes` varchar(256) DEFAULT NULL COMMENT '擅长旅游线路',
  `version` bigint(20) DEFAULT '0' COMMENT '版本号',
  `country_code` varchar(255) DEFAULT NULL COMMENT '国家代码',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10216 DEFAULT CHARSET=utf8mb4 COMMENT='领队表（t_leader）';

-- ----------------------------
-- Table structure for t_member
-- ----------------------------
DROP TABLE IF EXISTS `t_member`;
CREATE TABLE `t_member` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `t_id` bigint(20) DEFAULT NULL COMMENT '团队ID',
  `team_id` varchar(32) DEFAULT NULL COMMENT '腾讯组ID',
  `group_id` bigint(20) DEFAULT NULL COMMENT '小组ID',
  `real_name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `sex` int(11) NOT NULL DEFAULT '1' COMMENT '性别【1：男性；0：女性】',
  `birthday` varchar(64) DEFAULT NULL COMMENT '出生日期',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号码',
  `pro_code` int(5) DEFAULT NULL,
  `pro_name` varchar(50) DEFAULT NULL,
  `mark_phone` varchar(32) DEFAULT NULL COMMENT '备用手机号',
  `photo_url` varchar(256) DEFAULT NULL COMMENT '头像地址',
  `mail` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `address` varchar(256) DEFAULT NULL COMMENT '住址',
  `city` varchar(32) DEFAULT NULL COMMENT '城市',
  `province` varchar(32) DEFAULT NULL COMMENT '省份',
  `country` varchar(32) DEFAULT NULL COMMENT '国家',
  `role` int(11) DEFAULT NULL COMMENT '身份【1：领队；2：导游；3：游客】',
  `type` int(11) DEFAULT '1' COMMENT '类型【1：成人；2：儿童；3：老人】',
  `is_leader` int(11) DEFAULT '0' COMMENT '是否为小组长【1：是；0：否】',
  `is_admin` int(11) DEFAULT '0' COMMENT '是否为管理员【1：是；0：否】',
  `join_status` int(11) DEFAULT '0' COMMENT '加入状态【0：未加入；1：已加入】',
  `card_type` varchar(32) DEFAULT NULL COMMENT '证件类型',
  `card_num` varchar(32) DEFAULT NULL COMMENT '证件号码',
  `card_time` varchar(32) DEFAULT NULL COMMENT '证件有效日期',
  `weixin` varchar(64) DEFAULT NULL COMMENT '微信',
  `qq` varchar(32) DEFAULT NULL COMMENT 'qq',
  `photo_library1_url` varchar(256) DEFAULT NULL COMMENT '图片库1',
  `photo_library2_url` varchar(256) DEFAULT NULL COMMENT '图片库2',
  `photo_library3_url` varchar(256) DEFAULT NULL COMMENT '图片库3',
  `photo_library4_url` varchar(256) DEFAULT NULL COMMENT '图片库4',
  `photo_library5_url` varchar(256) DEFAULT NULL COMMENT '图片库5',
  `photo_library6_url` varchar(256) DEFAULT NULL COMMENT '图片库6',
  `photo_library7_url` varchar(256) DEFAULT NULL COMMENT '图片库7',
  `photo_library8_url` varchar(256) DEFAULT NULL COMMENT '图片库8',
  `photo_library9_url` varchar(256) DEFAULT NULL COMMENT '图片库9',
  `photo_library10_url` varchar(256) DEFAULT NULL COMMENT '图片库10',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  `country_code` varchar(255) DEFAULT NULL COMMENT '国家代号',
  `is_new_join` int(11) DEFAULT '0' COMMENT '是否为新加入（即为自己通过扫描填写资料加入）【0：否；1：是】',
  `unionid` varchar(256) DEFAULT NULL COMMENT '微信唯一标识',
  PRIMARY KEY (`ID`),
  KEY `index_tid` (`t_id`),
  KEY `index_userid` (`user_id`),
  KEY `index_teamid` (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9204 DEFAULT CHARSET=utf8mb4 COMMENT='成员表（t_member）';

-- ----------------------------
-- Table structure for t_onlookers
-- ----------------------------
DROP TABLE IF EXISTS `t_onlookers`;
CREATE TABLE `t_onlookers` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `schedule_id` bigint(20) DEFAULT NULL COMMENT '行程id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `is_system_info` int(11) DEFAULT NULL COMMENT '是否是系统信息1:是，0:否',
  `content` varchar(256) DEFAULT NULL COMMENT '发送文本内容',
  `thumbnail_url` text COMMENT '缩略图',
  `photos_url` text COMMENT '发表图片的路径',
  `send_time` timestamp NULL DEFAULT NULL COMMENT '发表时间',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `version` bigint(20) DEFAULT NULL COMMENT '版本',
  `wide` varchar(32) DEFAULT '' COMMENT '图片宽',
  `height` varchar(32) DEFAULT '' COMMENT '图片高',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=255 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_onlookers_details
-- ----------------------------
DROP TABLE IF EXISTS `t_onlookers_details`;
CREATE TABLE `t_onlookers_details` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `onlookers_id` bigint(20) DEFAULT NULL COMMENT '围观表id',
  `reply_id` bigint(20) DEFAULT NULL COMMENT '回复用户（围观别人的）',
  `by_reply_id` bigint(20) DEFAULT NULL COMMENT '被回复用户(他是被围观的)',
  `content` varchar(256) DEFAULT NULL COMMENT '回复内容',
  `is_like` int(11) DEFAULT NULL COMMENT '点赞1:是，0:否',
  `is_look` int(11) DEFAULT NULL COMMENT '查看1:是，0否',
  `father_id` bigint(20) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `version` bigint(20) DEFAULT NULL COMMENT '版本',
  `schedule_id` bigint(20) DEFAULT NULL COMMENT '行程ID',
  `img_url` text COMMENT ' 图片地址，多张以分号分割',
  `type` int(11) DEFAULT NULL COMMENT '1为点赞，2为回复,3评论',
  `reply_name` varchar(100) DEFAULT NULL COMMENT '回复用户  昵称',
  `by_reply_name` varchar(100) DEFAULT NULL COMMENT '被回复用户  昵称',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=380 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_onlookers_user
-- ----------------------------
DROP TABLE IF EXISTS `t_onlookers_user`;
CREATE TABLE `t_onlookers_user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '被围观用户id',
  `onlooker_user_id` bigint(20) DEFAULT NULL COMMENT '围观用户id',
  `onlookers_status` int(11) DEFAULT NULL COMMENT '围观申请状态,1:已申请，2:已确认',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `version` bigint(20) DEFAULT NULL COMMENT '版本',
  `schedule_id` bigint(20) DEFAULT NULL COMMENT '行程ID',
  `sex` int(11) DEFAULT NULL COMMENT '性别1男；2女',
  `name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_own_expense_agency
-- ----------------------------
DROP TABLE IF EXISTS `t_own_expense_agency`;
CREATE TABLE `t_own_expense_agency` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `travel_id` bigint(20) DEFAULT NULL COMMENT '旅行社id',
  `item_name` varchar(100) DEFAULT NULL COMMENT '自费项目名',
  `limit_number` varchar(255) DEFAULT NULL COMMENT '人数限制',
  `service_item` varchar(255) DEFAULT NULL COMMENT '服务项目名',
  `reference_price` varchar(255) DEFAULT NULL COMMENT '参考价格',
  `briefintroduction` text COMMENT '简介',
  `item_city` varchar(100) DEFAULT NULL COMMENT '项目城市',
  `city_id` varchar(255) DEFAULT NULL COMMENT '城市id',
  `lon` varchar(100) DEFAULT NULL COMMENT '经度',
  `lat` varchar(100) DEFAULT NULL COMMENT '纬度',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` varchar(255) DEFAULT NULL COMMENT '删除时此字段更新为deleted',
  `type` varchar(255) DEFAULT NULL COMMENT '索引字段',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_scenic_agency
-- ----------------------------
DROP TABLE IF EXISTS `t_scenic_agency`;
CREATE TABLE `t_scenic_agency` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `travel_id` bigint(20) DEFAULT NULL COMMENT '旅行社id',
  `scenic_name` varchar(255) DEFAULT NULL COMMENT '景点名称',
  `scenic_en_name` varchar(255) DEFAULT NULL COMMENT '景点英文名',
  `city_id` varchar(255) DEFAULT NULL COMMENT '城市的id',
  `scenic_city` varchar(100) DEFAULT NULL COMMENT '景点城市',
  `briefintroduction` text COMMENT '简介',
  `lon` varchar(100) DEFAULT NULL COMMENT '经度',
  `lat` varchar(100) DEFAULT NULL COMMENT '纬度',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` varchar(255) DEFAULT '1' COMMENT '删除的字段更新为deleted',
  `mafeng_id` varchar(255) DEFAULT NULL COMMENT '公有景点库d_spot的mafeng_id',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_shopping_agency
-- ----------------------------
DROP TABLE IF EXISTS `t_shopping_agency`;
CREATE TABLE `t_shopping_agency` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `travel_id` bigint(20) DEFAULT NULL COMMENT '旅行社id',
  `shopping_name` varchar(255) DEFAULT NULL COMMENT '购物场所名称',
  `shopping_city` varchar(100) DEFAULT NULL COMMENT '城市名称',
  `city_id` varchar(255) DEFAULT NULL COMMENT '城市id',
  `sell_products` varchar(255) DEFAULT NULL COMMENT '售卖产品',
  `residence_time` varchar(255) DEFAULT NULL COMMENT '停留时间',
  `lon` varchar(100) DEFAULT NULL COMMENT '经度',
  `lat` varchar(100) DEFAULT NULL COMMENT '纬度',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` varchar(255) DEFAULT NULL COMMENT '删除时此字段更新为deleted',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_team
-- ----------------------------
DROP TABLE IF EXISTS `t_team`;
CREATE TABLE `t_team` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `travel_id` bigint(20) DEFAULT NULL COMMENT '旅行社ID',
  `team_num` varchar(32) DEFAULT NULL COMMENT '团号',
  `tx_groupId` varchar(32) DEFAULT NULL COMMENT '腾讯云GroupId',
  `name` varchar(64) DEFAULT NULL COMMENT '团队名称',
  `intro` varchar(256) DEFAULT NULL COMMENT '团队简介',
  `photo_url` varchar(256) DEFAULT NULL COMMENT '头像地址',
  `status` int(11) DEFAULT '2' COMMENT '出行状态【1：出行中；2：即将出行；3：已出行】',
  `total_count` int(11) DEFAULT '0' COMMENT '总人数',
  `header_name` varchar(32) DEFAULT NULL COMMENT '领队姓名',
  `header_phone` varchar(32) DEFAULT NULL COMMENT '领队电话',
  `domestic_contact` varchar(32) DEFAULT NULL COMMENT '国内紧急联系人',
  `domestic_phone` varchar(32) DEFAULT NULL COMMENT '国内紧急联系人手机',
  `foreign_contact` varchar(32) DEFAULT NULL COMMENT '国外紧急联系人',
  `foreign_phone` varchar(32) DEFAULT NULL COMMENT '国外紧急联系人手机',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  `start_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `end_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `qr_code` varchar(512) DEFAULT NULL COMMENT '微信公众号二维码带团ID参数URL',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12469 DEFAULT CHARSET=utf8mb4 COMMENT='团队表（t_team）';

-- ----------------------------
-- Table structure for t_team_album
-- ----------------------------
DROP TABLE IF EXISTS `t_team_album`;
CREATE TABLE `t_team_album` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `team_id` varchar(64) DEFAULT NULL COMMENT '团队id（腾迅云groupId）',
  `photo_url` varchar(256) DEFAULT NULL COMMENT '原图地址',
  `thumbnail_url` varchar(256) DEFAULT NULL COMMENT '缩略图地址',
  `author` varchar(64) DEFAULT NULL COMMENT '发图人',
  `location` varchar(64) DEFAULT NULL COMMENT '发图人所在地',
  `lat` varchar(64) DEFAULT NULL,
  `lng` varchar(64) DEFAULT NULL,
  `photograph_time` timestamp NULL DEFAULT NULL COMMENT '照片日期',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `im_photo_url` varchar(256) DEFAULT NULL COMMENT 'im原图URL',
  `im_large_url` varchar(256) DEFAULT NULL COMMENT 'im大图URL',
  `im_thumbnail_url` varchar(256) DEFAULT NULL COMMENT 'im缩略图url',
  `type` int(11) NOT NULL DEFAULT '1' COMMENT '1 可见 2 隐藏',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_travel_agency
-- ----------------------------
DROP TABLE IF EXISTS `t_travel_agency`;
CREATE TABLE `t_travel_agency` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `name` varchar(64) NOT NULL COMMENT '旅行社名称',
  `contact_person` varchar(64) DEFAULT NULL COMMENT '联系人姓名',
  `contact_phone` varchar(32) NOT NULL COMMENT '联系人手机',
  `email` varchar(128) DEFAULT NULL COMMENT 'email',
  `address` varchar(64) DEFAULT NULL COMMENT '旅行社地址',
  `desc` text COMMENT '旅行社简介',
  `license_url` varchar(256) DEFAULT NULL COMMENT '营业执照地址',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COMMENT='旅行社表（t_travel_agency）';

-- ----------------------------
-- Table structure for t_waiting
-- ----------------------------
DROP TABLE IF EXISTS `t_waiting`;
CREATE TABLE `t_waiting` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `leisure_date` text COMMENT '空闲日期',
  `work_date` text COMMENT '忙碌日期',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `version` bigint(20) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_wx_team
-- ----------------------------
DROP TABLE IF EXISTS `t_wx_team`;
CREATE TABLE `t_wx_team` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '系统ID',
  `teamId` bigint(20) DEFAULT NULL COMMENT '团队表的id',
  `unionid` varchar(256) DEFAULT NULL COMMENT '微信的唯一标识',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COMMENT='微信团队信息关联表';

-- ----------------------------
-- Function structure for Distance1
-- ----------------------------
DROP FUNCTION IF EXISTS `Distance1`;
DELIMITER ;;
CREATE DEFINER=`letsgo`@`%` FUNCTION `Distance1`(lat1 DOUBLE,lng1 DOUBLE,lat2 DOUBLE,lng2 DOUBLE) RETURNS double
BEGIN  
DECLARE x DOUBLE ; 

SET x= round(6378.138*2*asin(sqrt(pow(sin( (lat1*pi()/180-lat2*pi()/180)/2),2)+cos(lat1*pi()/180)*cos(lat2*pi()/180)* pow(sin( (lng1*pi()/180-lng2*pi()/180)/2),2)))*1000);

RETURN x;  

END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for fristPinyin
-- ----------------------------
DROP FUNCTION IF EXISTS `fristPinyin`;
DELIMITER ;;
CREATE DEFINER=`letsgo`@`%` FUNCTION `fristPinyin`(P_NAME VARCHAR(255)) RETURNS varchar(255) CHARSET utf8
BEGIN
    DECLARE V_RETURN VARCHAR(255);
    SET V_RETURN = ELT(INTERVAL(CONV(HEX(left(CONVERT(P_NAME USING gbk),1)),16,10), 
        0xB0A1,0xB0C5,0xB2C1,0xB4EE,0xB6EA,0xB7A2,0xB8C1,0xB9FE,0xBBF7, 
        0xBFA6,0xC0AC,0xC2E8,0xC4C3,0xC5B6,0xC5BE,0xC6DA,0xC8BB,
        0xC8F6,0xCBFA,0xCDDA,0xCEF4,0xD1B9,0xD4D1),    
    'A','B','C','D','E','F','G','H','J','K','L','M','N','O','P','Q','R','S','T','W','X','Y','Z');
    RETURN V_RETURN;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for pinyin
-- ----------------------------
DROP FUNCTION IF EXISTS `pinyin`;
DELIMITER ;;
CREATE DEFINER=`letsgo`@`%` FUNCTION `pinyin`(P_NAME VARCHAR(255)) RETURNS varchar(255) CHARSET utf8
BEGIN
    DECLARE V_COMPARE VARCHAR(255);
    DECLARE V_RETURN VARCHAR(255);
    DECLARE I INT;
    SET I = 1;
    SET V_RETURN = '';
    while I < LENGTH(P_NAME) do
        SET V_COMPARE = SUBSTR(P_NAME, I, 1);
        IF (V_COMPARE != '') THEN
            #SET V_RETURN = CONCAT(V_RETURN, ',', V_COMPARE);
            SET V_RETURN = CONCAT(V_RETURN, fristPinyin(V_COMPARE));
            #SET V_RETURN = fristPinyin(V_COMPARE);
        END IF;
        SET I = I + 1;
    end while;
    IF (ISNULL(V_RETURN) or V_RETURN = '') THEN
        SET V_RETURN = P_NAME;
    END IF;
    RETURN V_RETURN;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for queryDistance
-- ----------------------------
DROP FUNCTION IF EXISTS `queryDistance`;
DELIMITER ;;
CREATE DEFINER=`letsgo`@`%` FUNCTION `queryDistance`(
lon1 DOUBLE,
lat1 DOUBLE,
lon2 DOUBLE,
lat2 DOUBLE
) RETURNS double
RETURN 6371 * acos(
cos(radians(lat1)) * cos(radians(lat2)) * cos(
radians(lon2) - radians(lon1)
) + sin(radians(lat1)) * sin(radians(lat2))
)
;;
DELIMITER ;

-- ----------------------------
-- Function structure for to_pinyin
-- ----------------------------
DROP FUNCTION IF EXISTS `to_pinyin`;
DELIMITER ;;
CREATE DEFINER=`letsgo`@`%` FUNCTION `to_pinyin`(NAME VARCHAR(255) CHARSET gbk) RETURNS varchar(255) CHARSET gbk
BEGIN
    DECLARE mycode INT;
    DECLARE tmp_lcode VARCHAR(2) CHARSET gbk;
    DECLARE lcode INT;
    
    DECLARE tmp_rcode VARCHAR(2) CHARSET gbk;
    DECLARE rcode INT;
    
    DECLARE mypy VARCHAR(255) CHARSET gbk DEFAULT '';
    DECLARE lp INT;
    
    SET mycode = 0;
    SET lp = 1;
    
    SET NAME = HEX(NAME);
    
    WHILE lp < LENGTH(NAME) DO
        
        SET tmp_lcode = SUBSTRING(NAME, lp, 2);
        SET lcode = CAST(ASCII(UNHEX(tmp_lcode)) AS UNSIGNED); 
        SET tmp_rcode = SUBSTRING(NAME, lp + 2, 2);
        SET rcode = CAST(ASCII(UNHEX(tmp_rcode)) AS UNSIGNED); 
        IF lcode > 128 THEN
            SET mycode =65536 - lcode * 256 - rcode ;
            SELECT CONCAT(mypy,pin_yin_) INTO mypy FROM t_base_pinyin WHERE CODE_ >= ABS(mycode) ORDER BY CODE_ ASC LIMIT 1;
            SET lp = lp + 4;
        ELSE
            SET mypy = CONCAT(mypy,CHAR(CAST(ASCII(UNHEX(SUBSTRING(NAME, lp, 2))) AS UNSIGNED)));
            SET lp = lp + 2;
        END IF;
    END WHILE;
    RETURN LOWER(mypy);
END
;;
DELIMITER ;
