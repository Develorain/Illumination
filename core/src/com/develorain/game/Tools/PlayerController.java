package com.develorain.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import static com.develorain.game.Illumination.PPM;
import static com.develorain.game.Screens.PlayScreen.currentTime;

public class PlayerController {
    // Reference of body to be controlled
    private Body body;

    // Variables
    private float lastTimeRightKeyPressed = -100;
    private float lastTimeLeftKeyPressed = -100;
    private float lastTimeDashed = -100;
    public boolean canJump = false;
    public boolean canDoubleJump = false;
    public boolean canWallJumpToLeft = false;
    public boolean canWallJumpToRight = false;
    public boolean canChargeDownwards = false;

    public PlayerController(Body body) {
        this.body = body;
    }

    public void handleInput() {
        boolean inputGiven = false;
        // Runs if right is pressed or held
        if((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) && body.getLinearVelocity().x <= 7) {
            body.applyLinearImpulse(new Vector2(1.5f, 0), body.getWorldCenter(), true);
            inputGiven = true;
        }

        // Runs if left is pressed or held
        if((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) && body.getLinearVelocity().x >= -7) {
            body.applyLinearImpulse(new Vector2(-1.5f, 0), body.getWorldCenter(), true);
            inputGiven = true;
        }

        // Jump
        if((Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) && canJump) {
            body.applyLinearImpulse(new Vector2(0, 10f), body.getWorldCenter(), true);
            inputGiven = true;
        }

        // Double jump
        if((Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) && canDoubleJump && !canJump && !canWallJumpToLeft && !canWallJumpToRight) {
            body.applyLinearImpulse(new Vector2(0, 8f), body.getWorldCenter(), true);
            canDoubleJump = false;
        }

        // Right wall jump
        if((Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) && canWallJumpToLeft && !canJump) {
            body.applyLinearImpulse(new Vector2(-10f, 10f), body.getWorldCenter(), true);
            inputGiven = true;
        }

        // Left wall jump
        if((Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) && canWallJumpToRight && !canJump) {
            body.applyLinearImpulse(new Vector2(10f, 10f), body.getWorldCenter(), true);
            inputGiven = true;
        }

        // Down charge
        if((Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) && canChargeDownwards) {
            body.setLinearVelocity(body.getLinearVelocity().x, 0);
            body.applyLinearImpulse(new Vector2(0, -20f), body.getWorldCenter(), true);
            canChargeDownwards = false;
            inputGiven = true;
        }

        // Dash right
        if((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && body.getLinearVelocity().x <= 25) {
            if(currentTime - lastTimeDashed > 1) {
                body.applyLinearImpulse(new Vector2(80f, 0), body.getWorldCenter(), true);
                lastTimeDashed = currentTime;
            }
            inputGiven = true;
        }

        // Dash left
        if((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && body.getLinearVelocity().x >= -25) {
            if(currentTime - lastTimeDashed > 1) {
                body.applyLinearImpulse(new Vector2(-80f, 0), body.getWorldCenter(), true);
                lastTimeDashed = currentTime;
            }
            inputGiven = true;
        }

        // Sprint right
        if((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) && Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT) && body.getLinearVelocity().x <= 15) {
            body.applyLinearImpulse(new Vector2(0.5f, 0), body.getWorldCenter(), true);
            inputGiven = true;
        }

        // Sprint left
        if((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) && Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT) && body.getLinearVelocity().x >= -15) {
            body.applyLinearImpulse(new Vector2(-0.5f, 0), body.getWorldCenter(), true);
            inputGiven = true;
        }

        // Teleport right
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            if(currentTime - lastTimeRightKeyPressed < 0.2f) {
                body.setTransform(body.getPosition().x + 100 / PPM, body.getPosition().y, 0);
                lastTimeRightKeyPressed = -100;
            } else {
                lastTimeRightKeyPressed = currentTime;
            }
        }

        // Teleport left
        if((Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.A))) {
            if(currentTime - lastTimeLeftKeyPressed < 0.2f) {
                body.setTransform(body.getPosition().x - 100 / PPM, body.getPosition().y, 0);
                lastTimeLeftKeyPressed = -100;
            } else {
                lastTimeLeftKeyPressed = currentTime;
            }
        }

        // Limit movement on Y-axis
        if(body.getLinearVelocity().y >= 20) {
            body.setLinearVelocity(body.getLinearVelocity().x, 20);
        }

        // Manual deceleration
        if(!inputGiven) {
            if(body.getLinearVelocity().x - 0.3f > 0) {
                body.setLinearVelocity(body.getLinearVelocity().x - 0.3f, body.getLinearVelocity().y);
            } else if(body.getLinearVelocity().x > 0) {
                body.setLinearVelocity(0, body.getLinearVelocity().y);
            }

            if(body.getLinearVelocity().x + 0.3f < 0) {
                body.setLinearVelocity(body.getLinearVelocity().x + 0.3f, body.getLinearVelocity().y);
            } else if(body.getLinearVelocity().x < 0) {
                body.setLinearVelocity(0, body.getLinearVelocity().y);
            }
        }
    }
}
