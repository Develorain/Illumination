package com.develorain.game.Tools.Slopes;

import com.badlogic.gdx.maps.MapObject;
import com.develorain.game.Tools.Level;

import static com.develorain.game.Illumination.BOUNDARY_LINE_BIT;

public class BoundarySlope extends SlopeTiledObject {
    public BoundarySlope(MapObject object, Level level) {
        super(object, level);
        fixture.setUserData(this);
        setCategoryFilter(BOUNDARY_LINE_BIT);
    }
}
