package com.treecrocs.flamabill.sprites;

import com.badlogic.gdx.math.Vector2;
import com.treecrocs.flamabill.characters.Player;

import java.util.ArrayList;

public class Checkpoint {

    private Vector2 startpoint = new Vector2();
    float height;
    float width;
    boolean currentSpawn;
    ArrayList<Checkpoint> checkpoints = new ArrayList<Checkpoint>();


    public Checkpoint(Vector2 startpoint,float height,float width)
    {
        this.startpoint.x = startpoint.x;
        this.startpoint.y = startpoint.y;
        this.height = height;
        this.width = width;
        currentSpawn=false;


    }


    public void update(float delta, Player player)
    {
        if(player.b2d.getPosition().x==this.startpoint.x)
        {
            currentSpawn=true;
            checkpoints.add(this);

        }



    }


}
