package com.develorain.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.develorain.game.Screens.PlayScreen;

public class BlueSlope extends SlopeTiledObject {
    public BlueSlope(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
    }
}
