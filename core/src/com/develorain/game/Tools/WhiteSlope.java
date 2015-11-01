package com.develorain.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.develorain.game.Screens.PlayScreen;

import static com.develorain.game.Illumination.WHITESLOPE_BIT;

public class WhiteSlope extends SlopeTiledObject {
    public WhiteSlope(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(WHITESLOPE_BIT);
    }
}
