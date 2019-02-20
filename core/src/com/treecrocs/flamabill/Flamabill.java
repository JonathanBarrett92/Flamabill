package com.treecrocs.flamabill;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.treecrocs.flamabill.screens.MenuScreen;
import com.treecrocs.flamabill.screens.PlayScreen;

public class Flamabill extends Game {

	//Pixels per metre scaling value
    public static final int V_WIDTH = 1200;
    public static final int V_HEIGHT = 600;
	public static final float PPM = 100.0f;

	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new MenuScreen(this));
		//img = new Texture("badlogic.jpg");

	}

	@Override
	public void render () {
		super.render();
	}
		
	@Override
	public void dispose () {
		batch.dispose();
	}
}
