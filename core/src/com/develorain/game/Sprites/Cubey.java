package com.develorain.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.develorain.game.Screens.PlayScreen;

import static com.develorain.game.Illumination.PPM;

public class Cubey extends Sprite {
    public final int PLAYER_WIDTH = 16;   // pixels
    public final int PLAYER_HEIGHT = 16;  // pixels
    public final int PLAYER_RESTITUTION = 0;
    public final int PLAYER_DENSITY = 4;
    public final boolean PLAYER_FIXED_ROTATION = true;

    public World world;
    public Body b2body;
    public Sprite boxSprite;
    Array<Body> tmpBodies;

    public Cubey(PlayScreen screen) {
        this.world = screen.getWorld();
        definePlayer();
        tmpBodies = new Array<Body>();
    }

    public void switchBoxSprite() {
        if(PlayScreen.WHITE_MODE) {
            boxSprite.setTexture(new Texture("Graphics/whitecubey.png"));
        }

        if(!PlayScreen.WHITE_MODE) {
            boxSprite.setTexture(new Texture("Graphics/blackcubey.png"));
        }

        //boxSprite.setSize(0.25f, 0.25f);
        //b2body.setUserData(boxSprite);
    }

    public void definePlayer() {
        // Player variable declaration
        BodyDef bdef;
        PolygonShape shape;
        FixtureDef fdef;

        boxSprite = new Sprite(new Texture("Graphics/whitecubey.png"));
        boxSprite.setOrigin(boxSprite.getWidth() / 2, boxSprite.getHeight() / 2);
        boxSprite.setSize(32f / PPM, 32f / PPM);

        // Initialize and define player definition
        bdef = new BodyDef();
        bdef.position.set(100 / PPM, 100 / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.fixedRotation = PLAYER_FIXED_ROTATION;

        // Initialize player shape
        shape = new PolygonShape();

        // Initialize and define player fixture
        fdef = new FixtureDef();
        fdef.restitution = PLAYER_RESTITUTION;
        fdef.density = PLAYER_DENSITY;
        fdef.shape = shape;
        shape.setAsBox(PLAYER_WIDTH / PPM, PLAYER_HEIGHT / PPM);

        // Initializing player body
        b2body = world.createBody(bdef);
        b2body.setUserData(boxSprite);
        b2body.createFixture(fdef);
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
}
