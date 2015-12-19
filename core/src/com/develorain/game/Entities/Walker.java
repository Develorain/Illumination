package com.develorain.game.Entities;

import com.badlogic.gdx.math.Vector2;
import com.develorain.game.Tools.Level;

public class Walker extends Enemy {
    private static final int WALKER_WIDTH = 16;
    private static final int WALKER_HEIGHT = 16;
    private static final int WALKER_DENSITY = 10;

    public Walker(float x, float y, Level level, EntityType type) {
        super(x, y, WALKER_WIDTH, WALKER_HEIGHT, WALKER_DENSITY, level, type, new Vector2(3, 0));
    }
}