package com.courter.pandasurvivor;

/**
 * Created by andrew on 11/2/14.
 */
public class Archer extends Hero {

    public Archer(float x, float y) {
        super(x, y, HeroClass.ARCHER);
        this.cantEquipSlots.add((Hero.GearSlot.SWORD));
        this.spellDmg = 0;
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
        return 0;
    }

    public float getPhysicalDmgDefault() {
        return 7;
    }
}