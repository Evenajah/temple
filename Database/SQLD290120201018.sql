-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema temple
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema temple
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `temple` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `temple`.`locations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temple`.`locations` (
  `location_id` INT(11) NOT NULL AUTO_INCREMENT,
  `location_name` VARCHAR(255) NOT NULL,
  `location_enable` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '0 block, 1 active',
  PRIMARY KEY (`location_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8
COMMENT = 'ตารางสถานที่';


-- -----------------------------------------------------
-- Table `temple`.`gender`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temple`.`gender` (
  `gender_id` INT(11) NOT NULL AUTO_INCREMENT,
  `gender_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`gender_id`),
  UNIQUE INDEX `gender_name_UNIQUE` (`gender_name` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8
COMMENT = 'ตารางเพศ';


-- -----------------------------------------------------
-- Table `temple`.`region`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temple`.`region` (
  `region_id` INT(11) NOT NULL AUTO_INCREMENT,
  `region_name` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL,
  PRIMARY KEY (`region_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin
COMMENT = 'ตารางภูมิภาค';


-- -----------------------------------------------------
-- Table `temple`.`province`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temple`.`province` (
  `province_id` INT(11) NOT NULL AUTO_INCREMENT,
  `province_desc` VARCHAR(150) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL,
  `region_id` INT(11) NOT NULL,
  PRIMARY KEY (`province_id`),
  INDEX `fk_region_id_idx` (`region_id` ASC) VISIBLE,
  CONSTRAINT `fk_region_id`
    FOREIGN KEY (`region_id`)
    REFERENCES `temple`.`region` (`region_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 78
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin
COMMENT = 'ตารางจังหวัด';


-- -----------------------------------------------------
-- Table `temple`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temple`.`roles` (
  `role_id` INT(11) NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE INDEX `role_name_UNIQUE` (`role_name` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8
COMMENT = 'ตารางบทบาทสมาชิก';


-- -----------------------------------------------------
-- Table `temple`.`title_names`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temple`.`title_names` (
  `title_id` INT(11) NOT NULL AUTO_INCREMENT,
  `title_display` VARCHAR(45) NOT NULL,
  `title_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`title_id`),
  UNIQUE INDEX `title_display_UNIQUE` (`title_display` ASC) VISIBLE,
  UNIQUE INDEX `title_name_UNIQUE` (`title_name` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8
COMMENT = 'ตารางคำนำหน้า';


-- -----------------------------------------------------
-- Table `temple`.`members`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temple`.`members` (
  `member_id` INT(11) NOT NULL AUTO_INCREMENT,
  `member_role_id` INT(11) NOT NULL,
  `member_username` VARCHAR(45) NOT NULL,
  `member_password` VARCHAR(100) NOT NULL,
  `member_title_id` INT(11) NOT NULL,
  `member_gender_id` INT(11) NOT NULL,
  `member_fname` VARCHAR(255) NOT NULL,
  `member_lname` VARCHAR(255) NOT NULL,
  `member_address` VARCHAR(255) NOT NULL,
  `member_tel` VARCHAR(45) NOT NULL,
  `member_emergency_tel` VARCHAR(45) NOT NULL,
  `member_email` VARCHAR(255) NULL DEFAULT NULL,
  `member_img` LONGBLOB NULL DEFAULT NULL,
  `member_register_date` DATETIME NULL DEFAULT NULL,
  `member_last_update` DATETIME NULL DEFAULT NULL,
  `member_job` VARCHAR(255) NULL DEFAULT NULL,
  `member_other` VARCHAR(255) NULL DEFAULT NULL,
  `member_blood` VARCHAR(255) NULL DEFAULT NULL,
  `member_allergy_food` VARCHAR(255) NULL DEFAULT NULL,
  `member_allergy_medicine` VARCHAR(255) NULL DEFAULT NULL,
  `member_disease` VARCHAR(255) NULL DEFAULT NULL,
  `member_emer_name` VARCHAR(255) NULL DEFAULT NULL,
  `member_emer_relationship` VARCHAR(255) NULL DEFAULT NULL,
  `member_enable` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '0 block, 1 active',
  `member_province_id` INT(11) NULL DEFAULT NULL,
  `member_id_card` VARCHAR(20) NOT NULL COMMENT 'เลขที่บัตรประชาชน',
  `member_age` INT(11) NULL DEFAULT NULL COMMENT 'อายุ',
  `member_ordian_number` INT(11) NULL DEFAULT NULL COMMENT 'จำนวนพรรษาที่บวช',
  `member_ordian_date` DATETIME NULL DEFAULT NULL COMMENT 'วันที่บวช',
  `member_postal_code` VARCHAR(5) NOT NULL COMMENT 'รหัสไปรษณีย์',
  PRIMARY KEY (`member_id`),
  UNIQUE INDEX `member_username_UNIQUE` (`member_username` ASC) VISIBLE,
  UNIQUE INDEX `member_id_card_UNIQUE` (`member_id_card` ASC) VISIBLE,
  INDEX `fk_members_titleNames_idx` (`member_title_id` ASC) VISIBLE,
  INDEX `fk_members_gender1_idx` (`member_gender_id` ASC) VISIBLE,
  INDEX `fk_members_roles1_idx` (`member_role_id` ASC) VISIBLE,
  INDEX `fk_members_province_idx` (`member_province_id` ASC) VISIBLE,
  CONSTRAINT `fk_members_gender1`
    FOREIGN KEY (`member_gender_id`)
    REFERENCES `temple`.`gender` (`gender_id`),
  CONSTRAINT `fk_members_province_id`
    FOREIGN KEY (`member_province_id`)
    REFERENCES `temple`.`province` (`province_id`),
  CONSTRAINT `fk_members_roles1`
    FOREIGN KEY (`member_role_id`)
    REFERENCES `temple`.`roles` (`role_id`),
  CONSTRAINT `fk_members_titleNames`
    FOREIGN KEY (`member_title_id`)
    REFERENCES `temple`.`title_names` (`title_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8
COMMENT = 'ตารางสมาชิก';


-- -----------------------------------------------------
-- Table `temple`.`courses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temple`.`courses` (
  `course_id` INT(11) NOT NULL AUTO_INCREMENT,
  `course_no` INT(11) NOT NULL DEFAULT '0',
  `course_name` VARCHAR(255) NOT NULL,
  `course_detail` VARCHAR(255) NOT NULL,
  `course_st_date` DATE NULL DEFAULT NULL,
  `course_end_date` DATE NULL DEFAULT NULL,
  `course_condition_min` INT(3) NULL DEFAULT NULL,
  `course_location_id` INT(11) NOT NULL,
  `course_create_by` INT(11) NOT NULL,
  `course_create_date` DATETIME NULL DEFAULT NULL,
  `course_last_update` DATETIME NULL DEFAULT NULL,
  `course_status` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '0 out time , 1 in time',
  `course_enable` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '0 block, 1 active',
  PRIMARY KEY (`course_id`),
  INDEX `fk_courses_location1_idx` (`course_location_id` ASC) VISIBLE,
  INDEX `fk_courses_members1_idx` (`course_create_by` ASC) VISIBLE,
  CONSTRAINT `fk_courses_location1`
    FOREIGN KEY (`course_location_id`)
    REFERENCES `temple`.`locations` (`location_id`),
  CONSTRAINT `fk_courses_members1`
    FOREIGN KEY (`course_create_by`)
    REFERENCES `temple`.`members` (`member_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 50
DEFAULT CHARACTER SET = utf8
COMMENT = 'ตารางคอร์ส';


-- -----------------------------------------------------
-- Table `temple`.`sensations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temple`.`sensations` (
  `sense_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'id ที่เป็น AI',
  `sense_expected` VARCHAR(255) NULL DEFAULT NULL COMMENT 'ความคาดหวัง',
  `sense_experience` VARCHAR(255) NULL DEFAULT NULL COMMENT 'ประสบการณ์ที่ผ่านมา',
  PRIMARY KEY (`sense_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 21
DEFAULT CHARACTER SET = utf8
COMMENT = 'ตารางของความคาดหวัง และประสบการณ์ที่ได้รับ ซึ่งมีการติดต่อกับตาราง Transportation';


-- -----------------------------------------------------
-- Table `temple`.`transportations_time`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temple`.`transportations_time` (
  `tran_time_id` INT(11) NOT NULL AUTO_INCREMENT,
  `tran_time_pickup` DATETIME NOT NULL COMMENT 'เวลารับ',
  `tran_time_send` DATETIME NOT NULL COMMENT 'เวลาส่ง',
  PRIMARY KEY (`tran_time_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 13
DEFAULT CHARACTER SET = utf8
COMMENT = 'ตารางเวลารับ ส่งการเดินทางของวัด';


-- -----------------------------------------------------
-- Table `temple`.`transportations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temple`.`transportations` (
  `tran_id` INT(11) NOT NULL AUTO_INCREMENT,
  `tran_name` VARCHAR(45) NOT NULL,
  `tran_time_id` INT(11) NULL DEFAULT NULL COMMENT 'fk ตารางเวลาการเดินทาง',
  `course_id` INT(11) NULL DEFAULT NULL COMMENT 'fk course, ไม่บังคับ',
  PRIMARY KEY (`tran_id`),
  INDEX `fk_courses_id_idx` (`course_id` ASC) VISIBLE,
  INDEX `fk_tran_time_id_idx` (`tran_time_id` ASC) VISIBLE,
  CONSTRAINT `fk_course_id`
    FOREIGN KEY (`course_id`)
    REFERENCES `temple`.`courses` (`course_id`),
  CONSTRAINT `fk_tran_time_id`
    FOREIGN KEY (`tran_time_id`)
    REFERENCES `temple`.`transportations_time` (`tran_time_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 25
DEFAULT CHARACTER SET = utf8
COMMENT = 'ตารางการเดินทาง';


-- -----------------------------------------------------
-- Table `temple`.`special_approve`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temple`.`special_approve` (
  `special_approve_id` INT(11) NOT NULL AUTO_INCREMENT,
  `member_id` INT(11) NOT NULL,
  `course_id` INT(11) NOT NULL,
  `sense_id` INT(11) NOT NULL,
  `spa_detail` VARCHAR(255) NULL DEFAULT NULL,
  `spa_status` ENUM('0', '1', '2', '3', '4') NOT NULL DEFAULT '0' COMMENT '0 ไม่Approve, 1 Approve, 2 รอApprove, 3 ยกเลิก , 4 รออนุมัตินอกเวลา',
  `create_date` DATETIME NULL DEFAULT NULL,
  `last_update` DATETIME NULL DEFAULT NULL,
  `tran_id` INT(11) NULL DEFAULT NULL COMMENT 'fk ตารางการเดินทาง',
  PRIMARY KEY (`special_approve_id`),
  INDEX `fk_members_has_courses1_courses1_idx` (`course_id` ASC) VISIBLE,
  INDEX `fk_members_has_courses1_members1_idx` (`member_id` ASC) VISIBLE,
  INDEX `fk_members_has_courses1_sensations1_idx` (`sense_id` ASC) VISIBLE,
  INDEX `fk_tran_id_idx` (`tran_id` ASC) VISIBLE,
  CONSTRAINT `fk_members_has_courses1_courses1`
    FOREIGN KEY (`course_id`)
    REFERENCES `temple`.`courses` (`course_id`),
  CONSTRAINT `fk_members_has_courses1_members1`
    FOREIGN KEY (`member_id`)
    REFERENCES `temple`.`members` (`member_id`),
  CONSTRAINT `fk_members_has_courses1_sensations1`
    FOREIGN KEY (`sense_id`)
    REFERENCES `temple`.`sensations` (`sense_id`),
  CONSTRAINT `fk_tran_id`
    FOREIGN KEY (`tran_id`)
    REFERENCES `temple`.`transportations` (`tran_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 16
DEFAULT CHARACTER SET = utf8
COMMENT = 'ตารางอนุมัติพิเศษ';


-- -----------------------------------------------------
-- Table `mydb`.`notifications`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`notifications` (
  `notification_id` INT NOT NULL,
  `special_approve_special_approve_id` INT(11) NOT NULL,
  `courses_course_id` INT(11) NOT NULL,
  `members_member_id` INT(11) NOT NULL,
  `notification_status` ENUM('0', '1') NOT NULL DEFAULT '0' COMMENT '0 = ยังไม่อ่าน ,  1  =  อ่านแล้ว\n',
  `notification_time` DATETIME NOT NULL,
  PRIMARY KEY (`notification_id`),
  INDEX `fk_notifications_special_approve_idx` (`special_approve_special_approve_id` ASC) VISIBLE,
  INDEX `fk_notifications_courses1_idx` (`courses_course_id` ASC) VISIBLE,
  INDEX `fk_notifications_members1_idx` (`members_member_id` ASC) VISIBLE,
  CONSTRAINT `fk_notifications_special_approve`
    FOREIGN KEY (`special_approve_special_approve_id`)
    REFERENCES `temple`.`special_approve` (`special_approve_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_notifications_courses1`
    FOREIGN KEY (`courses_course_id`)
    REFERENCES `temple`.`courses` (`course_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_notifications_members1`
    FOREIGN KEY (`members_member_id`)
    REFERENCES `temple`.`members` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
ROW_FORMAT = DYNAMIC;

USE `temple` ;

-- -----------------------------------------------------
-- Table `temple`.`locations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temple`.`locations` (
  `location_id` INT(11) NOT NULL AUTO_INCREMENT,
  `location_name` VARCHAR(255) NOT NULL,
  `location_enable` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '0 block, 1 active',
  PRIMARY KEY (`location_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8
COMMENT = 'ตารางสถานที่';


-- -----------------------------------------------------
-- Table `temple`.`gender`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temple`.`gender` (
  `gender_id` INT(11) NOT NULL AUTO_INCREMENT,
  `gender_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`gender_id`),
  UNIQUE INDEX `gender_name_UNIQUE` (`gender_name` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8
COMMENT = 'ตารางเพศ';


-- -----------------------------------------------------
-- Table `temple`.`region`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temple`.`region` (
  `region_id` INT(11) NOT NULL AUTO_INCREMENT,
  `region_name` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL,
  PRIMARY KEY (`region_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin
COMMENT = 'ตารางภูมิภาค';


-- -----------------------------------------------------
-- Table `temple`.`province`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temple`.`province` (
  `province_id` INT(11) NOT NULL AUTO_INCREMENT,
  `province_desc` VARCHAR(150) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL,
  `region_id` INT(11) NOT NULL,
  PRIMARY KEY (`province_id`),
  INDEX `fk_region_id_idx` (`region_id` ASC) VISIBLE,
  CONSTRAINT `fk_region_id`
    FOREIGN KEY (`region_id`)
    REFERENCES `temple`.`region` (`region_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 78
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin
COMMENT = 'ตารางจังหวัด';


-- -----------------------------------------------------
-- Table `temple`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temple`.`roles` (
  `role_id` INT(11) NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE INDEX `role_name_UNIQUE` (`role_name` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8
COMMENT = 'ตารางบทบาทสมาชิก';


-- -----------------------------------------------------
-- Table `temple`.`title_names`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temple`.`title_names` (
  `title_id` INT(11) NOT NULL AUTO_INCREMENT,
  `title_display` VARCHAR(45) NOT NULL,
  `title_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`title_id`),
  UNIQUE INDEX `title_display_UNIQUE` (`title_display` ASC) VISIBLE,
  UNIQUE INDEX `title_name_UNIQUE` (`title_name` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8
COMMENT = 'ตารางคำนำหน้า';


-- -----------------------------------------------------
-- Table `temple`.`members`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temple`.`members` (
  `member_id` INT(11) NOT NULL AUTO_INCREMENT,
  `member_role_id` INT(11) NOT NULL,
  `member_username` VARCHAR(45) NOT NULL,
  `member_password` VARCHAR(100) NOT NULL,
  `member_title_id` INT(11) NOT NULL,
  `member_gender_id` INT(11) NOT NULL,
  `member_fname` VARCHAR(255) NOT NULL,
  `member_lname` VARCHAR(255) NOT NULL,
  `member_address` VARCHAR(255) NOT NULL,
  `member_tel` VARCHAR(45) NOT NULL,
  `member_emergency_tel` VARCHAR(45) NOT NULL,
  `member_email` VARCHAR(255) NULL DEFAULT NULL,
  `member_img` LONGBLOB NULL DEFAULT NULL,
  `member_register_date` DATETIME NULL DEFAULT NULL,
  `member_last_update` DATETIME NULL DEFAULT NULL,
  `member_job` VARCHAR(255) NULL DEFAULT NULL,
  `member_other` VARCHAR(255) NULL DEFAULT NULL,
  `member_blood` VARCHAR(255) NULL DEFAULT NULL,
  `member_allergy_food` VARCHAR(255) NULL DEFAULT NULL,
  `member_allergy_medicine` VARCHAR(255) NULL DEFAULT NULL,
  `member_disease` VARCHAR(255) NULL DEFAULT NULL,
  `member_emer_name` VARCHAR(255) NULL DEFAULT NULL,
  `member_emer_relationship` VARCHAR(255) NULL DEFAULT NULL,
  `member_enable` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '0 block, 1 active',
  `member_province_id` INT(11) NULL DEFAULT NULL,
  `member_id_card` VARCHAR(20) NOT NULL COMMENT 'เลขที่บัตรประชาชน',
  `member_age` INT(11) NULL DEFAULT NULL COMMENT 'อายุ',
  `member_ordian_number` INT(11) NULL DEFAULT NULL COMMENT 'จำนวนพรรษาที่บวช',
  `member_ordian_date` DATETIME NULL DEFAULT NULL COMMENT 'วันที่บวช',
  `member_postal_code` VARCHAR(5) NOT NULL COMMENT 'รหัสไปรษณีย์',
  PRIMARY KEY (`member_id`),
  UNIQUE INDEX `member_username_UNIQUE` (`member_username` ASC) VISIBLE,
  UNIQUE INDEX `member_id_card_UNIQUE` (`member_id_card` ASC) VISIBLE,
  INDEX `fk_members_titleNames_idx` (`member_title_id` ASC) VISIBLE,
  INDEX `fk_members_gender1_idx` (`member_gender_id` ASC) VISIBLE,
  INDEX `fk_members_roles1_idx` (`member_role_id` ASC) VISIBLE,
  INDEX `fk_members_province_idx` (`member_province_id` ASC) VISIBLE,
  CONSTRAINT `fk_members_gender1`
    FOREIGN KEY (`member_gender_id`)
    REFERENCES `temple`.`gender` (`gender_id`),
  CONSTRAINT `fk_members_province_id`
    FOREIGN KEY (`member_province_id`)
    REFERENCES `temple`.`province` (`province_id`),
  CONSTRAINT `fk_members_roles1`
    FOREIGN KEY (`member_role_id`)
    REFERENCES `temple`.`roles` (`role_id`),
  CONSTRAINT `fk_members_titleNames`
    FOREIGN KEY (`member_title_id`)
    REFERENCES `temple`.`title_names` (`title_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8
COMMENT = 'ตารางสมาชิก';


-- -----------------------------------------------------
-- Table `temple`.`lockers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temple`.`lockers` (
  `locker_id` INT(11) NOT NULL AUTO_INCREMENT,
  `location_id` INT(11) NOT NULL,
  `locker_number` VARCHAR(45) NOT NULL,
  `locker_create_by` INT(11) NOT NULL,
  `locker_create_date` DATETIME NULL DEFAULT NULL,
  `locker_last_update` DATETIME NULL DEFAULT NULL,
  `locker_status` ENUM('0', '1') NOT NULL DEFAULT '0' COMMENT '0 = ว่าง, 1 = กำลังใช้งาน',
  `locker_enable` TINYINT(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`locker_id`),
  INDEX `fk_locker_members1_idx` (`locker_create_by` ASC) VISIBLE,
  INDEX `fk_locker_locations1_idx` (`location_id` ASC) VISIBLE,
  CONSTRAINT `fk_locker_locations1`
    FOREIGN KEY (`location_id`)
    REFERENCES `temple`.`locations` (`location_id`),
  CONSTRAINT `fk_locker_members1`
    FOREIGN KEY (`locker_create_by`)
    REFERENCES `temple`.`members` (`member_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'ตารางตู้เก็บสัมภาระ';


-- -----------------------------------------------------
-- Table `temple`.`baggage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temple`.`baggage` (
  `baggage_id` INT(11) NOT NULL AUTO_INCREMENT,
  `member_id` INT(11) NOT NULL,
  `locker_id` INT(11) NOT NULL,
  `baggage_status` ENUM('0', '1', '2') NOT NULL DEFAULT '0' COMMENT '0 ฝาก, 1 รับคืนแล้ว, 2  ยกเลิก',
  `baggage_create_date` DATETIME NULL DEFAULT NULL,
  `baggage_last_update` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`baggage_id`),
  INDEX `member_id` (`member_id` ASC) VISIBLE,
  INDEX `fk_lockers1` (`locker_id` ASC) VISIBLE,
  CONSTRAINT `fk_lockers1`
    FOREIGN KEY (`locker_id`)
    REFERENCES `temple`.`lockers` (`locker_id`),
  CONSTRAINT `fk_member`
    FOREIGN KEY (`member_id`)
    REFERENCES `temple`.`members` (`member_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'ตารางสัมภาระ';


-- -----------------------------------------------------
-- Table `temple`.`courses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temple`.`courses` (
  `course_id` INT(11) NOT NULL AUTO_INCREMENT,
  `course_no` INT(11) NOT NULL DEFAULT '0',
  `course_name` VARCHAR(255) NOT NULL,
  `course_detail` VARCHAR(255) NOT NULL,
  `course_st_date` DATE NULL DEFAULT NULL,
  `course_end_date` DATE NULL DEFAULT NULL,
  `course_condition_min` INT(3) NULL DEFAULT NULL,
  `course_location_id` INT(11) NOT NULL,
  `course_create_by` INT(11) NOT NULL,
  `course_create_date` DATETIME NULL DEFAULT NULL,
  `course_last_update` DATETIME NULL DEFAULT NULL,
  `course_status` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '0 out time , 1 in time',
  `course_enable` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '0 block, 1 active',
  PRIMARY KEY (`course_id`),
  INDEX `fk_courses_location1_idx` (`course_location_id` ASC) VISIBLE,
  INDEX `fk_courses_members1_idx` (`course_create_by` ASC) VISIBLE,
  CONSTRAINT `fk_courses_location1`
    FOREIGN KEY (`course_location_id`)
    REFERENCES `temple`.`locations` (`location_id`),
  CONSTRAINT `fk_courses_members1`
    FOREIGN KEY (`course_create_by`)
    REFERENCES `temple`.`members` (`member_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 50
DEFAULT CHARACTER SET = utf8
COMMENT = 'ตารางคอร์ส';


-- -----------------------------------------------------
-- Table `temple`.`courses_schedule`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temple`.`courses_schedule` (
  `course_id` INT(11) NOT NULL,
  `course_schedule_date` DATE NOT NULL,
  PRIMARY KEY (`course_id`, `course_schedule_date`),
  INDEX `fk_couse_shedule_courses1_idx` (`course_id` ASC) VISIBLE,
  CONSTRAINT `fk_couse_shedule_courses1`
    FOREIGN KEY (`course_id`)
    REFERENCES `temple`.`courses` (`course_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'ตารางเวลาคอร์สนอกเวลา';


-- -----------------------------------------------------
-- Table `temple`.`courses_teacher`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temple`.`courses_teacher` (
  `course_id` INT(11) NOT NULL,
  `member_id` INT(11) NOT NULL,
  PRIMARY KEY (`course_id`, `member_id`),
  INDEX `fk_courses_has_members_members1_idx` (`member_id` ASC) VISIBLE,
  INDEX `fk_courses_has_members_courses1_idx` (`course_id` ASC) VISIBLE,
  CONSTRAINT `fk_courses_has_members_courses1`
    FOREIGN KEY (`course_id`)
    REFERENCES `temple`.`courses` (`course_id`),
  CONSTRAINT `fk_courses_has_members_members1`
    FOREIGN KEY (`member_id`)
    REFERENCES `temple`.`members` (`member_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'ตารางอาจารย์ผู้สอบกับคอร์สที่สอน';


-- -----------------------------------------------------
-- Table `temple`.`history_dharma`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temple`.`history_dharma` (
  `history_dharma_id` INT(11) NOT NULL AUTO_INCREMENT,
  `history_dharma_desc` VARCHAR(255) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NULL DEFAULT NULL COMMENT 'ชื่อคอร์ส',
  `history_dharma_location` VARCHAR(255) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NULL DEFAULT NULL COMMENT 'สถานที่ปฏิบัติธรรม',
  `history_dharma_member_id` INT(11) NULL DEFAULT NULL COMMENT 'fk ตารางสมาชิก',
  PRIMARY KEY (`history_dharma_id`),
  INDEX `fk_history_dharma_member_id_idx` (`history_dharma_member_id` ASC) VISIBLE,
  CONSTRAINT `fk_history_dharma_member_id`
    FOREIGN KEY (`history_dharma_member_id`)
    REFERENCES `temple`.`members` (`member_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin
COMMENT = 'ตารางการปฏิบัติธรรมที่อื่น';


-- -----------------------------------------------------
-- Table `temple`.`sensations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temple`.`sensations` (
  `sense_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'id ที่เป็น AI',
  `sense_expected` VARCHAR(255) NULL DEFAULT NULL COMMENT 'ความคาดหวัง',
  `sense_experience` VARCHAR(255) NULL DEFAULT NULL COMMENT 'ประสบการณ์ที่ผ่านมา',
  PRIMARY KEY (`sense_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 21
DEFAULT CHARACTER SET = utf8
COMMENT = 'ตารางของความคาดหวัง และประสบการณ์ที่ได้รับ ซึ่งมีการติดต่อกับตาราง Transportation';


-- -----------------------------------------------------
-- Table `temple`.`transportations_time`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temple`.`transportations_time` (
  `tran_time_id` INT(11) NOT NULL AUTO_INCREMENT,
  `tran_time_pickup` DATETIME NOT NULL COMMENT 'เวลารับ',
  `tran_time_send` DATETIME NOT NULL COMMENT 'เวลาส่ง',
  PRIMARY KEY (`tran_time_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 13
DEFAULT CHARACTER SET = utf8
COMMENT = 'ตารางเวลารับ ส่งการเดินทางของวัด';


-- -----------------------------------------------------
-- Table `temple`.`transportations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temple`.`transportations` (
  `tran_id` INT(11) NOT NULL AUTO_INCREMENT,
  `tran_name` VARCHAR(45) NOT NULL,
  `tran_time_id` INT(11) NULL DEFAULT NULL COMMENT 'fk ตารางเวลาการเดินทาง',
  `course_id` INT(11) NULL DEFAULT NULL COMMENT 'fk course, ไม่บังคับ',
  PRIMARY KEY (`tran_id`),
  INDEX `fk_courses_id_idx` (`course_id` ASC) VISIBLE,
  INDEX `fk_tran_time_id_idx` (`tran_time_id` ASC) VISIBLE,
  CONSTRAINT `fk_course_id`
    FOREIGN KEY (`course_id`)
    REFERENCES `temple`.`courses` (`course_id`),
  CONSTRAINT `fk_tran_time_id`
    FOREIGN KEY (`tran_time_id`)
    REFERENCES `temple`.`transportations_time` (`tran_time_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 25
DEFAULT CHARACTER SET = utf8
COMMENT = 'ตารางการเดินทาง';


-- -----------------------------------------------------
-- Table `temple`.`members_has_courses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temple`.`members_has_courses` (
  `members_has_course_id` INT(11) NOT NULL AUTO_INCREMENT,
  `member_id` INT(11) NOT NULL,
  `course_id` INT(11) NOT NULL,
  `sense_id` INT(11) NOT NULL,
  `mhc_status` ENUM('0', '1', '2') NOT NULL DEFAULT '2' COMMENT '0 ยังไม่ผ่าน, 1 ผ่าน, 2กำลังเรียน',
  `register_date` DATETIME NULL DEFAULT NULL,
  `tran_id` INT(11) NULL DEFAULT NULL COMMENT 'fk การเดินทาง',
  PRIMARY KEY (`members_has_course_id`),
  INDEX `fk_members_has_courses_sensations1_idx` (`sense_id` ASC) VISIBLE,
  INDEX `fk_members_has_courses_members1_idx` (`member_id` ASC) VISIBLE,
  INDEX `fk_members_has_courses_courses1_idx` (`course_id` ASC) VISIBLE,
  INDEX `fk_members_has_courses_tran_id_idx` (`tran_id` ASC) VISIBLE,
  CONSTRAINT `fk_members_has_courses_courses1`
    FOREIGN KEY (`course_id`)
    REFERENCES `temple`.`courses` (`course_id`),
  CONSTRAINT `fk_members_has_courses_members1`
    FOREIGN KEY (`member_id`)
    REFERENCES `temple`.`members` (`member_id`),
  CONSTRAINT `fk_members_has_courses_sensations1`
    FOREIGN KEY (`sense_id`)
    REFERENCES `temple`.`sensations` (`sense_id`),
  CONSTRAINT `fk_members_has_courses_tran_id`
    FOREIGN KEY (`tran_id`)
    REFERENCES `temple`.`transportations` (`tran_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 26
DEFAULT CHARACTER SET = utf8
COMMENT = 'ตารางลงทะเบียนเรียน';


-- -----------------------------------------------------
-- Table `temple`.`special_approve`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temple`.`special_approve` (
  `special_approve_id` INT(11) NOT NULL AUTO_INCREMENT,
  `member_id` INT(11) NOT NULL,
  `course_id` INT(11) NOT NULL,
  `sense_id` INT(11) NOT NULL,
  `spa_detail` VARCHAR(255) NULL DEFAULT NULL,
  `spa_status` ENUM('0', '1', '2', '3', '4') NOT NULL DEFAULT '0' COMMENT '0 ไม่Approve, 1 Approve, 2 รอApprove, 3 ยกเลิก , 4 รออนุมัตินอกเวลา',
  `create_date` DATETIME NULL DEFAULT NULL,
  `last_update` DATETIME NULL DEFAULT NULL,
  `tran_id` INT(11) NULL DEFAULT NULL COMMENT 'fk ตารางการเดินทาง',
  PRIMARY KEY (`special_approve_id`),
  INDEX `fk_members_has_courses1_courses1_idx` (`course_id` ASC) VISIBLE,
  INDEX `fk_members_has_courses1_members1_idx` (`member_id` ASC) VISIBLE,
  INDEX `fk_members_has_courses1_sensations1_idx` (`sense_id` ASC) VISIBLE,
  INDEX `fk_tran_id_idx` (`tran_id` ASC) VISIBLE,
  CONSTRAINT `fk_members_has_courses1_courses1`
    FOREIGN KEY (`course_id`)
    REFERENCES `temple`.`courses` (`course_id`),
  CONSTRAINT `fk_members_has_courses1_members1`
    FOREIGN KEY (`member_id`)
    REFERENCES `temple`.`members` (`member_id`),
  CONSTRAINT `fk_members_has_courses1_sensations1`
    FOREIGN KEY (`sense_id`)
    REFERENCES `temple`.`sensations` (`sense_id`),
  CONSTRAINT `fk_tran_id`
    FOREIGN KEY (`tran_id`)
    REFERENCES `temple`.`transportations` (`tran_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 16
DEFAULT CHARACTER SET = utf8
COMMENT = 'ตารางอนุมัติพิเศษ';


-- -----------------------------------------------------
-- Table `temple`.`notifications`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `temple`.`notifications` (
  `notification_id` INT(11) NOT NULL,
  `special_approve_special_approve_id` INT(11) NOT NULL,
  `courses_course_id` INT(11) NOT NULL,
  `members_member_id` INT(11) NOT NULL,
  `notification_status` ENUM('0', '1') NOT NULL DEFAULT '0' COMMENT '0 = ยังไม่อ่าน ,  1  =  อ่านแล้ว\\n',
  `notification_time` DATETIME NOT NULL,
  PRIMARY KEY (`notification_id`),
  INDEX `fk_notifications_special_approve_idx` (`special_approve_special_approve_id` ASC) VISIBLE,
  INDEX `fk_notifications_courses1_idx` (`courses_course_id` ASC) VISIBLE,
  INDEX `fk_notifications_members1_idx` (`members_member_id` ASC) VISIBLE,
  CONSTRAINT `fk_notifications_courses1`
    FOREIGN KEY (`courses_course_id`)
    REFERENCES `temple`.`courses` (`course_id`),
  CONSTRAINT `fk_notifications_members1`
    FOREIGN KEY (`members_member_id`)
    REFERENCES `temple`.`members` (`member_id`),
  CONSTRAINT `fk_notifications_special_approve`
    FOREIGN KEY (`special_approve_special_approve_id`)
    REFERENCES `temple`.`special_approve` (`special_approve_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
ROW_FORMAT = DYNAMIC;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
