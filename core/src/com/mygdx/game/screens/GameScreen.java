package com.mygdx.game.screens;

import static com.mygdx.game.actors.Apple.APPLE_HEIGHT;
import static com.mygdx.game.actors.Apple.APPLE_WIDTH;
import static com.mygdx.game.actors.Bat.BAT_HEIGHT;
import static com.mygdx.game.actors.Bat.BAT_WIDTH;
import static com.mygdx.game.extras.Utils.SCREEN_HEIGHT;
import static com.mygdx.game.extras.Utils.SCREEN_WIDTH;
import static com.mygdx.game.extras.Utils.USER_APPLE;
import static com.mygdx.game.extras.Utils.USER_COUNTER;
import static com.mygdx.game.extras.Utils.USER_FLAMMIE;
import static com.mygdx.game.extras.Utils.USER_LEFTBORDER;
import static com.mygdx.game.extras.Utils.USER_RIGHTBORDER;
import static com.mygdx.game.extras.Utils.WORLD_HEIGHT;
import static com.mygdx.game.extras.Utils.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MainGame;
import com.mygdx.game.actors.Apple;
import com.mygdx.game.actors.Bat;
import com.mygdx.game.actors.Flammie;
import com.mygdx.game.extras.AssetMan;

/**
 *
 */
public class GameScreen extends BaseScreen implements ContactListener {
    // Atributo de la clase
    private static final float BAT_SPAWN_TIME = 1.5f;
    // Atributos de la instancia
    private float timeToCreateBat;
    private Flammie flammie;
    private Apple apple;
    private Sound hitSound;

    private Body leftBorder;
    private Body rightBorder;
    private Fixture leftBorderFixture;
    private Fixture rightBorderFixture;

    private int scoreNumber;

    private Array<Bat> arrayBats;

    // Depuración
    // TODO QUITAR AL FINAL
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera worldCamera;

    /**
     * Constructor por parámetros
     * @param mainGame
     */
    public GameScreen(MainGame mainGame) {
        super(mainGame);

        this.world.setContactListener(this);

        this.arrayBats = new Array<>();
        this.timeToCreateBat= 0f;

        prepareGameSound();
        prepareScore();

        // TODO QUITAR AL FINAL
        this.worldCamera = (OrthographicCamera) this.stage.getCamera();
        this.debugRenderer = new Box2DDebugRenderer();
    }

    /**
     *
     */
    public void addBackground(){
        this.background = new Image(AssetMan.getInstance().getBackground());
        this.background.setPosition(0,0);
        this.background.setSize(WORLD_WIDTH,WORLD_HEIGHT);
        this.stage.addActor(this.background);
    }

    /**
     *
     */
    public void addFlammie(){
        this.flammie = new Flammie(this.world, new Vector2(WORLD_WIDTH/2f,WORLD_HEIGHT/4f));
        this.stage.addActor(this.flammie);
    }

    public void addApple(){
        float randomXpos = MathUtils.random(APPLE_WIDTH, WORLD_WIDTH - APPLE_WIDTH);
        this.apple = new Apple(this.world, new Vector2(randomXpos,WORLD_HEIGHT + APPLE_HEIGHT + 0.1f));
        this.stage.addActor(this.apple);
    }

    public void removeApple(){
        if(!this.world.isLocked()){
            if(this.apple.isOutOfScreen(APPLE_HEIGHT)){
                this.apple.detach();
                this.apple.remove();
            }
        }
    }

    /**
     *
     * @param delta
     */
    public void addBats(float delta){
        // Mientras que el muñeco está vivo
        if(flammie.getState() == Flammie.STATE_ALIVE){
            // Acumulamos el tiempo entre un fotograma y el siguiente
            this.timeToCreateBat += delta;
            // Si el tiempo acumulado supera el establecido TODO como dificultad?
            if(this.timeToCreateBat >= BAT_SPAWN_TIME){
                // Reiniciamos el contador
                this.timeToCreateBat -= BAT_SPAWN_TIME;
                // Instanciamos un grupo de rocas fuera de la pantalla en función de la posición
                // actual de nuestro protagonista
                Bat bat = new Bat(this.world, new Vector2(flammie.getX() + BAT_WIDTH/2f, WORLD_HEIGHT + 0.5f));
                arrayBats.add(bat);
                // Añadimos el murciélago a la escena
                this.stage.addActor(bat);
            }
        }
    }

    /**
     *
     */
    public void removeBats(){
        // Por cada uno de los grupos de rocas (roca inferior, roca superior y bloque del contador)
        for(Bat bats : this.arrayBats){
            // Mientras que no se esté actualizando el mundo en este momento
            if(!world.isLocked()){
                // Comprobamos si el grupo de rocas se encuentra visibles
                if(bats.isOutOfScreen(BAT_HEIGHT)){
                    // Liberamos el espacio en la gráfica
                    bats.detach();
                    // Quitamos las rocas de la escena
                    bats.remove();
                    // Sacamos las rocas de la colección
                    arrayBats.removeValue(bats,false);
                }
            }
        }
    }

    /**
     *
     * @param delta
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Colocamos los murciélagos
        addBats(delta);
        // Configuramos el lote del escenario de tal forma que representa los elementos en función
        // del tamaño del mundo
        this.stage.getBatch().setProjectionMatrix(worldCamera.combined);
        this.stage.act();
        this.world.step(delta,6,2);
        this.stage.draw();

        this.debugRenderer.render(this.world, this.worldCamera.combined);
        // Liberamos el espacio de la gráfica destinado a las rocas que se encuentra ya fuera de la
        // pantalla
        removeBats();

        // Configuramos el lote del escenario de tal forma que representa solo la fuente en función
        // de la resolución de la pantalla en píxeles
        this.stage.getBatch().setProjectionMatrix(this.fontCamera.combined);
        this.stage.getBatch().begin();
        this.font.draw(this.stage.getBatch(), "Points: " + this.scoreNumber, SCREEN_WIDTH*0.1f, SCREEN_HEIGHT*0.95f);
        this.stage.getBatch().end();
    }

    /**
     *
     */
    @Override
    public void show(){
        super.show();
        addFlammie();
        addApple();
        addBorder(leftBorder,leftBorderFixture, USER_LEFTBORDER,new Vector2(0,0), new Vector2(0,WORLD_HEIGHT));
        addBorder(rightBorder,rightBorderFixture, USER_RIGHTBORDER,new Vector2(WORLD_WIDTH,0),new Vector2(WORLD_WIDTH,WORLD_HEIGHT));

        startMusic();
    }

    /**
     *
     */
    @Override
    public void hide() {
        this.flammie.detach();
        this.flammie.remove();

        this.music.stop();
    }

    /**
     *
     */
    @Override
    public void dispose() {
        this.stage.dispose();
        this.world.dispose();
        this.music.dispose();
    }

    @Override
    public void beginContact(Contact contact) {
        if(areColider(contact, USER_FLAMMIE, USER_APPLE)){
            this.scoreNumber++;
            this.apple.remove();

        }else{
            flammie.dies();
            this.hitSound.play();
            this.music.stop();
            for (Bat bat : arrayBats) {
                bat.stopActor();
            }

            this.stage.addAction(Actions.sequence(
                    Actions.delay(1.5f),
                    Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            mainGame.setScreen(mainGame.gameOverScreen);
                        }
                    })
            ));
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    // Métodos auxiliares
    /**
     * Método encargado de averiguar si dos actores se han chocado. Internamente debe comprobar dos
     * casos: si A se ha topado con B o si B se ha topado con A, ya que el registro del contacto
     * sino puede ser erróneo.
     * @param contact Objeto que registra el contacto entre dos objetos
     * @param objA Objeto A que se registra en el contacto
     * @param objB Objeto B que se registra en el contacto
     * @return Devuelve true si se ha producido contacto entre los dos objetos o false si no se ha
     * producido contacto.
     */
    private boolean areColider(Contact contact, Object objA, Object objB){
        return (contact.getFixtureA().getUserData().equals(objA) && contact.getFixtureB().getUserData().equals(objB)) ||
                (contact.getFixtureA().getUserData().equals(objB) && contact.getFixtureB().getUserData().equals(objA));
    }

    private void addBorder(Body border, Fixture fixture, String user, Vector2 vector1, Vector2 vector2) {
        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.StaticBody;
        border = world.createBody(bodydef);

        EdgeShape edge = new EdgeShape();
        edge.set(vector1,vector2);
        fixture = border.createFixture(edge, 1);
        fixture.setUserData(user);
        edge.dispose();
    }

    /**
     * Método encargado de configurar la música y los sonidos
     */
    private void prepareGameSound() {
        this.music = AssetMan.getInstance().getBGMusic();
        this.hitSound = AssetMan.getInstance().getHitSound();
    }

    /**
     * Método encargado de configurar la puntuación
     */
    private void prepareScore(){
        // Configuramos la fuente y su escala
        this.scoreNumber = 0;
        this.font = AssetMan.getInstance().getFont();
        this.font.getData().scale(1f);

        // Instanciamos la cámara con el tamáno de la pantalla
        this.fontCamera = new OrthographicCamera();
        this.fontCamera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.fontCamera.update();
    }
}
