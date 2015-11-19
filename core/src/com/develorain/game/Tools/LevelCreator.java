package com.develorain.game.Tools;

import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.develorain.game.Scenes.HUD;
import com.develorain.game.Screens.PlayScreen;
import com.develorain.game.Sprites.Player;
import com.develorain.game.Sprites.Walker;

import static com.develorain.game.Illumination.*;
import static com.develorain.game.Screens.PlayScreen.DEBUG_MODE;
import static com.develorain.game.Screens.PlayScreen.TIME_SLOWDOWN_MODIFIER;

public class LevelCreator {
    public int currentLevel = 0;
    public B2WorldCreator b2worldCreator;
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
    private PlayScreen screen;
    private SpriteBatch batch;
    private OrthographicCamera cam;
    private FitViewport fitViewport;

    public LevelCreator(PlayScreen screen, SpriteBatch batch) {
        this.screen = screen;
        this.batch = batch;

        cam = new OrthographicCamera();
        fitViewport = new FitViewport(2 * V_WIDTH / PPM, 2 * V_HEIGHT / PPM, cam);
    }

    public void loadNextLevel() {
        currentLevel++;
        mapLoader = new TmxMapLoader();
        tiledMap = mapLoader.load("Graphics/Maps/level" + currentLevel + ".tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / PPM);

        world = new World(new Vector2(0, -25f), true);

        b2dr = new Box2DDebugRenderer();

        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0.2f);

        hud = new HUD(batch);

        b2worldCreator = new B2WorldCreator(screen, rayHandler, this);

        player = b2worldCreator.getPlayer();

        playerController = new PlayerController(player);

        contactListener = new WorldContactListener(playerController);

        world.setContactListener(contactListener);
    }

    public void update(float dt) {
        hud.update(dt);

        for (int i = 0; i < b2worldCreator.getWalkers().size(); i++) {
            Walker enemy = b2worldCreator.getWalkers().get(i);
            enemy.update();
        }

        playerController.handleInput();

        world.step(1 / (60f * TIME_SLOWDOWN_MODIFIER), 6, 2);

        CameraUtilities.lerpToTarget(cam, new Vector2(player.playerB2DBody.getPosition().x, player.playerB2DBody.getPosition().y));

        mapRenderer.setView(cam);

        rayHandler.update();
    }

    public void render() {
        batch.setProjectionMatrix(cam.combined);
        rayHandler.setCombinedMatrix(cam);

        if (DEBUG_MODE)
            b2dr.render(world, cam.combined);

        mapRenderer.render();

        player.draw(batch);

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

    public void resize(int width, int height) {
        fitViewport.update(width, height);
    }

    public void dispose() {
        tiledMap.dispose();
        mapRenderer.dispose();
        world.dispose();
        b2dr.dispose();
    }
}
