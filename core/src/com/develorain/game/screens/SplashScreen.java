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

        //splashImage.setPosition(stage.getWidth() / 2 - splashImage.getWidth() / 2, stage.getHeight() / 2 - splashImage.getHeight() / 2);
        splashTexture = game.assetManager.get("graphics/Logo1.png", Texture.class);
        splashImage = new Image(splashTexture);
        splashImage.setOrigin(splashImage.getWidth() / 2, splashImage.getHeight() / 2);
        splashImage.setPosition(stage.getWidth() / 2 - splashImage.getWidth() / 2, stage.getHeight() - splashImage.getHeight() / 2);

        splashImage.addAction(sequence(alpha(0f), scaleTo(0.1f, 0.1f),
                parallel(fadeIn(2f, Interpolation.pow2),
                        scaleTo(2f, 2f, 2.5f, Interpolation.pow5),
                        moveTo(stage.getWidth() / 2 - splashImage.getWidth() / 2, stage.getHeight() / 2 - splashImage.getHeight() / 2, 2f, Interpolation.swing)),
                delay(1.5f), fadeOut(1.25f), run(transitionRunnable)));

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

        game.batch.begin();
        game.font.draw(game.batch, "Splash screen", 20, 20);
        game.batch.end();
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
