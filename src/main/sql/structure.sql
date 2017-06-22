
create table card (
  id       int(11)       NOT NULL AUTO_INCREMENT,
  ref      varchar(128)  NOT NULL,
  PRIMARY  KEY (id),
  UNIQUE   KEY ref  (ref)
) DEFAULT  CHARSET=utf8 AUTO_INCREMENT=1;

create table edition (
  id            int(11)     NOT NULL AUTO_INCREMENT,
  code          varchar(8)  NOT NULL,
  name          varchar(64) NOT NULL,
  release_date  date        NOT NULL,
  PRIMARY  KEY (id),
  UNIQUE   KEY code  (code)
) DEFAULT  CHARSET=utf8 AUTO_INCREMENT=1;

create table declinaison (
  id       int(11) NOT NULL AUTO_INCREMENT,
  card     int(11) NOT NULL,
  edition  int(11) NOT NULL,
  rarity   varchar(16) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT  edition FOREIGN KEY (edition) REFERENCES edition(id),
  CONSTRAINT  card    FOREIGN KEY (card)    REFERENCES card(id),
  UNIQUE KEY  declinaison (edition, card)
) DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;

