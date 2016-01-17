package com.develorain.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.develorain.game.Illumination;

public class LoadingScreen extends MyScreen {
    private ShapeRenderer shapeRenderer;
    private float progress;

    public LoadingScreen(final Illumination game) {
        super(game);
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {
        progress = 0f;
        queueAssets();
    }

    private void queueAssets() {
        game.assetManager.load("audio/music/disconnected.ogg", Music.class);
        game.assetManager.load("audio/sounds/hitsound.wav", Sound.class);
        game.assetManager.load("audio/sounds/startslowmotion.ogg", Sound.class);
        game.assetManager.load("audio/sounds/endslowmotion.ogg", Sound.class);
        game.assetManager.load("audio/sounds/Jump8.wav", Sound.class);
        game.assetManager.load("graphics/leaf.png", Texture.class);
        game.assetManager.load("graphics/leafblurred.png", Texture.class);
        game.assetManager.load("graphics/develorain.png", Texture.class);
        game.assetManager.load("graphics/uiskin.atlas", TextureAtlas.class);
    }

    public void update(float dt) {
        progress = MathUtils.lerp(progress, game.assetManager.getProgress(), 0.1f);

        // assetManager.update() returns false if not done loading and true if done loading
        if (game.assetManager.update() && progress >= game.assetManager.getProgress() - 0.001f) {
            game.setScreen(game.splashScreen);
        }
    }

    @Override
    public void render(float dt) {
        update(dt);

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(32, cam.viewportHeight / 2 - 8, progress * (cam.viewportWidth - 64), 2);
        shapeRenderer.end();
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
