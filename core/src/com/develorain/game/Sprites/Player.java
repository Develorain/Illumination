package com.develorain.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.develorain.game.Screens.PlayScreen;

import static com.develorain.game.Illumination.PPM;

public class Player extends Sprite {
    public final int PLAYER_WIDTH = 16;   // pixels
    public final int PLAYER_HEIGHT = 16;  // pixels
    public final int PLAYER_RESTITUTION = 0;
    public final int PLAYER_FRICTION = 0;
    public final int PLAYER_DENSITY = 10;
    public final boolean PLAYER_FIXED_ROTATION = true;
    public final float SENSOR_SCALING = 0.875f;

    public World world;
    public Body playerB2DBody;
    public Sprite boxSprite;
    public Array<Body> tmpBodies;

    public Player(PlayScreen screen) {
        this.world = screen.getWorld();
        definePlayer();
        tmpBodies = new Array<>();
    }

    public void definePlayer() {
        createPlayer();
    }

    private void createPlayer() {
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
        bdef.position.set(300 / PPM, 300 / PPM);
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
        playerShape.setAsBox(PLAYER_WIDTH / PPM, PLAYER_HEIGHT / PPM);

        // Initialize player body
        playerB2DBody = world.createBody(bdef);
        playerB2DBody.setUserData(boxSprite);
        playerB2DBody.createFixture(fdef);

        // Create player's foot sensor
        PolygonShape sensorShape = new PolygonShape();
        sensorShape.setAsBox(PLAYER_WIDTH / PPM, 6 / PPM);
        Vector2[] footCoords = new Vector2[] {
                new Vector2((-PLAYER_WIDTH * SENSOR_SCALING) / PPM, 0),
                new Vector2((PLAYER_WIDTH * SENSOR_SCALING) / PPM, 0),
                new Vector2((-PLAYER_WIDTH * SENSOR_SCALING) / PPM, -PLAYER_HEIGHT * 2 / PPM),
                new Vector2((PLAYER_WIDTH * SENSOR_SCALING) / PPM, -PLAYER_HEIGHT * 2 / PPM)
        };
        sensorShape.set(footCoords);
        fdef.density = 0;
        fdef.shape = sensorShape;
        fdef.isSensor = true;

        playerB2DBody.createFixture(fdef).setUserData("foot sensor");

        // Create player's left sensor
        sensorShape.setAsBox(6 / PPM, PLAYER_HEIGHT / PPM);
        Vector2[] leftCoords = new Vector2[] {
                new Vector2(2 * -PLAYER_WIDTH / PPM, (PLAYER_HEIGHT * SENSOR_SCALING) / PPM),
                new Vector2(0 / PPM, (PLAYER_HEIGHT * SENSOR_SCALING) / PPM),
                new Vector2(2 * -PLAYER_WIDTH / PPM, (-PLAYER_HEIGHT * SENSOR_SCALING) / PPM),
                new Vector2(0 / PPM, (-PLAYER_HEIGHT * SENSOR_SCALING) / PPM)
        };
        sensorShape.set(leftCoords);
        fdef.shape = sensorShape;
        fdef.isSensor = true;

        playerB2DBody.createFixture(fdef).setUserData("left sensor");

        // Create player's right sensor
        sensorShape.setAsBox(6 / PPM, PLAYER_HEIGHT / PPM);
        Vector2[] rightCoords = new Vector2[] {
                new Vector2(0 / PPM, (PLAYER_HEIGHT * SENSOR_SCALING) / PPM),
                new Vector2(2 * PLAYER_WIDTH / PPM, (PLAYER_HEIGHT * SENSOR_SCALING) / PPM),
                new Vector2(0 / PPM, (-PLAYER_HEIGHT * SENSOR_SCALING) / PPM),
                new Vector2(2 * PLAYER_WIDTH / PPM, (-PLAYER_HEIGHT * SENSOR_SCALING) / PPM)
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
