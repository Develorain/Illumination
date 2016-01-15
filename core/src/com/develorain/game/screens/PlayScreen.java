package com.develorain.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.develorain.game.Illumination;
import com.develorain.game.tools.LevelCreator;

public class PlayScreen implements Screen {
    public static boolean DEBUG_MODE = false;
    public static int TIME_SLOWDOWN_MODIFIER = 1;

    // Stores current game time
    public static float currentTime = 0;

    // Reference to the game, used to set Screens
    private Illumination game;
    private LevelCreator levelCreator;

    public PlayScreen(Illumination game) {
        this.game = game;

        levelCreator = new LevelCreator(game.batch);
        levelCreator.loadNextLevel();
    }

    public void update(float dt) {
        levelCreator.update(dt);

        currentTime += dt;

        handleInput();
    }

    @Override
    public void render(float dt) {
        update(dt);

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        levelCreator.render(dt);
    }

    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
            DEBUG_MODE = !DEBUG_MODE;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
            game.setScreen(game.mainMenuScreen);
        }
    }

    @Override
    public void dispose() {
    }

    @Override
    public void resize(int width, int height) {
        //levelCreator.fitViewport.update(width, height);
        levelCreator.resize(width, height);
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
