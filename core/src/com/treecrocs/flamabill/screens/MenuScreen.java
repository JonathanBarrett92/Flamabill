package com.treecrocs.flamabill.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.treecrocs.flamabill.Flamabill;

//Not sure what this does to be honest
import java.awt.datatransfer.FlavorMap;


public class MenuScreen implements Screen {

    //These are vital they're settings that change the size of the buttons and the bottom two change the spacing between buttons
    private static final int Button_Width = 300;
    private static final int Button_Height = 150;
    private static final int PlayButtonY = 300;
    private static final int QuitButtonY = 100;

    //calls info from Flamabill.java
    Flamabill game;

    //creates the textures for the buttons
    Texture PlayButtonActive;
    Texture PlayButtonInactive;
    Texture QuitButtonActive;
    Texture QuitButtonInactive;

    public MenuScreen(Flamabill game){
        this.game = game;

        //shows the app what to images to call and use from Assets, placeholders at the minute I'll update them once we get this going properly
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

        //used to find out where the mouse is as well as position the buttons on the X axis
        int x = (Flamabill.WIDTH/2) - (Button_Width/2);

        //play button code for positioning and telling it what to do when hovered or clicked GAME SCREEN MUST BE CHANGED
        if (Gdx.input.getX() < x+Button_Width && Gdx.input.getX() > x && Flamabill.HEIGHT - Gdx.input.getY() > PlayButtonY && Flamabill.HEIGHT - Gdx.input.getY() < PlayButtonY+Button_Height) {
            game.batch.draw(PlayButtonActive, x, PlayButtonY, Button_Width, Button_Height);

            //Changes game screen to the actual game, passes that on to Damo i think
            if(Gdx.input.isTouched()){
                game.setScreen(new PlayScreen(game)); //Needs to be changed to whatever the game screen will be called
            }
        } else {
            game.batch.draw(PlayButtonInactive, x, PlayButtonY, Button_Width, Button_Height);
        }

        //Quit button code for positioning and telling it what to do when hovered or clicked
        if (Gdx.input.getX() < x+Button_Width && Gdx.input.getX() > x && Flamabill.HEIGHT - Gdx.input.getY() > QuitButtonY && Flamabill.HEIGHT - Gdx.input.getY() < QuitButtonY+Button_Height) {
            game.batch.draw(QuitButtonActive, x, QuitButtonY, Button_Width, Button_Height);

            //quits the game when clicked
            if(Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        } else {
            game.batch.draw(QuitButtonInactive, x, QuitButtonY, Button_Width, Button_Height);
        }

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
