package com.mygdx.game.screens;

import static com.mygdx.game.extras.Utils.SCREEN_HEIGHT;
import static com.mygdx.game.extras.Utils.SCREEN_WIDTH;
import static com.mygdx.game.extras.Utils.WORLD_HEIGHT;
import static com.mygdx.game.extras.Utils.WORLD_WIDTH;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.MainGame;
import com.mygdx.game.extras.AssetMan;

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

    // Métodos auxiliares
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

    protected void addBackground(){
        this.background = new Image(AssetMan.getInstance().getBackgroundStart());
        this.background.setPosition(0,0);
        this.background.setSize(WORLD_WIDTH,WORLD_HEIGHT);
        this.stage.addActor(this.background);
    }
}
