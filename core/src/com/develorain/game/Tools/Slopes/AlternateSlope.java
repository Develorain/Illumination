package com.develorain.game.Tools.Slopes;

import com.badlogic.gdx.maps.MapObject;
import com.develorain.game.Tools.Level;

import static com.develorain.game.Illumination.ALTERNATE_SLOPE_BIT;
import static com.develorain.game.Illumination.UNCLIMBABLE_ALTERNATE_SLOPE_BIT;

public class AlternateSlope extends SlopeTiledObject {
    public AlternateSlope(MapObject object, Level level, boolean climbable) {
        super(object, level);
        fixture.setUserData(this);

        if (climbable) {
            setCategoryFilter(ALTERNATE_SLOPE_BIT);
        } else {
            setCategoryFilter(UNCLIMBABLE_ALTERNATE_SLOPE_BIT);
        }

    }
}
