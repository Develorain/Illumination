package com.develorain.game.tools;

import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.develorain.game.Illumination;
import com.develorain.game.entities.*;
import com.develorain.game.tools.lines.*;

import java.util.ArrayList;

public class WorldInitializer {
    private Player player;
    private ArrayList<Enemy>[] enemies = (ArrayList<Enemy>[]) new ArrayList[4];
    //private ArrayList<Enemy> enemies = new ArrayList<>();
    private Level level;

    public WorldInitializer(RayHandler rayHandler, Level level) {
        this.level = level;

        for (int i = 0; i < enemies.length; i++) {
            enemies[i] = new ArrayList();
        }

        TiledMap map = level.getTiledMap();

        for (MapObject object : map.getLayers().get(0).getObjects().getByType(PolylineMapObject.class)) {
            new WhiteLine(object, level);
        }

        for (MapObject object : map.getLayers().get(1).getObjects().getByType(PolylineMapObject.class)) {
            new BlueLine(object, level);
        }

        for (MapObject object : map.getLayers().get(2).getObjects().getByType(PolylineMapObject.class)) {
            new RedLine(object, level);
        }

        for (MapObject object : map.getLayers().get(3).getObjects().getByType(PolylineMapObject.class)) {
            new BoundaryLine(object, level);
        }

        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            getWalkers().add(new Walker(rect.getX(), rect.getY(), level, EntityType.WHITE_ENEMY));
        }

        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            getWalkers().add(new Walker(rect.getX(), rect.getY(), level, EntityType.BLUE_ENEMY));
        }

        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            getWalkers().add(new Walker(rect.getX(), rect.getY(), level, EntityType.RED_ENEMY));
        }

        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            LightFactory.createConeLight(rayHandler, rect.getX(), rect.getY(), Color.BLUE, 8, 270, 30);
            LightFactory.createConeLight(rayHandler, rect.getX(), rect.getY(), Color.WHITE, 8, 270, 30);
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
            getSprinters().add(new Sprinter(rect.getX(), rect.getY(), level, EntityType.WHITE_ENEMY));
        }

        for (MapObject object : map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            getSprinters().add(new Sprinter(rect.getX(), rect.getY(), level, EntityType.BLUE_ENEMY));
        }

        for (MapObject object : map.getLayers().get(12).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            getSprinters().add(new Sprinter(rect.getX(), rect.getY(), level, EntityType.RED_ENEMY));
        }

        for (MapObject object : map.getLayers().get(13).getObjects().getByType(PolylineMapObject.class)) {
            new UnclimbableLine(object, level);
        }

        for (MapObject object : map.getLayers().get(14).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            getExploders().add(new Exploder(rect.getX(), rect.getY(), level, EntityType.WHITE_ENEMY));
        }

        for (MapObject object : map.getLayers().get(15).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            getExploders().add(new Exploder(rect.getX(), rect.getY(), level, EntityType.BLUE_ENEMY));
        }

        for (MapObject object : map.getLayers().get(16).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            getExploders().add(new Exploder(rect.getX(), rect.getY(), level, EntityType.RED_ENEMY));
        }

        for (MapObject object : map.getLayers().get(17).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            Body body = BodyFactory.createBoxBody(level.getWorld(), Rectangle.class, 0, 0,
                    rect.width / Illumination.PPM, rect.height / Illumination.PPM, 0, false, null, true);
            FixtureDef fdef = SensorFactory.createSensorFixture(rect.getX(), rect.getY(), rect.width, rect.height, EntityType.TRIGGER_SENSOR);
            body.createFixture(fdef);
        }
    }

    public ArrayList<Enemy>[] getEnemies() {
        return enemies;
    }

    public ArrayList<Walker> getWalkers() {
        return (ArrayList) enemies[0];
    }

    public ArrayList<Sprinter> getSprinters() {
        return (ArrayList) enemies[1];
    }

    public ArrayList<Exploder> getExploders() {
        return (ArrayList) enemies[2];
    }

    public ArrayList<Projectile> getProjectiles() {
        return (ArrayList) enemies[3];
    }

    public Player getPlayer() {
        return player;
    }

    public void createProjectile(float x, float y, EntityType type) {
        Projectile projectile = new Projectile(x, y, level, type);
        getProjectiles().add(projectile);
    }
}
