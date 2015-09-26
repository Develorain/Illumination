package com.develorain.game.Tools;

import box2dLight.ConeLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;


public class LightBuilder {
    public static ConeLight createConeLight(RayHandler rayHandler, Body body, Color c, float dist, float dir, float cone) {
        ConeLight cl = new ConeLight(rayHandler, 120, c, dist, 0, 0, dir, cone);
        cl.setSoftnessLength(0f);
        cl.attachToBody(body);
        cl.setXray(true);
        return cl;
    }

    public static ConeLight createConeLight(RayHandler rayHandler, float x, float y, Color c, float dist, float dir, float cone) {
        return null;
    }
}
