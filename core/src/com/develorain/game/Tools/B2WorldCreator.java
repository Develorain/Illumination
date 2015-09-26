package com.develorain.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.develorain.game.Illumination;
import com.develorain.game.Screens.PlayScreen;

public class B2WorldCreator {

    public B2WorldCreator(PlayScreen screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        // Creates boxes around white tiles
        for(MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Illumination.PPM, (rect.getY() + rect.getHeight() / 2) / Illumination.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Illumination.PPM, rect.getHeight() / 2 / Illumination.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        // Creates boxes around black tiles
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Illumination.PPM, (rect.getY() + rect.getHeight() / 2) / Illumination.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Illumination.PPM, rect.getHeight() / 2 / Illumination.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }
}
