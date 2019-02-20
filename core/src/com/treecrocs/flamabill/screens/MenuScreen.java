package com.treecrocs.flamabill.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.treecrocs.flamabill.Flamabill;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

//Not sure what this does to be honest
import java.awt.datatransfer.FlavorMap;


public class MenuScreen implements Screen {

    //These are vital they're settings that change the size of the buttons and the bottom two change the spacing between buttons
    private static final int Button_Height = Flamabill.V_HEIGHT /7;
    private static final int Button_Width = Button_Height * 3;
    private static final int PlayButtonY = Flamabill.V_HEIGHT / 2 - 100;
    private static final int QuitButtonY = (Flamabill.V_HEIGHT /2) - (Button_Height + 25) - 100;

    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private float elapsedTime = 0;

    private final Animation LogoAnimation;
    private final Animation LogoAnimation2;
    private final Animation LogoAnimation3;

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

        batch = new SpriteBatch();
        textureAtlas = new TextureAtlas(Gdx.files.internal("Logo.atlas"));

        //animation = new Animation(1/15f, textureAtlas.getRegions());
        TextureRegion[] Logo = new TextureRegion[7];

        // Create an array of TextureRegions
        Logo[0] = (textureAtlas.findRegion("Logo_chillin1"));
        Logo[1] = (textureAtlas.findRegion("Logo_chillin2"));
        Logo[2] = (textureAtlas.findRegion("Logo_chillin3"));
        Logo[3] = (textureAtlas.findRegion("Logo_chillin4"));
        Logo[4] = (textureAtlas.findRegion("Logo_chillin5"));
        Logo[5] = (textureAtlas.findRegion("Logo_chillin6"));
        Logo[6] = (textureAtlas.findRegion("Logo_chillin7"));


        LogoAnimation = new Animation(0.1f, Logo);

        TextureRegion[] Logo2 = new TextureRegion[6];

        // Create an array of TextureRegions
        Logo2[0] = (textureAtlas.findRegion("Logo_Grow1"));
        Logo2[1] = (textureAtlas.findRegion("Logo_Grow2"));
        Logo2[2] = (textureAtlas.findRegion("Logo_Grow3"));
        Logo2[3] = (textureAtlas.findRegion("Logo_Grow4"));
        Logo2[4] = (textureAtlas.findRegion("Logo_Grow5"));
        Logo2[5] = (textureAtlas.findRegion("Logo_Grow6"));

        LogoAnimation2 = new Animation(0.1f,Logo2);

        TextureRegion[] Logo3 = new TextureRegion[3];

        // Create an array of TextureRegions
        Logo3[0] = (textureAtlas.findRegion("Logo_Rise1"));
        Logo3[1] = (textureAtlas.findRegion("Logo_Rise2"));
        Logo3[2] = (textureAtlas.findRegion("Logo_Rise3"));


        LogoAnimation3 = new Animation(0.1f,Logo3);

    }

    @Override
    public void show(){

    }

    @Override
    public void render(float delta){
//52.9, 80.8, 92.2
        Gdx.gl.glClearColor(52.9f/255f, 80.8f/255f, 92.2f/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        //used to find out where the mouse is as well as position the buttons on the X axis
        int x = (Flamabill.V_WIDTH/2) - (Button_Width/2);

        //play button code for positioning and telling it what to do when hovered or clicked GAME SCREEN MUST BE CHANGED
        if (Gdx.input.getX() < x+Button_Width && Gdx.input.getX() > x && Flamabill.V_HEIGHT - Gdx.input.getY() > PlayButtonY && Flamabill.V_HEIGHT - Gdx.input.getY() < PlayButtonY+Button_Height) {
            game.batch.draw(PlayButtonActive, x, PlayButtonY, Button_Width, Button_Height);

            //Changes game screen to the actual game, passes that on to Damo i think
            if(Gdx.input.isTouched())
                {
                    game.setScreen(new PlayScreen(game)); //Needs to be changed to whatever the game screen will be called
                }
        } else
            {
                game.batch.draw(PlayButtonInactive, x, PlayButtonY, Button_Width, Button_Height);
            }

        //Quit button code for positioning and telling it what to do when hovered or clicked
        if (Gdx.input.getX() < x+Button_Width && Gdx.input.getX() > x && Flamabill.V_HEIGHT - Gdx.input.getY() > QuitButtonY && Flamabill.V_HEIGHT - Gdx.input.getY() < QuitButtonY+Button_Height) {
            game.batch.draw(QuitButtonActive, x, QuitButtonY, Button_Width, Button_Height);

            //quits the game when clicked
            if(Gdx.input.isTouched())
            {
                Gdx.app.exit();
            }
        } else
            {
                game.batch.draw(QuitButtonInactive, x, QuitButtonY, Button_Width, Button_Height);
            }

        game.batch.end();


        batch.begin();
        //sprite.draw(batch);
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw((TextureRegion) LogoAnimation2.getKeyFrame(elapsedTime, false), 100, 300);
        batch.draw((TextureRegion) LogoAnimation3.getKeyFrame(elapsedTime, false), 100, 300);
        batch.draw((TextureRegion) LogoAnimation.getKeyFrame(elapsedTime, true), 100, 300);
        batch.end();
        

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
