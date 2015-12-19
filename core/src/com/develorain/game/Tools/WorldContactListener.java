package com.develorain.game.Tools;

import com.badlogic.gdx.physics.box2d.*;
import com.develorain.game.Entities.Enemy;
import com.develorain.game.Entities.Exploder;

import static com.develorain.game.Illumination.*;

public class WorldContactListener implements ContactListener {
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

        // if object has more than one bit, must rewrite switch statement
        switch (cDef) {
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
                //Illumination.manager.get("Audio/Sounds/hitsound.wav", Sound.class).play();
                break;
            case PLAYER_BIT | BLUE_ENEMY_BIT:
                playerController.shouldRespawn = true;
                //Illumination.manager.get("Audio/Sounds/hitsound.wav", Sound.class).play();
                break;
            case PLAYER_BIT | RED_ENEMY_BIT:
                playerController.shouldRespawn = true;
                //Illumination.manager.get("Audio/Sounds/hitsound.wav", Sound.class).play();
                break;

            case PROJECTILE_BIT | WHITE_LINE_BIT:
                if (fixA.getFilterData().categoryBits == PROJECTILE_BIT) {
                    ((Exploder) fixA.getUserData()).destroyProjectiles();
                } else {
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                }
                break;
            case PROJECTILE_BIT | BLUE_LINE_BIT:
                break;
            case PROJECTILE_BIT | RED_LINE_BIT:
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
