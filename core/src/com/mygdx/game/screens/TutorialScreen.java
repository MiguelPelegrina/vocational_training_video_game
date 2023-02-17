package com.mygdx.game.screens;

import static com.mygdx.game.extras.Utils.SCREEN_HEIGHT;
import static com.mygdx.game.extras.Utils.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.MainGame;
import com.mygdx.game.extras.AssetMan;

/**
 * Clase que mostrará un tutorial. Utilizaremos el estado del tutorial para mostrar los diferentes
 * aspectos del juego: evitar enemigos, coleccionar manzanas y como desplazarse
 */
public class TutorialScreen extends BaseScreen{
    // Atributos de la clase
    private static final float SWAP_STATE_TIME = 2.5f;
    // Atributos de la instancia
    private float timeToSwapState;
    private int state;
    private Sound listenSound;

    private OrthographicCamera worldCamera;

    /**
     * Constructor por parámetros
     * @param mainGame
     */
    public TutorialScreen(MainGame mainGame) {
        super(mainGame);
        this.state = 1;
        this.timeToSwapState = 0f;
        this.listenSound = AssetMan.getInstance().getListenSound();

        this.worldCamera = (OrthographicCamera) this.stage.getCamera();

        prepareMessage("");
    }

    @Override
    public void show() {
        super.show();
        this.listenSound.play();
    }

    @Override
    public void render(float delta){
        super.render(delta);

        this.timeToSwapState += delta;
        if(this.timeToSwapState >= SWAP_STATE_TIME){
            this.timeToSwapState -= SWAP_STATE_TIME;
            this.state++;
            if(this.state == 6) {
                this.state = 1;
            }
        }

        this.stage.getBatch().setProjectionMatrix(this.fontCamera.combined);
        this.stage.getBatch().begin();
        configureMessage();
        this.font.draw(this.stage.getBatch(), this.text + "", SCREEN_WIDTH*0.25f, SCREEN_HEIGHT*0.65f);
        this.stage.getBatch().end();

        if(Gdx.input.justTouched()){
            this.mainGame.setScreen(new GameScreen(this.mainGame));
            dispose();
        }
    }

    // Métodos auxiliares
    /**
     * Configuramos el mensaje que se va a mostrar en función del estado actual
     */
    private void configureMessage(){
        switch (this.state){
            case 1:
                prepareMessage("Move Flammie\nto the point\nyou wish by\ntouching!");
                break;
            case 2:
                prepareMessage("Dodge bats!");
                break;
            case 3:
                prepareMessage("Grab apples!");
                break;
            case 4:
                prepareMessage("Don't touch\nthe borders!");
                break;
            case 5:
                prepareMessage("Touch the\nscreen to \nto start!");
                break;
        }
    }
}
