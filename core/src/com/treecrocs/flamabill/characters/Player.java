package com.treecrocs.flamabill.characters;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.treecrocs.flamabill.screens.PlayScreen;


public class Player extends Sprite {

    public World world;
    public Body b2d;

    private Fixture playerPhysicsFixture;
    private Fixture playerSensorFixture;

    private Animation<TextureRegion> idle;
    private Animation<TextureRegion> running;
    private Animation<TextureRegion> jumping;
    private Animation<TextureRegion> falling;
    private Animation<TextureRegion> fallingSideways;
    private Animation<TextureRegion> damaged;
    private Animation<TextureRegion> deathAnim;

    PlayScreen playScreen;

    public Player(PlayScreen playScreen){
        this.playScreen = playScreen;
        this.world = playScreen.getWorld();
        Array<TextureRegion> frames = new Array<TextureRegion>();
        createAnimations(frames);


    }

    public void createAnimations(Array<TextureRegion> frames){

        //create idle
//        int idleFrames = 4;
//        int idleFrameSize = 32;
//        for (int i = 0; i < idleFrames; i++) {
//            frames.add(new TextureRegion());
//        }
    }

    public PlayerState getState(){
        //TODO: Determine if possible to do Event-based Delegation of PlayerState?
        return PlayerState.IDLE;
    }

    private Body createPlayerBody() {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        Body box = world.createBody(def);

        PolygonShape poly = new PolygonShape();
        poly.setAsBox(0.45f, 1.4f);
        playerPhysicsFixture = box.createFixture(poly, 1);
        poly.dispose();

        CircleShape circle = new CircleShape();
        circle.setRadius(0.45f);
        circle.setPosition(new Vector2(0, -1.4f));
        playerSensorFixture = box.createFixture(circle, 0);
        circle.dispose();

        box.setBullet(true);

        return box;
    }

}
