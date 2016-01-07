package com.develorain.game.tools.lines;

import com.badlogic.gdx.maps.MapObject;
import com.develorain.game.tools.Level;

import static com.develorain.game.Illumination.BLUE_LINE_BIT;

public class BlueLine extends Line {

    public BlueLine(MapObject object, Level level) {
        super(object, level);
        fixture.setUserData(this);
        setCategoryFilter(BLUE_LINE_BIT);
    }

}