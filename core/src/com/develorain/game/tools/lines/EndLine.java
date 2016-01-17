package com.develorain.game.tools.lines;

import com.badlogic.gdx.maps.MapObject;
import com.develorain.game.tools.Level;

import static com.develorain.game.tools.WorldContactListener.END_LINE_BIT;

public class EndLine extends Line {
    public EndLine(MapObject object, Level level) {
        super(object, level);
        fixture.setUserData(this);
        setCategoryFilter(END_LINE_BIT);
    }
}
