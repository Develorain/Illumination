package com.develorain.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.develorain.game.Illumination;
import com.develorain.game.Tools.LevelCreator;

public class PlayScreen implements Screen {
    public static boolean DEBUG_MODE = false;
    public static boolean WHITE_MODE = true;
    public static int TIME_SLOWDOWN_MODIFIER = 1;

    // Stores current game time
    public static float currentTime = 0;

    // Reference to the game, used to set Screens
    private Illumination game;

    private Music music;

    private LevelCreator levelCreator;

    public PlayScreen(Illumination game) {
        this.game = game;

        levelCreator = new LevelCreator(game.batch);
        levelCreator.loadNextLevel();

        //music = Illumination.manager.get("Audio/Music/disconnected.ogg", Music.class);
        //music.setVolume(0.5f);
        //music.setLooping(true);
        //music.play();
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

        levelCreator.render();
    }

    public void handleInput() {
        // Toggles between debug modes
        if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
            DEBUG_MODE = !DEBUG_MODE;
        }

        // Toggle between player sprites
        if (Gdx.input.isKeyJustPressed(Input.Keys.F2)) {
            WHITE_MODE = !WHITE_MODE;

            //player.switchBoxSprite();
        }
    }


    @Override
    public void dispose() {
    }

    @Override
    public void resize(int width, int height) {
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
