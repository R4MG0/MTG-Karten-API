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
  `name` VARCHAR(45) NULL,
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
  `Name` VARCHAR(45) NOT NULL,
  `Mana_id` INT NULL,
  `type_id` INT NULL,
  `subtype_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Card_Mana_idx` (`Mana_id` ASC) VISIBLE,
  INDEX `fk_Card_type1_idx` (`type_id` ASC) VISIBLE,
  INDEX `fk_Card_subtype1_idx` (`subtype_id` ASC) VISIBLE,
  CONSTRAINT `fk_Card_Mana`
    FOREIGN KEY (`Mana_id`)
    REFERENCES `mtgcardfilter`.`mana` (`id`)
    ON DELETE SET NULL,
  CONSTRAINT `fk_Card_subtype1`
    FOREIGN KEY (`subtype_id`)
    REFERENCES `mtgcardfilter`.`subtype` (`id`)
    ON DELETE SET NULL,
  CONSTRAINT `fk_Card_type1`
    FOREIGN KEY (`type_id`)
    REFERENCES `mtgcardfilter`.`type` (`id`)
    ON DELETE SET NULL)
ENGINE = InnoDB
AUTO_INCREMENT = 13
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

insert into mana (id, color)
values
    (1,"black"),
    (2, "white"),
    (3, "red"),
    (4, "blue"),
    (5, "green"),
    (6, "colorless"),
    (7, "black and white and colorless"),
    (8, "black and red and colorless"),
    (9, "black and green and colorless"),
    (10, "red and colorless"),
    (11, "black and colorless"),
    (12, "green and colorless"),
    (13, "white and colorless"),
    (14, "red and green"),
    (15, "+1 green or black"),
    (16, "+1 black"),
    (17, "+1 white"),
    (18, "+1 red"),
    (19, "+1 blue"),
    (20, "none");
    
insert into subtype (id, name)
values
    (1, "vampire"),
    (2, "squirrel"),
    (3, "wolf"),
    (4, "human"),
    (5, "none"),
    (6, "elf Druid"),
    (7, "swamp"),
    (8, "plains"),
    (9, "mountain"),
    (10, "Elemental Incarnation");

insert into type (id, name)
values
    (1, "creature"),
    (2, "instant"),
    (3, "sorcery"),
    (4, "artifact"),
    (5, "legendary creature"),
    (6, "enchantment"),
    (7, "basic land"),
    (8, "planeswalker"),
    (9, "artifact land"),
    (10, "token"),
    (11, "token creature");


Insert into card (id,name, mana_id, type_id, subtype_id)
values
    (1,"Cemetry Gatekeeper", 3, 1, 1),
    (2,"Edgar, Charmed Groom", 7, 5, 1),
    (3,"Olivia's Attendats", 10, 1, 1),
    (4,"Sanctify", 13, 3, 5),
    (5,"Bleed Dry", 11, 2, 5),
    (6,"Chitterspitter", 12, 4, 5),
    (7,"Scurry Oak", 12, 1, 2),
    (8,"Squirrel Mob", 12, 1, 2),
    (9,"Sylvan Anthem", 5, 6, 5),
    (10,"Lightning Bolt", 3, 2, 5),
    (11, "Immerwolf", 14, 1, 3),
    (12,"Nightpack Ambusher", 12, 1, 3);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
