package com.develorain.game.Tools.Slopes;

import com.badlogic.gdx.maps.MapObject;
import com.develorain.game.Tools.LevelCreator;

import static com.develorain.game.Illumination.NORMAL_SLOPE_BIT;

public class NormalSlope extends SlopeTiledObject {
    public NormalSlope(MapObject object, LevelCreator levelCreator) {
        super(object, levelCreator);
        fixture.setUserData(this);
        setCategoryFilter(NORMAL_SLOPE_BIT);
    }
}
