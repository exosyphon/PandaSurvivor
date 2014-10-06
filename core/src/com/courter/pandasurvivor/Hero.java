package com.courter.pandasurvivor;

import java.util.ArrayList;

public class Hero extends GameObject {
    public static final float WALKING_BOUNDS_HERO_HEIGHT = 20;
    public static final float WALKING_BOUNDS_HERO_WIDTH = 20;

    public static final float SHOOTING_BOUNDS_HERO_HEIGHT = 115;
    public static final float SHOOTING_BOUNDS_HERO_WIDTH = 80;

    private static final float XP_LEVEL_MULTIPLIER = 2;

    private float health;
    private float fullHealth;
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

    public Hero(float x, float y) {
        super(x, y, WALKING_BOUNDS_HERO_WIDTH, WALKING_BOUNDS_HERO_HEIGHT);
        this.shooting_bounds = createBoundsRectangle(
                x + (SHOOTING_BOUNDS_HERO_WIDTH / 6),
                y,
                SHOOTING_BOUNDS_HERO_WIDTH,
                SHOOTING_BOUNDS_HERO_HEIGHT
        );
        this.currentDirection = World.HeroDirections.DOWN;
        this.fullHealth = 100;
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

    public float getFullHealth() {
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
            this.currentLevelXpRequired *= XP_LEVEL_MULTIPLIER;
            this.currentXp = leftoverXp;
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
}
