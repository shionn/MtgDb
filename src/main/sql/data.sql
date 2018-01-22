
update edition set goldfish_name = mkm_name where code = 'CMD';

update edition set goldfish_name = 'Conspiracy'                      where code='CNS';
update edition set goldfish_name = 'Coldsnap Theme Deck Reprints'    where code='CST';
update edition set goldfish_name = 'Amonkhet Invocations'            where code='MPS_AKH';
update edition set goldfish_name = 'Arena Promos'                    where code='pARL';
update edition set goldfish_name = 'FNM Promos'                      where code='pFNM';
update edition set goldfish_name = 'Gateway Promos'                  where code='pGTW';
update edition set goldfish_name = 'Judge Promos'                    where code='pJGP';
update edition set goldfish_name = 'Media Promos'                    where code='pMEI'; --Media+Promos
update edition set goldfish_name = 'Game+Day+Promos'                 where code='pMGD';
update edition set goldfish_name = 'Magic Player Rewards'            where code='pMPR';
update edition set goldfish_name = 'Prerelease Cards'                where code='pPRE';
update edition set goldfish_name = 'JSS MSS Promos'                  where code='pSUS';
update edition set goldfish_name = 'WPN Promos|Gateway Promos'       where code='pWPN';
update edition set goldfish_name = 'Unique and Miscellaneous Promos' where code='pWOS';



update edition set mkm_name = 'Arena League Promos'         where code = 'pARL';
update edition set mkm_name = 'Friday Night Magic Promos'   where code = 'pFNM';
update edition set mkm_name = 'Gateway Promos'              where code = 'pGTW';
update edition set mkm_name = 'Judge Rewards Promos'        where code = 'pJGP';
update edition set mkm_name = 'Buy a Box Promos'            where code = 'pMEI'; -- Resale Promos, IDW Promos
update edition set mkm_name = 'Game Day Promos'             where code = 'pMGD';
update edition set mkm_name = 'Player Rewards Promos'       where code = 'pMPR';
update edition set mkm_name = 'Junior Super Series Promos'  where code = 'pSUS';
update edition set mkm_name = 'Game Day Promos'             where code = 'pWPN'; -- Gateway Promos
update edition set mkm_name = 'DCI Promos'                  where code = 'pWOS'; -- pas sur




update edition set foil = 'nofoil' where code in('PC2', 'HOP');
update edition set foil = 'nofoil' where code = 'POR';
update edition set foil = 'nofoil' where code in('2ED', '4ED', '5ED', '6ED');
update edition set foil = 'nofoil' where code in('DDQ');

delete from card_keyword;
INSERT INTO card_keyword (keyword, fr ) values ('Afflict',                  'affliction');
INSERT INTO card_keyword (keyword, fr ) values ('Ascend',                   'ascension');
INSERT INTO card_keyword (keyword, fr ) values ('Awaken',                   'eveil');
INSERT INTO card_keyword (keyword, fr ) values ('Battalion',                'bataillon');
INSERT INTO card_keyword (keyword, fr ) values ('Battle cry',               'cri de guerre');
INSERT INTO card_keyword (keyword, fr ) values ('Bestow',                   'grâce');
INSERT INTO card_keyword (keyword, fr ) values ('Bloodrush',                'coup de sang');
INSERT INTO card_keyword (keyword, fr ) values ('Bloodthirst',              'soif de sang');
INSERT INTO card_keyword (keyword, fr ) values ('Bolster',                  'renforcement');
INSERT INTO card_keyword (keyword, fr ) values ('Changeling',               'changelin');
INSERT INTO card_keyword (keyword, fr ) values ('Clash',                    'confrontez');
INSERT INTO card_keyword (keyword, fr ) values ('Constellation',            null);
INSERT INTO card_keyword (keyword, fr ) values ('Convoke',                  'convocation');
INSERT INTO card_keyword (keyword, fr ) values ('Converge',                 'convergence');
INSERT INTO card_keyword (keyword, fr ) values ('Crew',                     null);
INSERT INTO card_keyword (keyword, fr ) values ('Dash',                     'précipitation');
INSERT INTO card_keyword (keyword, fr ) values ('Defender',                 'defenseur');
INSERT INTO card_keyword (keyword, fr ) values ('Double strike',            'double initiative');
INSERT INTO card_keyword (keyword, fr ) values ('Devoid',                   'carence');
INSERT INTO card_keyword (keyword, fr ) values ('Devotion',                 'devotion');
INSERT INTO card_keyword (keyword, fr ) values ('Delirium',                 'delire');
INSERT INTO card_keyword (keyword, fr ) values ('Delve',                    'fouille');
INSERT INTO card_keyword (keyword, fr ) values ('Dethrone',                 null);
INSERT INTO card_keyword (keyword, fr ) values ('Deathtouch',               null);
INSERT INTO card_keyword (keyword, fr ) values ('Domain',                   null);
INSERT INTO card_keyword (keyword, fr ) values ('Echo',                     null);
INSERT INTO card_keyword (keyword, fr ) values ('Embalm',                   'embaumement');
INSERT INTO card_keyword (keyword, fr ) values ('Enrage',                   'rage');
INSERT INTO card_keyword (keyword, fr ) values ('Evoke',                    null);
INSERT INTO card_keyword (keyword, fr ) values ('Escalate',                 'intensification');
INSERT INTO card_keyword (keyword, fr ) values ('Eternalize',               'eternalisation');
INSERT INTO card_keyword (keyword, fr ) values ('Exalted',                  'exaltation');
INSERT INTO card_keyword (keyword, fr ) values ('Exert',                    'surmener');
INSERT INTO card_keyword (keyword, fr ) values ('Exploit',                  'exploitation');
INSERT INTO card_keyword (keyword, fr ) values ('Explores',                 'explore');
INSERT INTO card_keyword (keyword, fr ) values ('Fabricate',                'fabrication');
INSERT INTO card_keyword (keyword, fr ) values ('Fear',                     'peur');
INSERT INTO card_keyword (keyword, fr ) values ('Ferocious',                'feocite');
INSERT INTO card_keyword (keyword, fr ) values ('First strike',            'initiative');
INSERT INTO card_keyword (keyword, fr ) values ('Flash',                    'flash');
INSERT INTO card_keyword (keyword, fr ) values ('Flying',                   'vol');
INSERT INTO card_keyword (keyword, fr ) values ('Formidable',               'redoutable');
INSERT INTO card_keyword (keyword, fr ) values ('Forestwalk',               null);
INSERT INTO card_keyword (keyword, fr ) values ('Haste',                    'celerite');
INSERT INTO card_keyword (keyword, fr ) values ('Heroic',                   'heroique');
INSERT INTO card_keyword (keyword, fr ) values ('Hexproof',                 'defense talismanique');
INSERT INTO card_keyword (keyword, fr ) values ('Islandwalk',               'traversee des iles');
INSERT INTO card_keyword (keyword, fr ) values ('Imprint',                  'empreinte');
INSERT INTO card_keyword (keyword, fr ) values ('Improvise',                'improvisation');
INSERT INTO card_keyword (keyword, fr ) values ('Indestructible',           'indestructible');
INSERT INTO card_keyword (keyword, fr ) values ('Ingest',                   'ingestion');
INSERT INTO card_keyword (keyword, fr ) values ('Intimidate',               null);
INSERT INTO card_keyword (keyword, fr ) values ('Investigate',              'enqueter');
INSERT INTO card_keyword (keyword, fr ) values ('Kicker',                   'kick');
INSERT INTO card_keyword (keyword, fr ) values ('Landfall',                 'toucheterre');
INSERT INTO card_keyword (keyword, fr ) values ('Lifelink',                 'lien de vie');
INSERT INTO card_keyword (keyword, fr ) values ('Manifest',                 'manifestez');
INSERT INTO card_keyword (keyword, fr ) values ('Madness',                  'folie');
INSERT INTO card_keyword (keyword, fr ) values ('Megamorph',                'megamue');
INSERT INTO card_keyword (keyword, fr ) values ('Meld',                     'assimilisation');
INSERT INTO card_keyword (keyword, fr ) values ('Melee',                    null);
INSERT INTO card_keyword (keyword, fr ) values ('Menace',                   'menace');
INSERT INTO card_keyword (keyword, fr ) values ('Metalcraft',               'art des metaux');
INSERT INTO card_keyword (keyword, fr ) values ('Miracle',                  'miracle');
INSERT INTO card_keyword (keyword, fr ) values ('Monstrosity',              'monstruosite');
INSERT INTO card_keyword (keyword, fr ) values ('Morph',                    'mue');
INSERT INTO card_keyword (keyword, fr ) values ('Mountainwalk',             null);
INSERT INTO card_keyword (keyword, fr ) values ('Multikicker',              null);
INSERT INTO card_keyword (keyword, fr ) values ('Outlast',                  'resilience');
INSERT INTO card_keyword (keyword, fr ) values ('Parley',                   null);
INSERT INTO card_keyword (keyword, fr ) values ('Persist',                  null);
INSERT INTO card_keyword (keyword, fr ) values ('Plainswalk',               null);
INSERT INTO card_keyword (keyword, fr ) values ('Protection from creatures','protection contre les creatures');
INSERT INTO card_keyword (keyword, fr ) values ('Prowess',                  'prouesse');
INSERT INTO card_keyword (keyword, fr ) values ('Raid',                     'saccage');
INSERT INTO card_keyword (keyword, fr ) values ('Rally',                    'ralliement');
INSERT INTO card_keyword (keyword, fr ) values ('Reach',                    'portee');
INSERT INTO card_keyword (keyword, fr ) values ('Renown',                   'reputation');
INSERT INTO card_keyword (keyword, fr ) values ('Revolt',                   'revolte');
INSERT INTO card_keyword (keyword, fr ) values ('Surge',                    'deferlement');
INSERT INTO card_keyword (keyword, fr ) values ('Shadow',                   null);
INSERT INTO card_keyword (keyword, fr ) values ('Shroud',                   'linceul');
INSERT INTO card_keyword (keyword, fr ) values ('Skulk',                    'furtivite');
INSERT INTO card_keyword (keyword, fr ) values ('Snow-covered forestwalk',  null);
INSERT INTO card_keyword (keyword, fr ) values ('Soulbond',                 'association d''ames');
INSERT INTO card_keyword (keyword, fr ) values ('Spell mastery',            'maitrise de sort');
INSERT INTO card_keyword (keyword, fr ) values ('Strive',                   'obstination');
INSERT INTO card_keyword (keyword, fr ) values ('Suspend',                  'suspension');
INSERT INTO card_keyword (keyword, fr ) values ('Swampwalk',                null);
INSERT INTO card_keyword (keyword, fr ) values ('Trample',                  'pietinement');
INSERT INTO card_keyword (keyword, fr ) values ('Undying',                  null);
INSERT INTO card_keyword (keyword, fr ) values ('Unearth',                  null);
INSERT INTO card_keyword (keyword, fr ) values ('Vigilance',                null);
