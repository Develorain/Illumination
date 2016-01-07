package com.develorain.game.tools.lines;

import com.badlogic.gdx.maps.MapObject;
import com.develorain.game.tools.Level;

import static com.develorain.game.Illumination.BOUNDARY_LINE_BIT;

public class BoundaryLine extends Line {
    public BoundaryLine(MapObject object, Level level) {
        super(object, level);
        fixture.setUserData(this);
        setCategoryFilter(BOUNDARY_LINE_BIT);
    }
}
