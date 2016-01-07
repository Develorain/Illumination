package com.develorain.game.tools;

import com.badlogic.gdx.physics.box2d.*;
import com.develorain.game.entities.EntityType;

import static com.develorain.game.Illumination.*;
import static com.develorain.game.tools.PlayerController.SLOW_MOTION_MODE;

public class BodyFactory {
    private static final int DEFAULT_RESTITUTION = 0;
    private static final int DEFAULT_FRICTION = 0;
    private static final boolean DEFAULT_FIXED_ROTATION = true;

    public static Body createBoxBody(World world, Object entityInstanceForFixture, float x, float y, float width, float height,
                                     EntityType type, float density) {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);

        bdef.position.set(x, y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.fixedRotation = DEFAULT_FIXED_ROTATION;

        fdef.shape = shape;
        fdef.restitution = DEFAULT_RESTITUTION;
        fdef.density = density;
        fdef.friction = DEFAULT_FRICTION;

        switch (type) {
            case WHITE_ENEMY:
                fdef.filter.categoryBits = WHITE_ENEMY_BIT;
                fdef.filter.maskBits = WHITE_ENEMY_BIT | BLUE_ENEMY_BIT | RED_ENEMY_BIT | WHITE_LINE_BIT |
                        BLUE_LINE_BIT | RED_LINE_BIT | BOUNDARY_LINE_BIT | PLAYER_BIT;
                break;
            case BLUE_ENEMY:
                fdef.filter.categoryBits = BLUE_ENEMY_BIT;
                fdef.filter.maskBits = WHITE_ENEMY_BIT | BLUE_ENEMY_BIT | WHITE_LINE_BIT | BLUE_LINE_BIT |
                        RED_LINE_BIT | BOUNDARY_LINE_BIT | PLAYER_BIT;
                break;
            case RED_ENEMY:
                fdef.filter.categoryBits = RED_ENEMY_BIT;
                fdef.filter.maskBits = WHITE_ENEMY_BIT | RED_ENEMY_BIT | WHITE_LINE_BIT | BLUE_LINE_BIT |
                        RED_LINE_BIT | BOUNDARY_LINE_BIT | PLAYER_BIT;
                break;
            case PLAYER:
                fdef.filter.categoryBits = PLAYER_BIT;
                fdef.filter.maskBits = WHITE_LINE_BIT | UNCLIMBABLE_LINE_BIT | WHITE_ENEMY_BIT | END_LINE_BIT;

                if (!SLOW_MOTION_MODE) {
                    fdef.filter.maskBits |= BLUE_LINE_BIT | BLUE_ENEMY_BIT;
                } else {
                    fdef.filter.maskBits |= RED_LINE_BIT | RED_ENEMY_BIT;
                }
                break;
        }

        Body body = world.createBody(bdef);
        Fixture fixture = body.createFixture(fdef);
        fixture.setUserData(entityInstanceForFixture);
        return body;
    }
}
