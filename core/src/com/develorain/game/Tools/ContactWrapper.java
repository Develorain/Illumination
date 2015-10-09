package com.develorain.game.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;

public class ContactWrapper {

    public float normalVectorX, normalVectorY;
    public Fixture fixtureA, fixtureB;

    public ContactWrapper(Contact contact) {
        fixtureA = contact.getFixtureA();
        fixtureB = contact.getFixtureB();
        normalVectorX = contact.getWorldManifold().getNormal().x;
        normalVectorY = contact.getWorldManifold().getNormal().y;
    }
}
