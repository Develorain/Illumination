package com.develorain.game.Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Background {
    private ScreenViewport screenViewport;
    private OrthographicCamera cam;

    public Background() {
        screenViewport = new ScreenViewport();
        cam = new OrthographicCamera();
    }
}
