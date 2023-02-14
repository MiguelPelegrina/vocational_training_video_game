package com.mygdx.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.MainGame;

/**
 * Clase abstracta que implementa la interfaz Screen. Implementa los métodos de dicha interfaz
 * para que posteriormente las clases que heredan solo tengan que sobrecargar los métodos que
 * necesiten.
 * Alternativamente tendríamos mucho código repetido a través de métodos implementados
 * que no se van a utilizar.
 */
public abstract class BaseScreen implements Screen {
    // Atributos de la instancia
    protected MainGame mainGame;
    // TODO ABSTRAER TODOS LOS ATRIBUTOS Y MÉTODOS REPETIDOS
    protected Stage stage;
    protected World world;
    protected Music music;
    protected BitmapFont text;

    /**
     * Constructor por parámetros
     * @param mainGame
     */
    public BaseScreen(MainGame mainGame){
        this.mainGame = mainGame;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
