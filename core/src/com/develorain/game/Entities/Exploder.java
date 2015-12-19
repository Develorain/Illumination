package com.develorain.game.Entities;

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
    private final int PROJECTILE_DENSITY = 1; // TODO: MAKE A PROJECTILES CLASS
    public boolean isAlive = true;
    public boolean projectilesFired = false;
    private ArrayList<Body> projectiles;

    public Exploder(float x, float y, Level level, EntityType type) {
        super(x, y, level, type, new Vector2(3, 0));
    }

    @Override
    public void update() {
        if (isAlive && !projectilesFired) {
            body.setLinearVelocity(velocity.x, body.getLinearVelocity().y);
        } else if (!isAlive && !projectilesFired) {
            explode();
        }
    }

    @Override
    public void draw(Batch batch) {
        if (isAlive && !projectilesFired) {
            sprite.setPosition(body.getPosition().x - (ENEMY_WIDTH / PPM), body.getPosition().y - (ENEMY_HEIGHT / PPM));
            sprite.draw(batch);
        }
    }

    public void explode() {
        float x = body.getPosition().x;
        float y = body.getPosition().y;

        world.destroyBody(body);

        createProjectiles(x, y);
    }

    public void createProjectiles(float x, float y) {
        projectiles = new ArrayList<>();
        isAlive = false;

        for (int i = 0; i < 3; i++) {
            Random random = new Random();

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

            switch (type) {
                case WHITE_ENEMY:
                    fdef.filter.categoryBits = PROJECTILE_BIT;
                    fdef.filter.maskBits = WHITE_ENEMY_BIT | BLUE_ENEMY_BIT | RED_ENEMY_BIT;
                    break;
                case BLUE_ENEMY:
                    fdef.filter.categoryBits = PROJECTILE_BIT;
                    fdef.filter.maskBits = WHITE_ENEMY_BIT | BLUE_ENEMY_BIT;
                    break;
                case RED_ENEMY:
                    fdef.filter.categoryBits = PROJECTILE_BIT;
                    fdef.filter.maskBits = WHITE_ENEMY_BIT | RED_ENEMY_BIT;
                    break;
            }

            fdef.filter.maskBits |= WHITE_LINE_BIT | BLUE_LINE_BIT | RED_LINE_BIT | BOUNDARY_LINE_BIT | PLAYER_BIT;
            enemyShape.setAsBox(6 / PPM, 6 / PPM);

            body = world.createBody(bdef);
            body.applyLinearImpulse(new Vector2(random.nextFloat() * 5, random.nextFloat() * 10), body.getWorldCenter(), true);
            body.createFixture(fdef).setUserData(this);
            projectiles.add(body);
        }

        projectilesFired = true;
    }

    public void destroyProjectiles() {
        for (int i = 0; i < projectiles.size(); i++) {
            world.destroyBody(projectiles.get(i));
        }
    }
}
