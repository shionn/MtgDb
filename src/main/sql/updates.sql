
-- mise à jour migration vers mtgjson2
-- La colonne foil de edition semble inutile,
-- La colonne icon, mci_code egalement (remplacé par keyrune)
ALTER TABLE `edition` ADD `keyrune_code` VARCHAR(8) NULL DEFAULT NULL AFTER `mci_code`;
ALTER TABLE `edition` ADD `update_date` TIMESTAMP NULL DEFAULT NULL AFTER `foil`;
ALTER TABLE edition DROP foil;

-- Les colonne mci_number, multiverse_id, source ne sont pas utilisé
ALTER TABLE `card` ADD `update_date` TIMESTAMP NULL DEFAULT NULL AFTER `type`;
ALTER TABLE `card` CHANGE `loyalty` `loyalty` VARCHAR(5) NULL DEFAULT NULL;
ALTER TABLE `card` ADD `foil` VARCHAR(8) NULL DEFAULT NULL AFTER `number`;
ALTER TABLE card DROP mci_number;
ALTER TABLE card DROP multiverse_id;
ALTER TABLE card DROP source;

-- dans card_lang (multiverse_id est inutil)

-- mise à jour ancienne
ALTER TABLE `edition` ADD `icon` VARCHAR(8) NULL DEFAULT NULL AFTER `mci_code`;

