package com.develorain.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.develorain.game.Screens.PlayScreen;

public abstract class Enemy extends Sprite {
    World world;
    PlayScreen screen;
    public Body b2body;


    public Enemy(PlayScreen screen, float x, float y) {
        world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
    }

    protected abstract void defineEnemy();
}
