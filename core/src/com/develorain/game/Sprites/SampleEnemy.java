package com.develorain.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.develorain.game.Screens.PlayScreen;

import static com.develorain.game.Illumination.*;

public class SampleEnemy extends Enemy {
    public Sprite enemySprite;

    public SampleEnemy(PlayScreen screen, float x, float y) {
        super(screen, x, y);
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

        fdef.filter.categoryBits = ENEMY_BIT;
        fdef.filter.maskBits = DEFAULT_SLOPE_BIT | NORMAL_SLOPE_BIT | ALTERNATE_SLOPE_BIT | ENEMY_BIT;

        enemyShape.setAsBox(ENEMY_WIDTH / PPM, ENEMY_HEIGHT / PPM);

        b2body = world.createBody(bdef);
        b2body.createFixture(fdef);
    }

    protected void createSprite() {
        enemySprite = new Sprite(new Texture("Graphics/Sprites/EnemySprites/enemy.png"));
        enemySprite.setSize(ENEMY_WIDTH * 2 / PPM, ENEMY_HEIGHT * 2 / PPM);
        enemySprite.setOrigin(enemySprite.getWidth() / 2, enemySprite.getHeight() / 2);
        b2body.setUserData(enemySprite);
    }
}
