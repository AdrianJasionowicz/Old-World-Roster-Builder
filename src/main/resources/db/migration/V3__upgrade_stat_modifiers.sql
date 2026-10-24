-- Stat effects of upgrades (replaces the name-based switch in StatsCalculatorService).
-- Matches by name, like the old switch did,
-- so an upgrade name present under several types gets the effect for each of them.
--
-- Operations (applied in phases: SET -> ADD/IMPROVE -> BEST_OF):
--   SET      stat = value                  (Fellblade: S = 10)
--   ADD      stat + value                  (Halberd: S + 1)
--   IMPROVE  save better by value          (Light Armour: none -> 6+)
--   BEST_OF  save at least value+          (Talisman of Protection: ward 6+ unless better)

INSERT INTO upgrade_stat_modifier (upgrade_id, stat, operation, modifier_value)
SELECT u.id, m.stat, m.operation, m.modifier_value
FROM upgrade u
JOIN (VALUES
    -- Armour
    ('Light Armour',             'ARMOUR_SAVE', 'IMPROVE', 1),
    ('Heavy Armour',             'ARMOUR_SAVE', 'IMPROVE', 2),
    ('Armour of Destiny',        'ARMOUR_SAVE', 'SET',     5),
    ('Armour of Destiny',        'WARD_SAVE',   'BEST_OF', 4),
    ('Armour of Silvered Steel', 'ARMOUR_SAVE', 'SET',     2),
    ('Armour of Fortune',        'ARMOUR_SAVE', 'SET',     5),
    ('Armour of Fortune',        'WARD_SAVE',   'BEST_OF', 5),
    ('Glittering Scales',        'ARMOUR_SAVE', 'SET',     6),
    ('Gamblers Armour',          'ARMOUR_SAVE', 'SET',     5),
    ('Gamblers Armour',          'WARD_SAVE',   'BEST_OF', 6),
    ('Warpstone armour',         'ARMOUR_SAVE', 'SET',     4),
    ('Worlds Edge Armour',       'ARMOUR_SAVE', 'SET',     4),
    ('Armour Save(4+)',          'ARMOUR_SAVE', 'SET',     4),

    -- Shields and helms
    ('Shield',                   'ARMOUR_SAVE', 'IMPROVE', 1),
    ('Shield',                   'WARD_SAVE',   'BEST_OF', 6),
    ('Shields',                  'ARMOUR_SAVE', 'IMPROVE', 1),
    ('Shields',                  'WARD_SAVE',   'BEST_OF', 6),
    ('Charmed Shield',           'ARMOUR_SAVE', 'IMPROVE', 1),
    ('Charmed Shield',           'WARD_SAVE',   'BEST_OF', 6),
    ('Shield of Ptolos',         'ARMOUR_SAVE', 'IMPROVE', 1),
    ('Shield of Ptolos',         'WARD_SAVE',   'BEST_OF', 6),
    ('Shield of Distraction',    'ARMOUR_SAVE', 'IMPROVE', 1),
    ('Shield of Distraction',    'WARD_SAVE',   'BEST_OF', 6),
    ('Enchanted Shield',         'ARMOUR_SAVE', 'IMPROVE', 2),
    ('Enchanted Shield',         'WARD_SAVE',   'BEST_OF', 6),
    ('Spellshield',              'ARMOUR_SAVE', 'IMPROVE', 1),
    ('Tricksters helm',          'ARMOUR_SAVE', 'IMPROVE', 1),
    ('Helm of Discord',          'ARMOUR_SAVE', 'IMPROVE', 1),
    ('Dragonhelm',               'ARMOUR_SAVE', 'IMPROVE', 1),

    -- Talismans and ward saves
    ('Talisman of Preservation', 'WARD_SAVE',   'BEST_OF', 4),
    ('Talisman of Endurance',    'WARD_SAVE',   'BEST_OF', 5),
    ('Ward save (5+)',           'WARD_SAVE',   'BEST_OF', 5),
    ('Foul Pendant',             'WARD_SAVE',   'BEST_OF', 5),
    ('Talisman of Protection',   'WARD_SAVE',   'BEST_OF', 6),
    ('Screaming Bell',           'WARD_SAVE',   'BEST_OF', 4),
    ('Dodge (4+)',               'WARD_SAVE',   'BEST_OF', 4),
    ('Dodge (5+)',               'WARD_SAVE',   'BEST_OF', 5),
    ('Dodge (6+)',               'WARD_SAVE',   'BEST_OF', 6),

    -- Mounts
    ('Rat Ogre Bonebreaker',     'ARMOUR_SAVE', 'IMPROVE', 1),
    ('Great Pox Rat',            'ARMOUR_SAVE', 'IMPROVE', 1),
    ('War-litter',               'ARMOUR_SAVE', 'IMPROVE', 1),

    -- Weapons
    ('Fellblade',                 'S',  'SET', 10),
    ('Warlock-Augumented Weapon', 'S',  'ADD', 1),
    ('Warlock-Augumented Weapon', 'A',  'ADD', 1),
    ('Blade of Corruption',       'S',  'ADD', 1),
    ('Halberd',                   'S',  'ADD', 1),
    ('Dwarfbane',                 'S',  'ADD', 1),
    ('Sword of Might',            'S',  'ADD', 1),
    ('Warlock Optics',            'BS', 'ADD', 1),
    ('Great weapon',              'S',  'ADD', 2),
    ('Ogre Blade',                'S',  'ADD', 2),
    ('Giant Blade',               'S',  'ADD', 3),
    ('Sword of Bloodshed',        'A',  'ADD', 3),
    ('Sword of Strife',           'A',  'ADD', 2),
    ('Sword of Battle',           'A',  'ADD', 1),
    ('Fencers Blades',            'WS', 'SET', 10),
    ('Gold Sigil Sword',          'I',  'SET', 10),

    -- Skills
    ('Frenzy',                    'A',  'ADD', 1)
) AS m(name, stat, operation, modifier_value) ON u.name = m.name;
