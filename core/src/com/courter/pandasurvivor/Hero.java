package com.courter.pandasurvivor;

public class Hero extends GameObject {
    public static final float WALKING_BOUNDS_HERO_HEIGHT = 2;
    public static final float WALKING_BOUNDS_HERO_WIDTH = 2;

    public static final float SHOOTING_BOUNDS_HERO_HEIGHT = 115;
    public static final float SHOOTING_BOUNDS_HERO_WIDTH = 80;

    private static final float XP_LEVEL_MULTIPLIER = 1.25f;

    private float health;
    private float fullHealth;
    private static float healthRegenerationRate;
    private float lastTimeDamaged;
    private int currentLevel;
    private int currentLevelXpRequired;
    private int currentXp;
    float stateTime = 0;
    World.HeroDirections currentDirection;

    public Hero(float x, float y) {
        super(x, y, WALKING_BOUNDS_HERO_WIDTH, WALKING_BOUNDS_HERO_HEIGHT);
        this.shooting_bounds = createBoundsRectangle(
                x - (SHOOTING_BOUNDS_HERO_WIDTH / 6),
                y - (SHOOTING_BOUNDS_HERO_HEIGHT / 5.5f),
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
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;

        bounds.x = position.x - WALKING_BOUNDS_HERO_WIDTH / 2;
        bounds.y = position.y - WALKING_BOUNDS_HERO_HEIGHT / 2;

        shooting_bounds.x = position.x - (SHOOTING_BOUNDS_HERO_WIDTH / 6);
        shooting_bounds.y = position.y - (SHOOTING_BOUNDS_HERO_HEIGHT / 5.5f);
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

    public int getCurrentLevelXpRequired() {
        return currentLevelXpRequired;
    }

    public void setCurrentLevelXpRequired(int currentLevelXpRequired) {
        this.currentLevelXpRequired = currentLevelXpRequired;
    }

    public int getCurrentXp() {
        return this.currentXp;
    }

    public void setCurrentXp(int currentXp) {
        this.currentXp = currentXp;
    }

    public void handleXpGain(int xpGain) {
        this.currentXp += xpGain;
        if(this.currentXp >= this.currentLevelXpRequired) {
            int leftoverXp = this.currentXp - this.currentLevelXpRequired;
            this.currentLevel++;
            this.currentLevelXpRequired *= XP_LEVEL_MULTIPLIER;
            this.currentXp = leftoverXp;
        }
    }
}
