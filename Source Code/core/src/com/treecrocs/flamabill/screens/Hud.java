package com.treecrocs.flamabill.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.treecrocs.flamabill.Flamabill;
import com.treecrocs.flamabill.characters.Player;

public class Hud {

    public Stage stage;
    private Viewport viewport;

    public SpriteBatch batch;
    private TextureAtlas barAtlas;

    private Label countdownLabel;
    private Label flamaLabel;

    private int healthBarWidth = 192;
    private int healthBarHeight = 64;
    private float elapsedTime = 0;
    private float spawnDelay = 1.8f;
    private Player player;
    private boolean spawning;
    private boolean thereIsAWinner;

    public Animation<TextureRegion> healthBar;


    private boolean deadToTimer;

    public Hud(SpriteBatch sb, String playerLabel, Player player){
        this.player = player;
        batch = new SpriteBatch();
        barAtlas = new TextureAtlas(Gdx.files.internal("HealthBar.atlas"));
        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.clear();

        for (int i = 0; i < 35; i++ ) {
            TextureRegion temp = new TextureRegion(barAtlas.findRegion("HealthBar " + i), 0, 0, healthBarWidth, healthBarHeight);
            frames.add(temp);
        }
        healthBar = new Animation<TextureRegion>(0.5f, frames);


        viewport = new FitViewport(Flamabill.V_WIDTH, Flamabill.V_HEIGHT/2f, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.padRight(Flamabill.V_WIDTH - Flamabill.V_WIDTH/8f);
        table.setFillParent(true);

        //countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        flamaLabel = new Label(playerLabel, new Label.LabelStyle(new BitmapFont(), Color.WHITE));


        table.add(flamaLabel).expandX().padTop(10);
        table.row();
        //table.add(countdownLabel).expandX();

        stage.addActor(table);

    }


    public void update(float dt) {

        if(healthBar.getKeyFrameIndex(elapsedTime) == 34){
            player.dieToTimer();
            spawning = true;
            elapsedTime = 0;
        }

        if(player.isDeadToTimer()){
            spawning = true;
            elapsedTime = 0;
        }

        if(spawning){
            elapsedTime = 0;
            spawnDelay -= dt;
            if(spawnDelay <= 0){
                spawning = false;
                spawnDelay = 1.8f;
            }
        }

        if(player.isReplenishingHealth()){
            elapsedTime = 0;
        }
    }

    public void draw(float dt){
        batch.begin();

        elapsedTime += dt;
        batch.draw(healthBar.getKeyFrame(elapsedTime,false),(Flamabill.V_WIDTH/2f) - (healthBarWidth/2f),100);
//        if(thereIsAWinner){
//            new Label(displayWinner())
//        }
        batch.end();
    }


    public void dispose(){
        stage.dispose();
        batch.dispose();
    }

    public String displayWinner(String winner){
        this.thereIsAWinner = true;
        return winner;
    }

}