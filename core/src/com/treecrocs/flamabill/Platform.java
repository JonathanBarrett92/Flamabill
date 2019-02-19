package com.treecrocs.flamabill;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;


public class Platform  {
    //This class is a blueprint for possible other platforms in the game such as destroyable ones

    //variables used in sub classes
    protected Body body;
    protected BodyDef bdef;
    protected float ogx;
    protected float ogy;
    protected float hx;
    protected float hy;



    //create method for platform
    public  void  CreatePlatform (World world ,float x ,float y,float hx,float hy )
    {
        //create a blueprint for the body and its position in the world
        ogx = x;
        ogy = y;
        this.hx=hx;
        this.hy =hy;
        bdef = new BodyDef();
        bdef.position.set(ogx,ogy);

        //kinemaic body is needed as so it can move and be destroyed
        bdef.type = BodyDef.BodyType.KinematicBody;
        body = world.createBody(bdef);

        //this creates the shape of the platform using input from launcher
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(hx,hy);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        body.createFixture(fdef);
        //T h e    b o d y      i s      c r e a t e d




    }






}
