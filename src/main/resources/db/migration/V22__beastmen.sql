SELECT roster_begin_army();

INSERT INTO staging_unit VALUES
('Doombull', 235, 1, 'LORDS', 5, 7, 3, 5, 5, 4, 3, 5, 9, 0, 0),
('Beastlord', 130, 1, 'LORDS', 5, 7, 3, 5, 5, 3, 6, 4, 9, 0, 0),
('Great Bray-Shaman', 200, 1, 'LORDS', 5, 4, 3, 4, 5, 3, 3, 2, 7, 0, 0),
('Gorebull', 130, 1, 'HERO', 5, 6, 3, 5, 5, 3, 3, 4, 8, 0, 0),
('Wargor', 85, 1, 'HERO', 5, 6, 3, 4, 5, 2, 5, 3, 8, 0, 0),
('Bray-Shaman', 75, 1, 'HERO', 5, 4, 3, 3, 4, 2, 3, 1, 6, 0, 0),
('Gors', 7, 10, 'CORE', 5, 4, 3, 3, 4, 1, 3, 1, 6, 0, 0),
('Ungors', 5, 10, 'CORE', 5, 3, 3, 3, 3, 1, 3, 1, 6, 0, 0),
('Ungor Raiders', 6, 5, 'CORE', 5, 3, 3, 3, 3, 1, 3, 1, 6, 0, 0),
('Tuskgor Chariot', 80, 1, 'CORE', 0, 0, 0, 5, 4, 4, 0, 0, 0, 5, 0),
('Chaos Warhounds', 6, 5, 'CORE', 7, 4, 0, 3, 3, 1, 3, 1, 5, 0, 0),
('Bestigors', 12, 10, 'SPECIAL', 5, 4, 3, 4, 4, 1, 3, 1, 7, 0, 0),
('Centigors', 25, 5, 'SPECIAL', 8, 4, 3, 4, 4, 1, 2, 1, 7, 0, 0),
('Minotaurs', 60, 3, 'SPECIAL', 6, 4, 3, 4, 4, 3, 3, 3, 8, 0, 0),
('Razorgor Chariot', 155, 1, 'SPECIAL', 0, 0, 0, 5, 5, 5, 0, 0, 0, 5, 0),
('Razorgor', 55, 1, 'SPECIAL', 7, 3, 0, 5, 4, 3, 2, 4, 6, 0, 0),
('Harpies', 11, 5, 'SPECIAL', 4, 3, 0, 4, 3, 1, 4, 1, 6, 0, 0),
('Chaos Spawn', 55, 1, 'RARE', 0, 3, 0, 4, 5, 3, 2, 0, 10, 0, 0),
('Ghorgon', 275, 1, 'RARE', 7, 4, 0, 7, 6, 7, 2, 5, 10, 0, 0),
('Cygor', 275, 1, 'RARE', 8, 2, 1, 6, 6, 6, 2, 4, 10, 0, 0),
('Jabberslythe', 275, 1, 'RARE', 7, 5, 0, 5, 5, 6, 2, 5, 6, 0, 0),
('Giant', 200, 1, 'RARE', 6, 3, 3, 6, 5, 6, 3, 1, 10, 0, 0);

INSERT INTO staging_unit_upgrade
SELECT name, 'Primal Fury', 'FREE_UPGRADE', 0, 'Becomes Frenzied on a successful Leadership test'
FROM staging_unit
WHERE name IN ('Beastlord', 'Wargor', 'Gors', 'Bestigors');

INSERT INTO staging_unit_upgrade VALUES
('Doombull', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Doombull', 'Bloodgreed', 'FREE_UPGRADE', 0, 'Gains attacks after slaying enemies'),
('Doombull', 'Light Armour', 'UNIT_EQUIPMENT', 3, 'Light armour, 6+ armour save'),
('Doombull', 'Heavy Armour', 'UNIT_EQUIPMENT', 6, 'Heavy armour, 5+ armour save'),
('Doombull', 'Great weapon', 'UNIT_EQUIPMENT', 12, '+2 Strength, always strikes last'),
('Doombull', 'Additional hand weapon', 'UNIT_EQUIPMENT', 6, '+1 Attack'),
('Doombull', 'Shield', 'UNIT_EQUIPMENT', 6, '+1 armour save'),

('Beastlord', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Beastlord', 'Light Armour', 'UNIT_EQUIPMENT', 3, 'Light armour, 6+ armour save'),
('Beastlord', 'Heavy Armour', 'UNIT_EQUIPMENT', 6, 'Heavy armour, 5+ armour save'),
('Beastlord', 'Great weapon', 'UNIT_EQUIPMENT', 6, '+2 Strength, always strikes last'),
('Beastlord', 'Additional hand weapon', 'UNIT_EQUIPMENT', 3, '+1 Attack'),
('Beastlord', 'Shield', 'UNIT_EQUIPMENT', 3, '+1 armour save'),
('Beastlord', 'Razorgor Chariot', 'MOUNT', 155, 'Chariot pulled by a razorgor'),
('Beastlord', 'Tuskgor Chariot', 'MOUNT', 80, 'Chariot pulled by tuskgors'),

('Great Bray-Shaman', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Great Bray-Shaman', 'Level 3 Wizard', 'FREE_UPGRADE', 0, 'Level 3 Wizard'),
('Great Bray-Shaman', 'Level 4 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 4 Wizard'),
('Great Bray-Shaman', 'Tuskgor Chariot', 'MOUNT', 80, 'Chariot pulled by tuskgors'),

('Gorebull', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Gorebull', 'Bloodgreed', 'FREE_UPGRADE', 0, 'Gains attacks after slaying enemies'),
('Gorebull', 'Light Armour', 'UNIT_EQUIPMENT', 2, 'Light armour, 6+ armour save'),
('Gorebull', 'Heavy Armour', 'UNIT_EQUIPMENT', 4, 'Heavy armour, 5+ armour save'),
('Gorebull', 'Great weapon', 'UNIT_EQUIPMENT', 8, '+2 Strength, always strikes last'),
('Gorebull', 'Additional hand weapon', 'UNIT_EQUIPMENT', 4, '+1 Attack'),
('Gorebull', 'Shield', 'UNIT_EQUIPMENT', 4, '+1 armour save'),
('Gorebull', 'Battle Standard', 'BSB', 25, 'Battle Standard Bearer'),

('Wargor', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Wargor', 'Light Armour', 'UNIT_EQUIPMENT', 2, 'Light armour, 6+ armour save'),
('Wargor', 'Heavy Armour', 'UNIT_EQUIPMENT', 4, 'Heavy armour, 5+ armour save'),
('Wargor', 'Great weapon', 'UNIT_EQUIPMENT', 4, '+2 Strength, always strikes last'),
('Wargor', 'Additional hand weapon', 'UNIT_EQUIPMENT', 2, '+1 Attack'),
('Wargor', 'Shield', 'UNIT_EQUIPMENT', 2, '+1 armour save'),
('Wargor', 'Battle Standard', 'BSB', 25, 'Battle Standard Bearer'),
('Wargor', 'Tuskgor Chariot', 'MOUNT', 80, 'Chariot pulled by tuskgors'),

('Bray-Shaman', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Bray-Shaman', 'Level 1 Wizard', 'FREE_UPGRADE', 0, 'Level 1 Wizard'),
('Bray-Shaman', 'Level 2 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 2 Wizard'),
('Bray-Shaman', 'Tuskgor Chariot', 'MOUNT', 80, 'Chariot pulled by tuskgors'),

('Gors', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Gors', 'Shield', 'UNIT_EQUIPMENT', 1, '+1 armour save'),
('Gors', 'Additional hand weapon', 'UNIT_EQUIPMENT', 1, '+1 Attack'),
('Gors', 'Foe-render', 'COMMAND_GROUP', 10, 'Champion'),
('Gors', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Gors', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Ungors', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Ungors', 'Shield', 'FREE_UPGRADE', 0, '+1 armour save'),
('Ungors', 'Spear', 'UNIT_EQUIPMENT', 1, 'Fights in an extra rank'),
('Ungors', 'Halfhorn', 'COMMAND_GROUP', 6, 'Champion'),
('Ungors', 'Standard Bearer', 'COMMAND_GROUP', 6, 'Standard Bearer'),
('Ungors', 'Musician', 'COMMAND_GROUP', 3, 'Musician'),

('Ungor Raiders', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Ungor Raiders', 'Short Bow', 'FREE_UPGRADE', 0, 'Short range bow'),
('Ungor Raiders', 'Skirmishers', 'FREE_UPGRADE', 0, 'Skirmishers, Scouts, Vanguard'),
('Ungor Raiders', 'Halfhorn', 'COMMAND_GROUP', 6, 'Champion'),
('Ungor Raiders', 'Musician', 'COMMAND_GROUP', 3, 'Musician'),

('Tuskgor Chariot', 'Crew', 'FREE_UPGRADE', 0, 'Two gor crew with spears'),
('Chaos Warhounds', 'Scaly Skin (6+)', 'UNIT_EQUIPMENT', 1, 'Scaly skin, 6+ armour save'),
('Chaos Warhounds', 'Poisoned Attacks', 'UNIT_EQUIPMENT', 2, 'Poisoned Attacks'),

('Bestigors', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Bestigors', 'Great weapon', 'FREE_UPGRADE', 0, '+2 Strength, always strikes last'),
('Bestigors', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Bestigors', 'Gouge-horn', 'COMMAND_GROUP', 10, 'Champion'),
('Bestigors', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Bestigors', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Centigors', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Centigors', 'Spear', 'FREE_UPGRADE', 0, 'Spear'),
('Centigors', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Centigors', 'Shield', 'UNIT_EQUIPMENT', 1, '+1 armour save'),
('Centigors', 'Great weapon', 'UNIT_EQUIPMENT', 2, '+2 Strength, always strikes last'),
('Centigors', 'Throwing Axes', 'UNIT_EQUIPMENT', 2, 'Throwing weapons'),
('Centigors', 'Gorehoof', 'COMMAND_GROUP', 10, 'Champion'),
('Centigors', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Centigors', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Minotaurs', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Minotaurs', 'Bloodgreed', 'FREE_UPGRADE', 0, 'Gains attacks after slaying enemies'),
('Minotaurs', 'Light Armour', 'UNIT_EQUIPMENT', 5, 'Light armour, 6+ armour save'),
('Minotaurs', 'Shield', 'UNIT_EQUIPMENT', 5, '+1 armour save'),
('Minotaurs', 'Great weapon', 'UNIT_EQUIPMENT', 15, '+2 Strength, always strikes last'),
('Minotaurs', 'Additional hand weapon', 'UNIT_EQUIPMENT', 5, '+1 Attack'),
('Minotaurs', 'Bloodkine', 'COMMAND_GROUP', 20, 'Champion'),
('Minotaurs', 'Standard Bearer', 'COMMAND_GROUP', 20, 'Standard Bearer'),
('Minotaurs', 'Musician', 'COMMAND_GROUP', 10, 'Musician'),

('Razorgor Chariot', 'Crew', 'FREE_UPGRADE', 0, 'Two gor crew with spears'),
('Razorgor', 'Frenzy', 'FREE_UPGRADE', 0, 'Frenzy, +1 Attack'),
('Harpies', 'Flying', 'FREE_UPGRADE', 0, 'Fly, Skirmishers'),
('Chaos Spawn', 'Random Attacks', 'FREE_UPGRADE', 0, 'Random Movement, Random Attacks, Fear'),
('Ghorgon', 'Terror', 'FREE_UPGRADE', 0, 'Causes Terror'),
('Ghorgon', 'Swallow Whole', 'FREE_UPGRADE', 0, 'Devours enemy models'),
('Cygor', 'Soul-eater', 'FREE_UPGRADE', 0, 'Hurls rocks, hates wizards'),
('Jabberslythe', 'Aura of Madness', 'FREE_UPGRADE', 0, 'Terror, poisoned blood'),
('Giant', 'Terror', 'FREE_UPGRADE', 0, 'Causes Terror');

INSERT INTO staging_unit_upgrade
SELECT unit.name, item.name, item.upgrade_type, item.points, item.description
FROM staging_unit unit
CROSS JOIN (VALUES
    ('Axes of Khorgor', 'MAGIC_WEAPON', 40, 'Pair of axes, +1 Attack and Killing Blow'),
    ('Hunting Spear', 'MAGIC_WEAPON', 30, 'Thrown spear that wounds monsters easily'),
    ('Pelt of the Dark Young', 'MAGIC_ARMOUR', 35, 'Hide that makes the wearer hard to hit'),
    ('Shroud of Dark Dreams', 'MAGIC_ITEM', 25, 'Enemies near the bearer must re-roll successful Leadership tests'),
    ('Horn of the First Beast', 'MAGIC_ITEM', 20, 'One use: the unit gains Primal Fury')
) AS item(name, upgrade_type, points, description)
WHERE unit.unit_type IN ('LORDS', 'HERO')
  AND unit.name NOT IN ('Great Bray-Shaman', 'Bray-Shaman');

INSERT INTO staging_unit_upgrade
SELECT unit_name, banner.name, 'MAGIC_BANNER', banner.points, banner.description
FROM (VALUES ('Gorebull'), ('Wargor'), ('Gors'), ('Bestigors')) AS bearers(unit_name)
CROSS JOIN (VALUES
    ('Manbane Standard', 60, 'Fear and hatred of humans'),
    ('Banner of the Wild Herd', 50, 'The unit can march regardless of enemies nearby')
) AS banner(name, points, description);

SELECT roster_load_army('BEASTMEN');
