package com.mygdx.game.screens;

import static com.mygdx.game.extras.Utils.SCREEN_HEIGHT;
import static com.mygdx.game.extras.Utils.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.MainGame;
import com.mygdx.game.extras.AssetMan;

/**
 * Clase que mostrará un tutorial. Utilizaremos el estado del tutorial para mostrar los diferentes
 * aspectos del juego: evitar enemigos, coleccionar manzanas y como desplazarse
 */
public class TutorialScreen extends BaseScreen{
    // Atributos de la clase
    private static final float SWAP_STATE_TIME = 4f;
    // Atributos de la instancia
    private float timeToSwapState;
    private int state;
    private Sound sound;

    /**
     * Constructor por parámetros
     * @param mainGame
     */
    public TutorialScreen(MainGame mainGame) {
        super(mainGame);
        this.state = 1;
        this.timeToSwapState = 0f;
        this.sound = AssetMan.getInstance().getListenSound();

        prepareMessage("Dodge bats!");
    }

    @Override
    public void render(float delta){
        super.render(delta);

        this.timeToSwapState += delta;
        if(this.timeToSwapState >= SWAP_STATE_TIME){
            this.timeToSwapState -= SWAP_STATE_TIME;
            if(this.state < 5){

            }else{
                this.state = 1;
            }
        }

        this.stage.getBatch().setProjectionMatrix(this.fontCamera.combined);
        this.stage.getBatch().begin();
        switch (state){
            case 1:
                this.font.draw(this.stage.getBatch(), this.text + "", SCREEN_WIDTH*0.25f, SCREEN_HEIGHT*0.65f);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        }

        this.stage.getBatch().end();

        if(Gdx.input.isTouched()){
            // TODO ES RARO, ESTO NO DEBERÍA SER NECESARIO
            this.stage.addAction(Actions.sequence(
                    Actions.delay(0.11f),
                    Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            mainGame.setScreen(new GetReadyScreen(mainGame));
                        }
                    })
            ));
        }
    }
}
