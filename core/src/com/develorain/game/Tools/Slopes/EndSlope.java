package com.develorain.game.Tools.Slopes;

import com.badlogic.gdx.maps.MapObject;
import com.develorain.game.Screens.PlayScreen;
import com.develorain.game.Tools.LevelCreator;

import static com.develorain.game.Illumination.END_SLOPE_BIT;

public class EndSlope extends SlopeTiledObject {
    public EndSlope(MapObject object, LevelCreator levelCreator) {
        super(object, levelCreator);
        fixture.setUserData(this);
        setCategoryFilter(END_SLOPE_BIT);
    }
}
