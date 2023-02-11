package com.mygdx.game.screens;

import static com.mygdx.game.actors.Bat.BAT_WIDTH;
import static com.mygdx.game.extras.Utils.SCREEN_HEIGHT;
import static com.mygdx.game.extras.Utils.SCREEN_WIDTH;
import static com.mygdx.game.extras.Utils.WORLD_HEIGHT;
import static com.mygdx.game.extras.Utils.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MainGame;
import com.mygdx.game.actors.Bat;
import com.mygdx.game.actors.Flammie;
import com.mygdx.game.extras.AssetMan;

public class GameScreen extends BaseScreen{
    // Atributo de la clase
    private static final float BAT_SPAWN_TIME = 1.5f;

    // Atributos de la instancia
    private float timeToCreateBat;
    private Stage stage;
    private World world;
    private Image background;
    private Flammie flammie;
    private Music musicBG;

    private Body leftBorder;
    private Body rightBorder;
    private Fixture leftBorderFixture;
    private Fixture rightBorderFixture;

    private Array<Bat> arrayBats;

    // Depuración
    // TODO QUITAR AL FINAL
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera worldCamera;

    // Para mostrar la puntuación
    private OrthographicCamera fontCamera;
    private BitmapFont score;

    /**
     * Constructor por parámetros
     * @param mainGame
     */
    public GameScreen(MainGame mainGame) {
        super(mainGame);

        // Como todos los actores de este juego vuelan, el mundo no tendrá gravedad
        this.world = new World(new Vector2(0,0), true);
        FitViewport fitViewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        this.stage = new Stage(fitViewport);

        this.arrayBats = new Array<Bat>();
        this.timeToCreateBat= 0f;

        this.musicBG = AssetMan.getInstance().getBGMusic();

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
        this.flammie = new Flammie(this.world, new Vector2(WORLD_WIDTH/2f,WORLD_HEIGHT/2f));
        this.stage.addActor(this.flammie);
    }

    /**
     *
     * @param delta
     */
    public void addBats(float delta){
        // Mientras que el muñeco está vivo
        if(flammie.getState() == Flammie.STATE_NORMAL){
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
                if(bats.isOutOfScreen()){
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

        // Configuramos el lote del escenario de tal forma que representa solo la fuenta en función
        // de la resolución de la pantalla en píxeles
        this.stage.getBatch().setProjectionMatrix(this.fontCamera.combined);
        this.stage.getBatch().begin();
        this.score.draw(this.stage.getBatch(), arrayBats.size + "", SCREEN_WIDTH/2f, SCREEN_HEIGHT*0.95f);
        this.stage.getBatch().end();
    }

    /**
     *
     */
    @Override
    public void show(){
        super.show();
        addBackground();
        addFlammie();
        addBorder(leftBorder,leftBorderFixture,new Vector2(0,0), new Vector2(0,WORLD_HEIGHT));
        addBorder(rightBorder,rightBorderFixture,new Vector2(WORLD_WIDTH,0),new Vector2(WORLD_WIDTH,WORLD_HEIGHT));

        musicBG.setLooping(true);
        musicBG.play();
    }

    /**
     *
     */
    @Override
    public void hide() {
        this.flammie.detach();
        this.flammie.remove();

        this.musicBG.stop();
    }

    /**
     *
     */
    @Override
    public void dispose() {
        this.stage.dispose();
        this.world.dispose();
        this.musicBG.dispose();
    }

    // Métodos auxiliares
    private void addBorder(Body border, Fixture fixture, Vector2 vector1, Vector2 vector2) {
        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.StaticBody;
        border = world.createBody(bodydef);

        EdgeShape edge = new EdgeShape();
        edge.set(vector1,vector2);
        fixture = border.createFixture(edge, 1);
        edge.dispose();
    }

    /**
     * Método encargado de configurar la puntuación
     */
    private void prepareScore(){
        // Configuramos la fuente y su escala
        this.score = AssetMan.getInstance().getFont();
        this.score.getData().scale(1f);

        // Instanciamos la cámara con el taámano de la pantalla
        this.fontCamera = new OrthographicCamera();
        this.fontCamera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.fontCamera.update();
    }
}
