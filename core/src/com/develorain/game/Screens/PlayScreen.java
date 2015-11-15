package com.develorain.game.Screens;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.develorain.game.Illumination;
import com.develorain.game.Scenes.HUD;
import com.develorain.game.Sprites.Player;
import com.develorain.game.Sprites.SampleEnemy;
import com.develorain.game.Tools.B2WorldCreator;
import com.develorain.game.Tools.CameraUtilities;
import com.develorain.game.Tools.PlayerController;
import com.develorain.game.Tools.WorldContactListener;

import static com.develorain.game.Illumination.*;

public class PlayScreen implements Screen {
    public static boolean DEBUG_MODE = false;
    public static boolean WHITE_MODE = true;
    public static int TIME_SLOWDOWN_MODIFIER = 1;

    // Stores current game time
    public static float currentTime = 0;
    public RayHandler rayHandler;
    private PlayerController playerController;

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
    private B2WorldCreator b2worldCreator;
    private Player player;
    private HUD hud;

    private WorldContactListener contactListener;

    private Music music;

    public PlayScreen(Illumination game) {
        this.game = game;

        cam = new OrthographicCamera();
        fitViewport = new FitViewport(2 * V_WIDTH / PPM, 2 * V_HEIGHT / PPM, cam);

        mapLoader = new TmxMapLoader();
        tiledMap = mapLoader.load("Graphics/Maps/level1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / PPM);

        world = new World(new Vector2(0, -25f), true);

        b2dr = new Box2DDebugRenderer();

        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(1f);

        hud = new HUD(game.batch);

        b2worldCreator = new B2WorldCreator(this, rayHandler);

        player = b2worldCreator.getPlayer();

        playerController = new PlayerController(player);

        contactListener = new WorldContactListener(playerController);

        world.setContactListener(contactListener);

        music = Illumination.manager.get("audio/music/disconnected.ogg", Music.class);
        music.setVolume(0.5f);
        music.setLooping(true);
        //music.play();
    }

    public void update(float dt) {
        // Increasing current game time
        currentTime += dt;

        hud.update(dt);

        for (int i = 0; i < b2worldCreator.getSampleEnemies().size(); i++) {
            SampleEnemy enemy = b2worldCreator.getSampleEnemies().get(i);
            enemy.update();
        }

        handleInput();

        playerController.handleInput();

        world.step(1 / (60f * TIME_SLOWDOWN_MODIFIER), 6, 2);

        CameraUtilities.lerpToTarget(cam, new Vector2(player.playerB2DBody.getPosition().x, player.playerB2DBody.getPosition().y));

        // Sets the tiled tiledMap mapRenderer to render only what is on screen or in camera view
        mapRenderer.setView(cam);

        rayHandler.update();
    }

    @Override
    public void render(float dt) {
        update(dt);

        // Clears screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Sets projection of the batch to the camera's matrices
        game.batch.setProjectionMatrix(cam.combined);
        rayHandler.setCombinedMatrix(cam);

        mapRenderer.render();

        // Renders the box2D debug mapRenderer (lines)
        if (DEBUG_MODE)
            b2dr.render(world, cam.combined);

        player.draw(game.batch);

        rayHandler.render();

        // Draws HUD
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    public void handleInput() {
        // Toggles between debug modes
        if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
            DEBUG_MODE = !DEBUG_MODE;
        }

        // Toggle between player sprites
        if (Gdx.input.isKeyJustPressed(Input.Keys.F2)) {
            WHITE_MODE = !WHITE_MODE;

            player.switchBoxSprite();
        }

        // TODO: If I use E, using A breaks the program
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            String previousDirection = player.direction;

            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                cam.rotate(180);

                switch (previousDirection) {
                    case "up":
                        player = player.destroyAndRemake("down");
                        world.setGravity(new Vector2(0, -25));
                        break;
                    case "down":
                        player = player.destroyAndRemake("up");
                        world.setGravity(new Vector2(0, 25));
                        break;
                    case "left":
                        player = player.destroyAndRemake("right");
                        world.setGravity(new Vector2(25, 0));
                        break;
                    case "right":
                        player = player.destroyAndRemake("left");
                        world.setGravity(new Vector2(-25, 0));
                        break;
                }

            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                cam.rotate(90);

                switch (previousDirection) {
                    case "up":
                        player = player.destroyAndRemake("right");
                        world.setGravity(new Vector2(25, 0));
                        break;
                    case "down":
                        player = player.destroyAndRemake("left");
                        world.setGravity(new Vector2(-25, 0));
                        break;
                    case "left":
                        player = player.destroyAndRemake("up");
                        world.setGravity(new Vector2(0, 25));
                        break;
                    case "right":
                        player = player.destroyAndRemake("down");
                        world.setGravity(new Vector2(0, -25));
                        break;
                }
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                cam.rotate(-90);

                switch (previousDirection) {
                    case "up":
                        player = player.destroyAndRemake("left");
                        world.setGravity(new Vector2(-25, 0));
                        break;
                    case "down":
                        player = player.destroyAndRemake("right");
                        world.setGravity(new Vector2(25, 0));
                        break;
                    case "left":
                        player = player.destroyAndRemake("down");
                        world.setGravity(new Vector2(0, -25));
                        break;
                    case "right":
                        player = player.destroyAndRemake("up");
                        world.setGravity(new Vector2(0, 25));
                        break;
                }
            }
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
    public void show() {
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
}
