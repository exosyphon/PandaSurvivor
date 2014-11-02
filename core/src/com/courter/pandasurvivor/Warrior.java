package com.courter.pandasurvivor;

/**
 * Created by andrew on 11/2/14.
 */
public class Warrior extends Hero {

    public Warrior(float x, float y) {
        super(x, y, HeroClass.WARRIOR);
        this.cantEquipSlots.add((Hero.GearSlot.STAFF));
        this.spellDmg = 0;
        this.physDmg = 5;
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
        return 5;
    }

    public int getFullHealthDefault() {
        return 150;
    }
}