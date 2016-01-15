package com.develorain.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.develorain.game.Illumination;

public class MainMenuScreen implements Screen {
    private final Illumination game;

    private OrthographicCamera cam;
    private Stage stage;
    private Skin skin;

    private TextButton buttonPlay, buttonExit;

    public MainMenuScreen(Illumination game) {
        this.game = game;
        cam = new OrthographicCamera();
        stage = new Stage(new StretchViewport(Illumination.RESOLUTION_X, Illumination.RESOLUTION_Y, cam));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        skin = new Skin();
        skin.addRegions(game.assetManager.get("graphics/uiskin.atlas", TextureAtlas.class));
        skin.add("default-font", game.font);
        skin.load(Gdx.files.internal("graphics/uiskin.json"));

        initButtons();
    }

    public void update(float dt) {
        stage.act(dt);
    }

    @Override
    public void render(float dt) {
        update(dt);

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    private void initButtons() {
        buttonPlay = new TextButton("Play", skin, "default");
        buttonPlay.setSize(280, 60);
        buttonPlay.setPosition(Illumination.RESOLUTION_X / 2 - buttonPlay.getWidth() / 2,
                Illumination.RESOLUTION_Y / 2 - buttonPlay.getHeight() / 2);
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.playScreen);
            }
        });
        stage.addActor(buttonPlay);

        buttonExit = new TextButton("Exit", skin, "default");
        buttonExit.setSize(280, 60);
        buttonExit.setPosition(Illumination.RESOLUTION_X / 2 - buttonExit.getWidth() / 2,
                Illumination.RESOLUTION_Y / 2 - buttonExit.getHeight() / 2 - buttonExit.getHeight());
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        stage.addActor(buttonExit);
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
