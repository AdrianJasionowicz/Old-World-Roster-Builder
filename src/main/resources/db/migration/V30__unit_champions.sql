ALTER TABLE upgrade ADD COLUMN champion BOOLEAN NOT NULL DEFAULT FALSE;
ALTER TABLE upgrade_stat_modifier ADD COLUMN champion_only BOOLEAN NOT NULL DEFAULT FALSE;

UPDATE upgrade
SET champion = TRUE
WHERE upgrade_type = 'COMMAND_GROUP'
  AND (description = 'Champion' OR name = 'Nightleader');

INSERT INTO upgrade_stat_modifier (upgrade_id, stat, operation, modifier_value, champion_only)
SELECT id,
       CASE WHEN name IN ('Marksman', 'Hawkeye', 'Sharpshooter', 'Lord''s Bowman', 'Master of Arrows', 'Waywatcher Sentinel')
            THEN 'BS' ELSE 'A' END,
       'ADD', 1, TRUE
FROM upgrade
WHERE champion;
