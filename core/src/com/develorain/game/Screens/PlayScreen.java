package com.develorain.game.Screens;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.develorain.game.Illumination;
import com.develorain.game.Scenes.HUD;
import com.develorain.game.Sprites.Player;
import com.develorain.game.Tools.*;

import static com.develorain.game.Illumination.PPM;
import static com.develorain.game.Illumination.V_HEIGHT;
import static com.develorain.game.Illumination.V_WIDTH;


public class PlayScreen implements Screen {
    public static boolean DEBUG_MODE = false;
    public static boolean WHITE_MODE = true;
    public static int TIME_SLOWDOWN_MODIFIER = 1;

    public RayHandler rayHandler;
    private PlayerController playerController;

    // Stores current game time
    public static float currentTime = 0;

    // Reference to the game, used to set Screens
    private Illumination game;

    // Basic PlayScreen variables
    private OrthographicCamera cam;
    private Viewport fitViewport;

    // Map variables
    private TmxMapLoader mapLoader;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;

    // Box2D variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private Player player;
    private HUD hud;

    private WorldContactListener contactListener;

    public PlayScreen(Illumination game) {
        this.game = game;

        cam = new OrthographicCamera();
        fitViewport = new FitViewport(2 * V_WIDTH / PPM, 2 * V_HEIGHT / PPM, cam);

        mapLoader = new TmxMapLoader();
        tiledMap = mapLoader.load("Graphics/level1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / PPM);

        world = new World(new Vector2(0, -25f), true);

        b2dr = new Box2DDebugRenderer();

        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(1f);

        player = new Player(this, rayHandler, 300, 300);

        playerController = new PlayerController(player);

        LightBuilder.createConeLight(rayHandler, 800, 600, Color.CHARTREUSE, 8, 270, 30);
        LightBuilder.createConeLight(rayHandler, 800, 600, Color.YELLOW, 4, 270, 30);

        hud = new HUD(game.batch);

        contactListener = new WorldContactListener(playerController);

        world.setContactListener(contactListener);

        new B2WorldCreator(this);
    }

    public void update(float dt) {
        // Increasing current game time
        currentTime += dt;

        // Updates the HUD
        hud.update(dt);

        // Handles play screen input
        handleInput();

        // Handles player input
        playerController.handleInput();

        // Sets the world frames to 60 FPS
        world.step(1 / (60f * TIME_SLOWDOWN_MODIFIER), 6, 2);

        // Centers the camera on the player using interpolation (updates the camera)
        CameraUtilities.lerpToTarget(cam, new Vector2(player.playerB2DBody.getPosition().x, player.playerB2DBody.getPosition().y));

        // Sets the tiled tiledMap mapRenderer to render only what is on screen or in camera view
        mapRenderer.setView(cam);

        // Updates the ray handler
        rayHandler.update();
    }

    @Override
    public void render(float dt) {
        // Runs update method before rendering
        update(dt);

        // Clears screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Sets projection of the batch to the camera's matrices
        game.batch.setProjectionMatrix(cam.combined);
        rayHandler.setCombinedMatrix(cam);

        // Renders the tiledMap
        mapRenderer.render();

        // Renders the box2D debug mapRenderer (lines)
        if (DEBUG_MODE)
            b2dr.render(world, cam.combined);

        // Draws player
        player.draw(game.batch);

        // Renders ray handler
        rayHandler.render();

        // Draws HUD
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    public void handleInput() {
        // Toggles between debug modes
        if (Gdx.input.isKeyJustPressed(Input.Keys.F1))
            DEBUG_MODE = !DEBUG_MODE;

        // Toggle between player sprites
        if (Gdx.input.isKeyJustPressed(Input.Keys.F2)) {
            WHITE_MODE = !WHITE_MODE;

            player.switchBoxSprite();
        }
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        mapRenderer.dispose();
        world.dispose();
        b2dr.dispose();
    }

    @Override
    public void resize(int width, int height) {
        fitViewport.update(width, height);
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void show() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}
