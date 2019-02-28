package com.treecrocs.flamabill.sprites;


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

   private Vector2 startPos;
   private Vector2 endPos;
   private Vector2 forwardVel = new Vector2(60, 0);
   private Vector2 backwardVel = new Vector2(-60 , 0);
   private Vector2 currentVel = new Vector2();


   public MovingPlatform(World world, float startX, float startY, float endX, float endY, float height, float width )
   {
      startPos = new Vector2(startX, startY);
      endPos = new Vector2(endX, endY);
      //create a blueprint for the body and its position in the world

      this.height = height;
      this.width = width;
      this.world = world;
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
      this.body.setLinearVelocity(currentVel);
      if(getPosX() >= endPos.x &&getPosY()>=endPos.y )
      {
         this.body.setTransform(endPos,0);
         currentVel.x = backwardVel.x;
         currentVel.y = backwardVel.y;


      }
      if(getPosX()<=startPos.x&&getPosY()>=startPos.y )
      {
         this.body.setTransform(startPos,0);
         currentVel.x = forwardVel.x;
         currentVel.y = forwardVel.y;
      }




   }

   public float getPosX(){
      return this.body.getPosition().x;
   }

   public float getPosY(){
      return this.body.getPosition().y;
   }

}