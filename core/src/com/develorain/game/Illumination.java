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

    public AssetManager assetManager;
    public SpriteBatch batch;

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
        assetManager.dispose();
        loadingScreen.dispose();
        splashScreen.dispose();
    }
}
