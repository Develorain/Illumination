package com.develorain.game.Tools;

import com.badlogic.gdx.physics.box2d.*;

public class WorldContactListener implements ContactListener {
    private PlayerController playerController;

    public WorldContactListener(PlayerController playerController) {
        this.playerController = playerController;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        // If the player is on the ground
        if (fixA.getUserData() == "foot sensor" || fixB.getUserData() == "foot sensor") {
            playerController.footContactCounter++;
        }

        // If the player is touch the left wall
        if (fixA.getUserData() == "left sensor" || fixB.getUserData() == "left sensor") {
            playerController.leftContactCounter++;
        }

        // If the player is touch the right wall
        if (fixA.getUserData() == "right sensor" || fixB.getUserData() == "right sensor") {
            playerController.rightContactCounter++;
        }

        playerController.canDoubleJump = false;
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        // If the player just left the ground
        if (fixA.getUserData() == "foot sensor" || fixB.getUserData() == "foot sensor") {
            playerController.footContactCounter--;
        }

        // If the player just left the left wall
        if (fixA.getUserData() == "left sensor" || fixB.getUserData() == "left sensor") {
            playerController.leftContactCounter--;
        }

        // If the player just left the right wall
        if (fixA.getUserData() == "right sensor" || fixB.getUserData() == "right sensor") {
            playerController.rightContactCounter--;
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
