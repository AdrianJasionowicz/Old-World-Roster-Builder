SELECT roster_begin_army();

INSERT INTO staging_unit VALUES
('Tomb King', 135, 1, 'LORDS', 4, 6, 4, 5, 5, 4, 3, 4, 10, 0, 0),
('Liche High Priest', 185, 1, 'LORDS', 4, 3, 3, 3, 4, 3, 3, 1, 8, 0, 0),
('Tomb Herald', 60, 1, 'HERO', 4, 4, 3, 4, 5, 2, 3, 3, 8, 0, 0),
('Liche Priest', 80, 1, 'HERO', 4, 3, 3, 3, 3, 2, 3, 1, 7, 0, 0),
('Necrotect', 50, 1, 'HERO', 4, 3, 3, 4, 4, 2, 3, 2, 8, 0, 0),
('Skeleton Warriors', 4, 10, 'CORE', 4, 2, 2, 3, 3, 1, 2, 1, 5, 0, 0),
('Skeleton Archers', 5, 10, 'CORE', 4, 2, 2, 3, 3, 1, 2, 1, 5, 0, 0),
('Skeleton Horsemen', 12, 5, 'CORE', 4, 2, 2, 3, 3, 1, 2, 1, 5, 0, 0),
('Skeleton Horse Archers', 14, 5, 'CORE', 4, 2, 2, 3, 3, 1, 2, 1, 5, 0, 0),
('Skeleton Chariots', 55, 3, 'CORE', 4, 2, 2, 3, 4, 3, 2, 1, 5, 5, 0),
('Tomb Guard', 11, 10, 'SPECIAL', 4, 3, 3, 4, 4, 1, 3, 1, 8, 0, 0),
('Ushabti', 55, 3, 'SPECIAL', 5, 4, 2, 6, 4, 3, 3, 3, 8, 0, 0),
('Necropolis Knights', 55, 3, 'SPECIAL', 4, 4, 3, 4, 5, 3, 3, 3, 8, 0, 0),
('Sepulchral Stalkers', 55, 3, 'SPECIAL', 7, 3, 3, 4, 4, 3, 3, 3, 8, 0, 0),
('Tomb Scorpion', 85, 1, 'SPECIAL', 7, 4, 0, 5, 5, 4, 3, 4, 8, 4, 0),
('Tomb Swarm', 30, 2, 'SPECIAL', 4, 3, 0, 2, 2, 5, 1, 5, 10, 0, 0),
('Carrion', 25, 3, 'SPECIAL', 2, 3, 0, 4, 4, 2, 3, 2, 4, 0, 0),
('Screaming Skull Catapult', 90, 1, 'RARE', 0, 0, 0, 0, 7, 3, 0, 0, 0, 0, 0),
('Casket of Souls', 135, 1, 'RARE', 0, 0, 0, 0, 6, 5, 0, 0, 0, 0, 0),
('Bone Giant', 175, 1, 'RARE', 6, 3, 1, 6, 5, 6, 1, 4, 8, 4, 0),
('Hierotitan', 175, 1, 'RARE', 6, 3, 1, 6, 5, 6, 1, 3, 8, 4, 0),
('Necrosphinx', 225, 1, 'RARE', 6, 4, 0, 5, 8, 5, 1, 5, 8, 3, 0),
('Khemrian Warsphinx', 210, 1, 'RARE', 6, 4, 0, 5, 8, 5, 1, 4, 8, 3, 0);

INSERT INTO staging_unit_upgrade
SELECT name, 'Nehekharan Undead', 'FREE_UPGRADE', 0, 'Fear, Unbreakable, crumbles without the hierophant'
FROM staging_unit;

INSERT INTO staging_unit_upgrade VALUES
('Tomb King', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Tomb King', 'My Will Be Done', 'FREE_UPGRADE', 0, 'Nearby units use his Weapon Skill'),
('Tomb King', 'Great weapon', 'UNIT_EQUIPMENT', 6, '+2 Strength, always strikes last'),
('Tomb King', 'Flail', 'UNIT_EQUIPMENT', 6, '+2 Strength in the first round of combat'),
('Tomb King', 'Light Armour', 'UNIT_EQUIPMENT', 3, 'Light armour, 6+ armour save'),
('Tomb King', 'Shield', 'UNIT_EQUIPMENT', 3, '+1 armour save'),
('Tomb King', 'Skeletal Steed', 'MOUNT', 15, 'Skeletal steed'),
('Tomb King', 'Chariot', 'MOUNT', 55, 'Skeleton chariot'),
('Tomb King', 'Khemrian Warsphinx', 'MOUNT', 210, 'Monstrous sphinx'),

('Liche High Priest', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Liche High Priest', 'Level 3 Wizard', 'FREE_UPGRADE', 0, 'Level 3 Wizard'),
('Liche High Priest', 'Level 4 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 4 Wizard'),
('Liche High Priest', 'Skeletal Steed', 'MOUNT', 15, 'Skeletal steed'),

('Tomb Herald', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Tomb Herald', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Tomb Herald', 'Killing Blow', 'FREE_UPGRADE', 0, 'A 6 to wound slays outright'),
('Tomb Herald', 'Great weapon', 'UNIT_EQUIPMENT', 4, '+2 Strength, always strikes last'),
('Tomb Herald', 'Shield', 'UNIT_EQUIPMENT', 2, '+1 armour save'),
('Tomb Herald', 'Battle Standard', 'BSB', 25, 'Battle Standard Bearer'),
('Tomb Herald', 'Skeletal Steed', 'MOUNT', 15, 'Skeletal steed'),
('Tomb Herald', 'Chariot', 'MOUNT', 55, 'Skeleton chariot'),

('Liche Priest', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Liche Priest', 'Level 1 Wizard', 'FREE_UPGRADE', 0, 'Level 1 Wizard'),
('Liche Priest', 'Level 2 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 2 Wizard'),
('Liche Priest', 'Skeletal Steed', 'MOUNT', 15, 'Skeletal steed'),

('Necrotect', 'Great weapon', 'FREE_UPGRADE', 0, '+2 Strength, always strikes last'),
('Necrotect', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Necrotect', 'Hatred', 'FREE_UPGRADE', 0, 'Re-roll misses in the first round of combat'),

('Skeleton Warriors', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Skeleton Warriors', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Skeleton Warriors', 'Shield', 'FREE_UPGRADE', 0, '+1 armour save'),
('Skeleton Warriors', 'Spears', 'UNIT_EQUIPMENT', 1, 'Fights in an extra rank'),
('Skeleton Warriors', 'Master of Arms', 'COMMAND_GROUP', 10, 'Champion'),
('Skeleton Warriors', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Skeleton Warriors', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Skeleton Archers', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Skeleton Archers', 'Bow', 'FREE_UPGRADE', 0, 'Bow'),
('Skeleton Archers', 'Light Armour', 'UNIT_EQUIPMENT', 1, 'Light armour, 6+ armour save'),
('Skeleton Archers', 'Master of Arrows', 'COMMAND_GROUP', 10, 'Champion'),
('Skeleton Archers', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Skeleton Archers', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Skeleton Horsemen', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Skeleton Horsemen', 'Spear', 'FREE_UPGRADE', 0, 'Spear'),
('Skeleton Horsemen', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Skeleton Horsemen', 'Shield', 'FREE_UPGRADE', 0, '+1 armour save'),
('Skeleton Horsemen', 'Skeletal Steed', 'FREE_UPGRADE', 0, 'Rides a skeletal steed'),
('Skeleton Horsemen', 'Master of Horse', 'COMMAND_GROUP', 10, 'Champion'),
('Skeleton Horsemen', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Skeleton Horsemen', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Skeleton Horse Archers', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Skeleton Horse Archers', 'Bow', 'FREE_UPGRADE', 0, 'Bow'),
('Skeleton Horse Archers', 'Skeletal Steed', 'FREE_UPGRADE', 0, 'Rides a skeletal steed'),
('Skeleton Horse Archers', 'Fast Cavalry', 'FREE_UPGRADE', 0, 'Free reform and vanguard'),
('Skeleton Horse Archers', 'Master of Scouts', 'COMMAND_GROUP', 10, 'Champion'),

('Skeleton Chariots', 'Spear', 'FREE_UPGRADE', 0, 'Spear'),
('Skeleton Chariots', 'Bow', 'FREE_UPGRADE', 0, 'Bow'),
('Skeleton Chariots', 'Master of Chariots', 'COMMAND_GROUP', 10, 'Champion'),
('Skeleton Chariots', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Skeleton Chariots', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Tomb Guard', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Tomb Guard', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Tomb Guard', 'Shield', 'FREE_UPGRADE', 0, '+1 armour save'),
('Tomb Guard', 'Killing Blow', 'FREE_UPGRADE', 0, 'A 6 to wound slays outright'),
('Tomb Guard', 'Halberd', 'UNIT_EQUIPMENT', 1, '+1 Strength'),
('Tomb Guard', 'Tomb Captain', 'COMMAND_GROUP', 10, 'Champion'),
('Tomb Guard', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Tomb Guard', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Ushabti', 'Great weapon', 'FREE_UPGRADE', 0, '+2 Strength, always strikes last'),
('Ushabti', 'Greatbow', 'UNIT_EQUIPMENT', 0, 'Replaces great weapons, powerful bow'),
('Ushabti', 'Ushabti Ancient', 'COMMAND_GROUP', 10, 'Champion'),
('Necropolis Knights', 'Spear', 'FREE_UPGRADE', 0, 'Spear'),
('Necropolis Knights', 'Shield', 'UNIT_EQUIPMENT', 2, '+1 armour save'),
('Necropolis Knights', 'Necropolis Captain', 'COMMAND_GROUP', 10, 'Champion'),
('Sepulchral Stalkers', 'Halberd', 'FREE_UPGRADE', 0, '+1 Strength in close combat'),
('Sepulchral Stalkers', 'Transfixing Gaze', 'FREE_UPGRADE', 0, 'Petrifying shooting attack'),
('Tomb Scorpion', 'Killing Blow', 'FREE_UPGRADE', 0, 'A 6 to wound slays outright'),
('Tomb Scorpion', 'Entombed Beneath the Sands', 'FREE_UPGRADE', 0, 'Can arrive anywhere'),
('Tomb Swarm', 'Poisoned Attacks', 'FREE_UPGRADE', 0, 'Poisoned Attacks'),
('Carrion', 'Fly', 'FREE_UPGRADE', 0, 'Flying unit'),
('Screaming Skull Catapult', 'Crew', 'FREE_UPGRADE', 0, 'Three skeleton crew'),
('Casket of Souls', 'Keeper of the Casket', 'FREE_UPGRADE', 0, 'Guardian of the casket'),
('Bone Giant', 'Great weapon', 'UNIT_EQUIPMENT', 10, '+2 Strength, always strikes last'),
('Hierotitan', 'Level 1 Wizard', 'FREE_UPGRADE', 0, 'Channels the Lore of Nehekhara'),
('Necrosphinx', 'Fly', 'FREE_UPGRADE', 0, 'Flying monster'),
('Khemrian Warsphinx', 'Howdah Crew', 'FREE_UPGRADE', 0, 'Four skeleton crew');

INSERT INTO staging_unit_upgrade
SELECT unit.name, item.name, item.upgrade_type, item.points, item.description
FROM staging_unit unit
CROSS JOIN (VALUES
    ('Destroyer of Eternities', 'MAGIC_WEAPON', 60, 'Wounds every enemy in contact'),
    ('Blade of Antarhak', 'MAGIC_WEAPON', 30, 'Heals the bearer with every wound caused'),
    ('Armour of the Ages', 'MAGIC_ARMOUR', 30, 'Light armour that grows stronger'),
    ('Golden Death Mask of Kharnut', 'MAGIC_ITEM', 50, 'Enemies nearby cannot use their general''s Leadership'),
    ('Cloak of the Dunes', 'MAGIC_ITEM', 40, 'Bearer can fly through sand')
) AS item(name, upgrade_type, points, description)
WHERE unit.unit_type IN ('LORDS', 'HERO');

INSERT INTO staging_unit_upgrade
SELECT unit_name, banner.name, 'MAGIC_BANNER', banner.points, banner.description
FROM (VALUES ('Tomb Herald'), ('Tomb Guard'), ('Skeleton Warriors'), ('Skeleton Chariots')) AS bearers(unit_name)
CROSS JOIN (VALUES
    ('Standard of the Undying Legion', 50, 'Raises fallen warriors'),
    ('Icon of Rakaph', 25, 'Enemy charges become more difficult')
) AS banner(name, points, description);

SELECT roster_load_army('TOMB_KINGS');
