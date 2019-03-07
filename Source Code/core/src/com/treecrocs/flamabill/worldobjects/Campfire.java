package com.treecrocs.flamabill.worldobjects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.treecrocs.flamabill.Flamabill;
import com.treecrocs.flamabill.screens.PlayScreen;
import com.treecrocs.flamabill.tools.EntityCategory;
import com.treecrocs.flamabill.tools.WorldGenerator;


public class Campfire extends Sprite {

    private TextureAtlas atlas;
    private Animation<TextureRegion> anim;
    private World world;
    public Body body;
    public Rectangle bounds;
    private FixtureDef fdef;
    private SpriteBatch batch;

    private int campfireWidth = 32;
    private int campfireHeight = 32;
    private float elapsedTime;


    public Campfire(Rectangle bounds, World world){

        this.bounds = bounds;
        this.world = world;
        batch = new SpriteBatch();
        atlas = new TextureAtlas(Gdx.files.internal("Campfire-Lit.atlas"));
        anim = createAnimation("Campfire-Lit_Lit_", 4, 0.1f);
        elapsedTime = 0;

        BodyDef bdef = new BodyDef();
        fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        // Sets body type and position
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / Flamabill.PPM, (bounds.getY() + bounds.getHeight() / 2) / Flamabill.PPM);

        body = world.createBody(bdef);

        shape.setAsBox((bounds.getWidth()/2) / Flamabill.PPM, (bounds.getHeight()/2) / Flamabill.PPM);
        fdef.filter.categoryBits = EntityCategory.CHECKPOINT;
        fdef.filter.maskBits = EntityCategory.PLAYER;
        fdef.shape = shape;
        fdef.isSensor = true;
        body.createFixture(fdef);
        setBounds((bounds.getX()/Flamabill.PPM) - (campfireWidth/4f/Flamabill.PPM), bounds.getY()/Flamabill.PPM, campfireWidth/Flamabill.PPM, campfireHeight/Flamabill.PPM);

    }

    public void update(float dt){
        elapsedTime += dt;
        this.setRegion(anim.getKeyFrame(elapsedTime, true));
    }


    private Animation<TextureRegion> createAnimation(String atlasString, int noOfFrames, float duration){
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < noOfFrames; i++){
            frames.add(new TextureRegion(atlas.findRegion(atlasString + i), 0, 0, 32, 32));
        }
        return new Animation<TextureRegion>(duration, frames);
    }

    public Vector2 getCentrePosition(){
        return new Vector2((bounds.getX() + bounds.getWidth()/2), (bounds.getY() + bounds.getHeight()/2));
    }

}
