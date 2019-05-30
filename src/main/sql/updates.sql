

-- drop d'edition
-- VAN, DD3_GVL
SELECT * from deck_entry WHERE card IN (SELECT id FROM card WHERE edition = 'DD3_GVL');
DELETE FROM card_assistance WHERE id IN (SELECT id FROM card WHERE edition = 'DD3_GVL');
DELETE FROM card_lang WHERE id IN (SELECT id FROM card WHERE edition = 'DD3_GVL');
DELETE FROM card_legality WHERE id IN (SELECT id FROM card WHERE edition = 'DD3_GVL');
DELETE FROM card_price WHERE id IN (SELECT id FROM card WHERE edition = 'DD3_GVL');
DELETE FROM card_rule WHERE id IN (SELECT id FROM card WHERE edition = 'DD3_GVL');
DELETE FROM card_type WHERE id IN (SELECT id FROM card WHERE edition = 'DD3_GVL');
DELETE FROM card WHERE edition = 'DD3_GVL';
DELETE FROM edition WHERE code = 'DD3_GVL';


-- ajout des admin
ALTER TABLE `user` ADD `admin` TINYINT(1) NOT NULL DEFAULT '0' AFTER `activated`;

-- amelioration recuperation img
ALTER TABLE card_lang DROP multiverse_id;
ALTER TABLE `card` ADD `scryfall_id` VARCHAR(64) NULL DEFAULT NULL AFTER `link_card`;

-- refonte page editions
ALTER TABLE `edition` ADD `block` VARCHAR(64) NULL DEFAULT NULL AFTER `name`;
ALTER TABLE `edition` ADD `parent_code` VARCHAR(8) NULL DEFAULT NULL AFTER `block`;
ALTER TABLE `card` CHANGE `number` `number` VARCHAR(9) NULL DEFAULT NULL;

-- mise à jour migration vers mtgjson2
-- La colonne foil de edition semble inutile,
-- La colonne icon, mci_code egalement (remplacé par keyrune)
ALTER TABLE `edition` ADD `keyrune_code` VARCHAR(8) NULL DEFAULT NULL AFTER `mci_code`;
ALTER TABLE `edition` ADD `type` VARCHAR(15) NULL DEFAULT NULL AFTER `name`;
ALTER TABLE `edition` ADD `update_date` TIMESTAMP NULL DEFAULT NULL AFTER `foil`;
ALTER TABLE edition DROP foil;
ALTER TABLE edition DROP icon;
ALTER TABLE edition DROP mci_code;

-- Les colonne mci_number, multiverse_id, source ne sont pas utilisé
ALTER TABLE `card` ADD `update_date` TIMESTAMP NULL DEFAULT NULL AFTER `type`;
ALTER TABLE `card` CHANGE `loyalty` `loyalty` VARCHAR(5) NULL DEFAULT NULL;
ALTER TABLE `card` ADD `foil` VARCHAR(8) NULL DEFAULT NULL AFTER `number`;
ALTER TABLE `card` ADD `side` VARCHAR(1) NULL DEFAULT NULL AFTER `number`;
ALTER TABLE card DROP mci_number;
ALTER TABLE card DROP multiverse_id;
ALTER TABLE card DROP source;

-- dans card_lang (multiverse_id est inutil)

-- mise à jour ancienne
ALTER TABLE `edition` ADD `icon` VARCHAR(8) NULL DEFAULT NULL AFTER `mci_code`;

