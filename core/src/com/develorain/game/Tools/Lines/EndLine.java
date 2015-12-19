package com.develorain.game.Tools.Lines;

import com.badlogic.gdx.maps.MapObject;
import com.develorain.game.Tools.Level;

import static com.develorain.game.Illumination.END_LINE_BIT;

public class EndLine extends Line {
    public EndLine(MapObject object, Level level) {
        super(object, level);
        fixture.setUserData(this);
        setCategoryFilter(END_LINE_BIT);
    }
}
