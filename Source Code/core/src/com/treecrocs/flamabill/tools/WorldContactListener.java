package com.treecrocs.flamabill.tools;

import com.badlogic.gdx.physics.box2d.*;
import com.treecrocs.flamabill.characters.Player;

public class WorldContactListener implements ContactListener {


    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case EntityCategory.PLAYER | EntityCategory.DEATH:
                if(fixA.getFilterData().categoryBits == EntityCategory.PLAYER)
                    ((Player) fixA.getUserData()).die();
                else
                    ((Player) fixB.getUserData()).die();
                break;
            case EntityCategory.PLAYER | EntityCategory.CHECKPOINT:
                if(fixA.getFilterData().categoryBits == EntityCategory.PLAYER)
                    ((Player) fixA.getUserData()).replenishHealth();
                else
                    ((Player) fixB.getUserData()).replenishHealth();
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
