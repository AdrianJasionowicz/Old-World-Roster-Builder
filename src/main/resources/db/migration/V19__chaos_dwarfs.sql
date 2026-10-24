SELECT roster_begin_army();

INSERT INTO staging_unit VALUES
('Sorcerer-Prophet', 190, 1, 'LORDS', 3, 4, 3, 4, 5, 3, 2, 1, 10, 0, 0),
('Infernal Castellan', 75, 1, 'HERO', 3, 6, 3, 4, 5, 2, 3, 3, 9, 0, 0),
('Daemonsmith Sorcerer', 90, 1, 'HERO', 3, 4, 3, 4, 4, 2, 2, 1, 9, 0, 0),
('Bull Centaur Taur''ruk', 120, 1, 'HERO', 8, 6, 3, 5, 5, 3, 3, 4, 9, 0, 0),
('Hobgoblin Khan', 35, 1, 'HERO', 4, 4, 3, 4, 4, 2, 3, 3, 7, 0, 0),
('Infernal Guard', 11, 10, 'CORE', 3, 4, 3, 3, 4, 1, 2, 1, 9, 0, 0),
('Hobgoblin Cutthroats', 4, 10, 'CORE', 4, 3, 3, 3, 3, 1, 2, 1, 6, 0, 0),
('Hobgoblin Wolfraiders', 12, 5, 'CORE', 4, 3, 3, 3, 3, 1, 2, 1, 6, 0, 0),
('Infernal Ironsworn', 14, 10, 'SPECIAL', 3, 5, 3, 4, 4, 1, 2, 1, 9, 0, 0),
('Bull Centaur Renders', 50, 3, 'SPECIAL', 8, 4, 3, 4, 5, 3, 2, 2, 8, 0, 0),
('K''daai Fireborn', 70, 3, 'SPECIAL', 6, 4, 0, 5, 5, 3, 3, 3, 10, 0, 0),
('Deathshrieker Rocket Launcher', 80, 1, 'SPECIAL', 0, 0, 0, 0, 7, 3, 0, 0, 0, 0, 0),
('Magma Cannon', 120, 1, 'SPECIAL', 0, 0, 0, 0, 7, 3, 0, 0, 0, 0, 0),
('Dreadquake Mortar', 210, 1, 'RARE', 0, 0, 0, 0, 7, 4, 0, 0, 0, 0, 0),
('Iron Daemon', 225, 1, 'RARE', 0, 0, 3, 6, 6, 6, 0, 0, 10, 2, 0),
('K''daai Destroyer', 300, 1, 'RARE', 6, 5, 0, 6, 6, 7, 3, 6, 10, 0, 0);

INSERT INTO staging_unit_upgrade
SELECT name, 'Contempt', 'FREE_UPGRADE', 0, 'Despises lesser races, hatred of their foes'
FROM staging_unit
WHERE name IN ('Sorcerer-Prophet', 'Infernal Castellan', 'Daemonsmith Sorcerer', 'Infernal Guard', 'Infernal Ironsworn');

INSERT INTO staging_unit_upgrade VALUES
('Sorcerer-Prophet', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Sorcerer-Prophet', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Sorcerer-Prophet', 'Level 3 Wizard', 'FREE_UPGRADE', 0, 'Level 3 Wizard'),
('Sorcerer-Prophet', 'Level 4 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 4 Wizard'),
('Sorcerer-Prophet', 'Great weapon', 'UNIT_EQUIPMENT', 6, '+2 Strength, always strikes last'),
('Sorcerer-Prophet', 'Bale Taurus', 'MOUNT', 250, 'Flaming flying bull'),
('Sorcerer-Prophet', 'Lammasu', 'MOUNT', 200, 'Magic eating flying beast'),

('Infernal Castellan', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Infernal Castellan', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Infernal Castellan', 'Great weapon', 'UNIT_EQUIPMENT', 4, '+2 Strength, always strikes last'),
('Infernal Castellan', 'Shield', 'UNIT_EQUIPMENT', 2, '+1 armour save'),
('Infernal Castellan', 'Battle Standard', 'BSB', 25, 'Battle Standard Bearer'),

('Daemonsmith Sorcerer', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Daemonsmith Sorcerer', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Daemonsmith Sorcerer', 'Level 1 Wizard', 'FREE_UPGRADE', 0, 'Level 1 Wizard'),
('Daemonsmith Sorcerer', 'Level 2 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 2 Wizard'),

('Bull Centaur Taur''ruk', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Bull Centaur Taur''ruk', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Bull Centaur Taur''ruk', 'Great weapon', 'UNIT_EQUIPMENT', 4, '+2 Strength, always strikes last'),
('Bull Centaur Taur''ruk', 'Shield', 'UNIT_EQUIPMENT', 2, '+1 armour save'),
('Bull Centaur Taur''ruk', 'Battle Standard', 'BSB', 25, 'Battle Standard Bearer'),

('Hobgoblin Khan', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Hobgoblin Khan', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Hobgoblin Khan', 'Shield', 'UNIT_EQUIPMENT', 2, '+1 armour save'),
('Hobgoblin Khan', 'Great weapon', 'UNIT_EQUIPMENT', 4, '+2 Strength, always strikes last'),
('Hobgoblin Khan', 'Giant Wolf', 'MOUNT', 12, 'Giant wolf'),

('Infernal Guard', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Infernal Guard', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Infernal Guard', 'Shield', 'FREE_UPGRADE', 0, '+1 armour save'),
('Infernal Guard', 'Great weapon', 'UNIT_EQUIPMENT', 1, '+2 Strength, always strikes last'),
('Infernal Guard', 'Blunderbuss', 'UNIT_EQUIPMENT', 2, 'Short range gun'),
('Infernal Guard', 'Fireglaive', 'UNIT_EQUIPMENT', 3, 'Combined spear and gun'),
('Infernal Guard', 'Deathmask', 'COMMAND_GROUP', 10, 'Champion'),
('Infernal Guard', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Infernal Guard', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Hobgoblin Cutthroats', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Hobgoblin Cutthroats', 'Additional hand weapon', 'UNIT_EQUIPMENT', 1, '+1 Attack'),
('Hobgoblin Cutthroats', 'Shield', 'UNIT_EQUIPMENT', 1, '+1 armour save'),
('Hobgoblin Cutthroats', 'Short Bow', 'UNIT_EQUIPMENT', 1, 'Short range bow'),
('Hobgoblin Cutthroats', 'Boss', 'COMMAND_GROUP', 10, 'Champion'),
('Hobgoblin Cutthroats', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Hobgoblin Cutthroats', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Hobgoblin Wolfraiders', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Hobgoblin Wolfraiders', 'Short Bow', 'FREE_UPGRADE', 0, 'Short range bow'),
('Hobgoblin Wolfraiders', 'Giant Wolf', 'FREE_UPGRADE', 0, 'Rides a giant wolf'),
('Hobgoblin Wolfraiders', 'Fast Cavalry', 'FREE_UPGRADE', 0, 'Free reform and vanguard'),
('Hobgoblin Wolfraiders', 'Light Armour', 'UNIT_EQUIPMENT', 1, 'Light armour, 6+ armour save'),
('Hobgoblin Wolfraiders', 'Boss', 'COMMAND_GROUP', 10, 'Champion'),
('Hobgoblin Wolfraiders', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Hobgoblin Wolfraiders', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Infernal Ironsworn', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Infernal Ironsworn', 'Great weapon', 'FREE_UPGRADE', 0, '+2 Strength, always strikes last'),
('Infernal Ironsworn', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Infernal Ironsworn', 'Shield', 'FREE_UPGRADE', 0, '+1 armour save'),
('Infernal Ironsworn', 'Overseer', 'COMMAND_GROUP', 10, 'Champion'),
('Infernal Ironsworn', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Infernal Ironsworn', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Bull Centaur Renders', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Bull Centaur Renders', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Bull Centaur Renders', 'Shield', 'UNIT_EQUIPMENT', 2, '+1 armour save'),
('Bull Centaur Renders', 'Great weapon', 'UNIT_EQUIPMENT', 4, '+2 Strength, always strikes last'),
('Bull Centaur Renders', 'Bull Centaur Champion', 'COMMAND_GROUP', 15, 'Champion'),
('Bull Centaur Renders', 'Standard Bearer', 'COMMAND_GROUP', 15, 'Standard Bearer'),
('Bull Centaur Renders', 'Musician', 'COMMAND_GROUP', 10, 'Musician'),

('K''daai Fireborn', 'Daemonic', 'FREE_UPGRADE', 0, '5+ ward save, Fear, Immune to Psychology'),
('K''daai Fireborn', 'Flaming Attacks', 'FREE_UPGRADE', 0, 'Flaming Attacks'),
('Deathshrieker Rocket Launcher', 'Crew', 'FREE_UPGRADE', 0, 'Three chaos dwarf crew'),
('Magma Cannon', 'Crew', 'FREE_UPGRADE', 0, 'Three chaos dwarf crew'),
('Dreadquake Mortar', 'Crew', 'FREE_UPGRADE', 0, 'Four chaos dwarf crew'),
('Iron Daemon', 'Steam Cannonade', 'FREE_UPGRADE', 0, 'Multiple shooting attacks'),
('Iron Daemon', 'Skullcracker', 'UNIT_EQUIPMENT', 25, 'Ram for crushing enemies'),
('K''daai Destroyer', 'Daemonic', 'FREE_UPGRADE', 0, '5+ ward save, Fear, Immune to Psychology'),
('K''daai Destroyer', 'Terror', 'FREE_UPGRADE', 0, 'Causes Terror');

SELECT roster_load_army('CHAOS_DWARFS');
