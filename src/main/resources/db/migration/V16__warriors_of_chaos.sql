SELECT roster_begin_army();

INSERT INTO staging_unit VALUES
('Chaos Lord', 210, 1, 'LORDS', 4, 8, 3, 5, 5, 3, 8, 5, 9, 0, 0),
('Chaos Sorcerer Lord', 220, 1, 'LORDS', 4, 5, 3, 4, 5, 3, 5, 3, 8, 0, 0),
('Daemon Prince', 210, 1, 'LORDS', 6, 8, 0, 6, 5, 4, 7, 5, 9, 0, 0),
('Exalted Hero', 105, 1, 'HERO', 4, 7, 3, 5, 5, 2, 7, 4, 9, 0, 0),
('Chaos Sorcerer', 105, 1, 'HERO', 4, 5, 3, 4, 4, 2, 5, 2, 8, 0, 0),
('Chaos Warriors', 15, 10, 'CORE', 4, 5, 3, 4, 4, 1, 5, 1, 8, 0, 0),
('Chaos Marauders', 6, 10, 'CORE', 4, 4, 3, 3, 3, 1, 4, 1, 7, 0, 0),
('Marauder Horsemen', 16, 5, 'CORE', 4, 4, 3, 3, 3, 1, 4, 1, 7, 0, 0),
('Chaos Warhounds', 6, 5, 'CORE', 7, 4, 0, 3, 3, 1, 3, 1, 5, 0, 0),
('Chaos Knights', 50, 5, 'SPECIAL', 4, 5, 3, 4, 4, 1, 5, 2, 8, 0, 0),
('Chosen', 20, 5, 'SPECIAL', 4, 6, 3, 4, 4, 1, 5, 2, 8, 0, 0),
('Forsaken', 19, 5, 'SPECIAL', 5, 3, 0, 4, 4, 1, 5, 4, 8, 0, 0),
('Chaos Chariot', 120, 1, 'SPECIAL', 0, 0, 0, 5, 5, 4, 0, 0, 0, 4, 0),
('Gorebeast Chariot', 165, 1, 'SPECIAL', 0, 0, 0, 6, 5, 5, 0, 0, 0, 4, 0),
('Chaos Trolls', 35, 3, 'SPECIAL', 6, 3, 1, 5, 4, 3, 1, 3, 4, 0, 0),
('Chaos Ogres', 35, 3, 'SPECIAL', 6, 3, 2, 4, 4, 3, 2, 3, 7, 0, 0),
('Dragon Ogres', 70, 3, 'RARE', 7, 4, 2, 5, 5, 4, 2, 3, 8, 4, 0),
('Skullcrushers of Khorne', 65, 3, 'RARE', 4, 5, 3, 4, 4, 1, 5, 2, 8, 0, 0),
('Chaos Giant', 200, 1, 'RARE', 6, 3, 3, 6, 5, 6, 3, 1, 10, 0, 0),
('Hellcannon', 210, 1, 'RARE', 3, 4, 3, 6, 6, 6, 1, 3, 4, 4, 0),
('Slaughterbrute', 210, 1, 'RARE', 7, 5, 0, 6, 6, 6, 4, 5, 7, 0, 0),
('Mutalith Vortex Beast', 215, 1, 'RARE', 6, 3, 0, 6, 6, 6, 3, 5, 7, 4, 0);

INSERT INTO staging_unit_upgrade
SELECT unit.name,
       mark.name,
       'UNIT_EQUIPMENT',
       CASE WHEN unit.unit_type IN ('LORDS', 'HERO') THEN 15 ELSE 2 END,
       mark.description
FROM staging_unit unit
CROSS JOIN (VALUES
    ('Mark of Khorne', 'Frenzy and hatred'),
    ('Mark of Nurgle', 'Enemies have -1 to hit with shooting'),
    ('Mark of Slaanesh', 'Immune to Psychology'),
    ('Mark of Tzeentch', '+1 ward save, at least 6+')
) AS mark(name, description)
WHERE unit.name IN ('Chaos Lord', 'Chaos Sorcerer Lord', 'Exalted Hero', 'Chaos Sorcerer', 'Chaos Warriors', 'Chaos Marauders',
                    'Marauder Horsemen', 'Chaos Knights', 'Chosen', 'Forsaken');

INSERT INTO staging_unit_upgrade VALUES
('Chaos Lord', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Chaos Lord', 'Chaos Armour', 'FREE_UPGRADE', 0, '4+ armour save'),
('Chaos Lord', 'Great weapon', 'UNIT_EQUIPMENT', 6, '+2 Strength, always strikes last'),
('Chaos Lord', 'Halberd', 'UNIT_EQUIPMENT', 6, '+1 Strength'),
('Chaos Lord', 'Additional hand weapon', 'UNIT_EQUIPMENT', 3, '+1 Attack'),
('Chaos Lord', 'Lance', 'UNIT_EQUIPMENT', 6, '+2 Strength on the charge, mounted only'),
('Chaos Lord', 'Shield', 'UNIT_EQUIPMENT', 3, '+1 armour save'),
('Chaos Lord', 'Barded Chaos Steed', 'MOUNT', 25, 'Chaos steed with barding'),
('Chaos Lord', 'Daemonic Mount', 'MOUNT', 60, 'Daemonic steed'),
('Chaos Lord', 'Juggernaut of Khorne', 'MOUNT', 75, 'Brass daemon engine'),
('Chaos Lord', 'Manticore', 'MOUNT', 200, 'Monstrous flying mount'),
('Chaos Lord', 'Chaos Dragon', 'MOUNT', 380, 'Two-headed dragon'),

('Chaos Sorcerer Lord', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Chaos Sorcerer Lord', 'Chaos Armour', 'FREE_UPGRADE', 0, '4+ armour save'),
('Chaos Sorcerer Lord', 'Level 3 Wizard', 'FREE_UPGRADE', 0, 'Level 3 Wizard'),
('Chaos Sorcerer Lord', 'Level 4 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 4 Wizard'),
('Chaos Sorcerer Lord', 'Barded Chaos Steed', 'MOUNT', 25, 'Chaos steed with barding'),
('Chaos Sorcerer Lord', 'Disc of Tzeentch', 'MOUNT', 30, 'Flying daemonic disc'),
('Chaos Sorcerer Lord', 'Manticore', 'MOUNT', 200, 'Monstrous flying mount'),

('Daemon Prince', 'Daemonic', 'FREE_UPGRADE', 0, '5+ ward save, Fear, Immune to Psychology'),
('Daemon Prince', 'Fly', 'UNIT_EQUIPMENT', 40, 'Daemonic wings'),
('Daemon Prince', 'Chaos Armour', 'UNIT_EQUIPMENT', 20, '4+ armour save'),
('Daemon Prince', 'Level 1 Wizard', 'UNIT_EQUIPMENT', 35, 'Becomes a Level 1 Wizard'),

('Exalted Hero', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Exalted Hero', 'Chaos Armour', 'FREE_UPGRADE', 0, '4+ armour save'),
('Exalted Hero', 'Great weapon', 'UNIT_EQUIPMENT', 4, '+2 Strength, always strikes last'),
('Exalted Hero', 'Halberd', 'UNIT_EQUIPMENT', 4, '+1 Strength'),
('Exalted Hero', 'Additional hand weapon', 'UNIT_EQUIPMENT', 2, '+1 Attack'),
('Exalted Hero', 'Lance', 'UNIT_EQUIPMENT', 4, '+2 Strength on the charge, mounted only'),
('Exalted Hero', 'Shield', 'UNIT_EQUIPMENT', 2, '+1 armour save'),
('Exalted Hero', 'Battle Standard', 'BSB', 25, 'Battle Standard Bearer'),
('Exalted Hero', 'Barded Chaos Steed', 'MOUNT', 25, 'Chaos steed with barding'),
('Exalted Hero', 'Juggernaut of Khorne', 'MOUNT', 75, 'Brass daemon engine'),

('Chaos Sorcerer', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Chaos Sorcerer', 'Chaos Armour', 'FREE_UPGRADE', 0, '4+ armour save'),
('Chaos Sorcerer', 'Level 1 Wizard', 'FREE_UPGRADE', 0, 'Level 1 Wizard'),
('Chaos Sorcerer', 'Level 2 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 2 Wizard'),
('Chaos Sorcerer', 'Barded Chaos Steed', 'MOUNT', 25, 'Chaos steed with barding'),
('Chaos Sorcerer', 'Disc of Tzeentch', 'MOUNT', 30, 'Flying daemonic disc'),

('Chaos Warriors', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Chaos Warriors', 'Chaos Armour', 'FREE_UPGRADE', 0, '4+ armour save'),
('Chaos Warriors', 'Shield', 'UNIT_EQUIPMENT', 1, '+1 armour save'),
('Chaos Warriors', 'Halberd', 'UNIT_EQUIPMENT', 1, '+1 Strength'),
('Chaos Warriors', 'Great weapon', 'UNIT_EQUIPMENT', 2, '+2 Strength, always strikes last'),
('Chaos Warriors', 'Additional hand weapon', 'UNIT_EQUIPMENT', 2, '+1 Attack'),
('Chaos Warriors', 'Aspiring Champion', 'COMMAND_GROUP', 16, 'Champion'),
('Chaos Warriors', 'Standard Bearer', 'COMMAND_GROUP', 12, 'Standard Bearer'),
('Chaos Warriors', 'Musician', 'COMMAND_GROUP', 6, 'Musician'),

('Chaos Marauders', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Chaos Marauders', 'Light Armour', 'UNIT_EQUIPMENT', 1, 'Light armour, 6+ armour save'),
('Chaos Marauders', 'Shield', 'UNIT_EQUIPMENT', 1, '+1 armour save'),
('Chaos Marauders', 'Flail', 'UNIT_EQUIPMENT', 1, '+2 Strength in the first round of combat'),
('Chaos Marauders', 'Great weapon', 'UNIT_EQUIPMENT', 2, '+2 Strength, always strikes last'),
('Chaos Marauders', 'Marauder Chieftain', 'COMMAND_GROUP', 10, 'Champion'),
('Chaos Marauders', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Chaos Marauders', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Marauder Horsemen', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Marauder Horsemen', 'Chaos Steed', 'FREE_UPGRADE', 0, 'Rides a chaos steed'),
('Marauder Horsemen', 'Fast Cavalry', 'FREE_UPGRADE', 0, 'Free reform and vanguard'),
('Marauder Horsemen', 'Light Armour', 'UNIT_EQUIPMENT', 1, 'Light armour, 6+ armour save'),
('Marauder Horsemen', 'Shield', 'UNIT_EQUIPMENT', 1, '+1 armour save'),
('Marauder Horsemen', 'Throwing Axes', 'UNIT_EQUIPMENT', 2, 'Short range thrown weapon'),
('Marauder Horsemen', 'Marauder Horsemaster', 'COMMAND_GROUP', 10, 'Champion'),
('Marauder Horsemen', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Marauder Horsemen', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Chaos Warhounds', 'Fast Cavalry', 'FREE_UPGRADE', 0, 'Free reform and vanguard'),
('Chaos Warhounds', 'Poisoned Attacks', 'UNIT_EQUIPMENT', 1, 'Poisoned Attacks'),

('Chaos Knights', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Chaos Knights', 'Chaos Armour', 'FREE_UPGRADE', 0, '4+ armour save'),
('Chaos Knights', 'Shield', 'FREE_UPGRADE', 0, '+1 armour save'),
('Chaos Knights', 'Barded Chaos Steed', 'FREE_UPGRADE', 0, 'Rides a chaos steed with barding'),
('Chaos Knights', 'Lance', 'UNIT_EQUIPMENT', 2, '+2 Strength on the charge'),
('Chaos Knights', 'Ensorcelled Weapons', 'UNIT_EQUIPMENT', 3, 'Magic weapons'),
('Chaos Knights', 'Doom Knight', 'COMMAND_GROUP', 20, 'Champion'),
('Chaos Knights', 'Standard Bearer', 'COMMAND_GROUP', 20, 'Standard Bearer'),
('Chaos Knights', 'Musician', 'COMMAND_GROUP', 10, 'Musician'),

('Chosen', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Chosen', 'Chaos Armour', 'FREE_UPGRADE', 0, '4+ armour save'),
('Chosen', 'Shield', 'UNIT_EQUIPMENT', 1, '+1 armour save'),
('Chosen', 'Halberd', 'UNIT_EQUIPMENT', 1, '+1 Strength'),
('Chosen', 'Great weapon', 'UNIT_EQUIPMENT', 2, '+2 Strength, always strikes last'),
('Chosen', 'Additional hand weapon', 'UNIT_EQUIPMENT', 2, '+1 Attack'),
('Chosen', 'Chosen Champion', 'COMMAND_GROUP', 16, 'Champion'),
('Chosen', 'Standard Bearer', 'COMMAND_GROUP', 12, 'Standard Bearer'),
('Chosen', 'Musician', 'COMMAND_GROUP', 6, 'Musician'),

('Forsaken', 'Additional hand weapon', 'FREE_UPGRADE', 0, '+1 Attack'),
('Forsaken', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Forsaken', 'Frenzy', 'FREE_UPGRADE', 0, '+1 Attack, must charge'),

('Chaos Chariot', 'Chariot Crew', 'FREE_UPGRADE', 0, 'Two chaos warriors and two steeds'),
('Gorebeast Chariot', 'Chariot Crew', 'FREE_UPGRADE', 0, 'Two chaos warriors and a gorebeast'),
('Chaos Trolls', 'Regenerate', 'FREE_UPGRADE', 0, 'Regeneration save'),
('Chaos Trolls', 'Stupidity', 'FREE_UPGRADE', 0, 'May wander around aimlessly'),
('Chaos Ogres', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Chaos Ogres', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Chaos Ogres', 'Great weapon', 'UNIT_EQUIPMENT', 4, '+2 Strength, always strikes last'),
('Chaos Ogres', 'Additional hand weapon', 'UNIT_EQUIPMENT', 4, '+1 Attack'),
('Chaos Ogres', 'Ogre Champion', 'COMMAND_GROUP', 10, 'Champion'),
('Dragon Ogres', 'Great weapon', 'UNIT_EQUIPMENT', 4, '+2 Strength, always strikes last'),
('Dragon Ogres', 'Dragon Ogre Champion', 'COMMAND_GROUP', 15, 'Champion'),
('Skullcrushers of Khorne', 'Chaos Armour', 'FREE_UPGRADE', 0, '4+ armour save'),
('Skullcrushers of Khorne', 'Shield', 'FREE_UPGRADE', 0, '+1 armour save'),
('Skullcrushers of Khorne', 'Juggernaut of Khorne', 'FREE_UPGRADE', 0, 'Rides a brass daemon engine'),
('Skullcrushers of Khorne', 'Skullhunter', 'COMMAND_GROUP', 20, 'Champion'),
('Chaos Giant', 'Stubborn', 'FREE_UPGRADE', 0, 'Always uses its own Leadership'),
('Chaos Giant', 'Terror', 'FREE_UPGRADE', 0, 'Causes Terror'),
('Hellcannon', 'Crew', 'FREE_UPGRADE', 0, 'Three chaos dwarf handlers'),
('Slaughterbrute', 'Terror', 'FREE_UPGRADE', 0, 'Causes Terror'),
('Mutalith Vortex Beast', 'Terror', 'FREE_UPGRADE', 0, 'Causes Terror');

INSERT INTO staging_unit_upgrade
SELECT unit.name, item.name, item.upgrade_type, item.points, item.description
FROM staging_unit unit
CROSS JOIN (VALUES
    ('Hellfire Sword', 'MAGIC_WEAPON', 65, 'Burning blade, wounds cause extra damage'),
    ('Sword of Change', 'MAGIC_WEAPON', 30, 'Slain enemies turn into spawn'),
    ('Armour of Damnation', 'MAGIC_ARMOUR', 45, 'Chaos armour that curses attackers'),
    ('Helm of Many Eyes', 'MAGIC_ARMOUR', 30, 'Always strikes first'),
    ('Talisman of Corruption', 'MAGIC_ITEM', 35, 'Enemies in contact suffer -1 to wound')
) AS item(name, upgrade_type, points, description)
WHERE unit.unit_type IN ('LORDS', 'HERO');

INSERT INTO staging_unit_upgrade
SELECT unit_name, banner.name, 'MAGIC_BANNER', banner.points, banner.description
FROM (VALUES ('Exalted Hero'), ('Chaos Warriors'), ('Chosen'), ('Chaos Knights')) AS bearers(unit_name)
CROSS JOIN (VALUES
    ('Banner of Rage', 30, 'The unit has Frenzy'),
    ('Blasted Standard', 50, 'Protection against shooting')
) AS banner(name, points, description);

SELECT roster_load_army('CHAOS_WARRIORS');
