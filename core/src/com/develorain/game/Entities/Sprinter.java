package com.develorain.game.Entities;

import com.badlogic.gdx.math.Vector2;
import com.develorain.game.Tools.Level;

public class Sprinter extends Enemy {
    public Sprinter(float x, float y, Level level, EntityType type) {
        super(x, y, level, type, new Vector2(5, 0));
    }
}
