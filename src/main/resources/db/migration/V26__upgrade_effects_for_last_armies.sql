INSERT INTO upgrade_stat_modifier (upgrade_id, stat, operation, modifier_value)
SELECT u.id, m.stat, m.operation, m.modifier_value
FROM upgrade u
JOIN (VALUES
    ('Light Armour', 'ARMOUR_SAVE', 'IMPROVE', 1),
    ('Heavy Armour', 'ARMOUR_SAVE', 'IMPROVE', 2),
    ('Chaos Armour', 'ARMOUR_SAVE', 'SET', 4),
    ('Scaly Skin (6+)', 'ARMOUR_SAVE', 'SET', 6),
    ('Shield', 'ARMOUR_SAVE', 'IMPROVE', 1),
    ('Armour of Contempt', 'ARMOUR_SAVE', 'BEST_OF', 5),
    ('Mournfang', 'ARMOUR_SAVE', 'IMPROVE', 1),
    ('Steed of Slaanesh', 'ARMOUR_SAVE', 'IMPROVE', 1),
    ('Disc of Tzeentch', 'ARMOUR_SAVE', 'IMPROVE', 1),
    ('Juggernaut of Khorne', 'ARMOUR_SAVE', 'IMPROVE', 1),
    ('Daemonic', 'WARD_SAVE', 'BEST_OF', 5),
    ('Great weapon', 'S', 'ADD', 2),
    ('Hellforged Blade', 'S', 'ADD', 1),
    ('Additional hand weapon', 'A', 'ADD', 1),
    ('Frenzy', 'A', 'ADD', 1)
) AS m(name, stat, operation, modifier_value) ON u.name = m.name
WHERE NOT EXISTS (SELECT 1 FROM upgrade_stat_modifier existing WHERE existing.upgrade_id = u.id);
