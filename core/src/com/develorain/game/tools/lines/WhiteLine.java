package com.develorain.game.tools.lines;

import com.badlogic.gdx.maps.MapObject;
import com.develorain.game.tools.Level;

import static com.develorain.game.tools.WorldContactListener.WHITE_LINE_BIT;

public class WhiteLine extends Line {
    public WhiteLine(MapObject object, Level level) {
        super(object, level);
        fixture.setUserData(this);
        setCategoryFilter(WHITE_LINE_BIT);
    }
}
