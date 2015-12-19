package com.develorain.game.Tools;

import com.badlogic.gdx.physics.box2d.*;

import static com.develorain.game.Illumination.*;
import static com.develorain.game.Tools.PlayerController.SLOW_MOTION_MODE;

public abstract class BodyBuilder {
    public static Body createBox(World world, Object userDataObject, float x, float y, float width, float height, String colour, float density, boolean fixedRotation) {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape enemyShape = new PolygonShape();

        bdef.position.set(x / PPM, y / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.fixedRotation = fixedRotation;

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
            case "none":
                fdef.filter.categoryBits = PLAYER_BIT;
                fdef.filter.maskBits = DEFAULT_SLOPE_BIT | UNCLIMBABLE_DEFAULT_SLOPE_BIT | DEFAULT_ENEMY_BIT | END_SLOPE_BIT;

                if (!SLOW_MOTION_MODE) {
                    fdef.filter.maskBits |= NORMAL_SLOPE_BIT | UNCLIMBABLE_NORMAL_SLOPE_BIT | NORMAL_ENEMY_BIT;
                } else {
                    fdef.filter.maskBits |= ALTERNATE_SLOPE_BIT | UNCLIMBABLE_ALTERNATE_SLOPE_BIT | ALTERNATE_ENEMY_BIT;
                }
                break;
        }

        fdef.filter.maskBits |= DEFAULT_SLOPE_BIT | NORMAL_SLOPE_BIT | ALTERNATE_SLOPE_BIT | BOUNDARY_SLOPE_BIT | PLAYER_BIT;

        enemyShape.setAsBox(width / PPM, height / PPM);

        Body body = world.createBody(bdef);
        Fixture fixture = body.createFixture(fdef);
        fixture.setUserData(userDataObject);
        return body;
    }
}
