package com.develorain.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import static com.develorain.game.Screens.PlayScreen.TIME_SLOWDOWN_MODIFIER;
import static com.develorain.game.Screens.PlayScreen.currentTime;

public class PlayerController {
    private final int DASH_SPEED_CAP = 25;
    private final int SPRINT_SPEED_CAP = 15;
    private final int REGULAR_SPEED_CAP = 7;
    // Reference of body to be controlled
    private Body body;

    // Variables
    private float lastTimeDashed = -100;
    public int footContactCounter = 0;
    public int leftContactCounter = 0;
    public int rightContactCounter = 0;
    public boolean canDoubleJump = true;  // starts as true because player spawns starting in the air
    public boolean canChargeDownwards = false;

    public PlayerController(Body body) {
        this.body = body;
    }

    public void handleInput() {
        boolean inputGiven = false;
        // Runs if right is held
        if((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) && body.getLinearVelocity().x <= REGULAR_SPEED_CAP) {
            body.applyLinearImpulse(new Vector2(1.5f, 0), body.getWorldCenter(), true);
            body.applyTorque(1.3f, true);
            inputGiven = true;
        }

        // Runs if left is held
        if((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) && body.getLinearVelocity().x >= -REGULAR_SPEED_CAP) {
            body.applyLinearImpulse(new Vector2(-1.5f, 0), body.getWorldCenter(), true);
            body.applyTorque(-1.3f, true);
            inputGiven = true;
        }

        // Jump and double jump
        if((Gdx.input.isKeyJustPressed(Input.Keys.Z) || Gdx.input.isKeyJustPressed(Input.Keys.W))) {
            if(canJump()) {
                if(body.getLinearVelocity().y < 0) {
                    body.setLinearVelocity(body.getLinearVelocity().x, 0);
                }

                body.applyLinearImpulse(new Vector2(0, 10f), body.getWorldCenter(), true);
            } else if(canDoubleJump && !canWallJumpToLeft() && !canWallJumpToRight()) {
                if(body.getLinearVelocity().y < 0) {
                    body.setLinearVelocity(body.getLinearVelocity().x, 0);
                }

                body.applyLinearImpulse(new Vector2(0, 9f), body.getWorldCenter(), true);
                canDoubleJump = false;
            }

            inputGiven = true;
        }

        // Right wall jump
        if((Gdx.input.isKeyJustPressed(Input.Keys.Z) || Gdx.input.isKeyJustPressed(Input.Keys.W)) && canWallJumpToLeft() && !canJump()) {
            if(body.getLinearVelocity().y < 0) {
                body.setLinearVelocity(0, 0);
            }

            body.applyLinearImpulse(new Vector2(-10f, 10f), body.getWorldCenter(), true);
            inputGiven = true;
            canDoubleJump = true;
        }

        // Left wall jump
        if((Gdx.input.isKeyJustPressed(Input.Keys.Z) || Gdx.input.isKeyJustPressed(Input.Keys.W)) && canWallJumpToRight() && !canJump()) {
            if(body.getLinearVelocity().y < 0) {
                body.setLinearVelocity(0, 0);
            }
            body.applyLinearImpulse(new Vector2(10f, 10f), body.getWorldCenter(), true);
            inputGiven = true;
            canDoubleJump = true;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            TIME_SLOWDOWN_MODIFIER = TIME_SLOWDOWN_MODIFIER == 1 ? 4 : 1;
        }

        // Down charge
        if((Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) && canChargeDownwards) {
            float yVelocity = body.getLinearVelocity().y;
            if(yVelocity >= 0) {
                body.setLinearVelocity(body.getLinearVelocity().x, 0);
            }

            body.applyLinearImpulse(new Vector2(0, -20f), body.getWorldCenter(), true);
            canChargeDownwards = false;
            inputGiven = true;
        }

        // Dash right
        if((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && body.getLinearVelocity().x <= DASH_SPEED_CAP) {
            if(currentTime - lastTimeDashed > 1) {
                body.applyLinearImpulse(new Vector2(80f, 0), body.getWorldCenter(), true);
                lastTimeDashed = currentTime;
            }
            inputGiven = true;
        }

        // Dash left
        if((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && body.getLinearVelocity().x >= -DASH_SPEED_CAP) {
            if(currentTime - lastTimeDashed > 1) {
                body.applyLinearImpulse(new Vector2(-80f, 0), body.getWorldCenter(), true);
                lastTimeDashed = currentTime;
            }
            inputGiven = true;
        }

        // Sprint right
        if((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) && Gdx.input.isKeyPressed(Input.Keys.X) && body.getLinearVelocity().x <= SPRINT_SPEED_CAP) {
            body.applyLinearImpulse(new Vector2(0.5f, 0), body.getWorldCenter(), true);
            inputGiven = true;
        }

        // Sprint left
        if((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) && Gdx.input.isKeyPressed(Input.Keys.X) && body.getLinearVelocity().x >= -SPRINT_SPEED_CAP) {
            body.applyLinearImpulse(new Vector2(-0.5f, 0), body.getWorldCenter(), true);
            inputGiven = true;
        }

        /*
        // Limit movement on Y-axis
        if(body.getLinearVelocity().y >= 40) {
            body.setLinearVelocity(body.getLinearVelocity().x, 40);
        }
        */


        // Manual deceleration
        if(!inputGiven) {
            if(body.getLinearVelocity().x > 0.3f) {
                body.applyLinearImpulse(new Vector2(-0.2f, 0), body.getWorldCenter(), true);
            } //else if(body.getLinearVelocity().x > 0) {
               //body.setLinearVelocity(0, body.getLinearVelocity().y);
           //}

            if(body.getLinearVelocity().x < -0.3f) {
                body.applyLinearImpulse(new Vector2(0.2f, 0), body.getWorldCenter(), true);
            }// else if(body.getLinearVelocity().x < 0) {
                //body.setLinearVelocity(0, body.getLinearVelocity().y);
           //}
        }
    }

    private boolean canJump() {
        return footContactCounter > 0;
    }

    private boolean canWallJumpToLeft() {
        return rightContactCounter > 0;
    }

    private boolean canWallJumpToRight() {
        return leftContactCounter > 0;
    }
}
