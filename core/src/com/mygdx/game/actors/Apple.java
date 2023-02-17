package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.extras.AssetMan;

/**
 * Clase de tipo actor que interactua con el actor Flammie. Se trata de un objeto redondo que será
 * objeto de la hambre del actor principal
 */
public class Apple extends BaseActor {
    // Atributos de la clase
    public static final float APPLE_WIDTH = 0.5f;
    public static final float APPLE_HEIGHT = 0.5f;
    private static final float APPLE_FIXTURE_RADIUS = 0.225f;
    private static final float SPEED = -1f;
    // Atributos de la instancia
    private TextureRegion appleTR;
    private int state;
    private AppleIdentifier identifier;

    /**
     * Constructor por parámetros
     * @param world Mundo en el que se instancia el actor
     * @param position Posición en la que se instancia el actor
     */
    public Apple(World world, Vector2 position){
        super(world, position);
        this.appleTR = AssetMan.getInstance().getApple();
        this.state = STATE_ALIVE;
        this.identifier = new AppleIdentifier();

        createKinematicBody(position,0, SPEED);
        createCircularFixture(APPLE_FIXTURE_RADIUS, this.identifier);
    }

    /**
     * Método encargado de pintar la manzana
     * @param batch Lote que se encarga de pintar el actor
     * @param parentAlpha Indica el nivel de transparencia del padre
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(this.body.getPosition().x - (APPLE_WIDTH/2), this.body.getPosition().y - (APPLE_HEIGHT/2));
        batch.draw(this.appleTR, this.getX(),this.getY(),APPLE_WIDTH,APPLE_HEIGHT);
    }

    /**
     * Método encargado de devolver el estado de la manzana
     * @return Devuelve el estado de la manzana
     */
    public int getState(){
        return this.state;
    }

    /**
     * Método encargado de indicar que la manzana se ha comido
     */
    public void getsEaten(){
        state = STATE_DEAD;
    }

    /**
     * Método encargado de devolver un objeto que identifica a la manzana
     * @return Devuelve un objeto identificador de la manzana
     */
    public AppleIdentifier getIdentifier(){
        return this.identifier;
    }
}
