package com.develorain.game.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.develorain.game.Tools.BodyFactory;
import com.develorain.game.Tools.Level;

import static com.develorain.game.Illumination.PPM;

public abstract class Enemy {
    public final int ENEMY_WIDTH = 16;     // pixels
    public final int ENEMY_HEIGHT = 16;    // pixels
    public final int ENEMY_RESTITUTION = 0;
    public final int ENEMY_FRICTION = 0;
    public final int ENEMY_DENSITY = 10;
    public final boolean ENEMY_FIXED_ROTATION = true;
    public Body body;
    public Vector2 velocity;
    protected World world;
    protected Sprite sprite;
    protected EntityType type;

    public Enemy(float x, float y, Level level, EntityType type, Vector2 velocity) {
        this.type = type;
        this.velocity = velocity;
        world = level.getWorld();
        body = BodyFactory.createBoxBody(world, this, x, y, ENEMY_WIDTH, ENEMY_HEIGHT, type, ENEMY_DENSITY, true);
        createSprite();
    }

    public void update() {
        body.setLinearVelocity(velocity.x, body.getLinearVelocity().y);
    }

    public void draw(Batch batch) {
        sprite.setPosition(body.getPosition().x - (ENEMY_WIDTH / PPM), body.getPosition().y - (ENEMY_HEIGHT / PPM));
        sprite.draw(batch);
    }

    public void reverseVelocity(boolean x, boolean y) {
        if (x)
            velocity.x = -velocity.x;
        if (y)
            velocity.y = -velocity.y;
    }

    public void createSprite() {
        switch (type) {
            case WHITE_ENEMY:
                sprite = new Sprite(new Texture("Graphics/Textures/EnemySprites/whitewalker.png"));
                break;
            case BLUE_ENEMY:
                sprite = new Sprite(new Texture("Graphics/Textures/EnemySprites/bluewalker.png"));
                break;
            case RED_ENEMY:
                sprite = new Sprite(new Texture("Graphics/Textures/EnemySprites/redwalker.png"));
                break;
        }

        sprite.setSize(ENEMY_WIDTH * 2 / PPM, ENEMY_HEIGHT * 2 / PPM);
        //sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
    }
}
