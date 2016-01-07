package com.develorain.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.develorain.game.tools.Level;

public class Sprinter extends Enemy {
    private static final int SPRINTER_WIDTH = 16;
    private static final int SPRINTER_HEIGHT = 16;
    private static final int SPRINTER_DENSITY = 10;

    public Sprinter(float x, float y, Level level, EntityType type) {
        super(x, y, SPRINTER_WIDTH, SPRINTER_HEIGHT, SPRINTER_DENSITY, level, type, new Vector2(5, 0));
    }
}
