package com.develorain.game.Tools.Slopes;

import com.badlogic.gdx.maps.MapObject;
import com.develorain.game.Tools.Level;

import static com.develorain.game.Illumination.WHITE_LINE_BIT;
import static com.develorain.game.Illumination.UNCLIMBABLE_WHITE_LINE_BIT;

public class DefaultSlope extends SlopeTiledObject {
    public DefaultSlope(MapObject object, Level level, boolean climbable) {
        super(object, level);
        fixture.setUserData(this);
        if (climbable) {
            setCategoryFilter(WHITE_LINE_BIT);
        } else {
            setCategoryFilter(UNCLIMBABLE_WHITE_LINE_BIT);
        }
    }
}
