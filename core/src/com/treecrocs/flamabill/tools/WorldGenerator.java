package com.treecrocs.flamabill.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.treecrocs.flamabill.Flamabill;


public class WorldGenerator {

    private World world;
    private TiledMap map;
    private BodyDef bodyDef;
    private PolygonShape shape;
    private FixtureDef fixtureDef;
    private Body body;

    public WorldGenerator(World world, TiledMap map) {

        this.world = world;
        this.map = map;
        

        bodyDef = new BodyDef();
        shape = new PolygonShape();
        fixtureDef = new FixtureDef();

        generateSquareTerrain();

        /*
            TODO:
            Generate Polygonal Terrain
            Generate Lighting Fixtures
            Generate Interactive Items (collectibles and campfires?)
         */

    }


    private void generateSquareTerrain() {
        // Create ground bodies/ fixtures
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            // Sets body type and position
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / Flamabill.PPM, (rect.getY() + rect.getHeight() / 2) / Flamabill.PPM);


            body = world.createBody(bodyDef);

            shape.setAsBox((rect.getWidth() / 2) / Flamabill.PPM, (rect.getHeight() / 2) / Flamabill.PPM);
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }
    }

}
