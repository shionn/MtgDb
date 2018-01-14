
drop table card_lang;
drop table card;
drop table edition;
create table edition (
  code                  varchar(4)  NOT NULL,
  magic_cards_info_code varchar(6)  NULL,
  name                  varchar(64) NOT NULL,
  release_date          date        NOT NULL,
  PRIMARY  KEY (code),
  UNIQUE   KEY magic_cards_info_code(magic_cards_info_code),
  INDEX        name(name)
) DEFAULT CHARSET=utf8;

drop table card_lang;
drop table card;
create table card (
  id            varchar(64)   NOT NULL,
  edition       varchar(4)    NOT NULL,
  number        varchar(4)    NULL,

  name          varchar(256)  NOT NULL,
  text          varchar(1024) NULL,
  flavor        varchar(1024) NULL,

  type          varchar(64)   NOT NULL,

  mana_cost     varchar(32)   NULL,
  cmc           int(11),

  multiverse_id int(11),
  PRIMARY  KEY   (id),
  UNIQUE   KEY   multiverse_id(multiverse_id),
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
  CONSTRAINT card FOREIGN KEY (id) REFERENCES card(id)
) DEFAULT  CHARSET=utf8 AUTO_INCREMENT=1;

CREATE TABLE rule (
  id        INT(11) NOT NULL AUTO_INCREMENT ,
  card      INT(11) NOT NULL ,
  created   DATE    NOT NULL ,
  rule      TEXT    NOT NULL ,
  PRIMARY KEY (id),
  CONSTRAINT  ruling_card    FOREIGN KEY (card)    REFERENCES card(id)
) DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;