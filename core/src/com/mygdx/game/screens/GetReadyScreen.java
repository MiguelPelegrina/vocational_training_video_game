package com.mygdx.game.screens;

import static com.mygdx.game.extras.Utils.SCREEN_HEIGHT;
import static com.mygdx.game.extras.Utils.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.MainGame;
import com.mygdx.game.extras.AssetMan;

/**
 *
 */
public class GetReadyScreen extends BaseScreen{
    // Declaración de variables
    // TODO Megaman?

    /**
     * Constructor por parámetros
     * @param mainGame
     */
    public GetReadyScreen(MainGame mainGame) {
        super(mainGame);

        this.fontCamera = (OrthographicCamera) this.stage.getCamera();

        prepareMessage();
    }

    @Override
    public void render(float delta){
        super.render(delta);

        this.stage.getBatch().setProjectionMatrix(this.fontCamera.combined);
        this.stage.getBatch().begin();
        this.font.draw(this.stage.getBatch(), this.text + "", SCREEN_WIDTH*0.25f, SCREEN_HEIGHT*0.65f);
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
        //addFlammie();

        //startMusic();
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
