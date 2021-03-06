package com.courter.pandasurvivor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Hero extends GameObject {
    enum GearSlot {
        BOOTS, PANTS, CHESTPIECE, HELMET, STAFF, GLOVES, BRACERS, SWORD
    }

    enum HeroClass {
        MAGE, ARCHER, WARLOCK, WARRIOR
    }

    protected int currentActiveSkill;

    public static final float WALKING_BOUNDS_HERO_HEIGHT = (Gdx.graphics.getHeight() * .018f);
    public static final float WALKING_BOUNDS_HERO_WIDTH = (Gdx.graphics.getWidth() * .011f);

    public static final float SHOOTING_BOUNDS_HERO_HEIGHT = (Gdx.graphics.getHeight() * .106f);
    public static final float SHOOTING_BOUNDS_HERO_WIDTH = (Gdx.graphics.getWidth() * .044f);

    private static final float NEXT_XP_LEVEL_MULTIPLIER = 2;

    private static final int FULL_HEALTH_DEFAULT = 100;

    private float health;
    private int fullHealth;
    private static float healthRegenerationRate;
    private float lastTimeDamaged;
    private int currentLevel;
    private long currentLevelXpRequired;
    private long currentXp;
    float stateTime = 0;
    World.HeroDirections currentDirection;
    private long moneyTotal;
    private long ninjaKillCount;
    private long bossKillCount;
    private ArrayList<Item> inventory;
    private int maxInventorySize;
    private HashMap<Enum, Item> equippedGear;
    private float attackSpeed;
    private float goldBonus;
    protected float spellDmg;
    protected float physDmg;
    private int xpPointsToUse;
    protected HeroClass heroClass;
    protected List<GearSlot> cantEquipSlots;

    public Hero(float x, float y, HeroClass heroClass) {
        super(x, y, WALKING_BOUNDS_HERO_WIDTH, WALKING_BOUNDS_HERO_HEIGHT);
        this.shooting_bounds = createBoundsRectangle(
                x + (SHOOTING_BOUNDS_HERO_WIDTH / 6),
                y,
                SHOOTING_BOUNDS_HERO_WIDTH,
                SHOOTING_BOUNDS_HERO_HEIGHT
        );
        this.currentDirection = World.HeroDirections.DOWN;
        this.fullHealth = FULL_HEALTH_DEFAULT;
        this.health = fullHealth;
        this.healthRegenerationRate = .05f;
        this.lastTimeDamaged = 0;
        this.currentLevelXpRequired = 20;
        this.currentLevel = 1;
        this.currentXp = 0;
        this.moneyTotal = 0;
        this.ninjaKillCount = 0;
        this.bossKillCount = 0;
        this.inventory = new ArrayList<Item>();
        this.maxInventorySize = 12;
        this.equippedGear = new HashMap<Enum, Item>();
        this.attackSpeed = 0;
        this.goldBonus = 0;
        this.spellDmg = 0;
        this.physDmg = 0;
        this.xpPointsToUse = 0;
        this.heroClass = heroClass;
        this.cantEquipSlots = new ArrayList<GearSlot>();
        this.currentActiveSkill = 1;
    }

    public boolean canEquip(GearSlot slot) {
        return false;
    }

    public float getSpellDmgDefault() {
        return 0;
    }

    public float getPhysicalDmgDefault() {
        return 0;
    }

    public int getFullHealthDefault() {
        return FULL_HEALTH_DEFAULT;
    }

    private void recalculateGearBonuses() {
        this.spellDmg = getSpellDmgDefault();
        this.physDmg = getPhysicalDmgDefault();
        this.fullHealth = getFullHealthDefault();
        this.goldBonus = 0;
        this.attackSpeed = 0;
        DecimalFormat df = new DecimalFormat("0.0");
        for (Item item : equippedGear.values()) {
            this.spellDmg += item.getMagicBonus();
            this.physDmg += item.getPhysicalBonus();
            this.fullHealth += item.getHealthBonus();
            this.goldBonus += item.getExtraGoldBonus();
            this.fullHealth += item.getArmorBonus() / 2;
            this.attackSpeed += item.getAttackSpeedBonus();
            if (this.attackSpeed > 20.0f) {
                this.attackSpeed = 20;
            }
            this.spellDmg = Float.parseFloat(df.format(this.spellDmg));
            this.physDmg = Float.parseFloat(df.format(this.physDmg));
            this.goldBonus = Float.parseFloat(df.format(this.goldBonus));
            this.attackSpeed = Float.parseFloat(df.format(this.attackSpeed));
        }
    }

    public boolean hasHelmetEquipped() {
        return (equippedGear.containsKey(GearSlot.HELMET)) ? true : false;
    }

    public Sprite getHelmetSprite() {
        return equippedGear.get(GearSlot.HELMET).getSprite();
    }

    public boolean hasStaffEquipped() {
        return (equippedGear.containsKey(GearSlot.STAFF)) ? true : false;
    }

    public Sprite getStaffSprite() {
        return equippedGear.get(GearSlot.STAFF).getSprite();
    }

    public boolean hasChestpieceEquipped() {
        return (equippedGear.containsKey(GearSlot.CHESTPIECE)) ? true : false;
    }

    public Sprite getChestpieceSprite() {
        return equippedGear.get(GearSlot.CHESTPIECE).getSprite();
    }

    public boolean hasBracersEquipped() {
        return (equippedGear.containsKey(GearSlot.BRACERS)) ? true : false;
    }

    public Sprite getBracersSprite() {
        return equippedGear.get(GearSlot.BRACERS).getSprite();
    }

    public boolean hasGlovesEquipped() {
        return (equippedGear.containsKey(GearSlot.GLOVES)) ? true : false;
    }

    public Sprite getGlovesSprite() {
        return equippedGear.get(GearSlot.GLOVES).getSprite();
    }

    public boolean hasBootsEquipped() {
        return (equippedGear.containsKey(GearSlot.BOOTS)) ? true : false;
    }

    public Sprite getBootsSprite() {
        return equippedGear.get(GearSlot.BOOTS).getSprite();
    }

    public boolean hasPantsEquipped() {
        return (equippedGear.containsKey(GearSlot.PANTS)) ? true : false;
    }

    public Sprite getPantsSprite() {
        return equippedGear.get(GearSlot.PANTS).getSprite();
    }

    public boolean equipItem(Item item) {
        boolean shouldRemoveBounds = addToEquippedGear(item.getGearSlot(), item);
        recalculateGearBonuses();
        return shouldRemoveBounds;
    }

    public Item getCurrentlyEquippedItemFromItemClass(Item item) {
        return equippedGear.get(item.getGearSlot());
    }

    private boolean addToEquippedGear(GearSlot slot, Item item) {
        if (!cantEquipSlots.contains(slot)) {
            boolean shouldRemoveBounds = true;

            inventory.remove(item);
            if (equippedGear.containsKey(slot)) {
                inventory.add(equippedGear.get(slot));
                equippedGear.remove(slot);
                shouldRemoveBounds = false;
            }
            equippedGear.put(slot, item);

            return shouldRemoveBounds;
        } else {
            return false;
        }
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
        updateBounds();
    }

    @Override
    public void updateBounds() {
        bounds.x = position.x - WALKING_BOUNDS_HERO_WIDTH / 2;
        bounds.y = position.y - WALKING_BOUNDS_HERO_HEIGHT / 2;

        shooting_bounds.x = position.x - (SHOOTING_BOUNDS_HERO_WIDTH / 6);
        shooting_bounds.y = position.y - (SHOOTING_BOUNDS_HERO_HEIGHT / 5.5f);
    }

    public void addNinjaKill() {
        this.ninjaKillCount++;
    }

    public long getNinjaKillCount() {
        return this.ninjaKillCount;
    }

    public void addBossKill() {
        this.bossKillCount++;
    }

    public long getBossKillCount() {
        return this.bossKillCount;
    }

    public ArrayList<Item> getInventory() {
        return this.inventory;
    }

    public void addItemToInventory(Item item) {
        this.inventory.add(item);
    }

    public int getMaxInventorySize() {
        return this.maxInventorySize;
    }

    public void setCurrentDirection(World.HeroDirections updatedDirection) {
        currentDirection = updatedDirection;
    }

    public World.HeroDirections getCurrentDirection() {
        return currentDirection;
    }

    public float getHealth() {
        return this.health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public void subtractDamage(float damage) {
        this.health -= damage;
    }

    public void regenerateHealth() {
        this.health += healthRegenerationRate;
    }

    public float getLastTimeDamaged() {
        return lastTimeDamaged;
    }

    public void setLastTimeDamaged(float lastTimeDamaged) {
        this.lastTimeDamaged = lastTimeDamaged;
    }

    public int getFullHealth() {
        return fullHealth;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public long getCurrentLevelXpRequired() {
        return currentLevelXpRequired;
    }

    public void setCurrentLevelXpRequired(int currentLevelXpRequired) {
        this.currentLevelXpRequired = currentLevelXpRequired;
    }

    public long getCurrentXp() {
        return this.currentXp;
    }

    public void setCurrentXp(int currentXp) {
        this.currentXp = currentXp;
    }

    public void handleXpGain(int xpGain) {
        this.currentXp += xpGain;
        while (this.currentXp >= this.currentLevelXpRequired) {
            long leftoverXp = this.currentXp - this.currentLevelXpRequired;
            this.currentLevel++;
            this.currentLevelXpRequired *= NEXT_XP_LEVEL_MULTIPLIER;
            this.currentXp = leftoverXp;
            if (getCurrentLevel() % 2 == 0) {
                incrementXpPointsToUse();
            }
        }
    }

    public long getMoneyTotal() {
        return moneyTotal;
    }

    public void setMoneyTotal(long moneyTotal) {
        this.moneyTotal = moneyTotal;
    }

    public void addMoney(long moneyToAdd) {
        this.moneyTotal += moneyToAdd;
    }

    public float getSpellDmg() {
        return spellDmg;
    }

    public void setSpellDmg(float spellDmg) {
        this.spellDmg = spellDmg;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(float attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public float getGoldBonus() {
        return goldBonus;
    }

    public void setGoldBonus(float goldBonus) {
        this.goldBonus = goldBonus;
    }

    public float getPhysDmg() {
        return physDmg;
    }

    public void setPhysDmg(float physDmg) {
        this.physDmg = physDmg;
    }

    public int getXpPointsToUse() {
        return xpPointsToUse;
    }

    public void incrementXpPointsToUse() {
        this.xpPointsToUse += 1;
    }

    public void incrementHealth() {
        this.fullHealth += 2;
        this.xpPointsToUse -= 1;
    }

    public void incrementSpellDmg() {
        this.spellDmg += 1;
        this.xpPointsToUse -= 1;
    }

    public void incrementPhysicalDmg() {
        this.physDmg += 1;
        this.xpPointsToUse -= 1;
    }

    public void incrementGoldBonus() {
        this.goldBonus += 2;
        this.xpPointsToUse -= 1;
    }

    public int getCurrentActiveSkill() {
        return currentActiveSkill;
    }

    public void setCurrentActiveSkill(int skill) {
        this.currentActiveSkill = skill;
    }
}
