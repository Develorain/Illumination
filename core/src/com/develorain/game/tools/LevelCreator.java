package com.develorain.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.develorain.game.Illumination.PPM;
import static com.develorain.game.Illumination.RESOLUTION_X;
import static com.develorain.game.Illumination.RESOLUTION_Y;

public class LevelCreator {
    public int currentLevel = 0;
    public FitViewport fitViewport;
    private SpriteBatch batch;
    private OrthographicCamera cam;
    private Level level;

    public LevelCreator(SpriteBatch batch) {
        this.batch = batch;

        cam = new OrthographicCamera();
        fitViewport = new FitViewport(2f * RESOLUTION_X / PPM, 2f * RESOLUTION_Y / PPM, cam);
    }

    public void loadNextLevel() {
        currentLevel++;

        level = new Level(this, batch, cam, currentLevel);
    }

    public void update(float dt) {
        level.update(dt);

        if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            loadNextLevel();
        }
    }

    public void render(float dt) {
        level.render(dt);
    }

    public void resize(int width, int height) {
        fitViewport.update(width, height);
    }
}
