package com.develorain.game.tools;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.develorain.game.Illumination;

import static com.develorain.game.tools.GameInputHandler.SLOW_MOTION_MODE;

public class LightFactory {
    public static PointLight createPointLight(RayHandler rayHandler, Body body, Color c, float distance) {
        PointLight pl = new PointLight(rayHandler, 120, c, distance, 0, 0);
        pl.setSoftnessLength(0.5f);
        pl.attachToBody(body);
        pl.setXray(false);

        Filter filter = new Filter();
        filter.maskBits = WorldContactListener.WHITE_LINE_BIT | WorldContactListener.UNCLIMBABLE_LINE_BIT;

        if (!SLOW_MOTION_MODE) {
            filter.maskBits |= WorldContactListener.BLUE_LINE_BIT;
        } else {
            filter.maskBits |= WorldContactListener.RED_LINE_BIT;
        }

        pl.setContactFilter(filter);

        return pl;
    }

    public static PointLight createPointLight(RayHandler rayHandler, float x, float y, Color c, float distance) {
        PointLight pl = new PointLight(rayHandler, 120, c, distance, x / Illumination.PPM, y / Illumination.PPM);
        pl.setSoftnessLength(0.5f);
        pl.setXray(false);
        return pl;
    }

    public static ConeLight createConeLight(RayHandler rayHandler, Body body, Color c, float dist, float direction, float cone) {
        ConeLight cl = new ConeLight(rayHandler, 120, c, dist, 0, 0, direction, cone);
        cl.setSoftnessLength(0f);
        cl.attachToBody(body);
        cl.setXray(false);
        return cl;
    }

    public static ConeLight createConeLight(RayHandler rayHandler, float x, float y, Color c, float dist, float direction, float cone) {
        ConeLight cl = new ConeLight(rayHandler, 120, c, dist, x / Illumination.PPM, y / Illumination.PPM, direction, cone);
        cl.setSoftnessLength(2f); // sets intensity of shadows, higher number = less shadows
        cl.setXray(false);          // changes light from being in background to foreground. when x-ray is true, shadows are not formed
        return cl;
    }
}
