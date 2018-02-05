
drop table if exists card_lang;
drop table if exists card_price;
drop table if exists card_keyword;
drop table if exists card_type;
drop table if exists card_rule;
drop table if exists card_legality;
drop table if exists card;
drop table if exists deck_entry;
drop table if exists edition;
create table edition (
  code                  varchar(8)  NOT NULL,
  mci_code              varchar(7)  NULL,
  name                  varchar(64) NOT NULL,
  release_date          date        NOT NULL,
  goldfish_name         varchar(64) NULL, -- rempli manuellement
  mkm_name              varchar(64) NULL, -- non mis à jour automatiquement pour permettre l'édition à postiory
  mkm_id                int         NULL,
  online_only           boolean     NOT NULL,
  foil                  varchar(32) NOT NULL default 'both',
  PRIMARY  KEY (code),
  INDEX        name(name)
) DEFAULT CHARSET=utf8;

drop table if exists card_lang;
drop table if exists card_price;
drop table if exists card_keyword;
drop table if exists card_type;
drop table if exists card_rule;
drop table if exists card_legality;
drop table if exists card;
drop table if exists deck_entry;
create table card (
  id             varchar(64)   NOT NULL, -- sha1 setCode + cardName + cardImageName
  card           varchar(64)   NOT NULL, -- sha1 du nom
  link_card      varchar(64)   NULL,
  edition        varchar(8)    NOT NULL,
  number         varchar(4)    NULL,
  mci_number     varchar(4)    NULL,
  multiverse_id  int(11),

  name           varchar(256)  NOT NULL,
  text           varchar(1024) NULL,
  flavor         varchar(1024) NULL,
  original_text  varchar(1024) NULL,
  artist         varchar(256)  NULL,
  type           varchar(64)   NOT NULL,
  original_type  varchar(64)   NULL,
  mana_cost      varchar(64)   NULL, -- BFM c'est long !
  cmc            int(11),
  colors         varchar(5)    NULL,
  color_identity varchar(5)    NULL,
  layout         varchar(11)   NOT NULL,
  rarity         varchar(10)   NOT NULL,
  power          varchar(8)    NULL,
  toughness      varchar(8)    NULL,
  loyalty        int(2)        NULL,
  reserved       boolean,


  source         varchar(256)  NULL,

  PRIMARY  KEY   (id),
  INDEX          card(card),
  INDEX          layout(layout),
  INDEX          rarity(rarity),
  INDEX          artist(artist),
  INDEX          colors(colors),
  INDEX          power(power),
  INDEX          toughness(toughness),
  INDEX          loyalty(loyalty),
  FULLTEXT INDEX name(name),
  FULLTEXT INDEX text(text),
  FULLTEXT INDEX flavor(flavor),
  CONSTRAINT card_edition FOREIGN KEY (edition)   REFERENCES edition(code),
  CONSTRAINT link_card    FOREIGN KEY (link_card) REFERENCES card(id)
) DEFAULT  CHARSET=utf8;

drop table if exists card_lang;
create table card_lang (
  id            varchar(64)   NOT NULL,
  lang          varchar(2)    NOT NULL,
  multiverse_id int(11),
  name          varchar(128)  NOT NULL,

  PRIMARY  KEY (id, lang),
  CONSTRAINT card_lang FOREIGN KEY (id) REFERENCES card(id)
) DEFAULT  CHARSET=utf8;

drop table if exists card_type;
CREATE TABLE card_type (
  id        varchar(64) NOT NULL,
  type      varchar(32) NOT NULL,
  value     varchar(32) NOT NULL,
  PRIMARY  KEY (id, type, value),
  CONSTRAINT card_type FOREIGN KEY (id) REFERENCES card(id)
) DEFAULT CHARSET=utf8;

drop table if exists card_price;
create table card_price (
  id            varchar(64)   NOT NULL,
  source        varchar(32)    NOT NULL,
  link          varchar(256),
  price         decimal(10,2),
  price_date    datetime,
  update_date   datetime,
  error         varchar(256),

  PRIMARY  KEY (id, source),
  CONSTRAINT card_price FOREIGN KEY (id)   REFERENCES card(id)
) DEFAULT  CHARSET=utf8;

drop table if exists card_rule;
CREATE TABLE card_rule (
  id        varchar(64) NOT NULL ,
  created   DATE        NOT NULL ,
  rule      TEXT        NOT NULL ,
  CONSTRAINT  ruling_card    FOREIGN KEY (id)    REFERENCES card(id)
) DEFAULT CHARSET=utf8;

drop table if exists card_legality;
CREATE TABLE card_legality (
  id        varchar(64) NOT NULL ,
  format    varchar(32) not null,
  legality  varchar(32) not null,
  primary key (id, format),
  CONSTRAINT  legal_card    FOREIGN KEY (id)    REFERENCES card(id),
  INDEX     legality(legality)
) DEFAULT CHARSET=utf8;

drop table if exists card_keyword;
CREATE TABLE card_keyword (
  keyword varchar(32) not null,
  fr      varchar(32),
  primary key (keyword)
) DEFAULT CHARSET=utf8;

drop table if exists `user`;
drop table if exists deck;
drop table if exists deck_entry;
CREATE TABLE `user` (
  `email`    varchar(128) NOT NULL,
  `password` varchar(256) NOT NULL,
  activated  boolean,
  PRIMARY KEY (`email`)
) DEFAULT CHARSET=utf8;

drop table if exists deck;
drop table if exists deck_entry;
CREATE TABLE IF NOT EXISTS deck (
  id       INT          NOT NULL AUTO_INCREMENT,
  user     varchar(128) NOT NULL,
  name     varchar(128) NOT NULL,
  type     varchar(32)  NOT NULL,
  colors   varchar(5)   NULL,
  datetime created      NOT NULL,
  datetime updated      NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT deck_user FOREIGN KEY (user)    REFERENCES user(email)
) DEFAULT CHARSET=utf8;

drop table if exists deck_entry;
CREATE TABLE IF NOT EXISTS deck_entry (
  id       INT          NOT NULL AUTO_INCREMENT,
  deck     INT          NOT NULL,
  card     varchar(64)  NOT NULL,
  qty      int          NOT NULL,
  foil     boolean      NOT NULL,
  category varchar(32)  NOT NULL,
  tag      varchar(32)  NULL,
  PRIMARY KEY (id),
  CONSTRAINT in_deck   FOREIGN KEY (deck)    REFERENCES deck(id),
  CONSTRAINT card_deck FOREIGN KEY (card)    REFERENCES card(id)
) DEFAULT CHARSET=utf8;

