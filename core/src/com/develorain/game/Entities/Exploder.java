package com.develorain.game.Entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.develorain.game.Tools.BodyFactory;
import com.develorain.game.Tools.Level;

import java.util.ArrayList;

import static com.develorain.game.Illumination.PPM;

public class Exploder extends Enemy {
    private static final int EXPLODER_WIDTH = 16;
    private static final int EXPLODER_HEIGHT = 16;
    private static final int EXPLODER_DENSITY = 10;

    public boolean isAlive = true;
    private ArrayList<Body> projectiles;
    private Level level;

    public Exploder(float x, float y, Level level, EntityType type) {
        super(x, y, EXPLODER_WIDTH, EXPLODER_HEIGHT, EXPLODER_DENSITY, level, type, new Vector2(3, 0));
        this.level = level;
    }

    @Override
    public void update() {
        if (isAlive) {
            body.setLinearVelocity(velocity.x, body.getLinearVelocity().y);
        } else {
            explode();
        }
    }

    @Override
    public void draw(Batch batch) {
        if (isAlive) {
            sprite.setPosition(body.getPosition().x - (EXPLODER_WIDTH / PPM), body.getPosition().y - (EXPLODER_HEIGHT / PPM));
            sprite.draw(batch);
        }
    }

    public void explode() {
        float x = body.getPosition().x;
        float y = body.getPosition().y;

        world.destroyBody(body);

        projectiles = new ArrayList<>();
        isAlive = false;

        for (int i = 0; i < 3; i++) {
            Body body = BodyFactory.createBoxBody(world, this, x, y, EXPLODER_WIDTH / PPM, EXPLODER_HEIGHT / PPM, type, EXPLODER_DENSITY);

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
