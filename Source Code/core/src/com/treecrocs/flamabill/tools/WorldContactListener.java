package com.treecrocs.flamabill.tools;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.treecrocs.flamabill.characters.Player;
import com.treecrocs.flamabill.worldobjects.Campfire;

public class WorldContactListener implements ContactListener {


    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case EntityCategory.PLAYER | EntityCategory.DEATH:
                if (fixA.getFilterData().categoryBits == EntityCategory.PLAYER)
                    ((Player) fixA.getUserData()).dieToWater();
                else
                    ((Player) fixB.getUserData()).dieToWater();
                break;
            case EntityCategory.PLAYER | EntityCategory.CHECKPOINT:
                if (fixA.getFilterData().categoryBits == EntityCategory.PLAYER) { //false
                    Vector2 currPos = ((Player) fixA.getUserData()).playerBody.getPosition();
                    ((Player) fixA.getUserData()).updateSpawn(currPos);
                    ((Player) fixA.getUserData()).replenishHealth();
                }
                else {
                    Vector2 currPos = ((Player) fixB.getUserData()).playerBody.getPosition();
                    ((Player) fixB.getUserData()).updateSpawn(currPos);
                    ((Player) fixB.getUserData()).replenishHealth();
                }
                break;
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
