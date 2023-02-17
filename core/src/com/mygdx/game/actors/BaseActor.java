package com.mygdx.game.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
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
     * @param world
     * @param position
     */
    public BaseActor(World world, Vector2 position) {
        this.world = world;
        this.position = position;
    }

    /**
     * Método encargado de indicar cuando actor se encuentra fuera de la pantalla
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
     *
     */
    public void detach(){
        this.body.destroyFixture(this.fixture);
        this.world.destroyBody(this.body);
    }

    // Métodos auxiliares
    protected void createCircularFixture(float radius, Object identifier){
        CircleShape circle = new CircleShape();
        circle.setRadius(radius);
        this.fixture = this.body.createFixture(circle, 8);
        this.fixture.setUserData(identifier);
        circle.dispose();
    }
}
