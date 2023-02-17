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
    private static final float SWAP_STATE_TIME = 3f;
    // Atributos de la instancia
    private float timeToSwapState;
    private int state;
    private Sound listenSound;

    /**
     * Constructor por parámetros
     * @param mainGame
     */
    public TutorialScreen(MainGame mainGame) {
        super(mainGame);
        this.state = 1;
        this.timeToSwapState = 0f;
        this.listenSound = AssetMan.getInstance().getListenSound();

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
            if(this.state == 5) {
                this.state = 1;
            }
        }

        this.stage.getBatch().setProjectionMatrix(this.fontCamera.combined);
        this.stage.getBatch().begin();
        switch (state){
            case 1:
                prepareMessage("Move Flammie\nto the direction\nyou want!");
                this.font.draw(this.stage.getBatch(), this.text + "", SCREEN_WIDTH*0.25f, SCREEN_HEIGHT*0.65f);
                break;
            case 2:
                prepareMessage("Dodge bats!");
                this.font.draw(this.stage.getBatch(), this.text + "", SCREEN_WIDTH*0.25f, SCREEN_HEIGHT*0.65f);
                break;
            case 3:
                prepareMessage("Grab apples!");
                this.font.draw(this.stage.getBatch(), this.text + "", SCREEN_WIDTH*0.25f, SCREEN_HEIGHT*0.65f);
                break;
            case 4:
                prepareMessage("Don't touch\nthe borders!");
                this.font.draw(this.stage.getBatch(), this.text + "", SCREEN_WIDTH*0.25f, SCREEN_HEIGHT*0.65f);
                break;
        }
        //this.font.draw(this.stage.getBatch(), this.text + "", SCREEN_WIDTH*0.25f, SCREEN_HEIGHT*0.65f);

        this.stage.getBatch().end();

        if(Gdx.input.justTouched()){
            this.mainGame.setScreen(new GetReadyScreen(this.mainGame));
            dispose();
        }
    }
}
