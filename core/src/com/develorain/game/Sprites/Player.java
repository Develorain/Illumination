package com.develorain.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.develorain.game.Screens.PlayScreen;

import static com.develorain.game.Illumination.*;
import static com.develorain.game.Tools.PlayerController.SLOWMOTION_MODE;

public class Player extends Sprite {
    public final int PLAYER_WIDTH = 16;   // pixels
    public final int PLAYER_HEIGHT = 16;  // pixels
    public final int PLAYER_RESTITUTION = 0;
    public final int PLAYER_FRICTION = 0;
    public final int PLAYER_DENSITY = 10;
    public final float FOOT_SENSOR_SCALING_WIDTH = 0.875f;
    public final float FOOT_SENSOR_SCALING_HEIGHT = 1.5f;
    public final float SIDE_SENSOR_SCALING_HEIGHT = 1f;
    public final float SIDE_SENSOR_SCALING_WIDTH = 1.6f;
    public final boolean PLAYER_FIXED_ROTATION = true;

    public World world;
    public Body playerB2DBody;
    public Sprite boxSprite;
    public Array<Body> tmpBodies;

    public Player(PlayScreen screen, float x, float y) {
        this.world = screen.getWorld();
        createPlayer(x, y);
        tmpBodies = new Array<>();
    }

    private void createPlayer(float x, float y) {
        // Player variable declaration
        BodyDef bdef;
        PolygonShape playerShape;
        FixtureDef fdef;

        // Initialize player sprite
        boxSprite = new Sprite(new Texture("Graphics/whitecubey.png"));
        boxSprite.setSize(32f / PPM, 32f / PPM);
        boxSprite.setOrigin(boxSprite.getWidth() / 2, boxSprite.getHeight() / 2);

        // Initialize and define player definition
        bdef = new BodyDef();
        bdef.position.set(x / PPM, y / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.fixedRotation = PLAYER_FIXED_ROTATION;

        // Initialize player shape
        playerShape = new PolygonShape();

        // Initialize and define player fixture and shape
        fdef = new FixtureDef();
        fdef.restitution = PLAYER_RESTITUTION;
        fdef.density = PLAYER_DENSITY;
        fdef.friction = PLAYER_FRICTION;
        fdef.shape = playerShape;
        fdef.filter.categoryBits = PLAYER_BIT;

        if(!SLOWMOTION_MODE) {
            fdef.filter.maskBits = DEFAULT_BIT | WHITESLOPE_BIT | BLUESLOPE_BIT;
        } else {
            fdef.filter.maskBits = DEFAULT_BIT | WHITESLOPE_BIT | REDSLOPE_BIT;
        }

        playerShape.setAsBox(PLAYER_WIDTH / PPM, PLAYER_HEIGHT / PPM);

        // Initialize player body
        playerB2DBody = world.createBody(bdef);
        playerB2DBody.setUserData(boxSprite);
        playerB2DBody.createFixture(fdef);

        // Create player's foot sensor
        PolygonShape sensorShape = new PolygonShape();
        sensorShape.setAsBox(PLAYER_WIDTH / PPM, 6 / PPM);
        Vector2[] footCoords = new Vector2[] {
                new Vector2((-PLAYER_WIDTH * FOOT_SENSOR_SCALING_WIDTH) / PPM, 0),
                new Vector2((PLAYER_WIDTH * FOOT_SENSOR_SCALING_WIDTH) / PPM, 0),
                new Vector2((-PLAYER_WIDTH * FOOT_SENSOR_SCALING_WIDTH) / PPM, -PLAYER_HEIGHT * FOOT_SENSOR_SCALING_HEIGHT / PPM),
                new Vector2((PLAYER_WIDTH * FOOT_SENSOR_SCALING_WIDTH) / PPM, -PLAYER_HEIGHT * FOOT_SENSOR_SCALING_HEIGHT / PPM)
        };
        sensorShape.set(footCoords);
        fdef.density = 0;
        fdef.shape = sensorShape;
        fdef.isSensor = true;

        playerB2DBody.createFixture(fdef).setUserData("foot sensor");

        // Create player's left sensor
        sensorShape.setAsBox(6 / PPM, PLAYER_HEIGHT / PPM);
        Vector2[] leftCoords = new Vector2[] {
                new Vector2(SIDE_SENSOR_SCALING_WIDTH * -PLAYER_WIDTH / PPM, (PLAYER_HEIGHT * FOOT_SENSOR_SCALING_WIDTH * SIDE_SENSOR_SCALING_HEIGHT) / PPM),
                new Vector2(0 / PPM, (PLAYER_HEIGHT * FOOT_SENSOR_SCALING_WIDTH * SIDE_SENSOR_SCALING_HEIGHT) / PPM),
                new Vector2(SIDE_SENSOR_SCALING_WIDTH * -PLAYER_WIDTH / PPM, (-PLAYER_HEIGHT * FOOT_SENSOR_SCALING_WIDTH * SIDE_SENSOR_SCALING_HEIGHT) / PPM),
                new Vector2(0 / PPM, (-PLAYER_HEIGHT * FOOT_SENSOR_SCALING_WIDTH * SIDE_SENSOR_SCALING_HEIGHT) / PPM)
        };
        sensorShape.set(leftCoords);
        fdef.shape = sensorShape;
        fdef.isSensor = true;

        playerB2DBody.createFixture(fdef).setUserData("left sensor");

        // Create player's right sensor
        sensorShape.setAsBox(6 / PPM, PLAYER_HEIGHT / PPM);
        Vector2[] rightCoords = new Vector2[] {
                new Vector2(0 / PPM, (PLAYER_HEIGHT * FOOT_SENSOR_SCALING_WIDTH * SIDE_SENSOR_SCALING_HEIGHT) / PPM),
                new Vector2(SIDE_SENSOR_SCALING_WIDTH * PLAYER_WIDTH / PPM, (PLAYER_HEIGHT * FOOT_SENSOR_SCALING_WIDTH * SIDE_SENSOR_SCALING_HEIGHT) / PPM),
                new Vector2(0 / PPM, (-PLAYER_HEIGHT * FOOT_SENSOR_SCALING_WIDTH * SIDE_SENSOR_SCALING_HEIGHT) / PPM),
                new Vector2(SIDE_SENSOR_SCALING_WIDTH * PLAYER_WIDTH / PPM, (-PLAYER_HEIGHT * FOOT_SENSOR_SCALING_WIDTH * SIDE_SENSOR_SCALING_HEIGHT) / PPM)
        };
        sensorShape.set(rightCoords);
        fdef.shape = sensorShape;
        fdef.isSensor = true;

        playerB2DBody.createFixture(fdef).setUserData("right sensor");
    }

    public void draw(Batch batch) {
        batch.begin();
        world.getBodies(tmpBodies);
        for(Body body: tmpBodies) {
            if(body.getUserData() != null && body.getUserData() instanceof Sprite) {
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
        if(PlayScreen.WHITE_MODE) {
            boxSprite.setTexture(new Texture("Graphics/whitecubey.png"));
        }

        if(!PlayScreen.WHITE_MODE) {
            boxSprite.setTexture(new Texture("Graphics/blackcubey.png"));
        }
    }
}
