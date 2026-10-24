UPDATE upgrade AS u
SET name = fix.correct_name
FROM (VALUES
    ('Bitting Blade', 'MAGIC_WEAPON', 'Biting Blade'),
    ('Berseker Sword', 'MAGIC_WEAPON', 'Berserker Sword'),
    ('Shrieking Sword', 'MAGIC_WEAPON', 'Shrieking Blade'),
    ('Warriors Bane', 'MAGIC_WEAPON', 'Warrior Bane'),
    ('Sword of Anti-heroes', 'MAGIC_WEAPON', 'Sword of Anti-Heroes'),
    ('Fencers Blades', 'MAGIC_WEAPON', 'Fencer''s Blades'),
    ('Tricksters helm', 'MAGIC_ARMOUR', 'Trickster''s Helm'),
    ('Gamblers Armour', 'MAGIC_ARMOUR', 'Gambler''s Armour'),
    ('Rampagers Standard', 'MAGIC_BANNER', 'Rampager''s Standard'),
    ('Rangers Standard', 'MAGIC_BANNER', 'Ranger''s Standard'),
    ('Potion of Strenght', 'MAGIC_ITEM', 'Potion of Strength'),
    ('Dispell Scroll', 'MAGIC_ITEM', 'Dispel Scroll'),
    ('Channeling staff', 'MAGIC_ITEM', 'Channelling Staff'),
    ('Staff of Sorcerry', 'MAGIC_ITEM', 'Staff of Sorcery'),
    ('Seed of rebirth', 'MAGIC_ITEM', 'Seed of Rebirth'),
    ('Healing potion', 'MAGIC_ITEM', 'Healing Potion'),
    ('Dragonbane gem', 'MAGIC_ITEM', 'Dragonbane Gem'),
    ('Ironcurse icon', 'MAGIC_ITEM', 'Ironcurse Icon'),
    ('Dawn Stone', 'MAGIC_ITEM', 'Dawnstone'),
    ('The other tricksters shard', 'MAGIC_ITEM', 'The Other Trickster''s Shard'),
    ('Pipes of piebald', 'MAGIC_ITEM', 'Pipes of Piebald'),
    ('Wizzard 4 lvl', 'FREE_UPGRADE', 'Level 4 Wizard'),
    ('Lvl 4 Wizzard', 'FREE_UPGRADE', 'Level 4 Wizard (Grey Seer)')
) AS fix(wrong_name, upgrade_type, correct_name)
WHERE u.name = fix.wrong_name
  AND u.upgrade_type = fix.upgrade_type
  AND NOT EXISTS (SELECT 1 FROM upgrade other WHERE other.name = fix.correct_name AND other.upgrade_type = fix.upgrade_type);
