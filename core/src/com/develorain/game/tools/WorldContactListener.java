package com.develorain.game.tools;

import com.badlogic.gdx.physics.box2d.*;
import com.develorain.game.entities.Enemy;

public class WorldContactListener implements ContactListener {
    public static final short WHITE_LINE_BIT = 1;
    public static final short BLUE_LINE_BIT = 2;
    public static final short RED_LINE_BIT = 4;
    public static final short PLAYER_BIT = 8;
    public static final short PLAYER_FOOT_SENSOR_BIT = 16; // remove
    public static final short PLAYER_LEFT_SENSOR_BIT = 32;  // remove
    public static final short PLAYER_RIGHT_SENSOR_BIT = 64;  // remove
    public static final short WHITE_ENEMY_BIT = 128;
    public static final short BLUE_ENEMY_BIT = 256;
    public static final short RED_ENEMY_BIT = 512;
    public static final short BOUNDARY_LINE_BIT = 1024;
    public static final short END_LINE_BIT = 2048;
    public static final short UNCLIMBABLE_LINE_BIT = 4096;
    public static final short SCRIPTED_EVENT_TRIGGER_BIT = 8192;
    // last bit is -32768

    private PlayerController playerController;
    private LevelCreator levelCreator;

    public WorldContactListener(PlayerController playerController, LevelCreator levelCreator) {
        this.playerController = playerController;
        this.levelCreator = levelCreator;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        // TODO: must rewrite switch statement
        switch (cDef) {
            case PLAYER_BIT | SCRIPTED_EVENT_TRIGGER_BIT:
                /*
                // not working since only fired off once, need to use boolean to see if still in contact
                levelCreator.fitViewport.setWorldSize(levelCreator.fitViewport.getWorldWidth() + (levelCreator.fitViewport.getWorldWidth() * 0.02f),
                        levelCreator.fitViewport.getWorldHeight() + (levelCreator.fitViewport.getWorldHeight() * 0.02f));
                levelCreator.fitViewport.apply();
                */
                break;

            case PLAYER_FOOT_SENSOR_BIT | WHITE_LINE_BIT:
                playerController.footContactCounter++;
                break;
            case PLAYER_FOOT_SENSOR_BIT | BLUE_LINE_BIT:
                playerController.footContactCounter++;
                break;
            case PLAYER_FOOT_SENSOR_BIT | RED_LINE_BIT:
                playerController.footContactCounter++;
                break;

            case PLAYER_LEFT_SENSOR_BIT | WHITE_LINE_BIT:
                playerController.leftContactCounter++;
                break;
            case PLAYER_LEFT_SENSOR_BIT | BLUE_LINE_BIT:
                playerController.leftContactCounter++;
                break;
            case PLAYER_LEFT_SENSOR_BIT | RED_LINE_BIT:
                playerController.leftContactCounter++;
                break;

            case PLAYER_RIGHT_SENSOR_BIT | WHITE_LINE_BIT:
                playerController.rightContactCounter++;
                break;
            case PLAYER_RIGHT_SENSOR_BIT | BLUE_LINE_BIT:
                playerController.rightContactCounter++;
                break;
            case PLAYER_RIGHT_SENSOR_BIT | RED_LINE_BIT:
                playerController.rightContactCounter++;
                break;

            case WHITE_ENEMY_BIT | BOUNDARY_LINE_BIT:
                if (fixA.getFilterData().categoryBits == WHITE_ENEMY_BIT) {
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                } else {
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                }
                break;
            case BLUE_ENEMY_BIT | BOUNDARY_LINE_BIT:
                if (fixA.getFilterData().categoryBits == WHITE_ENEMY_BIT) {
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                } else {
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                }
                break;
            case RED_ENEMY_BIT | BOUNDARY_LINE_BIT:
                if (fixA.getFilterData().categoryBits == WHITE_ENEMY_BIT) {
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                } else {
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                }
                break;

            case WHITE_ENEMY_BIT | BLUE_ENEMY_BIT:
                ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case WHITE_ENEMY_BIT | RED_ENEMY_BIT:
                ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;

            case WHITE_ENEMY_BIT:
                ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case BLUE_ENEMY_BIT:
                ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case RED_ENEMY_BIT:
                ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;

            case PLAYER_BIT | WHITE_ENEMY_BIT:
                playerController.shouldRespawn = true;
                /*
                long id = Illumination.assetManager.get("Audio/Sounds/hitsound.wav", Sound.class).play();
                Illumination.assetManager.get("Audio/Sounds/hitsound.wav", Sound.class).setPitch(id, 0.1f);
                */
                break;
            case PLAYER_BIT | BLUE_ENEMY_BIT:
                /*
                playerController.shouldRespawn = true;
                Illumination.assetManager.get("Audio/Sounds/hitsound.wav", Sound.class).play();
                */
                break;
            case PLAYER_BIT | RED_ENEMY_BIT:
                /*
                playerController.shouldRespawn = true;
                Illumination.assetManager.get("Audio/Sounds/hitsound.wav", Sound.class).play();
                */
                break;

            case PLAYER_BIT | END_LINE_BIT:
                levelCreator.loadNextLevel();
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case PLAYER_BIT | SCRIPTED_EVENT_TRIGGER_BIT:
                levelCreator.fitViewport.setWorldSize(levelCreator.fitViewport.getWorldWidth() - (levelCreator.fitViewport.getWorldWidth() * 0.02f) / 60,
                        levelCreator.fitViewport.getWorldHeight() - (levelCreator.fitViewport.getWorldHeight() * 0.02f) / 60);
                levelCreator.fitViewport.apply();
                break;

            case PLAYER_FOOT_SENSOR_BIT | WHITE_LINE_BIT:
                playerController.footContactCounter--;
                playerController.canDoubleJump = true;
                break;
            case PLAYER_FOOT_SENSOR_BIT | BLUE_LINE_BIT:
                playerController.footContactCounter--;
                playerController.canDoubleJump = true;
                break;
            case PLAYER_FOOT_SENSOR_BIT | RED_LINE_BIT:
                playerController.footContactCounter--;
                playerController.canDoubleJump = true;
                break;

            case PLAYER_LEFT_SENSOR_BIT | WHITE_LINE_BIT:
                playerController.leftContactCounter--;
                playerController.canDoubleJump = true;
                break;
            case PLAYER_LEFT_SENSOR_BIT | BLUE_LINE_BIT:
                playerController.leftContactCounter--;
                playerController.canDoubleJump = true;
                break;
            case PLAYER_LEFT_SENSOR_BIT | RED_LINE_BIT:
                playerController.leftContactCounter--;
                playerController.canDoubleJump = true;
                break;

            case PLAYER_RIGHT_SENSOR_BIT | WHITE_LINE_BIT:
                playerController.rightContactCounter--;
                playerController.canDoubleJump = true;
                break;
            case PLAYER_RIGHT_SENSOR_BIT | BLUE_LINE_BIT:
                playerController.rightContactCounter--;
                playerController.canDoubleJump = true;
                break;
            case PLAYER_RIGHT_SENSOR_BIT | RED_LINE_BIT:
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
