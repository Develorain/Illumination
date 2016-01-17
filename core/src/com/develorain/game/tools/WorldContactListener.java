package com.develorain.game.tools;

import com.badlogic.gdx.physics.box2d.*;
import com.develorain.game.entities.Enemy;
import com.develorain.game.screens.PlayScreen;

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

    private GameInputHandler gameInputHandler;
    private PlayScreen playScreen;

    public WorldContactListener(GameInputHandler gameInputHandler, PlayScreen playScreen) {
        this.gameInputHandler = gameInputHandler;
        this.playScreen = playScreen;
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
                playScreen.fitViewport.setWorldSize(playScreen.fitViewport.getWorldWidth() + (playScreen.fitViewport.getWorldWidth() * 0.02f),
                        playScreen.fitViewport.getWorldHeight() + (playScreen.fitViewport.getWorldHeight() * 0.02f));
                playScreen.fitViewport.apply();
                */
                break;

            case PLAYER_FOOT_SENSOR_BIT | WHITE_LINE_BIT:
                gameInputHandler.footContactCounter++;
                break;
            case PLAYER_FOOT_SENSOR_BIT | BLUE_LINE_BIT:
                gameInputHandler.footContactCounter++;
                break;
            case PLAYER_FOOT_SENSOR_BIT | RED_LINE_BIT:
                gameInputHandler.footContactCounter++;
                break;

            case PLAYER_LEFT_SENSOR_BIT | WHITE_LINE_BIT:
                gameInputHandler.leftContactCounter++;
                break;
            case PLAYER_LEFT_SENSOR_BIT | BLUE_LINE_BIT:
                gameInputHandler.leftContactCounter++;
                break;
            case PLAYER_LEFT_SENSOR_BIT | RED_LINE_BIT:
                gameInputHandler.leftContactCounter++;
                break;

            case PLAYER_RIGHT_SENSOR_BIT | WHITE_LINE_BIT:
                gameInputHandler.rightContactCounter++;
                break;
            case PLAYER_RIGHT_SENSOR_BIT | BLUE_LINE_BIT:
                gameInputHandler.rightContactCounter++;
                break;
            case PLAYER_RIGHT_SENSOR_BIT | RED_LINE_BIT:
                gameInputHandler.rightContactCounter++;
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
                gameInputHandler.shouldRespawn = true;
                /*
                long id = Illumination.assetManager.get("Audio/Sounds/hitsound.wav", Sound.class).play();
                Illumination.assetManager.get("Audio/Sounds/hitsound.wav", Sound.class).setPitch(id, 0.1f);
                */
                break;
            case PLAYER_BIT | BLUE_ENEMY_BIT:
                /*
                gameInputHandler.shouldRespawn = true;
                Illumination.assetManager.get("Audio/Sounds/hitsound.wav", Sound.class).play();
                */
                break;
            case PLAYER_BIT | RED_ENEMY_BIT:
                /*
                gameInputHandler.shouldRespawn = true;
                Illumination.assetManager.get("Audio/Sounds/hitsound.wav", Sound.class).play();
                */
                break;

            case PLAYER_BIT | END_LINE_BIT:
                playScreen.loadNextLevel();
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            /*
            case PLAYER_BIT | SCRIPTED_EVENT_TRIGGER_BIT:
                playScreen.fitViewport.setWorldSize(playScreen.fitViewport.getWorldWidth() - (playScreen.fitViewport.getWorldWidth() * 0.02f) / 60,
                        playScreen.fitViewport.getWorldHeight() - (playScreen.fitViewport.getWorldHeight() * 0.02f) / 60);
                playScreen.fitViewport.apply();
                break;
            */

            case PLAYER_FOOT_SENSOR_BIT | WHITE_LINE_BIT:
                gameInputHandler.footContactCounter--;
                gameInputHandler.canDoubleJump = true;
                break;
            case PLAYER_FOOT_SENSOR_BIT | BLUE_LINE_BIT:
                gameInputHandler.footContactCounter--;
                gameInputHandler.canDoubleJump = true;
                break;
            case PLAYER_FOOT_SENSOR_BIT | RED_LINE_BIT:
                gameInputHandler.footContactCounter--;
                gameInputHandler.canDoubleJump = true;
                break;

            case PLAYER_LEFT_SENSOR_BIT | WHITE_LINE_BIT:
                gameInputHandler.leftContactCounter--;
                gameInputHandler.canDoubleJump = true;
                break;
            case PLAYER_LEFT_SENSOR_BIT | BLUE_LINE_BIT:
                gameInputHandler.leftContactCounter--;
                gameInputHandler.canDoubleJump = true;
                break;
            case PLAYER_LEFT_SENSOR_BIT | RED_LINE_BIT:
                gameInputHandler.leftContactCounter--;
                gameInputHandler.canDoubleJump = true;
                break;

            case PLAYER_RIGHT_SENSOR_BIT | WHITE_LINE_BIT:
                gameInputHandler.rightContactCounter--;
                gameInputHandler.canDoubleJump = true;
                break;
            case PLAYER_RIGHT_SENSOR_BIT | BLUE_LINE_BIT:
                gameInputHandler.rightContactCounter--;
                gameInputHandler.canDoubleJump = true;
                break;
            case PLAYER_RIGHT_SENSOR_BIT | RED_LINE_BIT:
                gameInputHandler.rightContactCounter--;
                gameInputHandler.canDoubleJump = true;
                break;
        }

        gameInputHandler.canChargeDownwards = true;
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
