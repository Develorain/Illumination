package com.develorain.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.develorain.game.screens.LoadingScreen;
import com.develorain.game.screens.MainMenuScreen;
import com.develorain.game.screens.PlayScreen;
import com.develorain.game.screens.SplashScreen;

public class Illumination extends Game {
    public static final String TITLE = "Illumination";
    public static final int RESOLUTION_X = 1440; // 1080
    public static final int RESOLUTION_Y = 900; // 720
    public static final float PPM = 100;  // MULTIPLYING BY PPM CONVERTS METERS TO PIXELS. DIVIDING CONVERTS PIXELS TO METERS.

    public static final short WHITE_LINE_BIT = 1;
    public static final short BLUE_LINE_BIT = 2;
    public static final short RED_LINE_BIT = 4;
    public static final short PLAYER_BIT = 8;
    public static final short PLAYER_FOOT_SENSOR_BIT = 16; // remove
    public static final short PLAYER_LEFT_SENSOR_BIT = 32;  // remove
    public static final short PLAYER_RIGHT_SENSOR_BIT = 64;  // remove
    public static final short WHITE_ENEMY_BIT = 128;
    public static final short BLUE_ENEMY_BIT = 256;
    public static final short RED_ENEMY_BIT = 512;
    public static final short BOUNDARY_LINE_BIT = 1024;
    public static final short END_LINE_BIT = 2048;
    public static final short UNCLIMBABLE_LINE_BIT = 4096;
    public static final short SCRIPTED_EVENT_TRIGGER_BIT = 8192;
    // last bit is -32768

    public AssetManager assetManager;
    public SpriteBatch batch;

    public BitmapFont font;

    public LoadingScreen loadingScreen;
    public SplashScreen splashScreen;
    public MainMenuScreen mainMenuScreen;
    public PlayScreen playScreen;

    @Override
    public void create() {
        batch = new SpriteBatch();
        assetManager = new AssetManager();
        assetManager.load("audio/music/disconnected.ogg", Music.class);
        assetManager.load("audio/sounds/hitsound.wav", Sound.class);
        assetManager.load("audio/sounds/startslowmotion.ogg", Sound.class);
        assetManager.load("audio/sounds/endslowmotion.ogg", Sound.class);
        assetManager.load("audio/sounds/Jump8.wav", Sound.class);
        assetManager.finishLoading();

        font = new BitmapFont();

        loadingScreen = new LoadingScreen(this);
        splashScreen = new SplashScreen(this);
        mainMenuScreen = new MainMenuScreen(this);
        playScreen = new PlayScreen(this);

        setScreen(loadingScreen);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        assetManager.dispose();
        loadingScreen.dispose();
        splashScreen.dispose();
    }
}
