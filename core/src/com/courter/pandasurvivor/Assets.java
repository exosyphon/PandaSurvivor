package com.courter.pandasurvivor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
//    public static Texture background;
//    public static TextureRegion backgroundRegion;
//
//    public static Texture items;
//    public static TextureRegion mainMenu;
//    public static TextureRegion pauseMenu;
//    public static TextureRegion ready;
//    public static TextureRegion gameOver;
//    public static TextureRegion highScoresRegion;
//    public static TextureRegion logo;
//    public static TextureRegion soundOn;
//    public static TextureRegion soundOff;
//    public static TextureRegion arrow;
//    public static TextureRegion pause;
//    public static Animation coinAnim;
//    public static Animation bobFall;
//    public static TextureRegion bobHit;
//    public static Animation squirrelFly;
//    public static BitmapFont font;
//    public static TextureRegion bullet;
//    public static TextureRegion grass;
//    public static TextureRegion flameWall;
//    public static TextureRegion flameWallActive;
//    public static TextureRegion flameWallDeactive;
//    public static TextureRegion powerup;
//    public static TextureRegion cloud;
//    public static Animation explosionAnim;
//    public static TextureRegion swarmLogoRegion;
//    public static Texture swarmLogo;
//
//
//    public static Music music;
//    public static Sound hitSound;
//    public static Sound coinSound;
//    public static Sound clickSound;
//    public static Sound explosionSound;
//    public static Sound laserSound;
//    public static Sound fireSound;
//    public static Sound powerupSound;
//
//    public static Texture loadTexture (String file) {
//        return new Texture(Gdx.files.internal(file));
//    }
//
//    public static void load () {
//        swarmLogo = loadTexture("data/swarm.png");
//        swarmLogoRegion = new TextureRegion(swarmLogo, 0, 0, 64, 64);
//
//        background = loadTexture("data/backnew2.png");
//        backgroundRegion = new TextureRegion(background, 0, 0, 320, 480);
//
//        items = loadTexture("data/test2.png");
//        mainMenu = new TextureRegion(items, 0, 136, 300, 110);
//        pauseMenu = new TextureRegion(items, 0, 36, 192, 96);
//        ready = new TextureRegion(items, 0, 0, 192, 32);
//        gameOver = new TextureRegion(items, 196, 36, 160, 96);
//        highScoresRegion = new TextureRegion(Assets.items, 0, 168, 300, 110 / 3);
//        logo = new TextureRegion(items, 0, 250, 274, 142);
//        soundOff = new TextureRegion(items, 304, 312, 64, 64);
//        soundOn = new TextureRegion(items, 304, 244, 64, 64);
//        arrow = new TextureRegion(items, 278, 318, 64, 64);
//        pause = new TextureRegion(items, 278, 250, 64, 64);
//
//        bullet = new TextureRegion(items, 404, 0, 8, 8);
//        flameWallActive = new TextureRegion(items, 304, 188, 48, 48);
//        flameWallDeactive = new TextureRegion(items, 304, 136, 48, 48);
//        cloud = new TextureRegion(items, 196, 0, 64, 32);
//        flameWall = new TextureRegion(items, 356, 144, 32, 32);
//        powerup = new TextureRegion(items, 382, 252, 32, 32);
//        grass = new TextureRegion(items, 360, 108, 32, 32);
//        explosionAnim = new Animation(.2f, new TextureRegion(items, 346, 288, 32, 32), new TextureRegion(items, 346, 252, 32, 32),
//                new TextureRegion(items, 356, 216, 32, 32), new TextureRegion(items, 356, 180, 32, 32));
//        coinAnim = new Animation(0.2f, new TextureRegion(items, 332, 0, 32, 32), new TextureRegion(items, 346, 360, 32, 32),
//                new TextureRegion(items, 346, 324, 32, 32), new TextureRegion(items, 346, 360, 32, 32));
//        bobFall = new Animation(0.2f, new TextureRegion(items, 360, 72, 32, 32), new TextureRegion(items, 360, 36, 32, 32));
//        bobHit = new TextureRegion(items, 368, 0, 32, 32);
//        squirrelFly = new Animation(0.2f, new TextureRegion(items, 264, 0, 32, 32), new TextureRegion(items, 296, 0, 32, 32));
//        font = new BitmapFont(Gdx.files.internal("data/font.fnt"), Gdx.files.internal("data/font.png"), false);
//
//        music = Gdx.audio.newMusic(Gdx.files.internal("data/music2.mp3"));
//        music.setLooping(true);
//        music.setVolume(.6f);
//        if (Settings.soundEnabled) music.play();
//        hitSound = Gdx.audio.newSound(Gdx.files.internal("data/hit.wav"));
//        coinSound = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));
//        clickSound = Gdx.audio.newSound(Gdx.files.internal("data/click.wav"));
//        explosionSound = Gdx.audio.newSound(Gdx.files.internal("data/explosion2.mp3"));
//        laserSound = Gdx.audio.newSound(Gdx.files.internal("data/laser.wav"));
//        fireSound = Gdx.audio.newSound(Gdx.files.internal("data/fire.wav"));
//        powerupSound = Gdx.audio.newSound(Gdx.files.internal("data/powerup.wav"));
//    }

//    public static void playSound (Sound sound) {
//        if (Settings.soundEnabled) sound.play(1);
//    }
}