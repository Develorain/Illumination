package com.develorain.game.Tools.Slopes;

import com.badlogic.gdx.maps.MapObject;
import com.develorain.game.Tools.Level;

import static com.develorain.game.Illumination.END_SLOPE_BIT;

public class EndSlope extends SlopeTiledObject {
    public EndSlope(MapObject object, Level level) {
        super(object, level);
        fixture.setUserData(this);
        setCategoryFilter(END_SLOPE_BIT);
    }
}
