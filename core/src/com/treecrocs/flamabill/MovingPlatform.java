package com.treecrocs.flamabill;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;


public class MovingPlatform extends Platform {
   private Vector2 dir = new Vector2();
   private Matrix3 mat = new Matrix3();
   private float dist;
   private float maxDist;

   public void move(Platform platform, float dx, float dy, float close, float delta) {

      dir.x = dx;
      dir.y = dy;
      maxDist = close;


      platform.body.setTransform(dir, 0);
      

      update(delta, dx, dy);

      platform.body.setLinearVelocity(dir);


   }

   public void update(float delta,float dx,float dy) {
      dist += dir.len() * delta;
      if (dist > maxDist) {
         dir.x = -dx;
         dir.y = -dy;
         dist = 0;



      }
   }

}
