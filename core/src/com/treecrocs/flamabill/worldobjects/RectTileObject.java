package com.treecrocs.flamabill.worldobjects;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.treecrocs.flamabill.Flamabill;
import com.treecrocs.flamabill.screens.PlayScreen;
import com.treecrocs.flamabill.tools.EntityCategory;

public abstract class RectTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;

    public RectTileObject(World world, TiledMap map, Rectangle bounds) {
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        // Sets body type and position
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / Flamabill.PPM, (bounds.getY() + bounds.getHeight() / 2) / Flamabill.PPM);

        body = world.createBody(bdef);

        shape.setAsBox((bounds.getWidth() / 2) / Flamabill.PPM, (bounds.getHeight() / 2) / Flamabill.PPM);
        fdef.shape = shape;
        body.createFixture(fdef);
    }
}
