package com.develorain.game.Tools.Slopes;

import com.badlogic.gdx.maps.MapObject;
import com.develorain.game.Tools.Level;

import static com.develorain.game.Illumination.ALTERNATE_SLOPE_BIT;

public class AlternateSlope extends SlopeTiledObject {
    public AlternateSlope(MapObject object, Level level) {
        super(object, level);
        fixture.setUserData(this);
        setCategoryFilter(ALTERNATE_SLOPE_BIT);
    }
}
