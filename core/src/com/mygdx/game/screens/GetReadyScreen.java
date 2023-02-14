package com.mygdx.game.screens;

import static com.mygdx.game.extras.Utils.SCREEN_HEIGHT;
import static com.mygdx.game.extras.Utils.SCREEN_WIDTH;
import static com.mygdx.game.extras.Utils.WORLD_HEIGHT;
import static com.mygdx.game.extras.Utils.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MainGame;
import com.mygdx.game.extras.AssetMan;

/**
 *
 */
public class GetReadyScreen extends BaseScreen{
    // Declaración de variables
    //TODO Megaman?

    /**
     * Constructor por parámetros
     * @param mainGame
     */
    public GetReadyScreen(MainGame mainGame) {
        super(mainGame);

        this.world = new World(new Vector2(0,0), true);

        FitViewport fitViewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        this.stage = new Stage(fitViewport);
        this.fontCamera = (OrthographicCamera) this.stage.getCamera();

        prepareMessage();
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.stage.getBatch().setProjectionMatrix(this.fontCamera.combined);
        this.stage.getBatch().begin();
        this.font.draw(this.stage.getBatch(), this.text + "", SCREEN_WIDTH*0.3f, SCREEN_HEIGHT*0.7f);
        this.stage.getBatch().end();

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

        //music.setLooping(true);
        //music.play();
    }

    // Métodos auxiliares
    private void prepareMessage() {
        // Configuramos la fuente y su escala
        this.text = "Ready?\nTouch the \nscreen to start!";
        this.font = AssetMan.getInstance().getFont();
        this.font.getData().scale(1f);

        // Instanciamos la cámara con el tamáno de la pantalla
        this.fontCamera = new OrthographicCamera();
        this.fontCamera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.fontCamera.update();
    }
}
