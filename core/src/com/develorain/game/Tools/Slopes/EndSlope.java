package com.develorain.game.Tools.Slopes;

import com.badlogic.gdx.maps.MapObject;
import com.develorain.game.Screens.PlayScreen;

import static com.develorain.game.Illumination.END_SLOPE_BIT;

public class EndSlope extends SlopeTiledObject {
    public EndSlope(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(END_SLOPE_BIT);
    }
}
