SELECT roster_begin_army();

INSERT INTO staging_unit VALUES
('Prince', 140, 1, 'LORDS', 5, 7, 6, 4, 3, 3, 8, 4, 10, 0, 0),
('Archmage', 225, 1, 'LORDS', 5, 4, 4, 3, 3, 3, 5, 1, 9, 0, 0),
('Noble', 70, 1, 'HERO', 5, 6, 6, 4, 3, 2, 7, 3, 9, 0, 0),
('Mage', 100, 1, 'HERO', 5, 4, 4, 3, 3, 2, 5, 1, 8, 0, 0),
('Dragon Mage', 200, 1, 'HERO', 5, 4, 4, 3, 3, 2, 5, 1, 8, 0, 0),
('Spearmen', 9, 10, 'CORE', 5, 4, 4, 3, 3, 1, 5, 1, 8, 0, 0),
('Archers', 11, 10, 'CORE', 5, 4, 4, 3, 3, 1, 5, 1, 8, 0, 0),
('Lothern Sea Guard', 13, 10, 'CORE', 5, 4, 4, 3, 3, 1, 5, 1, 8, 0, 0),
('Silver Helms', 21, 5, 'CORE', 5, 4, 4, 3, 3, 1, 5, 1, 8, 0, 0),
('Ellyrian Reavers', 16, 5, 'CORE', 5, 4, 4, 3, 3, 1, 5, 1, 8, 0, 0),
('White Lions', 14, 10, 'SPECIAL', 5, 5, 4, 4, 3, 1, 5, 1, 8, 0, 0),
('Swordmasters', 15, 5, 'SPECIAL', 5, 6, 4, 3, 3, 1, 6, 1, 8, 0, 0),
('Phoenix Guard', 15, 10, 'SPECIAL', 5, 5, 4, 3, 3, 1, 6, 1, 9, 0, 0),
('Dragon Princes', 29, 5, 'SPECIAL', 5, 5, 4, 3, 3, 1, 7, 1, 9, 0, 0),
('Shadow Warriors', 16, 5, 'SPECIAL', 5, 5, 5, 3, 3, 1, 6, 1, 8, 0, 0),
('Tiranoc Chariot', 85, 1, 'SPECIAL', 0, 0, 0, 4, 4, 4, 0, 0, 0, 5, 0),
('Lion Chariot', 140, 1, 'SPECIAL', 0, 0, 0, 5, 4, 4, 0, 0, 0, 4, 0),
('Repeater Bolt Thrower', 100, 1, 'RARE', 0, 0, 0, 0, 7, 3, 0, 0, 0, 0, 0),
('Great Eagle', 50, 1, 'RARE', 2, 5, 0, 4, 4, 3, 4, 2, 8, 0, 0),
('Flamespyre Phoenix', 150, 1, 'RARE', 2, 5, 0, 5, 5, 4, 3, 4, 8, 0, 0),
('Frostheart Phoenix', 225, 1, 'RARE', 2, 6, 0, 6, 6, 5, 4, 5, 9, 0, 0);

INSERT INTO staging_unit_upgrade
SELECT name, 'Always Strikes First', 'FREE_UPGRADE', 0, 'Strikes first in close combat'
FROM staging_unit
WHERE name NOT IN ('Repeater Bolt Thrower', 'Tiranoc Chariot', 'Lion Chariot');

INSERT INTO staging_unit_upgrade VALUES
('Prince', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Prince', 'Great weapon', 'UNIT_EQUIPMENT', 6, '+2 Strength, always strikes last'),
('Prince', 'Halberd', 'UNIT_EQUIPMENT', 6, '+1 Strength'),
('Prince', 'Lance', 'UNIT_EQUIPMENT', 6, '+2 Strength on the charge, mounted only'),
('Prince', 'Longbow', 'UNIT_EQUIPMENT', 6, 'Long range bow'),
('Prince', 'Light Armour', 'UNIT_EQUIPMENT', 3, 'Light armour, 6+ armour save'),
('Prince', 'Heavy Armour', 'UNIT_EQUIPMENT', 6, 'Heavy armour, 5+ armour save'),
('Prince', 'Shield', 'UNIT_EQUIPMENT', 3, '+1 armour save'),
('Prince', 'Barded Elven Steed', 'MOUNT', 24, 'Elven steed with barding'),
('Prince', 'Griffon', 'MOUNT', 150, 'Monstrous flying mount'),
('Prince', 'Sun Dragon', 'MOUNT', 200, 'Young dragon'),
('Prince', 'Moon Dragon', 'MOUNT', 300, 'Mature dragon'),
('Prince', 'Star Dragon', 'MOUNT', 400, 'Ancient dragon'),

('Archmage', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Archmage', 'Level 3 Wizard', 'FREE_UPGRADE', 0, 'Level 3 Wizard'),
('Archmage', 'Level 4 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 4 Wizard'),
('Archmage', 'Barded Elven Steed', 'MOUNT', 24, 'Elven steed with barding'),
('Archmage', 'Moon Dragon', 'MOUNT', 300, 'Mature dragon'),

('Noble', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Noble', 'Great weapon', 'UNIT_EQUIPMENT', 4, '+2 Strength, always strikes last'),
('Noble', 'Lance', 'UNIT_EQUIPMENT', 4, '+2 Strength on the charge, mounted only'),
('Noble', 'Longbow', 'UNIT_EQUIPMENT', 5, 'Long range bow'),
('Noble', 'Light Armour', 'UNIT_EQUIPMENT', 2, 'Light armour, 6+ armour save'),
('Noble', 'Heavy Armour', 'UNIT_EQUIPMENT', 4, 'Heavy armour, 5+ armour save'),
('Noble', 'Shield', 'UNIT_EQUIPMENT', 2, '+1 armour save'),
('Noble', 'Battle Standard', 'BSB', 25, 'Battle Standard Bearer'),
('Noble', 'Barded Elven Steed', 'MOUNT', 24, 'Elven steed with barding'),
('Noble', 'Great Eagle', 'MOUNT', 50, 'Flying mount'),

('Mage', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Mage', 'Level 1 Wizard', 'FREE_UPGRADE', 0, 'Level 1 Wizard'),
('Mage', 'Level 2 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 2 Wizard'),
('Mage', 'Barded Elven Steed', 'MOUNT', 24, 'Elven steed with barding'),

('Dragon Mage', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Dragon Mage', 'Level 1 Wizard', 'FREE_UPGRADE', 0, 'Level 1 Wizard'),
('Dragon Mage', 'Sun Dragon', 'FREE_UPGRADE', 0, 'Rides a young dragon'),
('Dragon Mage', 'Level 2 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 2 Wizard'),

('Spearmen', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Spearmen', 'Spear', 'FREE_UPGRADE', 0, 'Fights in an extra rank'),
('Spearmen', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Spearmen', 'Shield', 'FREE_UPGRADE', 0, '+1 armour save'),
('Spearmen', 'Sentinel', 'COMMAND_GROUP', 10, 'Champion'),
('Spearmen', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Spearmen', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Archers', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Archers', 'Longbow', 'FREE_UPGRADE', 0, 'Long range bow'),
('Archers', 'Light Armour', 'UNIT_EQUIPMENT', 1, 'Light armour, 6+ armour save'),
('Archers', 'Hawkeye', 'COMMAND_GROUP', 10, 'Champion'),
('Archers', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Archers', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Lothern Sea Guard', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Lothern Sea Guard', 'Spear', 'FREE_UPGRADE', 0, 'Fights in an extra rank'),
('Lothern Sea Guard', 'Bow', 'FREE_UPGRADE', 0, 'Bow'),
('Lothern Sea Guard', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Lothern Sea Guard', 'Shield', 'UNIT_EQUIPMENT', 1, '+1 armour save'),
('Lothern Sea Guard', 'Sea Master', 'COMMAND_GROUP', 10, 'Champion'),
('Lothern Sea Guard', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Lothern Sea Guard', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Silver Helms', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Silver Helms', 'Lance', 'FREE_UPGRADE', 0, '+2 Strength on the charge'),
('Silver Helms', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Silver Helms', 'Barded Elven Steed', 'FREE_UPGRADE', 0, 'Rides an elven steed with barding'),
('Silver Helms', 'Shield', 'UNIT_EQUIPMENT', 1, '+1 armour save'),
('Silver Helms', 'High Helm', 'COMMAND_GROUP', 10, 'Champion'),
('Silver Helms', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Silver Helms', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Ellyrian Reavers', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Ellyrian Reavers', 'Spear', 'FREE_UPGRADE', 0, 'Spear'),
('Ellyrian Reavers', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Ellyrian Reavers', 'Elven Steed', 'FREE_UPGRADE', 0, 'Rides an elven steed'),
('Ellyrian Reavers', 'Fast Cavalry', 'FREE_UPGRADE', 0, 'Free reform and vanguard'),
('Ellyrian Reavers', 'Bow', 'UNIT_EQUIPMENT', 2, 'Bow'),
('Ellyrian Reavers', 'Harbinger', 'COMMAND_GROUP', 10, 'Champion'),
('Ellyrian Reavers', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Ellyrian Reavers', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('White Lions', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('White Lions', 'Great weapon', 'FREE_UPGRADE', 0, '+2 Strength, always strikes last'),
('White Lions', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('White Lions', 'Lion Cloak', 'FREE_UPGRADE', 0, 'Better armour against shooting'),
('White Lions', 'Guardian', 'COMMAND_GROUP', 10, 'Champion'),
('White Lions', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('White Lions', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Swordmasters', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Swordmasters', 'Great weapon', 'FREE_UPGRADE', 0, '+2 Strength, strikes first thanks to their skill'),
('Swordmasters', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Swordmasters', 'Bladelord', 'COMMAND_GROUP', 10, 'Champion'),
('Swordmasters', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Swordmasters', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Phoenix Guard', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Phoenix Guard', 'Halberd', 'FREE_UPGRADE', 0, '+1 Strength in close combat'),
('Phoenix Guard', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Phoenix Guard', 'Ward save (4+)', 'FREE_UPGRADE', 0, 'Blessed by Asuryan'),
('Phoenix Guard', 'Keeper of the Flame', 'COMMAND_GROUP', 10, 'Champion'),
('Phoenix Guard', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Phoenix Guard', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Dragon Princes', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Dragon Princes', 'Lance', 'FREE_UPGRADE', 0, '+2 Strength on the charge'),
('Dragon Princes', 'Dragon Armour', 'FREE_UPGRADE', 0, 'Heavy armour proof against fire'),
('Dragon Princes', 'Shield', 'FREE_UPGRADE', 0, '+1 armour save'),
('Dragon Princes', 'Barded Elven Steed', 'FREE_UPGRADE', 0, 'Rides an elven steed with barding'),
('Dragon Princes', 'Drakemaster', 'COMMAND_GROUP', 10, 'Champion'),
('Dragon Princes', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Dragon Princes', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Shadow Warriors', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Shadow Warriors', 'Longbow', 'FREE_UPGRADE', 0, 'Long range bow'),
('Shadow Warriors', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Shadow Warriors', 'Scouts', 'FREE_UPGRADE', 0, 'Deploys as Scouts'),
('Shadow Warriors', 'Skirmish', 'FREE_UPGRADE', 0, 'Loose formation'),
('Shadow Warriors', 'Shadow-walker', 'COMMAND_GROUP', 10, 'Champion'),

('Tiranoc Chariot', 'Chariot Crew', 'FREE_UPGRADE', 0, 'Two elven crew and two steeds'),
('Lion Chariot', 'Chariot Crew', 'FREE_UPGRADE', 0, 'Two White Lion crew and two war lions'),
('Repeater Bolt Thrower', 'Crew', 'FREE_UPGRADE', 0, 'Two elven crew'),
('Great Eagle', 'Fly', 'FREE_UPGRADE', 0, 'Flying monster'),
('Flamespyre Phoenix', 'Fly', 'FREE_UPGRADE', 0, 'Flying monster'),
('Frostheart Phoenix', 'Fly', 'FREE_UPGRADE', 0, 'Flying monster');

INSERT INTO staging_unit_upgrade
SELECT unit.name, item.name, item.upgrade_type, item.points, item.description
FROM staging_unit unit
CROSS JOIN (VALUES
    ('Star Lance', 'MAGIC_WEAPON', 40, 'Lance that pierces all armour'),
    ('Blade of Leaping Gold', 'MAGIC_WEAPON', 30, '+3 Attacks'),
    ('Armour of Caledor', 'MAGIC_ARMOUR', 50, '2+ armour save'),
    ('Vambraces of Defence', 'MAGIC_ITEM', 15, 'Ward save against close combat hits')
) AS item(name, upgrade_type, points, description)
WHERE unit.unit_type IN ('LORDS', 'HERO');

INSERT INTO staging_unit_upgrade
SELECT unit_name, banner.name, 'MAGIC_BANNER', banner.points, banner.description
FROM (VALUES ('Noble'), ('Spearmen'), ('Silver Helms'), ('White Lions'), ('Swordmasters'), ('Phoenix Guard'), ('Dragon Princes')) AS bearers(unit_name)
CROSS JOIN (VALUES
    ('Banner of Ellyrion', 15, 'The unit has Vanguard'),
    ('Banner of the World Dragon', 50, 'Magic Resistance (2)')
) AS banner(name, points, description);

SELECT roster_load_army('HIGH_ELVES');
