package com.develorain.game.Tools.Slopes;

import com.badlogic.gdx.maps.MapObject;
import com.develorain.game.Tools.LevelCreator;

import static com.develorain.game.Illumination.DEFAULT_SLOPE_BIT;

public class DefaultSlope extends SlopeTiledObject {
    public DefaultSlope(MapObject object, LevelCreator levelCreator) {
        super(object, levelCreator);
        fixture.setUserData(this);
        setCategoryFilter(DEFAULT_SLOPE_BIT);
    }
}
