package com.develorain.game.Entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.develorain.game.Tools.BodyFactory;
import com.develorain.game.Tools.Level;

import java.util.ArrayList;

import static com.develorain.game.Illumination.PPM;

public class Exploder extends Enemy {
    public static final int PROJECTILE_WIDTH = 6;
    public static final int PROJECTILE_HEIGHT = 6;
    private final int PROJECTILE_DENSITY = 1; // TODO: MAKE A PROJECTILES CLASS
    public boolean isAlive = true;
    private ArrayList<Body> projectiles;

    public Exploder(float x, float y, Level level, EntityType type) {
        super(x, y, level, type, new Vector2(3, 0));
    }

    @Override
    public void update() {
        if (isAlive) {
            body.setLinearVelocity(velocity.x, body.getLinearVelocity().y);
        } else if (!isAlive) {
            explode();
        }
    }

    @Override
    public void draw(Batch batch) {
        if (isAlive) {
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
            Body body = BodyFactory.createBoxBody(world, this, x, y, PROJECTILE_WIDTH, PROJECTILE_HEIGHT, type, PROJECTILE_DENSITY, ENEMY_FIXED_ROTATION);

            //Random random = new Random();
            //body.applyLinearImpulse(new Vector2(random.nextFloat() * 5, random.nextFloat() * 10), body.getWorldCenter(), true);

            projectiles.add(body);
        }
    }

    public void destroyProjectiles() {
        for (int i = 0; i < projectiles.size(); i++) {
            world.destroyBody(projectiles.get(i));
        }
    }
}
