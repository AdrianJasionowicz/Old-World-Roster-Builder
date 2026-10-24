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
('General of the Empire', 85, 1, 'LORDS', 4, 5, 5, 4, 4, 3, 5, 3, 9, 0, 0),
('Arch Lector of Sigmar', 105, 1, 'LORDS', 4, 5, 3, 4, 4, 3, 4, 3, 9, 0, 0),
('Lord Magister', 165, 1, 'LORDS', 4, 3, 3, 3, 3, 3, 3, 1, 8, 0, 0),
('Grand Master', 145, 1, 'LORDS', 4, 6, 3, 4, 4, 3, 6, 4, 9, 0, 0),
('Captain of the Empire', 50, 1, 'HERO', 4, 5, 5, 4, 4, 2, 5, 3, 8, 0, 0),
('Warrior Priest of Sigmar', 65, 1, 'HERO', 4, 4, 3, 4, 4, 2, 4, 2, 8, 0, 0),
('Battle Wizard', 65, 1, 'HERO', 4, 3, 3, 3, 3, 2, 3, 1, 7, 0, 0),
('Master Engineer', 65, 1, 'HERO', 4, 3, 4, 3, 3, 2, 3, 1, 7, 0, 0),
('Witch Hunter', 50, 1, 'HERO', 4, 4, 4, 4, 4, 2, 4, 2, 8, 0, 0),
('Halberdiers', 6, 10, 'CORE', 4, 3, 3, 3, 3, 1, 3, 1, 7, 0, 0),
('Spearmen', 5, 10, 'CORE', 4, 3, 3, 3, 3, 1, 3, 1, 7, 0, 0),
('Swordsmen', 6, 10, 'CORE', 4, 4, 3, 3, 3, 1, 4, 1, 7, 0, 0),
('Handgunners', 8, 10, 'CORE', 4, 3, 3, 3, 3, 1, 3, 1, 7, 0, 0),
('Crossbowmen', 8, 10, 'CORE', 4, 3, 3, 3, 3, 1, 3, 1, 7, 0, 0),
('Archers', 7, 10, 'CORE', 4, 3, 3, 3, 3, 1, 3, 1, 7, 0, 0),
('Free Company Militia', 5, 10, 'CORE', 4, 3, 3, 3, 3, 1, 3, 1, 7, 0, 0),
('Knights of the Empire', 22, 5, 'CORE', 4, 4, 3, 3, 3, 1, 3, 1, 8, 0, 0),
('Greatswords', 11, 10, 'SPECIAL', 4, 4, 3, 3, 3, 1, 3, 1, 8, 0, 0),
('Pistoliers', 16, 5, 'SPECIAL', 4, 3, 3, 3, 3, 1, 3, 1, 7, 0, 0),
('Outriders', 18, 5, 'SPECIAL', 4, 3, 4, 3, 3, 1, 3, 1, 7, 0, 0),
('Demigryph Knights', 55, 3, 'SPECIAL', 4, 4, 3, 4, 3, 1, 3, 1, 8, 0, 0),
('Flagellants', 10, 10, 'SPECIAL', 4, 2, 2, 3, 3, 1, 3, 1, 10, 0, 0),
('Great Cannon', 120, 1, 'SPECIAL', 0, 0, 0, 0, 7, 3, 0, 0, 0, 0, 0),
('Mortar', 90, 1, 'SPECIAL', 0, 0, 0, 0, 7, 3, 0, 0, 0, 0, 0),
('Helblaster Volley Gun', 120, 1, 'RARE', 0, 0, 0, 0, 7, 3, 0, 0, 0, 0, 0),
('Helstorm Rocket Battery', 120, 1, 'RARE', 0, 0, 0, 0, 7, 3, 0, 0, 0, 0, 0),
('Steam Tank', 250, 1, 'RARE', 0, 0, 4, 6, 6, 10, 0, 0, 10, 1, 0),
('Luminark of Hysh', 120, 1, 'RARE', 0, 0, 0, 5, 5, 5, 0, 0, 0, 0, 0),
('Celestial Hurricanum', 120, 1, 'RARE', 0, 0, 0, 5, 5, 5, 0, 0, 0, 0, 0);

INSERT INTO staging_unit_upgrade VALUES
('General of the Empire', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('General of the Empire', 'Full Plate Armour', 'FREE_UPGRADE', 0, 'Full plate armour, 4+ armour save'),
('General of the Empire', 'Great weapon', 'UNIT_EQUIPMENT', 6, '+2 Strength, always strikes last'),
('General of the Empire', 'Lance', 'UNIT_EQUIPMENT', 6, '+2 Strength on the charge, mounted only'),
('General of the Empire', 'Additional hand weapon', 'UNIT_EQUIPMENT', 3, '+1 Attack'),
('General of the Empire', 'Brace of Pistols', 'UNIT_EQUIPMENT', 10, 'Two pistols, shooting and close combat'),
('General of the Empire', 'Shield', 'UNIT_EQUIPMENT', 3, '+1 armour save'),
('General of the Empire', 'Barded Warhorse', 'MOUNT', 18, 'Barded warhorse'),
('General of the Empire', 'Imperial Pegasus', 'MOUNT', 50, 'Flying mount'),
('General of the Empire', 'Imperial Griffon', 'MOUNT', 150, 'Monstrous flying mount'),

('Arch Lector of Sigmar', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Arch Lector of Sigmar', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Arch Lector of Sigmar', 'Prayers of Sigmar', 'FREE_UPGRADE', 0, 'Knows the Prayers of Sigmar'),
('Arch Lector of Sigmar', 'Great weapon', 'UNIT_EQUIPMENT', 6, '+2 Strength, always strikes last'),
('Arch Lector of Sigmar', 'Additional hand weapon', 'UNIT_EQUIPMENT', 3, '+1 Attack'),
('Arch Lector of Sigmar', 'Shield', 'UNIT_EQUIPMENT', 3, '+1 armour save'),
('Arch Lector of Sigmar', 'Barded Warhorse', 'MOUNT', 18, 'Barded warhorse'),
('Arch Lector of Sigmar', 'War Altar of Sigmar', 'MOUNT', 140, 'Chariot carrying the Golden Griffon'),

('Lord Magister', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Lord Magister', 'Level 3 Wizard', 'FREE_UPGRADE', 0, 'Level 3 Wizard'),
('Lord Magister', 'Level 4 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 4 Wizard'),
('Lord Magister', 'Warhorse', 'MOUNT', 12, 'Warhorse'),
('Lord Magister', 'Barded Warhorse', 'MOUNT', 18, 'Barded warhorse'),
('Lord Magister', 'Imperial Pegasus', 'MOUNT', 50, 'Flying mount'),

('Grand Master', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Grand Master', 'Lance', 'FREE_UPGRADE', 0, '+2 Strength on the charge'),
('Grand Master', 'Full Plate Armour', 'FREE_UPGRADE', 0, 'Full plate armour, 4+ armour save'),
('Grand Master', 'Shield', 'FREE_UPGRADE', 0, '+1 armour save'),
('Grand Master', 'Barded Warhorse', 'FREE_UPGRADE', 0, 'Rides a barded warhorse'),
('Grand Master', 'Inspiring Presence of the Order', 'FREE_UPGRADE', 0, 'Knights within range use his Leadership'),

('Captain of the Empire', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Captain of the Empire', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Captain of the Empire', 'Great weapon', 'UNIT_EQUIPMENT', 4, '+2 Strength, always strikes last'),
('Captain of the Empire', 'Lance', 'UNIT_EQUIPMENT', 4, '+2 Strength on the charge, mounted only'),
('Captain of the Empire', 'Additional hand weapon', 'UNIT_EQUIPMENT', 2, '+1 Attack'),
('Captain of the Empire', 'Brace of Pistols', 'UNIT_EQUIPMENT', 10, 'Two pistols, shooting and close combat'),
('Captain of the Empire', 'Shield', 'UNIT_EQUIPMENT', 2, '+1 armour save'),
('Captain of the Empire', 'Battle Standard', 'BSB', 25, 'Battle Standard Bearer'),
('Captain of the Empire', 'Barded Warhorse', 'MOUNT', 18, 'Barded warhorse'),
('Captain of the Empire', 'Imperial Pegasus', 'MOUNT', 50, 'Flying mount'),

('Warrior Priest of Sigmar', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Warrior Priest of Sigmar', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Warrior Priest of Sigmar', 'Prayers of Sigmar', 'FREE_UPGRADE', 0, 'Knows the Prayers of Sigmar'),
('Warrior Priest of Sigmar', 'Great weapon', 'UNIT_EQUIPMENT', 4, '+2 Strength, always strikes last'),
('Warrior Priest of Sigmar', 'Additional hand weapon', 'UNIT_EQUIPMENT', 2, '+1 Attack'),
('Warrior Priest of Sigmar', 'Shield', 'UNIT_EQUIPMENT', 2, '+1 armour save'),
('Warrior Priest of Sigmar', 'Barded Warhorse', 'MOUNT', 18, 'Barded warhorse'),

('Battle Wizard', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Battle Wizard', 'Level 1 Wizard', 'FREE_UPGRADE', 0, 'Level 1 Wizard'),
('Battle Wizard', 'Level 2 Wizard', 'UNIT_EQUIPMENT', 35, 'Upgrade to a Level 2 Wizard'),
('Battle Wizard', 'Warhorse', 'MOUNT', 12, 'Warhorse'),
('Battle Wizard', 'Barded Warhorse', 'MOUNT', 18, 'Barded warhorse'),

('Master Engineer', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Master Engineer', 'Brace of Pistols', 'UNIT_EQUIPMENT', 10, 'Two pistols, shooting and close combat'),
('Master Engineer', 'Repeater Handgun', 'UNIT_EQUIPMENT', 10, 'Fires several shots'),
('Master Engineer', 'Hochland Long Rifle', 'UNIT_EQUIPMENT', 20, 'Long range sniper rifle'),
('Master Engineer', 'Mechanical Steed', 'MOUNT', 15, 'Clockwork horse'),

('Witch Hunter', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Witch Hunter', 'Brace of Pistols', 'FREE_UPGRADE', 0, 'Two pistols, shooting and close combat'),
('Witch Hunter', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Witch Hunter', 'Great weapon', 'UNIT_EQUIPMENT', 4, '+2 Strength, always strikes last'),
('Witch Hunter', 'Additional hand weapon', 'UNIT_EQUIPMENT', 2, '+1 Attack'),
('Witch Hunter', 'Warhorse', 'MOUNT', 12, 'Warhorse'),

('Halberdiers', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Halberdiers', 'Halberd', 'FREE_UPGRADE', 0, '+1 Strength in close combat'),
('Halberdiers', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Halberdiers', 'Shield', 'UNIT_EQUIPMENT', 1, '+1 armour save'),
('Halberdiers', 'Sergeant', 'COMMAND_GROUP', 6, 'Champion'),
('Halberdiers', 'Standard Bearer', 'COMMAND_GROUP', 6, 'Standard Bearer'),
('Halberdiers', 'Musician', 'COMMAND_GROUP', 3, 'Musician'),

('Spearmen', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Spearmen', 'Spear', 'FREE_UPGRADE', 0, 'Fights in an extra rank'),
('Spearmen', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Spearmen', 'Shield', 'UNIT_EQUIPMENT', 1, '+1 armour save'),
('Spearmen', 'Sergeant', 'COMMAND_GROUP', 6, 'Champion'),
('Spearmen', 'Standard Bearer', 'COMMAND_GROUP', 6, 'Standard Bearer'),
('Spearmen', 'Musician', 'COMMAND_GROUP', 3, 'Musician'),

('Swordsmen', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Swordsmen', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Swordsmen', 'Shield', 'FREE_UPGRADE', 0, '+1 armour save'),
('Swordsmen', 'Duellist', 'COMMAND_GROUP', 6, 'Champion'),
('Swordsmen', 'Standard Bearer', 'COMMAND_GROUP', 6, 'Standard Bearer'),
('Swordsmen', 'Musician', 'COMMAND_GROUP', 3, 'Musician'),

('Handgunners', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Handgunners', 'Handgun', 'FREE_UPGRADE', 0, 'Armour piercing gun'),
('Handgunners', 'Marksman', 'COMMAND_GROUP', 6, 'Champion'),
('Handgunners', 'Hochland Long Rifle', 'COMMAND_GROUP', 20, 'Marksman with a long range sniper rifle'),
('Handgunners', 'Standard Bearer', 'COMMAND_GROUP', 6, 'Standard Bearer'),
('Handgunners', 'Musician', 'COMMAND_GROUP', 3, 'Musician'),

('Crossbowmen', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Crossbowmen', 'Crossbow', 'FREE_UPGRADE', 0, 'Strength 4 long range weapon'),
('Crossbowmen', 'Marksman', 'COMMAND_GROUP', 6, 'Champion'),
('Crossbowmen', 'Standard Bearer', 'COMMAND_GROUP', 6, 'Standard Bearer'),
('Crossbowmen', 'Musician', 'COMMAND_GROUP', 3, 'Musician'),

('Archers', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Archers', 'Longbow', 'FREE_UPGRADE', 0, 'Long range bow'),
('Archers', 'Skirmish', 'FREE_UPGRADE', 0, 'Loose formation'),
('Archers', 'Marksman', 'COMMAND_GROUP', 6, 'Champion'),
('Archers', 'Musician', 'COMMAND_GROUP', 3, 'Musician'),

('Free Company Militia', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Free Company Militia', 'Additional hand weapon', 'FREE_UPGRADE', 0, '+1 Attack'),
('Free Company Militia', 'Militia Leader', 'COMMAND_GROUP', 6, 'Champion'),
('Free Company Militia', 'Standard Bearer', 'COMMAND_GROUP', 6, 'Standard Bearer'),
('Free Company Militia', 'Musician', 'COMMAND_GROUP', 3, 'Musician'),

('Knights of the Empire', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Knights of the Empire', 'Lance', 'FREE_UPGRADE', 0, '+2 Strength on the charge'),
('Knights of the Empire', 'Full Plate Armour', 'FREE_UPGRADE', 0, 'Full plate armour, 4+ armour save'),
('Knights of the Empire', 'Shield', 'FREE_UPGRADE', 0, '+1 armour save'),
('Knights of the Empire', 'Barded Warhorse', 'FREE_UPGRADE', 0, 'Rides a barded warhorse'),
('Knights of the Empire', 'Inner Circle', 'UNIT_EQUIPMENT', 3, 'Elite knights, +1 Strength'),
('Knights of the Empire', 'Preceptor', 'COMMAND_GROUP', 10, 'Champion'),
('Knights of the Empire', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Knights of the Empire', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Greatswords', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Greatswords', 'Great weapon', 'FREE_UPGRADE', 0, '+2 Strength, always strikes last'),
('Greatswords', 'Full Plate Armour', 'FREE_UPGRADE', 0, 'Full plate armour, 4+ armour save'),
('Greatswords', 'Stubborn', 'FREE_UPGRADE', 0, 'Always uses its own Leadership'),
('Greatswords', 'Count''s Champion', 'COMMAND_GROUP', 10, 'Champion'),
('Greatswords', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Greatswords', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Pistoliers', 'Brace of Pistols', 'FREE_UPGRADE', 0, 'Two pistols, shooting and close combat'),
('Pistoliers', 'Light Armour', 'FREE_UPGRADE', 0, 'Light armour, 6+ armour save'),
('Pistoliers', 'Warhorse', 'FREE_UPGRADE', 0, 'Rides a warhorse'),
('Pistoliers', 'Fast Cavalry', 'FREE_UPGRADE', 0, 'Free reform and vanguard'),
('Pistoliers', 'Outrider Champion', 'COMMAND_GROUP', 10, 'Champion'),
('Pistoliers', 'Repeater Pistol', 'COMMAND_GROUP', 10, 'Champion with a repeater pistol'),
('Pistoliers', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Outriders', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Outriders', 'Repeater Handgun', 'FREE_UPGRADE', 0, 'Fires several shots'),
('Outriders', 'Heavy Armour', 'FREE_UPGRADE', 0, 'Heavy armour, 5+ armour save'),
('Outriders', 'Barded Warhorse', 'FREE_UPGRADE', 0, 'Rides a barded warhorse'),
('Outriders', 'Fast Cavalry', 'FREE_UPGRADE', 0, 'Free reform and vanguard'),
('Outriders', 'Sharpshooter', 'COMMAND_GROUP', 10, 'Champion'),
('Outriders', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Outriders', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Demigryph Knights', 'Hand Weapon', 'FREE_UPGRADE', 0, 'Hand weapon'),
('Demigryph Knights', 'Lance', 'FREE_UPGRADE', 0, '+2 Strength on the charge'),
('Demigryph Knights', 'Full Plate Armour', 'FREE_UPGRADE', 0, 'Full plate armour, 4+ armour save'),
('Demigryph Knights', 'Shield', 'FREE_UPGRADE', 0, '+1 armour save'),
('Demigryph Knights', 'Demigryph', 'FREE_UPGRADE', 0, 'Monstrous cavalry mount'),
('Demigryph Knights', 'Cavalry Halberd', 'UNIT_EQUIPMENT', 0, 'Replaces lance, +1 Strength in every round'),
('Demigryph Knights', 'Preceptor', 'COMMAND_GROUP', 10, 'Champion'),
('Demigryph Knights', 'Standard Bearer', 'COMMAND_GROUP', 10, 'Standard Bearer'),
('Demigryph Knights', 'Musician', 'COMMAND_GROUP', 5, 'Musician'),

('Flagellants', 'Flail', 'FREE_UPGRADE', 0, '+2 Strength in the first round of combat'),
('Flagellants', 'Unbreakable', 'FREE_UPGRADE', 0, 'Never flees'),
('Flagellants', 'Prophet of Doom', 'COMMAND_GROUP', 10, 'Champion'),

('Great Cannon', 'Crew', 'FREE_UPGRADE', 0, 'Three Empire gunners'),
('Mortar', 'Crew', 'FREE_UPGRADE', 0, 'Three Empire gunners'),
('Helblaster Volley Gun', 'Crew', 'FREE_UPGRADE', 0, 'Three Empire gunners'),
('Helstorm Rocket Battery', 'Crew', 'FREE_UPGRADE', 0, 'Three Empire gunners'),
('Steam Tank', 'Steam Cannon', 'FREE_UPGRADE', 0, 'Main gun of the tank'),
('Steam Tank', 'Engineer Commander', 'FREE_UPGRADE', 0, 'Engineer with a steam gun'),
('Steam Tank', 'Unbreakable', 'FREE_UPGRADE', 0, 'Never flees'),
('Luminark of Hysh', 'Arcane Engine', 'FREE_UPGRADE', 0, 'Bound spells of the Lore of Light'),
('Celestial Hurricanum', 'Arcane Engine', 'FREE_UPGRADE', 0, 'Bound spells of the Lore of Heavens');

INSERT INTO staging_unit_upgrade
SELECT unit.name, item.name, item.upgrade_type, item.points, item.description
FROM staging_unit unit
CROSS JOIN (VALUES
    ('Runefang', 'MAGIC_WEAPON', 85, 'Blade of the Elector Counts, wounds automatically and ignores armour'),
    ('Armour of Meteoric Iron', 'MAGIC_ARMOUR', 50, '1+ armour save that cannot be improved'),
    ('Silver Horn', 'MAGIC_ITEM', 10, 'The bearer causes Fear'),
    ('Icon of Magnus', 'MAGIC_ITEM', 40, 'Gives Magic Resistance to the bearer and his unit'),
    ('Van Horstmann''s Speculum', 'MAGIC_ITEM', 35, 'Swaps characteristics with an enemy in a challenge'),
    ('Rod of Power', 'MAGIC_ITEM', 35, 'Stores power dice for later turns')
) AS item(name, upgrade_type, points, description)
WHERE unit.unit_type IN ('LORDS', 'HERO');

INSERT INTO staging_unit_upgrade
SELECT unit_name, 'Griffon Banner', 'MAGIC_BANNER', 60, 'Unit counts an extra rank and is harder to break'
FROM (VALUES ('Captain of the Empire'), ('Greatswords'), ('Knights of the Empire'), ('Demigryph Knights')) AS units(unit_name);

DO
$$
    DECLARE
        staged   RECORD;
        stats_id BIGINT;
    BEGIN
        IF EXISTS (SELECT 1
                   FROM staging_unit_upgrade su
                   WHERE NOT EXISTS (SELECT 1 FROM staging_unit s WHERE s.name = su.unit_name)) THEN
            RAISE EXCEPTION 'Empire upgrade refers to an unknown unit';
        END IF;

        FOR staged IN SELECT * FROM staging_unit
            LOOP
                INSERT INTO unit_stats (m, ws, bs, s, t, w, i, a, ld, basic_save, ward_save)
                VALUES (staged.m, staged.ws, staged.bs, staged.s, staged.t, staged.w, staged.i, staged.a, staged.ld,
                        staged.basic_save, staged.ward_save)
                RETURNING id INTO stats_id;

                INSERT INTO unit (name, points_cost_per_unit, min_quantity, unit_type, unit_faction, unit_stats_id)
                VALUES (staged.name, staged.points, staged.min_quantity, staged.unit_type, 'EMPIRE', stats_id);
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
JOIN unit u ON u.name = su.unit_name AND u.unit_faction = 'EMPIRE'
JOIN upgrade up ON up.name = su.upgrade_name AND up.upgrade_type = su.upgrade_type;

DROP TABLE staging_unit_upgrade;
DROP TABLE staging_unit;
