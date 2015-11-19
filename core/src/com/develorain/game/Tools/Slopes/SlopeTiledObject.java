package com.develorain.game.Tools.Slopes;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.develorain.game.Screens.PlayScreen;
import com.develorain.game.Tools.LevelCreator;

import static com.develorain.game.Illumination.PPM;

public class SlopeTiledObject {
    public World world;
    public TiledMap map;
    public Body body;
    public Shape line;
    public PlayScreen screen;
    public MapObject object;
    public Fixture fixture;

    public SlopeTiledObject(PlayScreen screen, MapObject object, LevelCreator levelCreator) {
        this.object = object;
        this.screen = screen;
        this.world = levelCreator.getWorld();
        this.map = levelCreator.getTiledMap();

        line = createPolyline((PolylineMapObject) object);

        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bdef);
        fixture = body.createFixture(line, 1.0f);

        line.dispose();
    }

    private static ChainShape createPolyline(PolylineMapObject polyline) {
        float[] vertices = polyline.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < worldVertices.length; i++) {
            worldVertices[i] = new Vector2(vertices[i * 2] / PPM, vertices[i * 2 + 1] / PPM);
        }

        ChainShape cs = new ChainShape();
        cs.createChain(worldVertices);
        return cs;
    }

    public void setCategoryFilter(short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }
}
