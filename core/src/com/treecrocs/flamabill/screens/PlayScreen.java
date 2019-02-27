package com.treecrocs.flamabill.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.treecrocs.flamabill.Flamabill;
import com.treecrocs.flamabill.characters.CharacterController;
import com.treecrocs.flamabill.characters.Player;
import com.treecrocs.flamabill.tools.WorldGenerator;

public class PlayScreen implements Screen {

    private OrthographicCamera camera;
    private FitViewport viewport;
    private Flamabill game;
    private Player player;
    private TextureAtlas atlas;
    private Hud hud;
    private World world;

    // Debug renderer gives outlines to the objects
    private Box2DDebugRenderer b2dr;
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private CharacterController controller;

    public PlayScreen (Flamabill game){
        this.game = game;

        // Create world object with -9.8g in Y axis
        this.world = new World(new Vector2(0f,-9.80f), true);

        atlas = new TextureAtlas(Gdx.files.internal("Flama-Bill-Complete.atlas"));
        hud = new Hud(game.batch);

        camera = new OrthographicCamera();
        viewport = new FitViewport(Flamabill.V_WIDTH / Flamabill.PPM,Flamabill.V_HEIGHT / Flamabill.PPM, camera);

        // Load map and set up renderer
        maploader = new TmxMapLoader();
        map = maploader.load("../../stuff/Tiled/testmap2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Flamabill.PPM);

        // Center the camera correctly at the start
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);
        // For some reason all three of them return 0 with ExtendViewport is used
        System.out.println(viewport.getWorldWidth() + " | " + viewport.getScreenWidth() + " | " + viewport.getScreenX());

        // Vector2 is for gravity, the true is for sleeping bodies
        // (does not calculate objects that haven't moved)
        world = new World(new Vector2(0, -10f), true);
        b2dr = new Box2DDebugRenderer();

        controller = new CharacterController();
        player = new Player(this, controller);

        // Loads in the objects
        new WorldGenerator(world, map);

    }

    public World getWorld(){
        return this.world;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(controller);
    }

    // Updates camera position
    public void update(float dt) {
        world.step(1/60f, 6, 2);


        // player's body becomes the center of the camera position
        camera.position.x = player.b2d.getPosition().x;
        camera.position.y = player.b2d.getPosition().y;

        player.determineMovement(dt);
        player.update(dt);

        camera.update();
        // renderer will only draw what the camera can see in the game world
        renderer.setView(camera);
    }

    @Override
    public void render(float delta) {
        // delta time
        update(delta);

        // Clear the game screen with black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world, camera.combined);

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

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

    public TextureAtlas getAtlas(){
        return atlas;
    }
}
