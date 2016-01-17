package com.develorain.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.develorain.game.Illumination;

public abstract class MyScreen implements Screen {
    protected Illumination game;
    protected OrthographicCamera cam;
    protected Stage stage;

    public MyScreen(final Illumination game) {
        this.game = game;
        cam = new OrthographicCamera();
        stage = new Stage(new FitViewport(Illumination.RESOLUTION_X, Illumination.RESOLUTION_Y, cam));
    }
}
