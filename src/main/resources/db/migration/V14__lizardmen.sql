SELECT roster_begin_army();

INSERT INTO staging_unit VALUES
('Slann Mage-Priest', 300, 1, 'LORDS', 4, 2, 3, 3, 4, 5, 2, 1, 9, 0, 0),
('Saurus Oldblood', 140, 1, 'LORDS', 4, 6, 0, 5, 5, 3, 3, 5, 8, 0, 0),
('Scar-Veteran', 85, 1, 'HERO', 4, 5, 0, 5, 5, 2, 3, 4, 8, 0, 0),
('Skink Chief', 40, 1, 'HERO', 6, 4, 5, 3, 2, 2, 6, 3, 6, 0, 0),
('Skink Priest', 65, 1, 'HERO', 6, 2, 3, 3, 2, 2, 4, 1, 6, 0, 0),
('Saurus Warriors', 11, 10, 'CORE', 4, 3, 0, 4, 4, 1, 1, 2, 8, 0, 0),
('Skink Cohort', 6, 10, 'CORE', 6, 2, 3, 3, 2, 1, 4, 1, 6, 0, 0),
('Skink Skirmishers', 7, 10, 'CORE', 6, 2, 3, 3, 2, 1, 4, 1, 6, 0, 0),
('Temple Guard', 14, 10, 'SPECIAL', 4, 4, 0, 4, 4, 1, 2, 2, 8, 0, 0),
('Chameleon Skinks', 13, 5, 'SPECIAL', 6, 2, 4, 3, 2, 1, 4, 1, 6, 0, 0),
('Cold One Riders', 30, 5, 'SPECIAL', 4, 3, 0, 4, 4, 1, 1, 2, 8, 0, 0),
('Terradon Riders', 40, 3, 'SPECIAL', 2, 3, 3, 4, 4, 2, 2, 2, 6, 0, 0),
('Ripperdactyl Riders', 40, 3, 'SPECIAL', 2, 3, 3, 4, 4, 2, 2, 2, 6, 0, 0),
('Kroxigor', 55, 3, 'SPECIAL', 6, 3, 1, 5, 4, 3, 1, 3, 7, 0, 0),
('Bastiladon', 150, 1, 'SPECIAL', 4, 3, 0, 5, 6, 5, 1, 4, 6, 2, 0),
('Stegadon', 235, 1, 'SPECIAL', 6, 3, 0, 5, 6, 5, 2, 4, 6, 4, 0),
('Salamander Hunting Pack', 70, 1, 'RARE', 6, 3, 0, 5, 4, 3, 4, 3, 5, 0, 0),
('Razordon Hunting Pack', 70, 1, 'RARE', 6, 3, 0, 5, 4, 3, 4, 3, 5, 0, 0),
('Ancient Stegadon', 255, 1, 'RARE', 6, 3, 0, 5, 6, 5, 2, 4, 6, 4, 0),
('Troglodon', 205, 1, 'RARE', 6, 3, 0, 5, 5, 5, 2, 4, 6, 4, 0);

INSERT INTO staging_unit_upgrade
SELECT name, 'Cold Blooded', 'FREE_UPGRADE', 0, 'Rolls three dice for Leadership tests and discards the highest'
FROM staging_unit;

INSERT INTO staging_unit_upgrade
SELECT unit.name, skin.name, 'FREE_UPGRADE', 0, skin.description
FROM staging_unit unit
JOIN (VALUES
    ('Scaly Skin (4+)', '4+ armour save from thick scales'),
    ('Scaly Skin (5+)', '5+ armour save from thick scales'),
    ('Scaly Skin (6+)', '6+ armour save from thick scales')
) AS skin(name, description)
ON (skin.name = 'Scaly Skin (4+)' AND unit.name IN ('Kroxigor'))
    OR (skin.name = 'Scaly Skin (5+)' AND unit.name IN ('Saurus Oldblood', 'Scar-Veteran', 'Saurus Warriors', 'Temple Guard', 'Cold One Riders'))
    OR (skin.name = 'Scaly Skin (6+)' AND unit.name IN ('Skink Chief', 'Skink Priest', 'Skink Cohort', 'Skink Skirmishers', 'Chameleon Skinks', 'Terradon Riders', 'Ripperdactyl Riders'));

INSERT INTO staging_unit_upgrade VALUES
('Slann Mage-Priest', 'Level 4 Wizard', 'FREE_UPGRADE', 0, 'Level 4 Wizard'),
('Slann Mage-Priest', 'Palanquin', 'FREE_UPGRADE', 0, 'Floats on a magical palanquin'),
('Slann Mage-Priest', 'Ward save (5+)', 'FREE_UPGRADE', 0, 'Protected by the Old Ones'),
('Slann Mage-Priest', 'Becalming Cogitation', 'UNIT_EQUIPMENT', 25, 'Discipline of the Old Ones'),
('Slann Mage-Priest', 'Focus of Mystery', 'UNIT_EQUIPMENT', 35, 'Discipline of the Old Ones'),

('Saurus Oldblood', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Saurus Oldblood', 'Great weapon', 'UNIT_EQUIPMENT', 6, '+2 Strength, always strikes last'),
('Saurus Oldblood', 'Spear', 'UNIT_EQUIPMENT', 3, 'Spear'),
('Saurus Oldblood', 'Light Armour', 'UNIT_EQUIPMENT', 3, 'Light armour'),
('Saurus Oldblood', 'Shield', 'UNIT_EQUIPMENT', 3, '+1 armour save'),
('Saurus Oldblood', 'Cold One', 'MOUNT', 30, 'Reptilian mount'),
('Saurus Oldblood', 'Carnosaur', 'MOUNT', 200, 'Monstrous predator'),

('Scar-Veteran', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Scar-Veteran', 'Great weapon', 'UNIT_EQUIPMENT', 4, '+2 Strength, always strikes last'),
('Scar-Veteran', 'Spear', 'UNIT_EQUIPMENT', 2, 'Spear'),
('Scar-Veteran', 'Light Armour', 'UNIT_EQUIPMENT', 2, 'Light armour'),
('Scar-Veteran', 'Shield', 'UNIT_EQUIPMENT', 2, '+1 armour save'),
('Scar-Veteran', 'Battle Standard', 'BSB', 25, 'Battle Standard Bearer'),
('Scar-Veteran', 'Cold One', 'MOUNT', 30, 'Reptilian mount'),
('Scar-Veteran', 'Carnosaur', 'MOUNT', 200, 'Monstrous predator'),

('Skink Chief', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Skink Chief', 'Light Armour', 'UNIT_EQUIPMENT', 2, 'Light armour'),
('Skink Chief', 'Shield', 'UNIT_EQUIPMENT', 2, '+1 armour save'),
('Skink Chief', 'Blowpipe', 'UNIT_EQUIPMENT', 5, 'Poisoned darts'),
('Skink Chief', 'Battle Standard', 'BSB', 25, 'Battle Standard Bearer'),
('Skink Chief', 'Terradon', 'MOUNT', 30, 'Flying reptile'),
('Skink Chief', 'Ripperdactyl', 'MOUNT', 40, 'Flying reptile'),

('Skink Priest', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Skink Priest', 'Level 1 Wizard', 'FREE_UPGRADE', 0, 'Level 1 Wizard'),
('Skink Priest', 'Level 2 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 2 Wizard'),
('Skink Priest', 'Engine of the Gods', 'MOUNT', 140, 'Rides an Ancient Stegadon with the Engine of the Gods'),

('Saurus Warriors', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Saurus Warriors', 'Shield', 'FREE_UPGRADE', 0, '+1 armour save'),
('Saurus Warriors', 'Spears', 'UNIT_EQUIPMENT', 1, 'Fights in an extra rank'),
('Saurus Warriors', 'Spawn Leader', 'COMMAND_GROUP', 10, 'Champion'),
('Saurus Warriors', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Saurus Warriors', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Skink Cohort', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Skink Cohort', 'Javelins', 'FREE_UPGRADE', 0, 'Thrown spears'),
('Skink Cohort', 'Shield', 'FREE_UPGRADE', 0, '+1 armour save'),
('Skink Cohort', 'Kroxigor', 'COMMAND_GROUP', 55, 'A Kroxigor joins the unit'),
('Skink Cohort', 'Skink Brave', 'COMMAND_GROUP', 10, 'Champion'),
('Skink Cohort', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Skink Cohort', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Skink Skirmishers', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Skink Skirmishers', 'Blowpipe', 'FREE_UPGRADE', 0, 'Poisoned darts'),
('Skink Skirmishers', 'Skirmish', 'FREE_UPGRADE', 0, 'Loose formation'),
('Skink Skirmishers', 'Javelins', 'UNIT_EQUIPMENT', 0, 'Replaces blowpipes'),
('Skink Skirmishers', 'Shield', 'UNIT_EQUIPMENT', 1, '+1 armour save'),
('Skink Skirmishers', 'Skink Brave', 'COMMAND_GROUP', 10, 'Champion'),

('Temple Guard', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Temple Guard', 'Halberd', 'FREE_UPGRADE', 0, '+1 Strength in close combat'),
('Temple Guard', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour'),
('Temple Guard', 'Shield', 'FREE_UPGRADE', 0, '+1 armour save'),
('Temple Guard', 'Revering Guardian', 'COMMAND_GROUP', 10, 'Champion'),
('Temple Guard', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Temple Guard', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Chameleon Skinks', 'Blowpipe', 'FREE_UPGRADE', 0, 'Poisoned darts'),
('Chameleon Skinks', 'Scouts', 'FREE_UPGRADE', 0, 'Deploys as Scouts'),
('Chameleon Skinks', 'Skirmish', 'FREE_UPGRADE', 0, 'Loose formation'),
('Chameleon Skinks', 'Stalker', 'COMMAND_GROUP', 10, 'Champion'),

('Cold One Riders', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Cold One Riders', 'Spear', 'FREE_UPGRADE', 0, 'Spear'),
('Cold One Riders', 'Shield', 'FREE_UPGRADE', 0, '+1 armour save'),
('Cold One Riders', 'Cold One', 'FREE_UPGRADE', 0, 'Rides a cold one'),
('Cold One Riders', 'Spawn Leader', 'COMMAND_GROUP', 10, 'Champion'),
('Cold One Riders', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Cold One Riders', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Terradon Riders', 'Javelins', 'FREE_UPGRADE', 0, 'Thrown spears'),
('Terradon Riders', 'Fly', 'FREE_UPGRADE', 0, 'Flying unit'),
('Terradon Riders', 'Fireleech Bolas', 'UNIT_EQUIPMENT', 1, 'Flaming bolas'),
('Terradon Riders', 'Sky Leader', 'COMMAND_GROUP', 10, 'Champion'),
('Ripperdactyl Riders', 'Javelins', 'FREE_UPGRADE', 0, 'Thrown spears'),
('Ripperdactyl Riders', 'Fly', 'FREE_UPGRADE', 0, 'Flying unit'),
('Ripperdactyl Riders', 'Sky Leader', 'COMMAND_GROUP', 10, 'Champion'),

('Kroxigor', 'Great weapon', 'FREE_UPGRADE', 0, '+2 Strength, always strikes last'),
('Kroxigor', 'Kroxigor Ancient', 'COMMAND_GROUP', 15, 'Champion'),
('Bastiladon', 'Solar Engine', 'UNIT_EQUIPMENT', 0, 'Shooting weapon'),
('Bastiladon', 'Ark of Sotek', 'UNIT_EQUIPMENT', 0, 'Releases swarms of snakes'),
('Stegadon', 'Giant Bow', 'FREE_UPGRADE', 0, 'Bolt thrower carried on its back'),
('Stegadon', 'Skink Crew', 'FREE_UPGRADE', 0, 'Five skinks with javelins'),
('Stegadon', 'Sharpened Horns', 'UNIT_EQUIPMENT', 25, 'More impact hits'),
('Salamander Hunting Pack', 'Breathes Fire', 'FREE_UPGRADE', 0, 'Flaming spit'),
('Razordon Hunting Pack', 'Spikes', 'FREE_UPGRADE', 0, 'Volley of spikes'),
('Ancient Stegadon', 'Skink Crew', 'FREE_UPGRADE', 0, 'Five skinks with javelins'),
('Ancient Stegadon', 'Engine of the Gods', 'UNIT_EQUIPMENT', 140, 'Arcane device of the Old Ones'),
('Troglodon', 'Venom Spit', 'FREE_UPGRADE', 0, 'Poisoned shooting attack');

INSERT INTO staging_unit_upgrade
SELECT unit.name, item.name, item.upgrade_type, item.points, item.description
FROM staging_unit unit
CROSS JOIN (VALUES
    ('Blade of Realities', 'MAGIC_WEAPON', 50, 'May slay enemies outright'),
    ('Shield of the Mirrored Pool', 'MAGIC_ARMOUR', 30, 'Shield that reflects spells'),
    ('Cube of Darkness', 'MAGIC_ITEM', 35, 'One use: cancels the enemy magic phase'),
    ('Plaque of Tepok', 'MAGIC_ITEM', 20, 'Wizard knows an extra spell')
) AS item(name, upgrade_type, points, description)
WHERE unit.unit_type IN ('LORDS', 'HERO') AND unit.name <> 'Slann Mage-Priest';

INSERT INTO staging_unit_upgrade
SELECT unit_name, banner.name, 'MAGIC_BANNER', banner.points, banner.description
FROM (VALUES ('Scar-Veteran'), ('Skink Chief'), ('Saurus Warriors'), ('Temple Guard'), ('Cold One Riders')) AS bearers(unit_name)
CROSS JOIN (VALUES
    ('Sun Standard of Chotec', 50, 'Blinding light, enemies have -1 to hit'),
    ('Skavenpelt Banner', 20, 'The unit has Hatred (Skaven)')
) AS banner(name, points, description);

SELECT roster_load_army('LIZARDMEN');
