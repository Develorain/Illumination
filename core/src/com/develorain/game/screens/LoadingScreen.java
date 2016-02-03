package com.develorain.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.develorain.game.Illumination;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class LoadingScreen extends MyScreen {
    private ShapeRenderer shapeRenderer;
    private float progress;
    private Image titleImage;
    private Texture titleTexture;

    public LoadingScreen(final Illumination game) {
        super(game);
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {
        progress = 0f;

        queueAssets();

        titleTexture = new Texture(Gdx.files.internal("graphics/logos/illumination.png"));
        titleImage = new Image(titleTexture);
        titleImage.setOrigin(titleImage.getWidth() / 2, titleImage.getHeight() / 2);
        titleImage.setPosition(stage.getWidth() / 2 - titleImage.getWidth() / 2, stage.getHeight() / 2 - titleImage.getHeight() / 2 + 300);
        titleImage.addAction(sequence(alpha(0f), parallel(fadeIn(0.5f, Interpolation.pow2))));

        stage.addActor(titleImage);
    }

    private void queueAssets() {
        Illumination.assetManager.load("audio/music/disconnected.ogg", Music.class);
        Illumination.assetManager.load("audio/sounds/hitsound.wav", Sound.class);
        Illumination.assetManager.load("audio/sounds/startslowmotion.ogg", Sound.class);
        Illumination.assetManager.load("audio/sounds/endslowmotion.ogg", Sound.class);
        Illumination.assetManager.load("audio/sounds/Jump8.wav", Sound.class);
        Illumination.assetManager.load("graphics/logos/develorain.png", Texture.class);
        Illumination.assetManager.load("graphics/logos/illumination.png", Texture.class);
        Illumination.assetManager.load("graphics/gui/uiskin.atlas", TextureAtlas.class);
    }

    public void update(float dt) {
        stage.act(dt);

        progress = MathUtils.lerp(progress, Illumination.assetManager.getProgress(), 0.1f);

        // assetManager.update() returns false if not done loading and true if done loading
        if (Illumination.assetManager.update() && progress >= Illumination.assetManager.getProgress() - 0.001f) {
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

        stage.draw();
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
