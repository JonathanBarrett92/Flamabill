package com.treecrocs.flamabill.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.treecrocs.flamabill.Flamabill;

public class PlayScreen implements Screen {

    private OrthographicCamera camera;
    private ExtendViewport viewport;
    private Flamabill game;
    //private Player player;

    public PlayScreen (Flamabill game){
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(1200,800, camera);
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
