package com.develorain.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.develorain.game.Tools.Level;

import java.util.ArrayList;
import java.util.Random;

import static com.develorain.game.Illumination.*;

public class Exploder extends Enemy {
    private final int PROJECTILE_DENSITY = 1;
    public boolean isAlive = true;
    private ArrayList<Body> projectiles;

    public Exploder(float x, float y, Level level, String colour) {
        super(x, y, level, colour, new Vector2(3, 0));
    }

    @Override
    public void update() {
        if (isAlive) {
            b2body.setLinearVelocity(velocity.x, b2body.getLinearVelocity().y);
        } else {
            explode();
        }
    }

    @Override
    public void draw(Batch batch) {
        if (isAlive) {
            sprite.setPosition(b2body.getPosition().x - (ENEMY_WIDTH / PPM), b2body.getPosition().y - (ENEMY_HEIGHT / PPM));
            sprite.draw(batch);
        }
    }

    public void explode() {
        float x = b2body.getPosition().x;
        float y = b2body.getPosition().y;

        world.destroyBody(b2body);

        createProjectiles(x, y);
    }

    public void createProjectiles(float x, float y) {
        projectiles = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Random random = new Random();
            isAlive = false;

            BodyDef bdef = new BodyDef();
            FixtureDef fdef = new FixtureDef();
            PolygonShape enemyShape = new PolygonShape();

            bdef.position.set(x, y);
            bdef.type = BodyDef.BodyType.DynamicBody;
            bdef.fixedRotation = ENEMY_FIXED_ROTATION;

            fdef.shape = enemyShape;
            fdef.restitution = ENEMY_RESTITUTION;
            fdef.density = PROJECTILE_DENSITY;
            fdef.friction = ENEMY_FRICTION;

            switch (colour) {
                case "white":
                    fdef.filter.categoryBits = PROJECTILE_BIT;
                    fdef.filter.maskBits = DEFAULT_ENEMY_BIT | NORMAL_ENEMY_BIT | ALTERNATE_ENEMY_BIT;
                    break;
                case "blue":
                    fdef.filter.categoryBits = PROJECTILE_BIT;
                    fdef.filter.maskBits = DEFAULT_ENEMY_BIT | NORMAL_ENEMY_BIT;
                    break;
                case "red":
                    fdef.filter.categoryBits = PROJECTILE_BIT;
                    fdef.filter.maskBits = DEFAULT_ENEMY_BIT | ALTERNATE_ENEMY_BIT;
                    break;
            }

            fdef.filter.maskBits |= DEFAULT_SLOPE_BIT | NORMAL_SLOPE_BIT | ALTERNATE_SLOPE_BIT | BOUNDARY_SLOPE_BIT | PLAYER_BIT;
            enemyShape.setAsBox(6 / PPM, 6 / PPM);

            b2body = world.createBody(bdef);
            b2body.applyLinearImpulse(new Vector2(random.nextFloat() * 5, random.nextFloat() * 10), b2body.getWorldCenter(), true);
            b2body.createFixture(fdef).setUserData(this);
            projectiles.add(b2body);
        }
    }

    public void destroyProjectiles() {
        for (int i = 0; i < projectiles.size(); i++) {
            world.destroyBody(projectiles.get(i));
        }
    }
}
