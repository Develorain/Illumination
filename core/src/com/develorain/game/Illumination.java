package com.develorain.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.develorain.game.Screens.PlayScreen;

public class Illumination extends Game {
    public static final String TITLE = "Illumination";
    public static final int V_WIDTH = 1080;
    public static final int V_HEIGHT = 720;
    public static final float PPM = 100;

    public static final short DEFAULT_SLOPE_BIT = 1;
    public static final short NORMAL_SLOPE_BIT = 2;
    public static final short ALTERNATE_SLOPE_BIT = 4;
    public static final short PLAYER_BIT = 8;
    public static final short PLAYER_FOOT_SENSOR_BIT = 16; // remove
    public static final short PLAYER_LEFT_SENSOR_BIT = 32;  // remove
    public static final short PLAYER_RIGHT_SENSOR_BIT = 64;  // remove
    public static final short DEFAULT_ENEMY_BIT = 128;
    public static final short BOUNDARY_SLOPE_BIT = 256;
    public static final short END_SLOPE_BIT = 512;
    public static final short NORMAL_ENEMY_BIT = 1024;
    public static final short ALTERNATE_ENEMY_BIT = 2048;
    public static final short UNCLIMBABLE_DEFAULT_SLOPE_BIT = 4096;
    public static final short UNCLIMBABLE_NORMAL_SLOPE_BIT = 8192;
    public static final short UNCLIMBABLE_ALTERNATE_SLOPE_BIT = 16384;
    public static final short PROJECTILE_BIT = -32768;

    public static AssetManager manager;  // consider not using static keyword
    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        manager = new AssetManager();
        manager.load("Audio/Music/disconnected.ogg", Music.class);
        manager.load("Audio/Sounds/hitsound.wav", Sound.class);
        manager.load("Audio/Sounds/startslowmotion.ogg", Sound.class);
        manager.load("Audio/Sounds/endslowmotion.ogg", Sound.class);
        manager.finishLoading();

        setScreen(new PlayScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }
}
