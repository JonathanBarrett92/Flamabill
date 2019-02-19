package com.treecrocs.flamabill;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;


public class Platform  {

    protected Body body;
    protected BodyDef bdef;
    protected float ogx;
    protected float ogy;
    protected float hx;
    protected float hy;


    public  void  CreatePlatform (World world ,float x ,float y,float hx,float hy )
    {
        ogx = x;
        ogy = y;
        this.hx=hx;
        this.hy =hy;
        bdef = new BodyDef();
        bdef.position.set(ogx,ogy);

        bdef.type = BodyDef.BodyType.KinematicBody;
        body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(hx,hy);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        body.createFixture(fdef);





    }






}
