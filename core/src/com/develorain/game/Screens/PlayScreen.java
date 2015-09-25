package com.develorain.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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
import com.develorain.game.Sprites.Cubey;
import com.develorain.game.Tools.B2WorldCreator;


public class PlayScreen implements Screen {
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
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Illumination.PPM);

        // Set initial camera position to center of the screen (pretty much useless)
        cam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        // Initialize world
        world = new World(new Vector2(0, -9.8f), true);
        b2dr = new Box2DDebugRenderer();

        // Initialize player
        player = new Cubey(world);

        // Initializes the collision of the static tiles (ground)
        new B2WorldCreator(world, map);
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
        cam.update();

        // Sets the tiled map renderer to render only what is on screen or in camera view
        renderer.setView(cam);
    }

    @Override
    public void render(float dt) {
        update(dt);

        // Clears screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Renders the tiled map
        renderer.render();

        // Renders the box2D debug renderer (lines)
        b2dr.render(world, cam.combined);

        // Draws player
        player.drawPlayer(game.batch);

        // Sets projection of the batch to the camera's matrices
        game.batch.setProjectionMatrix(cam.combined);
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
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        }

        // Runs if down is pressed
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {

        }
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
