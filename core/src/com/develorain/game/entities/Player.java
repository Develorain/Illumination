package com.develorain.game.entities;

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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.develorain.game.tools.BodyFactory;
import com.develorain.game.tools.Level;
import com.develorain.game.tools.LightFactory;
import com.develorain.game.tools.SensorFactory;

import java.util.ArrayList;

import static com.develorain.game.Illumination.PPM;

public class Player {
    public final int PLAYER_DENSITY = 10;
    public final int PLAYER_WIDTH = 16;     // pixels
    public final int PLAYER_HEIGHT = 16;    // pixels
    public final float FOOT_SENSOR_WIDTH = 0.875f;
    public final float FOOT_SENSOR_HEIGHT = 1.5f;
    public final float SIDE_SENSOR_WIDTH = 1.6f;
    public final float SIDE_SENSOR_HEIGHT = 1f;
    public Body body;
    private World world;
    private RayHandler rayHandler;
    private Sprite playerSprite;
    private ArrayList<PointLight> pointLights = new ArrayList();
    private ArrayList<Sprite> sprites = new ArrayList();
    private Level level;

    private float timer = 0;

    public Player(RayHandler rayHandler, float x, float y, Level level) {
        this.world = level.getWorld();
        this.rayHandler = rayHandler;
        this.level = level;

        createPlayer(x, y, rayHandler);
    }

    private void createPlayer(float x, float y, RayHandler rayHandler) {
        body = BodyFactory.createBoxBody(world, this, x / PPM, y / PPM, PLAYER_WIDTH / PPM, PLAYER_HEIGHT / PPM, PLAYER_DENSITY, true, EntityType.PLAYER, false);
        body.createFixture(SensorFactory.createSensorFixture(0, 0, FOOT_SENSOR_WIDTH, FOOT_SENSOR_HEIGHT, EntityType.FOOT_SENSOR));
        body.createFixture(SensorFactory.createSensorFixture(0, 0, SIDE_SENSOR_WIDTH, SIDE_SENSOR_HEIGHT, EntityType.LEFT_SENSOR));
        body.createFixture(SensorFactory.createSensorFixture(0, 0, SIDE_SENSOR_WIDTH, SIDE_SENSOR_HEIGHT, EntityType.RIGHT_SENSOR));
        createSprite();
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
        playerSprite = new Sprite(new Texture("graphics/textures/whiteplayer.png"));
        playerSprite.setSize(PLAYER_WIDTH * 2 / PPM, PLAYER_HEIGHT * 2 / PPM);
        playerSprite.setOrigin(playerSprite.getWidth() / 2, playerSprite.getHeight() / 2);
        body.setUserData(playerSprite);
    }

    private void createLights(RayHandler rayHandler) {
        pointLights.add(LightFactory.createPointLight(rayHandler, body, Color.WHITE, 1));
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
