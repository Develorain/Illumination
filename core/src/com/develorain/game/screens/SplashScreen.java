package com.develorain.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.develorain.game.Illumination;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class SplashScreen implements Screen {
    private final Illumination game;
    private Stage stage;
    private OrthographicCamera cam;

    private Image splashImage;
    private Texture splashTexture;

    public SplashScreen(final Illumination game) {
        this.game = game;
        cam = new OrthographicCamera();
        stage = new Stage(new StretchViewport(Illumination.RESOLUTION_X, Illumination.RESOLUTION_Y, cam));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Runnable transitionRunnable = new Runnable() {
            @Override
            public void run() {
                game.setScreen(game.mainMenuScreen);
            }
        };

        splashTexture = game.assetManager.get("graphics/leaf.png", Texture.class);
        splashImage = new Image(splashTexture);
        splashImage.setOrigin(splashImage.getWidth() / 2, splashImage.getHeight() / 2);
        //splashImage.setPosition(stage.getWidth() / 2 - splashImage.getWidth() / 2, stage.getHeight() - splashImage.getHeight() / 2);
        splashImage.setPosition(stage.getWidth() / 2 - splashImage.getWidth() / 2, stage.getHeight() / 2 - splashImage.getHeight() / 2);

        splashImage.addAction(sequence(alpha(0f), scaleTo(0.5f, 0.5f),fadeIn(1f, Interpolation.pow2), delay(1f),
                fadeOut(1f), run(transitionRunnable)));

        stage.addActor(splashImage);
    }

    public void update(float dt) {
        stage.act(dt);
    }

    @Override
    public void render(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new MainMenuScreen(game));
        }

        update(dt);

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
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
        stage.dispose();
    }
}