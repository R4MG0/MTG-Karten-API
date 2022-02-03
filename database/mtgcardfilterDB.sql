-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema mtgcardfilter
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `mtgcardfilter` ;

-- -----------------------------------------------------
-- Schema mtgcardfilter
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mtgcardfilter` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `mtgcardfilter` ;

-- -----------------------------------------------------
-- Table `mtgcardfilter`.`mana`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mtgcardfilter`.`mana` ;

CREATE TABLE IF NOT EXISTS `mtgcardfilter`.`mana` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `color` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mtgcardfilter`.`subtype`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mtgcardfilter`.`subtype` ;

CREATE TABLE IF NOT EXISTS `mtgcardfilter`.`subtype` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mtgcardfilter`.`type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mtgcardfilter`.`type` ;

CREATE TABLE IF NOT EXISTS `mtgcardfilter`.`type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `mtgcardfilter`.`card`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mtgcardfilter`.`card` ;

CREATE TABLE IF NOT EXISTS `mtgcardfilter`.`card` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NULL DEFAULT NULL,
  `Mana_id` INT NOT NULL,
  `type_id` INT NOT NULL,
  `subtype_id` INT NOT NULL,
  PRIMARY KEY (`id`, `Mana_id`, `type_id`, `subtype_id`),
  INDEX `fk_Card_Mana_idx` (`Mana_id` ASC) VISIBLE,
  INDEX `fk_Card_type1_idx` (`type_id` ASC) VISIBLE,
  INDEX `fk_Card_subtype1_idx` (`subtype_id` ASC) VISIBLE,
  CONSTRAINT `fk_Card_Mana`
    FOREIGN KEY (`Mana_id`)
    REFERENCES `mtgcardfilter`.`mana` (`id`),
  CONSTRAINT `fk_Card_subtype1`
    FOREIGN KEY (`subtype_id`)
    REFERENCES `mtgcardfilter`.`subtype` (`id`),
  CONSTRAINT `fk_Card_type1`
    FOREIGN KEY (`type_id`)
    REFERENCES `mtgcardfilter`.`type` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 13
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
