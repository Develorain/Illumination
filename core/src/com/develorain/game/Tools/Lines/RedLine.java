package com.develorain.game.Tools.Lines;

import com.badlogic.gdx.maps.MapObject;
import com.develorain.game.Tools.Level;

import static com.develorain.game.Illumination.RED_LINE_BIT;
import static com.develorain.game.Illumination.UNCLIMBABLE_RED_LINE_BIT;

public class RedLine extends Line {
    public RedLine(MapObject object, Level level, boolean climbable) {
        super(object, level);
        fixture.setUserData(this);

        if (climbable) {
            setCategoryFilter(RED_LINE_BIT);
        } else {
            setCategoryFilter(UNCLIMBABLE_RED_LINE_BIT);
        }

    }
}
