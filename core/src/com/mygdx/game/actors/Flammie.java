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

    private Sound hitSound;

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
        this.hitSound = AssetMan.getInstance().getHitSound();

        createBody();
        createFixture();
    }


    /**
     *
     * @param delta
     */
    @Override
    public void act(float delta) {
        boolean jump  = Gdx.input.isTouched();
        int positionX = Gdx.input.getX();
        int positionY = Gdx.input.getY();

        // En este caso vamos a realizar varias comprobaciones para determinar el movimiento del
        // actor. A cuestiones prácticas la pantalla se divide en una matriz de 3 por 3. Cada
        // campo de la matriz determina el tipo de movimiento, excepto el campo central ([0][0]).
        // Los campos de las esquinas ([0][0], [0][2], [2][0], [2][2]) implican un movimiento
        // diagonal mientras que los demás campos ([1][0], [0][1], [1][2], [2][1]) implican un
        // movimiento unidireccional vertical u horizontal
        if(jump && this.state == STATE_NORMAL){
            // Comprobamos la primera columna de la matriz
            if(positionX > SCREEN_WIDTH * 0.66){
                this.animation = animationRight;

                if(positionY > SCREEN_HEIGHT * 0.33 && positionY < SCREEN_HEIGHT * 0.66 ){
                    this.body.setLinearVelocity(SPEED,0f);
                }else{
                    if(positionY < SCREEN_HEIGHT * 0.33){
                        this.body.setLinearVelocity(SPEED, SPEED);
                    }else{
                        this.body.setLinearVelocity(SPEED,-SPEED);
                    }
                }
            }else{
                // Comprobamos la tercera columna de la matriz
                if(positionX < SCREEN_WIDTH * 0.33){
                    this.animation = animationLeft;

                    if(positionY > SCREEN_HEIGHT * 0.33 && positionY < SCREEN_HEIGHT * 0.66 ){
                        this.body.setLinearVelocity(-SPEED,0f);
                    }else{
                        if(positionY < SCREEN_HEIGHT * 0.33){
                            this.body.setLinearVelocity(-SPEED, SPEED);
                        }else{
                            this.body.setLinearVelocity(-SPEED,-SPEED);
                        }
                    }
                }else{
                    // Comprobamos el centro de la segunda columna matriz
                    this.animation = animationStraight;

                    if(positionY > SCREEN_HEIGHT * 0.66){
                        this.body.setLinearVelocity(0,-SPEED);
                    }else{
                        if(positionY < SCREEN_HEIGHT * 0.33){
                            this.body.setLinearVelocity(0, SPEED);
                        }
                    }
                }
            }
        }else{
            this.body.setLinearVelocity(0,0);
            this.animation = animationStraight;
        }
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
     * Método encargado de "matar" al personaje
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
