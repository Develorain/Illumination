package com.develorain.game.tools;

import com.badlogic.gdx.physics.box2d.*;
import com.develorain.game.entities.EntityType;

import static com.develorain.game.tools.GameInputHandler.SLOW_MOTION_MODE;

public class BodyFactory {
    private static final int DEFAULT_RESTITUTION = 0;
    private static final int DEFAULT_FRICTION = 0;
    private static final boolean DEFAULT_FIXED_ROTATION = true;

    // entityRefenceForFixture is only used in reversing velocity of enemies. refactor this
    public static Body createBoxBody(World world, Object entityReferenceForFixture, float x, float y, float width, float height,
                                     float density, boolean makeFDef, EntityType type, boolean isStatic) {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);

        bdef.position.set(x, y);
        if (isStatic) {
            bdef.type = BodyDef.BodyType.StaticBody;
        } else {
            bdef.type = BodyDef.BodyType.DynamicBody;
        }
        bdef.fixedRotation = DEFAULT_FIXED_ROTATION;

        Body body = world.createBody(bdef);

        if (makeFDef) {
            FixtureDef fdef = new FixtureDef();
            fdef.shape = shape;
            fdef.restitution = DEFAULT_RESTITUTION;
            fdef.density = density;
            fdef.friction = DEFAULT_FRICTION;

            switch (type) {
                case WHITE_ENEMY:
                    fdef.filter.categoryBits = WorldContactListener.WHITE_ENEMY_BIT;
                    fdef.filter.maskBits = WorldContactListener.WHITE_ENEMY_BIT | WorldContactListener.BLUE_ENEMY_BIT | WorldContactListener.RED_ENEMY_BIT | WorldContactListener.WHITE_LINE_BIT |
                            WorldContactListener.BLUE_LINE_BIT | WorldContactListener.RED_LINE_BIT | WorldContactListener.BOUNDARY_LINE_BIT | WorldContactListener.PLAYER_BIT;
                    break;
                case BLUE_ENEMY:
                    fdef.filter.categoryBits = WorldContactListener.BLUE_ENEMY_BIT;
                    fdef.filter.maskBits = WorldContactListener.WHITE_ENEMY_BIT | WorldContactListener.BLUE_ENEMY_BIT | WorldContactListener.WHITE_LINE_BIT | WorldContactListener.BLUE_LINE_BIT |
                            WorldContactListener.RED_LINE_BIT | WorldContactListener.BOUNDARY_LINE_BIT | WorldContactListener.PLAYER_BIT;
                    break;
                case RED_ENEMY:
                    fdef.filter.categoryBits = WorldContactListener.RED_ENEMY_BIT;
                    fdef.filter.maskBits = WorldContactListener.WHITE_ENEMY_BIT | WorldContactListener.RED_ENEMY_BIT | WorldContactListener.WHITE_LINE_BIT | WorldContactListener.BLUE_LINE_BIT |
                            WorldContactListener.RED_LINE_BIT | WorldContactListener.BOUNDARY_LINE_BIT | WorldContactListener.PLAYER_BIT;
                    break;
                case PLAYER:
                    fdef.filter.categoryBits = WorldContactListener.PLAYER_BIT;
                    fdef.filter.maskBits = WorldContactListener.WHITE_LINE_BIT | WorldContactListener.UNCLIMBABLE_LINE_BIT | WorldContactListener.WHITE_ENEMY_BIT | WorldContactListener.END_LINE_BIT | WorldContactListener.SCRIPTED_EVENT_TRIGGER_BIT;

                    if (!SLOW_MOTION_MODE) {
                        fdef.filter.maskBits |= WorldContactListener.BLUE_LINE_BIT | WorldContactListener.BLUE_ENEMY_BIT;
                    } else {
                        fdef.filter.maskBits |= WorldContactListener.RED_LINE_BIT | WorldContactListener.RED_ENEMY_BIT;
                    }
                    break;
            }

            Fixture fixture = body.createFixture(fdef);
            fixture.setUserData(entityReferenceForFixture);
        }

        return body;
    }
}
