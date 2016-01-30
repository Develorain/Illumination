package com.develorain.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.develorain.game.Illumination;
import com.develorain.game.entities.Player;

import static com.develorain.game.screens.PlayScreen.TIME_SLOWDOWN_MODIFIER;

public class GameInputHandler {
    public static final int WALKING_SPEED_CAP = 5;
    public static final int SPRINTING_SPEED_CAP = 7;
    public static final int WALKING_ACCELERATION = 1;
    public static final int SPRINTING_ACCELERATION = 2;
    public static final int NO_INPUT_DECELERATION = SPRINTING_ACCELERATION;
    public static boolean SLOW_MOTION_MODE = false;
    public int footContactCounter = 0;
    public int leftContactCounter = 0;
    public int rightContactCounter = 0;
    public boolean canDoubleJump = true;  // starts as true because player spawns starting in the air
    public boolean canChargeDownwards = false;
    public boolean shouldRespawn = false;
    private float jumpTimer = 0;
    private Player player;
    private Body body;
    private Music music;

    public GameInputHandler(Player player) {
        this.player = player;
        this.body = player.body;

        music = Illumination.assetManager.get("audio/music/disconnected.ogg", Music.class);
        music.setVolume(0.5f);
        music.setLooping(true);
        music.play();
    }

    public void handleInput(float dt) {
        boolean inputGiven = false;

        System.out.println(body.getLinearVelocity().x);

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            inputGiven = true;

            float currentSpeedCap = WALKING_SPEED_CAP;
            float currentAcceleration = WALKING_ACCELERATION;

            if (Gdx.input.isKeyPressed(Input.Keys.X)) {
                currentSpeedCap = SPRINTING_SPEED_CAP;
                currentAcceleration = SPRINTING_ACCELERATION;
            }

            increaseVelocity(currentSpeedCap, currentAcceleration);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            inputGiven = true;

            float currentSpeedCap = -WALKING_SPEED_CAP;
            float currentAcceleration = -WALKING_ACCELERATION;

            if (Gdx.input.isKeyPressed(Input.Keys.X)) {
                currentSpeedCap = -SPRINTING_SPEED_CAP;
                currentAcceleration = -SPRINTING_ACCELERATION;
            }

            decreaseVelocity(currentSpeedCap, currentAcceleration);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            if (canJump()) {
                jumpTimer = 0;
                if (body.getLinearVelocity().y < 0) {
                    body.setLinearVelocity(body.getLinearVelocity().x, 0);
                }

                body.applyLinearImpulse(new Vector2(0, 7f), body.getWorldCenter(), true);
            } else if (canDoubleJump && !canWallJumpTowardsTheLeft() && !canWallJumpTowardsTheRight()) {
                jumpTimer = 0;
                body.setLinearVelocity(body.getLinearVelocity().x, NO_INPUT_DECELERATION);

                body.applyLinearImpulse(new Vector2(0, 7f), body.getWorldCenter(), true);
                canDoubleJump = false;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Z) && jumpTimer <= 0.1 && !canJump()) {
            if (body.getLinearVelocity().y < 0) {
                body.setLinearVelocity(body.getLinearVelocity().x, 0);
            }

            body.applyLinearImpulse(new Vector2(0, 1f), body.getWorldCenter(), true);
            jumpTimer += dt;
        } else if (!Gdx.input.isKeyPressed(Input.Keys.Z) && canJump()) {
            // when you land on the ground, consider making this in the contact listener
            jumpTimer = 0;
        } else if (!Gdx.input.isKeyPressed(Input.Keys.Z)) {
            // if you let go of jump after double jump
            jumpTimer = 0.2f;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Z) && canWallJumpTowardsTheLeft() && !canJump()) {
            jumpTimer = 0;

            if (body.getLinearVelocity().y < 0) {
                body.setLinearVelocity(0, 0);
            }

            body.applyLinearImpulse(new Vector2(-10f, 7f), body.getWorldCenter(), true);
            inputGiven = true;
            canDoubleJump = true;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Z) && canWallJumpTowardsTheRight() && !canJump()) {
            jumpTimer = 0;

            if (body.getLinearVelocity().y < 0) {
                body.setLinearVelocity(0, 0);
            }

            body.applyLinearImpulse(new Vector2(10f, 7f), body.getWorldCenter(), true);
            inputGiven = true;
            canDoubleJump = true;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            TIME_SLOWDOWN_MODIFIER = TIME_SLOWDOWN_MODIFIER == NO_INPUT_DECELERATION ? 3 : NO_INPUT_DECELERATION;
            SLOW_MOTION_MODE = !SLOW_MOTION_MODE;

            /*
            if (SLOW_MOTION_MODE)
                Illumination.assetManager.get("Audio/Sounds/startslowmotion.ogg", Sound.class).play();
            else
                Illumination.assetManager.get("Audio/Sounds/endslowmotion.ogg", Sound.class).play();
            */

            float xVelocity = body.getLinearVelocity().x;
            float yVelocity = body.getLinearVelocity().y;

            player = player.destroyAndRemake();

            body = player.body;
            body.setLinearVelocity(xVelocity, yVelocity);
        }

        // Down charge
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && canChargeDownwards) {
            float yVelocity = body.getLinearVelocity().y;
            if (yVelocity >= 0) {
                body.setLinearVelocity(body.getLinearVelocity().x, 0);
            }

            body.applyLinearImpulse(new Vector2(0, -20f), body.getWorldCenter(), true);
            canChargeDownwards = false;
            inputGiven = true;
        }

        if (shouldRespawn) {
            player.destroy();
            player = player.respawn();

            shouldRespawn = false;
        }

        // Limit movement on Y-axis
        if (body.getLinearVelocity().y >= 15) {
            body.setLinearVelocity(body.getLinearVelocity().x, 15);
        }

        // Music volume control
        if (body.getLinearVelocity().x != 0 || body.getLinearVelocity().y != 0) {
            if (music.getVolume() < 0.4f) {
                music.setVolume(music.getVolume() + (music.getVolume() * 0.04f));
            }
        } else {
            if (music.getVolume() > 0.2f) {
                music.setVolume(music.getVolume() - (music.getVolume() * 0.04f));
            }
        }

        // Manual deceleration
        if (!inputGiven) {
            if (body.getLinearVelocity().x > 0) {
                decreaseVelocity(0, -NO_INPUT_DECELERATION);
            } else if (body.getLinearVelocity().x < 0) {
                increaseVelocity(0, NO_INPUT_DECELERATION);
            }
        }
    }

    private void decreaseVelocity(float currentSpeedCap, float currentAcceleration) {
        if (body.getLinearVelocity().x > currentSpeedCap) {
            if (body.getLinearVelocity().x + currentAcceleration < currentSpeedCap) {
                body.setLinearVelocity(currentSpeedCap, body.getLinearVelocity().y);
            } else {
                body.setLinearVelocity(body.getLinearVelocity().x + currentAcceleration, body.getLinearVelocity().y);
            }
        }
    }

    private void increaseVelocity(float currentSpeedCap, float currentAcceleration) {
        if (body.getLinearVelocity().x < currentSpeedCap) {
            if (body.getLinearVelocity().x + currentAcceleration > currentSpeedCap) {
                body.setLinearVelocity(currentSpeedCap, body.getLinearVelocity().y);
            } else {
                body.setLinearVelocity(body.getLinearVelocity().x + currentAcceleration, body.getLinearVelocity().y);
            }
        }
    }

    private boolean canJump() {
        return footContactCounter > 0;
    }

    private boolean canWallJumpTowardsTheLeft() {
        return rightContactCounter > 0;
    }

    private boolean canWallJumpTowardsTheRight() {
        return leftContactCounter > 0;
    }
}
