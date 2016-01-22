package com.develorain.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.develorain.game.Illumination;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class MainMenuScreen extends MyScreen {
    public BitmapFont font;
    private Skin skin;
    private TextButton buttonPlay, buttonExit;
    private Image titleImage;
    private Texture titleTexture;

    public MainMenuScreen(Illumination game) {
        super(game);
        font = new BitmapFont();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        skin = new Skin();
        skin.addRegions(Illumination.assetManager.get("graphics/uiskin.atlas", TextureAtlas.class));
        skin.add("default-font", font);
        skin.load(Gdx.files.internal("graphics/uiskin.json"));

        initButtons();

        titleTexture = Illumination.assetManager.get("graphics/illumination.png", Texture.class);
        titleImage = new Image(titleTexture);
        titleImage.setOrigin(titleImage.getWidth() / 2, titleImage.getHeight() / 2);
        titleImage.setPosition(stage.getWidth() / 2 - titleImage.getWidth() / 2, stage.getHeight() / 2 - titleImage.getHeight() / 2 + 300);
        titleImage.addAction(sequence(alpha(0f), parallel(fadeIn(2f, Interpolation.pow2))));

        stage.addActor(titleImage);
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
        buttonPlay.addAction(sequence(alpha(0f), parallel(fadeIn(1f, Interpolation.pow2))));
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
        buttonExit.addAction(sequence(alpha(0f), parallel(fadeIn(1f, Interpolation.pow2))));
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
        buttonPlay.remove();
        buttonExit.remove();
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
