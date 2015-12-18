package com.develorain.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.develorain.game.Tools.Level;

import static com.develorain.game.Illumination.*;

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

    private Sprite sprite;

    protected World world;

    public Enemy(float x, float y, Level level, String colour, Vector2 velocity) {
        this.colour = colour;
        this.velocity = velocity;
        world = level.getWorld();
        createBody(x, y);
        createSprite();
    }

    public void update() {
        b2body.setLinearVelocity(velocity.x, b2body.getLinearVelocity().y);
    }

    public void draw(Batch batch) {
        Sprite sprite = (Sprite) b2body.getUserData();
        sprite.setPosition(b2body.getPosition().x - (ENEMY_WIDTH / PPM), b2body.getPosition().y - (ENEMY_HEIGHT / PPM));
        sprite.draw(batch);
    }

    public void reverseVelocity(boolean x, boolean y) {
        if (x)
            velocity.x = -velocity.x;
        if (y)
            velocity.y = -velocity.y;
    }

    public void createSprite() {
        switch (colour) {
            case "white":
                sprite = new Sprite(new Texture("Graphics/Sprites/EnemySprites/whitewalker.png"));
                break;
            case "blue":
                sprite = new Sprite(new Texture("Graphics/Sprites/EnemySprites/bluewalker.png"));
                break;
            case "red":
                sprite = new Sprite(new Texture("Graphics/Sprites/EnemySprites/redwalker.png"));
                break;
        }

        sprite.setSize(ENEMY_WIDTH * 2 / PPM, ENEMY_HEIGHT * 2 / PPM);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        b2body.setUserData(sprite);
    }

    public void createBody(float x, float y) {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape enemyShape = new PolygonShape();

        bdef.position.set(x / PPM, y / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.fixedRotation = ENEMY_FIXED_ROTATION;

        fdef.shape = enemyShape;
        fdef.restitution = ENEMY_RESTITUTION;
        fdef.density = ENEMY_DENSITY;
        fdef.friction = ENEMY_FRICTION;

        switch (colour) {
            case "white":
                fdef.filter.categoryBits = DEFAULT_ENEMY_BIT;
                fdef.filter.maskBits = DEFAULT_ENEMY_BIT | NORMAL_ENEMY_BIT | ALTERNATE_ENEMY_BIT;
                break;
            case "blue":
                fdef.filter.categoryBits = NORMAL_ENEMY_BIT;
                fdef.filter.maskBits = DEFAULT_ENEMY_BIT | NORMAL_ENEMY_BIT;
                break;
            case "red":
                fdef.filter.categoryBits = ALTERNATE_ENEMY_BIT;
                fdef.filter.maskBits = DEFAULT_ENEMY_BIT | ALTERNATE_ENEMY_BIT;
                break;
        }

        fdef.filter.maskBits |= DEFAULT_SLOPE_BIT | NORMAL_SLOPE_BIT | ALTERNATE_SLOPE_BIT | BOUNDARY_SLOPE_BIT | PLAYER_BIT;

        enemyShape.setAsBox(ENEMY_WIDTH / PPM, ENEMY_HEIGHT / PPM);

        b2body = world.createBody(bdef);
        b2body.createFixture(fdef).setUserData(this);
    }
}
