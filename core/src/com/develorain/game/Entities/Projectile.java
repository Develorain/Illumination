package com.develorain.game.Entities;

import com.badlogic.gdx.math.Vector2;
import com.develorain.game.Tools.Level;

public class Projectile extends Enemy {
    private static final int PROJECTILE_WIDTH = 6;
    private static final int PROJECTILE_HEIGHT = 6;
    private static final int PROJECTILE_DENSITY = 1;

    public Projectile(float x, float y, Level level, EntityType type) {
        super(x, y, PROJECTILE_WIDTH, PROJECTILE_HEIGHT, PROJECTILE_DENSITY, level, type, new Vector2(0, 0));
    }
}