/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : eta4

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 08/07/2018 14:59:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_url
-- ----------------------------
DROP TABLE IF EXISTS `sys_url`;
CREATE TABLE `sys_url`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_url
-- ----------------------------
INSERT INTO `sys_url` VALUES (1, '/eta4/');
INSERT INTO `sys_url` VALUES (2, '/eta4/home');
INSERT INTO `sys_url` VALUES (3, '/eta4/upload');
INSERT INTO `sys_url` VALUES (4, '/eta4/mine');
INSERT INTO `sys_url` VALUES (5, '/eta4/review');
INSERT INTO `sys_url` VALUES (6, '/eta4/query-stu');
INSERT INTO `sys_url` VALUES (7, '/eta4/query-tea');
INSERT INTO `sys_url` VALUES (8, '/eta4/award');
INSERT INTO `sys_url` VALUES (9, '/eta4/award-add');
INSERT INTO `sys_url` VALUES (10, '/eta4/student-management');
INSERT INTO `sys_url` VALUES (11, '/eta4/teacher-management');
INSERT INTO `sys_url` VALUES (12, '/eta4/import-award');
INSERT INTO `sys_url` VALUES (13, '/eta4/import-stu');
INSERT INTO `sys_url` VALUES (14, '/eta4/import-tea');
INSERT INTO `sys_url` VALUES (15, '/eta4/password');
INSERT INTO `sys_url` VALUES (16, '/eta4/logout');
INSERT INTO `sys_url` VALUES (17, '/eta4/detail-stu');
INSERT INTO `sys_url` VALUES (18, '/eta4/detail-tea');

-- ----------------------------
-- Table structure for sys_url_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_url_role`;
CREATE TABLE `sys_url_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `urlId` int(11) NOT NULL COMMENT 'urlId',
  `roleId` int(11) NOT NULL COMMENT '角色Id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_sys_url_role_sys_url_1`(`urlId`) USING BTREE,
  INDEX `fk_sys_url_role_t_role_1`(`roleId`) USING BTREE,
  CONSTRAINT `fk_sys_url_role_sys_url_1` FOREIGN KEY (`urlId`) REFERENCES `sys_url` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_sys_url_role_t_role_1` FOREIGN KEY (`roleId`) REFERENCES `t_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'url角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_url_role
-- ----------------------------
INSERT INTO `sys_url_role` VALUES (1, 2, 5);
INSERT INTO `sys_url_role` VALUES (2, 3, 5);
INSERT INTO `sys_url_role` VALUES (3, 4, 5);
INSERT INTO `sys_url_role` VALUES (4, 15, 5);
INSERT INTO `sys_url_role` VALUES (5, 16, 5);
INSERT INTO `sys_url_role` VALUES (6, 17, 5);
INSERT INTO `sys_url_role` VALUES (7, 2, 4);
INSERT INTO `sys_url_role` VALUES (8, 3, 4);
INSERT INTO `sys_url_role` VALUES (9, 4, 4);
INSERT INTO `sys_url_role` VALUES (10, 6, 4);
INSERT INTO `sys_url_role` VALUES (11, 15, 4);
INSERT INTO `sys_url_role` VALUES (12, 16, 4);
INSERT INTO `sys_url_role` VALUES (13, 17, 4);
INSERT INTO `sys_url_role` VALUES (14, 18, 4);
INSERT INTO `sys_url_role` VALUES (15, 2, 3);
INSERT INTO `sys_url_role` VALUES (16, 3, 3);
INSERT INTO `sys_url_role` VALUES (17, 4, 3);
INSERT INTO `sys_url_role` VALUES (18, 5, 3);
INSERT INTO `sys_url_role` VALUES (19, 6, 3);
INSERT INTO `sys_url_role` VALUES (20, 15, 3);
INSERT INTO `sys_url_role` VALUES (21, 16, 3);
INSERT INTO `sys_url_role` VALUES (22, 17, 3);
INSERT INTO `sys_url_role` VALUES (23, 18, 3);
INSERT INTO `sys_url_role` VALUES (24, 2, 2);
INSERT INTO `sys_url_role` VALUES (25, 3, 2);
INSERT INTO `sys_url_role` VALUES (26, 4, 2);
INSERT INTO `sys_url_role` VALUES (27, 6, 2);
INSERT INTO `sys_url_role` VALUES (28, 7, 2);
INSERT INTO `sys_url_role` VALUES (29, 15, 2);
INSERT INTO `sys_url_role` VALUES (30, 16, 2);
INSERT INTO `sys_url_role` VALUES (31, 17, 2);
INSERT INTO `sys_url_role` VALUES (32, 18, 2);
INSERT INTO `sys_url_role` VALUES (33, 2, 1);
INSERT INTO `sys_url_role` VALUES (34, 3, 1);
INSERT INTO `sys_url_role` VALUES (35, 4, 1);
INSERT INTO `sys_url_role` VALUES (36, 5, 1);
INSERT INTO `sys_url_role` VALUES (37, 6, 1);
INSERT INTO `sys_url_role` VALUES (38, 7, 1);
INSERT INTO `sys_url_role` VALUES (39, 8, 1);
INSERT INTO `sys_url_role` VALUES (40, 9, 1);
INSERT INTO `sys_url_role` VALUES (41, 10, 1);
INSERT INTO `sys_url_role` VALUES (42, 11, 1);
INSERT INTO `sys_url_role` VALUES (43, 12, 1);
INSERT INTO `sys_url_role` VALUES (44, 13, 1);
INSERT INTO `sys_url_role` VALUES (45, 14, 1);
INSERT INTO `sys_url_role` VALUES (46, 15, 1);
INSERT INTO `sys_url_role` VALUES (47, 16, 1);
INSERT INTO `sys_url_role` VALUES (48, 17, 1);
INSERT INTO `sys_url_role` VALUES (49, 18, 1);
INSERT INTO `sys_url_role` VALUES (50, 1, 5);
INSERT INTO `sys_url_role` VALUES (51, 1, 4);
INSERT INTO `sys_url_role` VALUES (52, 1, 3);
INSERT INTO `sys_url_role` VALUES (53, 1, 2);
INSERT INTO `sys_url_role` VALUES (54, 1, 1);

-- ----------------------------
-- Table structure for t_award
-- ----------------------------
DROP TABLE IF EXISTS `t_award`;
CREATE TABLE `t_award`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `awardName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '奖项名称',
  `awardTypeId` int(11) NOT NULL COMMENT '奖项类型',
  `createAt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateAt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_t_award_t_award_type_1`(`awardTypeId`) USING BTREE,
  CONSTRAINT `fk_t_award_t_award_type_1` FOREIGN KEY (`awardTypeId`) REFERENCES `t_award_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '奖项表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_award
-- ----------------------------
INSERT INTO `t_award` VALUES (21, '江苏省高校大学生物理及实验科技作品创新竞赛', 1, '2018-05-04 21:21:39', '2018-05-19 12:10:27');
INSERT INTO `t_award` VALUES (22, '全国大学生电子设计竞赛', 1, '2018-05-04 21:21:39', '2018-05-19 12:10:29');
INSERT INTO `t_award` VALUES (23, '全国大学生“飞思卡尔”杯智能汽车竞赛', 1, '2018-05-04 21:21:39', '2018-05-19 12:10:30');
INSERT INTO `t_award` VALUES (25, '全国大学生“用友”杯创业设计大赛', 1, '2018-05-04 21:21:39', '2018-05-19 12:10:32');
INSERT INTO `t_award` VALUES (26, '全国大学生数学建模竞赛', 1, '2018-05-04 21:21:39', '2018-05-19 12:10:33');
INSERT INTO `t_award` VALUES (27, '全国大学生基础力学实验竞赛', 1, '2018-05-04 21:21:39', '2018-05-19 12:10:35');
INSERT INTO `t_award` VALUES (28, '全国软件专业人才设计与开发大赛', 1, '2018-05-04 21:21:39', '2018-05-19 12:10:36');
INSERT INTO `t_award` VALUES (30, '全国大学生电子商务“创新、创意及创业”挑战赛', 1, '2018-05-04 21:21:39', '2018-05-19 12:10:37');
INSERT INTO `t_award` VALUES (32, '江苏省大学生职业规划大赛', 1, '2018-05-04 21:21:39', '2018-05-19 12:10:39');
INSERT INTO `t_award` VALUES (33, '“Omron杯”Sysmac自动化控制应用设计大赛', 1, '2018-05-04 21:21:39', '2018-05-19 12:10:40');
INSERT INTO `t_award` VALUES (34, '江苏省大学生电子设计竞赛', 1, '2018-05-04 21:21:39', '2018-05-19 12:10:41');
INSERT INTO `t_award` VALUES (35, '“学创杯”全国大学生创业综合模拟大赛', 1, '2018-05-04 21:21:39', '2018-05-19 12:10:42');
INSERT INTO `t_award` VALUES (36, '“蓝桥杯”全国软件和信息技术专业人才大赛', 1, '2018-05-04 21:21:39', '2018-05-19 12:10:58');

-- ----------------------------
-- Table structure for t_award_type
-- ----------------------------
DROP TABLE IF EXISTS `t_award_type`;
CREATE TABLE `t_award_type`  (
  `id` int(11) NOT NULL,
  `typeName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '奖项类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_award_type
-- ----------------------------
INSERT INTO `t_award_type` VALUES (1, '学生奖项');
INSERT INTO `t_award_type` VALUES (2, '教师奖项');

-- ----------------------------
-- Table structure for t_class
-- ----------------------------
DROP TABLE IF EXISTS `t_class`;
CREATE TABLE `t_class`  (
  `id` int(11) NOT NULL COMMENT 'id',
  `className` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '班级名称',
  `gradeId` int(11) NOT NULL COMMENT '年级id',
  `majorId` int(11) NOT NULL COMMENT '专业id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_t_class_t_grade_1`(`gradeId`) USING BTREE,
  INDEX `fk_t_class_t_major_1`(`majorId`) USING BTREE,
  CONSTRAINT `fk_t_class_t_grade_1` FOREIGN KEY (`gradeId`) REFERENCES `t_grade` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_t_class_t_major_1` FOREIGN KEY (`majorId`) REFERENCES `t_major` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '班级表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_class
-- ----------------------------
INSERT INTO `t_class` VALUES (382151, '15软件工程1班', 2015, 105);
INSERT INTO `t_class` VALUES (382152, '15软件工程2班', 2015, 105);

-- ----------------------------
-- Table structure for t_gender
-- ----------------------------
DROP TABLE IF EXISTS `t_gender`;
CREATE TABLE `t_gender`  (
  `id` int(11) NOT NULL COMMENT 'id',
  `genderName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '性别名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '性别表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_gender
-- ----------------------------
INSERT INTO `t_gender` VALUES (1, '男');
INSERT INTO `t_gender` VALUES (2, '女');

-- ----------------------------
-- Table structure for t_grade
-- ----------------------------
DROP TABLE IF EXISTS `t_grade`;
CREATE TABLE `t_grade`  (
  `id` int(11) NOT NULL COMMENT 'id',
  `gradeName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '年级名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '年级表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_grade
-- ----------------------------
INSERT INTO `t_grade` VALUES (2010, '2010');
INSERT INTO `t_grade` VALUES (2011, '2011');
INSERT INTO `t_grade` VALUES (2012, '2012');
INSERT INTO `t_grade` VALUES (2013, '2013');
INSERT INTO `t_grade` VALUES (2014, '2014');
INSERT INTO `t_grade` VALUES (2015, '2015');
INSERT INTO `t_grade` VALUES (2016, '2016');
INSERT INTO `t_grade` VALUES (2017, '2017');
INSERT INTO `t_grade` VALUES (2018, '2018');
INSERT INTO `t_grade` VALUES (2019, '2019');
INSERT INTO `t_grade` VALUES (2020, '2020');
INSERT INTO `t_grade` VALUES (2021, '2021');
INSERT INTO `t_grade` VALUES (2022, '2022');
INSERT INTO `t_grade` VALUES (2023, '2023');
INSERT INTO `t_grade` VALUES (2024, '2024');
INSERT INTO `t_grade` VALUES (2025, '2025');
INSERT INTO `t_grade` VALUES (2026, '2026');
INSERT INTO `t_grade` VALUES (2027, '2027');
INSERT INTO `t_grade` VALUES (2028, '2028');
INSERT INTO `t_grade` VALUES (2029, '2029');
INSERT INTO `t_grade` VALUES (2030, '2030');

-- ----------------------------
-- Table structure for t_major
-- ----------------------------
DROP TABLE IF EXISTS `t_major`;
CREATE TABLE `t_major`  (
  `id` int(11) NOT NULL COMMENT 'id',
  `majorName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '专业名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '专业表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_major
-- ----------------------------
INSERT INTO `t_major` VALUES (101, '电子信息工程');
INSERT INTO `t_major` VALUES (102, '电子科学与技术');
INSERT INTO `t_major` VALUES (103, '自动化');
INSERT INTO `t_major` VALUES (104, '计算机科学与技术');
INSERT INTO `t_major` VALUES (105, '软件工程');

-- ----------------------------
-- Table structure for t_rank
-- ----------------------------
DROP TABLE IF EXISTS `t_rank`;
CREATE TABLE `t_rank`  (
  `id` int(11) NOT NULL COMMENT 'id',
  `rankName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '等级名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '等级表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_rank
-- ----------------------------
INSERT INTO `t_rank` VALUES (1, '国家级');
INSERT INTO `t_rank` VALUES (2, '省部级');
INSERT INTO `t_rank` VALUES (3, '市级');

-- ----------------------------
-- Table structure for t_review
-- ----------------------------
DROP TABLE IF EXISTS `t_review`;
CREATE TABLE `t_review`  (
  `id` int(11) NOT NULL COMMENT 'id',
  `reviewName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '审核状态名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '审核状态表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_review
-- ----------------------------
INSERT INTO `t_review` VALUES (1, '未审核');
INSERT INTO `t_review` VALUES (2, '已审核');
INSERT INTO `t_review` VALUES (3, '未通过');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` int(11) NOT NULL COMMENT 'id',
  `roleName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `roleNameEn` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色英文名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES (1, '系统管理员', 'admin');
INSERT INTO `t_role` VALUES (2, '院系领导', 'leader');
INSERT INTO `t_role` VALUES (3, '辅导员', 'instructor');
INSERT INTO `t_role` VALUES (4, '任课教师', 'teacher');
INSERT INTO `t_role` VALUES (5, '学生', 'student');

-- ----------------------------
-- Table structure for t_student
-- ----------------------------
DROP TABLE IF EXISTS `t_student`;
CREATE TABLE `t_student`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `stuNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学号',
  `stuName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `genderId` int(11) NOT NULL COMMENT '性别id',
  `classId` int(11) NOT NULL COMMENT '班级id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_t_student_t_class_1`(`classId`) USING BTREE,
  INDEX `fk_t_student_t_gender_1`(`genderId`) USING BTREE,
  CONSTRAINT `fk_t_student_t_class_1` FOREIGN KEY (`classId`) REFERENCES `t_class` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_t_student_t_gender_1` FOREIGN KEY (`genderId`) REFERENCES `t_gender` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学生信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_student
-- ----------------------------
INSERT INTO `t_student` VALUES (1, '38215101', '范玉玲', 2, 382151);
INSERT INTO `t_student` VALUES (2, '38215102', '关铃娜', 2, 382151);
INSERT INTO `t_student` VALUES (3, '38215103', '孙義雯', 2, 382151);
INSERT INTO `t_student` VALUES (4, '38215104', '王榕', 2, 382151);
INSERT INTO `t_student` VALUES (5, '38215105', '朱希杰', 2, 382151);
INSERT INTO `t_student` VALUES (6, '38215107', '顾晓慧', 2, 382151);
INSERT INTO `t_student` VALUES (7, '38215108', '刘重君', 2, 382151);
INSERT INTO `t_student` VALUES (8, '38215109', '殷虹晨', 2, 382151);
INSERT INTO `t_student` VALUES (9, '38215111', '许鹏程', 1, 382151);
INSERT INTO `t_student` VALUES (10, '38215112', '窦永生', 1, 382151);
INSERT INTO `t_student` VALUES (11, '38215113', '任海军', 1, 382151);
INSERT INTO `t_student` VALUES (12, '38215114', '陈凡荣', 1, 382151);
INSERT INTO `t_student` VALUES (13, '38215116', '刘磊', 1, 382151);
INSERT INTO `t_student` VALUES (14, '38215117', '林恩凯', 1, 382151);
INSERT INTO `t_student` VALUES (15, '38215118', '冉晟宇', 1, 382151);
INSERT INTO `t_student` VALUES (16, '38215119', '张朋江', 1, 382151);
INSERT INTO `t_student` VALUES (17, '38215120', '乔景凌', 1, 382151);
INSERT INTO `t_student` VALUES (18, '38215121', '张未', 1, 382151);
INSERT INTO `t_student` VALUES (19, '38215123', '俞朝武', 1, 382151);
INSERT INTO `t_student` VALUES (20, '38215124', '丁志勇', 1, 382151);
INSERT INTO `t_student` VALUES (21, '38215125', '孙朝', 1, 382151);
INSERT INTO `t_student` VALUES (22, '38215126', '董宁', 1, 382151);
INSERT INTO `t_student` VALUES (23, '38215127', '沈佳浩', 1, 382151);
INSERT INTO `t_student` VALUES (24, '38215128', '丁杰', 1, 382151);
INSERT INTO `t_student` VALUES (25, '38215129', '叶添', 1, 382151);
INSERT INTO `t_student` VALUES (26, '38215130', '蔡永飞', 1, 382151);
INSERT INTO `t_student` VALUES (27, '38215131', '王雨飞', 1, 382151);
INSERT INTO `t_student` VALUES (28, '38215132', '徐尧', 1, 382151);
INSERT INTO `t_student` VALUES (29, '38215133', '侯宏伟', 1, 382151);
INSERT INTO `t_student` VALUES (30, '38215134', '史华', 1, 382151);
INSERT INTO `t_student` VALUES (31, '38215135', '黄彦博', 1, 382151);
INSERT INTO `t_student` VALUES (32, '38215136', '沈光熠', 1, 382151);
INSERT INTO `t_student` VALUES (33, '38215137', '吴野', 1, 382151);
INSERT INTO `t_student` VALUES (34, '38215138', '巩相杨', 1, 382151);
INSERT INTO `t_student` VALUES (35, '38215139', '夏静波', 1, 382151);
INSERT INTO `t_student` VALUES (36, '38215140', '叶闯凯', 1, 382151);
INSERT INTO `t_student` VALUES (37, '38215142', '童智全', 1, 382151);
INSERT INTO `t_student` VALUES (38, '38215143', '王怀鹏', 1, 382151);
INSERT INTO `t_student` VALUES (39, '38215144', '任学松', 1, 382151);
INSERT INTO `t_student` VALUES (40, '38215145', '肖浩宇', 1, 382151);
INSERT INTO `t_student` VALUES (41, '38215147', '李冠哲', 1, 382151);

-- ----------------------------
-- Table structure for t_teacher
-- ----------------------------
DROP TABLE IF EXISTS `t_teacher`;
CREATE TABLE `t_teacher`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `teaNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '教师号',
  `teaName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `genderId` int(11) NOT NULL COMMENT '性别id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_t_teacher_t_gender_1`(`genderId`) USING BTREE,
  CONSTRAINT `fk_t_teacher_t_gender_1` FOREIGN KEY (`genderId`) REFERENCES `t_gender` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 199 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '教师信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_teacher
-- ----------------------------
INSERT INTO `t_teacher` VALUES (1, '931200189', '褚福涛', 1);
INSERT INTO `t_teacher` VALUES (2, '931200190', '吕国芳', 1);
INSERT INTO `t_teacher` VALUES (3, '931300144', '王刚', 1);
INSERT INTO `t_teacher` VALUES (4, '931300145', '刘勇', 1);
INSERT INTO `t_teacher` VALUES (5, '100000617', '丁江', 1);
INSERT INTO `t_teacher` VALUES (6, '100000621', '史先强', 1);
INSERT INTO `t_teacher` VALUES (7, '100000625', '朱国康', 1);
INSERT INTO `t_teacher` VALUES (8, '138000344', '周曼云', 2);
INSERT INTO `t_teacher` VALUES (9, '938100285', '梁衡弘', 1);
INSERT INTO `t_teacher` VALUES (10, '999100251', '吴强', 1);
INSERT INTO `t_teacher` VALUES (11, '937100265', '张志政', 1);
INSERT INTO `t_teacher` VALUES (12, '938000731', '翟玉庆', 1);
INSERT INTO `t_teacher` VALUES (13, '938100697', '张柏礼', 1);
INSERT INTO `t_teacher` VALUES (14, '999100049', '李冰', 1);
INSERT INTO `t_teacher` VALUES (15, '931200105', '包金明', 1);
INSERT INTO `t_teacher` VALUES (16, '999100182', '常昌远', 1);
INSERT INTO `t_teacher` VALUES (17, '931100376', '赵宁', 1);
INSERT INTO `t_teacher` VALUES (18, '999100244', '董志芳', 2);
INSERT INTO `t_teacher` VALUES (19, '931400020', '张锡宁', 2);
INSERT INTO `t_teacher` VALUES (20, '931300017', '张萌', 1);
INSERT INTO `t_teacher` VALUES (21, '999100235', '钟锐', 1);
INSERT INTO `t_teacher` VALUES (22, '931100397', '张士凯', 1);
INSERT INTO `t_teacher` VALUES (23, '131100310', '陆清茹', 2);
INSERT INTO `t_teacher` VALUES (24, '131800119', '朱建军', 2);
INSERT INTO `t_teacher` VALUES (25, '164000261', '朱罕非', 1);
INSERT INTO `t_teacher` VALUES (26, '164000501', '郝菁', 2);
INSERT INTO `t_teacher` VALUES (27, '938000730', '刘亚军', 2);
INSERT INTO `t_teacher` VALUES (28, '938000732', '沈军', 1);
INSERT INTO `t_teacher` VALUES (29, '938000849', '王彩玲', 2);
INSERT INTO `t_teacher` VALUES (30, '938000865', '韩敬利', 2);
INSERT INTO `t_teacher` VALUES (31, '938100233', '王晓蔚', 2);
INSERT INTO `t_teacher` VALUES (32, '938100418', '朱节中', 1);
INSERT INTO `t_teacher` VALUES (33, '938100696', '杨全胜', 1);
INSERT INTO `t_teacher` VALUES (34, '100000677', '张京中', 1);
INSERT INTO `t_teacher` VALUES (35, '100000747', '华歆', 1);
INSERT INTO `t_teacher` VALUES (36, '100000786', '吴小虎', 1);
INSERT INTO `t_teacher` VALUES (37, '100000853', '胡永东', 1);
INSERT INTO `t_teacher` VALUES (38, '100000894', '王丽娜', 2);
INSERT INTO `t_teacher` VALUES (39, '100000895', '陈蓓玉', 2);
INSERT INTO `t_teacher` VALUES (40, '100000904', '赵振南', 1);
INSERT INTO `t_teacher` VALUES (41, '100000924', '文学志', 1);
INSERT INTO `t_teacher` VALUES (42, '100000941', '黄萍', 2);
INSERT INTO `t_teacher` VALUES (43, '100000944', '桂启发', 1);
INSERT INTO `t_teacher` VALUES (44, '100000945', '李文渊', 1);
INSERT INTO `t_teacher` VALUES (45, '100000953', '杨建明', 1);
INSERT INTO `t_teacher` VALUES (46, '999100078', '孙琳', 2);
INSERT INTO `t_teacher` VALUES (47, '999100191', '冯耀霖', 1);
INSERT INTO `t_teacher` VALUES (48, '999100200', '蒋珉', 1);
INSERT INTO `t_teacher` VALUES (49, '999100236', '凌明', 1);
INSERT INTO `t_teacher` VALUES (50, '900000886', '崇志宏', 1);
INSERT INTO `t_teacher` VALUES (51, '931000773', '田磊', 2);
INSERT INTO `t_teacher` VALUES (52, '931100449', '高乙月', 2);
INSERT INTO `t_teacher` VALUES (53, '931100606', '王其', 1);
INSERT INTO `t_teacher` VALUES (54, '931000756', '冉昌艳', 2);
INSERT INTO `t_teacher` VALUES (55, '931000794', '李家强', 1);
INSERT INTO `t_teacher` VALUES (56, '931100137', '沈晨鸣', 1);
INSERT INTO `t_teacher` VALUES (57, '931100373', '李东新', 1);
INSERT INTO `t_teacher` VALUES (58, '931100422', '朱萍', 2);
INSERT INTO `t_teacher` VALUES (59, '931100447', '胡素君', 2);
INSERT INTO `t_teacher` VALUES (60, '931100607', '卢新彪', 1);
INSERT INTO `t_teacher` VALUES (61, '931100665', '陈董', 1);
INSERT INTO `t_teacher` VALUES (62, '931100698', '崔晓波', 1);
INSERT INTO `t_teacher` VALUES (63, '931200183', '汪力纯', 1);
INSERT INTO `t_teacher` VALUES (64, '931300014', '樊路嘉', 1);
INSERT INTO `t_teacher` VALUES (65, '931300015', '龚克西', 2);
INSERT INTO `t_teacher` VALUES (66, '931300200', '裴荣', 2);
INSERT INTO `t_teacher` VALUES (67, '931300201', '丁文秋', 2);
INSERT INTO `t_teacher` VALUES (68, '931400019', '杨丹', 2);
INSERT INTO `t_teacher` VALUES (69, '131100309', '陈德斌', 1);
INSERT INTO `t_teacher` VALUES (70, '131000345', '刘福章', 1);
INSERT INTO `t_teacher` VALUES (71, '131100356', '黄卉', 2);
INSERT INTO `t_teacher` VALUES (72, '131000548', '戴义保', 1);
INSERT INTO `t_teacher` VALUES (73, '138100177', '李香菊', 2);
INSERT INTO `t_teacher` VALUES (74, '900001003', '缪勇', 1);
INSERT INTO `t_teacher` VALUES (75, '900001002', '沈锦仁', 1);
INSERT INTO `t_teacher` VALUES (76, '900001011', '程峰', 1);
INSERT INTO `t_teacher` VALUES (77, '931500024', '张强', 1);
INSERT INTO `t_teacher` VALUES (78, '900001066', '吕成绪', 1);
INSERT INTO `t_teacher` VALUES (79, '900001062', '胡轶宁', 1);
INSERT INTO `t_teacher` VALUES (80, '900001061', '王征', 1);
INSERT INTO `t_teacher` VALUES (81, '900001067', '赵阳', 1);
INSERT INTO `t_teacher` VALUES (82, '900001065', '陆静', 2);
INSERT INTO `t_teacher` VALUES (83, '900001063', '汪芸', 2);
INSERT INTO `t_teacher` VALUES (84, '900001064', '李骅', 1);
INSERT INTO `t_teacher` VALUES (85, '900001022', '黄钱彬', 1);
INSERT INTO `t_teacher` VALUES (86, '900001023', '薛峰', 1);
INSERT INTO `t_teacher` VALUES (87, '900001021', '程明权', 1);
INSERT INTO `t_teacher` VALUES (88, '999100220', '王志明', 2);
INSERT INTO `t_teacher` VALUES (89, '100000695', '王伟', 1);
INSERT INTO `t_teacher` VALUES (90, '100000723', '傅骏钦', 1);
INSERT INTO `t_teacher` VALUES (91, '100000745', '王世杰', 1);
INSERT INTO `t_teacher` VALUES (92, '100000761', '时龙兴', 1);
INSERT INTO `t_teacher` VALUES (93, '100000783', '应毅', 1);
INSERT INTO `t_teacher` VALUES (94, '100000802', '方巍', 1);
INSERT INTO `t_teacher` VALUES (95, '100000823', '杨鸿儒', 1);
INSERT INTO `t_teacher` VALUES (96, '100000872', '夏思宇', 1);
INSERT INTO `t_teacher` VALUES (97, '931100664', '曹永娟', 2);
INSERT INTO `t_teacher` VALUES (98, '938000773', '祝学云', 1);
INSERT INTO `t_teacher` VALUES (99, '999100126', '袁晓辉', 1);
INSERT INTO `t_teacher` VALUES (100, '999100175', '董逸生', 1);
INSERT INTO `t_teacher` VALUES (101, '999100214', '陶军', 1);
INSERT INTO `t_teacher` VALUES (102, '999100222', '吴剑章', 1);
INSERT INTO `t_teacher` VALUES (103, '931000885', '马旭东', 1);
INSERT INTO `t_teacher` VALUES (104, '931500022', '武淑萍', 2);
INSERT INTO `t_teacher` VALUES (105, '931400197', '陈凡', 1);
INSERT INTO `t_teacher` VALUES (106, '900001049', '周平', 1);
INSERT INTO `t_teacher` VALUES (107, '900001060', '沈傲东', 1);
INSERT INTO `t_teacher` VALUES (108, '931200230', '刘久付', 1);
INSERT INTO `t_teacher` VALUES (109, '931500021', '韩范', 1);
INSERT INTO `t_teacher` VALUES (110, '131800288', '陈以藟', 2);
INSERT INTO `t_teacher` VALUES (111, '164000169', '徐玉菁', 2);
INSERT INTO `t_teacher` VALUES (112, '931200186', '吴晓梅', 2);
INSERT INTO `t_teacher` VALUES (113, '931200188', '叶彦斐', 1);
INSERT INTO `t_teacher` VALUES (114, '137000114', '弭娜', 2);
INSERT INTO `t_teacher` VALUES (115, '164000132', '余康', 2);
INSERT INTO `t_teacher` VALUES (116, '131100180', '黄丽薇', 2);
INSERT INTO `t_teacher` VALUES (117, '131100182', '郑英', 2);
INSERT INTO `t_teacher` VALUES (118, '164000212', '吴春红', 2);
INSERT INTO `t_teacher` VALUES (119, '131800218', '庄丽', 2);
INSERT INTO `t_teacher` VALUES (120, '138100265', '孙丽', 2);
INSERT INTO `t_teacher` VALUES (121, '131100308', '许立峰', 1);
INSERT INTO `t_teacher` VALUES (122, '137800397', '周文娟', 2);
INSERT INTO `t_teacher` VALUES (123, '138100093', '俞琰', 2);
INSERT INTO `t_teacher` VALUES (124, '138100372', '赵勍邶', 1);
INSERT INTO `t_teacher` VALUES (125, '138100478', '谢修娟', 2);
INSERT INTO `t_teacher` VALUES (126, '931000739', '李晨', 1);
INSERT INTO `t_teacher` VALUES (127, '931000741', '李骏扬', 1);
INSERT INTO `t_teacher` VALUES (128, '931000743', '廉明', 1);
INSERT INTO `t_teacher` VALUES (129, '931000744', '孙培勇', 1);
INSERT INTO `t_teacher` VALUES (130, '938100598', '倪巍伟', 1);
INSERT INTO `t_teacher` VALUES (131, '164000369', '许庆', 2);
INSERT INTO `t_teacher` VALUES (132, '164000371', '曹诚伟', 1);
INSERT INTO `t_teacher` VALUES (133, '131100403', '辛海燕', 2);
INSERT INTO `t_teacher` VALUES (134, '131100404', '王迷迷', 2);
INSERT INTO `t_teacher` VALUES (135, '138100460', '操凤萍', 2);
INSERT INTO `t_teacher` VALUES (136, '131000519', '高建国', 1);
INSERT INTO `t_teacher` VALUES (137, '131100531', '陈玉林', 2);
INSERT INTO `t_teacher` VALUES (138, '131100532', '陈慧琴', 2);
INSERT INTO `t_teacher` VALUES (139, '138800549', '徐品琪', 1);
INSERT INTO `t_teacher` VALUES (140, '131100567', '郁佳佳', 2);
INSERT INTO `t_teacher` VALUES (141, '131100568', '左梅', 2);
INSERT INTO `t_teacher` VALUES (142, '100000642', '王理想', 1);
INSERT INTO `t_teacher` VALUES (143, '100000643', '樊佳伟', 1);
INSERT INTO `t_teacher` VALUES (144, '100000644', '吴任穷', 1);
INSERT INTO `t_teacher` VALUES (145, '900001068', '晏祥彪', 1);
INSERT INTO `t_teacher` VALUES (146, '100000640', '章伟', 2);
INSERT INTO `t_teacher` VALUES (147, '100000641', '马明', 1);
INSERT INTO `t_teacher` VALUES (148, '931000733', '胡飞', 1);
INSERT INTO `t_teacher` VALUES (149, '931000737', '秦明', 1);
INSERT INTO `t_teacher` VALUES (150, '931000742', '顾群', 1);
INSERT INTO `t_teacher` VALUES (151, '931000755', '冯宏星', 1);
INSERT INTO `t_teacher` VALUES (152, '931000793', '于兵', 1);
INSERT INTO `t_teacher` VALUES (153, '931100445', '赵力', 1);
INSERT INTO `t_teacher` VALUES (154, '931100518', '董乾', 1);
INSERT INTO `t_teacher` VALUES (155, '164000131', '李宗轩', 1);
INSERT INTO `t_teacher` VALUES (156, '164000149', '李振东', 1);
INSERT INTO `t_teacher` VALUES (157, '164000368', '吉静', 2);
INSERT INTO `t_teacher` VALUES (158, '131800290', '宋奎', 1);
INSERT INTO `t_teacher` VALUES (159, '999100031', '郝立', 2);
INSERT INTO `t_teacher` VALUES (160, '931100699', '薛红', 2);
INSERT INTO `t_teacher` VALUES (161, '931100243', '陈良斌', 1);
INSERT INTO `t_teacher` VALUES (162, '164000370', '王珩', 2);
INSERT INTO `t_teacher` VALUES (163, '931000815', '黄永明', 1);
INSERT INTO `t_teacher` VALUES (164, '931000816', '乐英高', 1);
INSERT INTO `t_teacher` VALUES (165, '931400196', '李泽民', 1);
INSERT INTO `t_teacher` VALUES (166, '999100219', '王增和', 1);
INSERT INTO `t_teacher` VALUES (167, '999100058', '林科学', 1);
INSERT INTO `t_teacher` VALUES (168, '938100695', '沈克勤', 1);
INSERT INTO `t_teacher` VALUES (169, '931100372', '许大宇', 1);
INSERT INTO `t_teacher` VALUES (170, '931100370', '陈黎来', 1);
INSERT INTO `t_teacher` VALUES (171, '931100692', '赵贤林', 1);
INSERT INTO `t_teacher` VALUES (172, '113000439', '朱金娟', 2);
INSERT INTO `t_teacher` VALUES (173, '931100374', '储荣', 1);
INSERT INTO `t_teacher` VALUES (174, '931100663', '王培章', 1);
INSERT INTO `t_teacher` VALUES (175, '931100562', '李林', 1);
INSERT INTO `t_teacher` VALUES (176, '931200184', '杨颖红', 2);
INSERT INTO `t_teacher` VALUES (177, '931000740', '徐大华', 1);
INSERT INTO `t_teacher` VALUES (178, '931100444', '束海泉', 1);
INSERT INTO `t_teacher` VALUES (179, '931000734', '李琦', 1);
INSERT INTO `t_teacher` VALUES (180, '931000735', '戚晨皓', 1);
INSERT INTO `t_teacher` VALUES (181, '931000736', '赵霞', 2);
INSERT INTO `t_teacher` VALUES (182, '931000738', '王琦龙', 1);
INSERT INTO `t_teacher` VALUES (183, '164000047', '吴小安', 2);
INSERT INTO `t_teacher` VALUES (184, '164000065', '于维顺', 1);
INSERT INTO `t_teacher` VALUES (185, '164000069', '张志鹏', 1);
INSERT INTO `t_teacher` VALUES (186, '138800576', '王晶晶', 2);
INSERT INTO `t_teacher` VALUES (187, '138100459', '刘雪娟', 2);
INSERT INTO `t_teacher` VALUES (188, '131100486', '张立珍', 2);
INSERT INTO `t_teacher` VALUES (189, '138100091', '朱林', 1);
INSERT INTO `t_teacher` VALUES (190, '133800295', '羊栋', 1);
INSERT INTO `t_teacher` VALUES (191, '100001010', '章品正', 1);
INSERT INTO `t_teacher` VALUES (192, '100001066', '王梦晓', 2);
INSERT INTO `t_teacher` VALUES (193, '100000974', '梁静婷', 2);
INSERT INTO `t_teacher` VALUES (194, '100000993', '王海彬', 1);
INSERT INTO `t_teacher` VALUES (195, '100000996', '虞金永', 1);
INSERT INTO `t_teacher` VALUES (196, '100000991', '马杰良', 1);
INSERT INTO `t_teacher` VALUES (197, '100000968', '张秀再', 1);
INSERT INTO `t_teacher` VALUES (198, '100001046', '王荣', 1);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `createAt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateAt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 241 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (2, '38215101', '38215101', '2018-04-25 21:22:21', '2018-05-18 23:37:04');
INSERT INTO `t_user` VALUES (3, '38215102', '38215102', '2018-04-25 21:22:21', '2018-05-19 00:01:58');
INSERT INTO `t_user` VALUES (4, '38215103', '38215103', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (5, '38215104', '38215104', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (6, '38215105', '38215105', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (7, '38215107', '38215107', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (8, '38215108', '38215108', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (9, '38215109', '38215109', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (10, '38215111', '38215111', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (11, '38215112', '38215112', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (12, '38215113', '38215113', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (13, '38215114', '38215114', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (14, '38215116', '38215116', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (15, '38215117', '38215117', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (16, '38215118', '38215118', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (17, '38215119', '38215119', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (18, '38215120', '38215120', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (19, '38215121', '38215121', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (20, '38215123', '38215123', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (21, '38215124', '38215124', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (22, '38215125', '38215125', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (23, '38215126', '38215126', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (24, '38215127', '38215127', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (25, '38215128', '38215128', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (26, '38215129', '38215129', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (27, '38215130', '38215130', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (28, '38215131', '38215131', '2018-04-25 21:22:21', '2018-05-27 20:53:26');
INSERT INTO `t_user` VALUES (29, '38215132', '38215132', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (30, '38215133', '38215133', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (31, '38215134', '38215134', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (32, '38215135', '38215135', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (33, '38215136', '38215136', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (34, '38215137', '38215137', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (35, '38215138', '38215138', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (36, '38215139', '38215139', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (37, '38215140', '38215140', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (38, '38215142', '38215142', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (39, '38215143', '38215143', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (40, '38215144', '38215144', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (41, '38215145', '38215145', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (42, '38215147', '38215147', '2018-04-25 21:22:21', '2018-04-25 21:22:21');
INSERT INTO `t_user` VALUES (43, '931200189', '931200189', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (44, '931200190', '931200190', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (45, '931300144', '931300144', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (46, '931300145', '931300145', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (47, '100000617', '100000617', '2018-04-25 22:18:55', '2018-05-30 20:55:44');
INSERT INTO `t_user` VALUES (48, '100000621', '100000621', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (49, '100000625', '100000625', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (50, '138000344', '138000344', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (51, '938100285', '938100285', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (52, '999100251', '999100251', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (53, '937100265', '937100265', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (54, '938000731', '938000731', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (55, '938100697', '938100697', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (56, '999100049', '999100049', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (57, '931200105', '931200105', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (58, '999100182', '999100182', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (59, '931100376', '931100376', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (60, '999100244', '999100244', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (61, '931400020', '931400020', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (62, '931300017', '931300017', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (63, '999100235', '999100235', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (64, '931100397', '931100397', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (65, '131100310', '131100310', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (66, '131800119', '131800119', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (67, '164000261', '164000261', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (68, '164000501', '164000501', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (69, '938000730', '938000730', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (70, '938000732', '938000732', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (71, '938000849', '938000849', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (72, '938000865', '938000865', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (73, '938100233', '38215131', '2018-04-25 22:18:55', '2018-05-27 20:51:50');
INSERT INTO `t_user` VALUES (74, '938100418', '938100418', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (75, '938100696', '938100696', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (76, '100000677', '100000677', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (77, '100000747', '100000747', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (78, '100000786', '100000786', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (79, '100000853', '100000853', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (80, '100000894', '100000894', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (81, '100000895', '100000895', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (82, '100000904', '100000904', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (83, '100000924', '100000924', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (84, '100000941', '100000941', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (85, '100000944', '100000944', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (86, '100000945', '100000945', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (87, '100000953', '100000953', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (88, '999100078', '999100078', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (89, '999100191', '999100191', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (90, '999100200', '999100200', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (91, '999100236', '999100236', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (92, '900000886', '900000886', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (93, '931000773', '931000773', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (94, '931100449', '931100449', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (95, '931100606', '931100606', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (96, '931000756', '931000756', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (97, '931000794', '931000794', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (98, '931100137', '931100137', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (99, '931100373', '931100373', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (100, '931100422', '931100422', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (101, '931100447', '931100447', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (102, '931100607', '931100607', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (103, '931100665', '931100665', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (104, '931100698', '931100698', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (105, '931200183', '931200183', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (106, '931300014', '931300014', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (107, '931300015', '931300015', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (108, '931300200', '931300200', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (109, '931300201', '931300201', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (110, '931400019', '931400019', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (111, '131100309', '131100309', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (112, '131000345', '131000345', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (113, '131100356', '131100356', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (114, '131000548', '131000548', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (115, '138100177', '138100177', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (116, '900001003', '900001003', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (117, '900001002', '900001002', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (118, '900001011', '900001011', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (119, '931500024', '931500024', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (120, '900001066', '900001066', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (121, '900001062', '900001062', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (122, '900001061', '900001061', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (123, '900001067', '900001067', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (124, '900001065', '900001065', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (125, '900001063', '900001063', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (126, '900001064', '900001064', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (127, '900001022', '900001022', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (128, '900001023', '900001023', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (129, '900001021', '900001021', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (130, '999100220', '999100220', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (131, '100000695', '100000695', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (132, '100000723', '100000723', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (133, '100000745', '100000745', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (134, '100000761', '100000761', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (135, '100000783', '100000783', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (136, '100000802', '100000802', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (137, '100000823', '100000823', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (138, '100000872', '100000872', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (139, '931100664', '931100664', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (140, '938000773', '938000773', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (141, '999100126', '999100126', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (142, '999100175', '999100175', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (143, '999100214', '999100214', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (144, '999100222', '999100222', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (145, '931000885', '931000885', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (146, '931500022', '931500022', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (147, '931400197', '931400197', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (148, '900001049', '900001049', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (149, '900001060', '900001060', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (150, '931200230', '931200230', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (151, '931500021', '931500021', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (152, '131800288', '131800288', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (153, '164000169', '164000169', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (154, '931200186', '931200186', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (155, '931200188', '931200188', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (156, '137000114', '137000114', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (157, '164000132', '164000132', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (158, '131100180', '131100180', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (159, '131100182', '131100182', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (160, '164000212', '164000212', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (161, '131800218', '131800218', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (162, '138100265', '138100265', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (163, '131100308', '131100308', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (164, '137800397', '137800397', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (165, '138100093', '138100093', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (166, '138100372', '138100372', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (167, '138100478', '138100478', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (168, '931000739', '931000739', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (169, '931000741', '931000741', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (170, '931000743', '931000743', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (171, '931000744', '931000744', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (172, '938100598', '938100598', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (173, '164000369', '164000369', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (174, '164000371', '164000371', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (175, '131100403', '131100403', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (176, '131100404', '131100404', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (177, '138100460', '138100460', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (178, '131000519', '131000519', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (179, '131100531', '131100531', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (180, '131100532', '131100532', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (181, '138800549', '138800549', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (182, '131100567', '131100567', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (183, '131100568', '131100568', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (184, '100000642', '100000642', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (185, '100000643', '100000643', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (186, '100000644', '100000644', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (187, '900001068', '900001068', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (188, '100000640', '100000640', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (189, '100000641', '100000641', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (190, '931000733', '931000733', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (191, '931000737', '931000737', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (192, '931000742', '931000742', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (193, '931000755', '931000755', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (194, '931000793', '931000793', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (195, '931100445', '931100445', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (196, '931100518', '931100518', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (197, '164000131', '164000131', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (198, '164000149', '164000149', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (199, '164000368', '164000368', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (200, '131800290', '131800290', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (201, '999100031', '999100031', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (202, '931100699', '931100699', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (203, '931100243', '931100243', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (204, '164000370', '164000370', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (205, '931000815', '931000815', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (206, '931000816', '931000816', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (207, '931400196', '931400196', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (208, '999100219', '999100219', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (209, '999100058', '999100058', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (210, '938100695', '938100695', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (211, '931100372', '931100372', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (212, '931100370', '931100370', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (213, '931100692', '931100692', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (214, '113000439', '113000439', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (215, '931100374', '931100374', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (216, '931100663', '931100663', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (217, '931100562', '931100562', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (218, '931200184', '931200184', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (219, '931000740', '931000740', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (220, '931100444', '931100444', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (221, '931000734', '931000734', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (222, '931000735', '931000735', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (223, '931000736', '931000736', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (224, '931000738', '931000738', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (225, '164000047', '164000047', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (226, '164000065', '164000065', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (227, '164000069', '164000069', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (228, '138800576', '138800576', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (229, '138100459', '138100459', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (230, '131100486', '131100486', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (231, '138100091', '138100091', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (232, '133800295', '133800295', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (233, '100001010', '100001010', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (234, '100001066', '100001066', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (235, '100000974', '100000974', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (236, '100000993', '100000993', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (237, '100000996', '100000996', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (238, '100000991', '100000991', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (239, '100000968', '100000968', '2018-04-25 22:18:55', '2018-04-25 22:18:55');
INSERT INTO `t_user` VALUES (240, '100001046', '100001046', '2018-04-25 22:18:55', '2018-04-25 22:18:55');

-- ----------------------------
-- Table structure for t_user_award
-- ----------------------------
DROP TABLE IF EXISTS `t_user_award`;
CREATE TABLE `t_user_award`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `awardTime` date NOT NULL COMMENT '获奖时间',
  `awardPlace` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '获奖名次',
  `createAt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
  `reviewAt` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `imagePath` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图片路径',
  `userId` int(11) NOT NULL COMMENT '用户id',
  `awardId` int(11) NOT NULL COMMENT '奖项id',
  `rankId` int(11) NOT NULL COMMENT '级别id',
  `reviewId` int(11) NOT NULL COMMENT '审核状态id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_t_user_award_t_award_1`(`awardId`) USING BTREE,
  INDEX `fk_t_user_award_t_rank_1`(`rankId`) USING BTREE,
  INDEX `fk_t_user_award_t_review_1`(`reviewId`) USING BTREE,
  INDEX `fk_t_user_award_t_user_1`(`userId`) USING BTREE,
  CONSTRAINT `fk_t_user_award_t_award_1` FOREIGN KEY (`awardId`) REFERENCES `t_award` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_t_user_award_t_rank_1` FOREIGN KEY (`rankId`) REFERENCES `t_rank` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_t_user_award_t_review_1` FOREIGN KEY (`reviewId`) REFERENCES `t_review` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_t_user_award_t_user_1` FOREIGN KEY (`userId`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户获奖信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_award
-- ----------------------------
INSERT INTO `t_user_award` VALUES (22, '2017-12-01', '一等奖', '2018-07-08 13:59:36', NULL, '\\upload\\382151\\38215131_2018_07_08_13_59_36.jpeg', 28, 22, 1, 2);
INSERT INTO `t_user_award` VALUES (23, '2017-12-01', '一等奖', '2018-07-08 13:59:41', NULL, '\\upload\\382151\\38215131_2018_07_08_13_59_41.jpeg', 28, 22, 1, 1);
INSERT INTO `t_user_award` VALUES (24, '2017-12-01', '一等奖', '2018-07-08 13:59:43', NULL, '\\upload\\382151\\38215131_2018_07_08_13_59_43.jpeg', 28, 22, 1, 1);

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `roleId` int(11) NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_t_user_role_t_role_1`(`roleId`) USING BTREE,
  INDEX `fk_t_user_role_t_user_1`(`userId`) USING BTREE,
  CONSTRAINT `fk_t_user_role_t_role_1` FOREIGN KEY (`roleId`) REFERENCES `t_role` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_t_user_role_t_user_1` FOREIGN KEY (`userId`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 240 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES (1, 2, 5);
INSERT INTO `t_user_role` VALUES (2, 3, 5);
INSERT INTO `t_user_role` VALUES (3, 4, 5);
INSERT INTO `t_user_role` VALUES (4, 5, 5);
INSERT INTO `t_user_role` VALUES (5, 6, 5);
INSERT INTO `t_user_role` VALUES (6, 7, 5);
INSERT INTO `t_user_role` VALUES (7, 8, 5);
INSERT INTO `t_user_role` VALUES (8, 9, 5);
INSERT INTO `t_user_role` VALUES (9, 10, 5);
INSERT INTO `t_user_role` VALUES (10, 11, 5);
INSERT INTO `t_user_role` VALUES (11, 12, 5);
INSERT INTO `t_user_role` VALUES (12, 13, 5);
INSERT INTO `t_user_role` VALUES (13, 14, 5);
INSERT INTO `t_user_role` VALUES (14, 15, 5);
INSERT INTO `t_user_role` VALUES (15, 16, 5);
INSERT INTO `t_user_role` VALUES (16, 17, 5);
INSERT INTO `t_user_role` VALUES (17, 18, 5);
INSERT INTO `t_user_role` VALUES (18, 19, 5);
INSERT INTO `t_user_role` VALUES (19, 20, 5);
INSERT INTO `t_user_role` VALUES (20, 21, 5);
INSERT INTO `t_user_role` VALUES (21, 22, 5);
INSERT INTO `t_user_role` VALUES (22, 23, 5);
INSERT INTO `t_user_role` VALUES (23, 24, 5);
INSERT INTO `t_user_role` VALUES (24, 25, 5);
INSERT INTO `t_user_role` VALUES (25, 26, 5);
INSERT INTO `t_user_role` VALUES (26, 27, 5);
INSERT INTO `t_user_role` VALUES (27, 28, 5);
INSERT INTO `t_user_role` VALUES (28, 29, 5);
INSERT INTO `t_user_role` VALUES (29, 30, 5);
INSERT INTO `t_user_role` VALUES (30, 31, 5);
INSERT INTO `t_user_role` VALUES (31, 32, 5);
INSERT INTO `t_user_role` VALUES (32, 33, 5);
INSERT INTO `t_user_role` VALUES (33, 34, 5);
INSERT INTO `t_user_role` VALUES (34, 35, 5);
INSERT INTO `t_user_role` VALUES (35, 36, 5);
INSERT INTO `t_user_role` VALUES (36, 37, 5);
INSERT INTO `t_user_role` VALUES (37, 38, 5);
INSERT INTO `t_user_role` VALUES (38, 39, 5);
INSERT INTO `t_user_role` VALUES (39, 40, 5);
INSERT INTO `t_user_role` VALUES (40, 41, 5);
INSERT INTO `t_user_role` VALUES (41, 42, 5);
INSERT INTO `t_user_role` VALUES (42, 43, 4);
INSERT INTO `t_user_role` VALUES (43, 44, 4);
INSERT INTO `t_user_role` VALUES (44, 45, 4);
INSERT INTO `t_user_role` VALUES (45, 46, 4);
INSERT INTO `t_user_role` VALUES (46, 47, 4);
INSERT INTO `t_user_role` VALUES (47, 48, 4);
INSERT INTO `t_user_role` VALUES (48, 49, 4);
INSERT INTO `t_user_role` VALUES (49, 50, 4);
INSERT INTO `t_user_role` VALUES (50, 51, 4);
INSERT INTO `t_user_role` VALUES (51, 52, 4);
INSERT INTO `t_user_role` VALUES (52, 53, 4);
INSERT INTO `t_user_role` VALUES (53, 54, 4);
INSERT INTO `t_user_role` VALUES (54, 55, 4);
INSERT INTO `t_user_role` VALUES (55, 56, 4);
INSERT INTO `t_user_role` VALUES (56, 57, 4);
INSERT INTO `t_user_role` VALUES (57, 58, 4);
INSERT INTO `t_user_role` VALUES (58, 59, 4);
INSERT INTO `t_user_role` VALUES (59, 60, 4);
INSERT INTO `t_user_role` VALUES (60, 61, 4);
INSERT INTO `t_user_role` VALUES (61, 62, 4);
INSERT INTO `t_user_role` VALUES (62, 63, 4);
INSERT INTO `t_user_role` VALUES (63, 64, 4);
INSERT INTO `t_user_role` VALUES (64, 65, 4);
INSERT INTO `t_user_role` VALUES (65, 66, 4);
INSERT INTO `t_user_role` VALUES (66, 67, 4);
INSERT INTO `t_user_role` VALUES (67, 68, 4);
INSERT INTO `t_user_role` VALUES (68, 69, 4);
INSERT INTO `t_user_role` VALUES (69, 70, 4);
INSERT INTO `t_user_role` VALUES (70, 71, 4);
INSERT INTO `t_user_role` VALUES (71, 72, 4);
INSERT INTO `t_user_role` VALUES (72, 73, 4);
INSERT INTO `t_user_role` VALUES (73, 74, 4);
INSERT INTO `t_user_role` VALUES (74, 75, 4);
INSERT INTO `t_user_role` VALUES (75, 76, 4);
INSERT INTO `t_user_role` VALUES (76, 77, 4);
INSERT INTO `t_user_role` VALUES (77, 78, 4);
INSERT INTO `t_user_role` VALUES (78, 79, 4);
INSERT INTO `t_user_role` VALUES (79, 80, 4);
INSERT INTO `t_user_role` VALUES (80, 81, 4);
INSERT INTO `t_user_role` VALUES (81, 82, 4);
INSERT INTO `t_user_role` VALUES (82, 83, 4);
INSERT INTO `t_user_role` VALUES (83, 84, 4);
INSERT INTO `t_user_role` VALUES (84, 85, 4);
INSERT INTO `t_user_role` VALUES (85, 86, 4);
INSERT INTO `t_user_role` VALUES (86, 87, 4);
INSERT INTO `t_user_role` VALUES (87, 88, 4);
INSERT INTO `t_user_role` VALUES (88, 89, 4);
INSERT INTO `t_user_role` VALUES (89, 90, 4);
INSERT INTO `t_user_role` VALUES (90, 91, 4);
INSERT INTO `t_user_role` VALUES (91, 92, 4);
INSERT INTO `t_user_role` VALUES (92, 93, 4);
INSERT INTO `t_user_role` VALUES (93, 94, 4);
INSERT INTO `t_user_role` VALUES (94, 95, 4);
INSERT INTO `t_user_role` VALUES (95, 96, 4);
INSERT INTO `t_user_role` VALUES (96, 97, 4);
INSERT INTO `t_user_role` VALUES (97, 98, 4);
INSERT INTO `t_user_role` VALUES (98, 99, 4);
INSERT INTO `t_user_role` VALUES (99, 100, 4);
INSERT INTO `t_user_role` VALUES (100, 101, 4);
INSERT INTO `t_user_role` VALUES (101, 102, 4);
INSERT INTO `t_user_role` VALUES (102, 103, 4);
INSERT INTO `t_user_role` VALUES (103, 104, 4);
INSERT INTO `t_user_role` VALUES (104, 105, 4);
INSERT INTO `t_user_role` VALUES (105, 106, 4);
INSERT INTO `t_user_role` VALUES (106, 107, 4);
INSERT INTO `t_user_role` VALUES (107, 108, 4);
INSERT INTO `t_user_role` VALUES (108, 109, 4);
INSERT INTO `t_user_role` VALUES (109, 110, 4);
INSERT INTO `t_user_role` VALUES (110, 111, 4);
INSERT INTO `t_user_role` VALUES (111, 112, 4);
INSERT INTO `t_user_role` VALUES (112, 113, 4);
INSERT INTO `t_user_role` VALUES (113, 114, 4);
INSERT INTO `t_user_role` VALUES (114, 115, 4);
INSERT INTO `t_user_role` VALUES (115, 116, 4);
INSERT INTO `t_user_role` VALUES (116, 117, 4);
INSERT INTO `t_user_role` VALUES (117, 118, 4);
INSERT INTO `t_user_role` VALUES (118, 119, 4);
INSERT INTO `t_user_role` VALUES (119, 120, 4);
INSERT INTO `t_user_role` VALUES (120, 121, 4);
INSERT INTO `t_user_role` VALUES (121, 122, 4);
INSERT INTO `t_user_role` VALUES (122, 123, 4);
INSERT INTO `t_user_role` VALUES (123, 124, 4);
INSERT INTO `t_user_role` VALUES (124, 125, 4);
INSERT INTO `t_user_role` VALUES (125, 126, 4);
INSERT INTO `t_user_role` VALUES (126, 127, 4);
INSERT INTO `t_user_role` VALUES (127, 128, 4);
INSERT INTO `t_user_role` VALUES (128, 129, 4);
INSERT INTO `t_user_role` VALUES (129, 130, 4);
INSERT INTO `t_user_role` VALUES (130, 131, 4);
INSERT INTO `t_user_role` VALUES (131, 132, 4);
INSERT INTO `t_user_role` VALUES (132, 133, 4);
INSERT INTO `t_user_role` VALUES (133, 134, 4);
INSERT INTO `t_user_role` VALUES (134, 135, 4);
INSERT INTO `t_user_role` VALUES (135, 136, 4);
INSERT INTO `t_user_role` VALUES (136, 137, 4);
INSERT INTO `t_user_role` VALUES (137, 138, 4);
INSERT INTO `t_user_role` VALUES (138, 139, 4);
INSERT INTO `t_user_role` VALUES (139, 140, 4);
INSERT INTO `t_user_role` VALUES (140, 141, 4);
INSERT INTO `t_user_role` VALUES (141, 142, 4);
INSERT INTO `t_user_role` VALUES (142, 143, 4);
INSERT INTO `t_user_role` VALUES (143, 144, 4);
INSERT INTO `t_user_role` VALUES (144, 145, 4);
INSERT INTO `t_user_role` VALUES (145, 146, 4);
INSERT INTO `t_user_role` VALUES (146, 147, 4);
INSERT INTO `t_user_role` VALUES (147, 148, 4);
INSERT INTO `t_user_role` VALUES (148, 149, 4);
INSERT INTO `t_user_role` VALUES (149, 150, 4);
INSERT INTO `t_user_role` VALUES (150, 151, 4);
INSERT INTO `t_user_role` VALUES (151, 152, 4);
INSERT INTO `t_user_role` VALUES (152, 153, 4);
INSERT INTO `t_user_role` VALUES (153, 154, 4);
INSERT INTO `t_user_role` VALUES (154, 155, 4);
INSERT INTO `t_user_role` VALUES (155, 156, 4);
INSERT INTO `t_user_role` VALUES (156, 157, 4);
INSERT INTO `t_user_role` VALUES (157, 158, 4);
INSERT INTO `t_user_role` VALUES (158, 159, 4);
INSERT INTO `t_user_role` VALUES (159, 160, 4);
INSERT INTO `t_user_role` VALUES (160, 161, 4);
INSERT INTO `t_user_role` VALUES (161, 162, 4);
INSERT INTO `t_user_role` VALUES (162, 163, 4);
INSERT INTO `t_user_role` VALUES (163, 164, 4);
INSERT INTO `t_user_role` VALUES (164, 165, 4);
INSERT INTO `t_user_role` VALUES (165, 166, 4);
INSERT INTO `t_user_role` VALUES (166, 167, 4);
INSERT INTO `t_user_role` VALUES (167, 168, 4);
INSERT INTO `t_user_role` VALUES (168, 169, 4);
INSERT INTO `t_user_role` VALUES (169, 170, 4);
INSERT INTO `t_user_role` VALUES (170, 171, 4);
INSERT INTO `t_user_role` VALUES (171, 172, 4);
INSERT INTO `t_user_role` VALUES (172, 173, 4);
INSERT INTO `t_user_role` VALUES (173, 174, 4);
INSERT INTO `t_user_role` VALUES (174, 175, 4);
INSERT INTO `t_user_role` VALUES (175, 176, 4);
INSERT INTO `t_user_role` VALUES (176, 177, 4);
INSERT INTO `t_user_role` VALUES (177, 178, 4);
INSERT INTO `t_user_role` VALUES (178, 179, 4);
INSERT INTO `t_user_role` VALUES (179, 180, 4);
INSERT INTO `t_user_role` VALUES (180, 181, 4);
INSERT INTO `t_user_role` VALUES (181, 182, 4);
INSERT INTO `t_user_role` VALUES (182, 183, 4);
INSERT INTO `t_user_role` VALUES (183, 184, 4);
INSERT INTO `t_user_role` VALUES (184, 185, 4);
INSERT INTO `t_user_role` VALUES (185, 186, 4);
INSERT INTO `t_user_role` VALUES (186, 187, 4);
INSERT INTO `t_user_role` VALUES (187, 188, 4);
INSERT INTO `t_user_role` VALUES (188, 189, 4);
INSERT INTO `t_user_role` VALUES (189, 190, 4);
INSERT INTO `t_user_role` VALUES (190, 191, 4);
INSERT INTO `t_user_role` VALUES (191, 192, 4);
INSERT INTO `t_user_role` VALUES (192, 193, 4);
INSERT INTO `t_user_role` VALUES (193, 194, 4);
INSERT INTO `t_user_role` VALUES (194, 195, 4);
INSERT INTO `t_user_role` VALUES (195, 196, 4);
INSERT INTO `t_user_role` VALUES (196, 197, 4);
INSERT INTO `t_user_role` VALUES (197, 198, 4);
INSERT INTO `t_user_role` VALUES (198, 199, 4);
INSERT INTO `t_user_role` VALUES (199, 200, 4);
INSERT INTO `t_user_role` VALUES (200, 201, 4);
INSERT INTO `t_user_role` VALUES (201, 202, 4);
INSERT INTO `t_user_role` VALUES (202, 203, 4);
INSERT INTO `t_user_role` VALUES (203, 204, 4);
INSERT INTO `t_user_role` VALUES (204, 205, 4);
INSERT INTO `t_user_role` VALUES (205, 206, 4);
INSERT INTO `t_user_role` VALUES (206, 207, 4);
INSERT INTO `t_user_role` VALUES (207, 208, 4);
INSERT INTO `t_user_role` VALUES (208, 209, 4);
INSERT INTO `t_user_role` VALUES (209, 210, 4);
INSERT INTO `t_user_role` VALUES (210, 211, 4);
INSERT INTO `t_user_role` VALUES (211, 212, 4);
INSERT INTO `t_user_role` VALUES (212, 213, 4);
INSERT INTO `t_user_role` VALUES (213, 214, 4);
INSERT INTO `t_user_role` VALUES (214, 215, 4);
INSERT INTO `t_user_role` VALUES (215, 216, 4);
INSERT INTO `t_user_role` VALUES (216, 217, 4);
INSERT INTO `t_user_role` VALUES (217, 218, 4);
INSERT INTO `t_user_role` VALUES (218, 219, 4);
INSERT INTO `t_user_role` VALUES (219, 220, 4);
INSERT INTO `t_user_role` VALUES (220, 221, 4);
INSERT INTO `t_user_role` VALUES (221, 222, 4);
INSERT INTO `t_user_role` VALUES (222, 223, 4);
INSERT INTO `t_user_role` VALUES (223, 224, 4);
INSERT INTO `t_user_role` VALUES (224, 225, 4);
INSERT INTO `t_user_role` VALUES (225, 226, 4);
INSERT INTO `t_user_role` VALUES (226, 227, 4);
INSERT INTO `t_user_role` VALUES (227, 228, 4);
INSERT INTO `t_user_role` VALUES (228, 229, 4);
INSERT INTO `t_user_role` VALUES (229, 230, 4);
INSERT INTO `t_user_role` VALUES (230, 231, 4);
INSERT INTO `t_user_role` VALUES (231, 232, 4);
INSERT INTO `t_user_role` VALUES (232, 233, 4);
INSERT INTO `t_user_role` VALUES (233, 234, 4);
INSERT INTO `t_user_role` VALUES (234, 235, 4);
INSERT INTO `t_user_role` VALUES (235, 236, 4);
INSERT INTO `t_user_role` VALUES (236, 237, 4);
INSERT INTO `t_user_role` VALUES (237, 238, 4);
INSERT INTO `t_user_role` VALUES (238, 239, 4);
INSERT INTO `t_user_role` VALUES (239, 240, 4);

-- ----------------------------
-- View structure for v_user_info
-- ----------------------------
DROP VIEW IF EXISTS `v_user_info`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `v_user_info` AS select `t_user`.`id` AS `userId`,`t_user`.`username` AS `username`,`t_user`.`password` AS `password`,`t_user_role`.`roleId` AS `roleId`,`t_role`.`roleName` AS `roleName`,`t_role`.`roleNameEn` AS `roleNameEn` from ((`t_user` join `t_user_role` on((`t_user_role`.`userId` = `t_user`.`id`))) join `t_role` on((`t_user_role`.`roleId` = `t_role`.`id`)));

-- ----------------------------
-- View structure for v_student_info
-- ----------------------------
DROP VIEW IF EXISTS `v_student_info`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `v_student_info` AS select `t_user`.`id` AS `userId`,`t_user`.`username` AS `username`,`t_user`.`createAt` AS `createAt`,`t_user`.`updateAt` AS `updateAt`,`t_user_role`.`roleId` AS `roleId`,`t_role`.`roleName` AS `roleName`,`t_role`.`roleNameEn` AS `roleNameEn`,`t_student`.`stuNo` AS `stuNo`,`t_student`.`stuName` AS `name`,`t_class`.`className` AS `className`,`t_gender`.`genderName` AS `genderName`,`t_grade`.`gradeName` AS `gradeName`,`t_major`.`majorName` AS `majorName`,`t_student`.`genderId` AS `genderId`,`t_student`.`classId` AS `classId`,`t_class`.`gradeId` AS `gradeId`,`t_class`.`majorId` AS `majorId` from (((((((`t_user` join `t_user_role` on((`t_user_role`.`userId` = `t_user`.`id`))) join `t_role` on((`t_user_role`.`roleId` = `t_role`.`id`))) join `t_student` on((`t_user`.`username` = `t_student`.`stuNo`))) join `t_class` on((`t_student`.`classId` = `t_class`.`id`))) join `t_gender` on((`t_student`.`genderId` = `t_gender`.`id`))) join `t_grade` on((`t_class`.`gradeId` = `t_grade`.`id`))) join `t_major` on((`t_class`.`majorId` = `t_major`.`id`)));


-- ----------------------------
-- View structure for v_teacher_info
-- ----------------------------
DROP VIEW IF EXISTS `v_teacher_info`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `v_teacher_info` AS select `t_user`.`id` AS `userId`,`t_user`.`username` AS `username`,`t_user`.`createAt` AS `createAt`,`t_user`.`updateAt` AS `updateAt`,`t_user_role`.`roleId` AS `roleId`,`t_role`.`roleName` AS `roleName`,`t_role`.`roleNameEn` AS `roleNameEn`,`t_teacher`.`teaNo` AS `teaNo`,`t_teacher`.`teaName` AS `name`,`t_gender`.`genderName` AS `genderName`,`t_teacher`.`genderId` AS `genderId` from ((((`t_user` join `t_user_role` on((`t_user_role`.`userId` = `t_user`.`id`))) join `t_role` on((`t_user_role`.`roleId` = `t_role`.`id`))) join `t_teacher` on((`t_user`.`username` = `t_teacher`.`teaNo`))) join `t_gender` on((`t_teacher`.`genderId` = `t_gender`.`id`)));


-- ----------------------------
-- View structure for v_student_award
-- ----------------------------
DROP VIEW IF EXISTS `v_student_award`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `v_student_award` AS select `t_user_award`.`id` AS `id`,`t_user_award`.`awardTime` AS `awardTime`,`t_user_award`.`awardPlace` AS `awardPlace`,`t_user_award`.`createAt` AS `createAt`,`t_user_award`.`reviewAt` AS `reviewAt`,`t_user_award`.`imagePath` AS `imagePath`,`t_award`.`awardName` AS `awardName`,`t_rank`.`rankName` AS `rankName`,`t_review`.`reviewName` AS `reviewName`,`v_student_info`.`username` AS `username`,`v_student_info`.`stuNo` AS `stuNo`,`v_student_info`.`name` AS `name`,`v_student_info`.`className` AS `className`,`v_student_info`.`genderName` AS `genderName`,`v_student_info`.`gradeName` AS `gradeName`,`v_student_info`.`majorName` AS `majorName`,`t_user_award`.`userId` AS `userId`,`t_user_award`.`awardId` AS `awardId`,`t_user_award`.`rankId` AS `rankId`,`t_user_award`.`reviewId` AS `reviewId`,`v_student_info`.`genderId` AS `genderId`,`v_student_info`.`classId` AS `classId`,`v_student_info`.`gradeId` AS `gradeId`,`v_student_info`.`majorId` AS `majorId` from ((((`t_user_award` join `t_award` on((`t_user_award`.`awardId` = `t_award`.`id`))) join `t_rank` on((`t_user_award`.`rankId` = `t_rank`.`id`))) join `t_review` on((`t_user_award`.`reviewId` = `t_review`.`id`))) join `v_student_info` on((`t_user_award`.`userId` = `v_student_info`.`userId`)));


-- ----------------------------
-- View structure for v_teacher_award
-- ----------------------------
DROP VIEW IF EXISTS `v_teacher_award`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `v_teacher_award` AS select `t_user_award`.`id` AS `id`,`t_user_award`.`awardTime` AS `awardTime`,`t_user_award`.`awardPlace` AS `awardPlace`,`t_user_award`.`createAt` AS `createAt`,`t_user_award`.`reviewAt` AS `reviewAt`,`t_user_award`.`imagePath` AS `imagePath`,`t_award`.`awardName` AS `awardName`,`t_rank`.`rankName` AS `rankName`,`t_review`.`reviewName` AS `reviewName`,`v_teacher_info`.`username` AS `username`,`v_teacher_info`.`teaNo` AS `teaNo`,`v_teacher_info`.`name` AS `name`,`v_teacher_info`.`genderName` AS `genderName`,`t_user_award`.`userId` AS `userId`,`t_user_award`.`awardId` AS `awardId`,`t_user_award`.`rankId` AS `rankId`,`t_user_award`.`reviewId` AS `reviewId`,`v_teacher_info`.`genderId` AS `genderId` from ((((`t_user_award` join `t_award` on((`t_user_award`.`awardId` = `t_award`.`id`))) join `t_rank` on((`t_user_award`.`rankId` = `t_rank`.`id`))) join `t_review` on((`t_user_award`.`reviewId` = `t_review`.`id`))) join `v_teacher_info` on((`t_user_award`.`userId` = `v_teacher_info`.`userId`)));


SET FOREIGN_KEY_CHECKS = 1;
