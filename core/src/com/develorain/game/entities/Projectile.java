package com.develorain.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.develorain.game.tools.Level;

public class Projectile extends Enemy {
    private static final int PROJECTILE_WIDTH = 6;
    private static final int PROJECTILE_HEIGHT = 6;
    private static final int PROJECTILE_DENSITY = 1;

    public Projectile(float x, float y, Level level, EntityType type) {
        // TODO: randomize the velocity of projectile
        super(x, y, PROJECTILE_WIDTH, PROJECTILE_HEIGHT, PROJECTILE_DENSITY, level, type, new Vector2(3, 0));
    }
}