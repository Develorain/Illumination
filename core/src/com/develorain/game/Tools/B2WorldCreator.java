package com.develorain.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.develorain.game.Screens.PlayScreen;
import com.develorain.game.Tools.Slopes.AlternateSlope;
import com.develorain.game.Tools.Slopes.DefaultSlope;
import com.develorain.game.Tools.Slopes.NormalSlope;

public class B2WorldCreator {

    public B2WorldCreator(PlayScreen screen) {
        TiledMap map = screen.getTiledMap();

        for(MapObject object : map.getLayers().get(0).getObjects().getByType(PolylineMapObject.class)) {
            new DefaultSlope(screen, object);
        }

        for(MapObject object : map.getLayers().get(1).getObjects().getByType(PolylineMapObject.class)) {
            new NormalSlope(screen, object);
        }

        for(MapObject object : map.getLayers().get(2).getObjects().getByType(PolylineMapObject.class)) {
            new AlternateSlope(screen, object);
        }
    }
}
