
drop table card_lang;
drop table card_price;
drop table card;
drop table edition;
create table edition (
  code                  varchar(8)  NOT NULL,
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
  CONSTRAINT card_edition FOREIGN KEY (edition)   REFERENCES edition(code)
  CONSTRAINT link_card    FOREIGN KEY (link_card) REFERENCES card(id)
) DEFAULT  CHARSET=utf8;

drop table card_lang;
create table card_lang (
  id            varchar(64)   NOT NULL,
  lang          varchar(2)    NOT NULL,
  multiverse_id int(11),
  name          varchar(128)  NOT NULL,

  PRIMARY  KEY (id, lang),
  CONSTRAINT card_lang FOREIGN KEY (id) REFERENCES card(id)
) DEFAULT  CHARSET=utf8;

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
) DEFAULT  CHARSET=utf8;

drop table card_rule;
CREATE TABLE card_rule (
  card      varchar(64) NOT NULL ,
  created   DATE        NOT NULL ,
  rule      TEXT        NOT NULL ,
  CONSTRAINT  ruling_card    FOREIGN KEY (card)    REFERENCES card(id)
) DEFAULT CHARSET=utf8;