INSERT INTO upgrade_profile (upgrade_id, position, name, m, ws, bs, s, t, w, i, a, ld, basic_save, ward_save)
SELECT u.id, p.position, p.name, p.m, p.ws, p.bs, p.s, p.t, p.w, p.i, p.a, p.ld, p.basic_save, p.ward_save
FROM (VALUES
    ('Great Pox Rat', 1, 'Great Pox Rat', '6', '3', '3', '4', '4', '1', '5', '2', '2', NULL, NULL),
    ('War-litter', 1, 'War-litter', '5', '4', NULL, '4', NULL, NULL, '5', '4', NULL, NULL, NULL)
) AS p (upgrade_name, position, name, m, ws, bs, s, t, w, i, a, ld, basic_save, ward_save)
JOIN upgrade u ON u.name = p.upgrade_name AND u.upgrade_type = 'MOUNT';
