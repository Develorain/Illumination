package com.develorain.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.develorain.game.Illumination;

public class SplashScreen implements Screen {

    private final Illumination game;
    private Stage stage;
    private OrthographicCamera cam;

    private Image splashImage;

    public SplashScreen(final Illumination game) {
        this.game = game;
        cam = new OrthographicCamera();
        stage = new Stage(new StretchViewport(Illumination.RESOLUTION_X, Illumination.RESOLUTION_Y, cam));
        Gdx.input.setInputProcessor(stage);

        Texture splashTexture = new Texture(Gdx.files.internal("graphics/Logo.png"));
        splashImage = new Image(splashTexture);
        splashImage.setPosition(stage.getWidth() - splashTexture.getWidth(), stage.getHeight() - splashTexture.getHeight());

        stage.addActor(splashImage);
    }

    @Override
    public void show() {

    }

    public void update(float dt) {
        stage.act(dt);
    }

    @Override
    public void render(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new PlayScreen(game));
        }

        update(dt);

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();

        game.batch.begin();

        game.font.draw(game.batch, "Splashscreen", 20, 20);

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

    }
}
