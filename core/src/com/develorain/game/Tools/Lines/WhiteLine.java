package com.develorain.game.Tools.Lines;

import com.badlogic.gdx.maps.MapObject;
import com.develorain.game.Tools.Level;

import static com.develorain.game.Illumination.WHITE_LINE_BIT;
import static com.develorain.game.Illumination.UNCLIMBABLE_WHITE_LINE_BIT;

public class WhiteLine extends Line {
    public WhiteLine(MapObject object, Level level, boolean climbable) {
        super(object, level);
        fixture.setUserData(this);
        if (climbable) {
            setCategoryFilter(WHITE_LINE_BIT);
        } else {
            setCategoryFilter(UNCLIMBABLE_WHITE_LINE_BIT);
        }
    }
}
