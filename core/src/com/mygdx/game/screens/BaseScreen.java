package com.mygdx.game.screens;

import static com.mygdx.game.extras.Utils.SCREEN_HEIGHT;
import static com.mygdx.game.extras.Utils.SCREEN_WIDTH;
import static com.mygdx.game.extras.Utils.WORLD_HEIGHT;
import static com.mygdx.game.extras.Utils.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MainGame;
import com.mygdx.game.extras.AssetMan;

/**
 * Clase abstracta que implementa la interfaz Screen. Sirve como clase base para las demás pantallas
 * de tal forma que contiene todos los atributos y métodos compartidos
 */
public abstract class BaseScreen implements Screen {
    // Atributos de la instancia
    protected MainGame mainGame;
    protected Stage stage;
    protected World world;
    protected Image background;
    protected OrthographicCamera fontCamera;
    protected Music music;
    protected String text;
    protected BitmapFont font;

    /**
     * Constructor por parámetros
     * @param mainGame
     */
    public BaseScreen(MainGame mainGame){
        this.mainGame = mainGame;

        // Como todos los actores de este juego vuelan, el mundo no tendrá gravedad
        this.world = new World(new Vector2(0,0), true);
        // Asignamos la interfaz encargada de gestionar los eventos de contacto implementados en la
        // propia clase
        FitViewport fitViewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        this.stage = new Stage(fitViewport);
    }

    @Override
    public void show() {
        addBackground();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        this.stage.act();
        this.world.step(delta,6,2);
        this.stage.draw();
    }

    // Métodos de la interfaz Screen a implementar
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

    // Métodos auxiliares para clases hijas
    /**
     *
     * @param text
     */
    protected void prepareMessage(String text) {
        // Configuramos la fuente y su escala
        this.text = text;
        this.font = AssetMan.getInstance().getFont();
        this.font.getData().scale(1f);

        // Instanciamos la cámara con el tamáno de la pantalla
        this.fontCamera = new OrthographicCamera();
        this.fontCamera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.fontCamera.update();
    }

    /**
     *
     */
    protected void addBackground(){
        this.background = new Image(AssetMan.getInstance().getBackgroundStart());
        this.background.setPosition(0,0);
        this.background.setSize(WORLD_WIDTH,WORLD_HEIGHT);
        this.stage.addActor(this.background);
    }

    /**
     *
     */
    protected void startMusic(){
        music.setLooping(true);
        music.play();
    }
}
