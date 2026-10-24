CREATE TEMPORARY TABLE common_item
(
    name         VARCHAR(255),
    upgrade_type VARCHAR(255),
    points       DOUBLE PRECISION,
    description  VARCHAR(1000),
    holders      VARCHAR(255)
);

INSERT INTO common_item VALUES
('Giant Blade', 'MAGIC_WEAPON', 60, '+3 Strength', 'CHARACTER'),
('Sword of Bloodshed', 'MAGIC_WEAPON', 60, '+3 Attacks', 'CHARACTER'),
('Obsidian Blade', 'MAGIC_WEAPON', 50, 'Ignores armour saves', 'CHARACTER'),
('Ogre Blade', 'MAGIC_WEAPON', 40, '+2 Strength', 'CHARACTER'),
('Sword of Strife', 'MAGIC_WEAPON', 40, '+2 Attacks', 'CHARACTER'),
('Fencer''s Blades', 'MAGIC_WEAPON', 35, 'Weapon Skill 10, two weapons', 'CHARACTER'),
('Sword of Anti-Heroes', 'MAGIC_WEAPON', 30, '+1 Strength and Attack for every enemy character in base contact', 'CHARACTER'),
('Spellthieving Sword', 'MAGIC_WEAPON', 25, 'Wounded wizards lose spells', 'CHARACTER'),
('Sword of Swift Slaying', 'MAGIC_WEAPON', 25, 'Always strikes first', 'CHARACTER'),
('Sword of Battle', 'MAGIC_WEAPON', 20, '+1 Attack', 'CHARACTER'),
('Berserker Sword', 'MAGIC_WEAPON', 20, 'Bearer is subject to Frenzy', 'CHARACTER'),
('Sword of Might', 'MAGIC_WEAPON', 20, '+1 Strength', 'CHARACTER'),
('Gold Sigil Sword', 'MAGIC_WEAPON', 15, 'Initiative 10', 'CHARACTER'),
('Sword of Striking', 'MAGIC_WEAPON', 15, '+1 to hit', 'CHARACTER'),
('Biting Blade', 'MAGIC_WEAPON', 10, '-1 armour save modifier', 'CHARACTER'),
('Relic Sword', 'MAGIC_WEAPON', 10, 'Always hits on 4+', 'CHARACTER'),
('Shrieking Blade', 'MAGIC_WEAPON', 10, 'Bearer causes Fear', 'CHARACTER'),
('Tormentor Sword', 'MAGIC_WEAPON', 5, 'Wounded enemies count as flammable', 'CHARACTER'),
('Warrior Bane', 'MAGIC_WEAPON', 5, 'Wounded enemies lose an Attack', 'CHARACTER'),
('Armour of Destiny', 'MAGIC_ARMOUR', 50, 'Heavy armour with a 4+ ward save', 'CHARACTER'),
('Trickster''s Helm', 'MAGIC_ARMOUR', 50, 'Enemies must re-roll successful hits', 'CHARACTER'),
('Armour of Silvered Steel', 'MAGIC_ARMOUR', 45, '2+ armour save', 'CHARACTER'),
('Armour of Fortune', 'MAGIC_ARMOUR', 35, 'Heavy armour with a 5+ ward save', 'CHARACTER'),
('Helm of Discord', 'MAGIC_ARMOUR', 30, '+1 armour save, may confuse an enemy in a challenge', 'CHARACTER'),
('Glittering Scales', 'MAGIC_ARMOUR', 25, 'Light armour, enemies have -1 to hit', 'CHARACTER'),
('Shield of Ptolos', 'MAGIC_ARMOUR', 25, 'Shield, 1+ armour save against shooting', 'CHARACTER'),
('Spellshield', 'MAGIC_ARMOUR', 20, 'Shield with Magic Resistance', 'CHARACTER'),
('Gambler''s Armour', 'MAGIC_ARMOUR', 20, 'Heavy armour with a 6+ ward save', 'CHARACTER'),
('Dragonhelm', 'MAGIC_ARMOUR', 10, '+1 armour save, 2+ ward save against flaming attacks', 'CHARACTER'),
('Enchanted Shield', 'MAGIC_ARMOUR', 5, 'Shield, +2 armour save', 'CHARACTER'),
('Charmed Shield', 'MAGIC_ARMOUR', 5, 'Shield, ignores the first hit', 'CHARACTER'),
('Talisman of Preservation', 'MAGIC_ITEM', 45, '4+ ward save', 'CHARACTER'),
('Obsidian Lodestone', 'MAGIC_ITEM', 45, 'Magic Resistance (3)', 'CHARACTER'),
('Talisman of Endurance', 'MAGIC_ITEM', 30, '5+ ward save', 'CHARACTER'),
('Obsidian Amulet', 'MAGIC_ITEM', 30, 'Magic Resistance (2)', 'CHARACTER'),
('Dawnstone', 'MAGIC_ITEM', 25, 'Re-roll failed armour saves', 'CHARACTER'),
('Opal Amulet', 'MAGIC_ITEM', 15, '4+ ward save against the first wound', 'CHARACTER'),
('Obsidian Trinket', 'MAGIC_ITEM', 15, 'Magic Resistance (1)', 'CHARACTER'),
('Talisman of Protection', 'MAGIC_ITEM', 15, '6+ ward save', 'CHARACTER'),
('Seed of Rebirth', 'MAGIC_ITEM', 10, '6+ regeneration', 'CHARACTER'),
('Dragonbane Gem', 'MAGIC_ITEM', 5, '2+ ward save against flaming attacks', 'CHARACTER'),
('Pidgeon Plucker Pendant', 'MAGIC_ITEM', 5, 'Enemy fliers are hit more easily', 'CHARACTER'),
('Luckstone', 'MAGIC_ITEM', 5, 'Re-roll one failed armour save', 'CHARACTER'),
('Wizarding Hat', 'MAGIC_ITEM', 100, 'Bearer becomes a Level 1 Wizard', 'CHARACTER'),
('Fozzrik''s Folding Fortress', 'MAGIC_ITEM', 100, 'Unfolds into a tower on the battlefield', 'CHARACTER'),
('Arabyan Carpet', 'MAGIC_ITEM', 50, 'Bearer can fly', 'CHARACTER'),
('Crown of Command', 'MAGIC_ITEM', 35, 'Bearer and his unit become Stubborn', 'CHARACTER'),
('Healing Potion', 'MAGIC_ITEM', 35, 'One use: heals wounds', 'CHARACTER'),
('Featherfoe Torc', 'MAGIC_ITEM', 35, 'Enemy fliers must re-roll hits', 'CHARACTER'),
('Ruby Ring of Ruin', 'MAGIC_ITEM', 25, 'Bound spell, fireball', 'CHARACTER'),
('The Terrifying Mask of EEE!', 'MAGIC_ITEM', 25, 'Bearer causes Terror', 'CHARACTER'),
('Potion of Strength', 'MAGIC_ITEM', 20, 'One use: +3 Strength for a turn', 'CHARACTER'),
('Potion of Toughness', 'MAGIC_ITEM', 20, 'One use: +3 Toughness for a turn', 'CHARACTER'),
('The Other Trickster''s Shard', 'MAGIC_ITEM', 15, 'Enemies must re-roll successful ward saves', 'CHARACTER'),
('Ironcurse Icon', 'MAGIC_ITEM', 5, '6+ ward save against war machines', 'CHARACTER'),
('Potion of Foolhardiness', 'MAGIC_ITEM', 5, 'One use: Immune to Psychology', 'CHARACTER'),
('Potion of Speed', 'MAGIC_ITEM', 5, 'One use: +3 Initiative for a turn', 'CHARACTER'),
('Book of Ashur', 'MAGIC_ITEM', 70, '+1 to cast and one extra spell', 'WIZARD'),
('Feedback Scroll', 'MAGIC_ITEM', 50, 'One use: enemy wizard takes hits for each power die', 'WIZARD'),
('Scroll of Leeching', 'MAGIC_ITEM', 50, 'One use: gains power dice from a dispel', 'WIZARD'),
('Sivejir''s Hex Scroll', 'MAGIC_ITEM', 50, 'One use: turns an enemy wizard into a frog', 'WIZARD'),
('Power Scroll', 'MAGIC_ITEM', 35, 'One use: the spell is cast with irresistible force', 'WIZARD'),
('Wand of Jet', 'MAGIC_ITEM', 35, 'Adds a die to casting attempts', 'WIZARD'),
('Forbidden Rod', 'MAGIC_ITEM', 35, 'One use: extra power dice at a price', 'WIZARD'),
('Earthing Rod', 'MAGIC_ITEM', 25, 'Re-roll on the miscast table', 'WIZARD'),
('Dispel Scroll', 'MAGIC_ITEM', 25, 'One use: dispels a spell automatically', 'WIZARD'),
('Power Stone', 'MAGIC_ITEM', 20, 'One use: two extra power dice', 'WIZARD'),
('Sceptre of Stability', 'MAGIC_ITEM', 15, 'Adds a die to dispel attempts', 'WIZARD'),
('Channelling Staff', 'MAGIC_ITEM', 15, '+1 to channel', 'WIZARD'),
('Scroll of Shielding', 'MAGIC_ITEM', 15, 'One use: gives a unit a ward save', 'WIZARD'),
('Rampager''s Standard', 'MAGIC_BANNER', 55, 'Re-roll the charge distance', 'BANNER'),
('Wailing Banner', 'MAGIC_BANNER', 50, 'The unit causes Terror', 'BANNER'),
('Ranger''s Standard', 'MAGIC_BANNER', 50, 'The unit has Strider', 'BANNER'),
('Razor Standard', 'MAGIC_BANNER', 45, 'The unit has Armour Piercing', 'BANNER'),
('War Banner', 'MAGIC_BANNER', 35, '+1 combat resolution', 'BANNER'),
('Banner of Swiftness', 'MAGIC_BANNER', 15, '+1 Movement', 'BANNER'),
('Lichebone Pennant', 'MAGIC_BANNER', 15, 'Magic Resistance (1)', 'BANNER'),
('Banner of Eternal Flame', 'MAGIC_BANNER', 10, 'The unit has Flaming Attacks', 'BANNER'),
('Gleaming Pennant', 'MAGIC_BANNER', 5, 'One use: re-roll a failed Leadership test', 'BANNER'),
('Scarecrow Banner', 'MAGIC_BANNER', 5, 'The unit causes Fear in fliers', 'BANNER');

INSERT INTO upgrade (name, upgrade_type, description)
SELECT name, upgrade_type, description
FROM common_item
ON CONFLICT (name, upgrade_type) DO NOTHING;

CREATE TEMPORARY TABLE item_holder AS
SELECT u.id AS unit_id, 'CHARACTER' AS holders
FROM unit u
WHERE u.unit_faction IN ('BEASTMEN', 'OGRE_KINGDOMS')
  AND u.unit_type IN ('LORDS', 'HERO')
UNION
SELECT u.id, 'WIZARD'
FROM unit u
JOIN unit_upgrade uu ON uu.unit_id = u.id
JOIN upgrade up ON up.id = uu.upgrade_id
WHERE u.unit_faction IN ('BEASTMEN', 'OGRE_KINGDOMS')
  AND up.name LIKE 'Level _ Wizard'
UNION
SELECT u.id, 'BANNER'
FROM unit u
JOIN unit_upgrade uu ON uu.unit_id = u.id
JOIN upgrade up ON up.id = uu.upgrade_id
WHERE u.unit_faction IN ('BEASTMEN', 'OGRE_KINGDOMS')
  AND up.upgrade_type IN ('MAGIC_BANNER', 'BSB');

INSERT INTO unit_upgrade (unit_id, upgrade_id, points_cost)
SELECT holder.unit_id, up.id, item.points
FROM common_item item
JOIN item_holder holder ON holder.holders = item.holders
JOIN upgrade up ON up.name = item.name AND up.upgrade_type = item.upgrade_type
ON CONFLICT (unit_id, upgrade_id) DO NOTHING;

DROP TABLE item_holder;
DROP TABLE common_item;
