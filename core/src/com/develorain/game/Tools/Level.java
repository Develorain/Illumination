package com.develorain.game.Tools;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.develorain.game.Entities.Enemy;
import com.develorain.game.Entities.Exploder;
import com.develorain.game.Entities.Player;
import com.develorain.game.Scenes.HUD;

import static com.develorain.game.Illumination.PPM;
import static com.develorain.game.Screens.PlayScreen.DEBUG_MODE;
import static com.develorain.game.Screens.PlayScreen.TIME_SLOWDOWN_MODIFIER;

public class Level {
    private WorldInitializer worldInitializer;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer b2dr;
    private WorldContactListener contactListener;
    private TmxMapLoader mapLoader;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Player player;
    private HUD hud;
    private PlayerController playerController;
    private RayHandler rayHandler;
    private OrthographicCamera cam;
    private MapProperties mapProperties;
    private int levelWidth;  // in tiles (ex: 300 tiles width)
    private int levelHeight; // in tiles (ex: 150 tiles height)
    private int TILE_WIDTH = 16;
    private int TILE_HEIGHT = 16;

    public Level(LevelCreator levelCreator, SpriteBatch batch, OrthographicCamera cam, int currentLevel) {
        this.batch = batch;
        this.cam = cam;

        mapLoader = new TmxMapLoader();
        tiledMap = mapLoader.load("Graphics/Maps/level" + currentLevel + ".tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / PPM);
        mapProperties = tiledMap.getProperties();
        levelWidth = mapProperties.get("width", Integer.class);
        levelHeight = mapProperties.get("height", Integer.class);

        world = new World(new Vector2(0, -25f), true);

        b2dr = new Box2DDebugRenderer();

        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0.25f);

        hud = new HUD(batch);

        worldInitializer = new WorldInitializer(rayHandler, this);

        player = worldInitializer.getPlayer();

        playerController = new PlayerController(player);

        contactListener = new WorldContactListener(playerController, levelCreator);

        world.setContactListener(contactListener);
    }

    public void update(float dt) {
        hud.update();

        for (int i = 0; i < worldInitializer.getEnemies().size(); i++) {
            Enemy enemy = worldInitializer.getEnemies().get(i);
            enemy.update();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            for (int i = 0; i < worldInitializer.getEnemies().size(); i++) {
                if (worldInitializer.getEnemies().get(i) instanceof Exploder) {
                    ((Exploder) worldInitializer.getEnemies().get(i)).explode();
                    worldInitializer.getEnemies().remove(i);
                }
            }
        }

        playerController.handleInput(dt);

        world.step(1 / (60f * TIME_SLOWDOWN_MODIFIER), 6, 2);

        CameraUtilities.lerpToTarget(cam, player.body.getPosition());

        float startX = cam.viewportWidth / 2;
        float startY = cam.viewportHeight / 2;

        CameraUtilities.boundary(cam, startX, startY, (levelWidth * TILE_WIDTH - startX * 2) / PPM, (levelHeight * TILE_HEIGHT - startY * 2) / PPM);

        mapRenderer.setView(cam);

        rayHandler.update();
    }

    public void render(float dt) {
        batch.setProjectionMatrix(cam.combined);
        rayHandler.setCombinedMatrix(cam);

        if (DEBUG_MODE) {
            b2dr.render(world, cam.combined);
        }

        mapRenderer.render();

        batch.begin();

        player.draw(batch, dt);

        for (int i = 0; i < worldInitializer.getEnemies().size(); i++) {
            worldInitializer.getEnemies().get(i).draw(batch);
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
}