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
('Dwarf Lord', 140, 1, 'LORDS', 3, 7, 4, 4, 5, 3, 4, 4, 10, 0, 0),
('Runelord', 120, 1, 'LORDS', 3, 6, 4, 4, 5, 3, 3, 2, 10, 0, 0),
('Daemon Slayer', 100, 1, 'LORDS', 3, 7, 3, 4, 5, 3, 5, 4, 10, 0, 0),
('Thane', 60, 1, 'HERO', 3, 6, 4, 4, 5, 2, 3, 3, 9, 0, 0),
('Runesmith', 70, 1, 'HERO', 3, 5, 4, 4, 5, 2, 2, 2, 9, 0, 0),
('Master Engineer', 70, 1, 'HERO', 3, 4, 5, 4, 4, 2, 2, 2, 9, 0, 0),
('Dragon Slayer', 50, 1, 'HERO', 3, 6, 3, 4, 4, 2, 4, 3, 10, 0, 0),
('Dwarf Warriors', 8, 10, 'CORE', 3, 4, 3, 3, 4, 1, 2, 1, 9, 0, 0),
('Longbeards', 11, 10, 'CORE', 3, 5, 3, 4, 4, 1, 2, 1, 9, 0, 0),
('Quarrellers', 11, 10, 'CORE', 3, 4, 3, 3, 4, 1, 2, 1, 9, 0, 0),
('Thunderers', 13, 10, 'CORE', 3, 4, 3, 3, 4, 1, 2, 1, 9, 0, 0),
('Rangers', 12, 10, 'CORE', 3, 4, 3, 3, 4, 1, 2, 1, 9, 0, 0),
('Hammerers', 13, 10, 'SPECIAL', 3, 5, 3, 4, 4, 1, 2, 1, 9, 0, 0),
('Ironbreakers', 13, 10, 'SPECIAL', 3, 5, 3, 4, 4, 1, 2, 1, 9, 0, 0),
('Miners', 11, 10, 'SPECIAL', 3, 4, 3, 3, 4, 1, 2, 1, 9, 0, 0),
('Slayers', 12, 5, 'SPECIAL', 3, 5, 3, 3, 4, 1, 2, 1, 10, 0, 0),
('Irondrakes', 18, 10, 'SPECIAL', 3, 4, 3, 4, 4, 1, 2, 1, 9, 0, 0),
('Cannon', 90, 1, 'SPECIAL', 0, 0, 0, 0, 7, 3, 0, 0, 0, 0, 0),
('Grudge Thrower', 80, 1, 'SPECIAL', 0, 0, 0, 0, 7, 3, 0, 0, 0, 0, 0),
('Gyrocopter', 75, 1, 'SPECIAL', 1, 4, 3, 4, 5, 3, 2, 2, 9, 4, 0),
('Organ Gun', 120, 1, 'RARE', 0, 0, 0, 0, 7, 3, 0, 0, 0, 0, 0),
('Flame Cannon', 140, 1, 'RARE', 0, 0, 0, 0, 7, 3, 0, 0, 0, 0, 0),
('Gyrobomber', 120, 1, 'RARE', 1, 4, 3, 4, 5, 3, 2, 2, 9, 4, 0);

INSERT INTO staging_unit_upgrade
SELECT unit.name, rule.name, 'FREE_UPGRADE', 0, rule.description
FROM staging_unit unit
CROSS JOIN (VALUES
    ('Resolute', '+1 Strength in the turn they charge'),
    ('Relentless', 'May march even with enemies nearby'),
    ('Ancestral Grudge', 'Hatred against Orcs and Goblins')
) AS rule(name, description);

INSERT INTO staging_unit_upgrade VALUES
('Dwarf Lord', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Dwarf Lord', 'Gromril Armour', 'FREE_UPGRADE', 0, 'Gromril armour, 4+ armour save'),
('Dwarf Lord', 'Great weapon', 'UNIT_EQUIPMENT', 6, '+2 Strength, always strikes last'),
('Dwarf Lord', 'Shield', 'UNIT_EQUIPMENT', 3, '+1 armour save'),
('Dwarf Lord', 'Oath Stone', 'UNIT_EQUIPMENT', 20, 'He and his unit become Stubborn'),
('Dwarf Lord', 'Shieldbearers', 'MOUNT', 25, 'Carried into battle on a shield'),

('Runelord', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Runelord', 'Gromril Armour', 'FREE_UPGRADE', 0, 'Gromril armour, 4+ armour save'),
('Runelord', 'Rune Lore', 'FREE_UPGRADE', 0, 'Adds dice to dispel attempts'),
('Runelord', 'Shield', 'UNIT_EQUIPMENT', 3, '+1 armour save'),
('Runelord', 'Anvil of Doom', 'UNIT_EQUIPMENT', 175, 'Strikes runes of power each turn'),

('Daemon Slayer', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Daemon Slayer', 'Additional hand weapon', 'FREE_UPGRADE', 0, '+1 Attack'),
('Daemon Slayer', 'Slayer', 'FREE_UPGRADE', 0, 'Unbreakable, wounds big monsters easily'),

('Thane', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Thane', 'Gromril Armour', 'FREE_UPGRADE', 0, 'Gromril armour, 4+ armour save'),
('Thane', 'Great weapon', 'UNIT_EQUIPMENT', 4, '+2 Strength, always strikes last'),
('Thane', 'Shield', 'UNIT_EQUIPMENT', 2, '+1 armour save'),
('Thane', 'Oath Stone', 'UNIT_EQUIPMENT', 20, 'He and his unit become Stubborn'),
('Thane', 'Battle Standard', 'BSB', 25, 'Battle Standard Bearer'),

('Runesmith', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Runesmith', 'Gromril Armour', 'FREE_UPGRADE', 0, 'Gromril armour, 4+ armour save'),
('Runesmith', 'Rune Lore', 'FREE_UPGRADE', 0, 'Adds dice to dispel attempts'),
('Runesmith', 'Great weapon', 'UNIT_EQUIPMENT', 4, '+2 Strength, always strikes last'),
('Runesmith', 'Shield', 'UNIT_EQUIPMENT', 2, '+1 armour save'),

('Master Engineer', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Master Engineer', 'Gromril Armour', 'FREE_UPGRADE', 0, 'Gromril armour, 4+ armour save'),
('Master Engineer', 'Great weapon', 'UNIT_EQUIPMENT', 4, '+2 Strength, always strikes last'),
('Master Engineer', 'Brace of Pistols', 'UNIT_EQUIPMENT', 10, 'Two pistols, shooting and close combat'),
('Master Engineer', 'Handgun', 'UNIT_EQUIPMENT', 5, 'Armour piercing gun'),

('Dragon Slayer', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Dragon Slayer', 'Additional hand weapon', 'FREE_UPGRADE', 0, '+1 Attack'),
('Dragon Slayer', 'Slayer', 'FREE_UPGRADE', 0, 'Unbreakable, wounds big monsters easily'),

('Dwarf Warriors', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Dwarf Warriors', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Dwarf Warriors', 'Shield', 'UNIT_EQUIPMENT', 1, '+1 armour save'),
('Dwarf Warriors', 'Great weapon', 'UNIT_EQUIPMENT', 1, '+2 Strength, always strikes last'),
('Dwarf Warriors', 'Veteran', 'COMMAND_GROUP', 10, 'Champion'),
('Dwarf Warriors', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Dwarf Warriors', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Longbeards', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Longbeards', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Longbeards', 'Old Grumblers', 'FREE_UPGRADE', 0, 'Nearby Dwarfs are Immune to Panic'),
('Longbeards', 'Shield', 'UNIT_EQUIPMENT', 1, '+1 armour save'),
('Longbeards', 'Great weapon', 'UNIT_EQUIPMENT', 1, '+2 Strength, always strikes last'),
('Longbeards', 'Elder', 'COMMAND_GROUP', 10, 'Champion'),
('Longbeards', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Longbeards', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Quarrellers', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Quarrellers', 'Crossbow', 'FREE_UPGRADE', 0, 'Strength 4 long range weapon'),
('Quarrellers', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Quarrellers', 'Shield', 'UNIT_EQUIPMENT', 1, '+1 armour save'),
('Quarrellers', 'Great weapon', 'UNIT_EQUIPMENT', 1, '+2 Strength, always strikes last'),
('Quarrellers', 'Veteran', 'COMMAND_GROUP', 10, 'Champion'),
('Quarrellers', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Quarrellers', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Thunderers', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Thunderers', 'Handgun', 'FREE_UPGRADE', 0, 'Armour piercing gun'),
('Thunderers', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Thunderers', 'Shield', 'UNIT_EQUIPMENT', 1, '+1 armour save'),
('Thunderers', 'Veteran', 'COMMAND_GROUP', 10, 'Champion'),
('Thunderers', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Thunderers', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Rangers', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Rangers', 'Crossbow', 'FREE_UPGRADE', 0, 'Strength 4 long range weapon'),
('Rangers', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Rangers', 'Scouts', 'FREE_UPGRADE', 0, 'Deploys as Scouts'),
('Rangers', 'Throwing Axes', 'UNIT_EQUIPMENT', 1, 'Short range thrown weapon'),
('Rangers', 'Great weapon', 'UNIT_EQUIPMENT', 1, '+2 Strength, always strikes last'),
('Rangers', 'Veteran', 'COMMAND_GROUP', 10, 'Champion'),
('Rangers', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Rangers', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Hammerers', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Hammerers', 'Great weapon', 'FREE_UPGRADE', 0, '+2 Strength, always strikes last'),
('Hammerers', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Hammerers', 'Stubborn', 'FREE_UPGRADE', 0, 'Always uses its own Leadership'),
('Hammerers', 'Shield', 'UNIT_EQUIPMENT', 2, '+1 armour save'),
('Hammerers', 'Gate Keeper', 'COMMAND_GROUP', 10, 'Champion'),
('Hammerers', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Hammerers', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Ironbreakers', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Ironbreakers', 'Gromril Armour', 'FREE_UPGRADE', 0, 'Gromril armour, 4+ armour save'),
('Ironbreakers', 'Shield', 'FREE_UPGRADE', 0, '+1 armour save'),
('Ironbreakers', 'Ironbeard', 'COMMAND_GROUP', 10, 'Champion'),
('Ironbreakers', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Ironbreakers', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Miners', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Miners', 'Great weapon', 'FREE_UPGRADE', 0, '+2 Strength, always strikes last'),
('Miners', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Miners', 'Underground Advance', 'FREE_UPGRADE', 0, 'Can arrive from any table edge'),
('Miners', 'Prospector', 'COMMAND_GROUP', 10, 'Champion'),
('Miners', 'Blasting Charges', 'COMMAND_GROUP', 15, 'Prospector carries explosives'),
('Miners', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Miners', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Slayers', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Slayers', 'Additional hand weapon', 'FREE_UPGRADE', 0, '+1 Attack'),
('Slayers', 'Slayer', 'FREE_UPGRADE', 0, 'Unbreakable, wounds big monsters easily'),
('Slayers', 'Great weapon', 'UNIT_EQUIPMENT', 1, '+2 Strength, always strikes last'),
('Slayers', 'Giant Slayer', 'COMMAND_GROUP', 12, 'Champion'),

('Irondrakes', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Irondrakes', 'Drakegun', 'FREE_UPGRADE', 0, 'Flaming breath weapon'),
('Irondrakes', 'Gromril Armour', 'FREE_UPGRADE', 0, 'Gromril armour, 4+ armour save'),
('Irondrakes', 'Ironwarden', 'COMMAND_GROUP', 10, 'Champion'),
('Irondrakes', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Irondrakes', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Cannon', 'Crew', 'FREE_UPGRADE', 0, 'Three Dwarf crew'),
('Grudge Thrower', 'Crew', 'FREE_UPGRADE', 0, 'Three Dwarf crew'),
('Organ Gun', 'Crew', 'FREE_UPGRADE', 0, 'Three Dwarf crew'),
('Flame Cannon', 'Crew', 'FREE_UPGRADE', 0, 'Three Dwarf crew'),
('Gyrocopter', 'Steam Gun', 'FREE_UPGRADE', 0, 'Flame template weapon'),
('Gyrocopter', 'Fly', 'FREE_UPGRADE', 0, 'Flying machine'),
('Gyrobomber', 'Clattergun', 'FREE_UPGRADE', 0, 'Rapid firing gun'),
('Gyrobomber', 'Fly', 'FREE_UPGRADE', 0, 'Flying machine');

INSERT INTO staging_unit_upgrade
SELECT unit_name, rune.name, rune.upgrade_type, rune.points, rune.description
FROM (VALUES ('Dwarf Lord'), ('Runelord'), ('Thane'), ('Runesmith'), ('Master Engineer')) AS characters(unit_name)
CROSS JOIN (VALUES
    ('Master Rune of Skalf Blackhammer', 'MAGIC_WEAPON', 75, 'Hits wound automatically'),
    ('Master Rune of Swiftness', 'MAGIC_WEAPON', 25, 'Always strikes first'),
    ('Rune of Fury', 'MAGIC_WEAPON', 25, '+1 Attack'),
    ('Rune of Might', 'MAGIC_WEAPON', 25, 'Doubles Strength against tougher foes'),
    ('Rune of Cleaving', 'MAGIC_WEAPON', 10, '+1 Strength'),
    ('Rune of Striking', 'MAGIC_WEAPON', 10, '+1 Weapon Skill'),
    ('Master Rune of Gromril', 'MAGIC_ARMOUR', 25, '1+ armour save that cannot be improved'),
    ('Rune of Iron', 'MAGIC_ARMOUR', 25, '+1 Wound'),
    ('Rune of Stone', 'MAGIC_ARMOUR', 5, '+1 armour save'),
    ('Master Rune of Spite', 'MAGIC_ITEM', 45, 'Attackers may be struck back'),
    ('Master Rune of Balance', 'MAGIC_ITEM', 40, 'Steals a power die from the enemy'),
    ('Rune of Spellbreaking', 'MAGIC_ITEM', 25, 'One use: dispels a spell automatically'),
    ('Rune of Warding', 'MAGIC_ITEM', 25, 'Ward save against spells'),
    ('Rune of Luck', 'MAGIC_ITEM', 10, 'Re-roll one failed roll per game')
) AS rune(name, upgrade_type, points, description);

INSERT INTO staging_unit_upgrade
SELECT unit_name, rune.name, 'MAGIC_BANNER', rune.points, rune.description
FROM (VALUES ('Thane'), ('Hammerers'), ('Ironbreakers'), ('Longbeards'), ('Dwarf Warriors')) AS bearers(unit_name)
CROSS JOIN (VALUES
    ('Master Rune of Valaya', 100, 'Magic Resistance for nearby units'),
    ('Rune of Battle', 25, '+1 combat resolution'),
    ('Rune of Courage', 25, 'Re-roll failed Break tests'),
    ('Rune of Sanctuary', 15, 'Protection against magic missiles')
) AS rune(name, points, description);

DO
$$
    DECLARE
        staged   RECORD;
        stats_id BIGINT;
    BEGIN
        IF EXISTS (SELECT 1
                   FROM staging_unit_upgrade su
                   WHERE NOT EXISTS (SELECT 1 FROM staging_unit s WHERE s.name = su.unit_name)) THEN
            RAISE EXCEPTION 'Dwarf upgrade refers to an unknown unit';
        END IF;

        FOR staged IN SELECT * FROM staging_unit
            LOOP
                INSERT INTO unit_stats (m, ws, bs, s, t, w, i, a, ld, basic_save, ward_save)
                VALUES (staged.m, staged.ws, staged.bs, staged.s, staged.t, staged.w, staged.i, staged.a, staged.ld,
                        staged.basic_save, staged.ward_save)
                RETURNING id INTO stats_id;

                INSERT INTO unit (name, points_cost_per_unit, min_quantity, unit_type, unit_faction, unit_stats_id)
                VALUES (staged.name, staged.points, staged.min_quantity, staged.unit_type, 'DWARFS', stats_id);
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
JOIN unit u ON u.name = su.unit_name AND u.unit_faction = 'DWARFS'
JOIN upgrade up ON up.name = su.upgrade_name AND up.upgrade_type = su.upgrade_type;

DROP TABLE staging_unit_upgrade;
DROP TABLE staging_unit;
