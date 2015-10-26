package com.develorain.game.Tools;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import java.util.ArrayList;

public class WorldContactListener implements ContactListener {
    // Instead of detecting that you're touching the ground using collisions, find the nearest tile and check player proximity to it
    // MAKE A SENSOR THAT IS LARGER THAN PLAYER'S BODY AND CHECK FOR NORMAL VECTOR INSTEAD OF MAKING MULTIPLE SENSORS

    private ArrayList<ContactWrapper> contactWrappers = new ArrayList<>();
    private PlayerController playerController;

    public WorldContactListener(PlayerController playerController) {
        this.playerController = playerController;
    }

    @Override
    public void beginContact(Contact contact) {
        contactWrappers.add(new ContactWrapper(contact));
        Vector2 normal = contact.getWorldManifold().getNormal();
        System.out.println(normal);
        // If the player is on the ground
        if (normal.y > 0f) {
            playerController.canJump = true;
        }

        // If the player is colliding to a wall on player's right
        if (normal.x == -1f && normal.y == 0f) {
            playerController.canWallJumpToLeft = true;
        }

        // If the player is colliding to a wall on player's left
        if (normal.x == 1f && normal.y == 0f) {
            playerController.canWallJumpToRight = true;
        }

    }

    @Override
    public void endContact(Contact contact) {
        for (ContactWrapper indexContactWrapper : contactWrappers) {
            if (indexContactWrapper.fixtureA == contact.getFixtureA() && indexContactWrapper.fixtureB == contact.getFixtureB()) {
                if (indexContactWrapper.normalVectorY > 0f) {
                    playerController.canJump = false;
                }

                if (indexContactWrapper.normalVectorX == -1f && indexContactWrapper.normalVectorY == 0f) {
                    playerController.canWallJumpToLeft = false;
                }

                if (indexContactWrapper.normalVectorX == 1f && indexContactWrapper.normalVectorY == 0f) {
                    playerController.canWallJumpToRight = false;
                }

                playerController.canChargeDownwards = true;
                contactWrappers.remove(indexContactWrapper);
                break;
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
