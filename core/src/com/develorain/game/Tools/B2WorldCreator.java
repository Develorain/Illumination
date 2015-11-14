package com.develorain.game.Tools;

import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.develorain.game.Screens.PlayScreen;
import com.develorain.game.Sprites.SampleEnemy;
import com.develorain.game.Tools.Slopes.AlternateSlope;
import com.develorain.game.Tools.Slopes.DefaultSlope;
import com.develorain.game.Tools.Slopes.NormalSlope;

import java.util.ArrayList;

public class B2WorldCreator {
    private ArrayList<SampleEnemy> sampleEnemies;

    public B2WorldCreator(PlayScreen screen, RayHandler rayHandler) {
        TiledMap map = screen.getTiledMap();

        for (MapObject object : map.getLayers().get(0).getObjects().getByType(PolylineMapObject.class)) {
            new DefaultSlope(screen, object);
        }

        for (MapObject object : map.getLayers().get(1).getObjects().getByType(PolylineMapObject.class)) {
            new NormalSlope(screen, object);
        }

        for (MapObject object : map.getLayers().get(2).getObjects().getByType(PolylineMapObject.class)) {
            new AlternateSlope(screen, object);
        }

        sampleEnemies = new ArrayList<>();
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            sampleEnemies.add(new SampleEnemy(screen, rect.getX(), rect.getY()));
        }

        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            LightBuilder.createConeLight(rayHandler, rect.getX(), rect.getY(), Color.CHARTREUSE, 8, 270, 30);
            LightBuilder.createConeLight(rayHandler, rect.getX(), rect.getY(), Color.YELLOW, 4, 270, 30);
        }
    }

    public ArrayList<SampleEnemy> getSampleEnemies() {
        return sampleEnemies;
    }
}
