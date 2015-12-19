package com.develorain.game.Tools.Slopes;

import com.badlogic.gdx.maps.MapObject;
import com.develorain.game.Tools.Level;

import static com.develorain.game.Illumination.BLUE_LINE_BIT;
import static com.develorain.game.Illumination.UNCLIMBABLE_BLUE_LINE_BIT;

public class NormalSlope extends SlopeTiledObject {
    public NormalSlope(MapObject object, Level level, boolean climbable) {
        super(object, level);
        fixture.setUserData(this);

        if (climbable) {
            setCategoryFilter(BLUE_LINE_BIT);
        } else {
            setCategoryFilter(UNCLIMBABLE_BLUE_LINE_BIT);
        }
    }
}
