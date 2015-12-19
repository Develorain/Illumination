package com.develorain.game.Entities;

import com.badlogic.gdx.math.Vector2;
import com.develorain.game.Tools.Level;

public class Walker extends Enemy {
    public Walker(float x, float y, Level level, String colour) {
        super(x, y, level, colour, new Vector2(3, 0));
    }
}
