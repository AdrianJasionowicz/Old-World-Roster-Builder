SELECT roster_begin_army();

INSERT INTO staging_unit VALUES
('Vampire Lord', 205, 1, 'LORDS', 6, 7, 4, 5, 5, 4, 7, 4, 10, 0, 0),
('Master Necromancer', 120, 1, 'LORDS', 4, 3, 3, 3, 4, 3, 3, 1, 8, 0, 0),
('Strigoi Ghoul King', 220, 1, 'LORDS', 6, 7, 0, 6, 5, 4, 7, 5, 9, 0, 0),
('Vampire', 100, 1, 'HERO', 6, 6, 4, 5, 4, 2, 6, 3, 8, 0, 0),
('Necromancer', 65, 1, 'HERO', 4, 3, 3, 3, 3, 2, 3, 1, 7, 0, 0),
('Wight King', 75, 1, 'HERO', 4, 5, 3, 4, 5, 3, 3, 3, 9, 0, 0),
('Tomb Banshee', 80, 1, 'HERO', 6, 3, 0, 3, 4, 2, 3, 3, 8, 0, 0),
('Skeleton Warriors', 8, 10, 'CORE', 4, 2, 2, 3, 3, 1, 2, 1, 3, 0, 0),
('Zombies', 4, 20, 'CORE', 4, 1, 0, 3, 2, 1, 1, 1, 2, 0, 0),
('Crypt Ghouls', 10, 10, 'CORE', 4, 3, 0, 3, 4, 1, 3, 2, 5, 0, 0),
('Dire Wolves', 9, 5, 'CORE', 9, 3, 0, 3, 3, 1, 3, 1, 3, 0, 0),
('Grave Guard', 11, 5, 'SPECIAL', 4, 3, 3, 4, 4, 1, 3, 2, 8, 0, 0),
('Black Knights', 20, 5, 'SPECIAL', 4, 3, 3, 4, 4, 1, 2, 1, 8, 0, 0),
('Crypt Horrors', 45, 3, 'SPECIAL', 7, 3, 0, 5, 5, 3, 2, 3, 4, 0, 0),
('Fell Bats', 16, 2, 'SPECIAL', 1, 3, 0, 3, 3, 2, 3, 2, 3, 0, 0),
('Vargheists', 40, 3, 'SPECIAL', 6, 4, 0, 5, 4, 3, 4, 4, 7, 0, 0),
('Corpse Cart', 75, 1, 'SPECIAL', 4, 2, 0, 3, 4, 4, 1, 2, 2, 5, 0),
('Hexwraiths', 30, 5, 'SPECIAL', 8, 3, 0, 4, 3, 1, 3, 1, 8, 0, 0),
('Blood Knights', 50, 5, 'RARE', 8, 5, 3, 5, 4, 1, 4, 2, 7, 0, 0),
('Spirit Hosts', 45, 1, 'RARE', 6, 2, 0, 3, 3, 4, 1, 4, 4, 0, 0),
('Cairn Wraiths', 50, 1, 'RARE', 6, 3, 0, 3, 3, 1, 3, 3, 8, 0, 0),
('Varghulf', 175, 1, 'RARE', 8, 5, 0, 5, 5, 4, 2, 5, 8, 0, 0),
('Black Coach', 200, 1, 'RARE', 8, 3, 0, 5, 5, 5, 2, 3, 4, 4, 0),
('Terrorgheist', 225, 1, 'RARE', 6, 3, 0, 6, 6, 6, 3, 3, 4, 0, 0);

INSERT INTO staging_unit_upgrade
SELECT name, 'Undead', 'FREE_UPGRADE', 0, 'Fear, Unbreakable, crumbles without a general'
FROM staging_unit;

INSERT INTO staging_unit_upgrade
SELECT unit.name, power.name, 'UNIT_EQUIPMENT', power.points, power.description
FROM staging_unit unit
CROSS JOIN (VALUES
    ('Dread Knight', 25, 'Vampiric power: deadly on horseback'),
    ('Red Fury', 35, 'Vampiric power: extra attacks for every wound caused'),
    ('Quickblood', 20, 'Vampiric power: always strikes first'),
    ('Aura of Dark Majesty', 30, 'Vampiric power: enemies nearby have -1 Leadership'),
    ('Beguile', 20, 'Vampiric power: enemies in contact must re-roll hits'),
    ('Master of the Black Arts', 50, 'Vampiric power: +1 to cast')
) AS power(name, points, description)
WHERE unit.name IN ('Vampire Lord', 'Vampire');

INSERT INTO staging_unit_upgrade VALUES
('Vampire Lord', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Vampire Lord', 'Level 1 Wizard', 'FREE_UPGRADE', 0, 'Level 1 Wizard'),
('Vampire Lord', 'Level 2 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 2 Wizard'),
('Vampire Lord', 'Level 3 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 3 Wizard'),
('Vampire Lord', 'Level 4 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 4 Wizard'),
('Vampire Lord', 'Great weapon', 'UNIT_EQUIPMENT', 6, '+2 Strength, always strikes last'),
('Vampire Lord', 'Additional hand weapon', 'UNIT_EQUIPMENT', 3, '+1 Attack'),
('Vampire Lord', 'Lance', 'UNIT_EQUIPMENT', 6, '+2 Strength on the charge, mounted only'),
('Vampire Lord', 'Heavy Armour', 'UNIT_EQUIPMENT', 6, 'Heavy armour, 5+ armour save'),
('Vampire Lord', 'Shield', 'UNIT_EQUIPMENT', 3, '+1 armour save'),
('Vampire Lord', 'Barded Nightmare', 'MOUNT', 24, 'Undead steed with barding'),
('Vampire Lord', 'Hellsteed', 'MOUNT', 50, 'Flying undead steed'),
('Vampire Lord', 'Abyssal Terror', 'MOUNT', 100, 'Monstrous flying mount'),
('Vampire Lord', 'Zombie Dragon', 'MOUNT', 320, 'Undead dragon'),

('Master Necromancer', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Master Necromancer', 'Level 3 Wizard', 'FREE_UPGRADE', 0, 'Level 3 Wizard'),
('Master Necromancer', 'Level 4 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 4 Wizard'),
('Master Necromancer', 'Nightmare', 'MOUNT', 15, 'Undead steed'),

('Strigoi Ghoul King', 'Poisoned Attacks', 'FREE_UPGRADE', 0, 'Poisoned Attacks'),
('Strigoi Ghoul King', 'Regenerate', 'FREE_UPGRADE', 0, 'Regeneration save'),
('Strigoi Ghoul King', 'Level 1 Wizard', 'FREE_UPGRADE', 0, 'Level 1 Wizard'),
('Strigoi Ghoul King', 'Level 2 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 2 Wizard'),
('Strigoi Ghoul King', 'Terrorgheist', 'MOUNT', 225, 'Monstrous undead bat'),

('Vampire', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Vampire', 'Level 1 Wizard', 'UNIT_EQUIPMENT', 35, 'Becomes a Level 1 Wizard'),
('Vampire', 'Great weapon', 'UNIT_EQUIPMENT', 4, '+2 Strength, always strikes last'),
('Vampire', 'Additional hand weapon', 'UNIT_EQUIPMENT', 2, '+1 Attack'),
('Vampire', 'Lance', 'UNIT_EQUIPMENT', 4, '+2 Strength on the charge, mounted only'),
('Vampire', 'Heavy Armour', 'UNIT_EQUIPMENT', 4, 'Heavy armour, 5+ armour save'),
('Vampire', 'Shield', 'UNIT_EQUIPMENT', 2, '+1 armour save'),
('Vampire', 'Battle Standard', 'BSB', 25, 'Battle Standard Bearer'),
('Vampire', 'Barded Nightmare', 'MOUNT', 24, 'Undead steed with barding'),
('Vampire', 'Hellsteed', 'MOUNT', 50, 'Flying undead steed'),

('Necromancer', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Necromancer', 'Level 1 Wizard', 'FREE_UPGRADE', 0, 'Level 1 Wizard'),
('Necromancer', 'Level 2 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 2 Wizard'),
('Necromancer', 'Nightmare', 'MOUNT', 15, 'Undead steed'),

('Wight King', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Wight King', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Wight King', 'Great weapon', 'UNIT_EQUIPMENT', 4, '+2 Strength, always strikes last'),
('Wight King', 'Shield', 'UNIT_EQUIPMENT', 2, '+1 armour save'),
('Wight King', 'Lance', 'UNIT_EQUIPMENT', 4, '+2 Strength on the charge, mounted only'),
('Wight King', 'Battle Standard', 'BSB', 25, 'Battle Standard Bearer'),
('Wight King', 'Barded Nightmare', 'MOUNT', 24, 'Undead steed with barding'),

('Tomb Banshee', 'Ethereal', 'FREE_UPGRADE', 0, 'Can only be wounded by magic'),
('Tomb Banshee', 'Ghostly Howl', 'FREE_UPGRADE', 0, 'Scream that kills the faint-hearted'),

('Skeleton Warriors', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Skeleton Warriors', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Skeleton Warriors', 'Shield', 'FREE_UPGRADE', 0, '+1 armour save'),
('Skeleton Warriors', 'Spears', 'UNIT_EQUIPMENT', 1, 'Fights in an extra rank'),
('Skeleton Warriors', 'Skeleton Champion', 'COMMAND_GROUP', 10, 'Champion'),
('Skeleton Warriors', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Skeleton Warriors', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Zombies', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Zombies', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Zombies', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Crypt Ghouls', 'Poisoned Attacks', 'FREE_UPGRADE', 0, 'Poisoned Attacks'),
('Crypt Ghouls', 'Crypt Ghast', 'COMMAND_GROUP', 10, 'Champion'),

('Dire Wolves', 'Fast Cavalry', 'FREE_UPGRADE', 0, 'Free reform and vanguard'),
('Dire Wolves', 'Doom Wolf', 'COMMAND_GROUP', 10, 'Champion'),

('Grave Guard', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Grave Guard', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Grave Guard', 'Killing Blow', 'FREE_UPGRADE', 0, 'A 6 to wound slays outright'),
('Grave Guard', 'Shield', 'UNIT_EQUIPMENT', 1, '+1 armour save'),
('Grave Guard', 'Great weapon', 'UNIT_EQUIPMENT', 2, '+2 Strength, always strikes last'),
('Grave Guard', 'Seneschal', 'COMMAND_GROUP', 10, 'Champion'),
('Grave Guard', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Grave Guard', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Black Knights', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Black Knights', 'Lance', 'FREE_UPGRADE', 0, '+2 Strength on the charge'),
('Black Knights', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Black Knights', 'Shield', 'FREE_UPGRADE', 0, '+1 armour save'),
('Black Knights', 'Nightmare', 'FREE_UPGRADE', 0, 'Rides an undead steed'),
('Black Knights', 'Ethereal Steeds', 'FREE_UPGRADE', 0, 'Moves through terrain'),
('Black Knights', 'Barding', 'UNIT_EQUIPMENT', 5, '+1 armour save, loses ethereal movement'),
('Black Knights', 'Hell Knight', 'COMMAND_GROUP', 10, 'Champion'),
('Black Knights', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Black Knights', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Crypt Horrors', 'Poisoned Attacks', 'FREE_UPGRADE', 0, 'Poisoned Attacks'),
('Crypt Horrors', 'Regenerate', 'FREE_UPGRADE', 0, 'Regeneration save'),
('Crypt Horrors', 'Crypt Haunter', 'COMMAND_GROUP', 10, 'Champion'),

('Fell Bats', 'Fly', 'FREE_UPGRADE', 0, 'Flying unit'),
('Vargheists', 'Fly', 'FREE_UPGRADE', 0, 'Flying unit'),
('Vargheists', 'Frenzy', 'FREE_UPGRADE', 0, '+1 Attack, must charge'),
('Vargheists', 'Vargoyle', 'COMMAND_GROUP', 10, 'Champion'),
('Corpse Cart', 'Balefire', 'UNIT_EQUIPMENT', 10, 'Enemy wizards nearby suffer -1 to cast'),
('Corpse Cart', 'Unholy Lodestone', 'UNIT_EQUIPMENT', 15, 'Nearby undead fight better'),
('Hexwraiths', 'Ethereal', 'FREE_UPGRADE', 0, 'Can only be wounded by magic'),
('Hexwraiths', 'Hellwraith', 'COMMAND_GROUP', 10, 'Champion'),

('Blood Knights', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Blood Knights', 'Lance', 'FREE_UPGRADE', 0, '+2 Strength on the charge'),
('Blood Knights', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Blood Knights', 'Shield', 'FREE_UPGRADE', 0, '+1 armour save'),
('Blood Knights', 'Barded Nightmare', 'FREE_UPGRADE', 0, 'Rides an undead steed with barding'),
('Blood Knights', 'Frenzy', 'FREE_UPGRADE', 0, '+1 Attack, must charge'),
('Blood Knights', 'Kastellan', 'COMMAND_GROUP', 10, 'Champion'),
('Blood Knights', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Blood Knights', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Spirit Hosts', 'Ethereal', 'FREE_UPGRADE', 0, 'Can only be wounded by magic'),
('Cairn Wraiths', 'Ethereal', 'FREE_UPGRADE', 0, 'Can only be wounded by magic'),
('Cairn Wraiths', 'Great weapon', 'FREE_UPGRADE', 0, '+2 Strength, always strikes last'),
('Cairn Wraiths', 'Tomb Banshee', 'COMMAND_GROUP', 15, 'A banshee joins the unit'),
('Varghulf', 'Frenzy', 'FREE_UPGRADE', 0, '+1 Attack, must charge'),
('Varghulf', 'Regenerate', 'FREE_UPGRADE', 0, 'Regeneration save'),
('Black Coach', 'Ethereal', 'FREE_UPGRADE', 0, 'Can only be wounded by magic'),
('Black Coach', 'Evocation of Death', 'FREE_UPGRADE', 0, 'Grows stronger as spells are cast'),
('Terrorgheist', 'Fly', 'FREE_UPGRADE', 0, 'Flying monster'),
('Terrorgheist', 'Death Shriek', 'FREE_UPGRADE', 0, 'Scream attack');

INSERT INTO staging_unit_upgrade
SELECT unit.name, item.name, item.upgrade_type, item.points, item.description
FROM staging_unit unit
CROSS JOIN (VALUES
    ('Skabscrath', 'MAGIC_WEAPON', 75, 'Great weapon that cuts through any armour'),
    ('Frostblade', 'MAGIC_WEAPON', 65, 'Wounded enemies may be slain outright'),
    ('Flayed Hauberk', 'MAGIC_ARMOUR', 30, 'Heavy armour that hits back'),
    ('Cursed Book', 'MAGIC_ITEM', 35, 'Enemies in contact have -1 to hit'),
    ('Book of Arkhan', 'MAGIC_ITEM', 40, 'Bound spell, Vanhel''s Danse Macabre')
) AS item(name, upgrade_type, points, description)
WHERE unit.unit_type IN ('LORDS', 'HERO') AND unit.name <> 'Tomb Banshee';

INSERT INTO staging_unit_upgrade
SELECT unit_name, banner.name, 'MAGIC_BANNER', banner.points, banner.description
FROM (VALUES ('Vampire'), ('Wight King'), ('Grave Guard'), ('Black Knights'), ('Blood Knights'), ('Skeleton Warriors')) AS bearers(unit_name)
CROSS JOIN (VALUES
    ('Screaming Banner', 25, 'Enemies nearby take a Leadership penalty'),
    ('Banner of the Barrows', 25, 'The unit has +1 to hit in the first round')
) AS banner(name, points, description);

SELECT roster_load_army('VAMPIRE_COUNTS');
