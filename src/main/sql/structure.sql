
drop table card_lang;
drop table card_price;
drop table card;
drop table edition;
create table edition (
  code                  varchar(7)  NOT NULL,
  mci_code              varchar(7)  NULL,
  name                  varchar(64) NOT NULL,
  release_date          date        NOT NULL,
  goldfish_name         varchar(64) NULL, -- rempli manuellement
  mkm_name              varchar(64) NULL, -- non mis à jour automatiquement pour permettre l'édition à postiory
  mkm_id                int         NULL,
  online_only           boolean     NOT NULL,
  PRIMARY  KEY (code),
  INDEX        name(name)
) DEFAULT CHARSET=utf8;

drop table card_lang;
drop table card_price;
drop table card;
create table card (
  id            varchar(64)   NOT NULL, -- sha1 setCode + cardName + cardImageName
  card          varchar(64)   NOT NULL, -- sha1 du nom
  edition       varchar(7)    NOT NULL,
  number        varchar(4)    NULL,
  mci_number    varchar(4)    NULL,

  name          varchar(256)  NOT NULL,
  text          varchar(1024) NULL,
  type          varchar(64)   NOT NULL,
  mana_cost     varchar(64)   NULL, -- BFM c'est long !
  cmc           int(11),

  flavor        varchar(1024) NULL,

  multiverse_id int(11),
  PRIMARY  KEY   (id),
  INDEX          card(card),
  FULLTEXT INDEX name(name),
  FULLTEXT INDEX text(text),
  FULLTEXT INDEX flavor(flavor),
  CONSTRAINT card_edition FOREIGN KEY (edition) REFERENCES edition(code)
) DEFAULT  CHARSET=utf8;

drop table card_lang;
create table card_lang (
  id            varchar(64)   NOT NULL,
  lang          varchar(2)    NOT NULL,
  multiverse_id int(11),
  name          varchar(128)  NOT NULL,

  PRIMARY  KEY (id, lang),
  CONSTRAINT card_lang FOREIGN KEY (id) REFERENCES card(id)
) DEFAULT  CHARSET=utf8 AUTO_INCREMENT=1;

drop table card_price;
create table card_price (
  id            varchar(64)   NOT NULL,
  source        varchar(32)    NOT NULL,
  link          varchar(128),
  price         decimal(10,2),
  price_date    datetime,
  update_date   datetime,

  PRIMARY  KEY (id, source),
  CONSTRAINT card_price FOREIGN KEY (id) REFERENCES card(id)
) DEFAULT  CHARSET=utf8 AUTO_INCREMENT=1;

-- CREATE TABLE rule (
--   id        INT(11) NOT NULL AUTO_INCREMENT ,
--   card      INT(11) NOT NULL ,
--   created   DATE    NOT NULL ,
--   rule      TEXT    NOT NULL ,
--   PRIMARY KEY (id),
--   CONSTRAINT  ruling_card    FOREIGN KEY (card)    REFERENCES card(id)
-- ) DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;