package com.develorain.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.develorain.game.Illumination;

public class Cubey extends Sprite {
    public World world;
    public Body b2body;
    public Sprite boxSprite;
    Array<Body> tmpBodies;

    public Cubey(World world) {
        this.world = world;
        definePlayer();
        tmpBodies = new Array<Body>();
    }

    public void definePlayer() {
        boxSprite = new Sprite(new Texture("cubey.png"));
        boxSprite.setSize(0.5f, 0.5f);

        BodyDef bdef = new BodyDef();
        bdef.position.set(100 / Illumination.PPM, 100 / Illumination.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        b2body.setUserData(boxSprite);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(boxSprite.getTexture().getWidth() / Illumination.PPM, boxSprite.getTexture().getHeight() / Illumination.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    public void drawPlayer(Batch batch) {
        batch.begin();
        world.getBodies(tmpBodies);
        for(Body body: tmpBodies) {
            if(body.getUserData() != null && body.getUserData() instanceof Sprite) {
                Sprite sprite = (Sprite) body.getUserData();

                // Sets the texture to the center of the player
                sprite.setPosition(body.getPosition().x - boxSprite.getTexture().getWidth() / Illumination.PPM,
                        body.getPosition().y - boxSprite.getTexture().getHeight() / Illumination.PPM);

                sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                sprite.draw(batch);
            }
        }
        batch.end();
    }
}
