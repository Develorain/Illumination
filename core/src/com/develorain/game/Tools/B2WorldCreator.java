package com.develorain.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.develorain.game.Screens.PlayScreen;

import static com.develorain.game.Illumination.PPM;

public class B2WorldCreator {

    public B2WorldCreator(PlayScreen screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        /*
        // Creates boxes around square tiles
        for(MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            Body body;

            BodyDef bdef = new BodyDef();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / PPM, (rect.getY() + rect.getHeight() / 2) / PPM);

            body = world.createBody(bdef);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(rect.getWidth() / 2 / PPM, rect.getHeight() / 2 / PPM);

            FixtureDef fdef = new FixtureDef();
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        */

        for(MapObject object : map.getLayers().get(1).getObjects().getByType(PolylineMapObject.class)) {
            Shape line;
            line = createPolyline((PolylineMapObject) object);

            Body body;
            BodyDef bdef = new BodyDef();
            bdef.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(bdef);
            body.createFixture(line, 1.0f);
            line.dispose();
        }
    }

    private static ChainShape createPolyline(PolylineMapObject polyline) {
        float[] vertices = polyline.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for(int i = 0; i < worldVertices.length; i++) {
            worldVertices[i] = new Vector2(vertices[i * 2] / PPM, vertices[i * 2 + 1] / PPM);
        }

        ChainShape cs = new ChainShape();
        cs.createChain(worldVertices);
        return cs;
    }
}
