package com.develorain.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.develorain.game.Tools.LevelCreator;

import static com.develorain.game.Illumination.*;

public class Walker extends Enemy {
    public Sprite enemySprite;

    public Walker(float x, float y, LevelCreator levelCreator, String colour) {
        super(x, y, levelCreator, colour);
    }

    @Override
    protected void createBody(float x, float y) {
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
                fdef.filter.maskBits = DEFAULT_ENEMY_BIT;
                break;
            case "red":
                fdef.filter.categoryBits = ALTERNATE_ENEMY_BIT;
                fdef.filter.maskBits = DEFAULT_ENEMY_BIT;
                break;
        }

        fdef.filter.maskBits |= DEFAULT_SLOPE_BIT | NORMAL_SLOPE_BIT | ALTERNATE_SLOPE_BIT | BOUNDARY_SLOPE_BIT | PLAYER_BIT;

        enemyShape.setAsBox(ENEMY_WIDTH / PPM, ENEMY_HEIGHT / PPM);

        b2body = world.createBody(bdef);
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    protected void createSprite() {
        switch (colour) {
            case "white":
                enemySprite = new Sprite(new Texture("Graphics/Sprites/EnemySprites/whitewalker.png"));
                break;
            case "blue":
                enemySprite = new Sprite(new Texture("Graphics/Sprites/EnemySprites/bluewalker.png"));
                break;
            case "red":
                enemySprite = new Sprite(new Texture("Graphics/Sprites/EnemySprites/redwalker.png"));
                break;
        }

        enemySprite.setSize(ENEMY_WIDTH * 2 / PPM, ENEMY_HEIGHT * 2 / PPM);
        enemySprite.setOrigin(enemySprite.getWidth() / 2, enemySprite.getHeight() / 2);
        b2body.setUserData(enemySprite);
    }

    public void update() {
        b2body.setLinearVelocity(velocity.x, b2body.getLinearVelocity().y);
    }
}
