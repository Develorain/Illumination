package com.develorain.game.Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.develorain.game.Illumination.PPM;

public class Background {
    private ScreenViewport screenViewport;

    private TmxMapLoader mapLoader;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;

    public Background() {
        screenViewport = new ScreenViewport();

        mapLoader = new TmxMapLoader();
        tiledMap = mapLoader.load("Graphics/Maps/level2.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / PPM);
    }

    public void update(OrthographicCamera cam) {
        mapRenderer.setView(cam);
    }

    public void render() {
        mapRenderer.render();
    }
}
