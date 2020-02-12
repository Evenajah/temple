CREATE DATABASE  IF NOT EXISTS `temple` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `temple`;
-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: temple
-- ------------------------------------------------------
-- Server version	5.5.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `baggage`
--

DROP TABLE IF EXISTS `baggage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `baggage` (
  `baggage_id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) NOT NULL,
  `locker_id` int(11) NOT NULL,
  `baggage_status` enum('0','1','2') NOT NULL DEFAULT '0' COMMENT '0 ฝาก, 1 รับคืนแล้ว, 2  ยกเลิก',
  `baggage_create_date` datetime DEFAULT NULL,
  `baggage_last_update` datetime DEFAULT NULL,
  PRIMARY KEY (`baggage_id`),
  KEY `member_id` (`member_id`),
  KEY `fk_lockers1` (`locker_id`),
  CONSTRAINT `fk_lockers1` FOREIGN KEY (`locker_id`) REFERENCES `lockers` (`locker_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_member` FOREIGN KEY (`member_id`) REFERENCES `members` (`member_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8 COMMENT='ตารางสัมภาระ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `baggage`
--

LOCK TABLES `baggage` WRITE;
/*!40000 ALTER TABLE `baggage` DISABLE KEYS */;
/*!40000 ALTER TABLE `baggage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses` (
  `course_id` int(11) NOT NULL AUTO_INCREMENT,
  `course_no` int(11) NOT NULL DEFAULT '0',
  `course_name` varchar(255) NOT NULL,
  `course_detail` varchar(255) NOT NULL,
  `course_st_date` date DEFAULT NULL,
  `course_end_date` date DEFAULT NULL,
  `course_condition_min` int(3) DEFAULT NULL,
  `course_location_id` int(11) NOT NULL,
  `course_create_by` int(11) NOT NULL,
  `course_create_date` datetime DEFAULT NULL,
  `course_last_update` datetime DEFAULT NULL,
  `course_status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '0 out time , 1 in time',
  `course_enable` tinyint(4) NOT NULL DEFAULT '1' COMMENT '0 block, 1 active',
  `course_transportation_temple_id` int(11) NOT NULL COMMENT 'fk ตารางการเดินทางของวัด',
  PRIMARY KEY (`course_id`),
  KEY `fk_courses_location1_idx` (`course_location_id`),
  KEY `fk_courses_members1_idx` (`course_create_by`),
  KEY `fk_course_transportation_temple_id_idx` (`course_transportation_temple_id`),
  CONSTRAINT `fk_course_transportation_temple_id` FOREIGN KEY (`course_transportation_temple_id`) REFERENCES `transportation_temple` (`transportation_temple_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_courses_location1` FOREIGN KEY (`course_location_id`) REFERENCES `locations` (`location_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_courses_members1` FOREIGN KEY (`course_create_by`) REFERENCES `members` (`member_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8 COMMENT='ตารางคอร์ส';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses_schedule`
--

DROP TABLE IF EXISTS `courses_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses_schedule` (
  `course_id` int(11) NOT NULL,
  `course_schedule_date` date NOT NULL,
  PRIMARY KEY (`course_id`,`course_schedule_date`),
  KEY `fk_couse_shedule_courses1_idx` (`course_id`),
  CONSTRAINT `fk_couse_shedule_courses1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ตารางเวลาคอร์สนอกเวลา';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses_schedule`
--

LOCK TABLES `courses_schedule` WRITE;
/*!40000 ALTER TABLE `courses_schedule` DISABLE KEYS */;
/*!40000 ALTER TABLE `courses_schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses_teacher`
--

DROP TABLE IF EXISTS `courses_teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses_teacher` (
  `course_id` int(11) NOT NULL,
  `member_id` int(11) NOT NULL,
  PRIMARY KEY (`course_id`,`member_id`),
  KEY `fk_courses_has_members_members1_idx` (`member_id`),
  KEY `fk_courses_has_members_courses1_idx` (`course_id`),
  CONSTRAINT `fk_courses_has_members_courses1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_courses_has_members_members1` FOREIGN KEY (`member_id`) REFERENCES `members` (`member_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ตารางอาจารย์ผู้สอบกับคอร์สที่สอน';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses_teacher`
--

LOCK TABLES `courses_teacher` WRITE;
/*!40000 ALTER TABLE `courses_teacher` DISABLE KEYS */;
/*!40000 ALTER TABLE `courses_teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gender`
--

DROP TABLE IF EXISTS `gender`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gender` (
  `gender_id` int(11) NOT NULL AUTO_INCREMENT,
  `gender_name` varchar(45) NOT NULL,
  PRIMARY KEY (`gender_id`),
  UNIQUE KEY `gender_name_UNIQUE` (`gender_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='ตารางเพศ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gender`
--

LOCK TABLES `gender` WRITE;
/*!40000 ALTER TABLE `gender` DISABLE KEYS */;
INSERT INTO `gender` VALUES (1,'ชาย'),(2,'หญิง'),(3,'อื่นๆ');
/*!40000 ALTER TABLE `gender` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history_dharma`
--

DROP TABLE IF EXISTS `history_dharma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `history_dharma` (
  `history_dharma_id` int(11) NOT NULL AUTO_INCREMENT,
  `history_dharma_desc` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'ชื่อคอร์ส',
  `history_dharma_location` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'สถานที่ปฏิบัติธรรม',
  `history_dharma_member_id` int(11) NOT NULL COMMENT 'fk ตารางสมาชิก',
  PRIMARY KEY (`history_dharma_id`),
  KEY `fk_history_dharma_member_id_idx` (`history_dharma_member_id`),
  CONSTRAINT `fk_history_dharma_member_id` FOREIGN KEY (`history_dharma_member_id`) REFERENCES `members` (`member_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ตารางการปฏิบัติธรรมที่อื่น';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history_dharma`
--

LOCK TABLES `history_dharma` WRITE;
/*!40000 ALTER TABLE `history_dharma` DISABLE KEYS */;
/*!40000 ALTER TABLE `history_dharma` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `locations`
--

DROP TABLE IF EXISTS `locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `locations` (
  `location_id` int(11) NOT NULL AUTO_INCREMENT,
  `location_name` varchar(255) NOT NULL,
  `location_enable` tinyint(4) NOT NULL DEFAULT '1' COMMENT '0 block, 1 active',
  PRIMARY KEY (`location_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='ตารางสถานที่';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `locations`
--

LOCK TABLES `locations` WRITE;
/*!40000 ALTER TABLE `locations` DISABLE KEYS */;
/*!40000 ALTER TABLE `locations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lockers`
--

DROP TABLE IF EXISTS `lockers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lockers` (
  `locker_id` int(11) NOT NULL AUTO_INCREMENT,
  `location_id` int(11) NOT NULL,
  `locker_number` varchar(45) NOT NULL,
  `locker_create_by` int(11) NOT NULL,
  `locker_create_date` datetime DEFAULT NULL,
  `locker_last_update` datetime DEFAULT NULL,
  `locker_status` enum('0','1') NOT NULL DEFAULT '0' COMMENT '0 = ว่าง, 1 = กำลังใช้งาน',
  `locker_enable` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`locker_id`),
  KEY `fk_locker_members1_idx` (`locker_create_by`),
  KEY `fk_locker_locations1_idx` (`location_id`),
  CONSTRAINT `fk_locker_locations1` FOREIGN KEY (`location_id`) REFERENCES `locations` (`location_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_locker_members1` FOREIGN KEY (`locker_create_by`) REFERENCES `members` (`member_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='ตารางตู้เก็บสัมภาระ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lockers`
--

LOCK TABLES `lockers` WRITE;
/*!40000 ALTER TABLE `lockers` DISABLE KEYS */;
/*!40000 ALTER TABLE `lockers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `members` (
  `member_id` int(11) NOT NULL AUTO_INCREMENT,
  `member_role_id` int(11) NOT NULL,
  `member_username` varchar(45) NOT NULL,
  `member_password` varchar(100) NOT NULL,
  `member_title_id` int(11) NOT NULL,
  `member_gender_id` int(11) NOT NULL,
  `member_fname` varchar(255) NOT NULL,
  `member_lname` varchar(255) NOT NULL,
  `member_address` varchar(255) NOT NULL,
  `member_tel` varchar(45) NOT NULL,
  `member_emergency_tel` varchar(45) NOT NULL,
  `member_email` varchar(255) DEFAULT NULL,
  `member_img` longblob,
  `member_register_date` datetime DEFAULT NULL,
  `member_last_update` datetime DEFAULT NULL,
  `member_job` varchar(255) DEFAULT NULL,
  `member_other` varchar(255) DEFAULT NULL,
  `member_blood` varchar(255) DEFAULT NULL,
  `member_allergy_food` varchar(255) DEFAULT NULL,
  `member_allergy_medicine` varchar(255) DEFAULT NULL,
  `member_disease` varchar(255) DEFAULT NULL,
  `member_emer_name` varchar(255) DEFAULT NULL,
  `member_emer_relationship` varchar(255) DEFAULT NULL,
  `member_enable` tinyint(4) NOT NULL DEFAULT '1' COMMENT '0 block, 1 active',
  `member_province_id` int(11) DEFAULT NULL,
  `member_id_card` varchar(13) NOT NULL COMMENT 'เลขที่บัตรประชาชน',
  `member_age` int(11) DEFAULT NULL COMMENT 'อายุ',
  `member_ordian_number` int(11) DEFAULT NULL COMMENT 'จำนวนพรรษาที่บวช',
  `member_ordian_date` datetime DEFAULT NULL COMMENT 'วันที่บวช',
  PRIMARY KEY (`member_id`),
  UNIQUE KEY `member_username_UNIQUE` (`member_username`),
  UNIQUE KEY `member_id_card_UNIQUE` (`member_id_card`),
  KEY `fk_members_titleNames_idx` (`member_title_id`),
  KEY `fk_members_gender1_idx` (`member_gender_id`),
  KEY `fk_members_roles1_idx` (`member_role_id`),
  KEY `fk_members_province_idx` (`member_province_id`),
  CONSTRAINT `fk_members_province_id` FOREIGN KEY (`member_province_id`) REFERENCES `province` (`province_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_members_gender1` FOREIGN KEY (`member_gender_id`) REFERENCES `gender` (`gender_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_members_roles1` FOREIGN KEY (`member_role_id`) REFERENCES `roles` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_members_titleNames` FOREIGN KEY (`member_title_id`) REFERENCES `title_names` (`title_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8 COMMENT='ตารางสมาชิก';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `members`
--

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
INSERT INTO `members` VALUES (1,1,'admin','$2a$10$Cj5ik8L5CvYZj/.x9sXKH.aZvQBY1GMkq2eznDqkda5vMB.NGO7Zi',1,1,'-','-','-','0000000000','0000000000','admin@gmail.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,'',NULL,NULL,NULL);
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `members_has_courses`
--

DROP TABLE IF EXISTS `members_has_courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `members_has_courses` (
  `members_has_course_id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `sense_id` int(11) NOT NULL,
  `mhc_status` enum('0','1','2') NOT NULL DEFAULT '2' COMMENT '0 ยังไม่ผ่าน, 1 ผ่าน, 2กำลังเรียน',
  `register_date` datetime DEFAULT NULL,
  `tran_id` int(11) DEFAULT NULL COMMENT 'fk การเดินทางทั่วไป',
  PRIMARY KEY (`members_has_course_id`),
  KEY `fk_members_has_courses_sensations1_idx` (`sense_id`),
  KEY `fk_members_has_courses_members1_idx` (`member_id`),
  KEY `fk_members_has_courses_courses1_idx` (`course_id`),
  KEY `fk_members_has_courses_tran_id_idx` (`tran_id`),
  CONSTRAINT `fk_members_has_courses_tran_id` FOREIGN KEY (`tran_id`) REFERENCES `transportations` (`tran_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_members_has_courses_courses1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_members_has_courses_members1` FOREIGN KEY (`member_id`) REFERENCES `members` (`member_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_members_has_courses_sensations1` FOREIGN KEY (`sense_id`) REFERENCES `sensations` (`sense_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8 COMMENT='ตารางลงทะเบียนเรียน';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `members_has_courses`
--

LOCK TABLES `members_has_courses` WRITE;
/*!40000 ALTER TABLE `members_has_courses` DISABLE KEYS */;
/*!40000 ALTER TABLE `members_has_courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `province`
--

DROP TABLE IF EXISTS `province`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `province` (
  `province_id` int(11) NOT NULL AUTO_INCREMENT,
  `province_desc` varchar(150) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`province_id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ตารางจังหวัด';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `province`
--

LOCK TABLES `province` WRITE;
/*!40000 ALTER TABLE `province` DISABLE KEYS */;
INSERT INTO `province` VALUES (1,'นครราชสีมา'),(2,'เชียงใหม่'),(3,'กาญจนบุรี'),(4,'ตาก'),(5,'อุบลราชธานี'),(6,'สุราษฎร์ธานี'),(7,'ชัยภูมิ'),(8,'แม่ฮ่องสอน'),(9,'เพชรบูรณ์'),(10,'ลำปาง'),(11,'อุดรธานี'),(12,'เชียงราย'),(13,'น่าน'),(14,'เลย'),(15,'ขอนแก่น'),(16,'พิษณุโลก'),(17,'บุรีรัมย์'),(18,'นครศรีธรรมราช'),(19,'สกลนคร'),(20,'นครสวรรค์'),(21,'ศรีสะเกษ'),(22,'กำแพงเพชร'),(23,'ร้อยเอ็ด'),(24,'สุรินทร์'),(25,'อุตรดิตถ์'),(26,'สงขลา'),(27,'สระแก้ว'),(28,'กาฬสินธุ์'),(29,'อุทัยธานี'),(30,'สุโขทัย'),(31,'แพร่'),(32,'ประจวบคีรีขันธ์'),(33,'จันทบุรี'),(34,'พะเยา'),(35,'เพชรบุรี'),(36,'ลพบุรี'),(37,'ชุมพร'),(38,'นครพนม'),(39,'สุพรรณบุรี'),(40,'ฉะเชิงเทรา'),(41,'มหาสารคาม'),(42,'ราชบุรี'),(43,'ตรัง'),(44,'ปราจีนบุรี'),(45,'กระบี่'),(46,'พิจิตร'),(47,'ยะลา'),(48,'ลำพูน'),(49,'นราธิวาส'),(50,'ชลบุรี'),(51,'มุกดาหาร'),(52,'บึงกาฬ'),(53,'พังงา'),(54,'ยโสธร'),(55,'หนองบัวลำภู'),(56,'สระบุรี'),(57,'ระยอง'),(58,'พัทลุง'),(59,'ระนอง'),(60,'อำนาจเจริญ'),(61,'หนองคาย'),(62,'ตราด'),(63,'พระนครศรีอยุธยา'),(64,'สตูล'),(65,'ชัยนาท'),(66,'นครปฐม'),(67,'นครนายก'),(68,'ปัตตานี'),(69,'กรุงเทพฯ'),(70,'ปทุมธานี'),(71,'สมุทรปราการ'),(72,'อ่างทอง'),(73,'สมุทรสาคร'),(74,'สิงห์บุรี'),(75,'นนทบุรี'),(76,'ภูเก็ต'),(77,'สมุทรสงคราม');
/*!40000 ALTER TABLE `province` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(45) NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_name_UNIQUE` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='ตารางบทบาทสมาชิก';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'admin'),(2,'monk'),(3,'user');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sensations`
--

DROP TABLE IF EXISTS `sensations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sensations` (
  `sense_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id ที่เป็น AI',
  `sense_expected` varchar(255) DEFAULT NULL COMMENT 'ความคาดหวัง',
  `sense_experience` varchar(255) DEFAULT NULL COMMENT 'ประสบการณ์ที่ผ่านมา',
  PRIMARY KEY (`sense_id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8 COMMENT='ตารางของความคาดหวัง และประสบการณ์ที่ได้รับ ซึ่งมีการติดต่อกับตาราง Transportation';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensations`
--

LOCK TABLES `sensations` WRITE;
/*!40000 ALTER TABLE `sensations` DISABLE KEYS */;
/*!40000 ALTER TABLE `sensations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `special_approve`
--

DROP TABLE IF EXISTS `special_approve`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `special_approve` (
  `special_approve_id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `sense_id` int(11) NOT NULL,
  `spa_detail` varchar(255) DEFAULT NULL,
  `spa_status` enum('0','1','2','3','4') NOT NULL DEFAULT '0' COMMENT '0 ไม่Approve, 1 Approve, 2 รอApprove, 3 ยกเลิก , 4 รออนุมัตินอกเวลา',
  `create_date` datetime DEFAULT NULL,
  `last_update` datetime DEFAULT NULL,
  PRIMARY KEY (`special_approve_id`),
  KEY `fk_members_has_courses1_courses1_idx` (`course_id`),
  KEY `fk_members_has_courses1_members1_idx` (`member_id`),
  KEY `fk_members_has_courses1_sensations1_idx` (`sense_id`),
  CONSTRAINT `fk_members_has_courses1_courses1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_members_has_courses1_members1` FOREIGN KEY (`member_id`) REFERENCES `members` (`member_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_members_has_courses1_sensations1` FOREIGN KEY (`sense_id`) REFERENCES `sensations` (`sense_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8 COMMENT='ตารางอนุมัติพิเศษ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `special_approve`
--

LOCK TABLES `special_approve` WRITE;
/*!40000 ALTER TABLE `special_approve` DISABLE KEYS */;
/*!40000 ALTER TABLE `special_approve` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `title_names`
--

DROP TABLE IF EXISTS `title_names`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `title_names` (
  `title_id` int(11) NOT NULL AUTO_INCREMENT,
  `title_display` varchar(45) NOT NULL,
  `title_name` varchar(45) NOT NULL,
  PRIMARY KEY (`title_id`),
  UNIQUE KEY `title_display_UNIQUE` (`title_display`),
  UNIQUE KEY `title_name_UNIQUE` (`title_name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='ตารางคำนำหน้า';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `title_names`
--

LOCK TABLES `title_names` WRITE;
/*!40000 ALTER TABLE `title_names` DISABLE KEYS */;
INSERT INTO `title_names` VALUES (1,'นาย','นาย'),(2,'นาง','นาง'),(3,'น.ส.','นางสาว'),(4,'พระอาจารย์','พระอาจารย์');
/*!40000 ALTER TABLE `title_names` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transportation_temple`
--

DROP TABLE IF EXISTS `transportation_temple`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transportation_temple` (
  `transportation_temple_id` int(11) NOT NULL AUTO_INCREMENT,
  `transportation_temple_name` varchar(255) NOT NULL,
  `transportation_temple_status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '0 = not active, 1 = active',
  `transportation_temple_time_pickup` datetime NOT NULL,
  `transportation_temple_time_send` datetime NOT NULL,
  PRIMARY KEY (`transportation_temple_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ตารางการเดินทางของวัด';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transportation_temple`
--

LOCK TABLES `transportation_temple` WRITE;
/*!40000 ALTER TABLE `transportation_temple` DISABLE KEYS */;
/*!40000 ALTER TABLE `transportation_temple` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transportations`
--

DROP TABLE IF EXISTS `transportations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transportations` (
  `tran_id` int(11) NOT NULL AUTO_INCREMENT,
  `tran_name` varchar(45) NOT NULL,
  `tran_status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '0 = not active, 1 = active',
  PRIMARY KEY (`tran_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='ตารางการเดินทางทั่วไป';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transportations`
--

LOCK TABLES `transportations` WRITE;
/*!40000 ALTER TABLE `transportations` DISABLE KEYS */;
/*!40000 ALTER TABLE `transportations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'temple'
--

--
-- Dumping routines for database 'temple'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-09  9:45:58
