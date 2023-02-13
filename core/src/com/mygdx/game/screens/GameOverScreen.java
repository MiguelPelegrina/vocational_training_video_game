package com.mygdx.game.screens;

import static com.mygdx.game.extras.Utils.USER_LEFTBORDER;
import static com.mygdx.game.extras.Utils.USER_RIGHTBORDER;
import static com.mygdx.game.extras.Utils.WORLD_HEIGHT;
import static com.mygdx.game.extras.Utils.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MainGame;
import com.mygdx.game.extras.AssetMan;

/**
 *
 */
public class GameOverScreen extends BaseScreen{
    private OrthographicCamera camera;
    private Music musicGO;

    /**
     * Constructor por parámetros
     * @param mainGame
     */
    public GameOverScreen(MainGame mainGame) {
        super(mainGame);

        this.musicGO = AssetMan.getInstance().getGOMusic();
    }

    /**
     *
     */
    @Override
    public void show(){
        super.show();

        musicGO.setLooping(true);
        musicGO.play();
    }

    /**
     *
     */
    @Override
    public void hide() {
        this.musicGO.stop();
    }

    /**
     *
     */
    @Override
    public void dispose() {
        this.musicGO.dispose();
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.isTouched()){

            this.mainGame.setScreen(new GameScreen(this.mainGame));
            dispose();
        }
    }
}
