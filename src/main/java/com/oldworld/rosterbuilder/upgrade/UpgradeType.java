package com.oldworld.rosterbuilder.upgrade;

public enum UpgradeType {
    COMMAND_GROUP,
    WEAPON_TEAM,
    MAGIC_BANNER,
    MAGIC_WEAPON,
    MAGIC_ARMOUR,
    TALISMAN,
    ENCHANTED_ITEM,
    ARCANE_ITEM,
    MAGIC_ITEM,
    UNIT_EQUIPMENT,
    BSB,
    FREE_UPGRADE,
    THE_SCAVENGE_PILE,
    MOUNT,
    PICK_UP_GENERAL;

    public boolean isMagicItem() {
        return this == MAGIC_ITEM || (isSingleItemSlot() && this != MAGIC_BANNER);
    }

    public boolean isSingleItemSlot() {
        return switch (this) {
            case MAGIC_WEAPON, MAGIC_ARMOUR, TALISMAN, ENCHANTED_ITEM, ARCANE_ITEM, MAGIC_BANNER -> true;
            default -> false;
        };
    }

    public boolean isBoughtPerModel() {
        return this == UNIT_EQUIPMENT;
    }

    public String displayName() {
        return name().toLowerCase().replace('_', ' ');
    }
}
