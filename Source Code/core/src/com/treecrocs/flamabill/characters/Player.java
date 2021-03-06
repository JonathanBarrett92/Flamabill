package com.treecrocs.flamabill.characters;


import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.treecrocs.flamabill.tools.EntityCategory;
import com.treecrocs.flamabill.Flamabill;
import com.treecrocs.flamabill.screens.PlayScreen;
import com.treecrocs.flamabill.worldobjects.Campfire;


public class Player extends Sprite {

    private World world;
    private PlayScreen playScreen;
    private TextureAtlas atlas;
    private CharacterController controller;
    public Body playerBody;
    private PointLight playerLight;
    private RayHandler rayHandler;

    private Animation<TextureRegion> idle;
    private Animation<TextureRegion> running;
    private Animation<TextureRegion> jumping;
    private Animation<TextureRegion> falling;
    private Animation<TextureRegion> fallingSideways;
    private Animation<TextureRegion> damaged;
    private Animation<TextureRegion> dead;
    private Animation<TextureRegion> waterDead;

    private PlayerState currentState;
    private PlayerState previousState;
    private boolean isRunningLeft;
    private boolean playerIsDeadToWater;
    private boolean playerIsDeadToTimer;
    private float stateTime = 0;
    private Vector2 spawn;
    private int deathTimer;
    private boolean replenishingHealth;
    private float replenishTimer = 1f;

    public Player(PlayScreen playScreen, CharacterController controller, String playerString, RayHandler rayHandler){

        this.playScreen = playScreen;
        this.world = playScreen.getWorld();
        this.controller = controller;
        this.rayHandler = rayHandler;

        spawn = this.playScreen.getWorldGen().getSpawnPoint();
        createPlayerBody(spawn.x, spawn.y);

        currentState = PlayerState.IDLE;
        previousState = PlayerState.IDLE;
        isRunningLeft = true;
        playerIsDeadToTimer = false;
        playerIsDeadToWater = false;

        idle = createAnimation("Flama-Bill-Complete_Standing" + playerString, 8, 0.2f);
        running = createAnimation("Flama-Bill-Complete_Run" + playerString, 8, 0.07f);
        jumping = createAnimation("Flama-Bill-Complete_Jump" + playerString, 9, 0.1f);
        falling = createAnimation("Flama-Bill-Complete_Fall" + playerString, 8,0.1f);
        fallingSideways = createAnimation("Flama-Bill-Complete_Fall-Sideways" + playerString, 8, 0.2f);
        dead = createAnimation("Flama-Bill-Complete_Died" + playerString, 12, 0.1f);
        waterDead = createAnimation("Flama-Bill-Complete_WaterDeath" + playerString, 11, 0.1f);

        //Initial values set.
        setBounds(spawn.x/Flamabill.PPM,spawn.y/Flamabill.PPM,96/Flamabill.PPM, 64/Flamabill.PPM);
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

    private TextureRegion getFrame(float dt){
        currentState = getState();
        TextureRegion region;

        switch (currentState){
            case DEAD:
                region = dead.getKeyFrame(stateTime, false);
                break;
            case WATERDEAD:
                region = waterDead.getKeyFrame(stateTime, false);
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
        if(this.isDeadToWater()){
            return PlayerState.WATERDEAD;
        }
        else if (this.isDeadToTimer()){
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
        if(isDead()){
            deathTimer++;
            if (deathTimer > 60){
                respawnPlayer();
                deathTimer = 0;
            }
        }

        if(replenishingHealth){
            replenishTimer -= dt;
            if(replenishTimer <= 0){
                replenishingHealth = false;
                replenishTimer = 1f;
            }
        }
    }

    public void determineMovement(float dt, int jumpKey, int rightKey, int leftKey, int dieKey){
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
            if(Gdx.input.isKeyJustPressed(dieKey)){
                this.dieToTimer();
            }
        }

    }


    private void createPlayerBody(float spawnX, float spawnY) {

        //Create player body definition
        BodyDef bodyDefinition = new BodyDef();
        bodyDefinition.position.set(spawnX/Flamabill.PPM, spawnY/Flamabill.PPM);
        bodyDefinition.type = BodyDef.BodyType.DynamicBody;
        playerBody = world.createBody(bodyDefinition);

        //Create player body Fixture Definition and shape
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape playerBox = new PolygonShape();
        playerBox.setAsBox(8/Flamabill.PPM,15/Flamabill.PPM);

        //Filtering
        fixtureDef.filter.categoryBits = EntityCategory.PLAYER;
        fixtureDef.filter.maskBits = EntityCategory.GROUND | EntityCategory.DEATH | EntityCategory.CHECKPOINT;

        fixtureDef.shape = playerBox;
        fixtureDef.friction = 0.4f;
        playerBody.createFixture(fixtureDef).setUserData(this);

        //Creating a head to prevent bug with ceiling contact causing impossible states.
        PolygonShape head = new PolygonShape();
        float offsetY = 16f/Flamabill.PPM;
        head.setAsBox(8/Flamabill.PPM, 1/Flamabill.PPM, new Vector2(0, offsetY), 0 );
        fixtureDef.filter.categoryBits = EntityCategory.HEADSENSOR;
        fixtureDef.filter.maskBits = EntityCategory.PLAYER | EntityCategory.GROUND;
        fixtureDef.shape = head;
        //fixtureDef.isSensor = true;
        playerBody.createFixture(fixtureDef).setUserData(this);

    }


    private void jump(){
        if (currentState != PlayerState.JUMPING && currentState != PlayerState.FALLING && currentState != PlayerState.FALLINGSIDEWAYS) {
            playerBody.applyLinearImpulse(new Vector2(0, 4.20f), playerBody.getWorldCenter(), true);
            currentState = PlayerState.JUMPING;
        }
    }

    public void dieToWater(){
        playerIsDeadToWater = true;
    }

    public void dieToTimer(){
        playerIsDeadToTimer = true;
    }

    private boolean isDeadToWater(){
        return playerIsDeadToWater;
    }

    public boolean isDeadToTimer(){
        return playerIsDeadToTimer;
    }

    private boolean isDead(){
        return (playerIsDeadToWater || playerIsDeadToTimer);
    }

    public void replenishHealth(){
        replenishingHealth = true;
    }

    public boolean isReplenishingHealth(){
        return replenishingHealth;
    }

    public void setSpawn(Vector2 spawn){
        this.spawn.x = spawn.x/Flamabill.PPM;
        this.spawn.y = spawn.y/Flamabill.PPM;
    }

    public void updateSpawn(Vector2 spawn){
        this.spawn.x = spawn.x;
        this.spawn.y = spawn.y;
    }

    private void respawnPlayer(){
        playerBody.setTransform(spawn.x, spawn.y, 0);
        playerIsDeadToWater = false;
        playerIsDeadToTimer = false;
    }
}
