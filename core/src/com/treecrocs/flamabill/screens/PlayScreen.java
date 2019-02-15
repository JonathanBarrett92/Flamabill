package com.treecrocs.flamabill.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.treecrocs.flamabill.Flamabill;

public class PlayScreen implements Screen {

    private OrthographicCamera camera;
    private ExtendViewport viewport;
    private Flamabill game;
    //private Player player;
    private TextureAtlas atlas;

    private World world;

    public PlayScreen (Flamabill game){
        this.game = game;

        // Create world object with -9.8g in Y axis
        this.world = new World(new Vector2(0f,-9.80f), true);

        //atlas = new TextureAtlas("atlas.atlas");

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(1200,800, camera);
    }

    public World getWorld(){
        return this.world;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //clear screen black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
