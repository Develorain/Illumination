package com.develorain.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
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
    public final boolean PLAYER_FIXED_ROTATION = false;

    public World world;
    public Body playerB2DBody;
    public Body sensorB2DBody;
    public Sprite boxSprite;
    Array<Body> tmpBodies;

    public Player(PlayScreen screen) {
        this.world = screen.getWorld();
        definePlayer();
        tmpBodies = new Array<Body>();
    }

    public void definePlayer() {
        createPlayer();
        createPlayerCollisionSensor();
    }

    private void createPlayer() {
        // Player variable declaration
        BodyDef playerBodyDef;
        PolygonShape playerShape;
        FixtureDef playerFixtureDef;

        // Initialize player sprite
        boxSprite = new Sprite(new Texture("Graphics/whitecubey.png"));
        boxSprite.setSize(32f / PPM, 32f / PPM);
        boxSprite.setOrigin(boxSprite.getWidth() / 2, boxSprite.getHeight() / 2);

        // Initialize and define player definition
        playerBodyDef = new BodyDef();
        playerBodyDef.position.set(300 / PPM, 300 / PPM);
        playerBodyDef.type = BodyDef.BodyType.DynamicBody;
        playerBodyDef.fixedRotation = PLAYER_FIXED_ROTATION;

        // Initialize player shape
        playerShape = new PolygonShape();

        // Initialize and define player fixture and shape
        playerFixtureDef = new FixtureDef();
        playerFixtureDef.restitution = PLAYER_RESTITUTION;
        playerFixtureDef.density = PLAYER_DENSITY;
        playerFixtureDef.friction = PLAYER_FRICTION;
        playerFixtureDef.shape = playerShape;
        playerShape.setAsBox(PLAYER_WIDTH / PPM, PLAYER_HEIGHT / PPM);

        // Initialize player body
        playerB2DBody = world.createBody(playerBodyDef);
        playerB2DBody.setUserData(boxSprite);
        playerB2DBody.createFixture(playerFixtureDef);
    }

    public void createPlayerCollisionSensor() {
        // Player variable declaration
        BodyDef sensorBodyDef;
        PolygonShape sensorShape;
        FixtureDef sensorFixtureDef;

        // Initialize and define player definition
        sensorBodyDef = new BodyDef();
        sensorBodyDef.type = BodyDef.BodyType.DynamicBody;
        sensorBodyDef.fixedRotation = true;
        sensorBodyDef.position.set(playerB2DBody.getPosition().x, playerB2DBody.getPosition().y);

        // Initialize player shape
        sensorShape = new PolygonShape();

        // Initialize and define player fixture and shape
        sensorFixtureDef = new FixtureDef();
        sensorFixtureDef.shape = sensorShape;
        sensorShape.setAsBox((PLAYER_WIDTH + 1) / PPM, (PLAYER_HEIGHT + 1) / PPM);
        sensorFixtureDef.isSensor = true;

        // Initialize player body
        sensorB2DBody = world.createBody(sensorBodyDef);
        sensorB2DBody.createFixture(sensorFixtureDef);
    }

    public void updateSensorPosition() {

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
