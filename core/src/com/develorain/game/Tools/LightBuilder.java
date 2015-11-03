package com.develorain.game.Tools;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;

import static com.develorain.game.Illumination.*;
import static com.develorain.game.Tools.PlayerController.SLOWMOTION_MODE;


public class LightBuilder {
    public static PointLight createPointLight(RayHandler rayHandler, Body body, Color c, float distance) {
        PointLight pl = new PointLight(rayHandler, 120, c, distance, 0, 0);
        pl.setSoftnessLength(0.5f);
        pl.attachToBody(body);
        pl.setXray(false);

        if(!SLOWMOTION_MODE) {
            // SET LIGHT CONTACT FILTER
        } else {
            // SET LIGHT CONTACT FILTER
        }

        pl.setContactFilter(WHITESLOPE_BIT, (short) 2, (short) 4); // 2 and 4 are nonsensical numbers, but them seem to work for some reason

        return pl;
    }

    public static PointLight createPointLight(RayHandler rayHandler, float x, float y, Color c, float distance) {
        PointLight pl = new PointLight(rayHandler, 120, c, distance, x / PPM, y / PPM);
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
        ConeLight cl = new ConeLight(rayHandler, 120, c, dist, x / PPM, y / PPM, direction, cone);
        cl.setSoftnessLength(0f); // sets intensity of shadows, higher number = less shadows
        cl.setXray(false);        // changes light from being in background to foreground. when x-ray is true, shadows are not formed
        return cl;
    }
}
