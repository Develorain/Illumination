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
            case PLAYER_LEFT_SENSOR_BIT | DEFAULT_SLOPE_BIT:
                playerController.leftContactCounter++;
                break;
            case PLAYER_RIGHT_SENSOR_BIT | DEFAULT_SLOPE_BIT:
                playerController.rightContactCounter++;
                break;
        }

        playerController.canDoubleJump = false;
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case PLAYER_FOOT_SENSOR_BIT | DEFAULT_SLOPE_BIT:
                playerController.footContactCounter--;
                break;
            case PLAYER_LEFT_SENSOR_BIT | DEFAULT_SLOPE_BIT:
                playerController.leftContactCounter--;
                break;
            case PLAYER_RIGHT_SENSOR_BIT | DEFAULT_SLOPE_BIT:
                playerController.rightContactCounter--;
                break;
        }

        // if endcontact is with player
        // TODO: Store the player fixture and check if fixtureA or fixtureB is equal to the player fixture. If it is, below lines are run
        playerController.canDoubleJump = true;
        playerController.canChargeDownwards = true;
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
