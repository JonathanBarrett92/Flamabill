package com.treecrocs.flamabill.tools;


/*
    These values are for Box2D Collision Filtering
    This used to be an enum but had problems with it being an enum for contact listener.
 */
public class EntityCategory {

    public static final short PLAYER = 1<<0;
    public static final short HEADSENSOR = 1<<1;
    public static final short GROUND = 1<<2;
    public static final short GROUNDSENSOR = 1<<3;
    public static final short WALLSENSOR = 1<<4;
    public static final short ENEMY = 1<<5;
    public static final short DEATH = 1<<6;
    public static final short CHECKPOINT = 1<<7;
    public static final short FINISHLINE = 1<<8;

}
