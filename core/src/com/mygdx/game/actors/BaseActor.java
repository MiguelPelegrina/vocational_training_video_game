package com.mygdx.game.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Clase abstracta que extiende de actor. Sirve como clase base para los demás actores de tal forma
 * que contiene todos los atributos y métodos compartidos.
 */
public abstract class BaseActor extends Actor {
    // Atributos de la clase
    public static final int STATE_ALIVE = 0;
    public static final int STATE_DEAD = 1;
    // Atributos de la instancia
    protected World world;
    protected Vector2 position;
    protected Body body;
    protected Fixture fixture;

    /**
     * Constructor por parámetros
     * @param world Mundo en el que se instancia el actor
     * @param position Posición en la que se instancia el actor
     */
    public BaseActor(World world, Vector2 position) {
        this.world = world;
        this.position = position;
    }

    /**
     * Método encargado de indicar cuando un actor se encuentra fuera de la pantalla
     * @return Devuelve true si la posición actual del actor está fuera de la pantalla o false si se
     * encuentra dentro
     */
    public boolean isOutOfScreen(float height){
        return this.body.getPosition().y <= -height - 0.1f;
    }

    /**
     * Método encargado de parar el movimiento del actor
     */
    public void stopActor(){
        this.body.setLinearVelocity(0,0);
    }

    /**
     * Método encargado de eliminar el body y el fixture del actor
     */
    public void detach(){
        this.body.destroyFixture(this.fixture);
        this.world.destroyBody(this.body);
    }

    // Métodos auxiliares que se van a utilizar en varios clases hijas
    /**
     * Método encargado de crear un cuerpo kinemático
     * @param position Posición en la cual se va a posicionar el actor
     * @param velocityX Velocidad en el eje X que se le asigna al actor
     * @param velocityY Velocidad en el eje Y que se le asigna al actor
     */
    protected void createKinematicBody(Vector2 position, float velocityX, float velocityY){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.KinematicBody;

        this.body = this.world.createBody(bodyDef);
        this.body.setLinearVelocity(velocityX, velocityY);
    }

    /**
     * Método encargado de asignar un fixture circular a un objeto
     * @param radius Tamaño del radio que se quiere asignar como fixture
     * @param identifier Objeto que identificará posteriormente al fixture del objeto a través del
     *                   método getUserData()
     */
    protected void createCircularFixture(float radius, Object identifier){
        CircleShape circle = new CircleShape();
        circle.setRadius(radius);
        this.fixture = this.body.createFixture(circle, 8);
        this.fixture.setUserData(identifier);
        circle.dispose();
    }
}
