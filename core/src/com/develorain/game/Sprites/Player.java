package com.develorain.game.Sprites;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.develorain.game.Screens.PlayScreen;
import com.develorain.game.Tools.LightBuilder;

import java.util.ArrayList;

import static com.develorain.game.Illumination.*;
import static com.develorain.game.Tools.PlayerController.SLOW_MOTION_MODE;

public class Player extends Sprite {
    public static final int PLAYER_RESTITUTION = 0;
    public static final int PLAYER_FRICTION = 0;
    public static final int PLAYER_DENSITY = 10;
    public final int PLAYER_WIDTH = 16;     // pixels
    public final int PLAYER_HEIGHT = 16;    // pixels
    public final float FOOT_SENSOR_WIDTH = 16f;
    public final float FOOT_SENSOR_HEIGHT = 6f;
    public final float SIDE_SENSOR_WIDTH = 6f;
    public final float SIDE_SENSOR_HEIGHT = 16f;
    public final float FOOT_SENSOR_SCALING_WIDTH = 0.875f;
    public final float FOOT_SENSOR_SCALING_HEIGHT = 1.5f;
    public final float SIDE_SENSOR_SCALING_HEIGHT = 1f;
    public final float SIDE_SENSOR_SCALING_WIDTH = 1.6f;
    public boolean PLAYER_FIXED_ROTATION = true;

    public World world;
    public PlayScreen screen;
    public RayHandler rayHandler;
    public String direction;
    public Body playerB2DBody;
    public Sprite playerSprite;
    public Array<Body> tmpBodies;
    public ArrayList<PointLight> pointLights = new ArrayList<>();

    public Player(PlayScreen screen, RayHandler rayHandler, float x, float y, String direction) {
        this.world = screen.getWorld();
        this.screen = screen;
        this.rayHandler = rayHandler;
        this.direction = direction;

        createPlayer(x, y, rayHandler);

        tmpBodies = new Array<>();
    }

    private void createPlayer(float x, float y, RayHandler rayHandler) {
        createBody(x, y);
        createSprite();
        createFootSensor();
        createLeftSensor();
        createRightSensor();
        createLights(rayHandler);
    }

    private void createBody(float x, float y) {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape playerShape = new PolygonShape();

        bdef.position.set(x / PPM, y / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.fixedRotation = PLAYER_FIXED_ROTATION;

        determinePlayerDirection(bdef);

        playerShape.setAsBox(PLAYER_WIDTH / PPM, PLAYER_HEIGHT / PPM);

        fdef.shape = playerShape;
        fdef.restitution = PLAYER_RESTITUTION;
        fdef.density = PLAYER_DENSITY;
        fdef.friction = PLAYER_FRICTION;
        fdef.filter.categoryBits = PLAYER_BIT;

        fdef.filter.maskBits = DEFAULT_SLOPE_BIT | ENEMY_BIT | END_SLOPE_BIT;

        if (!SLOW_MOTION_MODE) {
            fdef.filter.maskBits |= NORMAL_SLOPE_BIT;
        } else {
            fdef.filter.maskBits |= ALTERNATE_SLOPE_BIT;
        }

        playerB2DBody = world.createBody(bdef);
        playerB2DBody.createFixture(fdef);
    }

    private void determinePlayerDirection(BodyDef bdef) {
        switch (direction) {
            case "down":
                bdef.angle = 0;
                break;
            case "up":
                bdef.angle = MathUtils.PI;
                break;
            case "left":
                bdef.angle = -MathUtils.PI / 2;
                break;
            case "right":
                bdef.angle = MathUtils.PI / 2;
                break;
        }
    }

    private void createSprite() {
        playerSprite = new Sprite(new Texture("Graphics/Sprites/PlayerSprites/whiteplayer.png"));
        playerSprite.setSize(PLAYER_WIDTH * 2 / PPM, PLAYER_HEIGHT * 2 / PPM);
        playerSprite.setOrigin(playerSprite.getWidth() / 2, playerSprite.getHeight() / 2);
        playerB2DBody.setUserData(playerSprite);
    }

    private void createFootSensor() {
        FixtureDef fdef = new FixtureDef();
        PolygonShape sensorShape = new PolygonShape();

        fdef.isSensor = true;
        fdef.density = 0f;
        fdef.friction = 0;
        fdef.filter.categoryBits = PLAYER_FOOT_SENSOR_BIT;

        fdef.filter.maskBits = DEFAULT_SLOPE_BIT;

        if (!SLOW_MOTION_MODE) {
            fdef.filter.maskBits |= NORMAL_SLOPE_BIT;
        } else {
            fdef.filter.maskBits |= ALTERNATE_SLOPE_BIT;
        }

        sensorShape.setAsBox(FOOT_SENSOR_WIDTH / PPM, FOOT_SENSOR_HEIGHT / PPM);

        Vector2[] footCoords = new Vector2[]{
                new Vector2((-PLAYER_WIDTH * FOOT_SENSOR_SCALING_WIDTH) / PPM, 0),
                new Vector2((PLAYER_WIDTH * FOOT_SENSOR_SCALING_WIDTH) / PPM, 0),
                new Vector2((-PLAYER_WIDTH * FOOT_SENSOR_SCALING_WIDTH) / PPM, -PLAYER_HEIGHT * FOOT_SENSOR_SCALING_HEIGHT / PPM),
                new Vector2((PLAYER_WIDTH * FOOT_SENSOR_SCALING_WIDTH) / PPM, -PLAYER_HEIGHT * FOOT_SENSOR_SCALING_HEIGHT / PPM)
        };

        sensorShape.set(footCoords);
        fdef.shape = sensorShape;

        playerB2DBody.createFixture(fdef).setUserData("foot sensor");
    }

    private void createLeftSensor() {
        FixtureDef fdef = new FixtureDef();
        PolygonShape sensorShape = new PolygonShape();

        fdef.isSensor = true;
        fdef.density = 0f;
        fdef.friction = 0;
        fdef.filter.categoryBits = PLAYER_LEFT_SENSOR_BIT;
        fdef.filter.maskBits = DEFAULT_SLOPE_BIT;

        if (!SLOW_MOTION_MODE) {
            fdef.filter.maskBits |= NORMAL_SLOPE_BIT;
        } else {
            fdef.filter.maskBits |= ALTERNATE_SLOPE_BIT;
        }

        sensorShape.setAsBox(SIDE_SENSOR_WIDTH / PPM, SIDE_SENSOR_HEIGHT / PPM);

        Vector2[] leftCoords = new Vector2[]{
                new Vector2(SIDE_SENSOR_SCALING_WIDTH * -PLAYER_WIDTH / PPM, (PLAYER_HEIGHT * FOOT_SENSOR_SCALING_WIDTH * SIDE_SENSOR_SCALING_HEIGHT) / PPM),
                new Vector2(0 / PPM, (PLAYER_HEIGHT * FOOT_SENSOR_SCALING_WIDTH * SIDE_SENSOR_SCALING_HEIGHT) / PPM),
                new Vector2(SIDE_SENSOR_SCALING_WIDTH * -PLAYER_WIDTH / PPM, (-PLAYER_HEIGHT * FOOT_SENSOR_SCALING_WIDTH * SIDE_SENSOR_SCALING_HEIGHT) / PPM),
                new Vector2(0 / PPM, (-PLAYER_HEIGHT * FOOT_SENSOR_SCALING_WIDTH * SIDE_SENSOR_SCALING_HEIGHT) / PPM)
        };

        sensorShape.set(leftCoords);
        fdef.shape = sensorShape;

        playerB2DBody.createFixture(fdef).setUserData("left sensor");
    }

    private void createRightSensor() {
        FixtureDef fdef = new FixtureDef();
        PolygonShape sensorShape = new PolygonShape();

        fdef.isSensor = true;
        fdef.density = 0f;
        fdef.friction = 0;
        fdef.filter.categoryBits = PLAYER_RIGHT_SENSOR_BIT;
        fdef.filter.maskBits = DEFAULT_SLOPE_BIT;

        if (!SLOW_MOTION_MODE) {
            fdef.filter.maskBits |= NORMAL_SLOPE_BIT;
        } else {
            fdef.filter.maskBits |= ALTERNATE_SLOPE_BIT;
        }

        sensorShape.setAsBox(SIDE_SENSOR_WIDTH / PPM, SIDE_SENSOR_HEIGHT / PPM);

        Vector2[] rightCoords = new Vector2[]{
                new Vector2(0 / PPM, (PLAYER_HEIGHT * FOOT_SENSOR_SCALING_WIDTH * SIDE_SENSOR_SCALING_HEIGHT) / PPM),
                new Vector2(SIDE_SENSOR_SCALING_WIDTH * PLAYER_WIDTH / PPM, (PLAYER_HEIGHT * FOOT_SENSOR_SCALING_WIDTH * SIDE_SENSOR_SCALING_HEIGHT) / PPM),
                new Vector2(0 / PPM, (-PLAYER_HEIGHT * FOOT_SENSOR_SCALING_WIDTH * SIDE_SENSOR_SCALING_HEIGHT) / PPM),
                new Vector2(SIDE_SENSOR_SCALING_WIDTH * PLAYER_WIDTH / PPM, (-PLAYER_HEIGHT * FOOT_SENSOR_SCALING_WIDTH * SIDE_SENSOR_SCALING_HEIGHT) / PPM)
        };

        sensorShape.set(rightCoords);
        fdef.shape = sensorShape;

        playerB2DBody.createFixture(fdef).setUserData("right sensor");
    }

    private void createLights(RayHandler rayHandler) {
        pointLights.add(LightBuilder.createPointLight(rayHandler, playerB2DBody, Color.CHARTREUSE, 2));
        pointLights.add(LightBuilder.createPointLight(rayHandler, playerB2DBody, Color.BLUE, 3));
        pointLights.add(LightBuilder.createPointLight(rayHandler, playerB2DBody, Color.BLUE, 3));
    }

    public Player destroyAndRemake(String direction) {
        float x = playerB2DBody.getPosition().x * PPM;  // position is in world units, so it is converted to pixels
        float y = playerB2DBody.getPosition().y * PPM;

        world.destroyBody(playerB2DBody);

        for (int i = 0; i < pointLights.size(); i++) {
            pointLights.get(i).remove();
        }

        return new Player(screen, rayHandler, x, y, direction);
    }

    public void destroy() {
        world.destroyBody(playerB2DBody);

        for (int i = 0; i < pointLights.size(); i++) {
            pointLights.get(i).remove();
        }
    }

    public Player respawn() {
        for (MapObject object : screen.getTiledMap().getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            return new Player(screen, rayHandler, rect.getX(), rect.getY(), "down");
        }

        return null;
    }

    public void draw(Batch batch) {
        batch.begin();
        world.getBodies(tmpBodies);
        for (Body body : tmpBodies) {
            if (body.getUserData() != null && body.getUserData() instanceof Sprite) {
                Sprite sprite = (Sprite) body.getUserData();

                // Sets the texture to the center of the player
                sprite.setPosition(body.getPosition().x - (PLAYER_WIDTH / PPM), body.getPosition().y - (PLAYER_HEIGHT / PPM));

                sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);

                sprite.draw(batch);
            }
        }
        batch.end();
    }

    public void switchBoxSprite() {
        playerSprite.getTexture().dispose();

        if (PlayScreen.WHITE_MODE) {
            playerSprite.setTexture(new Texture("Graphics/Sprites/PlayerSprites/whiteplayer.png"));
        }

        if (!PlayScreen.WHITE_MODE) {
            playerSprite.setTexture(new Texture("Graphics/Sprites/PlayerSprites/blackplayer.png"));
        }
    }
}
