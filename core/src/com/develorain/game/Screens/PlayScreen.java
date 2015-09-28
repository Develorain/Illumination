package com.develorain.game.Screens;

import box2dLight.ConeLight;
import box2dLight.PointLight;
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
import com.develorain.game.Sprites.Cubey;
import com.develorain.game.Tools.B2WorldCreator;
import com.develorain.game.Tools.CameraUtilities;
import com.develorain.game.Tools.LightBuilder;

import static com.develorain.game.Illumination.PPM;
import static com.develorain.game.Illumination.V_HEIGHT;
import static com.develorain.game.Illumination.V_WIDTH;


public class PlayScreen implements Screen {
    public boolean DEBUG_MODE = true;
    public static boolean WHITE_MODE = true;

    RayHandler rayHandler;
    PointLight playerLight;
    ConeLight tempLight;
    float lastTimeRightKeyPressed = -100;
    float lastTimeLeftKeyPressed = -100;
    float lastTimeDashed = -100;
    float currentTime = 0;

    // Reference to the game, used to set Screens
    private Illumination game;

    // Basic PlayScreen variables
    private OrthographicCamera cam;
    private Viewport fitViewport;

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
        fitViewport = new FitViewport(2 * V_WIDTH / PPM, 2 * V_HEIGHT / PPM, cam);

        // Initialize tiled map variables
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("Graphics/level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / PPM);

        // Initialize world
        world = new World(new Vector2(0, -9.8f), true);
        b2dr = new Box2DDebugRenderer();

        // Initialize player
        player = new Cubey(this);

        // Initialize ray handler
        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(1f);

        // Initialize playerLight
        playerLight = LightBuilder.createPointLight(rayHandler, player.b2body, Color.BLACK, 3);

        // Temporary lamp
        tempLight = LightBuilder.createConeLight(rayHandler, 300, 300, Color.WHITE, 4, 270, 30);

        // Initializes the collision of the static tiles (ground)
        new B2WorldCreator(this);
    }

    public void update(float dt) {
        currentTime += dt;

        // Handle input
        handleInput(dt);

        // Sets the world frames to 60 FPS
        world.step(1 / 60f, 6, 2);

        // Updates the camera
        //CameraUtilities.lerpToTarget(cam, new Vector2(player.getX(), player.getY()));
        cameraUpdate(dt);

        // Sets the tiled map renderer to render only what is on screen or in camera view
        renderer.setView(cam);

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

        // Renders the tiled map
        renderer.render();

        // Renders the box2D debug renderer (lines)
        if(DEBUG_MODE)
            b2dr.render(world, cam.combined);

        // Draws player
        player.draw(game.batch);

        //rayHandler.render();
    }

    public void cameraUpdate(float dt) {
        // Centers the camera on the player using interpolation
        CameraUtilities.lerpToTarget(cam, new Vector2(player.b2body.getPosition().x, player.b2body.getPosition().y));
    }

    public void handleInput(float dt) {
        boolean inputGiven = false;
        // Runs if right is pressed or held
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 7) {
            player.b2body.applyLinearImpulse(new Vector2(1f, 0), player.b2body.getWorldCenter(), true);
            inputGiven = true;
        }

        // Runs if left is pressed or held
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -7) {
            player.b2body.applyLinearImpulse(new Vector2(-1f, 0), player.b2body.getWorldCenter(), true);
            inputGiven = true;
        }

        // Runs if up is pressed
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.b2body.applyLinearImpulse(new Vector2(0, 8f), player.b2body.getWorldCenter(), true);
            inputGiven = true;
        }

        // Runs if down is pressed
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            player.b2body.applyLinearImpulse(new Vector2(0, -20f), player.b2body.getWorldCenter(), true);
            inputGiven = true;
        }

        // Dash right
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && player.b2body.getLinearVelocity().x <= 25) {
            if(currentTime - lastTimeDashed > 0.5) {
                player.b2body.applyLinearImpulse(new Vector2(4f, 0), player.b2body.getWorldCenter(), true);
                lastTimeDashed = currentTime;
            }
            inputGiven = true;
        }

        // Dash left
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && player.b2body.getLinearVelocity().x >= -25) {
            if(currentTime - lastTimeDashed > 0.5) {
                player.b2body.applyLinearImpulse(new Vector2(-4f, 0), player.b2body.getWorldCenter(), true);
                lastTimeDashed = currentTime;
            }
            inputGiven = true;
        }


        // Teleport right
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            if(currentTime - lastTimeRightKeyPressed < 0.2f) {
                player.b2body.setTransform(player.b2body.getPosition().x + 100 / PPM, player.b2body.getPosition().y, 0);
                lastTimeRightKeyPressed = -100;
            } else {
                lastTimeRightKeyPressed = currentTime;
            }
        }

        // Teleport left
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            if(currentTime - lastTimeLeftKeyPressed < 0.2f) {
                player.b2body.setTransform(player.b2body.getPosition().x - 100 / PPM, player.b2body.getPosition().y, 0);
                lastTimeLeftKeyPressed = -100;
            } else {
                lastTimeLeftKeyPressed = currentTime;
            }
        }

        // Runs if shift is pressed
        if(Gdx.input.isKeyJustPressed(Input.Keys.F1))
            DEBUG_MODE = !DEBUG_MODE;

        // Runs if Q is pressed
        if(Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            WHITE_MODE = !WHITE_MODE;

            player.switchBoxSprite();
        }

        if(!inputGiven) {
            if(player.b2body.getLinearVelocity().x - 0.3f > 0) {
                player.b2body.setLinearVelocity(player.b2body.getLinearVelocity().x - 0.3f, player.b2body.getLinearVelocity().y);
            } else if(player.b2body.getLinearVelocity().x > 0) {
                player.b2body.setLinearVelocity(0, player.b2body.getLinearVelocity().y);
            }

            if(player.b2body.getLinearVelocity().x + 0.3f < 0) {
                player.b2body.setLinearVelocity(player.b2body.getLinearVelocity().x + 0.3f, player.b2body.getLinearVelocity().y);
            } else if(player.b2body.getLinearVelocity().x < 0) {
                player.b2body.setLinearVelocity(0, player.b2body.getLinearVelocity().y);
            }
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
        fitViewport.update(width, height);
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
