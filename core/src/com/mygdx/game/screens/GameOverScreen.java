package com.mygdx.game.screens;


import static com.mygdx.game.extras.Utils.SCREEN_HEIGHT;
import static com.mygdx.game.extras.Utils.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.MainGame;
import com.mygdx.game.extras.AssetMan;

/**
 * Clase que gestiona la pantalla final del juego
 */
public class GameOverScreen extends BaseScreen{
    // Atributos de la instancia
    private int score;

    /**
     * Constructor por parámetros
     * @param mainGame Instancia del juego
     */
    public GameOverScreen(MainGame mainGame) {
        super(mainGame);

        this.background = new Image(AssetMan.getInstance().getBackgroundDark());
        prepareMessage();

        this.music = AssetMan.getInstance().getGOMusic();
    }

    @Override
    public void show(){
        super.show();
        startMusic();
    }

    @Override
    public void hide() {
        this.music.stop();
    }

    @Override
    public void dispose() {
        this.music.dispose();
    }

    @Override
    public void render(float delta){
        super.render(delta);

        this.stage.getBatch().setProjectionMatrix(this.fontCamera.combined);
        this.stage.getBatch().begin();
        this.font.draw(this.stage.getBatch(), "You got\n" + this.score + this.text,
                SCREEN_WIDTH * 0.3f, SCREEN_HEIGHT * 0.8f);
        this.stage.getBatch().end();

        if(Gdx.input.isTouched()){
            // Nos creamos una nueva pantalla de juego para empezar una nueva ronda
            this.mainGame.setScreen(new GameScreen(this.mainGame));
        }
    }

    // Setter
    /**
     * Método encargado de modificar el texto que se va a mostrar posteriormente
     * @param score Puntuación final de la ronda
     */
    public void setScore(int score){
        this.score = score;
        // Comprobamos el número de manzanas para escribir el texto de forma adecuada
        if(this.score == 1){
            this.text = " apple!";
        }else{
            this.text = " apples!";
        }
        this.text += "\n\nTouch the \nscreen to \ntry again!";
    }
}
