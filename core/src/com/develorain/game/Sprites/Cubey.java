package com.develorain.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.develorain.game.Illumination;

public class Cubey extends Sprite {
    public World world;
    public Body b2body;

    public Cubey(World world) {
        this.world = world;
        defineCubey();
    }

    public void defineCubey() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(100 / Illumination.PPM, 100 / Illumination.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / Illumination.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
