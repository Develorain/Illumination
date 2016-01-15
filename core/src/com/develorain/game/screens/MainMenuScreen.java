package com.develorain.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.develorain.game.Illumination;

public class MainMenuScreen implements Screen {
    private final Illumination game;

    private OrthographicCamera cam;
    private Stage stage;
    private ShapeRenderer shapeRenderer;
    private Skin skin;

    public MainMenuScreen(Illumination game) {
        this.game = game;
        cam = new OrthographicCamera();
        stage = new Stage(new StretchViewport(Illumination.RESOLUTION_X, Illumination.RESOLUTION_Y, cam));
        skin = new Skin();
        skin.addRegions(game.assetManager.get("graphics/uiskin.atlas", TextureAtlas.class));
        skin.add("default-font", game.font);
        skin.load(game.assetManager.get("graphics/uiskin.json"));
    }

    @Override
    public void show() {

    }

    public void update(float dt) {

    }

    @Override
    public void render(float dt) {
        update(dt);

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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

    }
}
