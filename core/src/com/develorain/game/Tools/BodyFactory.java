package com.develorain.game.Tools;

import com.badlogic.gdx.physics.box2d.*;

import static com.develorain.game.Illumination.*;
import static com.develorain.game.Tools.PlayerController.SLOW_MOTION_MODE;

public class BodyFactory {
    public static Body createBox(World world, Object userDataObject, float x, float y, float width, float height, String colour, float density, boolean fixedRotation) {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / PPM, height / PPM);

        bdef.position.set(x / PPM, y / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.fixedRotation = fixedRotation;

        fdef.shape = shape;
        fdef.restitution = 0; // TODO: REMOVE MAGICAL NUMBER
        fdef.density = density;
        fdef.friction = 0; // TODO: REMOVE MAGICAL NUMBER

        switch (colour) {
            case "white":
                fdef.filter.categoryBits = WHITE_ENEMY_BIT;
                fdef.filter.maskBits = WHITE_ENEMY_BIT | BLUE_ENEMY_BIT | RED_ENEMY_BIT;
                fdef.filter.maskBits |= WHITE_SLOPE_BIT | BLUE_SLOPE_BIT | RED_SLOPE_BIT | BOUNDARY_SLOPE_BIT | PLAYER_BIT;
                break;
            case "blue":
                fdef.filter.categoryBits = BLUE_ENEMY_BIT;
                fdef.filter.maskBits = WHITE_ENEMY_BIT | BLUE_ENEMY_BIT;
                fdef.filter.maskBits |= WHITE_SLOPE_BIT | BLUE_SLOPE_BIT | RED_SLOPE_BIT | BOUNDARY_SLOPE_BIT | PLAYER_BIT;
                break;
            case "red":
                fdef.filter.categoryBits = RED_ENEMY_BIT;
                fdef.filter.maskBits = WHITE_ENEMY_BIT | RED_ENEMY_BIT;
                fdef.filter.maskBits |= WHITE_SLOPE_BIT | BLUE_SLOPE_BIT | RED_SLOPE_BIT | BOUNDARY_SLOPE_BIT | PLAYER_BIT;
                break;
            case "none":
                fdef.filter.categoryBits = PLAYER_BIT;
                fdef.filter.maskBits = WHITE_SLOPE_BIT | UNCLIMBABLE_WHITE_SLOPE_BIT | WHITE_ENEMY_BIT | END_SLOPE_BIT;

                if (!SLOW_MOTION_MODE) {
                    fdef.filter.maskBits |= BLUE_SLOPE_BIT | UNCLIMBABLE_BLUE_SLOPE_BIT | BLUE_ENEMY_BIT;
                } else {
                    fdef.filter.maskBits |= RED_SLOPE_BIT | UNCLIMBABLE_RED_SLOPE_BIT | RED_ENEMY_BIT;
                }

                break;
        }

        Body body = world.createBody(bdef);
        Fixture fixture = body.createFixture(fdef);
        fixture.setUserData(userDataObject);
        return body;
    }
}
