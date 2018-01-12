
create table edition (
  code                  varchar(4)  NOT NULL,
  magic_cards_info_code varchar(4)  NOT NULL,
  name                  varchar(64) NOT NULL,
  release_date          date        NOT NULL,
  PRIMARY  KEY (code),
  UNIQUE   KEY magic_cards_info_code(magic_cards_info_code)
) DEFAULT  CHARSET=utf8 AUTO_INCREMENT=1;

create table card (
  id            varchar(32)   NOT NULL,
  edition       varchar(4)    NOT NULL,
  number        varchar(4)    NULL,

  name          varchar(128)  NOT NULL,
  text          varchar(1024) NOT NULL,
  flavor        varchar(1024) NOT NULL,

  type          varchar(32)   NOT NULL,

  mana_cost     varchar(32)   NOT NULL,
  cmc           int(11),

  multiverse_id int(11),
  PRIMARY  KEY (id),
  UNIQUE   KEY multiverse_id(multiverse_id),
  CONSTRAINT card_edition FOREIGN KEY (edition) REFERENCES edition(code)
) DEFAULT  CHARSET=utf8 AUTO_INCREMENT=1;

create table card_lang (
  id            varchar(32)   NOT NULL,
  lang          varchar(2)    NOT NULL,
  multiverse_id int(11),
  name          varchar(128)  NOT NULL,

  PRIMARY  KEY (id),
  UNIQUE   KEY multiverse_id(multiverse_id),
  CONSTRAINT card_edition FOREIGN KEY (edition) REFERENCES edition(code)
) DEFAULT  CHARSET=utf8 AUTO_INCREMENT=1;


CREATE TABLE rule (
  id        INT(11) NOT NULL AUTO_INCREMENT ,
  card      INT(11) NOT NULL ,
  created   DATE    NOT NULL ,
  rule      TEXT    NOT NULL ,
  PRIMARY KEY (id),
  CONSTRAINT  ruling_card    FOREIGN KEY (card)    REFERENCES card(id)
) DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;