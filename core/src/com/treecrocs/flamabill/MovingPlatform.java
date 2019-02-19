package com.treecrocs.flamabill;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class MovingPlatform
{
   //variables
   private Body body;
   private BodyDef bdef;
   private float height;
   private float width;
   private World world;

   private boolean direction;

   private Vector2 startPos;
   private Vector2 endPos;
   private Vector2 forwardVel = new Vector2(30, 0);
   private Vector2 backwardVel = new Vector2(-30 , 0);


   public MovingPlatform(World world, float startX, float startY, float endX, float endY, float height, float width )
   {
      startPos = new Vector2(startX, startY);
      endPos = new Vector2(endX, endY);
      //create a blueprint for the body and its position in the world

      this.height = height;
      this.width = width;
      this.world = world;
      direction = true;


      createPlatform();

      
   }

   /*
      Creates the platform
    */
   private void createPlatform() {
      bdef = new BodyDef();
      bdef.position.set(startPos.x,startPos.y);

      //kinematic body is needed as so it can move and be destroyed
      bdef.type = BodyDef.BodyType.KinematicBody;
      body = world.createBody(bdef);

      //this creates the shape of the platform using input from launcher
      PolygonShape shape = new PolygonShape();
      shape.setAsBox(height,width);

      FixtureDef fdef = new FixtureDef();
      fdef.shape = shape;
      body.createFixture(fdef);

      //T h e    b o d y      i s      c r e a t e d
   }

   public void move()
   {
      System.out.println("PosX: " + getPosX() + " EndPos: " + endPos.x );
      //If going forward
      if(direction){ // && getPosX() <= endPos.x){
         this.body.applyLinearImpulse(forwardVel, this.body.getPosition(), true);
      }
      else{
         //this.body.setLinearVelocity(backwardVel);
      }

      System.out.println(getPosX() + " " + getPosY());

   }

   public float getPosX(){
      return this.body.getPosition().x;
   }

   public float getPosY(){
      return this.body.getPosition().y;
   }

}