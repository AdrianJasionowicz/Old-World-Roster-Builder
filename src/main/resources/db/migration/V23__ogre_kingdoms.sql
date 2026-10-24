SELECT roster_begin_army();

INSERT INTO staging_unit VALUES
('Tyrant', 210, 1, 'LORDS', 6, 6, 3, 5, 5, 5, 4, 5, 9, 0, 0),
('Slaughtermaster', 210, 1, 'LORDS', 6, 3, 3, 4, 5, 5, 2, 4, 8, 0, 0),
('Bruiser', 105, 1, 'HERO', 6, 5, 3, 5, 5, 4, 3, 4, 8, 0, 0),
('Butcher', 80, 1, 'HERO', 6, 3, 3, 4, 5, 4, 2, 3, 7, 0, 0),
('Firebelly', 75, 1, 'HERO', 6, 3, 3, 4, 5, 4, 2, 3, 7, 0, 0),
('Hunter', 130, 1, 'HERO', 6, 5, 4, 5, 5, 4, 3, 4, 8, 0, 0),
('Ogre Bulls', 30, 3, 'CORE', 6, 3, 2, 4, 4, 3, 2, 3, 7, 0, 0),
('Ironguts', 43, 3, 'CORE', 6, 3, 2, 4, 4, 3, 2, 3, 8, 0, 0),
('Gnoblars', 2.5, 20, 'CORE', 4, 2, 3, 3, 3, 1, 3, 1, 5, 0, 0),
('Leadbelchers', 43, 3, 'SPECIAL', 6, 3, 3, 4, 4, 3, 2, 3, 7, 0, 0),
('Maneaters', 55, 3, 'SPECIAL', 6, 4, 4, 5, 4, 3, 3, 3, 8, 0, 0),
('Mournfang Cavalry', 60, 2, 'SPECIAL', 6, 3, 2, 4, 4, 3, 2, 3, 7, 0, 0),
('Yhetees', 42, 3, 'SPECIAL', 7, 3, 0, 5, 4, 3, 4, 3, 7, 0, 0),
('Sabretusk Pack', 12, 1, 'SPECIAL', 8, 4, 0, 4, 4, 2, 4, 2, 5, 0, 0),
('Gorger', 50, 1, 'SPECIAL', 6, 3, 0, 5, 5, 4, 4, 4, 10, 0, 0),
('Ironblaster', 130, 1, 'RARE', 7, 0, 3, 5, 5, 5, 0, 0, 0, 4, 0),
('Gnoblar Scraplauncher', 130, 1, 'RARE', 7, 0, 0, 5, 5, 5, 0, 0, 0, 4, 0),
('Thundertusk', 230, 1, 'RARE', 7, 3, 0, 6, 6, 8, 2, 4, 6, 4, 0),
('Stonehorn', 250, 1, 'RARE', 7, 3, 0, 6, 6, 8, 2, 4, 6, 3, 0),
('Giant', 200, 1, 'RARE', 6, 3, 3, 6, 5, 6, 3, 1, 10, 0, 0);

INSERT INTO staging_unit_upgrade
SELECT name, 'Ogre Charge', 'FREE_UPGRADE', 0, 'Impact Hits on the charge'
FROM staging_unit
WHERE name IN ('Tyrant', 'Slaughtermaster', 'Bruiser', 'Butcher', 'Firebelly', 'Hunter', 'Ogre Bulls', 'Ironguts',
               'Leadbelchers', 'Maneaters', 'Mournfang Cavalry');

INSERT INTO staging_unit_upgrade VALUES
('Tyrant', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Tyrant', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Tyrant', 'Heavy Armour', 'UNIT_EQUIPMENT', 6, 'Heavy armour, 5+ armour save'),
('Tyrant', 'Great weapon', 'UNIT_EQUIPMENT', 12, '+2 Strength, always strikes last'),
('Tyrant', 'Additional hand weapon', 'UNIT_EQUIPMENT', 6, '+1 Attack'),
('Tyrant', 'Ironfist', 'UNIT_EQUIPMENT', 6, 'Counts as a shield in combat'),
('Tyrant', 'Ogre Pistol', 'UNIT_EQUIPMENT', 6, 'Short range brace of shots'),

('Slaughtermaster', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Slaughtermaster', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Slaughtermaster', 'Level 3 Wizard', 'FREE_UPGRADE', 0, 'Level 3 Wizard'),
('Slaughtermaster', 'Level 4 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 4 Wizard'),
('Slaughtermaster', 'Great weapon', 'UNIT_EQUIPMENT', 12, '+2 Strength, always strikes last'),

('Bruiser', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Bruiser', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Bruiser', 'Heavy Armour', 'UNIT_EQUIPMENT', 4, 'Heavy armour, 5+ armour save'),
('Bruiser', 'Great weapon', 'UNIT_EQUIPMENT', 8, '+2 Strength, always strikes last'),
('Bruiser', 'Additional hand weapon', 'UNIT_EQUIPMENT', 4, '+1 Attack'),
('Bruiser', 'Ironfist', 'UNIT_EQUIPMENT', 4, 'Counts as a shield in combat'),
('Bruiser', 'Battle Standard', 'BSB', 25, 'Battle Standard Bearer'),

('Butcher', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Butcher', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Butcher', 'Level 1 Wizard', 'FREE_UPGRADE', 0, 'Level 1 Wizard'),
('Butcher', 'Level 2 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 2 Wizard'),
('Butcher', 'Great weapon', 'UNIT_EQUIPMENT', 8, '+2 Strength, always strikes last'),

('Firebelly', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Firebelly', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Firebelly', 'Level 1 Wizard', 'FREE_UPGRADE', 0, 'Level 1 Wizard, Lore of Fire'),
('Firebelly', 'Fire Breath', 'FREE_UPGRADE', 0, 'Breath weapon with Flaming Attacks'),
('Firebelly', 'Great weapon', 'UNIT_EQUIPMENT', 8, '+2 Strength, always strikes last'),

('Hunter', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Hunter', 'Great weapon', 'FREE_UPGRADE', 0, '+2 Strength, always strikes last'),
('Hunter', 'Harpoon Launcher', 'FREE_UPGRADE', 0, 'Long range bolt that wounds monsters easily'),
('Hunter', 'Blood Vultures', 'UNIT_EQUIPMENT', 5, 'Thrown hunting birds'),
('Hunter', 'Stonehorn', 'MOUNT', 250, 'Rides a stonehorn'),

('Ogre Bulls', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Ogre Bulls', 'Light Armour', 'UNIT_EQUIPMENT', 2, 'Light armour, 6+ armour save'),
('Ogre Bulls', 'Additional hand weapon', 'UNIT_EQUIPMENT', 2, '+1 Attack'),
('Ogre Bulls', 'Ironfist', 'UNIT_EQUIPMENT', 2, 'Counts as a shield in combat'),
('Ogre Bulls', 'Crusher', 'COMMAND_GROUP', 10, 'Champion'),
('Ogre Bulls', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Ogre Bulls', 'Bellower', 'COMMAND_GROUP', 10, 'Musician'),

('Ironguts', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Ironguts', 'Great weapon', 'FREE_UPGRADE', 0, '+2 Strength, always strikes last'),
('Ironguts', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Ironguts', 'Gutlord', 'COMMAND_GROUP', 10, 'Champion'),
('Ironguts', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Ironguts', 'Bellower', 'COMMAND_GROUP', 10, 'Musician'),

('Gnoblars', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Gnoblars', 'Sharp Stuff', 'FREE_UPGRADE', 0, 'Thrown weapons'),
('Gnoblars', 'Trappers', 'UNIT_EQUIPMENT', 0.5, 'Scouts and traps'),
('Gnoblars', 'Groinbiter', 'COMMAND_GROUP', 5, 'Champion'),

('Leadbelchers', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Leadbelchers', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Leadbelchers', 'Leadbelcher Gun', 'FREE_UPGRADE', 0, 'Short range cannon'),
('Leadbelchers', 'Thunderfist', 'COMMAND_GROUP', 10, 'Champion'),
('Leadbelchers', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Leadbelchers', 'Bellower', 'COMMAND_GROUP', 10, 'Musician'),

('Maneaters', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Maneaters', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Maneaters', 'Great weapon', 'UNIT_EQUIPMENT', 5, '+2 Strength, always strikes last'),
('Maneaters', 'Additional hand weapon', 'UNIT_EQUIPMENT', 5, '+1 Attack'),
('Maneaters', 'Ogre Pistol', 'UNIT_EQUIPMENT', 5, 'Short range brace of shots'),
('Maneaters', 'Captain', 'COMMAND_GROUP', 10, 'Champion'),
('Maneaters', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Maneaters', 'Bellower', 'COMMAND_GROUP', 10, 'Musician'),

('Mournfang Cavalry', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Mournfang Cavalry', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Mournfang Cavalry', 'Mournfang', 'FREE_UPGRADE', 0, 'Rides a mournfang'),
('Mournfang Cavalry', 'Ironfist', 'UNIT_EQUIPMENT', 5, 'Counts as a shield in combat'),
('Mournfang Cavalry', 'Great weapon', 'UNIT_EQUIPMENT', 5, '+2 Strength, always strikes last'),
('Mournfang Cavalry', 'Crusher', 'COMMAND_GROUP', 10, 'Champion'),
('Mournfang Cavalry', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Mournfang Cavalry', 'Bellower', 'COMMAND_GROUP', 10, 'Musician'),

('Yhetees', 'Frost Weapons', 'FREE_UPGRADE', 0, 'Flaming-immune icy claws'),
('Yhetees', 'Greyback', 'COMMAND_GROUP', 10, 'Champion'),
('Sabretusk Pack', 'Fast Cavalry', 'FREE_UPGRADE', 0, 'Free reform and vanguard'),
('Gorger', 'Ambushers', 'FREE_UPGRADE', 0, 'Arrives from any table edge, Frenzy'),
('Ironblaster', 'Crew', 'FREE_UPGRADE', 0, 'Ogre crew and Rhinox'),
('Gnoblar Scraplauncher', 'Crew', 'FREE_UPGRADE', 0, 'Gnoblar crew and Rhinox'),
('Thundertusk', 'Numbing Chill', 'FREE_UPGRADE', 0, 'Enemies in contact strike last'),
('Stonehorn', 'Stone Skeleton', 'FREE_UPGRADE', 0, 'Halves multiple wounds'),
('Giant', 'Terror', 'FREE_UPGRADE', 0, 'Causes Terror');

INSERT INTO staging_unit_upgrade
SELECT unit.name, item.name, item.upgrade_type, item.points, item.description
FROM staging_unit unit
CROSS JOIN (VALUES
    ('Thundermace', 'MAGIC_WEAPON', 85, 'Hits every model in base contact'),
    ('Siegebreaker', 'MAGIC_WEAPON', 45, 'Multiple wounds and flaming attacks'),
    ('Gut Maw', 'MAGIC_ARMOUR', 30, 'Devours enemy magic weapons'),
    ('Greedy Fist', 'MAGIC_ARMOUR', 30, 'Ironfist that drains enemy magic items'),
    ('Rock Eye', 'MAGIC_ITEM', 25, 'The bearer cannot be ambushed and spots hidden enemies')
) AS item(name, upgrade_type, points, description)
WHERE unit.unit_type IN ('LORDS', 'HERO');

INSERT INTO staging_unit_upgrade
SELECT unit_name, banner.name, 'MAGIC_BANNER', banner.points, banner.description
FROM (VALUES ('Bruiser'), ('Ironguts'), ('Ogre Bulls'), ('Maneaters')) AS bearers(unit_name)
CROSS JOIN (VALUES
    ('Dragonhide Banner', 60, 'One use: breath weapon with Flaming Attacks'),
    ('Rag Banner', 15, 'The unit has Fear')
) AS banner(name, points, description);

SELECT roster_load_army('OGRE_KINGDOMS');
