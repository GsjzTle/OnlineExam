/*
 Navicat Premium Data Transfer

 Source Server         : Connect
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : OnlineExam

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 04/06/2022 23:43:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for class_class
-- ----------------------------
DROP TABLE IF EXISTS `class_class`;
CREATE TABLE `class_class` (
  `cid` int NOT NULL AUTO_INCREMENT,
  `class_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of class_class
-- ----------------------------
BEGIN;
INSERT INTO `class_class` VALUES (1, '18软件1班');
INSERT INTO `class_class` VALUES (2, '18软件2班');
INSERT INTO `class_class` VALUES (3, '18计科3班');
INSERT INTO `class_class` VALUES (4, '19计科2班');
INSERT INTO `class_class` VALUES (5, '20物联网3班');
COMMIT;

-- ----------------------------
-- Table structure for exam_data
-- ----------------------------
DROP TABLE IF EXISTS `exam_data`;
CREATE TABLE `exam_data` (
  `id` int NOT NULL AUTO_INCREMENT,
  `eid` int NOT NULL,
  `uid` int NOT NULL,
  `class_name` varchar(255) NOT NULL,
  `subject_name` varchar(255) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `auto_score` varchar(255) DEFAULT NULL,
  `teacher_score` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `score_choice` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '选择题各题分值',
  `score_subject` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '填空题各题分值',
  `user_choice` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '用户的答案1',
  `user_subject` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '用户的答案2',
  `grade_choice` int DEFAULT '-1' COMMENT '用户选择题得分',
  `grade_subject` int DEFAULT '-1' COMMENT '用户主观题得分',
  `grade_total` int DEFAULT NULL,
  `submitted` int NOT NULL DEFAULT '0',
  `submit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of exam_data
-- ----------------------------
BEGIN;
INSERT INTO `exam_data` VALUES (12, 1, 1, '18计科1班', '操作系统', '1+1', '1|#|2', '1|#|1', '1|#|2', '1|#|2', '0|#|2', '好|#|开心', 3, 2, 5, 1, '2022-05-31 20:54:51');
INSERT INTO `exam_data` VALUES (16, 1, 1, '19计科2班', '计算机网络', '1+1', '1|#|2', '1|#|2', '1|#|2', '1|#|2', '0|#|2', '好|#|开心', 3, 3, 6, 1, '2022-05-31 21:38:39');
INSERT INTO `exam_data` VALUES (17, 1, 1, '20物联网3班', 'SSM实战', '1+1', '1|#|0', '1|#|1', '1|#|2', '1', '0|#|0', '123|#|不开心', 1, 2, 3, 1, '2022-05-31 23:32:51');
INSERT INTO `exam_data` VALUES (20, 1, 1, '计算机科学与技术1', '操作系统', '1+1', '0|#|0', '0|#|2', '1|#|2', '1|#|2', '-1|#|-1', '|#|', -2, -2, 2, 1, '2022-06-02 18:31:29');
INSERT INTO `exam_data` VALUES (21, 1, 2, '计算机科学与技术1', '操作系统', '1+1', '1|#|0', '1|#|1', '1|#|2', '1|#|2', '0|#|1', '111|#|222', 1, -2, 1, 1, '2022-06-02 18:43:00');
INSERT INTO `exam_data` VALUES (23, 1, 3, '计算机科学与技术1', '操作系统', '1+1', '1|#|2', '1|#|2', '1|#|2', '1|#|2', '0|#|2', '111|#|', -2, 3, 1, 1, '2022-06-04 01:52:01');
COMMIT;

-- ----------------------------
-- Table structure for exam_exam
-- ----------------------------
DROP TABLE IF EXISTS `exam_exam`;
CREATE TABLE `exam_exam` (
  `eid` int NOT NULL AUTO_INCREMENT,
  `uid` int NOT NULL,
  `class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `subject_name` varchar(255) NOT NULL,
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `description` text,
  `problem_choice` varchar(255) DEFAULT NULL,
  `problem_subject` varchar(255) DEFAULT NULL,
  `score_choice` varchar(255) DEFAULT NULL,
  `score_subject` varchar(255) DEFAULT NULL,
  `visible` int DEFAULT NULL,
  `begin_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`eid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of exam_exam
-- ----------------------------
BEGIN;
INSERT INTO `exam_exam` VALUES (1, 1, '计算机科学与技术1', '操作系统', 'haha', '1+1', '1+1=？', '1|#|2', '1|#|2', '1|#|2', '1|#|2', 0, '2022-05-31 12:26:49', '2022-06-05 19:26:49', '2022-05-30 23:27:00');
INSERT INTO `exam_exam` VALUES (2, 1, '18软件2班', '操作系统', '小唐', 'this is a test', 'good', '3|#|2|#|5', '1|#|6|#|4', '3|#|4|#|10', '18|#|17|#|20', 0, '2022-06-04 23:24:53', '2022-06-05 00:00:00', '2022-06-04 23:25:17');
COMMIT;

-- ----------------------------
-- Table structure for problem_choice
-- ----------------------------
DROP TABLE IF EXISTS `problem_choice`;
CREATE TABLE `problem_choice` (
  `pid` int NOT NULL AUTO_INCREMENT,
  `uid` int NOT NULL,
  `sid` int NOT NULL,
  `subject_name` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `options` varchar(255) DEFAULT NULL,
  `answer` int DEFAULT NULL,
  `visible` int DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of problem_choice
-- ----------------------------
BEGIN;
INSERT INTO `problem_choice` VALUES (1, 1, 1, '操作系统', '1+1', '1+1=?', '2|#|3|#|4|#|5', 0, NULL, '2022-05-31 10:30:00');
INSERT INTO `problem_choice` VALUES (2, 1, 1, '操作系统', '2+2', '2+2=?', '2|#|3|#|4|#|5', 2, NULL, '2022-05-31 11:02:36');
INSERT INTO `problem_choice` VALUES (3, 1, 1, '操作系统', '3+3', '2+2=?', '2|#|3|#|4|#|5', 3, NULL, '2022-05-31 11:02:36');
INSERT INTO `problem_choice` VALUES (4, 1, 1, '操作系统', '4+4', '2+2=?', '2|#|3|#|4|#|5', 3, NULL, '2022-05-31 11:02:36');
INSERT INTO `problem_choice` VALUES (5, 1, 1, '操作系统', '5+5', '2+2=?', '2|#|3|#|4|#|5', 3, NULL, '2022-05-31 11:02:36');
INSERT INTO `problem_choice` VALUES (6, 1, 1, '操作系统', '6+6', '2+2=?', '2|#|3|#|4|#|5', 3, NULL, '2022-05-31 11:02:36');
COMMIT;

-- ----------------------------
-- Table structure for problem_subject
-- ----------------------------
DROP TABLE IF EXISTS `problem_subject`;
CREATE TABLE `problem_subject` (
  `pid` int NOT NULL AUTO_INCREMENT,
  `uid` int NOT NULL,
  `sid` int NOT NULL,
  `subject_name` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `standard_answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `visible` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of problem_subject
-- ----------------------------
BEGIN;
INSERT INTO `problem_subject` VALUES (1, 1, 1, '操作系统', '说说你今天的心情？', '是好还是坏呢', NULL, NULL, '2022-05-31 10:30:37');
INSERT INTO `problem_subject` VALUES (2, 1, 1, '操作系统', '你今天开心吗', '是开心还是不开心呢', NULL, NULL, '2022-06-01 09:37:47');
INSERT INTO `problem_subject` VALUES (3, 1, 1, '操作系统', '你还好吗', '是好还是坏呢', NULL, NULL, '2022-05-31 10:30:37');
INSERT INTO `problem_subject` VALUES (4, 1, 1, '操作系统', 'are u silly dog?', '是好还是坏呢', NULL, NULL, '2022-05-31 10:30:37');
INSERT INTO `problem_subject` VALUES (5, 1, 1, '操作系统', '今天天气怎么样', '是好还是坏呢', NULL, NULL, '2022-05-31 10:30:37');
INSERT INTO `problem_subject` VALUES (6, 1, 1, '操作系统', 'to be or not to be', '是好还是坏呢', NULL, NULL, '2022-05-31 10:30:37');
COMMIT;

-- ----------------------------
-- Table structure for subject_subject
-- ----------------------------
DROP TABLE IF EXISTS `subject_subject`;
CREATE TABLE `subject_subject` (
  `sid` int NOT NULL AUTO_INCREMENT,
  `subject_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of subject_subject
-- ----------------------------
BEGIN;
INSERT INTO `subject_subject` VALUES (1, '操作系统');
INSERT INTO `subject_subject` VALUES (2, '计算机网络');
INSERT INTO `subject_subject` VALUES (3, '数据结构');
INSERT INTO `subject_subject` VALUES (4, 'SSM实战');
INSERT INTO `subject_subject` VALUES (5, 'C++进阶');
COMMIT;

-- ----------------------------
-- Table structure for user_user
-- ----------------------------
DROP TABLE IF EXISTS `user_user`;
CREATE TABLE `user_user` (
  `uid` int NOT NULL AUTO_INCREMENT,
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `class_name` varchar(255) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` int DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user_user
-- ----------------------------
BEGIN;
INSERT INTO `user_user` VALUES (1, '小唐', '18软件2班', '1', '1', 1, '2022-05-31 20:39:04');
INSERT INTO `user_user` VALUES (2, '小薇', '19计科2班', 'gsjztle', '123', 1, '2022-06-02 00:14:25');
INSERT INTO `user_user` VALUES (3, '小臭', '21软件工程3班', 'yazi', '123456', 1, '2022-06-10 00:14:53');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
