DROP database IF EXISTS `MtgCardFilter`;
create database MtgCardFilter;

use MtgCardFilter;

--
-- Table structure for table `card`
--

DROP TABLE IF EXISTS `card`;
CREATE TABLE `card` (
                        `id` int NOT NULL auto_increment,
                        `Name` varchar(45) DEFAULT NULL,
                        `Mana_id` int NOT NULL,
                        `type_id` int NOT NULL,
                        `subtype_id` int NOT NULL,
                        PRIMARY KEY (`id`,`Mana_id`,`type_id`,`subtype_id`),
                        KEY `fk_Card_Mana_idx` (`Mana_id`),
                        KEY `fk_Card_type1_idx` (`type_id`),
                        KEY `fk_Card_subtype1_idx` (`subtype_id`),
                        CONSTRAINT `fk_Card_Mana` FOREIGN KEY (`Mana_id`) REFERENCES `mana` (`id`),
                        CONSTRAINT `fk_Card_subtype1` FOREIGN KEY (`subtype_id`) REFERENCES `subtype` (`id`),
                        CONSTRAINT `fk_Card_type1` FOREIGN KEY (`type_id`) REFERENCES `type` (`id`)
);

Insert into card (name, Mana_id, type_id, subtype_id)
values
    ("Cemetry Gatekeeper", 3, 1, 1),
    ("Edgar, Charmed Groom", 7, 5, 1),
    ("Olivia's Attendats", 10, 1, 1),
    ("Sanctify", 13, 3, 5),
    ("Bleed Dry", 11, 2, 5),
    ("Chitterspitter", 12, 4, 5),
    ("Scurry Oak", 12, 1, 2),
    ("Squirrel Mob", 12, 1, 2),
    ("Sylvan Anthem", 5, 6, 5),
    ("Lightning Bolt", 3, 2, 5),
    ("Immerwolf", 14, 1, 3),
    ("Nightpack Ambusher", 12, 1, 3);


--
-- Table structure for table `mana`
--

DROP TABLE IF EXISTS `mana`;
CREATE TABLE `mana` (
                        `id` int NOT NULL,
                        `color` varchar(45) DEFAULT NULL,
                        PRIMARY KEY (`id`)
);

Insert into mana (id, color)
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
    (14, "red and green");

Select * from mana;
--
-- Table structure for table `subtype`
--

DROP TABLE IF EXISTS `subtype`;
CREATE TABLE `subtype` (
                           `id` int NOT NULL,
                           `name` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ;

insert into subtype (id, name)
values
    (1, "vampire"),
    (2, "squirrel"),
    (3, "wolf"),
    (4, "human"),
    (5, "none");
--
-- Table structure for table `type`
--

DROP TABLE IF EXISTS `type`;
CREATE TABLE `type` (
                        `id` int NOT NULL,
                        `name` varchar(45) DEFAULT NULL,
                        PRIMARY KEY (`id`)
);

insert into type (id, name)
values
    (1, "creature"),
    (2, "instant"),
    (3, "sorcery"),
    (4, "artifact"),
    (5, "legendary creature"),
    (6, "enchantment");

select * from card;
