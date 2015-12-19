package com.develorain.game.Tools.Lines;

import com.badlogic.gdx.maps.MapObject;
import com.develorain.game.Tools.Level;

import static com.develorain.game.Illumination.BLUE_LINE_BIT;
import static com.develorain.game.Illumination.UNCLIMBABLE_BLUE_LINE_BIT;

public class BlueLine extends Line {
    public BlueLine(MapObject object, Level level, boolean climbable) {
        super(object, level);
        fixture.setUserData(this);

        if (climbable) {
            setCategoryFilter(BLUE_LINE_BIT);
        } else {
            setCategoryFilter(UNCLIMBABLE_BLUE_LINE_BIT);
        }
    }
}
