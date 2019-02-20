package com.treecrocs.flamabill.characters;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.treecrocs.flamabill.screens.PlayScreen;


public class Player extends Sprite {

    public World world;
    public Body b2d;

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

}
