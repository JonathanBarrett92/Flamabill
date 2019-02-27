package com.treecrocs.flamabill.characters;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.treecrocs.flamabill.tools.EntityCategory;
import com.treecrocs.flamabill.Flamabill;
import com.treecrocs.flamabill.screens.PlayScreen;


public class Player extends Sprite {

    public World world;
    public Body b2d;
    private CharacterController controller;

    private Animation<TextureRegion> idle;
    private Animation<TextureRegion> running;
    private Animation<TextureRegion> jumping;
    private Animation<TextureRegion> falling;
    private Animation<TextureRegion> fallingSideways;
    private Animation<TextureRegion> damaged;
    private Animation<TextureRegion> deathAnim;

    private boolean isRunningRight;

    private Fixture playerPhysicsFixture;
    private Fixture playerSensorFixture;

    PlayScreen playScreen;

    public PlayerState currentState;
    public PlayerState previousState;


    private TextureAtlas atlas;

    private float stateTime = 0;

    public Player(PlayScreen playScreen, CharacterController controller){

        this.playScreen = playScreen;
        this.world = playScreen.getWorld();
        this.controller = controller;
        createPlayerBody(512, 512);

        currentState = PlayerState.IDLE;
        previousState = PlayerState.IDLE;
        isRunningRight = true;


        /*
            Creating all textures from Atlas
         */
        Array<TextureRegion> frames = new Array<TextureRegion>();

        //Load Idle
        for (int i = 0; i < 8; i++){
            frames.add(new TextureRegion(playScreen.getAtlas().findRegion("Flama-Bill-Complete_Standing_" + i), 0, 0, 64, 64));
        }
        idle = new Animation<TextureRegion>(0.3f, frames);
        frames.clear();

        //Load Running
        for (int i = 0; i < 8; i++){
            frames.add(new TextureRegion(playScreen.getAtlas().findRegion("Flama-Bill-Complete_Run_" + i), 1536, 0, 64, 64));
        }
        running = new Animation<TextureRegion>(0.3f, frames);
        frames.clear();

        setBounds(512/Flamabill.PPM,512/Flamabill.PPM,64/Flamabill.PPM,64/Flamabill.PPM);
        setRegion(idle.getKeyFrame(stateTime, true));



    }

    public TextureRegion getFrame(float dt){
        currentState = getState();
        TextureRegion region;

        switch (currentState){
            case RUNNING:
                region = running.getKeyFrame(stateTime, true);
                break;
            default:
                region = idle.getKeyFrame(stateTime, true);
        }

        //if player is running left and the texture isn't facing left... flip it.
        if ((b2d.getLinearVelocity().x < 0 || !isRunningRight) && !region.isFlipX()) {
            region.flip(true, false);
            isRunningRight = false;
        }
        //if player is running right and the texture isn't facing right... flip it.
        else if((b2d.getLinearVelocity().x > 0 || isRunningRight) && region.isFlipX()){
            region.flip(true, false);
            isRunningRight = true;
        }

        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        stateTime = currentState == previousState ? stateTime + dt : 0;
        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return region;
    }


    public PlayerState getState(){
        if (this.b2d.getLinearVelocity().y > 0 || (this.b2d.getLinearVelocity().y < 0 && previousState == PlayerState.JUMPING)){
            return PlayerState.JUMPING;
        }
        else if (this.b2d.getLinearVelocity().y < 0){
            return PlayerState.FALLING;
        }
        else if (this.b2d.getLinearVelocity().x != 0){
            return PlayerState.RUNNING;
        }
        else{
            return PlayerState.IDLE;
        }
    }

    public void update(float dt){
        setPosition((b2d.getPosition().x - getWidth() / 2)/Flamabill.PPM, (b2d.getPosition().y - getHeight() / 3)/Flamabill.PPM);
        b2d.setTransform(b2d.getPosition(), 0);
        setRegion(getFrame(dt));
    }

    public void determineMovement(float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            b2d.applyLinearImpulse(new Vector2(0, 0.5f), b2d.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && b2d.getLinearVelocity().x <= 6) {
            //player.b2d.setLinearVelocity(new Vector2(0.2f, 0f));
            b2d.applyLinearImpulse(new Vector2(0.2f, 0f), b2d.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && b2d.getLinearVelocity().x >= -6) {
            //player.b2d.setLinearVelocity(new Vector2(0.2f, 0f));
            b2d.applyLinearImpulse(new Vector2(-0.2f, 0f), b2d.getWorldCenter(), true);
        }

    }

    private void createPlayerBody(int spawnX, int spawnY) {
        /*
            TODO:
                - Make proper rect and circle combo
                - Bitmask filtering
                - Update spawn position based on checkpoints
         */
        BodyDef def = new BodyDef();

        def.position.set(spawnX / Flamabill.PPM, spawnY / Flamabill.PPM);
        def.type = BodyDef.BodyType.DynamicBody;
        b2d = world.createBody(def);

        FixtureDef boxDef = new FixtureDef();
        FixtureDef sensorDefinition = new FixtureDef();


        PolygonShape playerBox = new PolygonShape();
        playerBox.setAsBox(16/Flamabill.PPM, 32/Flamabill.PPM);
        playerPhysicsFixture = b2d.createFixture(playerBox, 1);

        CircleShape groundSens = new CircleShape();
        groundSens.setRadius(16 / Flamabill.PPM);
        groundSens.setPosition(new Vector2(0, -32f/Flamabill.PPM));
        playerSensorFixture = b2d.createFixture(groundSens, 0);


        boxDef.filter.categoryBits = EntityCategory.PLAYER.getFilter();
        sensorDefinition.filter.categoryBits = EntityCategory.SENSOR.getFilter();

        boxDef.density = 0;

        boxDef.shape = playerBox;
        sensorDefinition.shape = groundSens;
        sensorDefinition.isSensor = true;

        b2d.createFixture(boxDef).setUserData(this);
        b2d.createFixture(sensorDefinition).setUserData(this);
    }


}
