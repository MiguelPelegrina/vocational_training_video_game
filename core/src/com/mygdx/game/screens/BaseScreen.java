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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
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
     * @param mainGame Instancia del juego
     */
    public BaseScreen(MainGame mainGame){
        this.mainGame = mainGame;
        // Como todos los actores de este juego vuelan, el mundo no tendrá gravedad
        this.world = new World(new Vector2(0,0), true);
        // Instanciamos el punto de vista
        FitViewport fitViewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        // Instanciamos el escenario
        this.stage = new Stage(fitViewport);
    }

    /**
     * Método encargado de colocar la imagen de fondo
     */
    @Override
    public void show() {
        addBackground();
    }

    /**
     *
     * @param delta
     */
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
     * Método encargado de crear un borde en función de los parámetros establecidos
     * @param body Body que funge como cuerpo del borde
     * @param fixture Fixture que como forma física del cuerpo
     * @param userData UserData que identifica
     * @param vector1
     * @param vector2
     */
    protected void addBorder(Body body, Fixture fixture, String userData, Vector2 vector1, Vector2 vector2) {
        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.StaticBody;
        body = this.world.createBody(bodydef);

        EdgeShape edge = new EdgeShape();
        edge.set(vector1,vector2);
        fixture = body.createFixture(edge, 1);
        fixture.setUserData(userData);
        edge.dispose();
    }

    /**
     * Método encargado de detectar colisiones entre dos clases de objetos
     * @param contact Contacto registrado
     * @param objA Primer objeto implicado en la colision
     * @param objB Segundo objeto implicado en la colision
     * @return Devuelve true si uno de los dos objetos ha colisionado con el otro o false si no han
     * colisionado
     */
    protected boolean areColliding(Contact contact, Object objA, Object objB) {
        return (contact.getFixtureA().getUserData().equals(objA) && contact.getFixtureB().getUserData().equals(objB)) ||
                (contact.getFixtureA().getUserData().equals(objB) && contact.getFixtureB().getUserData().equals(objA));
    }

    /**
     * Método encargado de preparar el texto que se va a mostrar por pantalla
     */
    protected void prepareMessage() {
        // Configuramos la fuente y su escala
        this.font = AssetMan.getInstance().getFont();
        // Es necesario escalar la fuente, si el tamaño de las letras es más grande. En este caso
        // es más grande porque sino las letras quedan difuminadas
        this.font.getData().setScale(0.5f,0.5f);

        // Instanciamos la cámara con el tamáno de la pantalla
        this.fontCamera = new OrthographicCamera();
        this.fontCamera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.fontCamera.update();
    }

    /**
     * Método encargado colocar la imagen de fondo y añadirla al escenario
     */
    protected void addBackground(){
        this.background.setPosition(0,0);
        this.background.setSize(WORLD_WIDTH,WORLD_HEIGHT);
        this.stage.addActor(this.background);
    }

    /**
     * Método encargado de empezar a tocar la música instanciada previamente
     */
    protected void startMusic(){
        this.music.setLooping(true);
        this.music.play();
    }
}
