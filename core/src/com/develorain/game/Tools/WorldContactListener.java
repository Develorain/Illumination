package com.develorain.game.Tools;

import com.badlogic.gdx.physics.box2d.*;

import static com.develorain.game.Illumination.*;

public class WorldContactListener implements ContactListener {
    private PlayerController playerController;

    public WorldContactListener(PlayerController playerController) {
        this.playerController = playerController;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case PLAYER_FOOT_SENSOR_BIT | DEFAULT_SLOPE_BIT:
                playerController.footContactCounter++;
                break;
            case PLAYER_FOOT_SENSOR_BIT | NORMAL_SLOPE_BIT:
                playerController.footContactCounter++;
                break;
            case PLAYER_FOOT_SENSOR_BIT | ALTERNATE_SLOPE_BIT:
                playerController.footContactCounter++;
                break;

            case PLAYER_LEFT_SENSOR_BIT | DEFAULT_SLOPE_BIT:
                playerController.leftContactCounter++;
                break;
            case PLAYER_LEFT_SENSOR_BIT | NORMAL_SLOPE_BIT:
                playerController.leftContactCounter++;
                break;
            case PLAYER_LEFT_SENSOR_BIT | ALTERNATE_SLOPE_BIT:
                playerController.leftContactCounter++;
                break;

            case PLAYER_RIGHT_SENSOR_BIT | DEFAULT_SLOPE_BIT:
                playerController.rightContactCounter++;
                break;
            case PLAYER_RIGHT_SENSOR_BIT | NORMAL_SLOPE_BIT:
                playerController.rightContactCounter++;
                break;
            case PLAYER_RIGHT_SENSOR_BIT | ALTERNATE_SLOPE_BIT:
                playerController.rightContactCounter++;
                break;

            case PLAYER_BIT | ENEMY_BIT:
                System.out.println("PLAYER DIED");
                //Illumination.manager.get("audio/sounds/hitsound.wav", Sound.class).play();
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case PLAYER_FOOT_SENSOR_BIT | DEFAULT_SLOPE_BIT:
                playerController.footContactCounter--;
                playerController.canDoubleJump = true;
                break;
            case PLAYER_FOOT_SENSOR_BIT | NORMAL_SLOPE_BIT:
                playerController.footContactCounter--;
                playerController.canDoubleJump = true;
                break;
            case PLAYER_FOOT_SENSOR_BIT | ALTERNATE_SLOPE_BIT:
                playerController.footContactCounter--;
                playerController.canDoubleJump = true;
                break;

            case PLAYER_LEFT_SENSOR_BIT | DEFAULT_SLOPE_BIT:
                playerController.leftContactCounter--;
                playerController.canDoubleJump = true;
                break;
            case PLAYER_LEFT_SENSOR_BIT | NORMAL_SLOPE_BIT:
                playerController.leftContactCounter--;
                playerController.canDoubleJump = true;
                break;
            case PLAYER_LEFT_SENSOR_BIT | ALTERNATE_SLOPE_BIT:
                playerController.leftContactCounter--;
                playerController.canDoubleJump = true;
                break;

            case PLAYER_RIGHT_SENSOR_BIT | DEFAULT_SLOPE_BIT:
                playerController.rightContactCounter--;
                playerController.canDoubleJump = true;
                break;
            case PLAYER_RIGHT_SENSOR_BIT | NORMAL_SLOPE_BIT:
                playerController.rightContactCounter--;
                playerController.canDoubleJump = true;
                break;
            case PLAYER_RIGHT_SENSOR_BIT | ALTERNATE_SLOPE_BIT:
                playerController.rightContactCounter--;
                playerController.canDoubleJump = true;
                break;
        }

        playerController.canChargeDownwards = true;
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
