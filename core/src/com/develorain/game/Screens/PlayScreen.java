package com.develorain.game.Screens;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.develorain.game.Illumination;
import com.develorain.game.Sprites.Cubey;
import com.develorain.game.Tools.B2WorldCreator;
import com.develorain.game.Tools.CameraUtilities;


public class PlayScreen implements Screen {
    public boolean DEBUG_MODE = false;
    public static boolean WHITE_MODE = true;

    RayHandler rayHandler;
    PointLight light;

    // Reference to the game, used to set Screens
    private Illumination game;

    // Basic PlayScreen variables
    private OrthographicCamera cam;
    private Viewport gamePort;

    // Tiled map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Box2D variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private Cubey player;

    public PlayScreen(Illumination game) {
        // Set game as class variable
        this.game = game;

        // Initialize camera/viewport variable
        cam = new OrthographicCamera();
        gamePort = new FitViewport(Illumination.V_WIDTH / Illumination.PPM, Illumination.V_HEIGHT / Illumination.PPM, cam); // possibly change to ScreenViewport
        //gamePort = new ScreenViewport(cam); temporary code

        // Initialize tiled map variables
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("Graphics/level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Illumination.PPM);

        // Initialize world
        world = new World(new Vector2(0, -9.8f), true);
        b2dr = new Box2DDebugRenderer();

        // Initialize player
        player = new Cubey(this);

        rayHandler = new RayHandler(world);
        light = new PointLight(rayHandler, 50, Color.WHITE, 1f, player.getX(), player.getY());

        // Initializes the collision of the static tiles (ground)
        new B2WorldCreator(this);
    }

    public void update(float dt) {
        // Handle input
        handleInput(dt);

        // Sets the world frames to 60 FPS
        world.step(1 / 60f, 6, 2);

        // Centers the camera on the player (requires refactoring in the future, incorporate lerping to target, add new package of utilities)
        cam.position.x = player.b2body.getPosition().x;
        cam.position.y = player.b2body.getPosition().y;

        // Updates the camera
        //CameraUtilities.lerpToTarget(cam, new Vector2(player.getX(), player.getY()));
        //cameraUpdate(dt);
        cam.update();

        // Sets the tiled map renderer to render only what is on screen or in camera view
        renderer.setView(cam);
    }

    public void cameraUpdate(float dt) {
        Vector3 position = cam.position;
        position.x = cam.position.x + (player.getX() - cam.position.x) * 0.1f;
        position.y = cam.position.y + (player.getY() - cam.position.y) * 0.1f;
        cam.position.set(position);

        cam.update();
    }

    @Override
    public void render(float dt) {
        update(dt);

        // Clears screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Sets projection of the batch to the camera's matrices
        game.batch.setProjectionMatrix(cam.combined);

        // Renders the tiled map
        renderer.render();

        // Renders the box2D debug renderer (lines)
        if(DEBUG_MODE)
            b2dr.render(world, cam.combined);

        // Draws player
        player.draw(game.batch);

        light.setPosition(player.getX(), player.getY());
        //rayHandler.setCombinedMatrix(cam.combined);
        rayHandler.updateAndRender();
    }

    public void handleInput(float dt) {
        // Runs if right is pressed or held
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2) {
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        }

        // Runs if left is pressed or held
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2) {
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }

        // Runs if up is pressed
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.b2body.applyLinearImpulse(new Vector2(0, 6f), player.b2body.getWorldCenter(), true);
        }

        // Runs if down is pressed
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {

        }

        // Runs if shift is pressed
        if(Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT))
            DEBUG_MODE = !DEBUG_MODE;

        // Runs if Q is pressed
        if(Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            WHITE_MODE = !WHITE_MODE;

            player = new Cubey(this);
        }
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
    }
}
