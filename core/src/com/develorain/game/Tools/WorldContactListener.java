package com.develorain.game.Tools;

import com.badlogic.gdx.physics.box2d.*;

public class WorldContactListener implements ContactListener {
    // Instead of detecting that you're touching the ground using collisions, find the nearest tile and check player proximity to it
    // MAKE A SENSOR THAT IS LARGER THAN PLAYER'S BODY AND CHECK FOR NORMAL VECTOR INSTEAD OF MAKING MULTIPLE SENSORS

    private PlayerController playerController;

    public WorldContactListener(PlayerController playerController) {
        this.playerController = playerController;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        // If the player is on the ground
        if(fixA.getUserData() == "foot sensor" || fixB.getUserData() == "foot sensor") {
            playerController.canJump = true;
        }

        // If the player is touch the left wall
        if(fixA.getUserData() == "left sensor" || fixB.getUserData() == "left sensor") {
            playerController.canWallJumpToRight = true;
        }

        // If the player is touch the right wall
        if(fixA.getUserData() == "right sensor" || fixB.getUserData() == "right sensor") {
            playerController.canWallJumpToLeft = true;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        // If the player just left the ground
        if(fixA.getUserData() == "foot sensor" || fixB.getUserData() == "foot sensor") {
            playerController.canJump = false;
        }

        // If the player just left the left wall
        if(fixA.getUserData() == "left sensor" || fixB.getUserData() == "left sensor") {
            playerController.canWallJumpToRight = false;
        }

        // If the player just left the right wall
        if(fixA.getUserData() == "right sensor" || fixB.getUserData() == "right sensor") {
            playerController.canWallJumpToLeft = false;
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
