package com.treecrocs.flamabill.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.treecrocs.flamabill.Flamabill;
import com.treecrocs.flamabill.MovingPlatform;
import com.treecrocs.flamabill.Platform;


public class PlayScreen implements Screen {

    private OrthographicCamera camera;
    private ExtendViewport viewport;
    private Flamabill game;
    private World world;
    private Box2DDebugRenderer b2dr;
    private float deltaTime;
    //private Player player;

    public PlayScreen (Flamabill game){
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(1200,800, camera);
        world = new World(new Vector2(0,-9.81f),true);
        b2dr = new Box2DDebugRenderer();



    }

    @Override
    public void show() {


    }

    @Override
    public void render(float deltaTime) {
        //clear screen black

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //I need to use delta time but do not understand how
        //deltaTime = Gdx.graphics.getDeltaTime();

        world.step(deltaTime,2,2);
        b2dr.render(world,camera.combined);

        //creating the platform object
       /* MovingPlatform platform = new MovingPlatform();
        platform.CreatePlatform(world,30,30,50,50);

        platform.move(platform,0,15,10); */

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
