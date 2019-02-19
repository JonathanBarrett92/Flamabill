package com.treecrocs.flamabill.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.treecrocs.flamabill.Flamabill;

//Not sure what this does to be honest


public class MenuScreen implements Screen {

    //These are vital they're settings that change the size of the buttons and the bottom two change the spacing between buttons
    private static final int Button_Width = 300;
    private static final int Button_Height = 150;
    private static final int PlayButtonY = 300;
    private static final int QuitButtonY = 100;
    private final Animation RunAnimation;

    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private Animation animation;
    private float elapsedTime = 0;

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
        textureAtlas = new TextureAtlas(Gdx.files.internal("Flama-Bill-Complete.atlas"));
        //animation = new Animation(1/15f, textureAtlas.getRegions());

        TextureRegion[] Run = new TextureRegion[8];

        // Rotate Up Animation
        // Create an array of TextureRegions
        Run[0] = (textureAtlas.findRegion("Flama-Bill-Complete_Standing_1"));
        Run[1] = (textureAtlas.findRegion("Flama-Bill-Complete_Standing_2"));
        Run[2] = (textureAtlas.findRegion("Flama-Bill-Complete_Standing_3"));
        Run[3] = (textureAtlas.findRegion("Flama-Bill-Complete_Standing_4"));
        Run[4] = (textureAtlas.findRegion("Flama-Bill-Complete_Standing_5"));
        Run[5] = (textureAtlas.findRegion("Flama-Bill-Complete_Standing_6"));
        Run[6] = (textureAtlas.findRegion("Flama-Bill-Complete_Standing_7"));
        Run[7] = (textureAtlas.findRegion("Flama-Bill-Complete_Standing_8"));

        RunAnimation = new Animation(0.1f, Run);
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
        int x = (Flamabill.V_WIDTH/2) - (Button_Width/2);

        //play button code for positioning and telling it what to do when hovered or clicked GAME SCREEN MUST BE CHANGED
        if (Gdx.input.getX() < x+Button_Width && Gdx.input.getX() > x && Flamabill.V_HEIGHT - Gdx.input.getY() > PlayButtonY && Flamabill.V_HEIGHT - Gdx.input.getY() < PlayButtonY+Button_Height) {
            game.batch.draw(PlayButtonActive, x, PlayButtonY, Button_Width, Button_Height);

            //Changes game screen to the actual game, passes that on to Damo i think
            if(Gdx.input.isTouched()){
                game.setScreen(new PlayScreen(game)); //Needs to be changed to whatever the game screen will be called
            }
        } else {
            game.batch.draw(PlayButtonInactive, x, PlayButtonY, Button_Width, Button_Height);
        }

        //Quit button code for positioning and telling it what to do when hovered or clicked
        if (Gdx.input.getX() < x+Button_Width && Gdx.input.getX() > x && Flamabill.V_HEIGHT - Gdx.input.getY() > QuitButtonY && Flamabill.V_HEIGHT - Gdx.input.getY() < QuitButtonY+Button_Height) {
            game.batch.draw(QuitButtonActive, x, QuitButtonY, Button_Width, Button_Height);

            //quits the game when clicked
            if(Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        } else {
            game.batch.draw(QuitButtonInactive, x, QuitButtonY, Button_Width, Button_Height);
        }

        game.batch.end();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        //sprite.draw(batch);
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw((TextureRegion) RunAnimation.getKeyFrame(elapsedTime, true), 0, 0);
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
        batch.dispose();
        textureAtlas.dispose();
    }
}
