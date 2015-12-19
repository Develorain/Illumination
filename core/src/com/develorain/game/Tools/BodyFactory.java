package com.develorain.game.Tools;

import com.badlogic.gdx.physics.box2d.*;
import com.develorain.game.Entities.EntityType;

import static com.develorain.game.Illumination.*;
import static com.develorain.game.Tools.PlayerController.SLOW_MOTION_MODE;

public class BodyFactory {
    public static Body createBoxBody(World world, Object userDataObject, float x, float y, float width, float height, EntityType type, float density, boolean fixedRotation) {
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
        fdef.friction = 0;    // TODO: REMOVE MAGICAL NUMBER

        switch (type) {
            case WHITE_ENEMY:
                fdef.filter.categoryBits = WHITE_ENEMY_BIT;
                fdef.filter.maskBits = WHITE_ENEMY_BIT | BLUE_ENEMY_BIT | RED_ENEMY_BIT;
                fdef.filter.maskBits |= WHITE_LINE_BIT | BLUE_LINE_BIT | RED_LINE_BIT | BOUNDARY_LINE_BIT | PLAYER_BIT;
                break;
            case BLUE_ENEMY:
                fdef.filter.categoryBits = BLUE_ENEMY_BIT;
                fdef.filter.maskBits = WHITE_ENEMY_BIT | BLUE_ENEMY_BIT;
                fdef.filter.maskBits |= WHITE_LINE_BIT | BLUE_LINE_BIT | RED_LINE_BIT | BOUNDARY_LINE_BIT | PLAYER_BIT;
                break;
            case RED_ENEMY:
                fdef.filter.categoryBits = RED_ENEMY_BIT;
                fdef.filter.maskBits = WHITE_ENEMY_BIT | RED_ENEMY_BIT;
                fdef.filter.maskBits |= WHITE_LINE_BIT | BLUE_LINE_BIT | RED_LINE_BIT | BOUNDARY_LINE_BIT | PLAYER_BIT;
                break;
            case PLAYER:
                fdef.filter.categoryBits = PLAYER_BIT;
                fdef.filter.maskBits = WHITE_LINE_BIT | UNCLIMBABLE_WHITE_LINE_BIT | WHITE_ENEMY_BIT | END_LINE_BIT;

                if (!SLOW_MOTION_MODE) {
                    fdef.filter.maskBits |= BLUE_LINE_BIT | UNCLIMBABLE_BLUE_LINE_BIT | BLUE_ENEMY_BIT;
                } else {
                    fdef.filter.maskBits |= RED_LINE_BIT | UNCLIMBABLE_RED_LINE_BIT | RED_ENEMY_BIT;
                }

                break;
        }

        Body body = world.createBody(bdef);
        Fixture fixture = body.createFixture(fdef);
        fixture.setUserData(userDataObject);
        return body;
    }
}
