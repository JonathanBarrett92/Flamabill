package com.treecrocs.flamabill.characters;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.treecrocs.flamabill.tools.EntityCategory;
import com.treecrocs.flamabill.Flamabill;
import com.treecrocs.flamabill.screens.PlayScreen;


public class Player extends Sprite {

    private World world;
    private PlayScreen playScreen;
    private TextureAtlas atlas;
    private CharacterController controller;
    public Body playerBody;

    private Animation<TextureRegion> idle;
    private Animation<TextureRegion> running;
    private Animation<TextureRegion> jumping;
    private Animation<TextureRegion> falling;
    private Animation<TextureRegion> fallingSideways;
    private Animation<TextureRegion> damaged;
    private Animation<TextureRegion> dead;

    private PlayerState currentState;
    private PlayerState previousState;
    private boolean isRunningLeft;
    private boolean playerIsDead;
    private float stateTime = 0;

    public Player(PlayScreen playScreen, CharacterController controller){

        this.playScreen = playScreen;
        this.world = playScreen.getWorld();
        this.controller = controller;
        createPlayerBody2(512, 512);

        currentState = PlayerState.IDLE;
        previousState = PlayerState.IDLE;
        isRunningLeft = true;

        idle = createAnimation("Flama-Bill-Complete_Standing_", 8, 0.2f);
        running = createAnimation("Flama-Bill-Complete_Run_", 8, 0.1f);
        jumping = createAnimation("Flama-Bill-Complete_Jump_", 8, 0.1f);
        falling = createAnimation("Flama-Bill-Complete_Fall_", 8,0.1f);
        fallingSideways = createAnimation("Flama-Bill-Complete_Fall-Sideways_", 7, 0.2f);
        dead = createAnimation("Flama-Bill-Complete_Died_", 11, 0.1f);

        //Initial values set.
        setBounds(512/Flamabill.PPM,512/Flamabill.PPM,96/Flamabill.PPM, 64/Flamabill.PPM);
        setRegion(idle.getKeyFrame(stateTime, true));

    }


    /*
        @desc: Custom function created to take regions of an Atlas and process it into an Animation
     */
    private Animation<TextureRegion> createAnimation(String atlasString, int noOfFrames, float duration){
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < noOfFrames; i++){
            frames.add(new TextureRegion(playScreen.getAtlas().findRegion(atlasString + i), 0, 0, 96, 64));
        }
        return new Animation<TextureRegion>(duration, frames);
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();
        TextureRegion region;

        switch (currentState){
            case DEAD:
                region = dead.getKeyFrame(stateTime, false);
                break;
            case RUNNING:
                region = running.getKeyFrame(stateTime, true);
                break;
            case JUMPING:
                region = jumping.getKeyFrame(stateTime, false);
                break;
            case FALLING:
                region = falling.getKeyFrame(stateTime, true);
                break;
            case FALLINGSIDEWAYS:
                region = fallingSideways.getKeyFrame(stateTime, true);
                break;
            default:
                region = idle.getKeyFrame(stateTime, true);
        }

        //if player is running left and the texture isn't facing left... flip it.
        if ((playerBody.getLinearVelocity().x > 0 || !isRunningLeft) && !region.isFlipX()) {
            region.flip(true, false);
            isRunningLeft = false;
        }
        //if player is running right and the texture isn't facing right... flip it.
        else if((playerBody.getLinearVelocity().x < 0 || isRunningLeft) && region.isFlipX()){
            region.flip(true, false);
            isRunningLeft = true;
        }

        //determine whether current state is the same as the previous state and increase timer
        //else state is changed and reset timer
        stateTime = currentState == previousState ? stateTime + dt : 0;
        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return region;
    }


    public PlayerState getState(){
        if(this.isDead()){
            return PlayerState.DEAD;
        }
        else if (this.playerBody.getLinearVelocity().y > 0.4f){
            return PlayerState.JUMPING;
        }
        else if (this.playerBody.getLinearVelocity().y < 0 && this.playerBody.getLinearVelocity().x < 1f && this.playerBody.getLinearVelocity().x > -1f){
            return PlayerState.FALLING;
        }
        else if (this.playerBody.getLinearVelocity().y < 0){
            return PlayerState.FALLINGSIDEWAYS;
        }
        else if (this.playerBody.getLinearVelocity().x != 0){
            return PlayerState.RUNNING;
        }
        else{
            return PlayerState.IDLE;
        }
    }

    public void update(float dt){
        setPosition(playerBody.getPosition().x - getWidth()/2, playerBody.getPosition().y - getHeight()/4);
        playerBody.setTransform(playerBody.getPosition(), 0);
        this.setRegion(getFrame(dt));
        //this.playerSensorFixture.setFriction(10f);

        //System.out.println(this.currentState);
        //System.out.println(getRegionX() + " " + getRegionY());
        //System.out.println(getFrame(dt));
        //System.out.println(this.playerBody.getLinearVelocity().x + " " + this.playerBody.getLinearVelocity().y);
    }

    public void determineMovement(float dt, int jumpKey, int rightKey, int leftKey){
        if(!isDead()){
            if(Gdx.input.isKeyJustPressed(jumpKey)) {
                this.jump();
            }
            if(Gdx.input.isKeyPressed(rightKey) && playerBody.getLinearVelocity().x <= 2) {
                //player.playerBody.setLinearVelocity(new Vector2(0.2f, 0f));
                playerBody.applyLinearImpulse(new Vector2(0.1f, 0f), playerBody.getWorldCenter(), true);
                //playerBody.setLinearVelocity(new Vector2(1.8f, 0f));
            }
            if(Gdx.input.isKeyPressed(leftKey) && playerBody.getLinearVelocity().x >= -2) {
                //playerBody.setLinearVelocity(new Vector2(-1.8f, 0f));
                playerBody.applyLinearImpulse(new Vector2(-0.1f, 0f), playerBody.getWorldCenter(), true);
            }
        }

    }


    private void createPlayerBody2(int spawnX, int spawnY) {

        /*
            TODO:
                - Bitmask filtering
                - Update spawn position based on checkpoints
         */

        //Create player body definition
        BodyDef bodyDefinition = new BodyDef();
        bodyDefinition.position.set(spawnX/Flamabill.PPM, spawnY/Flamabill.PPM);
        bodyDefinition.type = BodyDef.BodyType.DynamicBody;
        playerBody = world.createBody(bodyDefinition);

        //Create player body Fixture Definition and shape
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape playerBox = new PolygonShape();
        playerBox.setAsBox(15/Flamabill.PPM,15/Flamabill.PPM);

        //Filtering
        fixtureDef.filter.categoryBits = EntityCategory.PLAYER;
        fixtureDef.filter.maskBits = EntityCategory.GROUND | EntityCategory.DEATH | EntityCategory.CHECKPOINT;

        fixtureDef.shape = playerBox;
        fixtureDef.friction = 0.4f;
        playerBody.createFixture(fixtureDef).setUserData(this);

        //Creating a head to prevent bug with ceiling contact causing impossible states.
        PolygonShape head = new PolygonShape();
        float offsetY = 16f/Flamabill.PPM;
        head.setAsBox(15/Flamabill.PPM, 1/Flamabill.PPM, new Vector2(0, offsetY), 0 );
        fixtureDef.filter.categoryBits = EntityCategory.HEADSENSOR;
        fixtureDef.filter.maskBits = EntityCategory.PLAYER | EntityCategory.GROUND;
        fixtureDef.shape = head;
        //fixtureDef.isSensor = true;
        playerBody.createFixture(fixtureDef).setUserData(this);

    }

    public float getStateTime() {
        return stateTime;
    }

    private void jump(){
        if (currentState != PlayerState.JUMPING && currentState != PlayerState.FALLING && currentState != PlayerState.FALLINGSIDEWAYS) {
            playerBody.applyLinearImpulse(new Vector2(0, 4.20f), playerBody.getWorldCenter(), true);
            currentState = PlayerState.JUMPING;
        }
    }

    public void die(){
        playerIsDead = true;
        System.out.println(this + " dies");
    }

    public boolean isDead(){
        return playerIsDead;
    }

    public void replenishHealth(){
        System.out.println("Health replenished");
    }
}
