package com.mygdx.game.screens;

import static com.mygdx.game.extras.Utils.SCREEN_HEIGHT;
import static com.mygdx.game.extras.Utils.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.MainGame;
import com.mygdx.game.extras.AssetMan;

public class PauseScreen extends BaseScreen{
    /**
     * Constructor por parámetros
     *
     * @param mainGame Instancia del juego
     */
    public PauseScreen(MainGame mainGame) {
        super(mainGame);

        this.background = new Image(AssetMan.getInstance().getBackgroundDark());

        this.text = "Ready to\nto continue?";
        prepareMessage();
    }

    @Override
    public void render(float delta){
        super.render(delta);

        this.stage.getBatch().setProjectionMatrix(this.fontCamera.combined);
        this.stage.getBatch().begin();
        this.font.draw(this.stage.getBatch(), this.text,SCREEN_WIDTH*0.3f, SCREEN_HEIGHT*0.65f);
        this.stage.getBatch().end();

        if(Gdx.input.isTouched()){
            // Nos creamos una nueva pantalla de juego para empezar una nueva ronda
            this.mainGame.setScreen(this.mainGame.gameScreen);
        }
    }
}
