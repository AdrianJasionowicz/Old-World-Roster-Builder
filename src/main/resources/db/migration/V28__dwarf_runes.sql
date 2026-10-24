ALTER TABLE upgrade
    ADD COLUMN rune VARCHAR(255);

CREATE TEMPORARY TABLE new_rune
(
    name         VARCHAR(255),
    upgrade_type VARCHAR(255),
    points       DOUBLE PRECISION,
    description  VARCHAR(1000)
);

INSERT INTO new_rune VALUES
('Master Rune of Snorri Spangelhelm', 'MAGIC_WEAPON', 50, 'All close combat attacks hit automatically'),
('Master Rune of Dragon Slaying', 'MAGIC_WEAPON', 50, 'Wounds on 2+ against monsters, multiple wounds'),
('Master Rune of Alaric the Mad', 'MAGIC_WEAPON', 30, 'Ignores armour saves'),
('Master Rune of Breaking', 'MAGIC_WEAPON', 25, 'Destroys an enemy magic weapon on a hit'),
('Master Rune of Flight', 'MAGIC_WEAPON', 25, 'The weapon can be thrown and returns'),
('Rune of Parrying', 'MAGIC_WEAPON', 25, 'Parry save in close combat'),
('Rune of Grudges', 'MAGIC_WEAPON', 15, 'Re-roll failed hits against the chosen enemy'),
('Rune of Speed', 'MAGIC_WEAPON', 5, '+1 Initiative'),
('Rune of Fire', 'MAGIC_WEAPON', 5, 'Flaming Attacks'),
('Master Rune of Adamant', 'MAGIC_ARMOUR', 35, '+1 Toughness'),
('Master Rune of Steel', 'MAGIC_ARMOUR', 25, 'Enemy multiple wounds count as one'),
('Rune of Resistance', 'MAGIC_ARMOUR', 25, 'Enemies must re-roll successful wounds'),
('Rune of Shielding', 'MAGIC_ARMOUR', 25, 'Ward save against shooting'),
('Rune of Preservation', 'MAGIC_ARMOUR', 15, 'Immune to Killing Blow and Impact Hits'),
('Master Rune of Calm', 'TALISMAN', 40, 'Enemy wizards suffer -1 to cast'),
('Rune of Spelleating', 'TALISMAN', 50, 'One use: dispels a spell and destroys it'),
('Rune of Passage', 'TALISMAN', 20, 'The unit moves through terrain freely'),
('Rune of the Furnace', 'TALISMAN', 5, 'Immune to Flaming Attacks'),
('Master Rune of Stromni Redbeard', 'MAGIC_BANNER', 100, 'Friendly units nearby get +1 rank bonus'),
('Master Rune of Groth One-Hand', 'MAGIC_BANNER', 100, 'Units nearby may move again'),
('Master Rune of Grungni', 'MAGIC_BANNER', 50, 'Ward save against shooting for the unit'),
('Rune of Guarding', 'MAGIC_BANNER', 35, 'Ward save for the unit'),
('Rune of Slowness', 'MAGIC_BANNER', 35, 'Enemies charging the unit roll lower'),
('Rune of Stoicism', 'MAGIC_BANNER', 35, 'Enemy outnumbering is ignored');

INSERT INTO upgrade (name, upgrade_type, description)
SELECT name, upgrade_type, description
FROM new_rune
ON CONFLICT (name, upgrade_type) DO NOTHING;

INSERT INTO unit_upgrade (unit_id, upgrade_id, points_cost)
SELECT u.id, up.id, r.points
FROM new_rune r
JOIN upgrade up ON up.name = r.name AND up.upgrade_type = r.upgrade_type
JOIN unit u ON u.unit_faction = 'DWARFS'
WHERE (r.upgrade_type <> 'MAGIC_BANNER' AND u.name IN ('Dwarf Lord', 'Runelord', 'Thane', 'Runesmith', 'Master Engineer'))
   OR (r.upgrade_type = 'MAGIC_BANNER' AND u.name IN ('Thane', 'Hammerers', 'Ironbreakers', 'Longbeards', 'Dwarf Warriors'))
ON CONFLICT (unit_id, upgrade_id) DO NOTHING;

DROP TABLE new_rune;

UPDATE upgrade up
SET rune           = CASE WHEN up.name LIKE 'Master Rune of %' THEN 'MASTER_RUNE' ELSE 'RUNE' END,
    unique_in_army = up.name LIKE 'Master Rune of %'
WHERE (up.name LIKE 'Rune of %' OR up.name LIKE 'Master Rune of %')
  AND EXISTS (SELECT 1
              FROM unit_upgrade uu
                       JOIN unit u ON u.id = uu.unit_id
              WHERE uu.upgrade_id = up.id
                AND u.unit_faction = 'DWARFS');

UPDATE unit
SET magic_items_points_limit = CASE WHEN unit_type = 'LORDS' THEN 125 ELSE 75 END
WHERE unit_faction = 'DWARFS'
  AND unit_type IN ('LORDS', 'HERO');

UPDATE unit
SET magic_banner_points_limit = 50
WHERE unit_faction = 'DWARFS'
  AND name IN ('Hammerers', 'Ironbreakers', 'Longbeards');

INSERT INTO upgrade_stat_modifier (upgrade_id, stat, operation, modifier_value)
SELECT u.id, m.stat, m.operation, m.modifier_value
FROM upgrade u
JOIN (VALUES
    ('Master Rune of Adamant', 'T', 'ADD', 1),
    ('Rune of Speed', 'I', 'ADD', 1)
) AS m(name, stat, operation, modifier_value) ON u.name = m.name
WHERE NOT EXISTS (SELECT 1 FROM upgrade_stat_modifier existing WHERE existing.upgrade_id = u.id);
