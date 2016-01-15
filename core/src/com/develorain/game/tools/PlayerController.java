package com.develorain.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.develorain.game.Illumination;
import com.develorain.game.entities.Player;

import static com.develorain.game.screens.PlayScreen.TIME_SLOWDOWN_MODIFIER;

public class PlayerController {
    public static boolean SLOW_MOTION_MODE = false;
    private final int SPRINT_SPEED_CAP = 13;
    private final int REGULAR_SPEED_CAP = 8;
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

    public PlayerController(Player player) {
        this.player = player;
        this.body = player.body;

        //music = Illumination.assetManager.get("audio/music/disconnected.ogg", Music.class);
        //music.setVolume(0.5f);
        //music.setLooping(true);
        //music.play();
    }

    public void handleInput(float dt) {
        boolean inputGiven = false;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && body.getLinearVelocity().x <= REGULAR_SPEED_CAP) {
            body.applyLinearImpulse(new Vector2(1.5f / TIME_SLOWDOWN_MODIFIER, 0), body.getWorldCenter(), true);
            inputGiven = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && body.getLinearVelocity().x >= -REGULAR_SPEED_CAP) {
            body.applyLinearImpulse(new Vector2(-1.5f / TIME_SLOWDOWN_MODIFIER, 0), body.getWorldCenter(), true);
            inputGiven = true;
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
                body.setLinearVelocity(body.getLinearVelocity().x, 1);

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
            TIME_SLOWDOWN_MODIFIER = TIME_SLOWDOWN_MODIFIER == 1 ? 3 : 1;
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

        // Sprint right
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.X) && body.getLinearVelocity().x <= SPRINT_SPEED_CAP) {
            body.applyLinearImpulse(new Vector2(0.5f / TIME_SLOWDOWN_MODIFIER, 0), body.getWorldCenter(), true);
            inputGiven = true;
        }

        // Sprint left
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.X) && body.getLinearVelocity().x >= -SPRINT_SPEED_CAP) {
            body.applyLinearImpulse(new Vector2(-0.5f / TIME_SLOWDOWN_MODIFIER, 0), body.getWorldCenter(), true);
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
        /*
        if (body.getLinearVelocity().x != 0 || body.getLinearVelocity().y != 0) {
            if (music.getVolume() < 0.4f) {
                music.setVolume(music.getVolume() + (music.getVolume() * 0.04f));
            }
        } else {
            if (music.getVolume() > 0.2f) {
                music.setVolume(music.getVolume() - (music.getVolume() * 0.04f));
            }
        }
        */

        // Manual deceleration
        if (!inputGiven) {
            if (canJump()) {
                if (body.getLinearVelocity().x > 0.5f) {
                    body.applyLinearImpulse(new Vector2(-0.5f / TIME_SLOWDOWN_MODIFIER, 0), body.getWorldCenter(), true);
                } else if (body.getLinearVelocity().x > 0) {
                    body.setLinearVelocity(0, body.getLinearVelocity().y);
                }

                if (body.getLinearVelocity().x < -0.5f) {
                    body.applyLinearImpulse(new Vector2(0.5f / TIME_SLOWDOWN_MODIFIER, 0), body.getWorldCenter(), true);
                } else if (body.getLinearVelocity().x < 0) {
                    body.setLinearVelocity(0, body.getLinearVelocity().y);
                }
            } else {
                if (body.getLinearVelocity().x > 0.2f) {
                    body.applyLinearImpulse(new Vector2(-0.2f / TIME_SLOWDOWN_MODIFIER, 0), body.getWorldCenter(), true);
                } else if (body.getLinearVelocity().x > 0) {
                    body.setLinearVelocity(0, body.getLinearVelocity().y);
                }

                if (body.getLinearVelocity().x < -0.2f) {
                    body.applyLinearImpulse(new Vector2(0.2f / TIME_SLOWDOWN_MODIFIER, 0), body.getWorldCenter(), true);
                } else if (body.getLinearVelocity().x < 0) {
                    body.setLinearVelocity(0, body.getLinearVelocity().y);
                }
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
