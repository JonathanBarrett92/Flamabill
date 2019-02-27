package com.treecrocs.flamabill.tools;


/*
    These values are for Box2D Collision Filtering
 */
public enum EntityCategory {
    PLAYER((short)(1<<0)),
    GROUND((short)(1<<1)),
    SENSOR((short)(1<<2)),
    ENEMY((short)(1<<3));

    private final short value;

    EntityCategory(short value) {
        this.value = value;
    }

    public short getFilter() {
        return value;
    }


}
