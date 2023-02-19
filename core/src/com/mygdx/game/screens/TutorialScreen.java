package com.mygdx.game.screens;

import static com.mygdx.game.extras.Utils.SCREEN_HEIGHT;
import static com.mygdx.game.extras.Utils.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
        this.background = new Image(AssetMan.getInstance().getBackgroundDark());
        prepareMessage();
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
            if(this.state == 4) {
                this.state = 1;
            }
        }

        configureMessage();
        this.stage.getBatch().setProjectionMatrix(this.fontCamera.combined);
        this.stage.getBatch().begin();
        this.font.draw(this.stage.getBatch(), this.text, SCREEN_WIDTH*0.25f, SCREEN_HEIGHT*0.65f);
        this.stage.getBatch().end();

        if(Gdx.input.justTouched()){
            this.mainGame.setScreen(new GameScreen(this.mainGame));
        }
    }

    // Métodos auxiliares
    /**
     * Configuramos el mensaje que se va a mostrar en función del estado actual
     */
    private void configureMessage(){
        switch (this.state){
            case 1:
                this.text = "Move Flammie\nto the point\nyou wish by\ntouching!";
                break;
            case 2:
                this.text = "Dodge bats\nand eat apples!";
                break;
            case 3:
                this.text = "Touch the\nscreen to start!";
                break;
        }
    }
}
