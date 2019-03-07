package com.treecrocs.flamabill.tools;

import box2dLight.DirectionalLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.treecrocs.flamabill.Flamabill;
import com.treecrocs.flamabill.characters.Player;
import com.treecrocs.flamabill.screens.PlayScreen;
import com.treecrocs.flamabill.worldobjects.Campfire;

public class LightRenderer{

    private RayHandler rayHandler;
    private OrthographicCamera camera;
    private PointLight player1Light;
    private PointLight player2Light;
    private PlayScreen playScreen;

    public LightRenderer(PlayScreen playScreen, OrthographicCamera camera, RayHandler rayHandler, Player player1, Player player2){
        this.camera = camera;
        this.rayHandler = rayHandler;
        this.playScreen = playScreen;

        Filter filter = new Filter();
        filter.categoryBits = EntityCategory.PLAYER;
        filter.maskBits = EntityCategory.PLAYER | EntityCategory.CHECKPOINT;

        player1Light = new PointLight(rayHandler, 50, Color.RED, 2f, player1.playerBody.getPosition().x, player1.playerBody.getPosition().y);
        player1Light.setSoft(true);
        player1Light.setXray(true);
        player1Light.setSoftnessLength(3f);
        player1Light.setContactFilter(filter);
        player1Light.attachToBody(player1.playerBody);

        player2Light = new PointLight(rayHandler, 50, Color.BLUE, 2f, player2.playerBody.getPosition().x, player2.playerBody.getPosition().y);
        player2Light.setSoft(true);
        player2Light.setXray(true);
        player2Light.setSoftnessLength(3f);
        player2Light.setContactFilter(filter);
        player2Light.attachToBody(player2.playerBody);
        renderCampfires();

        DirectionalLight globalLight = new DirectionalLight(rayHandler,128,null, -90);
        globalLight.setDirection(-90);
        globalLight.add(rayHandler);
        System.out.println(globalLight.getDistance());
        globalLight.setSoftnessLength(500f);
        //globalLight.setXray(true);
    }

    public void renderLighting(){
        rayHandler.setCombinedMatrix(camera);
        rayHandler.updateAndRender();
    }

    public void renderCampfires(){
        for(Campfire campfire: playScreen.getWorldGen().getCampfires()){
            PointLight temp = new PointLight(rayHandler, 20, Color.ORANGE, 1f, 0,0);
            temp.attachToBody(campfire.body);
            temp.setContactFilter(EntityCategory.CHECKPOINT, EntityCategory.PLAYER, EntityCategory.GROUND);
            //temp.setXray(true);
        }
    }

    public RayHandler getRayHandler() {
        return rayHandler;
    }
}
