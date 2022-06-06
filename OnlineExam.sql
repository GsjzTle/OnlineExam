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

 Date: 07/06/2022 01:13:17
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of class_class
-- ----------------------------
BEGIN;
INSERT INTO `class_class` VALUES (1, '未加入班级');
INSERT INTO `class_class` VALUES (2, '18软件2班');
INSERT INTO `class_class` VALUES (3, '18计科3班');
INSERT INTO `class_class` VALUES (4, '19计科2班');
INSERT INTO `class_class` VALUES (5, '20物联网3班');
INSERT INTO `class_class` VALUES (6, '18软件1班');
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
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of exam_data
-- ----------------------------
BEGIN;
INSERT INTO `exam_data` VALUES (28, 12, 1, '18软件2班', '操作系统', '入学考试', '1|#|0|#|0', '1|#|1|#|1', '1|#|1|#|1', '1|#|1|#|1', '0|#|3|#|-1', '|#|111|#|', 1, 3, 4, 1, '2022-06-05 19:12:57');
INSERT INTO `exam_data` VALUES (31, 15, 1, '18软件2班', '操作系统', '仅有主观题的考试', NULL, '1', '', '1', '-1', '111', -2, 1, -1, 1, '2022-06-05 19:38:05');
INSERT INTO `exam_data` VALUES (35, 16, 1, '18软件2班', '操作系统', '仅有选择题的考试', '1', NULL, '1', '', '2', '', 1, -2, 1, 1, '2022-06-05 19:43:41');
INSERT INTO `exam_data` VALUES (36, 12, 4, '18软件2班', '操作系统', '入学考试', '1|#|0|#|0', '1|#|0|#|1', '1|#|1|#|1', '1|#|1|#|1', '0|#|2|#|0', '11|#|22|#|22', 1, 2, 3, 1, '2022-06-05 20:38:19');
INSERT INTO `exam_data` VALUES (37, 6, 10001, '18软件2班', '操作系统', '期末考试', '2|#|2|#|0', '10|#|0|#|9', '2|#|2|#|7', '10|#|7|#|9', '2|#|2|#|2', '好|#|好|#|好', 4, 19, 21, 1, '2022-06-05 22:57:10');
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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of exam_exam
-- ----------------------------
BEGIN;
INSERT INTO `exam_exam` VALUES (6, 1, '18软件2班', '操作系统', '小唐', '期末考试', 'this is a 期末考试', '3|#|2|#|4', '3|#|1|#|4', '2|#|2|#|7', '10|#|7|#|9', 0, '2022-06-05 18:29:22', '2022-06-05 23:00:00', '2022-06-05 18:36:09');
INSERT INTO `exam_exam` VALUES (12, 1, '18软件2班', '操作系统', '小唐', '入学考试', 'this is a 入学考试', '1|#|6|#|4', '5|#|2|#|1', '1|#|1|#|1', '1|#|1|#|1', 0, '2022-06-06 18:29:22', '2022-06-10 00:00:00', '2022-06-05 18:55:10');
INSERT INTO `exam_exam` VALUES (15, 1, '18软件2班', '操作系统', '小唐', '仅有主观题的考试', 'this is a 入学考试', '', '1', '', '1', 0, '2022-06-05 18:29:22', '2022-06-06 00:00:00', '2022-06-05 19:18:17');
INSERT INTO `exam_exam` VALUES (16, 1, '18软件2班', '操作系统', '小唐', '仅有选择题的考试', 'this is a 入学考试', '2', '', '1', '', 0, '2022-06-05 18:29:22', '2022-06-06 00:00:00', '2022-06-05 19:18:38');
COMMIT;

-- ----------------------------
-- Table structure for problem_choice
-- ----------------------------
DROP TABLE IF EXISTS `problem_choice`;
CREATE TABLE `problem_choice` (
  `pid` int NOT NULL AUTO_INCREMENT,
  `subject_name` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `options` varchar(255) DEFAULT NULL,
  `answer` int DEFAULT NULL,
  `visible` int DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of problem_choice
-- ----------------------------
BEGIN;
INSERT INTO `problem_choice` VALUES (1, '操作系统', '1+1', '1+1=?', '2|#|3|#|4|#|5', 0, NULL, '2022-05-31 10:30:00');
INSERT INTO `problem_choice` VALUES (2, '操作系统', '2+2', '2+2=?', '2|#|3|#|4|#|5', 2, NULL, '2022-05-31 11:02:36');
INSERT INTO `problem_choice` VALUES (3, '操作系统', '3+3', '3+3=?', '2|#|3|#|4|#|5', 2, NULL, '2022-05-31 11:02:36');
INSERT INTO `problem_choice` VALUES (4, '操作系统', '4+4', '4+4=?', '2|#|3|#|4|#|5', 3, NULL, '2022-05-31 11:02:36');
INSERT INTO `problem_choice` VALUES (5, '操作系统', '5+5', '5+5=?', '2|#|3|#|4|#|5', 3, NULL, '2022-05-31 11:02:36');
INSERT INTO `problem_choice` VALUES (6, '操作系统', '6+6', '6+6=?', '2|#|3|#|4|#|5', 2, NULL, '2022-05-31 11:02:36');
COMMIT;

-- ----------------------------
-- Table structure for problem_subject
-- ----------------------------
DROP TABLE IF EXISTS `problem_subject`;
CREATE TABLE `problem_subject` (
  `pid` int NOT NULL AUTO_INCREMENT,
  `subject_name` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `standard_answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `visible` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of problem_subject
-- ----------------------------
BEGIN;
INSERT INTO `problem_subject` VALUES (1, '操作系统', '说说你今天的心情？', '是好还是坏呢', NULL, NULL, '2022-05-31 10:30:37');
INSERT INTO `problem_subject` VALUES (2, '操作系统', '你今天开心吗', '是开心还是不开心呢', NULL, NULL, '2022-06-01 09:37:47');
INSERT INTO `problem_subject` VALUES (3, '计算机网络', '你还好吗', '是好还是坏呢', NULL, NULL, '2022-05-31 10:30:37');
INSERT INTO `problem_subject` VALUES (4, '操作系统', 'are u silly dog???', '是好还是坏呢??', NULL, NULL, '2022-05-31 10:30:37');
INSERT INTO `problem_subject` VALUES (5, '操作系统', '今天天气怎么样', '是好还是坏呢?', NULL, NULL, '2022-05-31 10:30:37');
INSERT INTO `problem_subject` VALUES (6, '操作系统', 'to be or not to be', '是好还是坏呢?', NULL, NULL, '2022-05-31 10:30:37');
INSERT INTO `problem_subject` VALUES (7, '数据结构', '测试题', 'this is a test gogo', NULL, NULL, '2022-06-07 00:27:04');
INSERT INTO `problem_subject` VALUES (8, '计算机网络', '哈哈哈', '哈哈哈呵呵', NULL, NULL, '2022-06-07 00:27:43');
COMMIT;

-- ----------------------------
-- Table structure for subject_subject
-- ----------------------------
DROP TABLE IF EXISTS `subject_subject`;
CREATE TABLE `subject_subject` (
  `sid` int NOT NULL AUTO_INCREMENT,
  `subject_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of subject_subject
-- ----------------------------
BEGIN;
INSERT INTO `subject_subject` VALUES (1, '操作系统');
INSERT INTO `subject_subject` VALUES (2, '计算机网络');
INSERT INTO `subject_subject` VALUES (3, '数据结构');
INSERT INTO `subject_subject` VALUES (4, 'SSM实战');
INSERT INTO `subject_subject` VALUES (5, 'C++进阶');
INSERT INTO `subject_subject` VALUES (6, 'Java 入门');
COMMIT;

-- ----------------------------
-- Table structure for user_user
-- ----------------------------
DROP TABLE IF EXISTS `user_user`;
CREATE TABLE `user_user` (
  `uid` int NOT NULL AUTO_INCREMENT,
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '???',
  `class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '未加入班级',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `role` int NOT NULL DEFAULT '1',
  `email` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=10003 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user_user
-- ----------------------------
BEGIN;
INSERT INTO `user_user` VALUES (1, '小唐', '18软件2班', '1', '1', '今天天气真好啊', 2, 'gsjztle@google.com', '2022-05-31 20:39:04');
INSERT INTO `user_user` VALUES (3, '小臭', '21软件工程3班', 'cat', '123456', '哒哒哒', 1, 'qq@163.com', '2022-06-10 00:14:53');
INSERT INTO `user_user` VALUES (4, '一一一一一', '未加入班级', 'test1test1test1', 'fcea920f7412b5da7be0cf42b8c93759', '很好', 1, 'lrcsm800513@163.com', '2022-06-05 20:33:45');
INSERT INTO `user_user` VALUES (5, '???', '18软件2班', 'test_test', 'e10adc3949ba59abbe56e057f20f883e', '哈哈哈哈', 2, '1233@qq.com', '2022-06-05 22:39:34');
INSERT INTO `user_user` VALUES (6, '管理员', '18软件2班', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '我是管理员', 2, 'admin@google.com', '2022-06-07 01:07:03');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
