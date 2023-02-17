package com.mygdx.game.screens;

import static com.mygdx.game.extras.Utils.SCREEN_HEIGHT;
import static com.mygdx.game.extras.Utils.SCREEN_WIDTH;
import static com.mygdx.game.extras.Utils.WORLD_HEIGHT;
import static com.mygdx.game.extras.Utils.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.MainGame;
import com.mygdx.game.actors.Bat;
import com.mygdx.game.actors.Flammie;
import com.mygdx.game.extras.AssetMan;

/**
 * Clase que mostrará un tutorial. Utilizaremos el estado del tutorial para mostrar los diferentes
 * aspectos del juego: evitar enemigos, coleccionar manzanas y como desplazarse
 */
public class TutorialScreen extends BaseScreen{
    // Atributos de la clase
    private static final float SWAP_STATE_TIME = 3f;
    // Atributos de la instancia
    private Flammie flammie;
    private Bat bat;
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
        this.flammie = new Flammie(this.world, new Vector2(WORLD_WIDTH/2f,WORLD_HEIGHT/4f));
        this.bat = new Bat(this.world, new Vector2(WORLD_WIDTH/2f,WORLD_HEIGHT/2f));
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
            if(this.state == 5) {
                this.state = 1;
            }
        }

        /*switch (state){
            case 1:
                this.stage.addActor(this.flammie);
                this.flammie.stopActor();
                break;
            case 2:
                this.stage.addAction(Actions.removeActor(this.flammie));
                this.stage.addActor(this.bat);
                break;
            case 3:
                break;
            case 4:
                break;
        }
        this.stage.getBatch().setProjectionMatrix(worldCamera.combined);
        this.stage.act();
        this.world.step(delta,6,2);
        this.stage.draw();*/

        this.stage.getBatch().setProjectionMatrix(this.fontCamera.combined);
        this.stage.getBatch().begin();
        configureMessage();
        this.font.draw(this.stage.getBatch(), this.text + "", SCREEN_WIDTH*0.25f, SCREEN_HEIGHT*0.65f);
        this.stage.getBatch().end();

        if(Gdx.input.justTouched()){
            this.mainGame.setScreen(new GetReadyScreen(this.mainGame));
            dispose();
        }
    }

    private void configureMessage(){
        switch (this.state){
            case 1:
                prepareMessage("Move Flammie\nto the direction\nyou want!");
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
        }
    }
}
