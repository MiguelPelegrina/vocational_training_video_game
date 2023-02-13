package com.mygdx.game.actors;

import static com.mygdx.game.extras.Utils.SCREEN_HEIGHT;
import static com.mygdx.game.extras.Utils.SCREEN_WIDTH;
import static com.mygdx.game.extras.Utils.USER_FLAMMIE;

import static java.lang.Math.round;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.extras.AssetMan;

public class Flammie extends Actor {
    // Atributos de la clase
    // Se requieren en otras clases del proyecto
    public static final int STATE_NORMAL = 0;
    public static final int STATE_DEAD = 1;
    // Solo se requieren en la propio clase
    private static final float FLAMMIE_WIDTH = 1f;
    private static final float FLAMMIE_HEIGHT = 1f;
    private static final float FLAMMIE_FIXTURE_RADIUS = 0.3f;
    private static final float SPEED = 1.5f;

    //Atributos de la instancia
    private int state;
    private Animation<TextureRegion> animation;
    private Animation<TextureRegion> animationStraight;
    private Animation<TextureRegion> animationLeft;
    private Animation<TextureRegion> animationRight;
    private Vector2 position;
    private float stateTime;
    private World world;
    private Body body;
    private Fixture fixture;

    /**
     * Constructor por parámetros
     */
    public Flammie(World world, Vector2 position){
        //Inicialización de atributos
        this.animationStraight = AssetMan.getInstance().getFlammieAnimation();
        this.animationLeft = AssetMan.getInstance().getFlammieAnimationL();
        this.animationRight = AssetMan.getInstance().getFlammieAnimationR();
        this.position = position;
        this.world = world;
        this.stateTime = 0f;
        this.state = STATE_NORMAL;

        createBody();
        createFixture();
    }


    /**
     *
     * @param delta
     */
    @Override
    public void act(float delta) {
        // FUNCIONA
        boolean moving  = Gdx.input.isTouched();
        int positionX = Gdx.input.getX();

        if(moving && this.state == STATE_NORMAL){
            if(positionX > SCREEN_WIDTH/2){
                this.body.setLinearVelocity(SPEED,0f);
                this.animation= animationRight;
            }else{
                this.body.setLinearVelocity(-SPEED,0f);
                this.animation = animationLeft;
            }
        }else{
            this.body.setLinearVelocity(0,0);
            this.animation = animationStraight;
        }

        // TODO NO FUNCIONA
        /*boolean moving  = Gdx.input.isTouched();
        int positionX = Gdx.input.getX();
        float distanciaX = positionX/100f;

        if(moving && this.state == STATE_NORMAL){
            if(distanciaX > this.body.getPosition().x){
                this.animation= animationRight;
            }else{
                this.animation = animationLeft;
            }
            this.body.setLinearVelocity(distanciaX - this.body.getPosition().x, 0f);
        }else{
            this.body.setLinearVelocity(0,0);
            this.animation = animationStraight;
        }*/
    }

    /**
     *
     * @param batch
     * @param parentAlpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(body.getPosition().x - FLAMMIE_WIDTH/2, body.getPosition().y - FLAMMIE_HEIGHT/2);
        batch.draw(this.animation.getKeyFrame(stateTime, true), getX(), getY(), FLAMMIE_WIDTH,FLAMMIE_HEIGHT);

        stateTime += Gdx.graphics.getDeltaTime();
    }

    /**
     *
     */
    public void detach(){
        this.body.destroyFixture(this.fixture);
        this.world.destroyBody(this.body);
    }

    public int getState(){
        return this.state;
    }

    /**
     * Método encargado de cambiar el estado del personaje a muerto cuando colisiona con un enemigo.
     */
    public void dies(){
        state = STATE_DEAD;
        stateTime = 0f;
    }

    // Métodos auxiliares
    /**
     *
     */
    private void createBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(this.position);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        this.body = this.world.createBody(bodyDef);
    }

    /**
     *
     */
    private void createFixture(){
        CircleShape circle = new CircleShape();
        circle.setRadius(FLAMMIE_FIXTURE_RADIUS);
        this.fixture = this.body.createFixture(circle, 8);
        this.fixture.setUserData(USER_FLAMMIE);
        circle.dispose();
    }
}
