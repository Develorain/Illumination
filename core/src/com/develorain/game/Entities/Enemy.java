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
    public Body body;
    public Vector2 velocity;
    protected World world;
    protected Sprite sprite;
    protected EntityType type;
    private float width;
    private float height;

    public Enemy(float x, float y, int width, int height, int density, Level level, EntityType type, Vector2 velocity) {
        this.type = type;
        this.velocity = velocity;
        this.width = width;
        this.height = height;
        world = level.getWorld();
        body = BodyFactory.createBoxBody(world, this, x / PPM, y / PPM, width / PPM, height / PPM, type, density);
        createSprite();
    }

    public void update() {
        body.setLinearVelocity(velocity.x, body.getLinearVelocity().y);
    }

    public void draw(Batch batch) {
        sprite.setPosition(body.getPosition().x - (width / PPM), body.getPosition().y - (height / PPM));
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

        sprite.setSize(width * 2 / PPM, height * 2 / PPM);
        //sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
    }
}
