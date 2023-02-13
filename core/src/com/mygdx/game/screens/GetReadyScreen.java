package com.mygdx.game.screens;

import static com.mygdx.game.extras.Utils.WORLD_HEIGHT;
import static com.mygdx.game.extras.Utils.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MainGame;

/**
 *
 */
public class GetReadyScreen extends BaseScreen{
    // Declaración de variables
    private OrthographicCamera camera;
    private Stage stage;
    private World world;

    /**
     * Constructor por parámetros
     * @param mainGame
     */
    public GetReadyScreen(MainGame mainGame) {
        super(mainGame);

        this.world = new World(new Vector2(0,0), true);

        FitViewport fitViewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        this.stage = new Stage(fitViewport);
        this.camera = (OrthographicCamera) this.stage.getCamera();
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.isTouched()){
            this.mainGame.setScreen(new GameScreen(this.mainGame));
            dispose();
        }
    }

    /**
     *
     */
    @Override
    public void show(){
        super.show();
        //addBackground();
        //addFlammie();

        //musicBG.setLooping(true);
        //musicBG.play();
    }
}
