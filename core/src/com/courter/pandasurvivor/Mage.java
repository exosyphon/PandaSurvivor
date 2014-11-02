package com.courter.pandasurvivor;

public class Mage extends Hero {
    private static final int SPELL_DMG_DEFAULT = 5;

    public Mage(float x, float y) {
        super(x, y, HeroClass.MAGE);
        this.cantEquipSlots.add((GearSlot.SWORD));
        this.spellDmg = SPELL_DMG_DEFAULT;
        this.physDmg = 0;
    }

    public boolean canEquip(GearSlot slot) {
        if (cantEquipSlots.contains(slot)) {
            return false;
        } else {
            return true;
        }
    }

    public float getSpellDmgDefault() {
        return SPELL_DMG_DEFAULT;
    }

    public float getPhysicalDmgDefault() {
        return 0;
    }
}
