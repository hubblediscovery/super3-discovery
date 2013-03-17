--<ScriptOptions statementTerminator=";"/>

ALTER TABLE `hubble_users`.`FacebookUserDemographics` DROP PRIMARY KEY;

ALTER TABLE `hubble_users`.`FacebookEntities` DROP PRIMARY KEY;

ALTER TABLE `hubble_users`.`UserFriends` DROP PRIMARY KEY;

ALTER TABLE `hubble_users`.`HubbleUser` DROP PRIMARY KEY;

ALTER TABLE `hubble_users`.`FbUserLikedEntities` DROP PRIMARY KEY;

ALTER TABLE `hubble_users`.`FacebookUserInterests` DROP PRIMARY KEY;

ALTER TABLE `hubble_users`.`FbUserLikedEntities` DROP INDEX `hubble_users`.`fk_2_idx`;

ALTER TABLE `hubble_users`.`FacebookUserInterests` DROP INDEX `hubble_users`.`fk_UserLikes_User_idx`;

ALTER TABLE `hubble_users`.`FacebookEntities` DROP INDEX `hubble_users`.`Category`;

ALTER TABLE `hubble_users`.`UserFriends` DROP INDEX `hubble_users`.`user_id_idx`;

ALTER TABLE `hubble_users`.`FacebookUserDemographics` DROP INDEX `hubble_users`.`fk_UserDemographics_User1_idx`;

DROP TABLE `hubble_users`.`HubbleUser`;

DROP TABLE `hubble_users`.`FacebookUserInterests`;

DROP TABLE `hubble_users`.`FacebookUserDemographics`;

DROP TABLE `hubble_users`.`UserFriends`;

DROP TABLE `hubble_users`.`FbUserLikedEntities`;

DROP TABLE `hubble_users`.`FacebookEntities`;

CREATE TABLE `hubble_users`.`HubbleUser` (
	`AppId` VARCHAR(45) NOT NULL,
	`Name` VARCHAR(100) NOT NULL,
	`Gender` VARCHAR(45),
	`Birthday` DATETIME,
	`Hometown` VARCHAR(255),
	PRIMARY KEY (`AppId`)
) ENGINE=InnoDB;

CREATE TABLE `hubble_users`.`FacebookUserInterests` (
	`User_AppId` VARCHAR(45) NOT NULL,
	`Quotes` VARCHAR(500),
	`Sports` VARCHAR(500),
	`Interests` TEXT,
	PRIMARY KEY (`User_AppId`)
) ENGINE=InnoDB;

CREATE TABLE `hubble_users`.`FacebookUserDemographics` (
	`facebookId` VARCHAR(45) NOT NULL,
	`User_AppId` VARCHAR(45) NOT NULL,
	`Username` VARCHAR(100),
	`InterestedIn` VARCHAR(45),
	`Locale` VARCHAR(45),
	`Languages` VARCHAR(255),
	`CurrentLocation` VARCHAR(255),
	`MeetingFor` VARCHAR(255),
	`Political` VARCHAR(255),
	`RelationshipStatus` VARCHAR(45),
	`Religion` VARCHAR(255),
	`SignificantOther` VARCHAR(255),
	`TimeZone` VARCHAR(45),
	`Website` VARCHAR(200),
	`Bio` TEXT,
	`Email` VARCHAR(100),
	`ThirdPartyId` VARCHAR(45),
	`UpdatedTime` DATETIME,
	`Verified` BIT,
	`Work` BLOB,
	PRIMARY KEY (`facebookId`,`User_AppId`)
) ENGINE=InnoDB;

CREATE TABLE `hubble_users`.`UserFriends` (
	`User_Id` VARCHAR(45) NOT NULL,
	`Friend_Id` VARCHAR(45) NOT NULL,
	PRIMARY KEY (`Friend_Id`,`User_Id`)
) ENGINE=InnoDB;

CREATE TABLE `hubble_users`.`FbUserLikedEntities` (
	`FbEntityId` VARCHAR(45) NOT NULL,
	`AppId` VARCHAR(45) NOT NULL,
	PRIMARY KEY (`FbEntityId`,`AppId`)
) ENGINE=InnoDB;

CREATE TABLE `hubble_users`.`FacebookEntities` (
	`Id` VARCHAR(45) NOT NULL,
	`Name` VARCHAR(100),
	`Category` VARCHAR(100),
	`CreatedTime` VARCHAR(45),
	`Keywords` TEXT,
	`Description` TEXT,
	PRIMARY KEY (`Id`)
) ENGINE=InnoDB;

CREATE INDEX `fk_2_idx` ON `hubble_users`.`FbUserLikedEntities` (`AppId` ASC);

CREATE INDEX `fk_UserLikes_User_idx` ON `hubble_users`.`FacebookUserInterests` (`User_AppId` ASC);

CREATE INDEX `Category` ON `hubble_users`.`FacebookEntities` (`Category` ASC);

CREATE INDEX `user_id_idx` ON `hubble_users`.`UserFriends` (`User_Id` ASC);

CREATE INDEX `fk_UserDemographics_User1_idx` ON `hubble_users`.`FacebookUserDemographics` (`User_AppId` ASC);

