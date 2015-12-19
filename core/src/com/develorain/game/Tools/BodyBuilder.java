package com.develorain.game.Tools;

import com.badlogic.gdx.physics.box2d.*;

import static com.develorain.game.Illumination.*;
import static com.develorain.game.Illumination.PLAYER_BIT;
import static com.develorain.game.Illumination.PPM;

public abstract class BodyBuilder {
    public static Body createBox(World world, Object object, float x, float y, float width, float height, String colour, float density) {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape enemyShape = new PolygonShape();

        bdef.position.set(x / PPM, y / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;

        fdef.shape = enemyShape;
        fdef.restitution = 0;
        fdef.density = density;
        fdef.friction = 0;

        switch (colour) {
            case "white":
                fdef.filter.categoryBits = DEFAULT_ENEMY_BIT;
                fdef.filter.maskBits = DEFAULT_ENEMY_BIT | NORMAL_ENEMY_BIT | ALTERNATE_ENEMY_BIT;
                break;
            case "blue":
                fdef.filter.categoryBits = NORMAL_ENEMY_BIT;
                fdef.filter.maskBits = DEFAULT_ENEMY_BIT | NORMAL_ENEMY_BIT;
                break;
            case "red":
                fdef.filter.categoryBits = ALTERNATE_ENEMY_BIT;
                fdef.filter.maskBits = DEFAULT_ENEMY_BIT | ALTERNATE_ENEMY_BIT;
                break;
        }

        fdef.filter.maskBits |= DEFAULT_SLOPE_BIT | NORMAL_SLOPE_BIT | ALTERNATE_SLOPE_BIT | BOUNDARY_SLOPE_BIT | PLAYER_BIT;

        enemyShape.setAsBox(width / PPM, height / PPM);

        return world.createBody(bdef).createFixture(fdef).setUserData(object);
        b2body = world.createBody(bdef);
        b2body.createFixture(fdef).setUserData(this);
    }
}
