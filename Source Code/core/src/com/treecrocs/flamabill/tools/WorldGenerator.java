package com.treecrocs.flamabill.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.treecrocs.flamabill.Flamabill;
import com.treecrocs.flamabill.worldobjects.Campfire;


public class WorldGenerator {

    private World world;
    private TiledMap map;
    private BodyDef bodyDef;
    private PolygonShape shape;
    private FixtureDef fixtureDef;
    private Body body;
    private Vector2 spawn;

    public static float spawnX;
    public static float spawnY;

    public WorldGenerator(World world, TiledMap map) {

        this.world = world;
        this.map = map;

        bodyDef = new BodyDef();
        shape = new PolygonShape();
        fixtureDef = new FixtureDef();

        generateSquareTerrain();
        generateWater();
        generateCheckpoints();
        spawn = getSpawnPoint();
        spawnX = spawn.x;
        spawnY = spawn.y;

        /*
            TODO:
            Generate Polygonal Terrain
            Generate Lighting Fixtures
         */

    }


    private void generateSquareTerrain() {
        // Create ground bodies/ fixtures
        for (MapObject object : map.getLayers().get("Ground").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            // Sets body type and position
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / Flamabill.PPM, (rect.getY() + rect.getHeight() / 2) / Flamabill.PPM);


            body = world.createBody(bodyDef);

            shape.setAsBox((rect.getWidth() / 2) / Flamabill.PPM, (rect.getHeight() / 2) / Flamabill.PPM);
            fixtureDef.filter.categoryBits = EntityCategory.GROUND;
            fixtureDef.filter.maskBits = EntityCategory.PLAYER;
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }
    }

    private void generateWater() {
        // Create ground bodies/ fixtures
        for (MapObject object : map.getLayers().get("Water").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            // Sets body type and position
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / Flamabill.PPM, (rect.getY() + rect.getHeight() / 2) / Flamabill.PPM);


            body = world.createBody(bodyDef);

            shape.setAsBox((rect.getWidth() / 2) / Flamabill.PPM, (rect.getHeight() / 2) / Flamabill.PPM);
            fixtureDef.filter.categoryBits = EntityCategory.DEATH;
            fixtureDef.filter.maskBits = EntityCategory.PLAYER;
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }
    }

    private void generateCheckpoints() {
        // Create ground bodies/ fixtures
        for (MapObject object : map.getLayers().get("CheckPoints").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
                new Campfire(rect, this.world);
//            // Sets body type and position
//            bodyDef.type = BodyDef.BodyType.StaticBody;
//            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / Flamabill.PPM, (rect.getY() + rect.getHeight() / 2) / Flamabill.PPM);
//
//
//            body = world.createBody(bodyDef);
//
//            shape.setAsBox((rect.getWidth() / 2) / Flamabill.PPM, (rect.getHeight() / 2) / Flamabill.PPM);
//            fixtureDef.filter.categoryBits = EntityCategory.CHECKPOINT;
//            fixtureDef.filter.maskBits = EntityCategory.PLAYER;
//            fixtureDef.isSensor = true;
//            fixtureDef.shape = shape;
//            body.createFixture(fixtureDef);


        }
    }

    public Vector2 getSpawnPoint() {
        // Create ground bodies/ fixtures
        float x = 0;
        float y = 0;

        for (MapObject object : map.getLayers().get("Player1Spawn").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            x = rect.getX();
            y = rect.getY();
        }
        return new Vector2(x, y);
    }


}
