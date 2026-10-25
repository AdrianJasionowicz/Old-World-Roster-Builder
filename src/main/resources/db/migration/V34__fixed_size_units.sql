ALTER TABLE unit ADD COLUMN fixed_size BOOLEAN NOT NULL DEFAULT FALSE;

UPDATE unit u
SET fixed_size = TRUE
FROM (VALUES
    ('CHAOS_DWARFS', 'Deathshrieker Rocket Launcher'),
    ('CHAOS_DWARFS', 'Magma Cannon'),
    ('CHAOS_DWARFS', 'Dreadquake Mortar'),
    ('DWARFS', 'Cannon'),
    ('DWARFS', 'Grudge Thrower'),
    ('DWARFS', 'Flame Cannon'),
    ('DWARFS', 'Organ Gun'),
    ('EMPIRE', 'Great Cannon'),
    ('EMPIRE', 'Mortar'),
    ('EMPIRE', 'Helblaster Volley Gun'),
    ('EMPIRE', 'Helstorm Rocket Battery'),
    ('ORCS_AND_GOBLINS', 'Goblin Rock Lobber'),
    ('ORCS_AND_GOBLINS', 'Goblin Spear Chukka'),
    ('ORCS_AND_GOBLINS', 'Doom Diver Catapult'),
    ('BRETONIA', 'Field Trebuchet'),
    ('CHAOS_WARRIORS', 'Hellcannon'),
    ('DAEMONS_OF_CHAOS', 'Skull Cannon'),
    ('DARK_ELVES', 'Reaper Bolt Thrower'),
    ('HIGH_ELVES', 'Repeater Bolt Thrower'),
    ('SKAVEN', 'Plague Claw Catapult'),
    ('SKAVEN', 'Warp Lightning Cannon'),
    ('TOMB_KINGS', 'Screaming Skull Catapult')
) AS w (faction, name)
WHERE u.unit_faction = w.faction AND u.name = w.name;

DO $$
BEGIN
    IF (SELECT count(*) FROM unit WHERE fixed_size) <> 22 THEN
        RAISE EXCEPTION 'Expected 22 war machines in the catalog';
    END IF;
END $$;

UPDATE unit
SET fixed_size = TRUE
WHERE unit_type = 'RARE'
  AND min_quantity = 1
  AND name NOT IN ('Chaos Spawn', 'Cairn Wraiths', 'Spirit Hosts', 'Razordon Hunting Pack', 'Salamander Hunting Pack');
