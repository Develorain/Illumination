package com.develorain.game.Tools.Slopes;

import com.badlogic.gdx.maps.MapObject;
import com.develorain.game.Screens.PlayScreen;
import com.develorain.game.Tools.LevelCreator;

import static com.develorain.game.Illumination.ALTERNATE_SLOPE_BIT;

public class AlternateSlope extends SlopeTiledObject {
    public AlternateSlope(PlayScreen screen, MapObject object, LevelCreator levelCreator) {
        super(screen, object, levelCreator);
        fixture.setUserData(this);
        setCategoryFilter(ALTERNATE_SLOPE_BIT);
    }
}
