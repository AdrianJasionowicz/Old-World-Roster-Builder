SELECT roster_begin_army();

INSERT INTO staging_unit VALUES
('Bloodthirster', 350, 1, 'LORDS', 8, 10, 0, 6, 6, 6, 9, 7, 9, 4, 0),
('Lord of Change', 425, 1, 'LORDS', 6, 5, 5, 5, 6, 6, 4, 5, 9, 0, 0),
('Great Unclean One', 350, 1, 'LORDS', 4, 6, 0, 6, 7, 8, 4, 5, 9, 0, 0),
('Keeper of Secrets', 330, 1, 'LORDS', 10, 9, 0, 6, 6, 6, 10, 6, 9, 0, 0),
('Daemon Prince', 220, 1, 'LORDS', 6, 7, 0, 5, 5, 5, 6, 5, 9, 0, 0),
('Herald of Khorne', 110, 1, 'HERO', 5, 7, 0, 5, 5, 2, 6, 3, 8, 0, 0),
('Herald of Tzeentch', 100, 1, 'HERO', 6, 3, 3, 4, 4, 2, 3, 2, 8, 0, 0),
('Herald of Nurgle', 90, 1, 'HERO', 4, 5, 0, 4, 5, 2, 3, 2, 8, 0, 0),
('Herald of Slaanesh', 90, 1, 'HERO', 6, 6, 0, 4, 4, 2, 7, 3, 8, 0, 0),
('Bloodletters', 14, 10, 'CORE', 5, 5, 0, 4, 3, 1, 4, 1, 7, 0, 0),
('Pink Horrors', 12, 10, 'CORE', 6, 2, 3, 3, 3, 1, 3, 1, 7, 0, 0),
('Plaguebearers', 14, 10, 'CORE', 4, 3, 0, 3, 4, 1, 2, 1, 7, 0, 0),
('Daemonettes', 13, 10, 'CORE', 6, 5, 0, 3, 3, 1, 5, 2, 7, 0, 0),
('Nurglings', 35, 2, 'CORE', 4, 2, 0, 2, 2, 3, 2, 3, 10, 0, 0),
('Flesh Hounds', 16, 5, 'CORE', 7, 5, 0, 4, 4, 2, 3, 2, 6, 0, 0),
('Bloodcrushers', 50, 3, 'SPECIAL', 7, 5, 0, 5, 4, 3, 4, 2, 7, 4, 0),
('Flamers of Tzeentch', 40, 3, 'SPECIAL', 6, 2, 4, 4, 4, 3, 3, 2, 7, 0, 0),
('Screamers', 30, 3, 'SPECIAL', 1, 3, 0, 4, 4, 1, 4, 1, 7, 0, 0),
('Plague Drones', 45, 3, 'SPECIAL', 1, 3, 0, 4, 5, 3, 2, 3, 7, 0, 0),
('Seekers of Slaanesh', 26, 5, 'SPECIAL', 10, 5, 0, 3, 3, 1, 5, 2, 7, 0, 0),
('Fiends of Slaanesh', 50, 1, 'SPECIAL', 8, 4, 0, 4, 4, 3, 6, 4, 7, 0, 0),
('Beasts of Nurgle', 55, 1, 'SPECIAL', 3, 3, 0, 3, 5, 4, 2, 0, 6, 0, 0),
('Seeker Chariot', 80, 1, 'SPECIAL', 0, 0, 0, 4, 4, 4, 0, 0, 0, 5, 0),
('Burning Chariot', 140, 1, 'SPECIAL', 0, 0, 0, 5, 5, 4, 0, 0, 0, 0, 0),
('Skull Cannon', 135, 1, 'RARE', 0, 0, 0, 5, 5, 4, 0, 0, 0, 4, 0),
('Soul Grinder', 190, 1, 'RARE', 6, 4, 3, 6, 7, 5, 3, 4, 9, 4, 0),
('Hellflayer', 125, 1, 'RARE', 0, 0, 0, 5, 5, 4, 0, 0, 0, 5, 0),
('Exalted Flamer', 60, 1, 'RARE', 6, 2, 4, 4, 4, 4, 3, 2, 7, 0, 0);

INSERT INTO staging_unit_upgrade
SELECT name, 'Daemonic', 'FREE_UPGRADE', 0, '5+ ward save, Fear, Immune to Psychology'
FROM staging_unit
WHERE name NOT IN ('Seeker Chariot', 'Burning Chariot', 'Skull Cannon', 'Hellflayer');

INSERT INTO staging_unit_upgrade VALUES
('Bloodthirster', 'Flying', 'FREE_UPGRADE', 0, 'Fly, Terror'),
('Bloodthirster', 'Great weapon', 'FREE_UPGRADE', 0, '+2 Strength, always strikes last'),
('Lord of Change', 'Flying', 'FREE_UPGRADE', 0, 'Fly, Terror'),
('Lord of Change', 'Level 3 Wizard', 'FREE_UPGRADE', 0, 'Level 3 Wizard'),
('Lord of Change', 'Level 4 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 4 Wizard'),
('Great Unclean One', 'Terror', 'FREE_UPGRADE', 0, 'Causes Terror'),
('Great Unclean One', 'Level 1 Wizard', 'FREE_UPGRADE', 0, 'Level 1 Wizard'),
('Great Unclean One', 'Level 2 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 2 Wizard'),
('Great Unclean One', 'Level 3 Wizard', 'UNIT_EQUIPMENT', 70, 'Upgrade to a Level 3 Wizard'),
('Great Unclean One', 'Level 4 Wizard', 'UNIT_EQUIPMENT', 105, 'Upgrade to a Level 4 Wizard'),
('Keeper of Secrets', 'Terror', 'FREE_UPGRADE', 0, 'Causes Terror'),
('Keeper of Secrets', 'Level 1 Wizard', 'FREE_UPGRADE', 0, 'Level 1 Wizard'),
('Keeper of Secrets', 'Level 2 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 2 Wizard'),
('Keeper of Secrets', 'Level 3 Wizard', 'UNIT_EQUIPMENT', 70, 'Upgrade to a Level 3 Wizard'),
('Keeper of Secrets', 'Level 4 Wizard', 'UNIT_EQUIPMENT', 105, 'Upgrade to a Level 4 Wizard'),
('Daemon Prince', 'Flying', 'UNIT_EQUIPMENT', 40, 'Daemonic wings, Fly'),
('Daemon Prince', 'Chaos Armour', 'UNIT_EQUIPMENT', 20, '4+ armour save'),
('Daemon Prince', 'Level 1 Wizard', 'UNIT_EQUIPMENT', 35, 'Level 1 Wizard'),
('Daemon Prince', 'Level 2 Wizard', 'UNIT_EQUIPMENT', 70, 'Level 2 Wizard'),

('Herald of Khorne', 'Hellblade', 'FREE_UPGRADE', 0, 'Killing Blow'),
('Herald of Khorne', 'Juggernaut of Khorne', 'MOUNT', 50, 'Brass daemon engine'),
('Herald of Khorne', 'Blood Throne', 'MOUNT', 130, 'Daemonic chariot'),
('Herald of Khorne', 'Battle Standard', 'BSB', 25, 'Battle Standard Bearer'),
('Herald of Tzeentch', 'Level 1 Wizard', 'FREE_UPGRADE', 0, 'Level 1 Wizard'),
('Herald of Tzeentch', 'Level 2 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 2 Wizard'),
('Herald of Tzeentch', 'Disc of Tzeentch', 'MOUNT', 30, 'Flying daemonic disc'),
('Herald of Tzeentch', 'Burning Chariot', 'MOUNT', 140, 'Flaming chariot'),
('Herald of Tzeentch', 'Battle Standard', 'BSB', 25, 'Battle Standard Bearer'),
('Herald of Nurgle', 'Level 1 Wizard', 'UNIT_EQUIPMENT', 35, 'Level 1 Wizard'),
('Herald of Nurgle', 'Level 2 Wizard', 'UNIT_EQUIPMENT', 70, 'Level 2 Wizard'),
('Herald of Nurgle', 'Palanquin of Nurgle', 'MOUNT', 50, 'Carried by nurglings'),
('Herald of Nurgle', 'Battle Standard', 'BSB', 25, 'Battle Standard Bearer'),
('Herald of Slaanesh', 'Level 1 Wizard', 'UNIT_EQUIPMENT', 35, 'Level 1 Wizard'),
('Herald of Slaanesh', 'Level 2 Wizard', 'UNIT_EQUIPMENT', 70, 'Level 2 Wizard'),
('Herald of Slaanesh', 'Steed of Slaanesh', 'MOUNT', 15, 'Fast daemonic steed'),
('Herald of Slaanesh', 'Seeker Chariot', 'MOUNT', 80, 'Daemonic chariot'),
('Herald of Slaanesh', 'Battle Standard', 'BSB', 25, 'Battle Standard Bearer'),

('Bloodletters', 'Hellblade', 'FREE_UPGRADE', 0, 'Killing Blow'),
('Bloodletters', 'Bloodreaper', 'COMMAND_GROUP', 10, 'Champion'),
('Bloodletters', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Bloodletters', 'Musician', 'COMMAND_GROUP', 10, 'Musician'),
('Pink Horrors', 'Level 1 Wizard', 'FREE_UPGRADE', 0, 'The unit channels a single spell'),
('Pink Horrors', 'Iridescent Horror', 'COMMAND_GROUP', 10, 'Champion'),
('Pink Horrors', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Pink Horrors', 'Musician', 'COMMAND_GROUP', 10, 'Musician'),
('Plaguebearers', 'Poisoned Attacks', 'FREE_UPGRADE', 0, 'Poisoned Attacks'),
('Plaguebearers', 'Plagueridden', 'COMMAND_GROUP', 10, 'Champion'),
('Plaguebearers', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Plaguebearers', 'Musician', 'COMMAND_GROUP', 10, 'Musician'),
('Daemonettes', 'Armour Piercing', 'FREE_UPGRADE', 0, 'Armour Piercing'),
('Daemonettes', 'Alluress', 'COMMAND_GROUP', 10, 'Champion'),
('Daemonettes', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Daemonettes', 'Musician', 'COMMAND_GROUP', 10, 'Musician'),
('Nurglings', 'Poisoned Attacks', 'FREE_UPGRADE', 0, 'Poisoned Attacks, Scouts'),
('Flesh Hounds', 'Collar of Khorne', 'FREE_UPGRADE', 0, 'Magic Resistance (2)'),
('Bloodcrushers', 'Hellblade', 'FREE_UPGRADE', 0, 'Killing Blow'),
('Bloodcrushers', 'Bloodhunter', 'COMMAND_GROUP', 10, 'Champion'),
('Bloodcrushers', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Bloodcrushers', 'Musician', 'COMMAND_GROUP', 10, 'Musician'),
('Flamers of Tzeentch', 'Warpflame', 'FREE_UPGRADE', 0, 'Flaming shooting attacks'),
('Flamers of Tzeentch', 'Pyrocaster', 'COMMAND_GROUP', 10, 'Champion'),
('Screamers', 'Flying', 'FREE_UPGRADE', 0, 'Fly, slashing attacks'),
('Plague Drones', 'Flying', 'FREE_UPGRADE', 0, 'Fly'),
('Plague Drones', 'Plaguebringer', 'COMMAND_GROUP', 10, 'Champion'),
('Seekers of Slaanesh', 'Fast Cavalry', 'FREE_UPGRADE', 0, 'Free reform and vanguard'),
('Seekers of Slaanesh', 'Heartseeker', 'COMMAND_GROUP', 10, 'Champion'),
('Seekers of Slaanesh', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Seekers of Slaanesh', 'Musician', 'COMMAND_GROUP', 10, 'Musician'),
('Fiends of Slaanesh', 'Soporific Musk', 'FREE_UPGRADE', 0, 'Enemies in contact strike last'),
('Beasts of Nurgle', 'Random Attacks', 'FREE_UPGRADE', 0, 'Poisoned random attacks'),
('Seeker Chariot', 'Crew', 'FREE_UPGRADE', 0, 'Two daemonettes and steeds'),
('Burning Chariot', 'Crew', 'FREE_UPGRADE', 0, 'Exalted Flamer and screamers'),
('Skull Cannon', 'Crew', 'FREE_UPGRADE', 0, 'Two bloodletters'),
('Soul Grinder', 'Harvester Cannon', 'FREE_UPGRADE', 0, 'Daemonic war engine with cannon'),
('Soul Grinder', 'Phlegm Bombardment', 'UNIT_EQUIPMENT', 25, 'Stone thrower attack'),
('Hellflayer', 'Crew', 'FREE_UPGRADE', 0, 'Daemonette crew and steeds'),
('Exalted Flamer', 'Warpflame', 'FREE_UPGRADE', 0, 'Flaming shooting attacks');

INSERT INTO staging_unit_upgrade
SELECT unit.name, gift.name, gift.upgrade_type, gift.points, gift.description
FROM staging_unit unit
CROSS JOIN (VALUES
    ('Exalted Gift', 'MAGIC_ITEM', 50, 'One exalted daemonic gift'),
    ('Greater Gift', 'MAGIC_ITEM', 25, 'One greater daemonic gift'),
    ('Lesser Gift', 'MAGIC_ITEM', 10, 'One lesser daemonic gift'),
    ('Hellforged Blade', 'MAGIC_WEAPON', 25, 'Daemonic weapon, +1 Strength'),
    ('Armour of Contempt', 'MAGIC_ARMOUR', 25, 'Daemonic armour, 5+ armour save')
) AS gift(name, upgrade_type, points, description)
WHERE unit.unit_type IN ('LORDS', 'HERO');

INSERT INTO staging_unit_upgrade
SELECT unit_name, banner.name, 'MAGIC_BANNER', banner.points, banner.description
FROM (VALUES ('Herald of Khorne'), ('Herald of Tzeentch'), ('Herald of Nurgle'), ('Herald of Slaanesh'),
             ('Bloodletters'), ('Pink Horrors'), ('Plaguebearers'), ('Daemonettes'),
             ('Bloodcrushers'), ('Seekers of Slaanesh')) AS bearers(unit_name)
CROSS JOIN (VALUES
    ('Banner of Blood', 25, '+1 combat resolution'),
    ('Icon of Endless War', 25, 'The unit may re-roll failed charge distances'),
    ('Banner of Sorcery', 50, '+1 to cast for wizards in the unit')
) AS banner(name, points, description);

SELECT roster_load_army('DAEMONS_OF_CHAOS');
