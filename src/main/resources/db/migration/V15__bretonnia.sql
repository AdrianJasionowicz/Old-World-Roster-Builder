SELECT roster_begin_army();

INSERT INTO staging_unit VALUES
('Bretonnian Lord', 110, 1, 'LORDS', 4, 6, 3, 4, 4, 3, 6, 4, 9, 0, 0),
('Prophetess of the Lady', 165, 1, 'LORDS', 4, 3, 3, 3, 3, 3, 3, 1, 8, 0, 0),
('Paladin', 60, 1, 'HERO', 4, 5, 3, 4, 4, 2, 5, 3, 8, 0, 0),
('Damsel of the Lady', 65, 1, 'HERO', 4, 3, 3, 3, 3, 2, 3, 1, 7, 0, 0),
('Knights of the Realm', 24, 5, 'CORE', 4, 4, 3, 3, 3, 1, 3, 1, 8, 0, 0),
('Knights Errant', 20, 5, 'CORE', 4, 3, 3, 3, 3, 1, 3, 1, 7, 0, 0),
('Men-at-Arms', 5, 10, 'CORE', 4, 2, 2, 3, 3, 1, 3, 1, 5, 0, 0),
('Peasant Bowmen', 6, 10, 'CORE', 4, 2, 3, 3, 3, 1, 3, 1, 5, 0, 0),
('Questing Knights', 28, 5, 'SPECIAL', 4, 4, 3, 3, 3, 1, 4, 1, 8, 0, 0),
('Pegasus Knights', 50, 3, 'SPECIAL', 4, 4, 3, 3, 3, 1, 4, 1, 8, 0, 0),
('Mounted Yeomen', 12, 5, 'SPECIAL', 4, 2, 3, 3, 3, 1, 3, 1, 5, 0, 0),
('Battle Pilgrims', 7, 10, 'SPECIAL', 4, 3, 3, 3, 3, 1, 3, 1, 8, 0, 0),
('Grail Knights', 33, 3, 'RARE', 4, 5, 3, 4, 3, 1, 5, 2, 8, 0, 0),
('Field Trebuchet', 110, 1, 'RARE', 0, 0, 0, 0, 7, 3, 0, 0, 0, 0, 0);

INSERT INTO staging_unit_upgrade
SELECT name, 'Blessing of the Lady', 'FREE_UPGRADE', 0, 'Prayed before battle: 5+ ward save'
FROM staging_unit
WHERE name IN ('Bretonnian Lord', 'Paladin', 'Knights of the Realm', 'Knights Errant', 'Questing Knights', 'Pegasus Knights', 'Grail Knights');

INSERT INTO staging_unit_upgrade
SELECT unit.name, knight.name, knight.upgrade_type, 0, knight.description
FROM staging_unit unit
CROSS JOIN (VALUES
    ('Hand Weapon', 'FREE_UPGRADE', 'Hand weapon'),
    ('Lance', 'FREE_UPGRADE', '+2 Strength on the charge'),
    ('Heavy Armour', 'FREE_UPGRADE', 'Heavy armour, 5+ armour save'),
    ('Shield', 'FREE_UPGRADE', '+1 armour save'),
    ('Barded Warhorse', 'FREE_UPGRADE', 'Rides a barded warhorse')
) AS knight(name, upgrade_type, description)
WHERE unit.name IN ('Knights of the Realm', 'Knights Errant', 'Questing Knights', 'Grail Knights');

INSERT INTO staging_unit_upgrade
SELECT unit.name, virtue.name, 'UNIT_EQUIPMENT', virtue.points, virtue.description
FROM staging_unit unit
CROSS JOIN (VALUES
    ('Virtue of Purity', 25, 'Ward save improves when praying'),
    ('Virtue of Discipline', 20, 'Nearby knights use his Leadership'),
    ('Virtue of Duty', 15, 'Fights better near the general'),
    ('Virtue of Heroism', 30, 'Wounds big monsters easily'),
    ('Virtue of Empathy', 20, 'Mount strikes as the knight')
) AS virtue(name, points, description)
WHERE unit.name IN ('Bretonnian Lord', 'Paladin');

INSERT INTO staging_unit_upgrade VALUES
('Bretonnian Lord', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Bretonnian Lord', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Bretonnian Lord', 'Great weapon', 'UNIT_EQUIPMENT', 6, '+2 Strength, always strikes last'),
('Bretonnian Lord', 'Lance', 'UNIT_EQUIPMENT', 6, '+2 Strength on the charge, mounted only'),
('Bretonnian Lord', 'Shield', 'UNIT_EQUIPMENT', 3, '+1 armour save'),
('Bretonnian Lord', 'Barded Warhorse', 'MOUNT', 20, 'Barded warhorse'),
('Bretonnian Lord', 'Royal Pegasus', 'MOUNT', 55, 'Flying mount'),
('Bretonnian Lord', 'Hippogryph', 'MOUNT', 150, 'Monstrous flying mount'),

('Prophetess of the Lady', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Prophetess of the Lady', 'Level 3 Wizard', 'FREE_UPGRADE', 0, 'Level 3 Wizard'),
('Prophetess of the Lady', 'Level 4 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 4 Wizard'),
('Prophetess of the Lady', 'Warhorse', 'MOUNT', 12, 'Warhorse'),
('Prophetess of the Lady', 'Unicorn', 'MOUNT', 50, 'Magic resistant mount'),

('Paladin', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Paladin', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Paladin', 'Great weapon', 'UNIT_EQUIPMENT', 4, '+2 Strength, always strikes last'),
('Paladin', 'Lance', 'UNIT_EQUIPMENT', 4, '+2 Strength on the charge, mounted only'),
('Paladin', 'Shield', 'UNIT_EQUIPMENT', 2, '+1 armour save'),
('Paladin', 'Battle Standard', 'BSB', 25, 'Battle Standard Bearer'),
('Paladin', 'Barded Warhorse', 'MOUNT', 20, 'Barded warhorse'),
('Paladin', 'Royal Pegasus', 'MOUNT', 55, 'Flying mount'),

('Damsel of the Lady', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Damsel of the Lady', 'Level 1 Wizard', 'FREE_UPGRADE', 0, 'Level 1 Wizard'),
('Damsel of the Lady', 'Level 2 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 2 Wizard'),
('Damsel of the Lady', 'Warhorse', 'MOUNT', 12, 'Warhorse'),

('Knights of the Realm', 'Knight Gallant', 'COMMAND_GROUP', 16, 'Champion'),
('Knights of the Realm', 'Standard Bearer', 'COMMAND_GROUP', 16, 'Standard Bearer'),
('Knights of the Realm', 'Musician', 'COMMAND_GROUP', 8, 'Musician'),
('Knights Errant', 'Impetuous', 'FREE_UPGRADE', 0, 'Must charge when able'),
('Knights Errant', 'Knight Gallant', 'COMMAND_GROUP', 16, 'Champion'),
('Knights Errant', 'Standard Bearer', 'COMMAND_GROUP', 16, 'Standard Bearer'),
('Knights Errant', 'Musician', 'COMMAND_GROUP', 8, 'Musician'),
('Knights Errant', 'Errantry Banner', 'MAGIC_BANNER', 20, '+1 Strength on the charge'),

('Men-at-Arms', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Men-at-Arms', 'Halberd', 'FREE_UPGRADE', 0, '+1 Strength in close combat'),
('Men-at-Arms', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Men-at-Arms', 'Shield', 'UNIT_EQUIPMENT', 1, '+1 armour save'),
('Men-at-Arms', 'Yeoman Warden', 'COMMAND_GROUP', 5, 'Champion'),
('Men-at-Arms', 'Standard Bearer', 'COMMAND_GROUP', 5, 'Standard Bearer'),
('Men-at-Arms', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Peasant Bowmen', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Peasant Bowmen', 'Longbow', 'FREE_UPGRADE', 0, 'Long range bow'),
('Peasant Bowmen', 'Defensive Stakes', 'UNIT_EQUIPMENT', 1, 'Protection against charges'),
('Peasant Bowmen', 'Braziers', 'COMMAND_GROUP', 5, 'Flaming arrows'),
('Peasant Bowmen', 'Villein', 'COMMAND_GROUP', 5, 'Champion'),
('Peasant Bowmen', 'Standard Bearer', 'COMMAND_GROUP', 5, 'Standard Bearer'),
('Peasant Bowmen', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Questing Knights', 'Great weapon', 'UNIT_EQUIPMENT', 0, 'Replaces the lance, +2 Strength'),
('Questing Knights', 'Paragon', 'COMMAND_GROUP', 16, 'Champion'),
('Questing Knights', 'Standard Bearer', 'COMMAND_GROUP', 16, 'Standard Bearer'),
('Questing Knights', 'Musician', 'COMMAND_GROUP', 8, 'Musician'),

('Pegasus Knights', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Pegasus Knights', 'Lance', 'FREE_UPGRADE', 0, '+2 Strength on the charge'),
('Pegasus Knights', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Pegasus Knights', 'Shield', 'FREE_UPGRADE', 0, '+1 armour save'),
('Pegasus Knights', 'Fly', 'FREE_UPGRADE', 0, 'Flying unit'),
('Pegasus Knights', 'Barding', 'UNIT_EQUIPMENT', 4, '+1 armour save'),
('Pegasus Knights', 'Knight Gallant', 'COMMAND_GROUP', 16, 'Champion'),
('Pegasus Knights', 'Standard Bearer', 'COMMAND_GROUP', 16, 'Standard Bearer'),
('Pegasus Knights', 'Musician', 'COMMAND_GROUP', 8, 'Musician'),

('Mounted Yeomen', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Mounted Yeomen', 'Spear', 'FREE_UPGRADE', 0, 'Spear'),
('Mounted Yeomen', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Mounted Yeomen', 'Warhorse', 'FREE_UPGRADE', 0, 'Rides a horse'),
('Mounted Yeomen', 'Fast Cavalry', 'FREE_UPGRADE', 0, 'Free reform and vanguard'),
('Mounted Yeomen', 'Shield', 'UNIT_EQUIPMENT', 1, '+1 armour save'),
('Mounted Yeomen', 'Shortbow', 'UNIT_EQUIPMENT', 1, 'Short range bow'),
('Mounted Yeomen', 'Warden', 'COMMAND_GROUP', 8, 'Champion'),
('Mounted Yeomen', 'Standard Bearer', 'COMMAND_GROUP', 8, 'Standard Bearer'),
('Mounted Yeomen', 'Musician', 'COMMAND_GROUP', 8, 'Musician'),

('Battle Pilgrims', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Battle Pilgrims', 'Grail Reliquae', 'COMMAND_GROUP', 70, 'Holy relic carried by the pilgrims'),
('Battle Pilgrims', 'Shield', 'UNIT_EQUIPMENT', 1, '+1 armour save'),

('Grail Knights', 'Grail Guardian', 'COMMAND_GROUP', 20, 'Champion'),
('Grail Knights', 'Standard Bearer', 'COMMAND_GROUP', 20, 'Standard Bearer'),
('Grail Knights', 'Musician', 'COMMAND_GROUP', 10, 'Musician'),
('Field Trebuchet', 'Crew', 'FREE_UPGRADE', 0, 'Three peasant crew');

INSERT INTO staging_unit_upgrade
SELECT unit.name, item.name, item.upgrade_type, item.points, item.description
FROM staging_unit unit
CROSS JOIN (VALUES
    ('Sword of the Quest', 'MAGIC_WEAPON', 40, 'Wounds monsters easily'),
    ('Morning Star of Fracasse', 'MAGIC_WEAPON', 35, 'Hits hard on the charge'),
    ('Gilded Cuirass', 'MAGIC_ARMOUR', 40, 'Heavy armour that rejects wounds'),
    ('Sacrament of the Lady', 'MAGIC_ITEM', 45, 'Ward save against spells')
) AS item(name, upgrade_type, points, description)
WHERE unit.unit_type IN ('LORDS', 'HERO');

INSERT INTO staging_unit_upgrade
SELECT unit_name, banner.name, 'MAGIC_BANNER', banner.points, banner.description
FROM (VALUES ('Paladin'), ('Knights of the Realm'), ('Questing Knights'), ('Grail Knights'), ('Pegasus Knights')) AS bearers(unit_name)
CROSS JOIN (VALUES
    ('Banner of the Lady', 50, 'The unit is Stubborn'),
    ('Valorous Standard', 35, 'Immune to Fear and Terror')
) AS banner(name, points, description);

SELECT roster_load_army('BRETONIA');
