package com.develorain.game.Entities;

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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.develorain.game.Tools.BodyFactory;
import com.develorain.game.Tools.Level;
import com.develorain.game.Tools.LightBuilder;

import java.util.ArrayList;

import static com.develorain.game.Illumination.*;
import static com.develorain.game.Tools.PlayerController.SLOW_MOTION_MODE;

public class Player {
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

    public World world;
    public RayHandler rayHandler;
    public Body body;
    public Sprite playerSprite;
    public ArrayList<PointLight> pointLights = new ArrayList<>();
    public ArrayList<Sprite> sprites = new ArrayList<>();
    public Level level;

    private float timer = 0;

    public Player(RayHandler rayHandler, float x, float y, Level level) {
        this.world = level.getWorld();
        this.rayHandler = rayHandler;
        this.level = level;

        createPlayer(x, y, rayHandler);
    }

    private void createPlayer(float x, float y, RayHandler rayHandler) {
        body = BodyFactory.createBoxBody(world, this, x / PPM, y / PPM, PLAYER_WIDTH / PPM, PLAYER_HEIGHT / PPM, EntityType.PLAYER, PLAYER_DENSITY);
        createSprite();
        createFootSensor();
        createLeftSensor();
        createRightSensor();
        createLights(rayHandler);
    }

    public void draw(Batch batch, float dt) {
        timer += dt;

        Sprite sprite = new Sprite(playerSprite);
        sprite.setPosition(body.getPosition().x - (PLAYER_WIDTH / PPM), body.getPosition().y - (PLAYER_HEIGHT / PPM));
        sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        sprites.add(sprite);

        if (timer >= 0.01f) {
            timer = 0;

            for (int i = 0; i < sprites.size(); i++) {
                sprites.get(i).draw(batch);
                sprites.get(i).setAlpha(sprites.get(i).getColor().a - 0.05f);

                if (sprites.get(i).getColor().a <= 0) {
                    sprites.remove(i);
                }
            }
        }
    }

    private void createSprite() {
        playerSprite = new Sprite(new Texture("Graphics/Textures/PlayerSprites/whiteplayer.png"));
        playerSprite.setSize(PLAYER_WIDTH * 2 / PPM, PLAYER_HEIGHT * 2 / PPM);
        playerSprite.setOrigin(playerSprite.getWidth() / 2, playerSprite.getHeight() / 2);
        body.setUserData(playerSprite);
    }

    private void createFootSensor() {
        FixtureDef fdef = new FixtureDef();
        PolygonShape sensorShape = new PolygonShape();

        fdef.isSensor = true;
        fdef.density = 0f;
        fdef.friction = 0;
        fdef.filter.categoryBits = PLAYER_FOOT_SENSOR_BIT;

        fdef.filter.maskBits = WHITE_LINE_BIT;

        if (!SLOW_MOTION_MODE) {
            fdef.filter.maskBits |= BLUE_LINE_BIT;
        } else {
            fdef.filter.maskBits |= RED_LINE_BIT;
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

        body.createFixture(fdef).setUserData("foot sensor");
    }

    private void createLeftSensor() {
        FixtureDef fdef = new FixtureDef();
        PolygonShape sensorShape = new PolygonShape();

        fdef.isSensor = true;
        fdef.density = 0f;
        fdef.friction = 0;
        fdef.filter.categoryBits = PLAYER_LEFT_SENSOR_BIT;
        fdef.filter.maskBits = WHITE_LINE_BIT;

        if (!SLOW_MOTION_MODE) {
            fdef.filter.maskBits |= BLUE_LINE_BIT;
        } else {
            fdef.filter.maskBits |= RED_LINE_BIT;
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

        body.createFixture(fdef).setUserData("left sensor");
    }

    private void createRightSensor() {
        FixtureDef fdef = new FixtureDef();
        PolygonShape sensorShape = new PolygonShape();

        fdef.isSensor = true;
        fdef.density = 0f;
        fdef.friction = 0;
        fdef.filter.categoryBits = PLAYER_RIGHT_SENSOR_BIT;
        fdef.filter.maskBits = WHITE_LINE_BIT;

        if (!SLOW_MOTION_MODE) {
            fdef.filter.maskBits |= BLUE_LINE_BIT;
        } else {
            fdef.filter.maskBits |= RED_LINE_BIT;
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

        body.createFixture(fdef).setUserData("right sensor");
    }

    private void createLights(RayHandler rayHandler) {
        pointLights.add(LightBuilder.createPointLight(rayHandler, body, Color.WHITE, 1));

        //pointLights.add(LightBuilder.createPointLight(rayHandler, body, Color.RED, 1));
        //pointLights.add(LightBuilder.createPointLight(rayHandler, body, Color.RED, 2));

        //pointLights.add(LightBuilder.createPointLight(rayHandler, body, Color.CHARTREUSE, 2));
        //pointLights.add(LightBuilder.createPointLight(rayHandler, body, Color.BLUE, 3));
        //pointLights.add(LightBuilder.createPointLight(rayHandler, body, Color.BLUE, 3));

        //pointLights.add(LightBuilder.createPointLight(rayHandler, body, Color.BLUE, 3));
        //pointLights.add(LightBuilder.createPointLight(rayHandler, body, Color.WHITE, 3));

        //pointLights.add(LightBuilder.createPointLight(rayHandler, body, Color.CHARTREUSE, 1));
        //pointLights.add(LightBuilder.createPointLight(rayHandler, body, Color.BLUE, 2));
        //pointLights.add(LightBuilder.createPointLight(rayHandler, body, Color.BLUE, 2));

        //pointLights.add(LightBuilder.createPointLight(rayHandler, body, Color.CHARTREUSE, 0.5f));
    }

    public Player destroyAndRemake() {
        float x = body.getPosition().x * PPM;  // position is in meters, so covert it to pixels
        float y = body.getPosition().y * PPM;

        world.destroyBody(body);

        for (int i = 0; i < pointLights.size(); i++) {
            pointLights.get(i).remove();
        }

        return new Player(rayHandler, x, y, level);
    }

    public void destroy() {
        world.destroyBody(body);

        for (int i = 0; i < pointLights.size(); i++) {
            pointLights.get(i).remove();
        }
    }

    public Player respawn() {
        for (MapObject object : level.getTiledMap().getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            return new Player(rayHandler, rect.getX(), rect.getY(), level);
        }

        return null;
    }
}
