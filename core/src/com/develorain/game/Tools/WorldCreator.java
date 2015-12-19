package com.develorain.game.Tools;

import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.develorain.game.Entities.*;
import com.develorain.game.Tools.Slopes.*;

import java.util.ArrayList;

public class WorldCreator {
    private Player player;
    private ArrayList<Enemy> enemies = new ArrayList<>();

    public WorldCreator(RayHandler rayHandler, Level level) {
        TiledMap map = level.getTiledMap();

        for (MapObject object : map.getLayers().get(0).getObjects().getByType(PolylineMapObject.class)) {
            new DefaultSlope(object, level, true);
        }

        for (MapObject object : map.getLayers().get(1).getObjects().getByType(PolylineMapObject.class)) {
            new NormalSlope(object, level, true);
        }

        for (MapObject object : map.getLayers().get(2).getObjects().getByType(PolylineMapObject.class)) {
            new AlternateSlope(object, level, true);
        }

        for (MapObject object : map.getLayers().get(3).getObjects().getByType(PolylineMapObject.class)) {
            new BoundarySlope(object, level);
        }

        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            enemies.add(new Walker(rect.getX(), rect.getY(), level, EntityType.WHITE_ENEMY));
        }

        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            enemies.add(new Walker(rect.getX(), rect.getY(), level, EntityType.BLUE_ENEMY));
        }

        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            enemies.add(new Walker(rect.getX(), rect.getY(), level, EntityType.RED_ENEMY));
        }

        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            LightBuilder.createConeLight(rayHandler, rect.getX(), rect.getY(), Color.BLUE, 8, 270, 30);
            LightBuilder.createConeLight(rayHandler, rect.getX(), rect.getY(), Color.WHITE, 8, 270, 30);
        }

        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            player = new Player(rayHandler, rect.getX(), rect.getY(), level);
        }

        for (MapObject object : map.getLayers().get(9).getObjects().getByType(PolylineMapObject.class)) {
            new EndSlope(object, level);
        }

        for (MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            enemies.add(new Sprinter(rect.getX(), rect.getY(), level, EntityType.WHITE_ENEMY));
        }

        for (MapObject object : map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            enemies.add(new Sprinter(rect.getX(), rect.getY(), level, EntityType.BLUE_ENEMY));
        }

        for (MapObject object : map.getLayers().get(12).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            enemies.add(new Sprinter(rect.getX(), rect.getY(), level, EntityType.RED_ENEMY));
        }

        for (MapObject object : map.getLayers().get(13).getObjects().getByType(PolylineMapObject.class)) {
            new DefaultSlope(object, level, false);
        }

        for (MapObject object : map.getLayers().get(14).getObjects().getByType(PolylineMapObject.class)) {
            new NormalSlope(object, level, false);
        }

        for (MapObject object : map.getLayers().get(15).getObjects().getByType(PolylineMapObject.class)) {
            new AlternateSlope(object, level, false);
        }

        for (MapObject object : map.getLayers().get(16).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            enemies.add(new Exploder(rect.getX(), rect.getY(), level, EntityType.WHITE_ENEMY));
        }

        for (MapObject object : map.getLayers().get(17).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            enemies.add(new Exploder(rect.getX(), rect.getY(), level, EntityType.BLUE_ENEMY));
        }

        for (MapObject object : map.getLayers().get(18).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            enemies.add(new Exploder(rect.getX(), rect.getY(), level, EntityType.RED_ENEMY));
        }
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public Player getPlayer() {
        return player;
    }
}
