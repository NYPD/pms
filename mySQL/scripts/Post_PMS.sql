ALTER TABLE `pms`.`campaignreward` 
CHANGE COLUMN `StartTime` `StartTime` TIMESTAMP NOT NULL ,
CHANGE COLUMN `EndTime` `EndTime` TIMESTAMP NOT NULL ;

ALTER TABLE `pms`.`event` 
CHANGE COLUMN `StartTime` `StartTime` TIMESTAMP NOT NULL ;


ALTER TABLE pms.campaignreward ALTER COLUMN StartTime DROP DEFAULT;
ALTER TABLE pms.campaignreward ALTER COLUMN EndTime DROP DEFAULT;

ALTER TABLE pms.event ALTER COLUMN Time DROP DEFAULT;

INSERT INTO `pms`.`allowedclan` (`ClanId`, `ClanName`) VALUES ('1000007315', 'NTR');
INSERT INTO `pms`.`allowedclan` (`ClanId`, `ClanName`) VALUES ('1000009754', 'PLSGO');

INSERT INTO `pms`.`localclansetting` (`ClanId`) VALUES ('1000007315');
INSERT INTO `pms`.`localclansetting` (`ClanId`) VALUES ('1000009754');

INSERT INTO `pms`.`role` (`RoleId`, `RoleName`) VALUES ('1', 'User');
INSERT INTO `pms`.`role` (`RoleId`, `RoleName`) VALUES ('2', 'Clan Admin');
INSERT INTO `pms`.`role` (`RoleId`, `RoleName`) VALUES ('3', 'System Admin');


INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Abbey');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Airfield');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Artic Region');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Cliff');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Dragon Ridge');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('El Halluf');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Ensk');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Erlenburg');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Fiery Salient');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Fisherman''s Bay');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Fjords');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Hidden Village');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Highway');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Himmelsdorf');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Karelia');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Kharkov');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Komarin');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Lakeville');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Live Oaks');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Malinovka');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Mines');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Mountain Pass');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Murovanka');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Northwest');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Pearl River');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Province');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Redshire');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Ruinburg');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Sand River');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Serene Coast');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Serverogorsk');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Siegfried Line');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('South Coast');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Steppes');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Swamp');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Tundra');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Westfield');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Widepark');
INSERT INTO `pms`.`mapname` (`Name`) VALUES ('Windstorm');

INSERT INTO `pms`.`newscarousel` (`ClanId`, `AccountId`, `Nickname`, `Title`, `LargeBannerUrl`, `MediumBannerUrl`, `SmallBannerUrl`, `IconImageUrl`, `Global`, `Active`) 
								VALUES ('1', '1', 'K-on', 'K-on Best Zaz', 'http://i.imgur.com/HmKJT70.png', 'http://i.imgur.com/OintRbj.png', 'http://i.imgur.com/ag38Ryi.png', 'http://i.imgur.com/5pQYTHx.jpg', '1', '1');

INSERT INTO `pms`.`userrole` (`AccountId`, `RoleId`, `Nickname`) VALUES ('1001167560', '3', 'NYPD');


