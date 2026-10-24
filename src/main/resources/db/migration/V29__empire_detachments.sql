ALTER TABLE unit
    ADD COLUMN can_have_detachments BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN can_be_detachment    BOOLEAN NOT NULL DEFAULT FALSE;

ALTER TABLE army_unit
    ADD COLUMN parent_id BIGINT REFERENCES army_unit (id) ON DELETE SET NULL;

CREATE INDEX idx_army_unit_parent ON army_unit (parent_id);

UPDATE unit
SET can_have_detachments = name IN ('Halberdiers', 'Spearmen', 'Swordsmen'),
    can_be_detachment    = TRUE
WHERE unit_faction = 'EMPIRE'
  AND name IN ('Halberdiers', 'Spearmen', 'Swordsmen', 'Handgunners', 'Crossbowmen', 'Archers', 'Free Company Militia');
