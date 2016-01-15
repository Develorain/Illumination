package com.develorain.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.develorain.game.Illumination;

public class LoadingScreen implements Screen {
    private final Illumination game;
    private ShapeRenderer shapeRenderer;
    private float progress;
    private OrthographicCamera cam;

    public LoadingScreen(final Illumination game) {
        this.game = game;
        cam = new OrthographicCamera(Illumination.RESOLUTION_X, Illumination.RESOLUTION_Y);
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {
        progress = 0f;
        queueAssets();
    }

    private void queueAssets() {
        game.assetManager.load("graphics/Logo1.png", Texture.class);
        game.assetManager.finishLoading();
    }

    public void update(float dt) {
        progress = MathUtils.lerp(progress, game.assetManager.getProgress(), 0.1f);

        if (game.assetManager.update() && progress >= game.assetManager.getProgress() - 0.001f) {
            game.setScreen(game.splashScreen);
        }
    }

    @Override
    public void render(float dt) {
        update(dt);

        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(32, cam.viewportHeight / 2 - 8, cam.viewportWidth - 64, 2);

        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(32, cam.viewportHeight / 2 - 8, progress * (cam.viewportWidth - 64), 2);
        shapeRenderer.end();

        game.batch.begin();
        game.font.draw(game.batch, "Loading screen", 20, 20);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

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

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
