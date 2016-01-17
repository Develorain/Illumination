package com.develorain.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.develorain.game.Illumination;
import com.develorain.game.tools.Level;

import static com.develorain.game.Illumination.*;

public class PlayScreen implements Screen {
    public static boolean DEBUG_MODE = false;
    public static int TIME_SLOWDOWN_MODIFIER = 1;

    public static float currentTime = 0;
    public int currentLevel = 0;
    public FitViewport fitViewport;
    private Illumination game;
    private Level level;
    private OrthographicCamera cam;

    public PlayScreen(Illumination game) {
        this.game = game;
        cam = new OrthographicCamera();
        fitViewport = new FitViewport(2f * RESOLUTION_X / PPM, 2f * RESOLUTION_Y / PPM, cam);

        loadNextLevel();
    }

    @Override
    public void render(float dt) {
        currentTime += dt;

        level.update(dt);
        level.render(dt);

        if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
            DEBUG_MODE = !DEBUG_MODE;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
            game.setScreen(game.mainMenuScreen);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            loadNextLevel();
        }
    }

    public void loadNextLevel() {
        currentLevel++;

        if (level != null) {
            level.dispose();
        }

        level = new Level(this, cam, currentLevel);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void resize(int width, int height) {
        fitViewport.update(width, height);
    }

    @Override
    public void show() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
}
