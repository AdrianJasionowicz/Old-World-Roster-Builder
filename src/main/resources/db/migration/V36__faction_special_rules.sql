ALTER TABLE special_rule ALTER COLUMN description TYPE TEXT;

CREATE TEMPORARY TABLE special_rule_keep AS
SELECT id, min(id) OVER (PARTITION BY name) AS keep_id
FROM special_rule;

INSERT INTO unit_special_rule (unit_id, special_rule_id)
SELECT DISTINCT usr.unit_id, k.keep_id
FROM unit_special_rule usr
JOIN special_rule_keep k ON k.id = usr.special_rule_id
WHERE k.id <> k.keep_id
ON CONFLICT DO NOTHING;

DELETE FROM unit_special_rule usr
USING special_rule_keep k
WHERE usr.special_rule_id = k.id AND k.id <> k.keep_id;

DELETE FROM special_rule s
USING special_rule_keep k
WHERE s.id = k.id AND k.id <> k.keep_id;

DROP TABLE special_rule_keep;

ALTER TABLE special_rule ADD CONSTRAINT uk_special_rule_name UNIQUE (name);

CREATE TABLE faction_special_rule (
    faction         VARCHAR(255) NOT NULL,
    special_rule_id BIGINT       NOT NULL REFERENCES special_rule (id) ON DELETE CASCADE,
    position        INTEGER      NOT NULL,
    PRIMARY KEY (faction, special_rule_id),
    UNIQUE (faction, position)
);

CREATE TEMPORARY TABLE staging_rule (
    name        VARCHAR(255),
    description TEXT
);

INSERT INTO staging_rule VALUES
    ('Strength in Numbers', 'Skaven units add their rank bonus to their Leadership.'),
    ('Verminous Valour', 'A Skaven character in a unit can let a nearby rank-and-file model take the hits meant for him in close combat.'),
    ('Scurry Away', 'Skaven roll an extra dice for flee and pursuit distances and keep the best ones.'),
    ('Detachments', 'Empire regiments can take smaller detachments that support them with counter-charges and supporting fire.'),
    ('Ancestral Grudge', 'Dwarfs hate Orcs and Goblins and may re-roll missed attacks against them in the first round of close combat.'),
    ('Relentless', 'The unit can march even when enemies are close by.'),
    ('Resolute', 'The unit gets +1 Strength in the turn it charges.'),
    ('Shieldwall', 'A unit with shields that does not move holds firm better against charges.'),
    ('Animosity', 'Greenskin units test at the start of each turn and may squabble, shout or charge on their own instead of following orders.'),
    ('Waaagh!', 'Once per game the general can call a Waaagh!, letting greenskin units move forward and fight with extra fury.'),
    ('Fear Elves', 'Goblins fear Elves unless they outnumber them.'),
    ('Undead', 'Undead units cause Fear, ignore psychology, cannot march away from the general and lose extra wounds instead of fleeing.'),
    ('The Curse', 'When the general dies and no Hierophant is left, Undead units start to crumble each turn.'),
    ('Always Strikes First', 'The unit strikes before enemies in close combat regardless of Initiative.'),
    ('Martial Prowess', 'The unit fights with one more rank than usual.'),
    ('Valour of Ages', 'High Elves may re-roll failed Leadership tests against Dark Elves.'),
    ('Eternal Hatred', 'Dark Elves hate every enemy and hate High Elves for the whole battle.'),
    ('Murderous Prowess', 'Dark Elves re-roll To Wound rolls of 1 in close combat.'),
    ('Cold-blooded', 'Leadership tests are made on 3D6, discarding the highest dice.'),
    ('Blessing of the Lady', 'If the army prays before battle, its knights get a ward save of 6+, or 5+ against attacks of Strength 5 or more.'),
    ('Lance Formation', 'Knights in a lance formation fight with more models and can use their full rank bonus.'),
    ('Peasants'' Duty', 'Peasants near knights may use their Leadership, but knights are not affected when peasants flee.'),
    ('Eye of the Gods', 'A Chaos character who wins a challenge rolls on the Eye of the Gods table for a gift or a curse.'),
    ('Marks of Chaos', 'Units and characters can take a Mark of Khorne, Nurgle, Slaanesh or Tzeentch, each giving its own bonus.'),
    ('Nehekharan Undead', 'Tomb Kings units cause Fear, ignore psychology and lose extra wounds instead of fleeing.'),
    ('The Hierophant', 'The Hierophant holds the army together; if he dies, units start to crumble.'),
    ('My Will Be Done', 'A Tomb King or Prince lets the unit he joins use his Weapon Skill and Leadership.'),
    ('Forest Stalker', 'Wood Elves move through forests without penalty and fight better inside them.'),
    ('Contempt', 'Chaos Dwarfs despise lesser races and are hard to break when fighting them.'),
    ('Primal Fury', 'Beastmen units with a character test before combat; if they pass they gain Frenzy for that round.'),
    ('Ambush', 'Beastmen units can start the battle hidden and enter from a table edge during the game.'),
    ('Ogre Charge', 'Charging Ogres make extra impact hits based on their ranks.'),
    ('Daemonic', 'Daemons cause Fear, ignore psychology, have a 5+ ward save and lose wounds through Daemonic Instability instead of fleeing.'),
    ('Reign of Chaos', 'At the start of each turn the Daemon player rolls on the Reign of Chaos table for a random effect.');

INSERT INTO special_rule (name, description)
SELECT name, description
FROM staging_rule
ON CONFLICT (name) DO UPDATE SET description = EXCLUDED.description;

CREATE TEMPORARY TABLE staging_faction_rule (
    faction   VARCHAR(255),
    position  INTEGER,
    rule_name VARCHAR(255)
);

INSERT INTO staging_faction_rule VALUES
    ('SKAVEN', 1, 'Strength in Numbers'),
    ('SKAVEN', 2, 'Verminous Valour'),
    ('SKAVEN', 3, 'Scurry Away'),
    ('EMPIRE', 1, 'Detachments'),
    ('DWARFS', 1, 'Ancestral Grudge'),
    ('DWARFS', 2, 'Relentless'),
    ('DWARFS', 3, 'Resolute'),
    ('DWARFS', 4, 'Shieldwall'),
    ('ORCS_AND_GOBLINS', 1, 'Animosity'),
    ('ORCS_AND_GOBLINS', 2, 'Waaagh!'),
    ('ORCS_AND_GOBLINS', 3, 'Fear Elves'),
    ('VAMPIRE_COUNTS', 1, 'Undead'),
    ('VAMPIRE_COUNTS', 2, 'The Curse'),
    ('HIGH_ELVES', 1, 'Always Strikes First'),
    ('HIGH_ELVES', 2, 'Martial Prowess'),
    ('HIGH_ELVES', 3, 'Valour of Ages'),
    ('DARK_ELVES', 1, 'Eternal Hatred'),
    ('DARK_ELVES', 2, 'Murderous Prowess'),
    ('LIZARDMEN', 1, 'Cold-blooded'),
    ('BRETONIA', 1, 'Blessing of the Lady'),
    ('BRETONIA', 2, 'Lance Formation'),
    ('BRETONIA', 3, 'Peasants'' Duty'),
    ('CHAOS_WARRIORS', 1, 'Eye of the Gods'),
    ('CHAOS_WARRIORS', 2, 'Marks of Chaos'),
    ('TOMB_KINGS', 1, 'Nehekharan Undead'),
    ('TOMB_KINGS', 2, 'The Hierophant'),
    ('TOMB_KINGS', 3, 'My Will Be Done'),
    ('WOOD_ELVES', 1, 'Forest Stalker'),
    ('WOOD_ELVES', 2, 'Martial Prowess'),
    ('CHAOS_DWARFS', 1, 'Contempt'),
    ('CHAOS_DWARFS', 2, 'Relentless'),
    ('BEASTMEN', 1, 'Primal Fury'),
    ('BEASTMEN', 2, 'Ambush'),
    ('OGRE_KINGDOMS', 1, 'Ogre Charge'),
    ('DAEMONS_OF_CHAOS', 1, 'Daemonic'),
    ('DAEMONS_OF_CHAOS', 2, 'Reign of Chaos');

DO $$
DECLARE
    missing TEXT;
BEGIN
    SELECT string_agg(f.rule_name, ', ') INTO missing
    FROM staging_faction_rule f
    WHERE NOT EXISTS (SELECT 1 FROM special_rule s WHERE s.name = f.rule_name);
    IF missing IS NOT NULL THEN
        RAISE EXCEPTION 'Unknown faction rules: %', missing;
    END IF;
END $$;

INSERT INTO faction_special_rule (faction, special_rule_id, position)
SELECT f.faction, s.id, f.position
FROM staging_faction_rule f
JOIN special_rule s ON s.name = f.rule_name;

DROP TABLE staging_faction_rule;
DROP TABLE staging_rule;
