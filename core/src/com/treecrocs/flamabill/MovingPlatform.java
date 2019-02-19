package com.treecrocs.flamabill;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;

//moving platform is a sub class of platfrom
public class MovingPlatform extends Platform {
   private Vector2 dir = new Vector2();
   //vector because theyre nice
   private float dist;//these will be used when I can get it to update correctly
   private float maxDist;//^^^
   //the movement of the platform have not got it working correctly
   //the shapes just overlap
   public void move(Platform platform, float dx, float dy, float close) {

      //initialising the vector
      dir.x = dx;
      dir.y = dy;
      maxDist = close;


      //this sets the position to where the platform will move to and from
      //I.E. the reference object
      platform.body.setTransform(dir, 0);



      //this sets the speed at which the platform shall go
      // the bigger the number the higher the speed
      platform.body.setLinearVelocity(dir);


   }




}
