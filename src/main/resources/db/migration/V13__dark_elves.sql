SELECT roster_begin_army();

INSERT INTO staging_unit VALUES
('Dreadlord', 140, 1, 'LORDS', 5, 7, 6, 4, 3, 3, 8, 4, 10, 0, 0),
('Supreme Sorceress', 200, 1, 'LORDS', 5, 4, 4, 3, 3, 3, 5, 1, 9, 0, 0),
('Master', 70, 1, 'HERO', 5, 6, 6, 4, 3, 2, 7, 3, 9, 0, 0),
('Sorceress', 85, 1, 'HERO', 5, 4, 4, 3, 3, 2, 5, 1, 8, 0, 0),
('Death Hag', 85, 1, 'HERO', 5, 6, 6, 4, 3, 2, 6, 3, 8, 0, 0),
('Khainite Assassin', 90, 1, 'HERO', 5, 9, 9, 4, 3, 2, 10, 3, 10, 0, 0),
('Dreadspears', 9, 10, 'CORE', 5, 4, 4, 3, 3, 1, 5, 1, 8, 0, 0),
('Bleakswords', 9, 10, 'CORE', 5, 4, 4, 3, 3, 1, 5, 1, 8, 0, 0),
('Darkshards', 10, 10, 'CORE', 5, 4, 4, 3, 3, 1, 5, 1, 8, 0, 0),
('Black Ark Corsairs', 11, 10, 'CORE', 5, 4, 4, 3, 3, 1, 5, 1, 8, 0, 0),
('Witch Elves', 10, 10, 'SPECIAL', 5, 4, 4, 3, 3, 1, 6, 1, 8, 0, 0),
('Executioners', 13, 10, 'SPECIAL', 5, 5, 4, 3, 3, 1, 5, 1, 8, 0, 0),
('Black Guard', 15, 10, 'SPECIAL', 5, 5, 4, 3, 3, 1, 6, 1, 9, 0, 0),
('Dark Riders', 17, 5, 'SPECIAL', 5, 4, 4, 3, 3, 1, 5, 1, 8, 0, 0),
('Shades', 16, 5, 'SPECIAL', 5, 4, 5, 3, 3, 1, 5, 1, 8, 0, 0),
('Cold One Knights', 28, 5, 'SPECIAL', 5, 5, 4, 4, 3, 1, 6, 1, 9, 0, 0),
('Cold One Chariot', 100, 1, 'SPECIAL', 0, 0, 0, 5, 5, 4, 0, 0, 0, 4, 0),
('Scourgerunner Chariot', 100, 1, 'SPECIAL', 0, 0, 0, 4, 4, 4, 0, 0, 0, 5, 0),
('Reaper Bolt Thrower', 70, 1, 'RARE', 0, 0, 0, 0, 7, 3, 0, 0, 0, 0, 0),
('War Hydra', 175, 1, 'RARE', 6, 4, 0, 5, 5, 5, 2, 5, 6, 4, 0),
('Kharibdyss', 160, 1, 'RARE', 6, 5, 0, 7, 5, 5, 4, 5, 6, 4, 0),
('Bloodwrack Shrine', 175, 1, 'RARE', 0, 0, 0, 5, 5, 5, 0, 0, 0, 5, 0);

INSERT INTO staging_unit_upgrade
SELECT name, 'Hatred', 'FREE_UPGRADE', 0, 'Re-roll misses in the first round of combat'
FROM staging_unit
WHERE name NOT IN ('Reaper Bolt Thrower', 'War Hydra', 'Kharibdyss', 'Bloodwrack Shrine', 'Cold One Chariot', 'Scourgerunner Chariot');

INSERT INTO staging_unit_upgrade VALUES
('Dreadlord', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Dreadlord', 'Great weapon', 'UNIT_EQUIPMENT', 6, '+2 Strength, always strikes last'),
('Dreadlord', 'Additional hand weapon', 'UNIT_EQUIPMENT', 3, '+1 Attack'),
('Dreadlord', 'Lance', 'UNIT_EQUIPMENT', 6, '+2 Strength on the charge, mounted only'),
('Dreadlord', 'Repeater Crossbow', 'UNIT_EQUIPMENT', 10, 'Fires two shots'),
('Dreadlord', 'Heavy Armour', 'UNIT_EQUIPMENT', 6, 'Heavy armour, 5+ armour save'),
('Dreadlord', 'Sea Dragon Cloak', 'UNIT_EQUIPMENT', 6, '+1 armour save, better against shooting'),
('Dreadlord', 'Shield', 'UNIT_EQUIPMENT', 3, '+1 armour save'),
('Dreadlord', 'Dark Steed', 'MOUNT', 15, 'Dark steed'),
('Dreadlord', 'Cold One', 'MOUNT', 30, 'Reptilian mount'),
('Dreadlord', 'Dark Pegasus', 'MOUNT', 50, 'Flying mount'),
('Dreadlord', 'Manticore', 'MOUNT', 200, 'Monstrous flying mount'),
('Dreadlord', 'Black Dragon', 'MOUNT', 320, 'Dragon'),

('Supreme Sorceress', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Supreme Sorceress', 'Level 3 Wizard', 'FREE_UPGRADE', 0, 'Level 3 Wizard'),
('Supreme Sorceress', 'Level 4 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 4 Wizard'),
('Supreme Sorceress', 'Dark Steed', 'MOUNT', 15, 'Dark steed'),
('Supreme Sorceress', 'Dark Pegasus', 'MOUNT', 50, 'Flying mount'),
('Supreme Sorceress', 'Black Dragon', 'MOUNT', 320, 'Dragon'),

('Master', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Master', 'Great weapon', 'UNIT_EQUIPMENT', 4, '+2 Strength, always strikes last'),
('Master', 'Additional hand weapon', 'UNIT_EQUIPMENT', 2, '+1 Attack'),
('Master', 'Lance', 'UNIT_EQUIPMENT', 4, '+2 Strength on the charge, mounted only'),
('Master', 'Repeater Crossbow', 'UNIT_EQUIPMENT', 10, 'Fires two shots'),
('Master', 'Heavy Armour', 'UNIT_EQUIPMENT', 4, 'Heavy armour, 5+ armour save'),
('Master', 'Sea Dragon Cloak', 'UNIT_EQUIPMENT', 4, '+1 armour save, better against shooting'),
('Master', 'Shield', 'UNIT_EQUIPMENT', 2, '+1 armour save'),
('Master', 'Battle Standard', 'BSB', 25, 'Battle Standard Bearer'),
('Master', 'Dark Steed', 'MOUNT', 15, 'Dark steed'),
('Master', 'Cold One', 'MOUNT', 30, 'Reptilian mount'),
('Master', 'Dark Pegasus', 'MOUNT', 50, 'Flying mount'),

('Sorceress', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Sorceress', 'Level 1 Wizard', 'FREE_UPGRADE', 0, 'Level 1 Wizard'),
('Sorceress', 'Level 2 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 2 Wizard'),
('Sorceress', 'Dark Steed', 'MOUNT', 15, 'Dark steed'),
('Sorceress', 'Dark Pegasus', 'MOUNT', 50, 'Flying mount'),

('Death Hag', 'Additional hand weapon', 'FREE_UPGRADE', 0, '+1 Attack'),
('Death Hag', 'Poisoned Attacks', 'FREE_UPGRADE', 0, 'Poisoned Attacks'),
('Death Hag', 'Frenzy', 'FREE_UPGRADE', 0, '+1 Attack, must charge'),
('Death Hag', 'Battle Standard', 'BSB', 25, 'Battle Standard Bearer'),
('Death Hag', 'Cauldron of Blood', 'MOUNT', 100, 'Altar of Khaine'),

('Khainite Assassin', 'Additional hand weapon', 'FREE_UPGRADE', 0, '+1 Attack'),
('Khainite Assassin', 'Throwing stars', 'FREE_UPGRADE', 0, 'Thrown weapons'),
('Khainite Assassin', 'Poisoned Attacks', 'FREE_UPGRADE', 0, 'Poisoned Attacks'),
('Khainite Assassin', 'Hidden', 'FREE_UPGRADE', 0, 'Hides inside another unit'),

('Dreadspears', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Dreadspears', 'Spear', 'FREE_UPGRADE', 0, 'Fights in an extra rank'),
('Dreadspears', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Dreadspears', 'Shield', 'FREE_UPGRADE', 0, '+1 armour save'),
('Dreadspears', 'Lordling', 'COMMAND_GROUP', 10, 'Champion'),
('Dreadspears', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Dreadspears', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Bleakswords', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Bleakswords', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Bleakswords', 'Shield', 'FREE_UPGRADE', 0, '+1 armour save'),
('Bleakswords', 'Lordling', 'COMMAND_GROUP', 10, 'Champion'),
('Bleakswords', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Bleakswords', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Darkshards', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Darkshards', 'Repeater Crossbow', 'FREE_UPGRADE', 0, 'Fires two shots'),
('Darkshards', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Darkshards', 'Shield', 'UNIT_EQUIPMENT', 1, '+1 armour save'),
('Darkshards', 'Guardmaster', 'COMMAND_GROUP', 10, 'Champion'),
('Darkshards', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Darkshards', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Black Ark Corsairs', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Black Ark Corsairs', 'Additional hand weapon', 'FREE_UPGRADE', 0, '+1 Attack'),
('Black Ark Corsairs', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Black Ark Corsairs', 'Sea Dragon Cloak', 'FREE_UPGRADE', 0, '+1 armour save, better against shooting'),
('Black Ark Corsairs', 'Repeater Handbow', 'UNIT_EQUIPMENT', 0, 'Replaces the second hand weapon'),
('Black Ark Corsairs', 'Reaver', 'COMMAND_GROUP', 10, 'Champion'),
('Black Ark Corsairs', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Black Ark Corsairs', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Witch Elves', 'Additional hand weapon', 'FREE_UPGRADE', 0, '+1 Attack'),
('Witch Elves', 'Poisoned Attacks', 'FREE_UPGRADE', 0, 'Poisoned Attacks'),
('Witch Elves', 'Frenzy', 'FREE_UPGRADE', 0, '+1 Attack, must charge'),
('Witch Elves', 'Hag', 'COMMAND_GROUP', 10, 'Champion'),
('Witch Elves', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Witch Elves', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Executioners', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Executioners', 'Great weapon', 'FREE_UPGRADE', 0, '+2 Strength, always strikes last'),
('Executioners', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Executioners', 'Killing Blow', 'FREE_UPGRADE', 0, 'A 6 to wound slays outright'),
('Executioners', 'Draich-master', 'COMMAND_GROUP', 10, 'Champion'),
('Executioners', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Executioners', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Black Guard', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Black Guard', 'Halberd', 'FREE_UPGRADE', 0, '+1 Strength in close combat'),
('Black Guard', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Black Guard', 'Stubborn', 'FREE_UPGRADE', 0, 'Always uses its own Leadership'),
('Black Guard', 'Tower Master', 'COMMAND_GROUP', 10, 'Champion'),
('Black Guard', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Black Guard', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Dark Riders', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Dark Riders', 'Spear', 'FREE_UPGRADE', 0, 'Spear'),
('Dark Riders', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Dark Riders', 'Dark Steed', 'FREE_UPGRADE', 0, 'Rides a dark steed'),
('Dark Riders', 'Fast Cavalry', 'FREE_UPGRADE', 0, 'Free reform and vanguard'),
('Dark Riders', 'Repeater Crossbow', 'UNIT_EQUIPMENT', 3, 'Fires two shots'),
('Dark Riders', 'Herald', 'COMMAND_GROUP', 10, 'Champion'),
('Dark Riders', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Dark Riders', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Shades', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Shades', 'Repeater Crossbow', 'FREE_UPGRADE', 0, 'Fires two shots'),
('Shades', 'Scouts', 'FREE_UPGRADE', 0, 'Deploys as Scouts'),
('Shades', 'Skirmish', 'FREE_UPGRADE', 0, 'Loose formation'),
('Shades', 'Additional hand weapon', 'UNIT_EQUIPMENT', 1, '+1 Attack'),
('Shades', 'Light Armour', 'UNIT_EQUIPMENT', 1, 'Light armour, 6+ armour save'),
('Shades', 'Bloodshade', 'COMMAND_GROUP', 10, 'Champion'),

('Cold One Knights', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Cold One Knights', 'Lance', 'FREE_UPGRADE', 0, '+2 Strength on the charge'),
('Cold One Knights', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Cold One Knights', 'Shield', 'FREE_UPGRADE', 0, '+1 armour save'),
('Cold One Knights', 'Cold One', 'FREE_UPGRADE', 0, 'Rides a cold one'),
('Cold One Knights', 'Dread Knight', 'COMMAND_GROUP', 10, 'Champion'),
('Cold One Knights', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Cold One Knights', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Cold One Chariot', 'Chariot Crew', 'FREE_UPGRADE', 0, 'Two dark elf crew and two cold ones'),
('Scourgerunner Chariot', 'Chariot Crew', 'FREE_UPGRADE', 0, 'Two beastmasters and two dark steeds'),
('Reaper Bolt Thrower', 'Crew', 'FREE_UPGRADE', 0, 'Two dark elf crew'),
('War Hydra', 'Breathes Fire', 'FREE_UPGRADE', 0, 'Flaming breath weapon'),
('War Hydra', 'Regenerate', 'FREE_UPGRADE', 0, 'Regeneration save'),
('Kharibdyss', 'Terror', 'FREE_UPGRADE', 0, 'Causes Terror'),
('Bloodwrack Shrine', 'Bloodwrack Medusa', 'FREE_UPGRADE', 0, 'Petrifying stare');

INSERT INTO staging_unit_upgrade
SELECT unit.name, item.name, item.upgrade_type, item.points, item.description
FROM staging_unit unit
CROSS JOIN (VALUES
    ('Hydra Blade', 'MAGIC_WEAPON', 100, 'Many extra attacks'),
    ('Chillblade', 'MAGIC_WEAPON', 50, 'Wounded enemies may be frozen'),
    ('Armour of Darkness', 'MAGIC_ARMOUR', 30, 'Heavy armour that resists magic'),
    ('Pendant of Khaeleth', 'MAGIC_ITEM', 50, 'Ward save against high Strength hits'),
    ('Ring of Hotek', 'MAGIC_ITEM', 25, 'Enemy wizards nearby risk miscasts')
) AS item(name, upgrade_type, points, description)
WHERE unit.unit_type IN ('LORDS', 'HERO') AND unit.name <> 'Khainite Assassin';

INSERT INTO staging_unit_upgrade
SELECT unit_name, 'Banner of Murder', 'MAGIC_BANNER', 30, 'The unit has Armour Piercing'
FROM (VALUES ('Master'), ('Dreadspears'), ('Black Guard'), ('Executioners'), ('Cold One Knights')) AS bearers(unit_name);

SELECT roster_load_army('DARK_ELVES');
