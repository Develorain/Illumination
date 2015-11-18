package com.develorain.game.Tools;

import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.develorain.game.Screens.PlayScreen;
import com.develorain.game.Sprites.Player;
import com.develorain.game.Sprites.Walker;
import com.develorain.game.Tools.Slopes.*;

import java.util.ArrayList;

public class B2WorldCreator {
    private Player player;
    private ArrayList<Walker> walkers;

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

        for (MapObject object : map.getLayers().get(3).getObjects().getByType(PolylineMapObject.class)) {
            new BoundarySlope(screen, object);
        }

        walkers = new ArrayList<>();
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            walkers.add(new Walker(screen, rect.getX(), rect.getY()));
        }

        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            LightBuilder.createConeLight(rayHandler, rect.getX(), rect.getY(), Color.ROYAL, 8, 270, 30);
            LightBuilder.createConeLight(rayHandler, rect.getX(), rect.getY(), Color.ROYAL, 4, 270, 30);
        }

        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            player = new Player(screen, rayHandler, rect.getX(), rect.getY(), "down");
        }

        for (MapObject object : map.getLayers().get(7).getObjects().getByType(PolylineMapObject.class)) {
            new EndSlope(screen, object);
        }
    }

    public ArrayList<Walker> getWalkers() {
        return walkers;
    }

    public Player getPlayer() {
        return player;
    }
}
