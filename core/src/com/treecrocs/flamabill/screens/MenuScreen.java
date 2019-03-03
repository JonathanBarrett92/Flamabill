package com.treecrocs.flamabill.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.treecrocs.flamabill.Flamabill;


public class MenuScreen implements Screen {

    private static final int Button_Height = Flamabill.V_HEIGHT /5;
    private static final int Button_Width = Button_Height * 2;
    private static final int PlayButtonY = Flamabill.V_HEIGHT / 2 - 100;
    private static final int QuitButtonY = (Flamabill.V_HEIGHT /2) - (Button_Height + 25) - 100;

    private static final int QuitX = ((Flamabill.V_WIDTH/2) - (Button_Width/2)) + (Button_Width/2) -15;
    private static final int PlayX = ((Flamabill.V_WIDTH/2) - (Button_Width/2)) + (Button_Width/2) -15;
    private static final int QuitY = (Flamabill.V_HEIGHT /2) - (Button_Height + 25) - (Button_Height/2) +5;
    private static final int PlayY = PlayButtonY+50;

    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private TextureAtlas textureAtlasButton;
    private float elapsedTime = 0;
    public static Texture backgroundTexture;
    public static Sprite backgroundSprite;

    private final Animation LogoAnimation;
    private final Animation logoAnimation2;
    private final Animation ButtonActive;

    private Label PlayLabel;
    private Label QuitLabel;

    private Music menuMusic;

    Flamabill game;

    //creates the textures for the buttons

    private Texture PlayButtonInactive;
    private Texture QuitButtonInactive;


    public MenuScreen(Flamabill game){
        this.game = game;
        BitmapFont font = new BitmapFont();

//        backgroundTexture = new Texture("DumbBackground.png");
//        backgroundSprite = new Sprite(backgroundTexture);

        //shows the app what to images to call and use from Assets, placeholders at the minute I'll update them once we get this going properly
        PlayButtonInactive = new Texture("Menu_Button_Play_Inactive.png");
        QuitButtonInactive = new Texture("Menu_Button_Play_Inactive.png");


        batch = new SpriteBatch();
        textureAtlas = new TextureAtlas(Gdx.files.internal("Logo.atlas"));
        textureAtlasButton = new TextureAtlas(Gdx.files.internal("Menu_Button_Play_active.atlas"));

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


        LogoAnimation = new Animation(0.08f, Logo);

        TextureRegion[] Logo2 = new TextureRegion[9];

        // Create an array of TextureRegions
        Logo2[0] = (textureAtlas.findRegion("Logo_Grow1"));
        Logo2[1] = (textureAtlas.findRegion("Logo_Grow2"));
        Logo2[2] = (textureAtlas.findRegion("Logo_Grow3"));
        Logo2[3] = (textureAtlas.findRegion("Logo_Grow4"));
        Logo2[4] = (textureAtlas.findRegion("Logo_Grow5"));
        Logo2[5] = (textureAtlas.findRegion("Logo_Grow6"));
        Logo2[6] = (textureAtlas.findRegion("Logo_Rise1"));
        Logo2[7] = (textureAtlas.findRegion("Logo_Rise2"));
        Logo2[8] = (textureAtlas.findRegion("Logo_Rise3"));

        logoAnimation2 = new Animation(0.08f,Logo2);



        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/gamesongtitle.mp3"));
        menuMusic.setLooping(true);
        menuMusic.play();

        TextureRegion[] Button = new TextureRegion[4];

        // Create an array of TextureRegions
        Button[0] = (textureAtlasButton.findRegion("Menu_Button_Play_active 1"));
        Button[1] = (textureAtlasButton.findRegion("Menu_Button_Play_active 2"));
        Button[2] = (textureAtlasButton.findRegion("Menu_Button_Play_active 3"));
        Button[3] = (textureAtlasButton.findRegion("Menu_Button_Play_active 4"));

        ButtonActive = new Animation(0.08f, Button);

        PlayLabel = new Label("PLAY", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        QuitLabel = new Label("QUIT", new Label.LabelStyle(new BitmapFont(), Color.WHITE));



    }

    @Override
    public void show(){

    }

    public void renderBackground()
    {
        backgroundSprite.draw(batch);
    }

    @Override
    public void render(float delta){
//52.9, 80.8, 92.2
        BitmapFont font = new BitmapFont();
        Gdx.gl.glClearColor(52.9f/255f, 80.8f/255f, 92.2f/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        // batch.begin();
        //renderBackground();
        //batch.end();

        //used to find out where the mouse is as well as position the buttons on the X axis
        int x = (Flamabill.V_WIDTH/2) - (Button_Width/2);

        //play button code for positioning and telling it what to do when hovered or clicked GAME SCREEN MUST BE CHANGED
        if (Gdx.input.getX() < x +Button_Width && Gdx.input.getX() > x && Flamabill.V_HEIGHT - Gdx.input.getY() > PlayButtonY && Flamabill.V_HEIGHT - Gdx.input.getY() < PlayButtonY+Button_Height) {
            batch.begin();
            batch.draw((TextureRegion) ButtonActive.getKeyFrame(elapsedTime, true),  x, PlayButtonY, Button_Width, Button_Height);
            batch.draw(QuitButtonInactive, x, QuitButtonY, Button_Width, Button_Height);

            batch.end();

            //Changes game screen to the actual game, passes that on to Damo i think
            if(Gdx.input.isTouched())
            {
                game.setScreen(new PlayScreen(game)); //Needs to be changed to whatever the game screen will be called
                menuMusic.stop();
            }
        } else
        {
            game.batch.draw(PlayButtonInactive, x, PlayButtonY, Button_Width, Button_Height);

        }

        //Quit button code for positioning and telling it what to do when hovered or clicked
        if (Gdx.input.getX() < x+Button_Width && Gdx.input.getX() > x && Flamabill.V_HEIGHT - Gdx.input.getY() > QuitButtonY && Flamabill.V_HEIGHT - Gdx.input.getY() < QuitButtonY+Button_Height) {
            batch.begin();
            batch.draw((TextureRegion) ButtonActive.getKeyFrame(elapsedTime, true),  x, QuitButtonY, Button_Width, Button_Height);
            batch.draw(PlayButtonInactive, x, PlayButtonY, Button_Width, Button_Height);

            batch.end();
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
        font.draw(batch, "Play", PlayX,  PlayY);
        font.draw(batch, "Quit", QuitX, QuitY);
        batch.end();



        batch.begin();
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw((TextureRegion) logoAnimation2.getKeyFrame(elapsedTime, false), (Flamabill.V_WIDTH/2f) - (160), (3*Flamabill.V_HEIGHT)/5f);
        if(logoAnimation2.isAnimationFinished(elapsedTime))
        {
            batch.draw((TextureRegion) LogoAnimation.getKeyFrame(elapsedTime, true), (Flamabill.V_WIDTH/2f) - (160), (3*Flamabill.V_HEIGHT)/5f);
        }
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
        menuMusic.dispose();
    }
}