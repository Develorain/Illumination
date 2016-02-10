package com.develorain.game.tools;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.develorain.game.entities.Enemy;
import com.develorain.game.entities.Player;
import com.develorain.game.scenes.HUD;
import com.develorain.game.screens.PlayScreen;

import java.util.ArrayList;

import static com.develorain.game.Illumination.PPM;
import static com.develorain.game.screens.PlayScreen.DEBUG_MODE;
import static com.develorain.game.screens.PlayScreen.TIME_SLOWDOWN_MODIFIER;

public class Level {
    private WorldInitializer worldInitializer;
    private World world;
    private Box2DDebugRenderer b2dr;
    private WorldContactListener worldContactListener;
    private TmxMapLoader mapLoader;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Player player;
    private HUD hud;
    private GameInputHandler gameInputHandler;
    private RayHandler rayHandler;
    private MapProperties mapProperties;
    private int levelWidth;  // in tiles (ex: 300 tiles width)
    private int levelHeight; // in tiles (ex: 150 tiles height)
    private int TILE_WIDTH = 16;
    private int TILE_HEIGHT = 16;
    private SpriteBatch batch;
    private OrthographicCamera cam;

    public Level(PlayScreen playScreen, OrthographicCamera cam, int currentLevel) {
        batch = new SpriteBatch();
        this.cam = cam;

        mapLoader = new TmxMapLoader();
        tiledMap = mapLoader.load("graphics/maps/level" + currentLevel + ".tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / PPM);
        mapProperties = tiledMap.getProperties();
        levelWidth = mapProperties.get("width", Integer.class);
        levelHeight = mapProperties.get("height", Integer.class);

        world = new World(new Vector2(0, -18f), true);

        b2dr = new Box2DDebugRenderer();

        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0.5f);

        hud = new HUD(batch);

        worldInitializer = new WorldInitializer(rayHandler, this);

        player = worldInitializer.getPlayer();

        gameInputHandler = new GameInputHandler(player);

        worldContactListener = new WorldContactListener(gameInputHandler, playScreen);

        world.setContactListener(worldContactListener);
    }

    public void update(float dt) {
        hud.update();

        for (ArrayList<Enemy> arrayList : worldInitializer.getEnemies()) {
            for (int i = 0; i < arrayList.size(); i++) {
                Enemy enemy = arrayList.get(i);
                enemy.update();
            }
        }

        for (int i = worldInitializer.getExploders().size() - 1; i >= 0; i--) {
            if (worldInitializer.getExploders().get(i).body.getPosition().x < player.body.getPosition().x + 300 / PPM) {
                worldInitializer.getExploders().get(i).explode();
                worldInitializer.getExploders().remove(i);
            }
        }

        gameInputHandler.handleInput(dt);

        world.step(1 / (60f * TIME_SLOWDOWN_MODIFIER), 6, 2);

        CameraUtilities.lerpToTarget(cam, player.body.getPosition());

        float startX = cam.viewportWidth / 2;
        float startY = cam.viewportHeight / 2;

        CameraUtilities.boundary(cam, startX, startY, (levelWidth * TILE_WIDTH - startX * 2) / PPM, (levelHeight * TILE_HEIGHT - startY * 2) / PPM);

        mapRenderer.setView(cam);

        rayHandler.update();
    }

    public void render(float dt) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(cam.combined);
        rayHandler.setCombinedMatrix(cam);

        if (DEBUG_MODE) {
            b2dr.render(world, cam.combined);
        }

        mapRenderer.render();

        batch.begin();

        player.draw(batch);

        for (ArrayList<Enemy> arrayList : worldInitializer.getEnemies()) {
            for (int i = 0; i < arrayList.size(); i++) {
                arrayList.get(i).draw(batch);
            }
        }

        batch.end();

        rayHandler.render();

        batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    public World getWorld() {
        return world;
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public WorldInitializer getWorldInitializer() {
        return worldInitializer;
    }

    public void dispose() {
        batch.dispose();
    }
}
