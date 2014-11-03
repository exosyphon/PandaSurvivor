package com.courter.pandasurvivor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Assets {
    public static Texture dpad;
    public static Texture aButton;
    public static Texture bagButton;
    public static Texture pandaSpriteSheet;
    public static Texture ninjaSpriteSheet;
    public static Texture fireball;
    public static Texture wall;
    public static Texture healthBar;
    public static Texture coins;
    public static Texture retryPrompt;
    public static Texture pumpkinBossSpriteSheet;
    public static Texture bossKey;
    public static Texture swordSprite;
    public static Texture staffSprite;
    public static Texture inventorySprite;
    public static Texture useDestroyInventoryOptionsSprite;
    public static Texture armorButton;
    public static Texture armorViewSprite;
    public static Texture emptyBootsSprite;
    public static Texture emptyBracersSprite;
    public static Texture emptyChestpieceSprite;
    public static Texture emptyPantsSprite;
    public static Texture emptyStaffSprite;
    public static Texture emptyGloveSprite;
    public static Texture emptyHelmetSprite;
    public static Texture clothHelmetSprite;
    public static Texture clothChestpieceSprite;
    public static Texture clothBootsSprite;
    public static Texture clothGlovesSprite;
    public static Texture clothPantsSprite;
    public static Texture clothBracersSprite;
    public static Texture gearStatsViewSprite;
    public static Texture cantEquipGearStatsViewSprite;
    public static Texture statsDestroyViewSprite;
    public static Texture gearStatsWithoutButtonsViewSprite;
    public static Texture grayAddStatButtonSprite;
    public static Texture addStatButtonSprite;
    public static Texture levelPortalSprite;
    public static Texture houseSprite;
    public static Texture fireballSkillButtonSprite;
    public static Texture disabledFireballSkillButtonSprite;
    public static Texture freezeRingSkillButtonSprite;
    public static Texture disabledFreezeRingSkillButtonSprite;
    public static Texture freezeRingSprite;
    public static Texture frozenGroundSprite;

    public static Texture loadTexture(String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static void load() {
        dpad = loadTexture("dpad.png");
        aButton = loadTexture("a-button.png");
        pandaSpriteSheet = loadTexture("panda3.png");
        ninjaSpriteSheet = loadTexture("ninjas.png");
        fireball = loadTexture("fireball.png");
        wall = loadTexture("wall.png");
        healthBar = loadTexture("healthbar.png");
        coins = loadTexture("gold_coins.png");
        bossKey = loadTexture("boss_key.png");
        retryPrompt = loadTexture("tryagain.png");
        pumpkinBossSpriteSheet = loadTexture("pumpkin_boss.png");
        bagButton = loadTexture("bag_button.png");
        armorButton = loadTexture("armor_button.png");
        inventorySprite = loadTexture("inventory.png");
        useDestroyInventoryOptionsSprite = loadTexture("use_destroy.png");
        swordSprite = loadTexture("sword.png");
        staffSprite = loadTexture("staff.png");
        armorViewSprite = loadTexture("armor_view.png");
        emptyHelmetSprite = loadTexture("empty_helmet.png");
        emptyBootsSprite = loadTexture("empty_boots.png");
        emptyBracersSprite = loadTexture("empty_bracers.png");
        emptyChestpieceSprite = loadTexture("empty_chestpiece.png");
        emptyGloveSprite = loadTexture("empty_glove.png");
        emptyPantsSprite = loadTexture("empty_pants.png");
        emptyStaffSprite = loadTexture("empty_staff.png");
        clothHelmetSprite = loadTexture("cloth_helmet.png");
        clothChestpieceSprite = loadTexture("cloth_chestpiece.png");
        clothGlovesSprite = loadTexture("cloth_glove.png");
        clothBootsSprite = loadTexture("cloth_boots.png");
        clothPantsSprite = loadTexture("cloth_pants.png");
        clothBracersSprite = loadTexture("cloth_bracers.png");
        gearStatsViewSprite = loadTexture("gear_stat_view.png");
        statsDestroyViewSprite = loadTexture("stats_destroy.png");
        gearStatsWithoutButtonsViewSprite = loadTexture("gear_stat_view_without_buttons.png");
        grayAddStatButtonSprite = loadTexture("disabled_add_stat_button.png");
        addStatButtonSprite = loadTexture("add_stat_button.png");
        levelPortalSprite = loadTexture("level_portal.png");
        houseSprite = loadTexture("house.png");
        cantEquipGearStatsViewSprite = loadTexture("cant_equip_gear_stat_view.png");
        fireballSkillButtonSprite = loadTexture("fireball_skill_button.png");
        disabledFireballSkillButtonSprite = loadTexture("disabled_fireball_skill_button.png");
        freezeRingSkillButtonSprite = loadTexture("freezering_skill_button.png");
        disabledFreezeRingSkillButtonSprite = loadTexture("disabled_freezering_skill_button.png");
        freezeRingSprite = loadTexture("freeze_ring.png");
        frozenGroundSprite = loadTexture("ground_ice.png");
    }
}