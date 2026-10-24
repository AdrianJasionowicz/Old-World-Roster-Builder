CREATE TEMPORARY TABLE staging_unit
(
    name         VARCHAR(255),
    points       DOUBLE PRECISION,
    min_quantity INTEGER,
    unit_type    VARCHAR(255),
    m            INTEGER,
    ws           INTEGER,
    bs           INTEGER,
    s            INTEGER,
    t            INTEGER,
    w            INTEGER,
    i            INTEGER,
    a            INTEGER,
    ld           INTEGER,
    basic_save   INTEGER,
    ward_save    INTEGER
);

CREATE TEMPORARY TABLE staging_unit_upgrade
(
    unit_name    VARCHAR(255),
    upgrade_name VARCHAR(255),
    upgrade_type VARCHAR(255),
    points       DOUBLE PRECISION,
    description  VARCHAR(1000)
);

INSERT INTO staging_unit VALUES
('Orc Warboss', 110, 1, 'LORDS', 4, 5, 3, 5, 5, 3, 3, 4, 9, 0, 0),
('Black Orc Warboss', 145, 1, 'LORDS', 4, 6, 3, 5, 5, 3, 4, 4, 9, 0, 0),
('Orc Great Shaman', 180, 1, 'LORDS', 4, 3, 3, 4, 5, 3, 2, 1, 8, 0, 0),
('Night Goblin Warboss', 65, 1, 'LORDS', 4, 5, 3, 4, 4, 3, 5, 4, 7, 0, 0),
('Goblin Great Shaman', 140, 1, 'LORDS', 4, 2, 3, 3, 4, 3, 3, 1, 6, 0, 0),
('Orc Big Boss', 55, 1, 'HERO', 4, 5, 3, 4, 5, 2, 3, 3, 8, 0, 0),
('Black Orc Big Boss', 70, 1, 'HERO', 4, 6, 3, 4, 5, 2, 3, 3, 8, 0, 0),
('Orc Shaman', 65, 1, 'HERO', 4, 3, 3, 3, 4, 2, 2, 1, 7, 0, 0),
('Night Goblin Big Boss', 35, 1, 'HERO', 4, 4, 3, 4, 4, 2, 4, 3, 6, 0, 0),
('Night Goblin Shaman', 55, 1, 'HERO', 4, 2, 3, 3, 3, 2, 3, 1, 5, 0, 0),
('Orc Boyz', 6, 10, 'CORE', 4, 3, 3, 3, 4, 1, 2, 1, 7, 0, 0),
('Orc Arrer Boyz', 7, 10, 'CORE', 4, 3, 3, 3, 4, 1, 2, 1, 7, 0, 0),
('Night Goblins', 3, 20, 'CORE', 4, 2, 3, 3, 3, 1, 3, 1, 5, 0, 0),
('Goblins', 3, 20, 'CORE', 4, 2, 3, 3, 3, 1, 2, 1, 6, 0, 0),
('Goblin Wolf Riders', 12, 5, 'CORE', 4, 2, 3, 3, 3, 1, 2, 1, 6, 0, 0),
('Snotlings', 25, 2, 'CORE', 4, 2, 0, 1, 1, 3, 3, 3, 4, 0, 0),
('Black Orcs', 12, 10, 'SPECIAL', 4, 4, 3, 4, 4, 1, 2, 1, 8, 0, 0),
('Savage Orcs', 8, 10, 'SPECIAL', 4, 3, 3, 3, 4, 1, 2, 1, 7, 0, 0),
('Orc Boar Boyz', 16, 5, 'SPECIAL', 4, 3, 3, 3, 4, 1, 2, 1, 7, 0, 0),
('Trolls', 35, 3, 'SPECIAL', 6, 3, 1, 5, 4, 3, 1, 3, 4, 0, 0),
('Orc Boar Chariot', 80, 1, 'SPECIAL', 0, 0, 0, 5, 5, 4, 0, 0, 0, 4, 0),
('Goblin Wolf Chariot', 50, 1, 'SPECIAL', 0, 0, 0, 5, 4, 3, 0, 0, 0, 5, 0),
('Goblin Spear Chukka', 35, 1, 'SPECIAL', 0, 0, 0, 0, 7, 3, 0, 0, 0, 0, 0),
('Goblin Rock Lobber', 85, 1, 'SPECIAL', 0, 0, 0, 0, 7, 3, 0, 0, 0, 0, 0),
('Giant', 200, 1, 'RARE', 6, 3, 3, 6, 5, 6, 3, 1, 10, 0, 0),
('Doom Diver Catapult', 80, 1, 'RARE', 0, 0, 0, 0, 7, 3, 0, 0, 0, 0, 0),
('Snotling Pump Wagon', 40, 1, 'RARE', 0, 0, 0, 2, 4, 3, 0, 0, 0, 0, 0);

INSERT INTO staging_unit_upgrade
SELECT unit.name, rule.name, 'FREE_UPGRADE', 0, rule.description
FROM staging_unit unit
JOIN (VALUES
    ('Orc', 'Animosity', 'May squabble instead of acting'),
    ('Orc', 'Choppas', '+1 Strength in the first round of combat'),
    ('Goblin', 'Animosity', 'May squabble instead of acting'),
    ('Goblin', 'Fear Elves', 'Fears Elves unless they outnumber them'),
    ('Night Goblin', 'Hatred (Dwarfs)', 'Hates Dwarfs')
) AS rule(race, name, description)
ON (rule.race = 'Orc' AND unit.name LIKE 'Orc %' AND unit.name NOT IN ('Orc Boar Chariot'))
    OR (rule.race = 'Orc' AND unit.name = 'Savage Orcs')
    OR (rule.race = 'Goblin' AND (unit.name LIKE '%Goblin%') AND unit.name NOT LIKE '%Chariot' AND unit.name NOT LIKE '%Chukka' AND unit.name NOT LIKE '%Lobber')
    OR (rule.race = 'Night Goblin' AND unit.name LIKE 'Night Goblin%');

INSERT INTO staging_unit_upgrade VALUES
('Orc Warboss', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Orc Warboss', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Orc Warboss', 'Great weapon', 'UNIT_EQUIPMENT', 6, '+2 Strength, always strikes last'),
('Orc Warboss', 'Additional hand weapon', 'UNIT_EQUIPMENT', 6, '+1 Attack'),
('Orc Warboss', 'Shield', 'UNIT_EQUIPMENT', 3, '+1 armour save'),
('Orc Warboss', 'War Boar', 'MOUNT', 16, 'War boar'),
('Orc Warboss', 'Wyvern', 'MOUNT', 200, 'Monstrous flying mount'),

('Black Orc Warboss', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Black Orc Warboss', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Black Orc Warboss', 'Choppas', 'FREE_UPGRADE', 0, '+1 Strength in the first round of combat'),
('Black Orc Warboss', 'Quell Animosity', 'FREE_UPGRADE', 0, 'Stops nearby units from squabbling'),
('Black Orc Warboss', 'Great weapon', 'UNIT_EQUIPMENT', 6, '+2 Strength, always strikes last'),
('Black Orc Warboss', 'Shield', 'UNIT_EQUIPMENT', 3, '+1 armour save'),
('Black Orc Warboss', 'War Boar', 'MOUNT', 16, 'War boar'),
('Black Orc Warboss', 'Wyvern', 'MOUNT', 200, 'Monstrous flying mount'),

('Orc Great Shaman', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Orc Great Shaman', 'Level 3 Wizard', 'FREE_UPGRADE', 0, 'Level 3 Wizard'),
('Orc Great Shaman', 'Level 4 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 4 Wizard'),
('Orc Great Shaman', 'War Boar', 'MOUNT', 16, 'War boar'),
('Orc Great Shaman', 'Wyvern', 'MOUNT', 200, 'Monstrous flying mount'),

('Night Goblin Warboss', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Night Goblin Warboss', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Night Goblin Warboss', 'Great weapon', 'UNIT_EQUIPMENT', 4, '+2 Strength, always strikes last'),
('Night Goblin Warboss', 'Additional hand weapon', 'UNIT_EQUIPMENT', 4, '+1 Attack'),
('Night Goblin Warboss', 'Shield', 'UNIT_EQUIPMENT', 2, '+1 armour save'),
('Night Goblin Warboss', 'Great Cave Squig', 'MOUNT', 30, 'Bouncing squig mount'),

('Goblin Great Shaman', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Goblin Great Shaman', 'Level 3 Wizard', 'FREE_UPGRADE', 0, 'Level 3 Wizard'),
('Goblin Great Shaman', 'Level 4 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 4 Wizard'),
('Goblin Great Shaman', 'Giant Wolf', 'MOUNT', 12, 'Giant wolf'),
('Goblin Great Shaman', 'Gigantic Spider', 'MOUNT', 50, 'Monstrous spider mount'),

('Orc Big Boss', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Orc Big Boss', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Orc Big Boss', 'Great weapon', 'UNIT_EQUIPMENT', 4, '+2 Strength, always strikes last'),
('Orc Big Boss', 'Additional hand weapon', 'UNIT_EQUIPMENT', 4, '+1 Attack'),
('Orc Big Boss', 'Shield', 'UNIT_EQUIPMENT', 2, '+1 armour save'),
('Orc Big Boss', 'Battle Standard', 'BSB', 25, 'Battle Standard Bearer'),
('Orc Big Boss', 'War Boar', 'MOUNT', 16, 'War boar'),

('Black Orc Big Boss', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Black Orc Big Boss', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Black Orc Big Boss', 'Choppas', 'FREE_UPGRADE', 0, '+1 Strength in the first round of combat'),
('Black Orc Big Boss', 'Quell Animosity', 'FREE_UPGRADE', 0, 'Stops nearby units from squabbling'),
('Black Orc Big Boss', 'Great weapon', 'UNIT_EQUIPMENT', 4, '+2 Strength, always strikes last'),
('Black Orc Big Boss', 'Shield', 'UNIT_EQUIPMENT', 2, '+1 armour save'),
('Black Orc Big Boss', 'Battle Standard', 'BSB', 25, 'Battle Standard Bearer'),
('Black Orc Big Boss', 'War Boar', 'MOUNT', 16, 'War boar'),

('Orc Shaman', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Orc Shaman', 'Level 1 Wizard', 'FREE_UPGRADE', 0, 'Level 1 Wizard'),
('Orc Shaman', 'Level 2 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 2 Wizard'),
('Orc Shaman', 'War Boar', 'MOUNT', 16, 'War boar'),

('Night Goblin Big Boss', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Night Goblin Big Boss', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Night Goblin Big Boss', 'Great weapon', 'UNIT_EQUIPMENT', 4, '+2 Strength, always strikes last'),
('Night Goblin Big Boss', 'Shield', 'UNIT_EQUIPMENT', 2, '+1 armour save'),
('Night Goblin Big Boss', 'Battle Standard', 'BSB', 25, 'Battle Standard Bearer'),
('Night Goblin Big Boss', 'Great Cave Squig', 'MOUNT', 30, 'Bouncing squig mount'),

('Night Goblin Shaman', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Night Goblin Shaman', 'Level 1 Wizard', 'FREE_UPGRADE', 0, 'Level 1 Wizard'),
('Night Goblin Shaman', 'Level 2 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 2 Wizard'),

('Orc Boyz', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Orc Boyz', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Orc Boyz', 'Shield', 'UNIT_EQUIPMENT', 1, '+1 armour save'),
('Orc Boyz', 'Spears', 'UNIT_EQUIPMENT', 1, 'Fights in an extra rank'),
('Orc Boyz', 'Additional hand weapon', 'UNIT_EQUIPMENT', 1, '+1 Attack'),
('Orc Boyz', 'Big ''Uns', 'UNIT_EQUIPMENT', 2, 'Bigger and meaner, +1 Weapon Skill and +1 Strength'),
('Orc Boyz', 'Boss', 'COMMAND_GROUP', 10, 'Champion'),
('Orc Boyz', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Orc Boyz', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Orc Arrer Boyz', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Orc Arrer Boyz', 'Bow', 'FREE_UPGRADE', 0, 'Bow'),
('Orc Arrer Boyz', 'Light Armour', 'UNIT_EQUIPMENT', 1, 'Light armour, 6+ armour save'),
('Orc Arrer Boyz', 'Boss', 'COMMAND_GROUP', 10, 'Champion'),
('Orc Arrer Boyz', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Orc Arrer Boyz', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Night Goblins', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Night Goblins', 'Shield', 'UNIT_EQUIPMENT', 0.5, '+1 armour save'),
('Night Goblins', 'Spears', 'UNIT_EQUIPMENT', 0.5, 'Fights in an extra rank'),
('Night Goblins', 'Short Bow', 'UNIT_EQUIPMENT', 0.5, 'Short range bow'),
('Night Goblins', 'Nets', 'COMMAND_GROUP', 35, 'Netters reduce the Strength of enemies in contact'),
('Night Goblins', 'Night Goblin Fanatic', 'COMMAND_GROUP', 25, 'Hidden fanatic released when the enemy comes close'),
('Night Goblins', 'Boss', 'COMMAND_GROUP', 10, 'Champion'),
('Night Goblins', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Night Goblins', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Goblins', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Goblins', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Goblins', 'Shield', 'UNIT_EQUIPMENT', 0.5, '+1 armour save'),
('Goblins', 'Spears', 'UNIT_EQUIPMENT', 0.5, 'Fights in an extra rank'),
('Goblins', 'Short Bow', 'UNIT_EQUIPMENT', 0.5, 'Short range bow'),
('Goblins', 'Boss', 'COMMAND_GROUP', 10, 'Champion'),
('Goblins', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Goblins', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Goblin Wolf Riders', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Goblin Wolf Riders', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Goblin Wolf Riders', 'Giant Wolf', 'FREE_UPGRADE', 0, 'Rides a giant wolf'),
('Goblin Wolf Riders', 'Fast Cavalry', 'FREE_UPGRADE', 0, 'Free reform and vanguard'),
('Goblin Wolf Riders', 'Shield', 'UNIT_EQUIPMENT', 1, '+1 armour save'),
('Goblin Wolf Riders', 'Spears', 'UNIT_EQUIPMENT', 1, 'Fights in an extra rank'),
('Goblin Wolf Riders', 'Short Bow', 'UNIT_EQUIPMENT', 1, 'Short range bow'),
('Goblin Wolf Riders', 'Boss', 'COMMAND_GROUP', 10, 'Champion'),
('Goblin Wolf Riders', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Goblin Wolf Riders', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Snotlings', 'Unbreakable', 'FREE_UPGRADE', 0, 'Never flees'),
('Snotlings', 'Skirmish', 'FREE_UPGRADE', 0, 'Loose formation'),

('Black Orcs', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Black Orcs', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Black Orcs', 'Armed to da Teef', 'FREE_UPGRADE', 0, 'Chooses hand weapons, two weapons or great weapons each round'),
('Black Orcs', 'Choppas', 'FREE_UPGRADE', 0, '+1 Strength in the first round of combat'),
('Black Orcs', 'Quell Animosity', 'FREE_UPGRADE', 0, 'Stops nearby units from squabbling'),
('Black Orcs', 'Shield', 'UNIT_EQUIPMENT', 1, '+1 armour save'),
('Black Orcs', 'Boss', 'COMMAND_GROUP', 10, 'Champion'),
('Black Orcs', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Black Orcs', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Savage Orcs', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Savage Orcs', 'Frenzy', 'FREE_UPGRADE', 0, '+1 Attack, must charge'),
('Savage Orcs', 'Warpaint', 'FREE_UPGRADE', 0, '6+ ward save'),
('Savage Orcs', 'Additional hand weapon', 'UNIT_EQUIPMENT', 1, '+1 Attack'),
('Savage Orcs', 'Shield', 'UNIT_EQUIPMENT', 1, '+1 armour save'),
('Savage Orcs', 'Spears', 'UNIT_EQUIPMENT', 1, 'Fights in an extra rank'),
('Savage Orcs', 'Big ''Uns', 'UNIT_EQUIPMENT', 2, 'Bigger and meaner, +1 Weapon Skill and +1 Strength'),
('Savage Orcs', 'Boss', 'COMMAND_GROUP', 10, 'Champion'),
('Savage Orcs', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Savage Orcs', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Orc Boar Boyz', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Orc Boar Boyz', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Orc Boar Boyz', 'Shield', 'FREE_UPGRADE', 0, '+1 armour save'),
('Orc Boar Boyz', 'War Boar', 'FREE_UPGRADE', 0, 'Rides a war boar'),
('Orc Boar Boyz', 'Spears', 'UNIT_EQUIPMENT', 2, 'Fights in an extra rank'),
('Orc Boar Boyz', 'Big ''Uns', 'UNIT_EQUIPMENT', 3, 'Bigger and meaner, +1 Weapon Skill and +1 Strength'),
('Orc Boar Boyz', 'Boss', 'COMMAND_GROUP', 10, 'Champion'),
('Orc Boar Boyz', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Orc Boar Boyz', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Trolls', 'Regenerate', 'FREE_UPGRADE', 0, 'Regeneration save'),
('Trolls', 'Stupidity', 'FREE_UPGRADE', 0, 'May wander around aimlessly'),
('Trolls', 'Fear', 'FREE_UPGRADE', 0, 'Causes Fear'),

('Orc Boar Chariot', 'Chariot Crew', 'FREE_UPGRADE', 0, 'Two Orc crew and two war boars'),
('Orc Boar Chariot', 'Scythes', 'FREE_UPGRADE', 0, 'Extra impact hits'),
('Goblin Wolf Chariot', 'Chariot Crew', 'FREE_UPGRADE', 0, 'Three Goblin crew and two wolves'),
('Goblin Spear Chukka', 'Crew', 'FREE_UPGRADE', 0, 'Three Goblin crew'),
('Goblin Rock Lobber', 'Crew', 'FREE_UPGRADE', 0, 'Three Goblin crew'),
('Doom Diver Catapult', 'Crew', 'FREE_UPGRADE', 0, 'Three Goblin crew'),
('Giant', 'Stubborn', 'FREE_UPGRADE', 0, 'Always uses its own Leadership'),
('Giant', 'Terror', 'FREE_UPGRADE', 0, 'Causes Terror'),
('Giant', 'Large Target', 'FREE_UPGRADE', 0, 'Can be seen over other units'),
('Snotling Pump Wagon', 'Random Movement', 'FREE_UPGRADE', 0, 'Moves a random distance');

INSERT INTO staging_unit_upgrade
SELECT unit.name, item.name, item.upgrade_type, item.points, item.description
FROM staging_unit unit
CROSS JOIN (VALUES
    ('Battleaxe of the Last Waaagh!', 'MAGIC_WEAPON', 70, 'Gains extra attacks for every enemy rank'),
    ('Porko''s Pigstikka', 'MAGIC_WEAPON', 25, 'Deadly on the charge'),
    ('Nibbla''s ''Itty Ring', 'MAGIC_ITEM', 30, 'One use: blasts a unit with a powerful hex')
) AS item(name, upgrade_type, points, description)
WHERE unit.unit_type IN ('LORDS', 'HERO');

INSERT INTO staging_unit_upgrade
SELECT unit_name, banner.name, 'MAGIC_BANNER', banner.points, banner.description
FROM (VALUES ('Orc Big Boss'), ('Black Orc Big Boss'), ('Night Goblin Big Boss'), ('Black Orcs'), ('Orc Boyz'), ('Orc Boar Boyz'), ('Savage Orcs')) AS bearers(unit_name)
CROSS JOIN (VALUES
    ('Spider Banner', 50, 'The unit has Poisoned Attacks'),
    ('Bad Moon Banner', 50, 'Enemy shooting at the unit is harder to hit')
) AS banner(name, points, description);

DO
$$
    DECLARE
        staged   RECORD;
        stats_id BIGINT;
    BEGIN
        IF EXISTS (SELECT 1
                   FROM staging_unit_upgrade su
                   WHERE NOT EXISTS (SELECT 1 FROM staging_unit s WHERE s.name = su.unit_name)) THEN
            RAISE EXCEPTION 'Orcs and Goblins upgrade refers to an unknown unit';
        END IF;

        FOR staged IN SELECT * FROM staging_unit
            LOOP
                INSERT INTO unit_stats (m, ws, bs, s, t, w, i, a, ld, basic_save, ward_save)
                VALUES (staged.m, staged.ws, staged.bs, staged.s, staged.t, staged.w, staged.i, staged.a, staged.ld,
                        staged.basic_save, staged.ward_save)
                RETURNING id INTO stats_id;

                INSERT INTO unit (name, points_cost_per_unit, min_quantity, unit_type, unit_faction, unit_stats_id)
                VALUES (staged.name, staged.points, staged.min_quantity, staged.unit_type, 'ORCS_AND_GOBLINS', stats_id);
            END LOOP;
    END
$$;

INSERT INTO upgrade (name, upgrade_type, description)
SELECT DISTINCT ON (upgrade_name, upgrade_type) upgrade_name, upgrade_type, description
FROM staging_unit_upgrade
ON CONFLICT (name, upgrade_type) DO NOTHING;

INSERT INTO unit_upgrade (unit_id, upgrade_id, points_cost)
SELECT u.id, up.id, su.points
FROM staging_unit_upgrade su
JOIN unit u ON u.name = su.unit_name AND u.unit_faction = 'ORCS_AND_GOBLINS'
JOIN upgrade up ON up.name = su.upgrade_name AND up.upgrade_type = su.upgrade_type;

DROP TABLE staging_unit_upgrade;
DROP TABLE staging_unit;
