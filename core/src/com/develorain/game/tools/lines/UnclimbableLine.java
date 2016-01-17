package com.develorain.game.tools.lines;

import com.badlogic.gdx.maps.MapObject;
import com.develorain.game.tools.Level;

import static com.develorain.game.tools.WorldContactListener.UNCLIMBABLE_LINE_BIT;

public class UnclimbableLine extends Line {
    public UnclimbableLine(MapObject object, Level level) {
        super(object, level);
        fixture.setUserData(this);
        setCategoryFilter(UNCLIMBABLE_LINE_BIT);
    }
}
