package com.develorain.game.tools.lines;

import com.badlogic.gdx.maps.MapObject;
import com.develorain.game.tools.Level;

import static com.develorain.game.Illumination.RED_LINE_BIT;

public class RedLine extends Line {
    public RedLine(MapObject object, Level level) {
        super(object, level);
        fixture.setUserData(this);
        setCategoryFilter(RED_LINE_BIT);
    }
}