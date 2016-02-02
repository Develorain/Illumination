package com.develorain.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.develorain.game.screens.LoadingScreen;
import com.develorain.game.screens.MainMenuScreen;
import com.develorain.game.screens.PlayScreen;
import com.develorain.game.screens.SplashScreen;

public class Illumination extends Game {
    public static final String TITLE = "Illumination";
    public static final int RESOLUTION_X = 1440; // 1080 // 1120
    public static final int RESOLUTION_Y = 900;  // 720  // 700
    public static final float PPM = 100;  // MULTIPLYING BY PPM CONVERTS METERS TO PIXELS. DIVIDING CONVERTS PIXELS TO METERS.

    public static AssetManager assetManager;

    public LoadingScreen loadingScreen;
    public SplashScreen splashScreen;
    public MainMenuScreen mainMenuScreen;
    public PlayScreen playScreen;

    @Override
    public void create() {
        assetManager = new AssetManager();
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
        assetManager.dispose();
        loadingScreen.dispose();
        splashScreen.dispose();
    }
}
