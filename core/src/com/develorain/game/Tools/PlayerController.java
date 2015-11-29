package com.develorain.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.develorain.game.Sprites.Player;

import static com.develorain.game.Screens.PlayScreen.TIME_SLOWDOWN_MODIFIER;

public class PlayerController {
    public static boolean SLOW_MOTION_MODE = false;
    private final int SPRINT_SPEED_CAP = 13;
    private final int REGULAR_SPEED_CAP = 7;
    public int footContactCounter = 0;
    public int leftContactCounter = 0;
    public int rightContactCounter = 0;
    public boolean canDoubleJump = true;  // starts as true because player spawns starting in the air
    public boolean canChargeDownwards = false;
    public boolean shouldRespawn = false;
    private float curveTimer = 0;
    private int jumpNumber = 0;

    // Reference of player and body to be controlled
    private Player player;
    private Body body;

    public PlayerController(Player player) {
        this.player = player;
        this.body = player.playerB2DBody;
    }

    public void handleInput(float dt) {
        boolean inputGiven = false;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && body.getLinearVelocity().x <= REGULAR_SPEED_CAP) {
            body.applyLinearImpulse(new Vector2(1f / TIME_SLOWDOWN_MODIFIER, 0), body.getWorldCenter(), true);
            inputGiven = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && body.getLinearVelocity().x >= -REGULAR_SPEED_CAP) {
            body.applyLinearImpulse(new Vector2(-1f / TIME_SLOWDOWN_MODIFIER, 0), body.getWorldCenter(), true);
            inputGiven = true;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            if (canJump()) {
                if (body.getLinearVelocity().y < 0) {
                    body.setLinearVelocity(body.getLinearVelocity().x, 0);
                }

                body.applyLinearImpulse(new Vector2(0, 6f), body.getWorldCenter(), true);
                jumpNumber = 1;
            } else if (canDoubleJump && !canWallJumpTowardsTheLeft() && !canWallJumpTowardsTheRight() && curveTimer == 0) {
                curveTimer = 0;

                if (body.getLinearVelocity().y < 0) {
                    body.setLinearVelocity(body.getLinearVelocity().x, 0);
                }

                body.applyLinearImpulse(new Vector2(0, 5f), body.getWorldCenter(), true);
                canDoubleJump = false;
                jumpNumber = 2;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            if (!canJump()) {
                if (jumpNumber == 0 || jumpNumber == 1 || jumpNumber == 2) {
                    if (curveTimer < 0.15) {
                        if (body.getLinearVelocity().y < 0) {
                            body.setLinearVelocity(body.getLinearVelocity().x, 0);
                        }

                        body.applyLinearImpulse(new Vector2(0, 0.8f), body.getWorldCenter(), true);
                    }
                }
            }

            curveTimer += dt;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Z) && canWallJumpTowardsTheLeft() && !canJump()) {
            if (body.getLinearVelocity().y < 0) {
                body.setLinearVelocity(0, 0);
            }

            body.applyLinearImpulse(new Vector2(-10f, 6f), body.getWorldCenter(), true);
            inputGiven = true;
            canDoubleJump = true;
            jumpNumber = 2;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Z) && canWallJumpTowardsTheRight() && !canJump()) {
            if (body.getLinearVelocity().y < 0) {
                body.setLinearVelocity(0, 0);
            }

            body.applyLinearImpulse(new Vector2(10f, 6f), body.getWorldCenter(), true);
            inputGiven = true;
            canDoubleJump = true;
            jumpNumber = 2;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            TIME_SLOWDOWN_MODIFIER = TIME_SLOWDOWN_MODIFIER == 1 ? 3 : 1;
            SLOW_MOTION_MODE = !SLOW_MOTION_MODE;

            //if (SLOW_MOTION_MODE)
            //  Illumination.manager.get("Audio/Sounds/startslowmotion.ogg", Sound.class).play();
            //else
            //  Illumination.manager.get("Audio/Sounds/endslowmotion.ogg", Sound.class).play();

            float xVelocity = body.getLinearVelocity().x;
            float yVelocity = body.getLinearVelocity().y;

            player = player.destroyAndRemake();

            body = player.playerB2DBody;
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
        if (body.getLinearVelocity().y >= 20) {
            body.setLinearVelocity(body.getLinearVelocity().x, 20);
        }

        if (!Gdx.input.isKeyPressed(Input.Keys.Z)) {
            jumpNumber++;
            curveTimer = 0;
        }

        // Manual deceleration
        if (!inputGiven) {
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
