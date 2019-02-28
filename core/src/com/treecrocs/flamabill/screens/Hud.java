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

public class Hud {

    public Stage stage;
    private Viewport viewport;

    private float elapsedTime = 0;
    private SpriteBatch batch;
    private TextureAtlas barAtlas;

    private Integer worldTimer;
    private float timeCount;
    private Integer score;

    public Animation<TextureRegion> HealthBar;

    private Label countdownLabel;
    private Label scoreLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label FlamaLabel;
    private Label EmptyLabel;

    public Hud(SpriteBatch sb){

        batch = new SpriteBatch();
        barAtlas = new TextureAtlas(Gdx.files.internal("HealthBar.atlas"));
        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.clear();

        for (int i = 0; i < 35; i++ ) {
            frames.add(new TextureRegion(barAtlas.findRegion("HealthBar " + i), 0, 0, 96, 32));
        }
        HealthBar = new Animation<TextureRegion>(1f, frames);

        worldTimer = 70;
        timeCount = 0;
        score = 0;

        viewport = new FitViewport(Flamabill.V_WIDTH, Flamabill.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        FlamaLabel = new Label("Flama-Bill", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        EmptyLabel = new Label(" . ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));


        table.add(FlamaLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.add(EmptyLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);



    }


    public void update(float dt) {
        timeCount += dt;

        if (timeCount>= 1) {
            worldTimer--;
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }

    }

    public void dispose(){
        stage.dispose();
    }

}