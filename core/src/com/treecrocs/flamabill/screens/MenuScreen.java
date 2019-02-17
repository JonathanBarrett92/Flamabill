package com.treecrocs.flamabill.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.treecrocs.flamabill.Flamabill;


public class MenuScreen implements Screen {

    Flamabill game;

    Texture PlayButtonActive;
    Texture PlayButtonInactive;
    Texture QuitButtonActive;
    Texture QuitButtonInactive;

    public MenuScreen(Flamabill game){
        this.game = game;

        PlayButtonActive = new Texture("Menu_Button_Play_active.png");
        PlayButtonInactive = new Texture("Menu_Button_Play_Inactive.png");
        QuitButtonActive = new Texture("Menu_Button_Quit_active.png");
        QuitButtonInactive = new Texture("Menu_Button_Quit_Inactive.png");


    }

    @Override
    public void show(){

    }

    @Override
    public void render(float delta){

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        game.batch.draw(PlayButtonInactive, 100, 100);

        game.batch.end();
    }

    @Override
    public void resize(int width, int height){

    }

    @Override
    public void pause(){

    }

    @Override
    public void resume(){

    }

    @Override
    public void hide(){

    }

    @Override
    public void dispose(){

    }
}
