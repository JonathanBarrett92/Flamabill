package com.treecrocs.flamabill.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.treecrocs.flamabill.Flamabill;

// Object loading needs to be split into different classes
public class B2WorldLoader {
    public B2WorldLoader(World world, TiledMap map) {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        // Create ground bodies/ fixtures
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            // Sets body type and position
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Flamabill.PPM, (rect.getY() + rect.getHeight() / 2) / Flamabill.PPM);


            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) / Flamabill.PPM, (rect.getHeight() / 2) / Flamabill.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }



    }
}
