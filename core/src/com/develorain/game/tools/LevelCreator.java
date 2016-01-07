package com.develorain.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.develorain.game.Illumination.*;

public class LevelCreator {
    public int currentLevel = 0;
    private SpriteBatch batch;
    private OrthographicCamera cam;
    private FitViewport fitViewport;
    private Level level;

    public LevelCreator(SpriteBatch batch) {
        this.batch = batch;

        cam = new OrthographicCamera();
        fitViewport = new FitViewport(2f * V_WIDTH / PPM, 2f * V_HEIGHT / PPM, cam);
        //fitViewport = new FitViewport(1.5f * V_WIDTH / PPM, 1.5f * V_HEIGHT / PPM, cam);
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
