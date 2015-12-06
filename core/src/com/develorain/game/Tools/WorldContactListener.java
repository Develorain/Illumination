package com.develorain.game.Tools;

import com.badlogic.gdx.physics.box2d.*;
import com.develorain.game.Sprites.Enemy;
import com.develorain.game.Sprites.Exploder;
import com.develorain.game.Tools.Slopes.EndSlope;

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

        if (fixA.getUserData().getClass() == EndSlope.class) {
            System.out.println("woot");
        } else {
            //((Enemy) fixB.getUserData()).reverseVelocity(true, false);
        }

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        // if object has more than one bit, must rewrite switch statement
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

            case DEFAULT_ENEMY_BIT | BOUNDARY_SLOPE_BIT:
                if (fixA.getFilterData().categoryBits == DEFAULT_ENEMY_BIT) {
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                } else {
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                }
                break;
            case NORMAL_ENEMY_BIT | BOUNDARY_SLOPE_BIT:
                if (fixA.getFilterData().categoryBits == DEFAULT_ENEMY_BIT) {
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                } else {
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                }
                break;
            case ALTERNATE_ENEMY_BIT | BOUNDARY_SLOPE_BIT:
                if (fixA.getFilterData().categoryBits == DEFAULT_ENEMY_BIT) {
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                } else {
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                }
                break;

            case DEFAULT_ENEMY_BIT | NORMAL_ENEMY_BIT:
                ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case DEFAULT_ENEMY_BIT | ALTERNATE_ENEMY_BIT:
                ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;

            case DEFAULT_ENEMY_BIT:
                ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case NORMAL_ENEMY_BIT:
                ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case ALTERNATE_ENEMY_BIT:
                ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;

            case PLAYER_BIT | DEFAULT_ENEMY_BIT:
                playerController.shouldRespawn = true;
                //Illumination.manager.get("Audio/Sounds/hitsound.wav", Sound.class).play();
                break;
            case PLAYER_BIT | NORMAL_ENEMY_BIT:
                playerController.shouldRespawn = true;
                //Illumination.manager.get("Audio/Sounds/hitsound.wav", Sound.class).play();
                break;
            case PLAYER_BIT | ALTERNATE_ENEMY_BIT:
                playerController.shouldRespawn = true;
                //Illumination.manager.get("Audio/Sounds/hitsound.wav", Sound.class).play();
                break;

            case PLAYER_BIT | END_SLOPE_BIT:
                levelCreator.loadNextLevel();
                break;

            case PROJECTILE_BIT | DEFAULT_SLOPE_BIT:
                if (fixA.getFilterData().categoryBits == PROJECTILE_BIT) {
                    //((Enemy) fixA.getUserData()).reverseVelocity(true, false);

                    System.out.println("ran");
                    ((Exploder) fixA.getUserData()).destroyProjectiles();
                } else {
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                }
                break;
            case PROJECTILE_BIT | NORMAL_SLOPE_BIT:
                break;
            case PROJECTILE_BIT | ALTERNATE_SLOPE_BIT:
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
