package com.develorain.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.develorain.game.Tools.Level;

public abstract class Enemy extends Sprite {
    public final int ENEMY_WIDTH = 16;     // pixels
    public final int ENEMY_HEIGHT = 16;    // pixels
    public final int ENEMY_RESTITUTION = 0;
    public final int ENEMY_FRICTION = 0;
    public final int ENEMY_DENSITY = 10;
    public final boolean ENEMY_FIXED_ROTATION = true;
    public Body b2body;
    public Vector2 velocity;
    public String colour;
    protected World world;

    public Enemy(float x, float y, Level level, String colour) {
        this.colour = colour;
        world = level.getWorld();
        velocity = new Vector2(3, 0);
        createBody(x, y);
        createSprite();
    }

    protected abstract void createBody(float x, float y);

    protected abstract void createSprite();

    public void reverseVelocity(boolean x, boolean y) {
        if (x)
            velocity.x = -velocity.x;
        if (y)
            velocity.y = -velocity.y;
    }
}
