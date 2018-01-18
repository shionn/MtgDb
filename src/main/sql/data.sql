
update edition set goldfish_name = mkm_name where code = 'CMD';

update edition set goldfish_name = 'Coldsnap Theme Deck Reprints' where code='CST';
update edition set goldfish_name = 'Magic Player Rewards'         where code='pMPR';
update edition set goldfish_name = 'Arena Promos'                 where code='pARL';
update edition set goldfish_name = 'JSS MSS Promos'               where code='pSUS';

update edition set mkm_name = 'Junior Super Series Promos' where code = 'pSUS';
update edition set mkm_name = 'Player Rewards Promos'      where code = 'pMPR';
update edition set mkm_name = 'Arena League Promos'        where code = 'pARL';
