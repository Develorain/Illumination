package com.develorain.game.Tools;

import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.develorain.game.Entities.*;
import com.develorain.game.Tools.Lines.*;

import java.util.ArrayList;

public class WorldInitializer {
    private Player player;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private Level level;

    public WorldInitializer(RayHandler rayHandler, Level level) {
        this.level = level;
        TiledMap map = level.getTiledMap();

        for (MapObject object : map.getLayers().get(0).getObjects().getByType(PolylineMapObject.class)) {
            new WhiteLine(object, level, true);
        }

        for (MapObject object : map.getLayers().get(1).getObjects().getByType(PolylineMapObject.class)) {
            new BlueLine(object, level, true);
        }

        for (MapObject object : map.getLayers().get(2).getObjects().getByType(PolylineMapObject.class)) {
            new RedLine(object, level, true);
        }

        for (MapObject object : map.getLayers().get(3).getObjects().getByType(PolylineMapObject.class)) {
            new BoundaryLine(object, level);
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
            new EndLine(object, level);
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
            new WhiteLine(object, level, false);
        }

        for (MapObject object : map.getLayers().get(14).getObjects().getByType(PolylineMapObject.class)) {
            new BlueLine(object, level, false);
        }

        for (MapObject object : map.getLayers().get(15).getObjects().getByType(PolylineMapObject.class)) {
            new RedLine(object, level, false);
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

    public void createProjectile(float x, float y, EntityType type) {
        Projectile projectile = new Projectile(x, y, level, type);
        enemies.add(projectile);
    }
}
