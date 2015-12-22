package com.develorain.game.Entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.develorain.game.Tools.Level;

import static com.develorain.game.Illumination.PPM;

public class Exploder extends Enemy {
    private static final int EXPLODER_WIDTH = 16;
    private static final int EXPLODER_HEIGHT = 16;
    private static final int EXPLODER_DENSITY = 10;

    private Level level;
    private boolean alive;

    public Exploder(float x, float y, Level level, EntityType type) {
        super(x, y, EXPLODER_WIDTH, EXPLODER_HEIGHT, EXPLODER_DENSITY, level, type, new Vector2(3, 0));
        this.level = level;
        alive = true;
    }

    @Override
    public void update() {
        if (alive) {
            body.setLinearVelocity(velocity.x, body.getLinearVelocity().y);
        }
    }

    @Override
    public void draw(Batch batch) {
        if (alive) {
            sprite.setPosition(body.getPosition().x - (EXPLODER_WIDTH / PPM), body.getPosition().y - (EXPLODER_HEIGHT / PPM));
            sprite.draw(batch);
        }
    }

    public void explode() {
        float x = body.getPosition().x * PPM;
        float y = body.getPosition().y * PPM;

        alive = false;

        sprite.getTexture().dispose();
        world.destroyBody(body);

        for (int i = 0; i < 3; i++) {
            level.getWorldInitializer().createProjectile(x, y, type);
        }
    }
}
