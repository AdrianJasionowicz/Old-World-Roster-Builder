SELECT roster_begin_army();

INSERT INTO staging_unit VALUES
('Glade Lord', 140, 1, 'LORDS', 5, 7, 7, 4, 3, 3, 8, 4, 10, 0, 0),
('Spellweaver', 220, 1, 'LORDS', 5, 4, 4, 3, 3, 3, 5, 1, 9, 0, 0),
('Treeman Ancient', 285, 1, 'LORDS', 5, 5, 4, 6, 6, 6, 2, 5, 9, 3, 0),
('Glade Captain', 70, 1, 'HERO', 5, 6, 6, 4, 3, 2, 7, 3, 9, 0, 0),
('Spellsinger', 85, 1, 'HERO', 5, 4, 4, 3, 3, 2, 5, 1, 8, 0, 0),
('Branchwraith', 75, 1, 'HERO', 5, 5, 0, 4, 4, 2, 7, 3, 8, 0, 0),
('Glade Guard', 12, 10, 'CORE', 5, 4, 4, 3, 3, 1, 5, 1, 8, 0, 0),
('Eternal Guard', 12, 10, 'CORE', 5, 5, 4, 3, 3, 1, 6, 1, 9, 0, 0),
('Dryads', 12, 5, 'CORE', 5, 4, 0, 4, 4, 1, 6, 2, 8, 0, 0),
('Glade Riders', 20, 5, 'CORE', 5, 4, 4, 3, 3, 1, 5, 1, 8, 0, 0),
('Wardancers', 15, 5, 'SPECIAL', 5, 6, 4, 3, 3, 1, 6, 1, 8, 0, 0),
('Wild Riders', 27, 5, 'SPECIAL', 5, 5, 4, 4, 3, 1, 6, 1, 8, 0, 0),
('Deepwood Scouts', 14, 5, 'SPECIAL', 5, 4, 5, 3, 3, 1, 5, 1, 8, 0, 0),
('Tree Kin', 45, 3, 'SPECIAL', 5, 4, 0, 4, 5, 3, 3, 3, 8, 4, 0),
('Warhawk Riders', 40, 3, 'SPECIAL', 2, 4, 4, 3, 3, 2, 5, 2, 8, 0, 0),
('Great Eagle', 50, 1, 'SPECIAL', 2, 5, 0, 4, 4, 3, 4, 2, 8, 0, 0),
('Waywatchers', 18, 5, 'RARE', 5, 5, 5, 3, 3, 1, 5, 1, 8, 0, 0),
('Treeman', 285, 1, 'RARE', 5, 5, 4, 6, 6, 6, 2, 5, 8, 3, 0);

INSERT INTO staging_unit_upgrade
SELECT name, 'Forest Stalkers', 'FREE_UPGRADE', 0, 'Move through forests without penalty'
FROM staging_unit
WHERE name NOT IN ('Great Eagle', 'Warhawk Riders');

INSERT INTO staging_unit_upgrade VALUES
('Glade Lord', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Glade Lord', 'Asrai Longbow', 'UNIT_EQUIPMENT', 10, 'Long range bow'),
('Glade Lord', 'Great weapon', 'UNIT_EQUIPMENT', 6, '+2 Strength, always strikes last'),
('Glade Lord', 'Spear', 'UNIT_EQUIPMENT', 3, 'Spear'),
('Glade Lord', 'Light Armour', 'UNIT_EQUIPMENT', 3, 'Light armour, 6+ armour save'),
('Glade Lord', 'Shield', 'UNIT_EQUIPMENT', 3, '+1 armour save'),
('Glade Lord', 'Elven Steed', 'MOUNT', 18, 'Elven steed'),
('Glade Lord', 'Great Stag', 'MOUNT', 50, 'Monstrous stag'),
('Glade Lord', 'Great Eagle', 'MOUNT', 50, 'Flying mount'),
('Glade Lord', 'Forest Dragon', 'MOUNT', 320, 'Dragon of the forest'),

('Spellweaver', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Spellweaver', 'Level 3 Wizard', 'FREE_UPGRADE', 0, 'Level 3 Wizard'),
('Spellweaver', 'Level 4 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 4 Wizard'),
('Spellweaver', 'Elven Steed', 'MOUNT', 18, 'Elven steed'),
('Spellweaver', 'Unicorn', 'MOUNT', 50, 'Magic resistant mount'),

('Treeman Ancient', 'Level 2 Wizard', 'FREE_UPGRADE', 0, 'Level 2 Wizard'),
('Treeman Ancient', 'Tree Whack', 'FREE_UPGRADE', 0, 'Single crushing attack'),
('Treeman Ancient', 'Terror', 'FREE_UPGRADE', 0, 'Causes Terror'),

('Glade Captain', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Glade Captain', 'Asrai Longbow', 'UNIT_EQUIPMENT', 10, 'Long range bow'),
('Glade Captain', 'Great weapon', 'UNIT_EQUIPMENT', 4, '+2 Strength, always strikes last'),
('Glade Captain', 'Spear', 'UNIT_EQUIPMENT', 2, 'Spear'),
('Glade Captain', 'Light Armour', 'UNIT_EQUIPMENT', 2, 'Light armour, 6+ armour save'),
('Glade Captain', 'Shield', 'UNIT_EQUIPMENT', 2, '+1 armour save'),
('Glade Captain', 'Battle Standard', 'BSB', 25, 'Battle Standard Bearer'),
('Glade Captain', 'Elven Steed', 'MOUNT', 18, 'Elven steed'),
('Glade Captain', 'Great Stag', 'MOUNT', 50, 'Monstrous stag'),

('Spellsinger', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Spellsinger', 'Level 1 Wizard', 'FREE_UPGRADE', 0, 'Level 1 Wizard'),
('Spellsinger', 'Level 2 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 2 Wizard'),
('Spellsinger', 'Elven Steed', 'MOUNT', 18, 'Elven steed'),

('Branchwraith', 'Level 1 Wizard', 'UNIT_EQUIPMENT', 35, 'Becomes a Level 1 Wizard'),
('Branchwraith', 'Fear', 'FREE_UPGRADE', 0, 'Causes Fear'),

('Glade Guard', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Glade Guard', 'Asrai Longbow', 'FREE_UPGRADE', 0, 'Long range bow'),
('Glade Guard', 'Lord''s Bowman', 'COMMAND_GROUP', 10, 'Champion'),
('Glade Guard', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Glade Guard', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Eternal Guard', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Eternal Guard', 'Spear', 'FREE_UPGRADE', 0, 'Fights in an extra rank'),
('Eternal Guard', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Eternal Guard', 'Shield', 'FREE_UPGRADE', 0, '+1 armour save'),
('Eternal Guard', 'Stubborn', 'FREE_UPGRADE', 0, 'Always uses its own Leadership'),
('Eternal Guard', 'Eternal Warden', 'COMMAND_GROUP', 10, 'Champion'),
('Eternal Guard', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Eternal Guard', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Dryads', 'Fear', 'FREE_UPGRADE', 0, 'Causes Fear'),
('Dryads', 'Skirmish', 'FREE_UPGRADE', 0, 'Loose formation'),
('Dryads', 'Branch Nymph', 'COMMAND_GROUP', 10, 'Champion'),

('Glade Riders', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Glade Riders', 'Spear', 'FREE_UPGRADE', 0, 'Spear'),
('Glade Riders', 'Asrai Longbow', 'FREE_UPGRADE', 0, 'Long range bow'),
('Glade Riders', 'Elven Steed', 'FREE_UPGRADE', 0, 'Rides an elven steed'),
('Glade Riders', 'Fast Cavalry', 'FREE_UPGRADE', 0, 'Free reform and vanguard'),
('Glade Riders', 'Glade Knight', 'COMMAND_GROUP', 10, 'Champion'),
('Glade Riders', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Glade Riders', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Wardancers', 'Additional hand weapon', 'FREE_UPGRADE', 0, '+1 Attack'),
('Wardancers', 'Talismanic Tattoos', 'FREE_UPGRADE', 0, '6+ ward save'),
('Wardancers', 'Skirmish', 'FREE_UPGRADE', 0, 'Loose formation'),
('Wardancers', 'Bladesinger', 'COMMAND_GROUP', 10, 'Champion'),
('Wardancers', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Wild Riders', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Wild Riders', 'Spear', 'FREE_UPGRADE', 0, 'Hunting spear'),
('Wild Riders', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Wild Riders', 'Elven Steed', 'FREE_UPGRADE', 0, 'Rides an elven steed'),
('Wild Riders', 'Frenzy', 'FREE_UPGRADE', 0, '+1 Attack, must charge'),
('Wild Riders', 'Shield', 'UNIT_EQUIPMENT', 2, '+1 armour save'),
('Wild Riders', 'Wild Hunter', 'COMMAND_GROUP', 10, 'Champion'),
('Wild Riders', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Wild Riders', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Deepwood Scouts', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Deepwood Scouts', 'Asrai Longbow', 'FREE_UPGRADE', 0, 'Long range bow'),
('Deepwood Scouts', 'Scouts', 'FREE_UPGRADE', 0, 'Deploys as Scouts'),
('Deepwood Scouts', 'Skirmish', 'FREE_UPGRADE', 0, 'Loose formation'),
('Deepwood Scouts', 'Master Scout', 'COMMAND_GROUP', 10, 'Champion'),

('Tree Kin', 'Fear', 'FREE_UPGRADE', 0, 'Causes Fear'),
('Tree Kin', 'Tree Kin Elder', 'COMMAND_GROUP', 15, 'Champion'),
('Warhawk Riders', 'Spear', 'FREE_UPGRADE', 0, 'Spear'),
('Warhawk Riders', 'Asrai Longbow', 'FREE_UPGRADE', 0, 'Long range bow'),
('Warhawk Riders', 'Fly', 'FREE_UPGRADE', 0, 'Flying unit'),
('Warhawk Riders', 'Wind Rider', 'COMMAND_GROUP', 10, 'Champion'),
('Great Eagle', 'Fly', 'FREE_UPGRADE', 0, 'Flying monster'),
('Waywatchers', 'Asrai Longbow', 'FREE_UPGRADE', 0, 'Long range bow'),
('Waywatchers', 'Scouts', 'FREE_UPGRADE', 0, 'Deploys as Scouts'),
('Waywatchers', 'Skirmish', 'FREE_UPGRADE', 0, 'Loose formation'),
('Waywatchers', 'Waywatcher Sentinel', 'COMMAND_GROUP', 10, 'Champion'),
('Treeman', 'Terror', 'FREE_UPGRADE', 0, 'Causes Terror'),
('Treeman', 'Strangleroots', 'FREE_UPGRADE', 0, 'Ranged root attack');

INSERT INTO staging_unit_upgrade
SELECT unit.name, item.name, item.upgrade_type, item.points, item.description
FROM staging_unit unit
CROSS JOIN (VALUES
    ('Daith''s Reaper', 'MAGIC_WEAPON', 50, 'Re-roll failed hits and wounds'),
    ('Hail of Doom Arrow', 'MAGIC_WEAPON', 30, 'One use: a storm of arrows'),
    ('Armour of the Fey', 'MAGIC_ARMOUR', 40, 'Light armour with a ward save'),
    ('Moonstone of the Hidden Ways', 'MAGIC_ITEM', 50, 'One use: moves the bearer through forests')
) AS item(name, upgrade_type, points, description)
WHERE unit.unit_type IN ('LORDS', 'HERO') AND unit.name NOT IN ('Treeman Ancient', 'Branchwraith');

INSERT INTO staging_unit_upgrade
SELECT unit_name, banner.name, 'MAGIC_BANNER', banner.points, banner.description
FROM (VALUES ('Glade Captain'), ('Glade Guard'), ('Eternal Guard'), ('Wild Riders')) AS bearers(unit_name)
CROSS JOIN (VALUES
    ('Banner of the Hunter King', 30, 'The unit causes Fear'),
    ('Banner of Midsummer Wood', 40, 'Ambush from the forests')
) AS banner(name, points, description);

SELECT roster_load_army('WOOD_ELVES');
