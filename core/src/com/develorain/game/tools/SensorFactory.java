package com.develorain.game.tools;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.develorain.game.entities.EntityType;

import static com.develorain.game.Illumination.*;
import static com.develorain.game.tools.PlayerController.SLOW_MOTION_MODE;

public class SensorFactory {
    public static final int PLAYER_WIDTH = 16;     // pixels
    public static final int PLAYER_HEIGHT = 16;    // pixels

    public static FixtureDef createSensorFixture(float x, float y, float sensorWidth, float sensorHeight, EntityType type) {
        FixtureDef fdef = new FixtureDef();
        PolygonShape sensorShape = new PolygonShape();

        fdef.isSensor = true;
        fdef.density = 0f;
        fdef.friction = 0;

        Vector2[] sensorCoords = new Vector2[]{}; // first is top left, top right, bottom right, bottom left
        switch (type) {
            case FOOT_SENSOR:
                sensorCoords = new Vector2[]{
                        new Vector2((-PLAYER_WIDTH * sensorWidth) / PPM, 0),
                        new Vector2((PLAYER_WIDTH * sensorWidth) / PPM, 0),
                        new Vector2((-PLAYER_WIDTH * sensorWidth) / PPM, -PLAYER_HEIGHT * sensorHeight / PPM),
                        new Vector2((PLAYER_WIDTH * sensorWidth) / PPM, -PLAYER_HEIGHT * sensorHeight / PPM)
                };

                fdef.filter.categoryBits = PLAYER_FOOT_SENSOR_BIT;

                fdef.filter.maskBits = WHITE_LINE_BIT;

                if (!SLOW_MOTION_MODE) {
                    fdef.filter.maskBits |= BLUE_LINE_BIT;
                } else {
                    fdef.filter.maskBits |= RED_LINE_BIT;
                }
                break;
            case LEFT_SENSOR:
                sensorCoords = new Vector2[]{
                        new Vector2(sensorWidth * -PLAYER_WIDTH / PPM, (PLAYER_HEIGHT * sensorHeight) / PPM),
                        new Vector2(0 / PPM, (PLAYER_HEIGHT * sensorHeight) / PPM),
                        new Vector2(sensorWidth * -PLAYER_WIDTH / PPM, (-PLAYER_HEIGHT * sensorHeight) / PPM),
                        new Vector2(0 / PPM, (-PLAYER_HEIGHT * sensorHeight) / PPM)
                };

                fdef.filter.categoryBits = PLAYER_LEFT_SENSOR_BIT;

                fdef.filter.maskBits = WHITE_LINE_BIT;

                if (!SLOW_MOTION_MODE) {
                    fdef.filter.maskBits |= BLUE_LINE_BIT;
                } else {
                    fdef.filter.maskBits |= RED_LINE_BIT;
                }
                break;
            case RIGHT_SENSOR:
                sensorCoords = new Vector2[]{
                        new Vector2(0 / PPM, (PLAYER_HEIGHT * sensorHeight) / PPM),
                        new Vector2(sensorWidth * PLAYER_WIDTH / PPM, (PLAYER_HEIGHT * sensorHeight) / PPM),
                        new Vector2(0 / PPM, (-PLAYER_HEIGHT * sensorHeight) / PPM),
                        new Vector2(sensorWidth * PLAYER_WIDTH / PPM, (-PLAYER_HEIGHT * sensorHeight) / PPM)
                };

                fdef.filter.categoryBits = PLAYER_RIGHT_SENSOR_BIT;

                fdef.filter.maskBits = WHITE_LINE_BIT;

                if (!SLOW_MOTION_MODE) {
                    fdef.filter.maskBits |= BLUE_LINE_BIT;
                } else {
                    fdef.filter.maskBits |= RED_LINE_BIT;
                }
                break;
            case TRIGGER_SENSOR:
                sensorCoords = new Vector2[]{
                        new Vector2(x / PPM, (y + sensorHeight) / PPM),
                        new Vector2((x + sensorWidth) / PPM, (y + sensorHeight) / PPM),
                        new Vector2((x + sensorWidth) / PPM, y / PPM),
                        new Vector2(x / PPM, y / PPM)
                };

                fdef.filter.categoryBits = SCRIPTED_EVENT_TRIGGER_BIT;

                fdef.filter.maskBits = PLAYER_BIT;
                break;
        }

        sensorShape.set(sensorCoords);
        fdef.shape = sensorShape;

        return fdef;
    }
}