package com.courter.pandasurvivor;

/**
 * Created by andrew on 11/2/14.
 */
public class Warlock extends Hero {
    private static final int SPELL_DMG_DEFAULT = 5;

    public Warlock(float x, float y) {
        super(x, y, HeroClass.WARLOCK);
        this.cantEquipSlots.add((Hero.GearSlot.SWORD));
        this.spellDmg = SPELL_DMG_DEFAULT;
        this.physDmg = 0;
    }

    public boolean canEquip(Hero.GearSlot slot) {
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