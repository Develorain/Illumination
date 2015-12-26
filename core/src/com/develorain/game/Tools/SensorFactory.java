package com.develorain.game.Tools;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.develorain.game.Entities.EntityType;

import static com.develorain.game.Illumination.*;
import static com.develorain.game.Tools.PlayerController.SLOW_MOTION_MODE;

public class SensorFactory {
    public static final int PLAYER_WIDTH = 16;     // pixels
    public static final int PLAYER_HEIGHT = 16;    // pixels

    public static FixtureDef createSensorFixture(float sensorWidth, float sensorHeight, EntityType type) {
        FixtureDef fdef = new FixtureDef();
        PolygonShape sensorShape = new PolygonShape();

        fdef.isSensor = true;
        fdef.density = 0f;
        fdef.friction = 0;

        fdef.filter.maskBits = WHITE_LINE_BIT;

        if (!SLOW_MOTION_MODE) {
            fdef.filter.maskBits |= BLUE_LINE_BIT;
        } else {
            fdef.filter.maskBits |= RED_LINE_BIT;
        }

        Vector2[] sensorCoords = new Vector2[]{};
        switch (type) {
            case FOOT_SENSOR:
                sensorCoords = new Vector2[]{
                        new Vector2((-PLAYER_WIDTH * sensorWidth) / PPM, 0),
                        new Vector2((PLAYER_WIDTH * sensorWidth) / PPM, 0),
                        new Vector2((-PLAYER_WIDTH * sensorWidth) / PPM, -PLAYER_HEIGHT * sensorHeight / PPM),
                        new Vector2((PLAYER_WIDTH * sensorWidth) / PPM, -PLAYER_HEIGHT * sensorHeight / PPM)
                };

                fdef.filter.categoryBits = PLAYER_FOOT_SENSOR_BIT;
                break;
            case LEFT_SENSOR:
                sensorCoords = new Vector2[]{
                        new Vector2(sensorWidth * -PLAYER_WIDTH / PPM, (PLAYER_HEIGHT * sensorHeight) / PPM),
                        new Vector2(0 / PPM, (PLAYER_HEIGHT * sensorHeight) / PPM),
                        new Vector2(sensorWidth * -PLAYER_WIDTH / PPM, (-PLAYER_HEIGHT * sensorHeight) / PPM),
                        new Vector2(0 / PPM, (-PLAYER_HEIGHT * sensorHeight) / PPM)
                };

                fdef.filter.categoryBits = PLAYER_LEFT_SENSOR_BIT;
                break;
            case RIGHT_SENSOR:
                sensorCoords = new Vector2[]{
                        new Vector2(0 / PPM, (PLAYER_HEIGHT * sensorHeight) / PPM),
                        new Vector2(sensorWidth * PLAYER_WIDTH / PPM, (PLAYER_HEIGHT * sensorHeight) / PPM),
                        new Vector2(0 / PPM, (-PLAYER_HEIGHT * sensorHeight) / PPM),
                        new Vector2(sensorWidth * PLAYER_WIDTH / PPM, (-PLAYER_HEIGHT * sensorHeight) / PPM)
                };

                fdef.filter.categoryBits = PLAYER_RIGHT_SENSOR_BIT;
                break;
        }

        sensorShape.set(sensorCoords);
        fdef.shape = sensorShape;

        return fdef;
    }
}